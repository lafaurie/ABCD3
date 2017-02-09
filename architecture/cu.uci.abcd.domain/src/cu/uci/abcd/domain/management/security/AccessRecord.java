package cu.uci.abcd.domain.management.security;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abcd.domain.util.Auxiliary;
import cu.uci.abos.core.domain.Row;

@Entity
@Table(name = "daccessrecord", schema = "abcdn")
@SequenceGenerator(name = "seq_daccessrecord", sequenceName = "sq_daccessrecord", allocationSize = 1, schema = "abcdn")
public class AccessRecord implements Serializable, Row {
	private static final long serialVersionUID = 2923804822200973552L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_daccessrecord")
	@Column(name = "id", nullable = false)
	private Long accessRecordnID;

	@Column(name = "motivation", nullable = true)
	private String motivation;

	@Column(name = "accessdate", nullable = false)
	private Timestamp accessDate;

	@ManyToOne
	@JoinColumn(name = "person", nullable = false)
	private Person person;

	@ManyToOne
	@JoinColumn(name = "authenticateduser", nullable = false)
	private User authenticatedUser;

	@ManyToOne
	@JoinColumn(name = "room", nullable = false)
	private Room room;
	
	@ManyToOne
	@JoinColumn(name = "library", nullable = false)
	private Library library;

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

	public Long getAccessRecordnID() {
		return accessRecordnID;
	}

	public void setAccessRecordnID(Long accessRecordnID) {
		this.accessRecordnID = accessRecordnID;
	}

	public String getMotivation() {
		return motivation;
	}

	public void setMotivation(String motivation) {
		this.motivation = motivation;
	}

	public Timestamp getAccessDate() {
		return accessDate;
	}

	public void setAccessDate(Timestamp accessDate) {
		this.accessDate = accessDate;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public User getAuthenticatedUser() {
		return authenticatedUser;
	}

	public void setAuthenticatedUser(User authenticatedUser) {
		this.authenticatedUser = authenticatedUser;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getAccessDateHourToString() {
		Date date = new Date(getAccessDate().getTime());
		return Auxiliary.FormatDate(date);
	} 
	
	public String getAccessDateMinutesToString() {
		DateFormat df = new SimpleDateFormat("hh:mm a");
		return df.format(getAccessDate());
	} 
	
	@Override
	public Object getRowID() {
		return getAccessRecordnID();
	}

}
