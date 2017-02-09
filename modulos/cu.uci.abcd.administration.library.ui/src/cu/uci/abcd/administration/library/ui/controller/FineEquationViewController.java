package cu.uci.abcd.administration.library.ui.controller;

import cu.uci.abcd.domain.management.library.FineEquation;
import cu.uci.abos.api.ui.ViewController;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class FineEquationViewController implements ViewController {

	private AllManagementLibraryViewController allManagementLibraryViewController;

	public AllManagementLibraryViewController getAllManagementLibraryViewController() {
		return allManagementLibraryViewController;
	}

	public void setAllManagementLibraryViewController(AllManagementLibraryViewController allManagementLibraryViewController) {
		this.allManagementLibraryViewController = allManagementLibraryViewController;
	}
	//FIXME BORRAR CODIGO COMENTARIADO
	public FineEquation saveFineEquation(FineEquation fineEquation) {
		// return null;
		return allManagementLibraryViewController.getFineEquationService().addFineEquation(fineEquation);
	}

	public FineEquation findFineEquationByLibrary(Long idLibrary) {
		// return null;
		return allManagementLibraryViewController.getFineEquationService().readFineEquation(idLibrary);
	}
	//FIXME BORRAR CODIGO COMENTARIADO
	// public Library getLibraryById(Long idLibrary) {
	// return
	// allManagementLibraryViewController.getLibraryService().readLibrary(idLibrary);
	// }

}
