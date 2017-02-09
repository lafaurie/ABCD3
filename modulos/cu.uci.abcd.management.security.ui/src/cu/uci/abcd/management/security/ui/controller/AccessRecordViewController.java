package cu.uci.abcd.management.security.ui.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abcd.domain.management.security.AccessRecord;
import cu.uci.abos.api.ui.ViewController;

public class AccessRecordViewController implements ViewController {

	private AllManagementSecurityViewController allManagementSecurityViewController;

	public AllManagementSecurityViewController getAllManagementSecurityViewController() {
		return allManagementSecurityViewController;
	}

	public void setAllManagementSecurityViewController(AllManagementSecurityViewController allManagementSecurityViewController) {
		this.allManagementSecurityViewController = allManagementSecurityViewController;
	}

	public Page<AccessRecord> findAccessByParams(Library library, String firstName, String secondName, String firstSurname, String secondSurname, String identification, Room room, Date fromDate, Date toDate,
			int page, int size, int direction, String order) {
		return allManagementSecurityViewController.getAccessRecordService().findAll(library, firstName, secondName, firstSurname, secondSurname, identification, room, fromDate, toDate, page, size, direction,
				order);
	}

	public AccessRecord addAccessRecord(AccessRecord accessRecord) {
		return allManagementSecurityViewController.getAccessRecordService().addAccessrecord(accessRecord);
	}

	public AccessRecord readAccessRecord(Long idAccessRecord) {
		return allManagementSecurityViewController.getAccessRecordService().readAccessRecord(idAccessRecord);
	}

	public void deleteAccessRecord(Long idAccessRecord) {
		allManagementSecurityViewController.getAccessRecordService().deleteAccessRecord(idAccessRecord);
	}
	
	public List<Room> findRoomByLibrary(Long idLibrary) {
		return allManagementSecurityViewController.getAccessRecordService().findRoomByLibrary(idLibrary);
	}

}
