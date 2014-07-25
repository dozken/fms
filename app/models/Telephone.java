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
public class Telephone extends Model{
	
    @Id
    public Long id;
    
	public String number;
	
	public int delme;
	
	@ManyToOne(cascade = CascadeType.ALL)
	public AuthorisedUser user;
	
	@ManyToOne(cascade = CascadeType.ALL)
	public Company company;
	
	public static final Finder<Long, Telephone> find = new Finder<Long, Telephone>(Long.class,Telephone.class);

	public static boolean contains(String tel) {
		return find.where().eq("number", tel).findUnique() != null; 
	}
	
}
