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
	
	@OneToMany(cascade = CascadeType.ALL)
	public List<File> files;
	
	public Long time; 
	
	public static final Finder<Long, Folder> find = new Finder<Long, Folder>(Long.class,Folder.class);
	
}
