package cu.uci.abcd.administration.library.impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import cu.uci.abcd.administration.library.ICalendarService;
import cu.uci.abcd.dao.management.library.CalendarDAO;
import cu.uci.abcd.dao.specification.LibrarySpecification;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Calendar;
import cu.uci.abcd.domain.management.library.Library;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class CalendarServiceImpl implements ICalendarService {
	private CalendarDAO calendarDAO;

	public void bind(CalendarDAO calendarDAO, Map<?, ?> properties) {
		this.calendarDAO = calendarDAO;
	}

	@Override
	public Calendar addCalendar(Calendar calendar) {
		return calendarDAO.save(calendar);
	}

	@Override
	public Calendar readCalendar(Long idCalendar) {
		return calendarDAO.findOne(idCalendar);
	}

	@Override
	public void deleteCalendar(Long idCalendar) {
		calendarDAO.delete(idCalendar);

	}

	@Override
	public Page<Calendar> findAll(Library library, Nomenclator dayType,
			Integer year, int page, int size, int direction, String order) {
		return calendarDAO.findAll(
				LibrarySpecification.searchCalendar(library, dayType, year),
				PageSpecification.getPage(page, size, direction, order));
	}

	@Override
	public List<Calendar> findCalendarByDaytypeAndLibrary(Nomenclator daytype,
			Long libraryID) {
		return calendarDAO.findDistinctCalendarByDaytypeAndLibrary_LibraryID(
				daytype, libraryID);
	}

}
