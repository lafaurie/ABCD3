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
import cu.uci.abcd.domain.management.library.Provider;
import cu.uci.abcd.domain.management.library.Worker;
import cu.uci.abos.core.domain.Row;

@Entity
@Table(name = "dpurchaseorder", schema = "abcdn")
@SequenceGenerator(name = "seq_dpurchaseorder", sequenceName = "sq_dpurchaseorder", allocationSize = 1, schema = "abcdn")
public class PurchaseOrder implements Serializable, Row{
	private static final long serialVersionUID = 4456382439091519225L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dpurchaseorder")
	@Column(name = "id", nullable = false)
	private Long purchaseOrderID;

	@Column(name = "creationdate", nullable = false)
	private Date creationDate;

	@Column(name = "requestidentifier")
	private String requestIdentifier;

	@ManyToOne
	@JoinColumn(name = "creator", nullable = false)
	private Worker creator;

	@ManyToOne
	@JoinColumn(name = "approvedby")
	private Worker approvedBy;

	@ManyToOne
	@JoinColumn(name = "provider", nullable = false)
	private Provider provider;

	@ManyToOne
	@JoinColumn(name = "state", nullable = false)
	private Nomenclator state;

	@ManyToOne
	@JoinColumn(name = "acceptancereason")
	private Nomenclator acceptanceMotive;

	@ManyToOne
	@JoinColumn(name = "rejectionreason")
	private Nomenclator rejectionMotive;

	@Column(name = "quantity")
	private Integer quantity;


	@Column(name = "quotenumber")
	private Integer quoteNumber;
	
	@Column(name = "ordernumber", nullable = false)
	private String orderNumber;
	
	@Column(name = "price")
	private Double price;
	
	@Column(name = "objecttype")
	private String objectType;

	@Column(name = "objecttitle")
	private String objecttitle;

	@Column(name = "totalamount")
	private Double totalAmount;
	
	@ManyToOne
	@JoinColumn(name = "cointype")
	private Nomenclator cointype;

	@ManyToOne
	@JoinColumn(name = "library", nullable = false)
	private Library library;
	
	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

	@OneToMany(mappedBy = "purchaseorder")
	protected List<PurchaseRequest> purchaseRequests;

	public Long getPurchaseOrderID() {
		return purchaseOrderID;
	}

	public void setPurchaseOrderID(Long purchaseOrderID) {
		this.purchaseOrderID = purchaseOrderID;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationdate) {
		this.creationDate = creationdate;
	}

	public String getRequestIdentifier() {
		return requestIdentifier;
	}

	public void setRequestIdentifier(String requestIdentifier) {
		this.requestIdentifier = requestIdentifier;
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

	public void setApprovedBy(Worker approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Nomenclator getState() {
		return state;
	}

	public void setState(Nomenclator state) {
		this.state = state;
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

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((requestIdentifier == null) ? 0 : requestIdentifier.hashCode());
		result = prime * result + ((purchaseOrderID == null) ? 0 : purchaseOrderID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PurchaseOrder other = (PurchaseOrder) obj;
		if (requestIdentifier == null) {
			if (other.requestIdentifier != null) {
				return false;
			}
		} else if (!requestIdentifier.equals(other.requestIdentifier)) {
			return false;
		}
		if (purchaseOrderID == null) {
			if (other.purchaseOrderID != null) {
				return false;
			}
		} else if (!purchaseOrderID.equals(other.purchaseOrderID)) {
			return false;
		}
		return true;
	}

	@Override
	public Object getRowID() {
		return getPurchaseOrderID();
	}

	public Integer getQuoteNumber() {
		return quoteNumber;
	}

	public void setQuoteNumber(Integer quoteNumber) {
		this.quoteNumber = quoteNumber;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getObjecttitle() {
		return objecttitle;
	}

	public void setObjecttitle(String objecttitle) {
		this.objecttitle = objecttitle;
	}

	public Nomenclator getCointype() {
		return cointype;
	}

	public void setCointype(Nomenclator cointype) {
		this.cointype = cointype;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}	
	
}
