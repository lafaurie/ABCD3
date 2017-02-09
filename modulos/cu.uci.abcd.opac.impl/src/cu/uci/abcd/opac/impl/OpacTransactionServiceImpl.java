package cu.uci.abcd.opac.impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import cu.uci.abcd.dao.circulation.TransactionDAO;
import cu.uci.abcd.dao.specification.OpacSpecification;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.domain.circulation.Transaction;
import cu.uci.abcd.opac.IOpacTransactionService;

public class OpacTransactionServiceImpl implements IOpacTransactionService {
	
	TransactionDAO transactionDAO;

	@Override
	public Page<Transaction> findAll(Long loanUserId, int page, int size, int direction, String orderByString) {		
		
		Page<Transaction> temp = transactionDAO.findAll(OpacSpecification.searchTransaction(loanUserId), PageSpecification.getPage(page, size, direction, orderByString));
								
		return temp;
	}

	@Override
	public List<Transaction> findAllTransaction() {
		return (List<Transaction>) transactionDAO.findAll();
	}

	@Override
	public Transaction findTransaction(Long idTransaction) {
		return transactionDAO.findOne(idTransaction);
	}
	   
	public void bind(TransactionDAO transactionDAO, Map<String, Object> properties) {
		this.transactionDAO = transactionDAO;
		System.out.println("servicio registrado");
	}

}
