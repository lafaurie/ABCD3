package cu.uci.abcd.domain.common;

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
@SequenceGenerator(name = "seq_dstatistics", sequenceName = "sq_dstatistics", schema = "abcdn", allocationSize = 1)
@Table(name = "dstatistic", schema = "abcdn")
public class Statistic implements Serializable, Row {

	private static final long serialVersionUID = 895617932758906346L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dstatistics")
	@Column(name = "id")
	protected Long tableID;
	
	@Column(name = "databasetype")
	private String databaseType;
	
	@Column(name = "tablename")
	private String tableName;
	
	@Column(name = "headcolumn")
	private String headColumn;
	
	@Column(name = "headrow")
	private String headRow;
	
	@Column(name = "valuecolumn")
	private String valueColumn;
	
	@Column(name = "valuerow")
	private String valueRow;
	
	@Column(name = "description")
	private String description;

	public Statistic() {
		super();
	}

	public Long getTableID() {
		return tableID;
	}

	public void setTableID(Long tableID) {
		this.tableID = tableID;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDatabaseType() {
		return databaseType;
	}

	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}

	public String getHeadColumn() {
		return headColumn;
	}

	public void setHeadColumn(String headColumn) {
		this.headColumn = headColumn;
	}

	public String getValueColumn() {
		return valueColumn;
	}

	public void setValueColumn(String valueColumn) {
		this.valueColumn = valueColumn;
	}

	public String getHeadRow() {
		return headRow;
	}

	public void setHeadRow(String headRow) {
		this.headRow = headRow;
	}

	public String getValueRow() {
		return valueRow;
	}

	public void setValueRow(String valueRow) {
		this.valueRow = valueRow;
	}

	@Override
	public Object getRowID() {
		return getTableID();
	}

}
