package cu.uci.abcd.administration.library;

import java.util.List;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.CirculationRule;
import cu.uci.abcd.domain.management.library.Library;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public interface ICirculationRuleService {

	public CirculationRule addCirculationRule(CirculationRule circulationRule);

	public CirculationRule readCirculationRule(Long idCirculationRule);

	public void deleteCirculationRule(Long idCirculationRule);

	public List<CirculationRule> findAll(Long idLibrary);

	public Page<CirculationRule> findAll(Library library,
			Nomenclator recordType, Nomenclator loanUserType, int page,
			int size, int direction, String order);

	public CirculationRule findByLoanUserTypeAndRecordTypeAndLibrary_LibraryID(
			Nomenclator loanUserType, Nomenclator recordType, Long actorID);
}
