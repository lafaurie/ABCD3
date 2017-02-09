package cu.uci.abcd.administration.library.impl;

import java.util.Map;

import cu.uci.abcd.administration.library.IFineEquationService;
import cu.uci.abcd.dao.management.library.FineEquationDAO;
import cu.uci.abcd.domain.management.library.FineEquation;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class FineEquationServiceImpl implements IFineEquationService {

	private FineEquationDAO fineEquationDAO;

	public void bind(FineEquationDAO fineEquationDAO, Map<?, ?> properties) {
		this.fineEquationDAO = fineEquationDAO;
	}

	@Override
	public FineEquation readFineEquation(Long idLibrary) {
		return fineEquationDAO
				.findDistinctFineEquationByLibrary_LibraryID(idLibrary);
	}

	@Override
	public FineEquation addFineEquation(FineEquation fineEquation) {
		return fineEquationDAO.save(fineEquation);
	}

}
