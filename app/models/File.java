package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class File extends Model{
	
    @Id
    public Long id;

	public String name;
	
	public String path;

	public String type;
	
	@ManyToOne(cascade = CascadeType.ALL)
	public AuthorisedUser user;

	@ManyToOne(cascade = CascadeType.ALL)
    public Folder folder;

    @ManyToOne(cascade = CascadeType.ALL)
    public Mail mail;
	
	public Long time;

    public boolean trashed;
	
	public static final Finder<Long, File> find = new Finder<Long, File>(Long.class,File.class);

    public static File findByInfo(AuthorisedUser user, Folder folder, String name){
        return find.where().eq("folder", folder).eq("name", name).ne("trashed",false).findUnique();
    }
}