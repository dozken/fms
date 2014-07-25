package controllers;

import static play.data.Form.form;

import java.util.ArrayList;

import com.avaje.ebean.Ebean;

import models.AuthorisedUser; 
import models.Bank;
import models.Company;
import models.Telephone;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.company.compList;
import views.html.company.newComp;
import views.html.success;

public class Companies extends Controller
{
    public static Result list() {
    	AuthorisedUser buh = AuthorisedUser.getByMail(session().get("connected"));
        return ok(compList.render(Company.getByBuh(buh)));
    }
    
    public static Result newComp() {
    	return ok(newComp.render()); 
    }
    
    public static Result delete(Long id) {
    	Company comp = Company.find.byId(id);
    	comp.delete();
    	return redirect("compList");
    }
    
    public static Result saveComp() {
    	DynamicForm form = form().bindFromRequest();
    	Company comp = new Company();
    	comp.name = form.get("name");
    	comp.address = form.get("address");
    	comp.bin = form.get("bin");
    	comp.save();
    	comp.tels = new ArrayList<Telephone>();
    	for (int i=1; i<=10; i++) if (form.data().containsKey("tel"+i)) {
    		String tel = form.get("tel" + i);  
    		if (!Telephone.contains(tel)) {
    			Telephone t = new Telephone();
    			t.number = tel;
    			t.company = comp;
    			t.save();
    			comp.tels.add(t);
    		}
    	}
    	
    	comp.banks = new ArrayList<Bank>();
    	for (int i=1; i<=10; i++) if (form.data().containsKey("bank"+i)) {
    		String bank = form.get("bank" + i);
    		String iik  = form.get("iik"+i);
    		String bik  = form.get("bik"+i);
    		Bank b = new Bank();
    		b.name = bank;
    		b.iik = iik;
    		b.bik = bik;
    		b.company = comp;
    		b.save();
    		comp.banks.add(b);
    	}
    	
    	AuthorisedUser buh = AuthorisedUser.getByMail(session().get("connected")); 
    	comp.buh = buh;
    	comp.save(); 
    	flash("success", "success");
    	return redirect("compList"); 
    }
}