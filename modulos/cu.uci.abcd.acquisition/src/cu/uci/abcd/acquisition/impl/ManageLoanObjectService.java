package cu.uci.abcd.acquisition.impl;

import java.util.Map;

import cu.uci.abcd.acquisition.IManageLoanObjectService;
import cu.uci.abcd.dao.common.LoanObjectDAO;
import cu.uci.abcd.domain.common.LoanObject;

public class ManageLoanObjectService implements IManageLoanObjectService{

	private LoanObjectDAO loanObjectDAO;

	@Override
	public LoanObject addLoanObject(LoanObject loanObject) {
		
		return loanObjectDAO.save(loanObject);
	}
	
	public void bindLoanObjectDao(LoanObjectDAO loanObjectDAO, Map<?, ?> properties) {
		this.loanObjectDAO = loanObjectDAO;
	}

}
