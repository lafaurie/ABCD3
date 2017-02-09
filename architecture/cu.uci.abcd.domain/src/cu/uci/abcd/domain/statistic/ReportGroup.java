package cu.uci.abcd.domain.statistic;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cu.uci.abos.core.domain.Row;

@Entity
@Table(name="dreportgroup", schema="abcdn")
@SequenceGenerator(name = "seq_dreportgroup", sequenceName = "sq_dreportgroup", schema = "abcdn", allocationSize = 1)
public class ReportGroup implements Serializable, Row{
	private static final long serialVersionUID=1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dreportgroup")
	@Column(name="id")
	private Long id;
	
	@Column(name="indicator")
	private String indicator;
	
	@Column(name="position")
	private String position;
	
	@Column(name="indicatorlist")
	private String indicatorList;
	
	public ReportGroup() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getIndicatorList() {
		return indicatorList;
	}

	public void setIndicatorList(String indicatorList) {
		this.indicatorList = indicatorList;
	}

	@Override
	public Object getRowID() {
		return getId();
	}
	
}
