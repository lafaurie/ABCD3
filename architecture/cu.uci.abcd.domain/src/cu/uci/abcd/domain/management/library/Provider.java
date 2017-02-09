package cu.uci.abcd.domain.management.library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.Person;
import cu.uci.abos.core.domain.Row;

@Entity
@Table(name = "dprovider", schema = "abcdn")
@SequenceGenerator(name = "seq_dprovider", sequenceName = "sq_dprovider", schema = "abcdn", allocationSize = 1)
public class Provider implements Serializable, Row {
	private static final long serialVersionUID = -7861435917073847843L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dprovider")
	@Column(name = "id", nullable = false)
	private Long providerID;
	
	@Column(name = "rif", length = 20)
	private String rif;
	
	@Column(name = "nit", length = 20)
	private String nit;
	
	@Column(name = "providername", nullable = true, length = 50)
	private String providerName;

	@Column(name = "webpage", nullable = true, length = 100)
	private String webPage;
	
	@Column(name = "email", nullable = true, length = 50)
	private String email;
	
	@Column(name = "firstphonenumber", nullable = true, length = 20)
	private String firstPhoneNumber;
	
	@Column(name = "secondphonenumber", nullable = true, length = 20)
	private String secondPhoneNumber;
	
	@Column(name = "fax", nullable = true, length = 20)
	private String fax;
	
	@Column(name = "address", nullable = true, length = 300)
	private String address;
	
	@ManyToOne
	@JoinColumn(name = "country", nullable = false)
	private Nomenclator country;
	  
	  
	@Column(name = "isintitutional", nullable = false)
	private boolean isIntitutional;
	
	
	@OneToOne
	@JoinColumn(name = "person", nullable = true)
	private Person person;

	  
	  
	public boolean isIntitutional() {
		return isIntitutional;
	}

	public void setIntitutional(boolean isIntitutional) {
		this.isIntitutional = isIntitutional;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Nomenclator getCountry() {
		return country;
	}

	public void setCountry(Nomenclator country) {
		this.country = country;
	}

	public String getWebPage() {
		return webPage;
	}

	public void setWebPage(String webPage) {
		this.webPage = webPage;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstPhoneNumber() {
		return firstPhoneNumber;
	}

	public void setFirstPhoneNumber(String firstPhoneNumber) {
		this.firstPhoneNumber = firstPhoneNumber;
	}
	
	public String getSecondPhoneNumber() {
		return secondPhoneNumber;
	}

	public void setSecondPhoneNumber(String secondPhoneNumber) {
		this.secondPhoneNumber = secondPhoneNumber;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@ManyToOne
	@JoinColumn(name = "state", nullable = false)
	private Nomenclator providerState;

	@OneToOne
	@JoinColumn(name = "representative", nullable = true)
	private Person representative;

	@ManyToMany
	@JoinTable(name = "dprovidertype", schema = "abcdn", joinColumns = { @JoinColumn(name = "provider", referencedColumnName = "id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "providertype", referencedColumnName = "id", nullable = false) })
	private List<Nomenclator> types;

	@ManyToOne
	@JoinColumn(name = "library", nullable = false)
	private Library library;
	
	public Provider() {
		super();
		types = new ArrayList<>();
	}

	public List<Nomenclator> getTypes() {
		return types;
	}

	public void setTypes(List<Nomenclator> types) {
		this.types = types;
	}

	public Long getProviderID() {
		return providerID;
	}

	public void setProviderID(Long providerID) {
		this.providerID = providerID;
	}

	public String getRif() {
		return rif;
	}

	public void setRif(String rif) {
		this.rif = rif;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public Nomenclator getProviderState() {
		return providerState;
	}

	public void setProviderState(Nomenclator providerState) {
		this.providerState = providerState;
	}

	public Person getRepresentative() {
		return representative;
	}

	public void setRepresentative(Person representative) {
		this.representative = representative;
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}
	
	@Override
	public Object getRowID() {
		return getProviderID();
	}

	@Override
	public String toString() {
		return getName();
	}
	
	public String getName(){
		return (isIntitutional())?getProviderName():getPerson().getFullName();
	}
	
	public String getService(){
		
		List<String> type = new ArrayList<String>();
		for (Nomenclator nomenclator : getTypes()) {
			//String sep = "";
			//if (type != "") {
			//	sep = " - ";
			//}
			type.add(nomenclator.getNomenclatorName());
		}
		 Collections.sort(type);
		 String type1 = "";
		 for (int i = 0; i < type.size(); i++) {
			 type1 = type1 + "  " +type.get(i);
		}
		 return type1;
	}
}
