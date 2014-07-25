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
public class Bank extends Model{
	
    @Id
    public Long id;
    
	public String name;
	
	public String iik;
	
	public String bik; 
	
	@ManyToOne(cascade = CascadeType.ALL)
	public Company company;
	
	public static final Finder<Long, Bank> find = new Finder<Long, Bank>(Long.class,Bank.class);

	public static boolean contains(String name) {
		return find.where().eq("name", name).findUnique() != null;
	}
	
	public static Bank byName(String bank) {
			return find.where().eq("name", bank).findUnique(); 
	}
	
}
