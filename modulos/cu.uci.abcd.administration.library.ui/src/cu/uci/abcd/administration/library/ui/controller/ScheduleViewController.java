package cu.uci.abcd.administration.library.ui.controller;

import java.util.List;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Schedule;
import cu.uci.abos.api.ui.ViewController;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class ScheduleViewController implements ViewController {

	private AllManagementLibraryViewController allManagementLibraryViewController;

	public AllManagementLibraryViewController getAllManagementLibraryViewController() {
		return allManagementLibraryViewController;
	}

	public void setAllManagementLibraryViewController(AllManagementLibraryViewController allManagementLibraryViewController) {
		this.allManagementLibraryViewController = allManagementLibraryViewController;
	}

	public Schedule saveSchedule(Schedule schedule) {
		return allManagementLibraryViewController.getScheduleService().addSchedule(schedule);
	}

	public Schedule getScheduleById(Long idSchedule) {
		return allManagementLibraryViewController.getScheduleService().readSchedule(idSchedule);
	}

	public void deleteScheduleById(Long idSchedule) {
		allManagementLibraryViewController.getScheduleService().deleteSchedule(idSchedule);
	}

	public Page<Schedule> findScheduleByLibrary(Library library, int page, int size, int direction, String order) {
		return allManagementLibraryViewController.getScheduleService().findAll(library, page, size, direction, order);
	}

	public List<Schedule> findAll(long idLibrary, Nomenclator dayOfWeek) {
		return allManagementLibraryViewController.getScheduleService().findAll(idLibrary, dayOfWeek);
	}
	
	public List<Schedule> findAll(long idLibrary) {
		return allManagementLibraryViewController.getScheduleService().findAll(idLibrary);
	}

}
