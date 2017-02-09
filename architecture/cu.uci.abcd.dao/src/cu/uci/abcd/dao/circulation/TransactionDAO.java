package cu.uci.abcd.dao.circulation;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.circulation.LoanUser;
import cu.uci.abcd.domain.circulation.Transaction;
import cu.uci.abcd.domain.common.Nomenclator;

public interface TransactionDAO extends PagingAndSortingRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {

	public List<Transaction> findDistinctTransactionByLoanUser_Person_PersonID(Long personID);

	public List<Transaction> findDistinctTransactionByLoanObject_LoanObjectID(Long loanObjectID);

	//@Query("select n from Transaction n where n.loanUser = ?1 and n.state = ?2 or n.state = ?3 or n.state = ?4 and n.transactionID <> ALL (select x.parenttransaction.transactionID from Transaction x where x.loanUser = ?1  and x.parenttransaction is not null)")
	@Query("select n from Transaction n where n.loanUser = ?1 and (n.state = ?2 or n.state = ?3 or n.state = ?4) and n.isparent = false ")
	public List<Transaction> searchTransactionsByLoanUser(LoanUser loanUser, Nomenclator stateBorrowed,Nomenclator stateRenew,Nomenclator stateLate);

	@Query("select n from Transaction n where n.transactionID <> ALL (select x.parenttransaction.transactionID from Transaction x where x.parenttransaction is not null)")
	public List<Transaction> searchTransactions();

	public List<Transaction> findDistinctTransactionByStateAndLoanUserAndLoanObject_RecordType(Nomenclator loanState, LoanUser loanUser, Nomenclator recordType);

	@Query("select n from Transaction n where n.loanUser = ?1 and n.state = ?2 and n.transactionID <> ALL (select x.parenttransaction.transactionID from Transaction x where x.loanUser = ?1  and x.parenttransaction is not null)")
	public List<Transaction> searchTransactionsLoanByLoanUser(LoanUser loanUser, Nomenclator stateLoan);

	@Query("select n from Transaction n where n.loanUser = ?1 and n.state = ?2 and n.isparent = false")
	public List<Transaction> searchTransactionsNotReturnByLoanUser(LoanUser loanUser, Nomenclator stateLoan);

	@Query("select n from Transaction n where n.endTransactionDate < ?1 and n.state = ?2")
	public List<Transaction> searchTransactionByDate(Date system, Nomenclator state);
}
