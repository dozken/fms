package controllers;

import static play.data.Form.form;

import java.util.ArrayList;

import com.avaje.ebean.Ebean;

import models.AuthorisedUser; 
import models.Bank;
import models.Company;
import models.SecurityRole;
import models.Telephone;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Crypto;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.company.compList;
import views.html.company.newComp;
import views.html.company.newUser;
import views.html.company.userList;
import views.html.success;

public class CompanyUsers extends Controller
{
	
	public static Result delete(Long id) {
		AuthorisedUser user =  AuthorisedUser.find.byId(id);
		user.myCompany = null;
		user.save();
		user.delete();
		return redirect(request().getHeader("referer"));
	}
	
	public static Result list(Long id){
		return ok(userList.render(Company.find.byId(id))); 
	}
	
    public static Result newUser(Long id) {
    	return ok(newUser.render(Company.find.byId(id)));
    }
    
    public static Result saveUser(){
    	flash("success", "success");
    	DynamicForm form = form().bindFromRequest();
    	AuthorisedUser user = new AuthorisedUser();
    	user.firstName = form.get("firstName");
    	user.lastName = form.get("lastName");
    	user.email = form.get("email");
    	user.password = Crypto.encryptAES(user.email);
    	
    	user.tels = new ArrayList<Telephone>(); 
    	for (int i=1; i<=10; i++) if (form.data().containsKey("tel"+i)) {
    		String tel = form.get("tel" + i);  
    		if (!Telephone.contains(tel)) {
    			Telephone t = new Telephone();
    			t.number = tel;
    			t.save();
    			user.tels.add(t);
    		}
    	}
    	
    	String role = form.get("role");
    	user.roles = new ArrayList<SecurityRole>();
    	user.roles.add(SecurityRole.findByName(role));
    	
    	Company comp = Company.find.byId(Long.parseLong(form.get("compId")));
    	user.myCompany = comp;
    	user.save();
    	
    	return redirect("userList?id="+comp.id);
    }
}