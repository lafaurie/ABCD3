package cu.uci.abcd.opac.ui.controller;

import java.util.List;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.acquisition.Suggestion;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abos.api.ui.ViewController;

public class SuggestionViewController implements ViewController {

	private ProxyController proxyController;

	public Suggestion addSuggestion(Suggestion suggestion) {
		return proxyController.getSuggestionService().addSuggestion(suggestion);
	}

	public Suggestion readSuggestion(Long idSuggestion) {
		return proxyController.getSuggestionService().findSuggestion(
				idSuggestion);
	}
  
	public int countSuggestionPendingByUserAndLibrary(Long userId, Long libraryId) {
				return proxyController.getSuggestionService().countSuggestionByUserAndLibraryAndState(userId, libraryId);
			}
	
	public int countSuggestionByUserAndLibrary(Long userId, Long libraryId) {
		if (proxyController.getSuggestionService() != null) {
			return proxyController.getSuggestionService().countSuggestionByUserAndLibrary(userId, libraryId);
		}
		return 0;
	}

	public void deleteSuggestion(Long idSuggestion) {
		proxyController.getSuggestionService().deleteSuggestion(idSuggestion);
	}
	
	public int findAllSuggestionsPending() {		
		List<Suggestion> tempo = proxyController.getSuggestionService().findAllSuggestionsPending();
		return tempo.size();
	}
	
	public boolean findAllSuggestionByName(String titleSuggestion,Long userId) {
		return proxyController.getSuggestionService().findAllSuggestionByName(titleSuggestion, userId);
	}
	
	public boolean findAllSuggestionByName(Long suggestionId, String titleSuggestion,Long userId) {
		return proxyController.getSuggestionService().findAllSuggestionByName(suggestionId, titleSuggestion, userId);
	}
	public int findAllSuggestions() {		
		List<Suggestion> temp = proxyController.getSuggestionService().findAllSuggestions();
	
		return temp.size();  
	}
	        
	public Page<Suggestion> findAllSuggestionByUserAndLibrary(Long userId, Long libraryId, int page, int size, int direction, String orderByString) {
		
		if (proxyController.getSuggestionService() != null) {			
			
			return proxyController.getSuggestionService().findAllSuggestionByUserAndLibrary(userId, libraryId, page, size, direction, orderByString);
		}   
		
		Page<Suggestion> other = null;
		
		return other;
	}

	public Page<Suggestion> findAllSuggestionPendingByUserAndLibrary(Long userId, Long libraryId, int page, int size, int direction, String orderByString) {

		Page<Suggestion> temp = proxyController.getSuggestionService().findAllSuggestionPendingByUserAndLibrary(userId, libraryId, page, size, direction, orderByString);

		return temp;

	}    

	
////.....Nomenclator....\\\\\
	
	public Nomenclator findNomenclator(Long idNomenclator) {
		
		return proxyController.getIOpacNomenclatorService().findNomenclator(idNomenclator);
	}
	
	public List<Nomenclator> findAllNomenclatorsByCode(String nomencaltorCode) {
		
		return  proxyController.getIOpacNomenclatorService().findAllNomenclatorsByCode(nomencaltorCode);
	}

	public void setProxyController(ProxyController proxyController) {
		this.proxyController = proxyController;
	}
}