package cu.uci.abcd.domain.opac;

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

import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.core.domain.Row;

@Entity
@Table(name = "dopaclibrarydatasources", schema = "abcdn")
@SequenceGenerator(name = "seq_dopaclibrarydatasource", sequenceName = "sq_dopaclibrarydatasource", schema = "abcdn", allocationSize = 1)
public class OpacDataSources implements Serializable, Row {
	private static final long serialVersionUID = -5012730440430938231L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dopaclibrarydatasource")
	@Column(name = "id", nullable = false)
	private Long opacDataSourcesID;
	
	@ManyToOne
	@JoinColumn(name = "library", nullable = false)
	private Library library;

	@Column(name = "databasename", length = 500)
	private String databaseName;

	@Override
	public Object getRowID() {
		
		return null;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public Long getOpacDataSourcesID() {
		return opacDataSourcesID;
	}

	public void setOpacDataSourcesID(Long opacDataSourcesID) {
		this.opacDataSourcesID = opacDataSourcesID;
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

}
