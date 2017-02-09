package cu.uci.abcd.domain.statistic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cu.uci.abos.core.domain.Row;

@Entity
@Table(name = "dreport", schema = "abcdn")
@SequenceGenerator(name = "seq_dreport", sequenceName = "sq_dreport", schema = "abcdn", allocationSize = 1)
public class Report implements Serializable, Row {
	private static final long serialVersionUID = 4383152854517470202L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dreport")
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "reportname")
	private String nameReport;

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinTable(name = "dreportindicator", schema = "abcdn", joinColumns = { @JoinColumn(name = "report", referencedColumnName = "id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "indicator", referencedColumnName = "id", nullable = false) })
	private List<Indicator> indicators;

	public Report() {
		super();
		indicators = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNameReport() {
		return nameReport;
	}

	public void setNameReport(String nameReport) {
		this.nameReport = nameReport;
	}

	public List<Indicator> getIndicators() {
		return indicators;
	}

	public void setIndicators(List<Indicator> indicators) {
		this.indicators = indicators;
	}

	@Override
	public Object getRowID() {
		return getId();
	}
	
	@Override
	public String toString() {
		return  nameReport;
	}

	

}