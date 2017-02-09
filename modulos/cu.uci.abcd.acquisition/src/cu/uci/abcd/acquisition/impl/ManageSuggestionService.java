package cu.uci.abcd.acquisition.impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cu.uci.abcd.acquisition.IManageSuggestionService;
import cu.uci.abcd.dao.acquisition.SuggestionDAO;
import cu.uci.abcd.dao.common.NomenclatorDAO;
import cu.uci.abcd.dao.common.UserDAO;
import cu.uci.abcd.domain.acquisition.Suggestion;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;

public class ManageSuggestionService implements IManageSuggestionService {
	private SuggestionDAO suggestionDao;
	private NomenclatorDAO nomenclatorDao;
	private UserDAO userDao;
	
	@Override
	public Suggestion findOne(Long idSuggestion) {
		return suggestionDao.findOne(idSuggestion);
	}

	@Override
	// RF_AQ2.1_Rechazar Sugerencias
	public Suggestion addSuggestion(Suggestion a) {
		return suggestionDao.save(a);

	}

	@Override
	public List<Suggestion> findAll() {
		return (List<Suggestion>) suggestionDao.findAll();
	}

	public void bind(SuggestionDAO suggestionDao, Map<?, ?> properties) {
		this.suggestionDao = suggestionDao;

	}

	public void bind1(NomenclatorDAO nomenclatorDao, Map<?, ?> properties) {
		this.nomenclatorDao = nomenclatorDao;
	}
	
	public void bindUser(UserDAO userDao, Map<?, ?> properties) {
		this.userDao = userDao;
	}
	@Override
	public Page<Suggestion> findAllSuggestions(Specification<Suggestion> specification, Pageable pageable) {
		return suggestionDao.findAll(specification, pageable);
	}

	@Override
	public Nomenclator getNomenclator(Long id) {
		return nomenclatorDao.findOne(id);
	}

	@Override
	public List<Nomenclator> findAllNomenclators(Long libraryID, Long id) {
		return nomenclatorDao.findNomenclatorsByLibraryOrLibraryNullAndParent(libraryID,id);
	}

	@Override
	public void deleteSuggestion(Long idSuggestion) {
		suggestionDao.delete(idSuggestion);
	}

	@Override
	public List<Suggestion> findSuggestionByIdDesiderata(Long idDesiderata) {
		return suggestionDao.findDistinctSuggestionByAssociateDesiderata_desiderataID(idDesiderata);
	}

	@Override
	public List<User> findAllUsers() {
	
		return (List<User>) userDao.findAll();
	}

}
