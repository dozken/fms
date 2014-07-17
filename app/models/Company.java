package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Company extends Model{
	
    @Id
    public Long id;
    
	public String name;
	
	public String requisite;
	
	@ManyToMany(cascade = CascadeType.ALL)
	public List<AuthorisedUser> users;
	
	public static final Finder<Long, Company> find = new Finder<Long, Company>(Long.class,Company.class);
	
}
