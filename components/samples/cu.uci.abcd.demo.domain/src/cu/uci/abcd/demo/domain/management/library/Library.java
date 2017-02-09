package cu.uci.abcd.demo.domain.management.library;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import cu.uci.abcd.demo.domain.common.TangibleActor;

@Entity
@Table(name = "dlibrary", schema = "abcd")
@PrimaryKeyJoinColumn(name = "library_id")
@DiscriminatorValue(value = "LIBRARY")
public class Library extends TangibleActor {
	@Column(name = "library_name")
	private String libraryName;
	@Column(name = "library_surface")
	private Double librarySurface;
	@Column(name = "room_quantity")
	private Integer roomQuantity;
	@Column(name = "available_reading_seats")
	private Integer availableReadingSeats;
	@Column(name = "available_user_formation_seats")
	private Integer availableUserFormationSeats;
	@Column(name = "available_work_group_seats")
	private Integer availableWorkGroupSeats;
	@Column(name = "open_book_shelves")
	private Double openBookShelves;
	@Column(name = "deposit_book_shelves")
	private Double depositBookShelves;
	@Column(name = "user_pc_quantity")
	private Integer userPcQuantity;
	@Column(name = "worker_pc_quantity")
	private Integer workerPcQuantity;
	@Column(name = "diverse_reader_player_quantity")
	private Integer diverseReaderPlayerQuantity;
	@OneToMany(mappedBy = "ownerLibrary")
	private List<FormationCourse> formationCourseList;

	public String getLibraryName() {
		return libraryName;
	}

	public void setLibraryName(String libraryName) {
		this.libraryName = libraryName;
	}

	public Double getLibrarySurface() {
		return librarySurface;
	}

	public void setLibrarySurface(Double librarySurface) {
		this.librarySurface = librarySurface;
	}

	public Integer getRoomQuantity() {
		return roomQuantity;
	}

	public void setRoomQuantity(Integer roomQuantity) {
		this.roomQuantity = roomQuantity;
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

	public void setAvailableUserFormationSeats(
			Integer availableUserFormationSeats) {
		this.availableUserFormationSeats = availableUserFormationSeats;
	}

	public Integer getAvailableWorkGroupSeats() {
		return availableWorkGroupSeats;
	}

	public void setAvailableWorkGroupSeats(Integer availableWorkGroupSeats) {
		this.availableWorkGroupSeats = availableWorkGroupSeats;
	}

	public Double getOpenBookShelves() {
		return openBookShelves;
	}

	public void setOpenBookShelves(Double openBookShelves) {
		this.openBookShelves = openBookShelves;
	}

	public Double getDepositBookShelves() {
		return depositBookShelves;
	}

	public void setDepositBookShelves(Double depositBookShelves) {
		this.depositBookShelves = depositBookShelves;
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

	public Integer getDiverseReaderPlayerQuantity() {
		return diverseReaderPlayerQuantity;
	}

	public void setDiverseReaderPlayerQuantity(
			Integer diverseReaderPlayerQuantity) {
		this.diverseReaderPlayerQuantity = diverseReaderPlayerQuantity;
	}

	public List<FormationCourse> getFormationCourseList() {
		return formationCourseList;
	}

	public void setFormationCourseList(List<FormationCourse> formationCourseList) {
		this.formationCourseList = formationCourseList;
	}

	public void addFormationCourse(FormationCourse course) {
		this.formationCourseList.add(course);
		if (course.getOwnerLibrary() != this) {
			course.setOwnerLibrary(this);
		}
	}

}
