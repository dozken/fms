/*
 * Copyright 2012 Steve Chaloner
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.avaje.ebean.Ebean;

import models.AuthorisedUser;
import models.SecurityRole;
import models.UserPermission;
import play.Application;
import play.GlobalSettings;
import play.libs.Crypto;
import play.libs.F.Promise;
import play.mvc.Http.RequestHeader;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.Arrays;

import views.html.errorPage;
import views.html.notFoundPage;
import static play.mvc.Results.*;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
public class Global extends GlobalSettings
{
    @Override
    public void onStart(Application application)
    {
        if (SecurityRole.find.findRowCount() == 0)
        {
            for (String name : Arrays.asList("admin", "buh", "cadmin", "cbuh"))
            {
                SecurityRole role = new SecurityRole();
                role.name = name;
                role.save();
            }
        }

        if (UserPermission.find.findRowCount() == 0)
        {
            UserPermission permission = new UserPermission();
            permission.value = "printers.edit";
            permission.save();
        }
        
        if (AuthorisedUser.find.findRowCount() == 0)
        {
            AuthorisedUser user = new AuthorisedUser();
            user.firstName = "Global";
            user.lastName = "Analytic";
            user.email = "global@analytic";
            user.password = Crypto.encryptAES("1234");
            user.roles = new ArrayList<SecurityRole>();
            user.roles.add(SecurityRole.findByName("admin"));
            user.permissions = new ArrayList<UserPermission>();
            user.permissions.add(UserPermission.findByValue("printers.edit"));
            user.save();
            Ebean.saveManyToManyAssociations(user,"roles");
            Ebean.saveManyToManyAssociations(user,"permissions");
        }
    }
    public Promise<Result> onError(RequestHeader request, Throwable t) {
        return Promise.<Result>pure(internalServerError(
                errorPage.render(t.getMessage())
        ));
    }
    public Promise<Result> onHandlerNotFound(RequestHeader request) {
        return Promise.<Result>pure(notFound(
                notFoundPage.render(request.uri())
        ));
    }
}
