package cu.uci.abcd.domain.management.library;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abos.core.domain.Row;

@Entity
@Table(name = "dcirculationrule", schema = "abcdn")
@SequenceGenerator(name = "seq_dcirculationrule", sequenceName = "sq_dcirculationrule", schema = "abcdn", allocationSize = 1)
public class CirculationRule implements Serializable, Row {
	private static final long serialVersionUID = -1547117352883280554L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dcirculationrule")
	@Column(name = "id", nullable = false)
	private Long circulationRuleID;
	
	@Column(name = "loandaysquantity", nullable = true)
	private Integer quantityDayOnLoan;
	
	@Column(name = "queuedaysquantity", nullable = true)
	private Integer quantityDayOnLoanIfQueue;
	
	@Column(name = "allowedloansquantity", nullable = true)
	private Integer quantityOfLoanAllowed;
	
	@Column(name = "allowedrenewalquantity", nullable = true)
	private Integer quantityOfRenewedAllowed;
	
	@Column(name = "reneweddaysquantity", nullable = true)
	private Integer quantityOfRenewedDayAllowed;
	
	@Column(name = "allowdelayedreservations", nullable = true)
	private boolean reservationDelayAllowed;
	
	@Column(name = "allowedsamerecordcopiesloan", nullable = true)
	private boolean severalMaterialsAllowed;
	
	@Column(name = "allowreservation", nullable = true)
	private boolean reservationAllowed;
	
	@Column(name = "waitdaysreservedmaterialquantity", nullable = true)
	private Integer quantityOfDayToPickUpTheMaterial;
	
	@Column(name = "delaymentdayfine", nullable = true)
	private Double amountPenalty;
	
	@Column(name = "delaymentdayfineifreserved", nullable = true)
	private Double amountPenaltyByReserved;
	
	@Column(name = "lostfine", nullable = true)
	private Double amountPenaltyByLost;
	
	@Column(name = "suspensiondaysperdelaydays", nullable = true)
	private Integer quantitySuspentionDayByDelay;
	
	@Column(name = "suspensiondaysperdelaydaysifreserved", nullable = true)
	private Integer quantitySuspentionDayByDelayOfReservation;

	@Column(name = "lostsuspensionday", nullable = true)
	private Integer lostSuspensionDay;

	public Integer getLostSuspensionDay() {
		return lostSuspensionDay;
	}

	public void setLostSuspensionDay(Integer lostSuspensionDay) {
		this.lostSuspensionDay = lostSuspensionDay;
	}

	@ManyToOne
	@JoinColumn(name = "usertype", nullable = false)
	private Nomenclator loanUserType;

	@ManyToOne
	@JoinColumn(name = "objecttype", nullable = false)
	private Nomenclator recordType;

	@ManyToOne
	@JoinColumn(name = "library", nullable = false)
	private Library library;

	@ManyToOne
	@JoinColumn(name = "state", nullable = false)
	private Nomenclator circulationRuleState;

	public Long getCirculationRuleID() {
		return circulationRuleID;
	}

	public void setCirculationRuleID(Long circulationRuleID) {
		this.circulationRuleID = circulationRuleID;
	}

	public Integer getQuantityDayOnLoan() {
		return quantityDayOnLoan;
	}

	public void setQuantityDayOnLoan(int quantityDayOnLoan) {
		this.quantityDayOnLoan = quantityDayOnLoan;
	}

	public Integer getQuantityDayOnLoanIfQueue() {
		return quantityDayOnLoanIfQueue;
	}

	public void setQuantityDayOnLoanIfQueue(int quantityDayOnLoanIfQueue) {
		this.quantityDayOnLoanIfQueue = quantityDayOnLoanIfQueue;
	}

	public Integer getQuantityOfLoanAllowed() {
		return quantityOfLoanAllowed;
	}

	public void setQuantityOfLoanAllowed(int quantityOfLoanAllowed) {
		this.quantityOfLoanAllowed = quantityOfLoanAllowed;
	}

