package cu.uci.abcd.domain.common;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import cu.uci.abcd.domain.acquisition.Suggestion;
import cu.uci.abcd.domain.circulation.Penalty;
import cu.uci.abcd.domain.circulation.Transaction;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Provider;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abos.core.domain.Row;

@Entity
@Table(name = "dloanobject", schema = "abcdn")
@SequenceGenerator(name = "seq_loanobject", sequenceName = "sq_dloanobject", schema = "abcdn", allocationSize = 1)
public class LoanObject implements Serializable, Row {
	private static final long serialVersionUID = 7760287126131166077L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_loanobject")
	@Column(name = "id")
	private Long loanObjectID;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "author")
	private String author;
	
	@Column(name = "inventorynumber")
	private String inventorynumber;
	
	@ManyToOne
	@JoinColumn(name = "room")
	private Room room;
	
	@ManyToOne
	@JoinColumn(name = "provider")
	private Provider provider;
	
	@Column(name = "creationdate")
	private Date registrationDate;
	
	@Column(name = "volume")
	private String volume;
	
	@Column(name = "tome")
	private String tome;
	
	@Column(name = "editionnumber")
	private String editionNumber;
	
	@ManyToOne
	@JoinColumn(name = "kindofmaterial")
	private Nomenclator recordType;
	
	@ManyToOne
	@JoinColumn(name = "objectstate")
	private Nomenclator loanObjectState;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "library")
	private Library libraryOwner;

	@Column(name = "isisdatabasename")
	private String isisdatabasename;
	
	@ManyToOne
	@JoinColumn(name = "acquisitioncointype")
	private Nomenclator acquisitionCoinType;
	
	@Column(name = "price")
	private Double price;
	
	@Column(name = "controlnumber")
	private String controlNumber;
	
	@Column(name = "checked")
	private boolean checked;
	
	@ManyToOne
	@JoinColumn(name = "situation")
	private Nomenclator situation;

	@ManyToOne
	@JoinColumn(name = "recommendation")
	private Suggestion recommendation;
	
	@Column(name = "conditions")
	private String conditions;
	
	@Column(name = "catalogued")
	private Boolean catalogued;
	
	@Column(name = "exchangedby")
	private String exchangedBy;
	
	@ManyToOne
	@JoinColumn(name = "acquisitiontype")
	private Nomenclator acquisitionType;
		
	@OneToMany(mappedBy = "loanObject")
	private List<Transaction> transactions;
	
	@OneToMany(mappedBy = "loanObject")
	private List<Penalty> penalties;
	
	@Transient
	private	int quantity=0;
	
	public LoanObject() {
		super();		
		transactions = new ArrayList<>();
		penalties = new ArrayList<>();
	}

	public Long getLoanObjectID() {
		return loanObjectID;
	}

	public void setLoanObjectID(Long loanObjectID) {
		this.loanObjectID = loanObjectID;
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

	public String getIsisDataBaseName() {
		return isisdatabasename;
	}

	public void setIsisDataBaseName(String isisdatabasename) {
		this.isisdatabasename = isisdatabasename;
	}

	public String getInventorynumber() {
		return inventorynumber;
	}

	public void setInventorynumber(String inventorynumber) {
		this.inventorynumber = inventorynumber;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
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

	public String getEditionNumber() {
		return editionNumber;
	}

	public void setEditionNumber(String editionNumber) {
		this.editionNumber = editionNumber;
	}

	public Nomenclator getRecordType() {
		return recordType;
	}

	public void setRecordType(Nomenclator recordType) {
		this.recordType = recordType;
	}

	public Nomenclator getLoanObjectState() {
		return loanObjectState;
	}

	public void setLoanObjectState(Nomenclator loanObjectState) {
		this.loanObjectState = loanObjectState;
	}

	public Library getLibraryOwner() {
		return libraryOwner;
	}

	public void setLibraryOwner(Library libraryOwner) {
		this.libraryOwner = libraryOwner;
	}
	
	public Double getPrice(){
		return price;
	}
	
	public void setPrice(Double price){
		this.price = price;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public List<Penalty> getPenalties() {
		return penalties;
	}

	public void setPenalties(List<Penalty> penalties) {
		this.penalties = penalties;
	}

	@PrePersist
	public void generateCreationDate() {
		// this.registrationDate = DomainUtil.CURRENT_DATE;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((inventorynumber == null) ? 0 : inventorynumber.hashCode());
		result = prime * result + ((libraryOwner == null) ? 0 : libraryOwner.hashCode());
		result = prime * result + ((loanObjectID == null) ? 0 : loanObjectID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoanObject other = (LoanObject) obj;
		if (inventorynumber == null) {
			if (other.inventorynumber != null)
				return false;
		} else if (!inventorynumber.equals(other.inventorynumber))
			return false;
		if (libraryOwner == null) {
			if (other.libraryOwner != null)
				return false;
		} else if (!libraryOwner.equals(other.libraryOwner))
			return false;
		if (loanObjectID == null) {
			if (other.loanObjectID != null)
				return false;
		} else if (!loanObjectID.equals(other.loanObjectID))
			return false;
		return true;
	}    

	public Nomenclator getAcquisitionCoinType() {
		return acquisitionCoinType;
	}

	public void setAcquisitionCoinType(Nomenclator acquisitionCoinType) {
		this.acquisitionCoinType = acquisitionCoinType;
	}
	
	public Nomenclator getAcquisitionType(){
		return acquisitionType;
	}
	
	public void setAcquisitionType(Nomenclator acquisitionType){
		this.acquisitionType = acquisitionType;
	}
	
	public Boolean getCatalogued(){
		return this.catalogued;
	}
	
	public void setCatalogued(Boolean catalogued){
		this.catalogued = catalogued;
	}

	@Override
	public Object getRowID() {
		return getLoanObjectID();
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getBookAvailable() {
		return quantity;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getControlNumber() {
		return controlNumber;
	}

	public void setControlNumber(String controlNumber) {
		this.controlNumber = controlNumber;
	}
	
	public Nomenclator getSituation(){
		return situation;
	}
	
	public void setSituation(Nomenclator situation){
		this.situation = situation;
	}
	
	public Suggestion getRecommendation(){
	    return recommendation;
	}
	
	public void setRecommendation(Suggestion recommendation){
		this.recommendation = recommendation;
	}
	
	public String getConditions(){
		return conditions;
	}
	
	public void setConditions(String conditions){
		this.conditions = conditions;
	}
	
	public Provider getProvider(){
		return this.provider;
	}
	
	public void setProvider(Provider provider){
		this.provider = provider;
	}
	
	public String getExchangedBy(){
		return this.exchangedBy;
	}
	
	public void setExchangedBy(String exchangedBy){
		this.exchangedBy = exchangedBy;
	}
}