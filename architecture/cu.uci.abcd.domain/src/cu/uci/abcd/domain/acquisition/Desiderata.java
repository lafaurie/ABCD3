package cu.uci.abcd.domain.acquisition;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Worker;
import cu.uci.abos.core.domain.Row;

@Entity
@Table(name = "ddesiderata", schema = "abcdn")
@SequenceGenerator(name = "seq_ddesiderata", sequenceName = "sq_ddesiderata", allocationSize = 1, schema = "abcdn")
public class Desiderata implements Serializable, Row {
	private static final long serialVersionUID = -5576191849074054708L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ddesiderata")
	@Column(name = "id", nullable = false)
	private Long desiderataID;

	@Column(name = "title", nullable = false, length = 60)
	private String title;

	@Column(name = "author", length = 60)
	private String author;

	@Column(name = "editorial", length = 50)
	private String editorial;

	@Column(name = "publicationcity", length = 50)
	private String publicationCity;

	@Column(name = "editionnumber", length = 10)
	private String editionNumber;

	@Column(name = "publicationyear")
	private Integer publicationYear;

	@Column(name = "volume", length = 10)
	private String volume;

	@Column(name = "tome", length = 10)
	private String tome;

	@Column(name = "itemnumber", length = 10)
	private String itemNumber;

	@Column(name = "isbn", length = 10)
	private String isbn;

	@Column(name = "price")
	private Double price;

	@Column(name = "creationdate", nullable = false)
	private Date creationDate;

	@Column(name = "motive", nullable = false, length = 500)
	private String motive;

	@Column(name = "issn", length = 20)
	private String issn;

	@Column(name = "quantity", length = 20)
	private int quantity;

	@ManyToOne
	@JoinColumn(name = "library", nullable = false)
	private Library library;

	@ManyToOne
	@JoinColumn(name = "usertype", nullable = false)
	private Nomenclator userType;

	@ManyToOne
	@JoinColumn(name = "cointype", nullable = false)
	private Nomenclator cointype;

	@ManyToOne
	@JoinColumn(name = "desideratastate", nullable = false)
	private Nomenclator state;

	@ManyToOne
	@JoinColumn(name = "creator")
	private Worker creator;

	@OneToMany(mappedBy = "associateDesiderata")
	protected List<Suggestion> suggestions;

	@ManyToOne
	@JoinColumn(name = "purchaserequest")
	private PurchaseRequest purchaseRequest;

	public Long getDesidertaID() {
		return desiderataID;
	}

	public void setDesidertaID(Long desidertaID) {
		this.desiderataID = desidertaID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public String getPublicationCity() {
		return publicationCity;
	}

	public void setPublicationCity(String publicationCity) {
		this.publicationCity = publicationCity;
	}

	public String getEditionNumber() {
		return editionNumber;
	}

	public void setEditionNumber(String editionNumber) {
		this.editionNumber = editionNumber;
	}

	public Integer getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(Integer publicationYear) {
		this.publicationYear = publicationYear;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getTome() {
		return tome;
	}

	public void setTome(String tome) {
		this.tome = tome;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getMotive() {
		return motive;
	}

	public void setMotive(String motive) {
		this.motive = motive;
	}

	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

	public Nomenclator getUsertype() {
		return userType;
	}

	public void setUserType(Nomenclator userType) {
		this.userType = userType;
	}

	public Nomenclator getState() {
		return state;
	}

	public void setState(Nomenclator state) {
		this.state = state;
	}	


	public PurchaseRequest getPurchaseRequest() {
		return purchaseRequest;
	}

	public void setPurchaseRequest(PurchaseRequest purchaseRequest) {
		this.purchaseRequest = purchaseRequest;
		if (!purchaseRequest.getDesideratas().contains(this)) {
			purchaseRequest.getDesideratas().add(this);
		}
	}

	public Suggestion addSuggestion(Suggestion suggestion) {
		suggestions.add(suggestion);
		if (!suggestion.getAssociateDesiderata().equals(this)) {
			suggestion.setAssociateDesiderata(this);
		}
		return suggestion;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((desiderataID == null) ? 0 : desiderataID.hashCode());
		result = prime * result + ((desiderataID == null) ? 0 : desiderataID.hashCode());
		return result;
	}

	@Override
	public Object getRowID() {
		return getDesidertaID();
	}

	public List<Suggestion> getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(List<Suggestion> suggestions) {
		this.suggestions = suggestions;
	}

	public Nomenclator getUserType() {
		return userType;
	}

	public Nomenclator getCointype() {
		return cointype;
	}

	public void setCointype(Nomenclator cointype) {
		this.cointype = cointype;
	}

	public Worker getCreator() {
		return creator;
	}

	public void setCreator(Worker creator) {
		this.creator = creator;
	}

	
	
}