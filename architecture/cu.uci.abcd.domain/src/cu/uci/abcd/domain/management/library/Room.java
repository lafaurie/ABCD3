package cu.uci.abcd.domain.management.library;

import java.io.Serializable;
import java.sql.Timestamp;
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

import cu.uci.abos.core.domain.Row;

@Entity
@Table(name = "droom", schema = "abcdn")
@SequenceGenerator(name = "seq_droom", sequenceName = "sq_droom", schema = "abcdn", allocationSize = 1)
public class Room implements Serializable, Row {
	private static final long serialVersionUID = -802802854021818715L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_droom")
	@Column(name = "id", nullable = false)
	private Long roomID;
	
	@Column(name = "roomname", nullable = false, length = 50)
	private String roomName;
	
	@ManyToOne
	@JoinColumn(name = "library", nullable = false)
	private Library library;
	
	@Column(name = "availablereadingseats")
	private Integer availableReadingSeats;

	@Column(name = "availableuserformationseats")
	private Integer availableUserFormationSeats;

	@Column(name = "availablewokgroupseats")
	private Integer availableWorkGroupSeats;

	@Column(name = "depositbookshelves")
	private Integer depositBookShelves;

	@Column(name = "openbookshelves")
	private Integer openBookShelves;

	@Column(name = "surface")
	private Double surface;

	@Column(name = "readersplayersquantity")
	private Integer diverseReaderPlayerQuantity;

	@Column(name = "userspcquantity")
	private Integer userPcQuantity;

	@Column(name = "workerpcquantity")
	private Integer workerPcQuantity;
	
	@Column(name = "hour", nullable = true)
	private Timestamp hour;

	public Timestamp getHour() {
		return hour;
	}
	
	public String getHourConvert(){		
		return new SimpleDateFormat("hh:mma").format(getHour());		
	}

	public void setHour(Timestamp hour) {
		this.hour = hour;
	}

	public Room() {
		super();
	}

	public Long getRoomID() {
		return roomID;
	}

	public void setRoomID(Long roomID) {
		this.roomID = roomID;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}
	
	public Integer getAvailableReadingSeats() {
		return availableReadingSeats;
	}

	public void setAvailableReadingSeats(Integer availableReadingSeats) {
		this.availableReadingSeats = availableReadingSeats;
	}

	public Integer getAvailableUserFormationSeats() {
		return availableUserFormationSeats;
	}

	public void setAvailableUserFormationSeats(Integer availableUserFormationSeats) {
		this.availableUserFormationSeats = availableUserFormationSeats;
	}

	public Integer getAvailableWorkGroupSeats() {
		return availableWorkGroupSeats;
	}

	public void setAvailableWorkGroupSeats(Integer availableWorkGroupSeats) {
		this.availableWorkGroupSeats = availableWorkGroupSeats;
	}

	public Integer getDepositBookShelves() {
		return depositBookShelves;
	}

	public void setDepositBookShelves(Integer depositBookShelves) {
		this.depositBookShelves = depositBookShelves;
	}

	public Integer getOpenBookShelves() {
		return openBookShelves;
	}

	public void setOpenBookShelves(Integer openBookShelves) {
		this.openBookShelves = openBookShelves;
	}

	public Double getSurface() {
		return surface;
	}
	
	

	public void setSurface(Double surface) {
		this.surface = surface;
	}

	public Integer getDiverseReaderPlayerQuantity() {
		return diverseReaderPlayerQuantity;
	}

	public void setDiverseReaderPlayerQuantity(Integer diverseReaderPlayerQuantity) {
		this.diverseReaderPlayerQuantity = diverseReaderPlayerQuantity;
	}

	public Integer getUserPcQuantity() {
		return userPcQuantity;
	}

	public void setUserPcQuantity(Integer userPcQuantity) {
		this.userPcQuantity = userPcQuantity;
	}

	public Integer getWorkerPcQuantity() {
		return workerPcQuantity;
	}

	public void setWorkerPcQuantity(Integer workerPcQuantity) {
		this.workerPcQuantity = workerPcQuantity;
	}

	public String getSurfaceToString() {
		return (getSurface()==null?"-":getSurface().toString());
	}
	
	public String getAvailableReadingSeatsToString() {
		return (getAvailableReadingSeats()==null?"-":getAvailableReadingSeats().toString());
	}
	
	public String getUserPcQuantityToString() {
		return (getUserPcQuantity()==null?"-":getUserPcQuantity().toString());
	}
	
	
	@Override
	public Object getRowID() {
		return getRoomID();
	}
	
	@Override
	public String toString() {
		return getRoomName();
	}
	
	}

