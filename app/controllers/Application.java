package controllers;

import models.AuthorisedUser;
import play.Routes;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.user.login;

public class Application extends Controller{
    public static Result index(){
        return ok(login.render());
    }
    public static Result javascriptRoutes() {
        response().setContentType("text/javascript");
        return ok(Routes.javascriptRouter(
                "myJsRoutes",
                routes.javascript.Files.saveUploadedFile()
        ));
    }
}