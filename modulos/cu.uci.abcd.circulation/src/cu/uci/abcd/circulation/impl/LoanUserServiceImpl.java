package cu.uci.abcd.circulation.impl;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import cu.uci.abcd.circulation.ILoanUserService;
import cu.uci.abcd.dao.circulation.LoanUserDAO;
import cu.uci.abcd.dao.common.NomenclatorDAO;
import cu.uci.abcd.dao.common.StatementExecutorDAO;
import cu.uci.abcd.dao.common.UserDAO;
import cu.uci.abcd.dao.management.library.RoomDAO;
import cu.uci.abcd.dao.specification.CirculationSpecification;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.domain.circulation.LoanUser;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;

/**
 * @see ILoanUserService
 */
public class LoanUserServiceImpl implements ILoanUserService {

	private LoanUserDAO loanUserDAO;
	private NomenclatorDAO nomenclatorDAO;
	private UserDAO userDAO;
	private RoomDAO roomDAO;
	private StatementExecutorDAO executorDAO;

	@Override
	public LoanUser addLoanUser(LoanUser loanUser) {
		return loanUserDAO.save(loanUser);
	}

	@Override
	public void deleteLoanUser(Long idLoanUser) {
		loanUserDAO.delete(idLoanUser);
	}

	@Override
	public LoanUser findOneLoanUser(Long idLoanUser) {
		return (LoanUser) loanUserDAO.findOne(idLoanUser);
	}

	@Override
	public Nomenclator findByID(Long nomenclatorID) {
		return nomenclatorDAO.findByNomenclatorID(nomenclatorID);
	}

	@Override
	public User findUserByPersonID(Long personID) {
		return userDAO.findUserByPerson_PersonID(personID);
	}

	
	@Override
	public List<Nomenclator> findByNomenclator(Long idLibrary, Long code) {
		return nomenclatorDAO.findNomenclatorsByLibraryOrLibraryNullAndParent(idLibrary, code);
	}
	
	@Override
	public List<LoanUser> findAllLoanUserList() {
		return (List<LoanUser>) loanUserDAO.findAll();
	}

	@Override
	public LoanUser findByLoanUserCode(String code) {
		return loanUserDAO.findByLoanUserCode(code);
	}

	@Override
	public LoanUser findLoanUserByPersonIDAndState(Long personID, Long nomenclatorID) {
		return loanUserDAO.findByPerson_PersonIDAndLoanUserState_NomenclatorID(personID, nomenclatorID);
	}

	@Override
	public Page<LoanUser> findLoanUserConsult(String loanUserCode,
			Room roomUser, Nomenclator loanUserType, Nomenclator loanUserState,
			Nomenclator faculty, Nomenclator career,
			String firstName, String secondName, String firstLastName,
			String secondLastName, Date fromDate, Date toDate, String DNI,Library library,
			int page, int size, int direction, String order) {
		return loanUserDAO.findAll(CirculationSpecification
				.searchLoanUserConsult(loanUserCode, roomUser, loanUserType,
						loanUserState,faculty,career, firstName, secondName, firstLastName,
						secondLastName, fromDate, toDate, DNI,library),
				PageSpecification.getPage(page, size, direction, order));
	}

	@Override
	public Page<LoanUser> findLoanUserFragment(String params, Library library,int page,
			int size, int direction, String order) {
		return loanUserDAO.findAll(
				CirculationSpecification.searchLoanUserFragment(params,library),
				PageSpecification.getPage(page, size, direction, order));
	}

	// ----------------------bind
	public void bindLoanUserDao(LoanUserDAO loanUserDAO, Map<?, ?> properties) {
		this.loanUserDAO = loanUserDAO;
	}

	public void bindNomenclatorDao(NomenclatorDAO nomenclatorDAO, Map<?, ?> properties) {
		this.nomenclatorDAO = nomenclatorDAO;
	}

	public void bindUserDao(UserDAO userDao, Map<?, ?> properties) {
		this.userDAO = userDao;
	}

	public void bindRoomDao(RoomDAO roomDao, Map<?, ?> properties) {
		this.roomDAO = roomDao;
	}
	
