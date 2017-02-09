package cu.uci.abcd.domain.circulation;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abos.core.domain.Row;

@Entity
@Table(name = "dreservation", schema = "abcdn")
@SequenceGenerator(name = "seq_dreservation", sequenceName = "sq_dreservation", allocationSize = 1, schema = "abcdn")
public class Reservation implements Serializable, Row {
	private static final long serialVersionUID = 3584902974041192528L;
	        
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dreservation")
	@Column(name = "id")
	private Long reservationID;
	
	@Column(name = "reservationdate")
	private Date reservationDate;	
	
	@Column(name = "reservationenddate")
	private Date reservationEndDate;	
	
	@Column(name = "creationdate")
	private Date creationDate;

	@ManyToOne
	@JoinColumn(name = "state")
	private Nomenclator state;	
	
	@ManyToOne
	@JoinColumn(name = "loanuser")
	private LoanUser loanUser;
	
	@ManyToOne
	@JoinColumn(name = "reservationtype")
	private Nomenclator reservationType;
	
	@ManyToOne
	@JoinColumn(name = "reasoncancel")
	private Nomenclator reasoncancel;
	
	@Column(name = "title")
	private String title;

	@ManyToOne
	@JoinColumn(name = "objecttype")
	private Nomenclator objecttype;	
	
	@Column(name = "cancellationdate")
	private Date cancellationDate;	
	
	@Column(name = "reservationhour")
	private java.util.Date reservationHour;	

	public java.util.Date getReservationHour() {
		return reservationHour;
	}

	public void setReservationHour(java.util.Date reservationHour) {
		this.reservationHour = reservationHour;
	}

	public Long getReservationID() {
		return reservationID;
	}

	public void setReservationID(Long reservationID) {
		this.reservationID = reservationID;
	}

	public Nomenclator getState() {
		return state;
	}

	public void setState(Nomenclator state) {
		this.state = state;
	}    
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "dreservationloanobject", schema = "abcdn", joinColumns = { @JoinColumn(name = "reservation", referencedColumnName = "id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "loanobject", referencedColumnName = "id", nullable = false) })
	private List<LoanObject> reservationList;	
	

	public Reservation(){
		super();
		reservationList = new ArrayList<>();		
	}

	public LoanUser getLoanUser() {
		return loanUser;
	}

	public void setLoanUser(LoanUser loanUser) {
		this.loanUser = loanUser;
		if (!loanUser.getReservationList().contains(this)) {
			loanUser.getReservationList().add(this);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((loanUser == null) ? 0 : loanUser.hashCode());
		result = prime * result + ((reservationID == null) ? 0 : reservationID.hashCode());
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
		Reservation other = (Reservation) obj;	
		if (loanUser == null) {
			if (other.loanUser != null)
				return false;
		} else if (!loanUser.equals(other.loanUser))
			return false;
		if (reservationID == null) {
			if (other.reservationID != null)
				return false;
		} else if (!reservationID.equals(other.reservationID))
			return false;
		return true;
	}
	
	
	public Nomenclator getReservationType() {
		return reservationType;
	}

	public void setReservationType(Nomenclator reservationType) {
		this.reservationType = reservationType;
	}

	@PrePersist
	public void generateDefaultValues() {

	}

	public Date getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}

	public Date getReservationEndDate() {
		return reservationEndDate;
	}

	public void setReservationEndDate(Date reservationEndDate) {
		this.reservationEndDate = reservationEndDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	

	public Date getCancellationDate() {
		return cancellationDate;
	}

	public void setCancellationDate(Date cancellationDate) {
		this.cancellationDate = cancellationDate;
	}	
	
	public List<LoanObject> getReservationList() {
		return reservationList;
	}

	public void setReservationList(List<LoanObject> reservationList) {
		this.reservationList = reservationList;
	}

	@Override
	public Object getRowID() {
		return getReservationID();
	}

	public Nomenclator getReasoncancel() {
		return reasoncancel;
	}

	public void setReasoncancel(Nomenclator reasoncancel) {
		this.reasoncancel = reasoncancel;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Nomenclator getObjecttype() {
		return objecttype;
	}

	public void setObjecttype(Nomenclator objecttype) {
		this.objecttype = objecttype;
	}
	
	public String getAuthorLoanObject(){
		return getReservationList().get(0).getAuthor();
	}
	
		
}
