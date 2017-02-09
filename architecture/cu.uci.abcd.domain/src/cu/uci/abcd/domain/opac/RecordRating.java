package cu.uci.abcd.domain.opac;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "drecordrating", schema = "abcdn")
@SequenceGenerator(name = "seq_drecordrating", sequenceName = "sq_drecordrating", allocationSize = 1, schema = "abcdn")
public class RecordRating implements Serializable, Row {
	private static final long serialVersionUID = -5467379352766715147L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_drecordrating")
	@Column(name = "id", nullable = false)
	private Long userID;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "library", nullable = false)
	private Library libraryOwner;

	@Column(name = "isisdatabasename", nullable = false)
	private String isisdatabasename;

	@Column(name = "mfn", nullable = false)
	private String mfn;

	@Column(name = "rating", nullable = false)
	private float rating;

	public Library getLibraryOwner() {
		return libraryOwner;
	}

	public void setLibraryOwner(Library libraryOwner) {
		this.libraryOwner = libraryOwner;
	}

	public String getIsisdatabasename() {
		return isisdatabasename;
	}

	public void setIsisdatabasename(String isisdatabasename) {
		this.isisdatabasename = isisdatabasename;
	}

	public String getMfn() {
		return mfn;
	}

	public void setMfn(String mfn) {
		this.mfn = mfn;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public Long getUserID() {
		return userID;
	}	

	@Override
	public Object getRowID() {
		return getUserID();
	}

}
