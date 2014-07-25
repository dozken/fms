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
public class Company extends Model{
	
    @Id
    public Long id;
    
	public String name;
	
	public String address;
	
	public String bin;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
	public List<Telephone> tels;
	
	@OneToMany(cascade = CascadeType.ALL)
	public List<Bank> banks;
		
	@ManyToOne(cascade = CascadeType.ALL)
	public AuthorisedUser buh;  
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "myCompany")
	public List<AuthorisedUser> workers;
	
	public static final Finder<Long, Company> find = new Finder<Long, Company>(Long.class,Company.class);

	public static List<Company> getByBuh(AuthorisedUser buh) {
		return find.where().eq("buh", buh).findList();
	}
	
}
