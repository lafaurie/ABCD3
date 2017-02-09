package cu.uci.abcd.domain.acquisition;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
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
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.core.domain.Row;

@Entity
@Table(name = "dsuggestion", schema = "abcdn")
@SequenceGenerator(name = "seq_dsuggestion", sequenceName = "sq_dsuggestion", allocationSize = 1, schema = "abcdn")
public class Suggestion implements Serializable, Row {
	private static final long serialVersionUID = -402017973097933506L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dsuggestion")
	@Column(name = "id", nullable = false)
	private Long suggestionID;

	@ManyToOne
	@JoinColumn(name = "duser", nullable = false)
	private User user;

	@Column(name = "author", length = 500)
	private String author;

	@Column(name = "title", nullable = false, length = 500)
	private String title;

	@Column(name = "editorial", length = 100)
	private String editorial;

	@Column(name = "publicationdate")
	private Date publicationDate;

	@Column(name = "registerdate")
	private Date registerDate;

	@Column(name = "note", nullable = false)
	private String note;

	@ManyToOne
	@JoinColumn(name = "state", nullable = false)
	private Nomenclator state;

	@ManyToOne
	@JoinColumn(name = "acceptancemotive", nullable = false)
	private Nomenclator acceptanceMotive;

	@ManyToOne
	@JoinColumn(name = "rejectionmotive", nullable = false)
	private Nomenclator rejectionMotive;

	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
	@JoinColumn(name = "desiderata")
	private Desiderata associateDesiderata;
	
	@ManyToOne
	@JoinColumn(name = "library", nullable = false)
	private Library library;
	
	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}
	public Long getSuggestionID() {
		return suggestionID;
	}

	public void setSuggestionID(Long suggestionID) {
		this.suggestionID = suggestionID;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Nomenclator getState() {
		return state;
	}

	public void setState(Nomenclator state) {
		this.state = state;
	}

	public Nomenclator getAcceptanceMotive() {
		return acceptanceMotive;
	}

	public void setAcceptanceMotive(Nomenclator acceptanceMotive) {
		this.acceptanceMotive = acceptanceMotive;
	}

	public Nomenclator getRejectMotive() {
		return rejectionMotive;
	}

	public void setRejectMotive(Nomenclator rejectionMotive) {
		this.rejectionMotive = rejectionMotive;
	}

	public Desiderata getAssociateDesiderata() {
		return associateDesiderata;
	}

	public void setAssociateDesiderata(Desiderata associateDesiderata) {
		this.associateDesiderata = associateDesiderata;
		if (!associateDesiderata.getSuggestions().contains(this)) {
			associateDesiderata.getSuggestions().add(this);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + ((suggestionID == null) ? 0 : suggestionID.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Suggestion other = (Suggestion) obj;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (suggestionID == null) {
			if (other.suggestionID != null)
				return false;
		} else if (!suggestionID.equals(other.suggestionID))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public Object getRowID() {
		return getSuggestionID();
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

}
