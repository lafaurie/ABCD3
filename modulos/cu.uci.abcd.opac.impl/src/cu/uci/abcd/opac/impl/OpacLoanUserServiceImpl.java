package cu.uci.abcd.opac.impl;

import java.util.Map;

import cu.uci.abcd.dao.circulation.LoanUserDAO;
import cu.uci.abcd.domain.circulation.LoanUser;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.opac.IOpacLoanUserService;

public class OpacLoanUserServiceImpl implements IOpacLoanUserService {

	LoanUserDAO loanUserDAO;

	public void bind(LoanUserDAO loanUserDAO, Map<String, Object> properties) {
		this.loanUserDAO = loanUserDAO;
		System.out.println("servicio registrado");
	}
   
	@Override
	public LoanUser findLoanUserByIdPersonAndIdLibrary(Long personID, Long libraryID) {

		return loanUserDAO.findByPersonIDAndLoanUserStateAndLibraryID(personID, Nomenclator.LOANUSER_STATE_ACTIVE, libraryID);
	}

}
