package controllers;

import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.mail_example;

/**
 * Created by RSA on 21.07.2014.
 */
public class Mails extends Controller {
    public static Result showExample(){
        return ok(mail_example.render());
    }
    public static Result saveMail(){
        try{
            DynamicForm form = Form.form().bindFromRequest();
            String text = form.get("message");

            return ok();
        }catch(Exception e){
            e.printStackTrace();
            return ok();
        }
    }
}
