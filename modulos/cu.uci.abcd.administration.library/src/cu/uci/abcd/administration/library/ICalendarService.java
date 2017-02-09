package cu.uci.abcd.administration.library;

import java.util.List;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Calendar;
import cu.uci.abcd.domain.management.library.Library;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public interface ICalendarService {

	public Calendar addCalendar(Calendar calendar);

	public Calendar readCalendar(Long idCalendar);

	public void deleteCalendar(Long idCalendar);

	public Page<Calendar> findAll(Library library, Nomenclator dayType,
			Integer year, int page, int size, int direction, String order);

	public List<Calendar> findCalendarByDaytypeAndLibrary(Nomenclator daytype,
			Long libraryID);

}
