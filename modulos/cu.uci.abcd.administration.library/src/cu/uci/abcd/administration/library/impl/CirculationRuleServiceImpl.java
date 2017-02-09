package cu.uci.abcd.administration.library.impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import cu.uci.abcd.administration.library.ICirculationRuleService;
import cu.uci.abcd.dao.management.library.CirculationRuleDAO;
import cu.uci.abcd.dao.specification.LibrarySpecification;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.CirculationRule;
import cu.uci.abcd.domain.management.library.Library;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class CirculationRuleServiceImpl implements ICirculationRuleService {

	private CirculationRuleDAO circulationRuleDAO;

	public void bind(CirculationRuleDAO circulationRuleDAO, Map<?, ?> properties) {
		this.circulationRuleDAO = circulationRuleDAO;
	}

	@Override
	public CirculationRule addCirculationRule(CirculationRule circulationRule) {
		return circulationRuleDAO.save(circulationRule);
	}

	@Override
	public CirculationRule readCirculationRule(Long idCirculationRule) {
		return circulationRuleDAO.findOne(idCirculationRule);
	}

	@Override
	public void deleteCirculationRule(Long idCirculationRule) {
		circulationRuleDAO.delete(idCirculationRule);

	}

	@Override
	public Page<CirculationRule> findAll(Library library,
			Nomenclator recordType, Nomenclator loanUserType, int page,
			int size, int direction, String order) {
		return circulationRuleDAO.findAll(LibrarySpecification
				.searchCirculationRule(library, recordType, loanUserType),
				PageSpecification.getPage(page, size, direction, order));
	}

	@Override
	public CirculationRule findByLoanUserTypeAndRecordTypeAndLibrary_LibraryID(
			Nomenclator loanUserType, Nomenclator recordType, Long actorID) {
		return circulationRuleDAO
				.findDistinctCirculationRuleByLoanUserTypeAndRecordTypeAndLibrary_LibraryID(
						loanUserType, recordType, actorID);
	}

	@Override
	public List<CirculationRule> findAll(Long idLibrary) {
		return circulationRuleDAO
				.findDistinctCirculationRuleByLibrary_LibraryID(idLibrary);
	}

}
