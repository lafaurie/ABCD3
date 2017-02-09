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
@Table(name = "dvariablestatistic", schema = "abcdn")
@SequenceGenerator(name = "seq_dvariablestatistic", sequenceName = "sq_dvariablestatistic", schema = "abcdn", allocationSize = 1)
public class Variable implements Serializable, Row {
	private static final long serialVersionUID = 8310662229265183237L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dvariablestatistic")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "field")
	private String field;
	
	@Column(name = "variableheader")
	private String header;
	
	@Column(name = "output_format")
	private String outputFormat;
	
	
	@Column(name="databasename")
	private String databaseName;

	public Variable() {
		super();
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getOutputFormat() {
		return outputFormat;
	}

	public void setOutputFormat(String outputFormat) {
		this.outputFormat = outputFormat;
	}

	@Override
	public Object getRowID() {
		return getId();
	}
}
