package cu.uci.abcd.administration.library;

import java.util.List;

import org.springframework.data.domain.Page;
import org.unesco.jisis.corelib.common.WorksheetDef;

import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public interface ILibraryService {

	public Library addLibrary(Library library);

	public Library readLibrary(Long idLibrary);

	public void deleteLibrary(Long idLibrary);

	public Page<Library> findAll(String libraryName, int page, int size,
			int direction, String order);

	public List<Nomenclator> findNomenclatorByCode(Long idLibrary, Long idParent);

	public List<Nomenclator> findNomenclatorIdOrderByCode(Long idLibrary,
			Long idParent);

	public Nomenclator findNomenclatorById(Long idNomenclator);

	public Library findLibraryByName(String libraryName);

	public Library findLibraryByHome(String isisHome);

	public List<String> getDatabaseNames(String libraryIsisDatabasesHomeFolder)
			throws JisisDatabaseException;

	public List<String> getWorksheetNames(String databaseName,
			String libraryIsisDatabasesHomeFolder)
			throws JisisDatabaseException;

	public WorksheetDef getWorksheet(String worksheetName, String databaseName,
			String libraryIsisDatabasesHomeFolder)
			throws JisisDatabaseException;

	public boolean updateWorksheet(WorksheetDef worksheetDef)
			throws JisisDatabaseException;

}
