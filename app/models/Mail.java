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
public class Mail extends Model{
	
    @Id
    public Long id;
    
	public AuthorisedUser to;
	
	public AuthorisedUser from;
	
	public String message; 
	
	
	@ManyToMany(cascade = CascadeType.ALL)
	public List<File> atts;
	
	public Long time;
		
	public static final Finder<Long, Mail> find = new Finder<Long, Mail>(Long.class,Mail.class);
	
}
