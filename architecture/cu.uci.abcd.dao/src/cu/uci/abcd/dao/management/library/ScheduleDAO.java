package cu.uci.abcd.dao.management.library;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Schedule;

public interface ScheduleDAO extends PagingAndSortingRepository<Schedule, Long>, JpaSpecificationExecutor<Schedule> {

	public List<Schedule> findDistinctScheduleByLibrary_LibraryIDAndDayOfWeek(Long libraryID, Nomenclator dayOfWeek );
	
	@Query("select n from Schedule n where n.library.libraryID = ?1 order by n.dayOfWeek.nomenclatorID ASC, n.startHour ASC")
	public List<Schedule> findDistinctScheduleByLibrary_LibraryID(Long libraryID);
}