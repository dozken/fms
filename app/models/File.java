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
	
	@ManyToMany(cascade = CascadeType.ALL)
	public List<AuthorisedUser> users;
	
	@ManyToMany(cascade = CascadeType.ALL)
	public List<Folder> folders;
	
	public Long time;  
	
	public static final Finder<Long, File> find = new Finder<Long, File>(Long.class,File.class);
	
}
