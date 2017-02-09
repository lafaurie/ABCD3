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
@Table(name = "dpurchaserequest", schema = "abcdn")
@SequenceGenerator(name = "seq_dpurchaserequest", sequenceName = "sq_dpurchaserequest", allocationSize = 1, schema = "abcdn")
public class PurchaseRequest implements Serializable, Row {
	private static final long serialVersionUID = -975273211353246714L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dpurchaserequest")
	@Column(name = "id", nullable = false)
	private Long purchaseRequestID;

	@Column(name = "creationdate", nullable = false)
	private Date creationDate;

	@Column(name = "requestidentifier", nullable = false, length = 15)
	private String requestIdentifier;

	@Column(name = "requestnumber", nullable = false)
	private String requestNumber;

	@Column(name = "objecttype", nullable = false)
	private String objectType;

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	@ManyToOne
	@JoinColumn(name = "area", nullable = false)
	private Library area;

	@ManyToOne
	@JoinColumn(name = "creator", nullable = false)
	private Worker creator;

	@ManyToOne
	@JoinColumn(name = "approvedby")
	private Worker approvedBy;

	@ManyToOne
	@JoinColumn(name = "state", nullable = false)
	private Nomenclator state;

	@ManyToOne
	@JoinColumn(name = "acceptancereason")
	private Nomenclator acceptanceMotive;

	@ManyToOne
	@JoinColumn(name = "rejectionreason")
	private Nomenclator rejectionMotive;
	
	@ManyToOne
	@JoinColumn(name = "purchaseorder")
	private PurchaseOrder purchaseorder;


	public PurchaseOrder getPurchaseorder() {
		return purchaseorder;
	}

	public void setPurchaseorder(PurchaseOrder purchaseorder) {
		this.purchaseorder = purchaseorder;
	}

	public Nomenclator getAcceptanceMotive() {
		return acceptanceMotive;
	}

	public void setAcceptanceMotive(Nomenclator acceptanceMotive) {
		this.acceptanceMotive = acceptanceMotive;
	}

	public Nomenclator getRejectionMotive() {
		return rejectionMotive;
	}

	public void setRejectionMotive(Nomenclator rejectionMotive) {
		this.rejectionMotive = rejectionMotive;
	}

	@OneToMany(mappedBy = "purchaseRequest")
	protected List<Desiderata> desideratass;

	public Long getPurchaseRequestID() {
		return purchaseRequestID;
	}

	public void setPurchaseRequestID(Long purchaseRequestID) {
		this.purchaseRequestID = purchaseRequestID;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getRequestIdentifier() {
		return requestIdentifier;
	}

	public void setRequestIdentifier(String requestIdentifier) {
		this.requestIdentifier = requestIdentifier;
	}

	public Library getArea() {
		return area;
	}

	public void setArea(Library area) {
		this.area = area;
	}

	public Worker getCreator() {
		return creator;
	}

	public void setCreator(Worker creator) {
		this.creator = creator;
	}

	public Worker getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedby(Worker approvedby) {
		this.approvedBy = approvedby;
	}

	public Nomenclator getState() {
		return state;
	}

	public void setState(Nomenclator state) {
		this.state = state;
	}

	public List<Desiderata> getDesideratas() {
		return desideratass;
	}

	public void setDesideratas(List<Desiderata> desideratass) {
		this.desideratass = desideratass;
	}

	public void associateDesiderata(Desiderata desiderata) {
		this.desideratass.add(desiderata);
		if (!desiderata.getPurchaseRequest().equals(this)) {
			desiderata.setPurchaseRequest(this);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((purchaseRequestID == null) ? 0 : purchaseRequestID.hashCode());
		return result;
	}

	@Override
	public Object getRowID() {
		return getPurchaseRequestID();
	}

	public String getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	public void setApprovedBy(Worker approvedBy) {
		this.approvedBy = approvedBy;
	}

}
