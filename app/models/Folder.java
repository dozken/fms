package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Folder extends Model{
	
    @Id
    public Long id;
    
	public String name;
	
	@ManyToOne(cascade = CascadeType.ALL)
	public AuthorisedUser user;
	
	public Long time; 
	
	public static final Finder<Long, Folder> find = new Finder<Long, Folder>(Long.class,Folder.class);

    public static Folder findByInfo(AuthorisedUser user, String name){
        return find.where().eq("user", user).eq("name", name).findUnique();
    }
	
}
