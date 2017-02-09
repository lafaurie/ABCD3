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
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import cu.uci.abos.core.domain.Row;

@Entity
@Table(name = "dindicator", schema = "abcdn")
@SequenceGenerator(name = "seq_dindicator", sequenceName = "sq_dindicator", schema = "abcdn", allocationSize = 1)
public class Indicator implements Serializable, Row {
	private static final long serialVersionUID = -4446061525696207184L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dindicator")
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "indicatornum")
	private String indicatorId;
	
	@Column(name = "indicatorname")
	private String nameIndicator;
	
	@Column(name = "querytext")
	private String queryText;

	@Transient
	private Object value;

	@ManyToMany(mappedBy = "indicators", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Report> reports;

	public Indicator() {
		super();
		reports = new ArrayList<>();

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIndicatorId() {
		return indicatorId;
	}

	public void setIndicatorId(String indicatorId) {
		this.indicatorId = indicatorId;
	}

	public String getNameIndicator() {
		return nameIndicator;
	}

	public void setNameIndicator(String nameIndicator) {
		this.nameIndicator = nameIndicator;
	}

	public String getQueryText() {
		if (queryText==null){
			queryText="";
		}
			
		return queryText;
	}

	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}

	public List<Report> getReports() {
		return reports;
	}

	public void setReports(List<Report> reports) {
		this.reports = reports;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		if (value != null) {
			if (value instanceof List<?>) {
				List<?> values = (List<?>) value;
				StringBuilder sb = new StringBuilder();
				for (Object element : values) {
					sb.append(element);
					sb.append(", ");
				}
				return sb.substring(0, sb.length() - 2);
			} else {
				return value.toString();
			}
		}
		return "";//super.toString();
	}

	@Override
	public Object getRowID() {
		return getId();
	}

}
