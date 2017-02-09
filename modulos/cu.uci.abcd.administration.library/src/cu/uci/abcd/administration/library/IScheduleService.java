package cu.uci.abcd.administration.library;

import java.util.List;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Schedule;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public interface IScheduleService {

	public Schedule addSchedule(Schedule schedule);

	public Schedule readSchedule(Long idSchedule);

	public void deleteSchedule(Long idSchedule);

	public List<Schedule> findAll(long idLibrary, Nomenclator dayOfWeek);

	public List<Schedule> findAll(long idLibrary);

	public Page<Schedule> findAll(Library library, int page, int size,
			int direction, String order);

}
