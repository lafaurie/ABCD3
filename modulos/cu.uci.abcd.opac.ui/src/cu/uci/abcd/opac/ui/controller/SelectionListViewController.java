package cu.uci.abcd.opac.ui.controller;
    
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.opac.SelectionList;
import cu.uci.abcd.opac.RecordIsis;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.reports.PDFReportGenerator;

public class SelectionListViewController implements ViewController {
	private ProxyController proxyController;

	public SelectionList addSelectionList(SelectionList selectionList) {
		return proxyController.getIOpacSelectionListService().addSelectionList(selectionList);
	}

	public SelectionList findSelectionList(Long idSelectionList) {
		return proxyController.getIOpacSelectionListService().findSelectionList(idSelectionList);
	}
   
	public int findAllPublicSelectionLists() {
		if (proxyController.getIOpacSelectionListService() != null) {
			return proxyController.getIOpacSelectionListService().findAllPublicSelectionList();
		}
		return 0;
	}

	public List<SelectionList> findAllSelectionListsByUser(Long userId, Long libraryId) {
		if (proxyController.getIOpacSelectionListService() != null) {
			return proxyController.getIOpacSelectionListService().findAllSelectionListByUser(userId, libraryId);
		}
		return new ArrayList<SelectionList>();
	}
	
	public boolean findAllSelectionListsByName(String nameSelectionList, Long userId) {
			return proxyController.getIOpacSelectionListService().findAllSelectionListByName(nameSelectionList, userId);
	}
	     
	public boolean findAllSelectionListsByName(String nameSelectionList, Long userId, Long selectionListId) {
		return proxyController.getIOpacSelectionListService().findAllSelectionListByName(nameSelectionList, userId, selectionListId);
	}
	
	public List<SelectionList> findAllSelectionLists() {
			return proxyController.getIOpacSelectionListService().findAllSelectionList();
	}
	    

	public Page<SelectionList> findAllPublicSelectionListPage(int page, int size, int direction, String orderByString) {

		return proxyController.getIOpacSelectionListService().findAllPublicSelectionListPage(page, size, direction, orderByString);
	}

	public Page<SelectionList> findAllSelectionListPageByUser(Integer userId, Long libraryId, int page, int size, int direction, String orderByString) {

		return proxyController.getIOpacSelectionListService().findAllSelectionListPageByUser(userId, libraryId, page, size, direction, orderByString);
	}   
     
	public void deleteSelectionList(Long idSelectionList) {
		proxyController.getIOpacSelectionListService().deleteSelectionList(idSelectionList);
	}

	// //.....Nomenclator....\\\\\

	public Nomenclator findNomenclator(Long idNomenclator) {

		return proxyController.getIOpacNomenclatorService().findNomenclator(idNomenclator);
	}
	
	public List<Nomenclator> findAllNomencaltors(Long nomenclatorID) {

		return proxyController.getIOpacNomenclatorService().findAllNomencaltors(nomenclatorID);
	}

	// //.....Isis.....\\\\

	/*public List<RecordIsis> findByMfns(long[] mfns, String databaseName, String libraryIsisDatabasesHomeFolder, Library library) throws JisisDatabaseException {

		return proxyController.getIOpacDataBaseManager().findByMfns(mfns, databaseName, libraryIsisDatabasesHomeFolder, library);
	}
	  */  
	
	public List<RecordIsis> findRecordByControlNumber(List<String> controlNumber, String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException {
				   
		return proxyController.getIOpacDataBaseManager().findRecordByControlNumber(controlNumber, databaseName, libraryIsisDatabasesHomeFolder);

	}   
	
	// //.....PDFGenerator.....\\\\

	public PDFReportGenerator getPdfGenerator() {
		return proxyController.getPDFGeneratorService();		
	}


	public void setProxyController(ProxyController proxyController) {
		this.proxyController = proxyController;
	}
}
