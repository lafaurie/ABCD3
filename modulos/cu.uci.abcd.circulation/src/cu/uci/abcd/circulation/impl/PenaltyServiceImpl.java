package cu.uci.abcd.circulation.impl;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import cu.uci.abcd.circulation.IPenaltyService;
import cu.uci.abcd.dao.circulation.PenaltyDAO;
import cu.uci.abcd.dao.common.NomenclatorDAO;
import cu.uci.abcd.dao.common.StatementExecutorDAO;
import cu.uci.abcd.dao.management.library.FineEquationDAO;
import cu.uci.abcd.dao.specification.CirculationSpecification;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.domain.circulation.LoanUser;
import cu.uci.abcd.domain.circulation.Penalty;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.FineEquation;
import cu.uci.abcd.domain.management.library.Library;

public class PenaltyServiceImpl implements IPenaltyService {

	private PenaltyDAO penaltyDAO;
	private FineEquationDAO fineEquationDAO;
	private NomenclatorDAO nomenclatorDAO;
	private StatementExecutorDAO executorDAO;

	@Override
	public Penalty addPenalty(Penalty penalty) {
		return penaltyDAO.save(penalty);
	}

	@Override
	public void deletePenalty(Long idPenalty) {
		penaltyDAO.delete(idPenalty);
	}

	@Override
	public Penalty findOnePenalty(Long idPenalty) {
		return penaltyDAO.findOne(idPenalty);
	}

	@Override
	public FineEquation findFineEquationByLibrary(Long idLibrary) {
		return fineEquationDAO.findDistinctFineEquationByLibrary_LibraryID(idLibrary);
	}

	public void bindPenaltyDao(PenaltyDAO penaltyDAO, Map<?, ?> properties) {
		this.penaltyDAO = penaltyDAO;
	}

	public void bindFineEquationDao(FineEquationDAO fineEquationDAO, Map<?, ?> properties) {
		this.fineEquationDAO = fineEquationDAO;
	}

	public void bindNomenclatorDao(NomenclatorDAO nomenclatorDAO, Map<?, ?> properties) {
		this.nomenclatorDAO = nomenclatorDAO;
	}

	public void bindStatementExecutorDao(StatementExecutorDAO executorDAO, Map<?, ?> properties) {
		this.executorDAO = executorDAO;
	}

	@Override
	public Page<Penalty> findAllPenaltyConsult(Nomenclator penalty_type,
			Nomenclator penalty_state, Nomenclator loan_user_type_id,
			String loan_user_code, String firstName, String secondName,
			String firstLast, String secondLast, Date fromDate, Date toDate,
			String title, String author, String control_number,Library library, int page,
			int size, int direction, String orderByString) {

		return penaltyDAO.findAll(CirculationSpecification
				.searchPenaltyConsult(penalty_type, penalty_state,
						loan_user_type_id, loan_user_code, firstName,
						secondName, firstLast, secondLast, fromDate, toDate,
						title, author, control_number,library), PageSpecification
				.getPage(page, size, direction, orderByString));
	}

	@Override
	public Page<Penalty> findAllPenaltyByLoanUserCurrent(Long loanUserID, int page, int size, int direction, String orderByString) {
		return penaltyDAO.findAll(CirculationSpecification.searchPenaltyByLoanUserCurrent(loanUserID), PageSpecification.getPage(page, size, direction, orderByString));
	}

	@Override
	public Page<Penalty> findAllPenaltyByLoanUserHistory(Long loanUserID, int page, int size, int direction, String orderByString) {
		return penaltyDAO.findAll(CirculationSpecification.searchPenaltyByLoanUserHistory(loanUserID), PageSpecification.getPage(page, size, direction, orderByString));
	}

	@Override
	public Page<Penalty> findAllPenaltyByLoanObject(Long loanObjectID, int page, int size, int direction, String orderByString) {
		return penaltyDAO.findAll(CirculationSpecification.searchPenaltyByLoanObject(loanObjectID), PageSpecification.getPage(page, size, direction, orderByString));
	}

	@Override
	public List<Penalty> findPenaltyByLoanUserIdAndPenaltyTypeAndPenaltyState(Long loanUserID, Nomenclator penaltyType, Nomenclator penaltyState) {
		return penaltyDAO.findDistinctPenaltyByLoanUser_Person_PersonIDAndPenaltyTypeAndPenaltyState(loanUserID, penaltyType, penaltyState);
	}

	@Override
	public void updateStatePenaltyTypeSuspension() {

		java.util.Date fecha = new java.util.Date();
		Nomenclator penaltyState = nomenclatorDAO.findOne(Nomenclator.PENALTY_STATE_ACTIVE);
		Nomenclator penaltyStateInactive = nomenclatorDAO.findOne(Nomenclator.PENALTY_STATE_INACTIVE);

		executorDAO.executeUpdate("update abcdn.dpenalty set state = ?1 where expirationdate < ?2 and state = ?3", 
				new Object[] { new Long(penaltyStateInactive.getNomenclatorID()), 
				new Date(fecha.getTime()), 
				new Long(penaltyState.getNomenclatorID())});
	}

	@Override
	public List<Penalty> searchPenaltyByIDLoanUser(Long idLoanUser) {
		return penaltyDAO.findDistinctPenaltyByLoanUser_Person_PersonID(idLoanUser);
	}

	@Override
	public Penalty searchPenaltyByLoanUserAndLoanObject(
			Nomenclator penaltyState, LoanUser loanUser, LoanObject loanObject) {
		return penaltyDAO.searchPenaltyByLoanUserAndLoanObject(penaltyState, loanUser, loanObject);
	}
}
