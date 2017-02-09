package cu.uci.abcd.administration.library.impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.unesco.jisis.corelib.common.WorksheetDef;

import cu.uci.abcd.administration.library.ILibraryService;
import cu.uci.abcd.dao.common.NomenclatorDAO;
import cu.uci.abcd.dao.management.library.LibraryDAO;
import cu.uci.abcd.dao.specification.LibrarySpecification;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class LibraryServiceImpl implements ILibraryService {

	private LibraryDAO libraryDAO;
	private NomenclatorDAO nomenclatorDAO;
	private IJisisDataProvider providerJISIS;

	public void bind(LibraryDAO libraryDAO, Map<?, ?> properties) {
		this.libraryDAO = libraryDAO;
	}

	public void bind1(NomenclatorDAO nomenclatorDAO, Map<?, ?> properties) {
		this.nomenclatorDAO = nomenclatorDAO;
	}

	public void bind2(IJisisDataProvider providerJISIS, Map<?, ?> properties) {
		this.providerJISIS = providerJISIS;
	}

	@Override
	public Library addLibrary(Library library) {
		return libraryDAO.save(library);
	}

	@Override
	public Library readLibrary(Long idLibrary) {
		return libraryDAO.findOne(idLibrary);
	}

	@Override
	public void deleteLibrary(Long idLibrary) {
		libraryDAO.delete(idLibrary);

	}

	@Override
	public Page<Library> findAll(String libraryName, int page, int size,
			int direction, String order) {
		return libraryDAO.findAll(
				LibrarySpecification.searchLibrary(libraryName),
				PageSpecification.getPage(page, size, direction, order));
	}

	@Override
	public List<Nomenclator> findNomenclatorByCode(Long idLibrary, Long idParent) {
		return nomenclatorDAO.findNomenclatorsByLibraryOrLibraryNullAndParent(
				idLibrary, idParent);
	}

	@Override
	public Nomenclator findNomenclatorById(Long idNomenclator) {
		return nomenclatorDAO.findByNomenclatorID(idNomenclator);
	}

	@Override
	public Library findLibraryByName(String libraryName) {
		return libraryDAO.findLibraryByLibraryNameIgnoreCase(libraryName);
	}

	@Override
	public Library findLibraryByHome(String isisHome) {
		return libraryDAO.findLibraryByIsisDefHomeIgnoreCase(isisHome);
	}

	@Override
	public List<String> getDatabaseNames(String libraryIsisDatabasesHomeFolder)
			throws JisisDatabaseException {
		return providerJISIS.getDatabaseNames(libraryIsisDatabasesHomeFolder);
	}

	@Override
	public List<String> getWorksheetNames(String databaseName,
			String libraryIsisDatabasesHomeFolder) {
		try {
			return providerJISIS.getWorksheetNames(databaseName,
					libraryIsisDatabasesHomeFolder);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public WorksheetDef getWorksheet(String worksheetName, String databaseName,
			String libraryIsisDatabasesHomeFolder) {
		try {
			return providerJISIS.getWorksheet(worksheetName, databaseName,
					libraryIsisDatabasesHomeFolder);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean updateWorksheet(WorksheetDef worksheet) {
		try {
			return providerJISIS.updateWorksheet(worksheet);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Nomenclator> findNomenclatorIdOrderByCode(Long idLibrary,
			Long idParent) {
		return nomenclatorDAO
				.findNomenclatorsByLibraryOrLibraryNullAndParentOrderById(
						idLibrary, idParent);
	}

}
