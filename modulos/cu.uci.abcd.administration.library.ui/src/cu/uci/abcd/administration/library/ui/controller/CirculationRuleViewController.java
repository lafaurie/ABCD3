package cu.uci.abcd.administration.library.ui.controller;

import java.util.List;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.CirculationRule;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.ViewController;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class CirculationRuleViewController implements ViewController {

	private AllManagementLibraryViewController allManagementLibraryViewController;

	public AllManagementLibraryViewController getAllManagementLibraryViewController() {
		return allManagementLibraryViewController;
	}

	public void setAllManagementLibraryViewController(
			AllManagementLibraryViewController allManagementLibraryViewController) {
		this.allManagementLibraryViewController = allManagementLibraryViewController;
	}

	public CirculationRule saveCirculationRule(CirculationRule circulationRule) {
		return allManagementLibraryViewController.getCirculationRuleService()
				.addCirculationRule(circulationRule);
	}

	public CirculationRule getCirculationRuleById(Long idCirculationRule) {
		return allManagementLibraryViewController.getCirculationRuleService()
				.readCirculationRule(idCirculationRule);
	}

	public void deleteCirculationRuleById(Long idCirculationRule) {
		allManagementLibraryViewController.getCirculationRuleService()
				.deleteCirculationRule(idCirculationRule);
	}

	public Page<CirculationRule> findCirculationRuleByParams(Library library,
			Nomenclator recordType, Nomenclator loanUserType, int page,
			int size, int direction, String order) {
		return allManagementLibraryViewController.getCirculationRuleService()
				.findAll(library, recordType, loanUserType, page, size,
						direction, order);
	}

	public CirculationRule findByLoanUserTypeAndRecordTypeAndLibrary_LibraryID(
			Nomenclator loanUserType, Nomenclator recordType, Long actorID) {
		return allManagementLibraryViewController.getCirculationRuleService()
				.findByLoanUserTypeAndRecordTypeAndLibrary_LibraryID(
						loanUserType, recordType, actorID);
	}

	public List<CirculationRule> findCirculationRuleByLibrary(Long idLibrary) {
		return allManagementLibraryViewController.getCirculationRuleService()
				.findAll(idLibrary);
	}
}
