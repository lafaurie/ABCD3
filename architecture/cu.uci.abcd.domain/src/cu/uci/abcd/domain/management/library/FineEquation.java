package cu.uci.abcd.domain.management.library;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cu.uci.abos.core.domain.Row;

@Entity
@Table(name = "dfineequation", schema = "abcdn")
@SequenceGenerator(name = "seq_dfineequation", sequenceName = "sq_dfineequation", allocationSize = 1, schema = "abcdn")
public class FineEquation implements Serializable, Row {
	private static final long serialVersionUID = -3890238169920985630L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dfineequation")
	@Column(name = "id", nullable = false)
	private Long fineEquationID;

	@Column(name = "delayamount", nullable = false)
	private String delayAmount;

	@Column(name = "lostamount", nullable = false)
	private String lostAmount;

	@OneToOne
	@JoinColumn(name = "library", nullable = false)
	private Library library;

	public Long getFineEquationID() {
		return fineEquationID;
	}

	public void setFineEquationID(Long fineEquationID) {
		this.fineEquationID = fineEquationID;
	}

	public Double getDelayAmount() {
		return Double.parseDouble(getDelayAmountToString());
	}
	
	public String getDelayAmountToString() {
		return delayAmount;
	}

	public void setDelayAmount(String delayAmount) {
		this.delayAmount = delayAmount;
	}

	public Double getLostAmount() {
		return Double.parseDouble(getLostAmountToString());
	}
	
	public String getLostAmountToString() {
		return lostAmount;
	}

	public void setLostAmount(String lostAmount) {
		this.lostAmount = lostAmount;
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

	@Override
	public Object getRowID() {
		return getFineEquationID();
	}

}
