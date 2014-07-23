package controllers;

import models.AuthorisedUser;
import play.Play;
import play.Routes;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.user.login;
import views.html.helps.messenger;

public class Application extends Controller{
    public static Result index(){
        return ok(login.render());
    }
    public static Result javascriptRoutes() {
        response().setContentType("text/javascript");
        return ok(Routes.javascriptRouter(
                "myJsRoutes",
                Drive.saveUploadedFile()
        ));
    }
    public static void restrict(){
        AuthorisedUser user = Users.getConnectedUser();
        if(user==null)
            redirect(routes.Application.index());
        else
        if(!user.roles.get(0).name.equals("admin")||!user.roles.get(0).name.equals("moderator"))
            redirect(routes.Application.accessDenied());
    }
    public static Result accessDenied(){
        return ok(messenger.render("warning", Messages.get("accessDenied"), Messages.get("accessDeniedDesc")));
    }
    public static String getRootPath(){
        return Play.application().path().getPath();
    }
}