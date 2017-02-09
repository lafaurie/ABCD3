package cu.uci.abcd.acquisition;

import cu.uci.abcd.domain.common.LoanObject;

public interface IManageLoanObjectService {
	
	/**
	 * Add Loan Object
	 * 
	 * @param loanObject
	 * @return
	 */
	public LoanObject addLoanObject(LoanObject loanObject);

}
