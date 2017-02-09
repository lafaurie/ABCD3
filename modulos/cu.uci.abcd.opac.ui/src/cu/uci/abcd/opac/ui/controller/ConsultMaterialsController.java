package cu.uci.abcd.opac.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.unesco.jisis.corelib.common.FieldSelectionTable;
import org.unesco.jisis.corelib.common.WorksheetDef;
import org.unesco.jisis.corelib.record.Field;

import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.Option;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.opac.OpacDataSources;
import cu.uci.abcd.opac.RecordIsis;
import cu.uci.abos.api.ui.ViewController;

public class ConsultMaterialsController implements ViewController {

	private ProxyController proxyController;
	private String yearParser;
	private int year;

	public List<RecordIsis> findByOptions(List<Option> options, String databaseName, String libraryIsisDatabasesHomeFolder, Library library) throws JisisDatabaseException {
		return proxyController.getIOpacDataBaseManager().findByOptions(options, databaseName, libraryIsisDatabasesHomeFolder, library);
	}

	public List<RecordIsis> find(String term, String databaseName, String libraryIsisDatabasesHomeFolder, Library library, List<Option> options) throws JisisDatabaseException {
		return proxyController.getIOpacDataBaseManager().find(term, databaseName, libraryIsisDatabasesHomeFolder, library, options);
	}

	public FieldSelectionTable getFieldSelectionTable(String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException {
		return proxyController.getIOpacDataBaseManager().getFieldSelectionTable(databaseName, libraryIsisDatabasesHomeFolder);
	}

	public List<String> getWorksheetNames(String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException {
		return proxyController.getIOpacDataBaseManager().getWorksheetNames(databaseName, libraryIsisDatabasesHomeFolder);
	}

	public List<String> getDataBaseNames(String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException {
		return proxyController.getIOpacDataBaseManager().getDatabaseNames(libraryIsisDatabasesHomeFolder);
	}    

	public WorksheetDef getWorksheet(String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException {
		return proxyController.getIOpacDataBaseManager().getWorksheet(databaseName, libraryIsisDatabasesHomeFolder);
	}

	public float ratingByUser(Long libraryId, String mfn, String dataBaseName) {
		return proxyController.getIOpacRecordRatingService().findRatingByRecord(libraryId, mfn, dataBaseName);
	}

	public List<RecordIsis> filterByDate(Integer after, Integer before, List<RecordIsis> recordIsis) {

		if (after != null && before != null) {

			for (int i = 0; i < recordIsis.size(); i++) {

				try {

					year = recordIsis.get(i).getPublicationDate();

					if (!(after <= year && year <= before)) {
						recordIsis.remove(i);
						i--;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			return recordIsis;

		} else if (after != null) {
			for (int i = 0; i < recordIsis.size(); i++) {

				try {

					Field publicationFieldAfter = (Field) recordIsis.get(i).getRecord().getField(260);
					yearParser = publicationFieldAfter.getSubfield("c");

					if (yearParser.charAt(0) != '[') {
						if (yearParser.charAt(yearParser.length() - 1) != '.') {
							year = Integer.parseInt(yearParser);
						} else
							year = Integer.parseInt(yearParser.substring(0, yearParser.length() - 2));

					} else if (yearParser.charAt(yearParser.length() - 1) != '.')
						year = Integer.parseInt(yearParser.substring(1, yearParser.length() - 3));
					else
						year = Integer.parseInt(yearParser.substring(1, yearParser.length() - 1));

					if (year <= after) {
						recordIsis.remove(i);
						i--;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			return recordIsis;

		} else if (before != null) {
			for (int i = 0; i < recordIsis.size(); i++) {

				try {

					Field publicationFieldAfter = (Field) recordIsis.get(i).getRecord().getField(260);
					yearParser = publicationFieldAfter.getSubfield("c");

					if (yearParser.charAt(0) != '[') {
						if (yearParser.charAt(yearParser.length() - 1) != '.') {
							year = Integer.parseInt(yearParser);
						} else
							year = Integer.parseInt(yearParser.substring(0, yearParser.length() - 2));

					} else if (yearParser.charAt(yearParser.length() - 1) != '.')
						year = Integer.parseInt(yearParser.substring(1, yearParser.length() - 3));
					else
						year = Integer.parseInt(yearParser.substring(1, yearParser.length() - 1));

					if (year >= before) {
						recordIsis.remove(i);
						i--;
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			return recordIsis;
		}

		return recordIsis;

	}

	public List<Library> findAllLibrary() {
		return proxyController.getIOpacLibraryService().findAllLibrary();
	};

	/**
	 * 
	 * Nomenclator
	 */

	public List<Nomenclator> findAllNomencaltors(Long nomenclatorID) {

		return proxyController.getIOpacNomenclatorService().findAllNomencaltors(nomenclatorID);
	}

	public void setProxyController(ProxyController proxyController) {
		this.proxyController = proxyController;
	}

	/**
	 * Library
	 */

	public List<Library> findAllAvailableLibrary() {
		return proxyController.getIOpacLibraryService().findAllAvailableLibrary();
	}

	public List<String> getAllDataSourcesByLibrary(Long libraryID) {

		List<String> databaseNames = new ArrayList<String>();

		List<OpacDataSources> dataSources = proxyController.getIOpacLibraryService().getAllDataSourcesByLibrary(libraryID);

		for (OpacDataSources opacDataSources : dataSources)
			databaseNames.add(opacDataSources.getDatabaseName());

		return databaseNames;
	}

}
