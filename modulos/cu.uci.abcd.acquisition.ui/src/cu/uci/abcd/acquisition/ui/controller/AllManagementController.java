package cu.uci.abcd.acquisition.ui.controller;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cu.uci.abcd.acquisition.IManageDesiderataService;
import cu.uci.abcd.acquisition.IManageLoanObjectService;
import cu.uci.abcd.acquisition.IManagePurchaseOrderService;
import cu.uci.abcd.acquisition.IManagePurchaseRequestService;
import cu.uci.abcd.acquisition.IManageRoomService;
import cu.uci.abcd.acquisition.IManageSuggestionService;
import cu.uci.abcd.acquisition.IRegistrationManageAcquisitionService;
import cu.uci.abcd.acquisition.IRegistrationManageSampleAcquisitionService;
import cu.uci.abcd.domain.acquisition.Desiderata;
import cu.uci.abcd.domain.acquisition.PurchaseOrder;
import cu.uci.abcd.domain.acquisition.PurchaseRequest;
import cu.uci.abcd.domain.acquisition.Suggestion;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Provider;
import cu.uci.abcd.domain.management.library.Worker;
import cu.uci.abos.api.ui.ViewController;

public class AllManagementController implements ViewController {
	private IManageDesiderataService desiderataService;
	private IManagePurchaseRequestService purchaseRequestService;
	private IManageSuggestionService suggestionService;
	private IRegistrationManageAcquisitionService acquisitionService;
	private IRegistrationManageSampleAcquisitionService acquisitionSampleService;
	private IManageLoanObjectService acquisitionLoanObjectService; 
	private IManageRoomService acquisitionRoomService;

	
	public IManageRoomService getAcquisitionRoomService() {
		return acquisitionRoomService;
	}

	public void setAcquisitionRoomService(IManageRoomService acquisitionRoomService) {
		this.acquisitionRoomService = acquisitionRoomService;
	}

	private IManagePurchaseOrderService purchaseOrder;
	

