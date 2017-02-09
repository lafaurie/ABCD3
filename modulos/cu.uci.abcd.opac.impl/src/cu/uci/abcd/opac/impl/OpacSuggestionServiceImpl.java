package cu.uci.abcd.opac.impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import cu.uci.abcd.dao.acquisition.SuggestionDAO;
import cu.uci.abcd.dao.specification.OpacSpecification;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.domain.acquisition.Suggestion;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.opac.IOpacSuggestionService;

public class OpacSuggestionServiceImpl implements IOpacSuggestionService {
	SuggestionDAO suggestionDAO;

	@Override
	public Suggestion addSuggestion(Suggestion suggestion) {

		return suggestionDAO.save(suggestion);
	}

	@Override
	public Suggestion updateSuggestion(Suggestion suggestion) {
		return suggestionDAO.save(suggestion);
	}

	@Override
	public void deleteSuggestion(Long idSuggestion) {
		suggestionDAO.delete(idSuggestion);
	}

	@Override
	public Suggestion findSuggestion(Long arg0) {
		return null;
	}

	@Override
	public Page<Suggestion> findAllSuggestionByUserAndLibrary(Long userId, Long libraryId, int page, int size, int direction, String orderByString) {

		return suggestionDAO.findAll(OpacSpecification.searchSuggestion(userId, libraryId), PageSpecification.getPage(page, size, direction, orderByString));
	}

	@Override
	public Page<Suggestion> findAllSuggestionPendingByUserAndLibrary(Long userId, Long libraryId, int page, int size, int direction, String orderByString) {

		return suggestionDAO.findAll(OpacSpecification.searchSuggestionPending(userId, libraryId), PageSpecification.getPage(page, size, direction, orderByString));
	}

	@Override
	public List<Suggestion> findAllSuggestions() {
		return (List<Suggestion>) suggestionDAO.findAll();
	}

	@Override
	public List<Suggestion> findAllSuggestionsPending() {
		List<Suggestion> temp = (List<Suggestion>) suggestionDAO.findAll();
		// SuggestionState suggetState = SuggestionState.PENDING;
		// FIXME OIGRES, HAcER esto en BD
		// for (int i = 0; i < temp.size(); i++) {
		// if (temp.get(i).getState().getNomenclatorID() !=
		// Nomenclator.SUGGESTION_STATE_PENDING)
		// temp.remove(i);
		// i --;
		// }
		return temp;
	}
	
	@Override
	public boolean findAllSuggestionByName(String titleSuggestion, Long userId) {

		List<Suggestion> temp = (List<Suggestion>) findAllSuggestionsPending();
		boolean booleanSuggestion = false;

		for (int i = 0; i < temp.size(); i++)
			if (temp.get(i).getTitle().equals(titleSuggestion) && temp.get(i).getUser().getUserID() == userId.intValue()) {
				booleanSuggestion = true;
				break;
			}
		return booleanSuggestion;
	}

	@Override
	public boolean findAllSuggestionByName(Long suggestionId, String titleSuggestion, Long userId) {

		List<Suggestion> temp = (List<Suggestion>) findAllSuggestionsPending();
		boolean booleanSuggestion = false;

		for (int i = 0; i < temp.size(); i++)
			if (temp.get(i).getSuggestionID() != suggestionId && temp.get(i).getTitle().equals(titleSuggestion) && temp.get(i).getUser().getUserID() == userId.intValue()) {
				booleanSuggestion = true;
				break;
			}
		return booleanSuggestion;
	}

	public void bind(SuggestionDAO suggestionDAO, Map<String, Object> properties) {
		this.suggestionDAO = suggestionDAO;
		System.out.println("servicio registrado");
	}

	@Override
	public int countSuggestionByUserAndLibrary(Long userId, Long libraryId) {
		List<Suggestion> tempSuggestions = (List<Suggestion>) suggestionDAO.findAll();
		int count = 0;

		// FIXEDDD
		for (Suggestion suggestion : tempSuggestions)
			if (suggestion.getUser().getUserID() == userId)
				count++;

		return count;
	}

	@Override
	public int countSuggestionByUserAndLibraryAndState(Long userId, Long libraryId) {
		List<Suggestion> tempSuggestions = (List<Suggestion>) suggestionDAO.findAll();
		int count = 0;
		// FIXEED
		for (Suggestion suggestion : tempSuggestions)
			if (suggestion.getUser().getUserID() == userId && suggestion.getState().getNomenclatorID() == Nomenclator.SUGGESTION_STATE_PENDING)
				count++;

		return count;
	}
}
