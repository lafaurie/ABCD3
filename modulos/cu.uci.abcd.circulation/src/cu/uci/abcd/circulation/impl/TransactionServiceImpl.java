package cu.uci.abcd.circulation.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import cu.uci.abcd.circulation.ITransactionService;
import cu.uci.abcd.dao.circulation.TransactionDAO;
import cu.uci.abcd.dao.common.NomenclatorDAO;
import cu.uci.abcd.dao.common.StatementExecutorDAO;
import cu.uci.abcd.dao.management.library.CalendarDAO;
import cu.uci.abcd.dao.management.library.CirculationRuleDAO;
import cu.uci.abcd.dao.management.library.ScheduleDAO;
import cu.uci.abcd.dao.specification.CirculationSpecification;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.domain.circulation.LoanUser;
import cu.uci.abcd.domain.circulation.Transaction;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Calendar;
import cu.uci.abcd.domain.management.library.CirculationRule;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abcd.domain.management.library.Schedule;


/**
 * 
 * @author Abelito
 * 
 */
public class TransactionServiceImpl implements ITransactionService {

	private TransactionDAO transactionDAO;
	private CirculationRuleDAO circulationRuleDAO;
	private CalendarDAO calendarDAO;
	private ScheduleDAO scheduleDAO;
	private NomenclatorDAO nomenclatorDAO;
	private StatementExecutorDAO executorDAO;
	
	/**
	 * @see ITransactionService
	 */

	@Override
	public Transaction addTransaction(Transaction transaction) {
		return transactionDAO.save(transaction);
	}

	@Override
	public void deleteTransaction(Long idTransaction) {
		Transaction eliminar = transactionDAO.findOne(idTransaction);
		transactionDAO.delete(eliminar);
	}

	@Override
	public Transaction findOneTransaction(Long idTransaction) {
		return transactionDAO.findOne(idTransaction);
	}

	@Override
	public CirculationRule findCirculationRule(Nomenclator circulationRuleState, Nomenclator loanUserType, Nomenclator recordType, Long actorID) {
		return circulationRuleDAO.findDistinctCirculationRuleBycirculationRuleStateAndLoanUserTypeAndRecordTypeAndLibrary_LibraryID(circulationRuleState, loanUserType, recordType, actorID);
	}

	@Override
	public List<Schedule> findHorarybyLibrary(Long actorID) {

	return scheduleDAO.findDistinctScheduleByLibrary_LibraryID(actorID);
		
	}

	public void bindTransactionDao(TransactionDAO transactionDAO, Map<?, ?> properties) {
		this.transactionDAO = transactionDAO;
	}

	public void bindCirculationRuleDao(CirculationRuleDAO circulationRuleDAO, Map<?, ?> properties) {
		this.circulationRuleDAO = circulationRuleDAO;
	}

	public void bindCalendarDao(CalendarDAO calendarDAO, Map<?, ?> properties) {
		this.calendarDAO = calendarDAO;
	}

	public void bindScheduleDao(ScheduleDAO scheduleDAO, Map<?, ?> properties) {
		this.scheduleDAO = scheduleDAO;
	}

	public void bindNomenclatorDao(NomenclatorDAO nomenclatorDAO, Map<?, ?> properties) {
		this.nomenclatorDAO = nomenclatorDAO;
	}

	public void bindStatementExecutorDao(StatementExecutorDAO executorDAO, Map<?, ?> properties) {
		this.executorDAO = executorDAO;
	}
	@Override
	public List<Transaction> findAll() {
		return (List<Transaction>) transactionDAO.searchTransactions();
	}
//, Nomenclator stateRenew, Nomenclator stateLate
	@Override
	public List<Transaction> searchTransactionsByLoanUser(LoanUser loanUser, Nomenclator stateLoan, Nomenclator stateRenew, Nomenclator stateLate) {
		return transactionDAO.searchTransactionsByLoanUser(loanUser, stateLoan,stateRenew,stateLate);
	}

	@Override
	public List<Transaction> findTransactionByStateAndLoanUserAndLoanObject_RecordType(Nomenclator loanState, LoanUser loanUser, Nomenclator recordType) {
		return transactionDAO.findDistinctTransactionByStateAndLoanUserAndLoanObject_RecordType(loanState, loanUser, recordType);
	}

	@Override
	public List<Transaction> searchTransactionsLoanByLoanUser(LoanUser loanUser, Nomenclator stateLoan) {

		return transactionDAO.searchTransactionsLoanByLoanUser(loanUser, stateLoan);
	}

	@Override
	public List<Calendar> findCalendar(Long arg0) {
		return calendarDAO.findDistinctCalendarByLibrary_LibraryID(arg0);
	}

	@Override
	public Page<Transaction> findAllTransactionConsult(String inventory_number,
			String title, Nomenclator record_type_id,
			Nomenclator loan_user_type_id, String loan_user_code,
			String first_Name, String second_Name, String first_Surname,
			String second_Surname, Nomenclator loan_type,
			Nomenclator transaction_state, Date dateRegister,
			Date endDateRegister, Room loan_object_rooms,Library library, int page, int size,
			int direction, String orderByString) {
		return transactionDAO.findAll(CirculationSpecification
				.searchTransaction(inventory_number, title, record_type_id,
						loan_user_type_id, loan_user_code, first_Name,
						second_Name, first_Surname, second_Surname, loan_type,
						transaction_state, dateRegister, endDateRegister,
						loan_object_rooms,library), PageSpecification.getPage(page,
				size, direction, orderByString));
	}

	@Override
	public Page<Transaction> findAllTransactionByLoanUserCurrent(Long loanUserID, int page, int size, int direction, String orderByString) {
		return transactionDAO.findAll(CirculationSpecification.searchTransactionByLoanUserCurrent(loanUserID), PageSpecification.getPage(page, size, direction, orderByString));
	}

