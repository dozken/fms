package controllers;

import play.i18n.Messages;
import static play.data.Form.form;

import models.AuthorisedUser;
import play.data.DynamicForm;
import play.libs.Crypto;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.user.login;
import views.html.user.newBuh;
import views.html.home; 

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
    
    public static Result newBuh() {
    	return ok(newBuh.render()); 
    }
    
    public static Result buhs() {
    	return TODO; 
    }
    
    public static Result saveBuh() {
    	return TODO; 
    }
}