	public Integer getQuantityOfRenewedAllowed() {
		return quantityOfRenewedAllowed;
	}

	public void setQuantityOfRenewedAllowed(int quantityOfRenewedAllowed) {
		this.quantityOfRenewedAllowed = quantityOfRenewedAllowed;
	}

	public Integer getQuantityOfRenewedDayAllowed() {
		return quantityOfRenewedDayAllowed;
	}

	public void setQuantityOfRenewedDayAllowed(int quantityOfRenewedDayAllowed) {
		this.quantityOfRenewedDayAllowed = quantityOfRenewedDayAllowed;
	}

	public boolean isReservationDelayAllowed() {
		return reservationDelayAllowed;
	}

	public void setReservationDelayAllowed(boolean reservationDelayAllowed) {
		this.reservationDelayAllowed = reservationDelayAllowed;
	}

	public boolean isSeveralMaterialsAllowed() {
		return severalMaterialsAllowed;
	}

	public void setSeveralMaterialsAllowed(boolean severalMaterialsAllowed) {
		this.severalMaterialsAllowed = severalMaterialsAllowed;
	}

	public boolean isReservationAllowed() {
		return reservationAllowed;
	}

	public void setReservationAllowed(boolean reservationAllowed) {
		this.reservationAllowed = reservationAllowed;
	}

	public Integer getQuantityOfDayToPickUpTheMaterial() {
		return quantityOfDayToPickUpTheMaterial;
	}

	public void setQuantityOfDayToPickUpTheMaterial(int quantityOfDayToPickUpTheMaterial) {
		this.quantityOfDayToPickUpTheMaterial = quantityOfDayToPickUpTheMaterial;
	}

	public Double getAmountPenalty() {
		return amountPenalty;
	}

	public void setAmountPenalty(Double amountPenalty) {
		this.amountPenalty = amountPenalty;
	}

	public Double getAmountPenaltyByReserved() {
		return amountPenaltyByReserved;
	}

	public void setAmountPenaltyByReserved(Double amountPenaltyByReserved) {
		this.amountPenaltyByReserved = amountPenaltyByReserved;
	}

	public Double getAmountPenaltyByLost() {
		return amountPenaltyByLost;
	}

	public void setAmountPenaltyByLost(Double amountPenaltyByLost) {
		this.amountPenaltyByLost = amountPenaltyByLost;
	}

	public Integer getQuantitySuspentionDayByDelay() {
		return quantitySuspentionDayByDelay;
	}

	public void setQuantitySuspentionDayByDelay(int quantitySuspentionDayByDelay) {
		this.quantitySuspentionDayByDelay = quantitySuspentionDayByDelay;
	}

	public Integer getQuantitySuspentionDayByDelayOfReservation() {
		return quantitySuspentionDayByDelayOfReservation;
	}

	public void setQuantitySuspentionDayByDelayOfReservation(int quantitySuspentionDayByDelayOfReservation) {
		this.quantitySuspentionDayByDelayOfReservation = quantitySuspentionDayByDelayOfReservation;
	}

	public Nomenclator getLoanUserType() {
		return loanUserType;
	}

	public void setLoanUserType(Nomenclator loanUserType) {
		this.loanUserType = loanUserType;
	}

	public Nomenclator getRecordType() {
		return recordType;
	}

	public void setRecordType(Nomenclator recordType) {
		this.recordType = recordType;
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

	public Nomenclator getCirculationRuleState() {
		return circulationRuleState;
	}

	public void setCirculationRuleState(Nomenclator circulationRuleState) {
		this.circulationRuleState = circulationRuleState;
	}
	
	public boolean equals(CirculationRule circulationRule) {
		return this.getLibrary().equals(circulationRule.getLibrary())
				&& this.getLoanUserType().equals(
						circulationRule.getLoanUserType())
				&& this.getRecordType().equals(
						circulationRule.getRecordType());
	}
	
	@Override
	public Object getRowID() {
		return getCirculationRuleID();
	}

}
