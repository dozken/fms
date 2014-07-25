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
package models;

import be.objectify.deadbolt.core.models.Permission;
import be.objectify.deadbolt.core.models.Role;
import be.objectify.deadbolt.core.models.Subject;
import play.db.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.UniqueConstraint;

import java.util.List;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
@Entity
public class AuthorisedUser extends Model implements Subject
{
    @Id
    public Long id;

    public String firstName;
    
    public String lastName; 
    
    @Column(unique = true)
    public String email;
    
    public String password;
    
    @ManyToOne(cascade = CascadeType.ALL)
    public Company myCompany; 
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy="buh")
    public List<Company> clients;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy="user")
    public List<Telephone> tels;

    @ManyToMany(cascade = CascadeType.ALL)
    public List<SecurityRole> roles;

    @ManyToMany(cascade = CascadeType.ALL)
    public List<UserPermission> permissions;

    public static final Finder<Long, AuthorisedUser> find = new Finder<Long, AuthorisedUser>(Long.class,AuthorisedUser.class);
    @Override
    public List<? extends Role> getRoles()
    {
        return roles;
    }

    @Override
    public List<? extends Permission> getPermissions()
    {
        return permissions;
    }

    @Override
    public String getIdentifier()
    {
        return email;
    }
    
    public static void delete(Long id) {
    	find.byId(id).delete();
    }

    public static AuthorisedUser findByUserName(String userName)
    {
        return find.where()
                   .eq("userName",
                       userName)
                   .findUnique();
    }

	public static AuthorisedUser getByTel(String tel, String password) {
		return find.where().eq("tel", tel).eq("password", password).findUnique();
	}

	public static AuthorisedUser getByMail(String email, String password) {
		return find.where().eq("email", email).eq("password", password).findUnique();
	}

	public static AuthorisedUser getByMail(String email) {
		return find.where().eq("email", email).findUnique();
	}
}
