package cu.uci.abcd.administration.library.impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import cu.uci.abcd.administration.library.IScheduleService;
import cu.uci.abcd.dao.management.library.ScheduleDAO;
import cu.uci.abcd.dao.specification.LibrarySpecification;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Schedule;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class ScheduleServiceImpl implements IScheduleService {

	private ScheduleDAO scheduleDAO;

	public void bind(ScheduleDAO scheduleDAO, Map<?, ?> properties) {
		this.scheduleDAO = scheduleDAO;
	}

	@Override
	public Schedule addSchedule(Schedule schedule) {
		return scheduleDAO.save(schedule);
	}

	@Override
	public Schedule readSchedule(Long idSchedule) {
		return scheduleDAO.findOne(idSchedule);
	}

	@Override
	public void deleteSchedule(Long idSchedule) {
		scheduleDAO.delete(idSchedule);

	}

	@Override
	public Page<Schedule> findAll(Library library, int page, int size,
			int direction, String order) {
		return scheduleDAO.findAll(
				LibrarySpecification.searchSchedule(library),
				PageSpecification.getPageInOrder(page, size, direction, order));
	}

	@Override
	public List<Schedule> findAll(long idLibrary, Nomenclator dayOfWeek) {
		return scheduleDAO.findDistinctScheduleByLibrary_LibraryIDAndDayOfWeek(
				idLibrary, dayOfWeek);
	}

	@Override
	public List<Schedule> findAll(long idLibrary) {
		return scheduleDAO.findDistinctScheduleByLibrary_LibraryID(idLibrary);
	}

}
