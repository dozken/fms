package controllers;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Ebean;

import play.i18n.Messages;
import static play.data.Form.form;
import models.AuthorisedUser;
import models.SecurityRole;
import models.Telephone;
import play.data.DynamicForm;
import play.libs.Crypto;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.user.login;
import views.html.user.newBuh;
import views.html.home;
import views.html.success;

public class Users extends Controller
{
    public static Result login()
    {
        DynamicForm form = form().bindFromRequest();
        String identifier = form.get("identifier");
        String password = form.get("password");
        
        password = Crypto.encryptAES(password);
        
        AuthorisedUser user;
        
        if (identifier.contains("@"))
        	user = AuthorisedUser.getByMail(identifier, password); else 
        	user = AuthorisedUser.getByTel(identifier, password);
        
        if (user == null) {
        	flash("authorization", Messages.get("authorization.error"));
        	return redirect(request().getHeader("referer")); 
        } else {
        	session("connected", user.email);
        	return ok(home.render());
        }
    }

    public static AuthorisedUser getConnectedUser(){
        String mail = session().get("connected");
        if(mail==null)
            return null;
        else
            return AuthorisedUser.getByMail(mail);
    }
    
    public static Result newBuh() {
    	return ok(newBuh.render()); 
    }
    
    public static Result buhs() {
    	return TODO; 
    }
    
    public static Result saveBuh() {
    	DynamicForm form = form().bindFromRequest();
    	System.err.println(form);
    	String firstName = form.get("firstName"); 
    	String lastName  = form.get("lastName");
    	String email = form.get("email");
    	String password = form.get("password");
    	String repeat = form.get("password");
    	AuthorisedUser user = AuthorisedUser.getByMail(email);
    	if (!password.equals(repeat) || user !=  null) {
    		return redirect(request().getHeader("referer"));
    	}
    	
    	user = new AuthorisedUser();
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
    	
    	password = Crypto.encryptAES(password);
    	
    	user.firstName = firstName;
    	user.lastName = lastName;
    	user.email = email; 
    	user.password = password;
    	user.roles = new ArrayList<SecurityRole>();
    	user.roles.add(SecurityRole.findByName("buh"));
    	user.save();
    	for (Telephone tel : user.tels) {
    		tel.user = user;
    		tel.save();
    		Ebean.saveAssociation(tel, "user");
    	}
    	
    	return ok(success.render("successfullyRegistered"));
    }
    
    public static Result logOut() {
		session().remove("connected");
		return Application.index();
	}
}