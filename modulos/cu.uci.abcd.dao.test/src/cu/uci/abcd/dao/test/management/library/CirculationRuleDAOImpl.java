package cu.uci.abcd.dao.test.management.library;

import java.util.LinkedList;
import java.util.List;

import cu.uci.abcd.dao.management.library.CirculationRuleDAO;
import cu.uci.abcd.dao.test.DaoTestException;
import cu.uci.abcd.dao.test.DaoUtil;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.CirculationRule;

public class CirculationRuleDAOImpl extends DaoUtil<CirculationRule> implements CirculationRuleDAO{

	@Override
	public List<CirculationRule> findDistinctCirculationRuleByLibrary_LibraryID(Long library) {
		List<CirculationRule> result = new LinkedList<CirculationRule>();
		for (CirculationRule circulationRule : data) {
			if ( library.equals(circulationRule.getLibrary().getLibraryID())) {
				result.add(circulationRule);
			}
		}
		return result;
	}

	@Override
	public CirculationRule findDistinctCirculationRuleByLoanUserTypeAndRecordTypeAndLibrary_LibraryID(Nomenclator loanUserType, Nomenclator recordType, Long library) {
		for (CirculationRule circulationRule : data) {
			if ( library.equals(circulationRule.getLibrary().getLibraryID())&& loanUserType.equals(circulationRule.getLoanUserType())&& recordType.equals(circulationRule.getRecordType())) {
				return circulationRule;
			}
		}
		throw new DaoTestException("Data Not Found.");
	}

	
}
