package cu.uci.abcd.administration.library.ui.controller;

import java.util.Map;

import cu.uci.abcd.administration.library.ICalendarService;
import cu.uci.abcd.administration.library.ICirculationRuleService;
import cu.uci.abcd.administration.library.ICoinService;
import cu.uci.abcd.administration.library.IEnrollmentService;
import cu.uci.abcd.administration.library.IFineEquationService;
import cu.uci.abcd.administration.library.IFormationCourseService;
import cu.uci.abcd.administration.library.ILibraryService;
import cu.uci.abcd.administration.library.IProviderService;
import cu.uci.abcd.administration.library.IRoomService;
import cu.uci.abcd.administration.library.IScheduleService;
import cu.uci.abcd.administration.library.IWorkerService;
import cu.uci.abcd.management.security.IPersonService;
import cu.uci.abos.api.ui.ViewController;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class AllManagementLibraryViewController implements ViewController {

	private ILibraryService libraryService;
	private IRoomService roomService;
	private IFormationCourseService formationCourseService;
	private IPersonService personService;
	private IProviderService providerService;
	private ICoinService coinService;
	private IWorkerService workerService;
	private IFineEquationService fineEquationService;
	private ICalendarService calendarService;
	private IScheduleService scheduleService;
	private ICirculationRuleService circulationRuleService;
	private IEnrollmentService enrollmentService;

	public ILibraryService getLibraryService() {
		return libraryService;
	}

	public void setLibraryService(ILibraryService libraryService) {
		this.libraryService = libraryService;
	}

	public IRoomService getRoomService() {
		return roomService;
	}

	public void setRoomService(IRoomService roomService) {
		this.roomService = roomService;
	}

	public IFormationCourseService getFormationCourseService() {
		return formationCourseService;
	}

	public void setFormationCourseService(
			IFormationCourseService formationCourseService) {
		this.formationCourseService = formationCourseService;
	}

	public IPersonService getPersonService() {
		return personService;
	}

	public void setPersonService(IPersonService personService) {
		this.personService = personService;
	}

	public IProviderService getProviderService() {
		return providerService;
	}

	public void setProviderService(IProviderService providerService) {
		this.providerService = providerService;
	}

	public ICoinService getCoinService() {
		return coinService;
	}

	public void setCoinService(ICoinService coinService) {
		this.coinService = coinService;
	}

	public IWorkerService getWorkerService() {
		return workerService;
	}

	public void setWorkerService(IWorkerService workerService) {
		this.workerService = workerService;
	}

	public IFineEquationService getFineEquationService() {
		return fineEquationService;
	}

	public void setFineEquationService(IFineEquationService fineEquationService) {
		this.fineEquationService = fineEquationService;
	}

	public ICalendarService getCalendarService() {
		return calendarService;
	}

	public void setCalendarService(ICalendarService calendarService) {
		this.calendarService = calendarService;
	}

	public IScheduleService getScheduleService() {
		return scheduleService;
	}

	public void setScheduleService(IScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	public ICirculationRuleService getCirculationRuleService() {
		return circulationRuleService;
	}

	public void setCirculationRuleService(
			ICirculationRuleService circulationRuleService) {
		this.circulationRuleService = circulationRuleService;
	}

	public IEnrollmentService getEnrollmentService() {
		return enrollmentService;
	}

	public void setEnrollmentService(IEnrollmentService enrollmentService) {
		this.enrollmentService = enrollmentService;
	}

	public void bindLibraryService(ILibraryService libraryService,
			Map<?, ?> properties) {
		this.setLibraryService(libraryService);
	}

	public void bindRoomService(IRoomService roomService, Map<?, ?> properties) {
		this.setRoomService(roomService);
	}

	public void bindFormationCourseService(
			IFormationCourseService formationCourseService, Map<?, ?> properties) {
		this.setFormationCourseService(formationCourseService);
	}

	public void bindPersonService(IPersonService personService,
			Map<?, ?> properties) {
		this.setPersonService(personService);
	}

	public void bindProviderService(IProviderService providerService,
			Map<?, ?> properties) {
		this.setProviderService(providerService);
	}

	public void bindCoinService(ICoinService coinService, Map<?, ?> properties) {
		this.setCoinService(coinService);
	}

	public void bindWorkerService(IWorkerService workerService,
			Map<?, ?> properties) {
		this.setWorkerService(workerService);
	}

	public void bindFineEquationService(
			IFineEquationService fineEquationService, Map<?, ?> properties) {
		this.setFineEquationService(fineEquationService);
	}

	public void bindCalendarService(ICalendarService calendarService,
			Map<?, ?> properties) {
		this.setCalendarService(calendarService);
	}

	public void bindScheduleService(IScheduleService scheduleService,
			Map<?, ?> properties) {
		this.setScheduleService(scheduleService);
	}

	public void bindCirculationRuleService(
			ICirculationRuleService circulationRuleService, Map<?, ?> properties) {
		this.setCirculationRuleService(circulationRuleService);
	}

	public void bindEnrollmentService(IEnrollmentService enrollmentService,
			Map<?, ?> properties) {
		this.setEnrollmentService(enrollmentService);
	}
}