	public void bindStatementExecutorDao(StatementExecutorDAO executorDAO, Map<?, ?> properties) {
		this.executorDAO = executorDAO;
	}

	public void updateStateLoanUser() {
		
		java.util.Date fecha = new java.util.Date();
	
		Nomenclator loanUserStateActive = nomenclatorDAO.findOne(Nomenclator.LOANUSER_STATE_ACTIVE);
		Nomenclator loanUserStateInactive = nomenclatorDAO.findOne(Nomenclator.LOANUSER_STATE_INACTIVE);
		Nomenclator stateRenew = nomenclatorDAO.findOne(Nomenclator.LOAN_STATE_RENEW);
		Nomenclator stateLoan = nomenclatorDAO.findOne(Nomenclator.LOAN_STATE_BORROWED);
		Nomenclator stateLate = nomenclatorDAO.findOne(Nomenclator.LOAN_STATE_LATE);
		
		 java.util.Date d = new java.util.Date();
         Date a = new Date(d.getTime() - 899999999 );
         Date b = new Date(a.getTime() - 899999999 );
         Date c = new Date(b.getTime() - 899999999 );
         
         int fromYear = Integer.parseInt(new SimpleDateFormat("yyyy")
                         .format(c));
         int fromMonth = Integer.parseInt(new SimpleDateFormat("MM")
                         .format(c));
         int fromDay = Integer.parseInt(new SimpleDateFormat("dd")
                         .format(c));
         @SuppressWarnings("deprecation")
		Date dateExpiration = new Date(fromYear, fromMonth, fromDay);
     		
		System.out.println("por ejecutar"); 
		
		executorDAO.executeUpdate(
						"update abcdn.dloanuser set disabilitationremarks = 'Usuario Inactivo porque expiro su tiempo estimado', state = ?1 where id not in (select loanuser from abcdn.dtransactions where loanstate = ?2 or loanstate = ?3 or loanstate = ?4 and isparent = false) and expirationdate < ?5 and state = ?6 ",
					new Object[] {
								new Long(loanUserStateInactive.getNomenclatorID()),
								new Long(stateLoan.getNomenclatorID()),
								new Long(stateRenew.getNomenclatorID()),
								new Long(stateLate.getNomenclatorID()),
								new Date(fecha.getTime()),
								new Long(loanUserStateActive.getNomenclatorID())});
		
		System.out.println("ejecutado1"); 
		
		executorDAO
		.executeUpdate(
				"update abcdn.dloanuser set expirationdate = ?1 where id in (select loanuser from abcdn.dtransactions where loanstate = ?2 or loanstate = ?3 or loanstate = ?4 and isparent = false) and expirationdate < ?5 and state = ?6",
				new Object[] {
						new Date(dateExpiration.getTime()),
						new Long(stateLoan.getNomenclatorID()),
						new Long(stateRenew.getNomenclatorID()),
						new Long(stateLate.getNomenclatorID()),
						new Date(fecha.getTime()),
						new Long(loanUserStateActive.getNomenclatorID())});

		System.out.println("ejecutado2"); 
		
	}

	@Override
	public List<Room> findRoomByLibrary(Long libraryID) {
		return roomDAO.findDistinctRoomByLibrary_LibraryID(libraryID);
	}

	@Override
	public Page<LoanUser> findLoanUserFragmentInterLibrarian(String params,
			Library library, int page, int size, int direction, String order) {
		return loanUserDAO.findAll(CirculationSpecification
				.searchLoanUserTypeInterLibrarianFragment(params,library),
				PageSpecification.getPage(page, size, direction, order));

	}

	@Override
	public Page<LoanUser> findLoanUserFragmentOtherType(String params,Library library,
			int page, int size, int direction, String order) {
		return loanUserDAO.findAll(CirculationSpecification
				.searchLoanUserOtherTypeFragment(params,library), PageSpecification
				.getPage(page, size, direction, order));

	}

	@Override
	public List<Nomenclator> findByNomenclators(Long idLibrary, Long code1,
			Long code2) {
		return nomenclatorDAO.findNomenclatorsByLibraryOrLibraryNullAndParents(idLibrary, code1, code2);
	}

}
