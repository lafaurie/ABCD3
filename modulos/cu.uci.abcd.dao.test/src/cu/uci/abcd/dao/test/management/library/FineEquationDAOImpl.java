package cu.uci.abcd.dao.test.management.library;

import cu.uci.abcd.dao.management.library.FineEquationDAO;
import cu.uci.abcd.dao.test.DaoTestException;
import cu.uci.abcd.dao.test.DaoUtil;
import cu.uci.abcd.domain.management.library.FineEquation;

public class FineEquationDAOImpl extends DaoUtil<FineEquation> implements FineEquationDAO {

	@Override
	public FineEquation findDistinctFineEquationByLibrary_LibraryID(Long library) {
		for (FineEquation fineEquation : data) {
			if ( library.equals(fineEquation.getLibrary().getLibraryID())) {
				return fineEquation;
			}
		}
		throw new DaoTestException("Data Not Found.");
	}

	

}
