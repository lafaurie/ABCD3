package cu.uci.abcd.administration.library.ui.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Calendar;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.ViewController;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class CalendarViewController implements ViewController {

	private AllManagementLibraryViewController allManagementLibraryViewController;

	public AllManagementLibraryViewController getAllManagementLibraryViewController() {
		return allManagementLibraryViewController;
	}

	public void setAllManagementLibraryViewController(
			AllManagementLibraryViewController allManagementLibraryViewController) {
		this.allManagementLibraryViewController = allManagementLibraryViewController;
	}

	public Calendar saveCalendar(Calendar calendar) {
		return allManagementLibraryViewController.getCalendarService()
				.addCalendar(calendar);
	}

	public Calendar getCalendarById(Long idCalendar) {
		return allManagementLibraryViewController.getCalendarService()
				.readCalendar(idCalendar);
	}

	public void deleteCalendarById(Long idCalendar) {
		allManagementLibraryViewController.getCalendarService().deleteCalendar(
				idCalendar);
	}

	public Page<Calendar> findCalendarByLibraryAndDayType(Library library,
			Nomenclator dayType, boolean thisYear, int page, int size,
			int direction, String order) {
		Integer year = thisYear ? Integer.parseInt(new SimpleDateFormat("yyyy")
				.format(new java.util.Date())) : null;
		return allManagementLibraryViewController.getCalendarService().findAll(
				library, dayType, year, page, size, direction, order);
	}

	public List<Calendar> findCalendarByLibraryAndDayType(Nomenclator daytype,
			Long libraryID) {
		return allManagementLibraryViewController.getCalendarService()
				.findCalendarByDaytypeAndLibrary(daytype, libraryID);
	}

}