	@Override
	public Page<Transaction> findAllTransactionByLoanUserHistory(Long loanUserID, int page, int size, int direction, String orderByString) {
		return transactionDAO.findAll(CirculationSpecification.searchTransactionByLoanUserHistory(loanUserID), PageSpecification.getPage(page, size, direction, orderByString));
	}

	@Override
	public Page<Transaction> findAllTransactionByLoanObject(Long loanObjectID, int page, int size, int direction, String orderByString) {

		return transactionDAO.findAll(CirculationSpecification.searchTransactionByLoanObject(loanObjectID), PageSpecification.getPage(page, size, direction, orderByString));
	}

	@Override
	public Page<Transaction> findAllTransactionByLoanUserReturn(Long loanUserID, int page, int size, int direction, String orderByString) {

		return transactionDAO.findAll(CirculationSpecification.searchTransactionByLoanUserReturn(loanUserID), PageSpecification.getPage(page, size, direction, orderByString));
	}
	
	@Override
	public void registerPenaltyAutomatic() {
	
		java.util.Date fecha = new java.util.Date();
	
		Nomenclator loanStateBorrowed = nomenclatorDAO.findOne(Nomenclator.LOAN_STATE_BORROWED);
		Nomenclator loanStateRenew = nomenclatorDAO.findOne(Nomenclator.LOAN_STATE_RENEW);
		
	//	Nomenclator circulationRuleState = nomenclatorDAO.findOne(Nomenclator.CIRCULATION_RULE_STATE_ACTIVE);

		Nomenclator loanStateLate = nomenclatorDAO.findOne(Nomenclator.LOAN_STATE_LATE);
		
	//	Double amountCirculationRule;
	//	long dayDelay = 0;
		System.out.println("transaccion por procesar,actualizar el estado de una transaccion a atrasada"); 
		
		executorDAO.executeUpdate(
				"update abcdn.dtransactions set loanstate = ?1, isparent ='false' where endtransactiondate < ?2 and (loanstate = ?3 or loanstate = ?4)",
			new Object[] {
						new Long(loanStateLate.getNomenclatorID()),
						new Date(fecha.getTime()),
						new Long(loanStateBorrowed.getNomenclatorID()),
						new Long(loanStateRenew.getNomenclatorID())});
		
		System.out.println("transaccion actualizada, estado atrasada"); 

	//Sancion por atraso en la entrega del material
	/*	
		Nomenclator typeSuspension = nomenclatorDAO.findOne(Nomenclator.PENALTY_TYPE_SUSPENCION);
		Nomenclator stateActive = nomenclatorDAO.findOne(Nomenclator.PENALTY_STATE_ACTIVE);
		
		java.util.Date fecha1 = new java.util.Date();
	
		System.out.println("Sancion de suspension por atraso en la entrega del material"); 
		
		executorDAO
				.executeUpdate(
						"insert into abcdn.dpenalty (loanuser,loanobject,library,state,penaltytype,effectivedate,expirationdate) values ((select loanuser, loanobject, librarian from abcdn.dtransactions where loanstate = ?1, isparent ='false'), ?2, ?3,?4,?5)",
		new Object[] {
						new Long(loanStateLate.getNomenclatorID()),						
						new Long(stateActive.getNomenclatorID()),
						new Long(typeSuspension.getNomenclatorID()),
						new Date(fecha1.getTime()),
						new Date(fecha1.getTime())});
		
		System.out.println("sancion por atraso insertada "); 		
		
	*/
	}
	
	//FIXME METODO COMPLEJO
	public java.util.Date sumar_dias(Date startDate,int dias, Long idLibrary)
	{
		//FIXME OIGRES QUE ES ESTOS
			java.util.Date fechaSalida = null;
			int temp = 0;
		
			List<Long> list = new ArrayList<>();
			
			java.util.Calendar calendario = java.util.Calendar.getInstance();
			calendario.setTime( startDate );
			
			int DiaActual=calendario.get(java.util.Calendar.DAY_OF_WEEK);
		   
			if (DiaActual == 1) {
				temp = 506;
			}
			if (DiaActual == 2) {
				temp = 507;
			}
			if (DiaActual == 3) {
				temp = 508;
			}
			if (DiaActual == 4) {
				temp = 509;
			}
			if (DiaActual == 5) {
				temp = 510;
			}
			if (DiaActual == 6) {
				temp = 511;
			}
			if (DiaActual == 7) {
				temp = 512;
			}
		
		  for (int i = 0; i < dias; i++) {			  
			  if(list.contains(temp))
			 {	  
				  calendario.add(java.util.Calendar.DATE,1 ); 
				  DiaActual=calendario.get(java.util.Calendar.DAY_OF_WEEK);
			      fechaSalida=calendario.getTime();
			 }
			 else 
			 {	

			      calendario.add(java.util.Calendar.DATE,3 ); 
			      fechaSalida=calendario.getTime();
			      DiaActual=2;
			 }
		} 
			
		  return fechaSalida;
	}

	@Override
	public Page<Transaction> findAllTransactionByRegisterReturn(String params,
			Library library,List<Room> listRoomWorker, int page, int size, int direction,
			String orderByString) {

		return transactionDAO.findAll(CirculationSpecification
				.searchTransactionByRegisterReturn(params,library,listRoomWorker), PageSpecification
				.getPage(page, size, direction, orderByString));
	}

	@Override
	public List<Transaction> searchTransactionsNotReturnLoanByLoanUser(
			LoanUser loanUser, Nomenclator stateLoan) {
		return transactionDAO.searchTransactionsNotReturnByLoanUser(loanUser, stateLoan);
	}
	


}
