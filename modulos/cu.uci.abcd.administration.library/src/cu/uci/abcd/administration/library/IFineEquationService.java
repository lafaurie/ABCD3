package cu.uci.abcd.administration.library;

import cu.uci.abcd.domain.management.library.FineEquation;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public interface IFineEquationService {

	public FineEquation addFineEquation(FineEquation fineEquation);

	public FineEquation readFineEquation(Long idLibrary);
}
