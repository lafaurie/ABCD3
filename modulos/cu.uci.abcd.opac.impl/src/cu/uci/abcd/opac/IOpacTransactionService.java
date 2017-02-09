package cu.uci.abcd.opac;

import java.util.List;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.circulation.Transaction;

public interface IOpacTransactionService {			    
	//FIXME FALTAN COMENTARIOS DE INTERFACE
	public List<Transaction> findAllTransaction();
	
	public Transaction findTransaction(Long idTransaction);
	
	public Page<Transaction> findAll(Long loanUserId, int page, int size, int direction, String orderByString);

}
