package cu.uci.abcd.dao.management.library;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Calendar;

public interface CalendarDAO extends PagingAndSortingRepository<Calendar, Long>, JpaSpecificationExecutor<Calendar>{

	public List<Calendar> findDistinctCalendarByLibrary_LibraryID(Long libraryID);
	
	public List<Calendar> findDistinctCalendarByDaytypeAndLibrary_LibraryID(Nomenclator daytype, Long libraryID);
}
