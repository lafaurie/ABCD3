package cu.uci.abcd.domain.management.library;

import java.io.Serializable;
import java.sql.Date;

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
import cu.uci.abcd.domain.util.Auxiliary;
import cu.uci.abos.core.domain.Row;

@Entity
@Table(name = "dcoin", schema = "abcdn")
@SequenceGenerator(name = "seq_dcoin", sequenceName = "sq_dcoin", schema = "abcdn", allocationSize = 1)
public class Coin implements Serializable, Row {
	private static final long serialVersionUID = -3253083344030403693L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dcoin")
	@Column(name = "id", nullable = false)
	private Long coinID;

	@Column(name = "coinname", nullable = true, length = 30)
	private String coinName;

	@Column(name = "exchangerate", nullable = false)
	private String exchangeRate;

	@Column(name = "lastupdate", nullable = false)
	private Date updatedDate;

	@ManyToOne
	@JoinColumn(name = "cointype", nullable = false)
	private Nomenclator coinType;
	
	@ManyToOne
	@JoinColumn(name = "library", nullable = true)
	private Library library;

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

	public Long getCoinID() {
		return coinID;
	}

	public void setCoinID(Long coinID) {
		this.coinID = coinID;
	}

	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

	public String getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Nomenclator getCoinType() {
		return coinType;
	}

	public void setCoinType(Nomenclator coinType) {
		this.coinType = coinType;
	}
	
	public String getUpdatedDateToString() {
		return Auxiliary.FormatDate(getUpdatedDate());
	}
	
	@Override
	public Object getRowID() {
		return getCoinID();
	}
	
	@Override
	public String toString() {
		return getCoinName() + "(" + getCoinType().getNomenclatorName()+ ")";
	}

}
