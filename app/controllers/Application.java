package controllers;

import models.AuthorisedUser;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.login;

public class Application extends Controller
{
    public static Result index()
    {
        return ok(login.render());
    }
}