	public IManagePurchaseOrderService getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(IManagePurchaseOrderService purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	public IManageSuggestionService getSuggestion() {
		return suggestionService;
	}

	public void setSuggestion(IManageSuggestionService suggestion) {
		this.suggestionService = suggestion;
	}

	public IManageDesiderataService getDesiderata() {
		return desiderataService;
	}

	public void setDesiderata(IManageDesiderataService order) {
		this.desiderataService = order;
	}

	public IManagePurchaseRequestService getPurchaseRequest() {
		return purchaseRequestService;
	}

	public void setPurchaseRequest(IManagePurchaseRequestService purchaseRequest) {
		this.purchaseRequestService = purchaseRequest;
	}

	public IRegistrationManageAcquisitionService getAcquisition() {
		return acquisitionService;
	}

	public void setAcquisition(IRegistrationManageAcquisitionService acquisition) {
		this.acquisitionService = acquisition;
	}

	public IRegistrationManageSampleAcquisitionService getSampleAcquisition() {
		return acquisitionSampleService;
	}
	

	public void setSampleAcquisition(IRegistrationManageSampleAcquisitionService sampleAcquisition) {
		this.acquisitionSampleService = sampleAcquisition;
	}
	
	public IManageLoanObjectService getAcquisitionLoanObjectService() {
		return acquisitionLoanObjectService;
	}   
	
	public void setLoanObjectAcquisition(IManageLoanObjectService locanObjectAcquisition) {
		this.acquisitionLoanObjectService = locanObjectAcquisition;
	}


	public void bindPurchaseOrder(IManagePurchaseOrderService purchaseOrder, Map<?, ?> properties) {
		this.setPurchaseOrder(purchaseOrder);
	}

	public void bindSuggestion(IManageSuggestionService suggestion, Map<?, ?> properties) {
		this.setSuggestion(suggestion);
	}

	public void bindDesiderata(IManageDesiderataService desiderata, Map<?, ?> properties) {
		this.setDesiderata(desiderata);
	}

	public void bindPurchaseRequest(IManagePurchaseRequestService purchaseRequest, Map<?, ?> properties) {
		this.setPurchaseRequest(purchaseRequest);

	}

	public void bindAcquisition(IRegistrationManageAcquisitionService acquisition, Map<?, ?> properties) {
		this.setAcquisition(acquisition);
	}

	public void bindSampleAcquisition(IRegistrationManageSampleAcquisitionService acquisition, Map<?, ?> properties) {
		this.setSampleAcquisition(acquisition);
	}
	
	public void bindLoanObjectAcquisition(IManageLoanObjectService acquisition, Map<?, ?> properties) {
		this.setLoanObjectAcquisition(acquisition);
	}
	
	public void bindRoomService(IManageRoomService acquisition, Map<?, ?> properties) {
		this.setAcquisitionRoomService(acquisition);
	}	
	

		
	public Page<Suggestion> findAllPendingSuggestions(Nomenclator state, String params,int page, int size,int direction, String orderByString) {
		return getSuggestion().findAllSuggestions(new AllSpecification().searchSuggestionByState(state, params),(Pageable) Auxiliary.getPage(page, size, direction, orderByString));
}
	
	public Page<Suggestion> findAllApprovedSuggestionsByParameters(String title,String autor,Date since, Date until, User userType,Nomenclator state,Library library,int page, int size,int direction, String orderByString) {
	 	return getSuggestion().findAllSuggestions(new AllSpecification().searchApprovedSuggestionByParametersSpecification(title,autor,since, until,userType,state,library),(Pageable) Auxiliary.getPage(page, size, direction, orderByString));
}
	
	public Page<Suggestion> findAllPendingSuggestionsByParameters(String title,String autor, Nomenclator state,int page, int size,int direction, String orderByString) {
	 	return getSuggestion().findAllSuggestions(new AllSpecification().searchPendingSuggestionByParametersSpecification(title,autor, state),(Pageable) Auxiliary.getPage(page, size, direction, orderByString));
}
	
	public Page<Suggestion> findAllSuggestionByTitle(String title,Nomenclator state,  int page, int size,int direction, String orderByString) {
		return getSuggestion().findAllSuggestions(new AllSpecification().searchSuggestionByTitle(title,state),(Pageable) Auxiliary.getPage(page, size, direction, orderByString));
}
	
	public Page<Suggestion> findAllSuggestionByAuthor(String author,Nomenclator state,  int page, int size,int direction, String orderByString) {
		return getSuggestion().findAllSuggestions(new AllSpecification().searchSuggestionByAuthor(author,state),(Pageable) Auxiliary.getPage(page, size, direction, orderByString));
}
	
	public Page<Suggestion> findAllSuggestionByEditor(String editor,Nomenclator state,  int page, int size,int direction, String orderByString) {
		return getSuggestion().findAllSuggestions(new AllSpecification().searchSuggestionByEditor(editor,state),(Pageable) Auxiliary.getPage(page, size, direction, orderByString));
}
	
	public Page<Suggestion> findAllAssociatedSuggestionByOrderId(Long idDesiderata,Nomenclator state,  int page, int size,int direction, String orderByString) {
		return getSuggestion().findAllSuggestions(new AllSpecification().searchSuggestionDesiderataId(idDesiderata,state),(Pageable) Auxiliary.getPage(page, size, direction, orderByString));
}
	
	public Page<Desiderata> findAllPendingDesideratas(Nomenclator state,int page, int size,int direction, String orderByString) {
		return getDesiderata().findAllDesideratas( new AllSpecification().searchDesiderataByState(state),Auxiliary.getPage(page, size, direction, orderByString));
	
}
	
	public Page<Desiderata> findDesideratasByPurchaseRequest(Nomenclator state, String params,int page, int size,int direction, String orderByString) {
		return getDesiderata().findAllDesideratas( new AllSpecification().searchDesiderataByPurchaseRequest(state,params),Auxiliary.getPage(page, size, direction, orderByString));
	
}

	public Page<Desiderata> findAllDesideratasByParameters(String titulo,
			String autor, String editor, Date since, Date until,
			Nomenclator state,Library library, int page, int size, int direction,
			String orderByString) {
		return getDesiderata()
				.findAllDesideratas(
						new AllSpecification().searchDesiderataByParametersSpecification(
								titulo, autor, editor, since, until, state,library),
						Auxiliary.getPage(page, size, direction, orderByString));
	}
	
	public Page<Desiderata> findAllDesideratasByPurchaseRequestId(Long idPurchaseOrder,Nomenclator state,  int page, int size,int direction, String orderByString){
		return getDesiderata().findAllDesideratas(new AllSpecification().searchDesideratasByPurchaseRequestId(idPurchaseOrder, state), Auxiliary.getPage(page, size, direction, orderByString));
	}
	
	public Page<PurchaseRequest> findAllPendingPurchaseRequest(Nomenclator state, String requestNumber,int page, int size,int direction, String orderByString) {
		return getPurchaseRequest().findAllPurchaseRequest( new AllSpecification().searchPurchaseRequestByState(state,requestNumber),Auxiliary.getPage(page, size, direction, orderByString));
	
}
	public Page<PurchaseRequest> findAllPurchaseRequestByParameters(Date since, Date until,Worker workerID,String requestNumber, Nomenclator state, Library library,int page, int size, int direction, String orderByString){
		return getPurchaseRequest().findAllPurchaseRequest(new AllSpecification().searchPurchaseRequestByParameters(since,until,workerID,requestNumber, state,library), Auxiliary.getPage(page, size, direction, orderByString));
		
	}
		
	public Page<PurchaseOrder> findAllPendingPurchaseOrder(Nomenclator state,int page, int size,int direction, String orderByString) {
		return getPurchaseOrder().findAllPurchaseOrders(new AllSpecification().searchPurchaseOrderByState(state),Auxiliary.getPage(page, size, direction, orderByString));
}
	
	public Page<PurchaseOrder> searchPendingPurchaseOrderByParameters(long approvedByID,long createdByID,Nomenclator state,int page, int size, int direction, String orderByString){
		return getPurchaseOrder().findAllPurchaseOrders(new AllSpecification().searchPendingPurchaseOrderByParameters(approvedByID,createdByID,state), Auxiliary.getPage(page, size, direction, orderByString));
	}
	
	public Page<PurchaseOrder> searchConsultPurchaseOrderByParameters(Date since,Date until,Provider nameProvider,Integer indice, Double totalAmount, Worker creator, Nomenclator state, String orderNumber, Library library, int page, int size, int direction, String orderByString){
		return getPurchaseOrder().findAllPurchaseOrders(new AllSpecification().searchConsultPurchaseOrderByParameters(since, until,nameProvider,indice,totalAmount,  creator, state, orderNumber, library), Auxiliary.getPage(page, size, direction, orderByString));
	}
	
	public void rejectAllPendingSuggestion(Nomenclator reason) {
		List<Suggestion> lista=getSuggestion().findAll();
		int cont = lista.size() - 1;
		if (lista.size() > 0) {
			while (cont >= 0) {
				Suggestion a = getSuggestion().findOne(lista.get(cont).getSuggestionID());
				if(a.getState().equals(getSuggestion().getNomenclator((long)701))){
				a.setState(getSuggestion().getNomenclator((long)703));
				a.setRejectMotive(reason);
				getSuggestion().addSuggestion(a);
				lista.remove(cont);
				}
				cont--;
			}
		}
	}

	public void approveAllPendingSuggestion(Nomenclator reason) {
		List<Suggestion> lista=getSuggestion().findAll();
		int cont = lista.size() - 1;
		if (lista.size() > 0) {
			while (cont >= 0) {
				Suggestion a = getSuggestion().findOne(lista.get(cont).getSuggestionID());
				if(a.getState().equals(getSuggestion().getNomenclator((long)701))){
				a.setState(getSuggestion().getNomenclator((long)702));
				a.setRejectMotive(reason);
				getSuggestion().addSuggestion(a);
				lista.remove(cont);
				}
				cont--;
			}
		}
	}

	public void approveAllPendingPurchaseOrder(Nomenclator reason) {
		List<PurchaseOrder> lista = getPurchaseOrder().findAll();
		int cont = lista.size() - 1;
		if (lista.size() > 0) {
			while (cont >= 0) {
				 PurchaseOrder a =getPurchaseOrder().readPurchaseOrder(lista .get(cont).getPurchaseOrderID());
				 if(a.getState().equals(getSuggestion().getNomenclator((long)1019))){
				 a.setState(getSuggestion().getNomenclator((long)1020));
				 a.setAcceptanceMotive(reason);
				 getPurchaseOrder().setPurchaseOrder(a); 
				 lista.remove(cont);
				 }
				 cont--;			 

			}
		}
	}


	public void rejectAllPendingPurchaseOrder(Nomenclator reason) {
		List<PurchaseOrder> lista = getPurchaseOrder().findAll();
		int cont = lista.size() - 1;
		if (lista.size() > 0) {
			while (cont >= 0) {
				PurchaseOrder a =getPurchaseOrder().readPurchaseOrder(lista .get(cont).getPurchaseOrderID());
				 if(a.getState().equals(getSuggestion().getNomenclator((long)1019))){
				 a.setState(getSuggestion().getNomenclator((long)1025));
				 a.setAcceptanceMotive(reason);
				 getPurchaseOrder().setPurchaseOrder(a); 
				 lista.remove(cont);
				 }
				 cont--;	
			}
		}

	}

	public long generateID() {
		List<Desiderata> desiderataList = getDesiderata().findAll();
		if (desiderataList.size() == 0)
			return 1;
		return desiderataList.get(desiderataList.size() - 1).getDesidertaID() + 1;
	}



	public String generateIdentifier() {
		long clave = 0;
		String claveR = "";
		List<Desiderata> desiderataList = getDesiderata().findAll();
		if (desiderataList.size() == 0)
			return "1234567891" + 0;
		clave = (Long) desiderataList.get(desiderataList.size() - 1).getDesidertaID() + 1;
		claveR += clave;
		return claveR;
	}


}
