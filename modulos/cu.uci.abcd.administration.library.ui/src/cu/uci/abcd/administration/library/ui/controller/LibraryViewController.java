package cu.uci.abcd.administration.library.ui.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.unesco.jisis.corelib.common.WorksheetDef;

import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.management.library.FormationCourse;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abcd.domain.management.library.Worker;
import cu.uci.abos.api.ui.ViewController;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class LibraryViewController implements ViewController {

	private AllManagementLibraryViewController allManagementLibraryViewController;

	public AllManagementLibraryViewController getAllManagementLibraryViewController() {
		return allManagementLibraryViewController;
	}

	public void setAllManagementLibraryViewController(AllManagementLibraryViewController allManagementLibraryViewController) {
		this.allManagementLibraryViewController = allManagementLibraryViewController;
	}

	/* LIBRARY */
	public Library getLibraryById(Long idLibrary) {
		return allManagementLibraryViewController.getLibraryService().readLibrary(idLibrary);
	}

	public Library saveLibrary(Library library) {
		return allManagementLibraryViewController.getLibraryService().addLibrary(library);
	}

	public void deleteLibraryById(Long idLibrary) {
		allManagementLibraryViewController.getLibraryService().deleteLibrary(idLibrary);
	}

	public Page<Library> findByLibraryByParams(String libraryName, int page, int size, int direction, String order) {
		return allManagementLibraryViewController.getLibraryService().findAll(libraryName, page, size, direction, order);
	}
	
	public Library findLibraryByName(String libraryName) {
		return allManagementLibraryViewController.getLibraryService().findLibraryByName(libraryName);
	}
	
	public Library findLibraryByHome(String isisHome) {
		return allManagementLibraryViewController.getLibraryService().findLibraryByHome(isisHome);
	}
	
	
	
	//FIXME BORRAR CODIGO COMENTARIADO
	/* FORMATION COURSE */
	
	public FormationCourse getFormationCourseById(Long idFormationCourse) {
		return allManagementLibraryViewController.getFormationCourseService().readFormationCourse(idFormationCourse);
	}
	
	public FormationCourse getFormationCourseByName(Long idLibrary, String courseName) {
		return allManagementLibraryViewController.getFormationCourseService().findFormationCourseByName(idLibrary, courseName);
	}

	public FormationCourse saveFormationCourse(FormationCourse formationCourse) {
		return allManagementLibraryViewController.getFormationCourseService().addFormationCourse(formationCourse);
	}

	public void deleteFormationCourseById(Long idFormationCourse) {
		allManagementLibraryViewController.getFormationCourseService().deleteFormationCourse(idFormationCourse);
	}

	public Page<FormationCourse> findFormationCourseByParams(Library library, String courseName, int clasificationConsult, Room room, int addressedTo, Person proffessor, Date fromDate, Date toDate, int page, int size, int direction, String order) {
		return allManagementLibraryViewController.getFormationCourseService().findAll(library, courseName, clasificationConsult, room, addressedTo, proffessor, fromDate, toDate, page, size, direction, order);
	}
	
	//FIXME BORRAR CODIGO COMENTARIADO
	/* ROOM */
	public Room saveRoom(Room room) {

		Room roomSaved = null;
		Library library = room.getLibrary();
		try {
			// jisis sirve
			getAllManagementLibraryViewController().getLibraryService()
					.getDatabaseNames(library.getIsisDefHome());
		} catch (Exception e1) {
			return null;
		}
		try {
			// postgres sirve
			roomSaved = allManagementLibraryViewController
					.getRoomService().addRoom(room);
		} catch (Exception e2) {
			return null;
		}
		// hacer todo
		List<Room> allRooms = findRoomByLibrary(library
				.getLibraryID());
		saveRoomJisis(allRooms, library);

		return roomSaved;
	
		//return allManagementLibraryViewController.getRoomService().addRoom(room);
	}

	public int deleteRoom(Room room) {
		Library library = room.getLibrary();
		try {
			// jisis sirve
			getAllManagementLibraryViewController().getLibraryService()
					.getDatabaseNames(library.getIsisDefHome());
		} catch (Exception e1) {
			return 2;
		}
		try {
			allManagementLibraryViewController.getRoomService()
					.deleteRoom(room.getRoomID());
		} catch (Exception x) {
			return 1;
		}
		
		List<Room> allRooms = findRoomByLibrary(library
				.getLibraryID());
		saveRoomJisis(allRooms, library);
		return 3;
	}
	
	public void saveRoomJisis(List<Room> allRooms, Library library){
		
		String pickList = allRooms.toString();
		pickList = pickList.replace("[", "");
		pickList = pickList.replace("]", "");
		pickList = pickList.replace(",", ";");
		String databaseName = "Registro_De_Adquisicion";
		String isisDefHome = library.getIsisDefHome();
		String workSheetNameDonation = "Donación";
		String workSheetNameCompra = "Compra";
		String workSheetNameDefault = "Hoja de trabajo por defecto";
		String workSheetNameCanje = "Canje";
		try {
			
			WorksheetDef workSheetDonation = getAllManagementLibraryViewController()
					.getLibraryService().getWorksheet(
							workSheetNameDonation,
							databaseName, isisDefHome);
			workSheetDonation.getFieldByTag(7).getSubFieldByIndex(0).setPickList(pickList);
			getAllManagementLibraryViewController()
			.getLibraryService().updateWorksheet(workSheetDonation);

			
			WorksheetDef workSheetCompra = getAllManagementLibraryViewController()
					.getLibraryService()
					.getWorksheet(workSheetNameCompra,
							databaseName, isisDefHome);
			workSheetCompra.getFieldByTag(7).getSubFieldByIndex(0).setPickList(pickList);
			getAllManagementLibraryViewController()
			.getLibraryService()
					.updateWorksheet(workSheetCompra);

			
			WorksheetDef workSheetDefault = getAllManagementLibraryViewController()
					.getLibraryService()
					.getWorksheet(workSheetNameDefault,
							databaseName, isisDefHome);
			workSheetDefault.getFieldByTag(7).getSubFieldByIndex(0).setPickList(pickList);
			getAllManagementLibraryViewController()
			.getLibraryService().updateWorksheet(workSheetDefault);

			
			WorksheetDef workSheetCanje = getAllManagementLibraryViewController()
					.getLibraryService()
					.getWorksheet(workSheetNameCanje,
							databaseName, isisDefHome);
			workSheetCanje.getFieldByTag(7).getSubFieldByIndex(0).setPickList(pickList);
			getAllManagementLibraryViewController()
			.getLibraryService()
					.updateWorksheet(workSheetCanje);
			
		} catch (JisisDatabaseException e1) {
			//RetroalimentationUtils
			//		.showErrorMessage("JISIS is down");
		}
		
	}
	
	public Room readRoom(Long idRoom) {
		return allManagementLibraryViewController.getRoomService().readRoom(idRoom);
	}

	public Page<Room> findRoomByParams(Library library, String roomName, int page, int size, int direction, String order) {
		return allManagementLibraryViewController.getRoomService().findAll(library, roomName, page, size, direction, order);
	}
	
	public List<Room> findRoomByLibrary(Long idLibrary) {
		return allManagementLibraryViewController.getRoomService().findAll(
				idLibrary);
	}


	/* PERSON */
	public Person getPersonById(Long idPerson) {
		return allManagementLibraryViewController.getPersonService().readPerson(idPerson);
	}

	public Page<Person> findPersonByParams(Library library, String param, String dni, int page, int size, int direction, String order) {
		return allManagementLibraryViewController.getPersonService().findAll(library, param, page, size, direction, order);
	}
	//FIXME BORRAR CODIGO COMENTARIADO
	/* WORKER */
	public Worker saveWorker(Worker worker) {
		return allManagementLibraryViewController.getWorkerService().addWorker(worker);
	}

	public Page<Worker> findWorkerByParams(Library library, String firstNameConsult, String secondNameConsult, String firstSurnameConsult, String secondSurnameConsult, String dni,
			Nomenclator workerType, Nomenclator gender, Date fromDate, Date toDate, int page, int size, int direction, String order) {
		return allManagementLibraryViewController.getWorkerService().findAll(library, firstNameConsult, secondNameConsult, firstSurnameConsult, secondSurnameConsult, dni, workerType, gender,
				fromDate, toDate, page, size, direction, order);
	}

	public void deleteWorkerById(Long idWorker) {
		allManagementLibraryViewController.getWorkerService().deleteWorker(idWorker);
	}

	public Worker getWorkerById(Long idWorker) {
		return allManagementLibraryViewController.getWorkerService().readWorker(idWorker);
	}
	//FIXME BORRAR CODIGO COMENTARIADO
	/* NOMENCLATOR */
	public List<Nomenclator> findNomenclatorByCode(Long idLibrary, Long code) {
		return allManagementLibraryViewController.getLibraryService().findNomenclatorByCode(idLibrary, code);
	}

	public Nomenclator findNomenclatorById(Long indNomenclator) {
		return allManagementLibraryViewController.getLibraryService().findNomenclatorById(indNomenclator);
	}
	
	/**
	 jisis
	*/
	
	public List<String> getDatabaseNames(String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException{
		return allManagementLibraryViewController.getLibraryService()
					.getDatabaseNames(libraryIsisDatabasesHomeFolder);
	}

	public List<String> getWorksheetNames(String databaseName,
			String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException{
			return allManagementLibraryViewController
						.getLibraryService()
						.getWorksheetNames(databaseName, libraryIsisDatabasesHomeFolder);
	}
	
	
	public WorksheetDef getWorksheet(String worksheetName, String databaseName,
			String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException{
			return allManagementLibraryViewController
						.getLibraryService().getWorksheet(worksheetName, databaseName,
					libraryIsisDatabasesHomeFolder);
	}
	
	
	public boolean updateWorksheet(WorksheetDef worksheet) throws JisisDatabaseException{
			return allManagementLibraryViewController.getLibraryService().updateWorksheet(worksheet);
	}

}
