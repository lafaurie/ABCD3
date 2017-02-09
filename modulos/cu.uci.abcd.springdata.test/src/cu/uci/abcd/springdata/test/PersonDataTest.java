package cu.uci.abcd.springdata.test;

import java.sql.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

import cu.uci.abcd.dao.common.PersonDAO;
import cu.uci.abcd.domain.circulation.LoanUserStudents;
import cu.uci.abcd.domain.common.Nomenclator;

public class PersonDataTest extends DataTest<PersonDAO> {
	
	private Label resultado;

	@Override
	public ServiceTracker<PersonDAO, PersonDAO> getServiceTracker() {
		if (serviceTracker == null) {
			serviceTracker = new ServiceTracker<PersonDAO, PersonDAO>(FrameworkUtil.getBundle(PersonDAO.class).getBundleContext(), PersonDAO.class, null);
		}
		return serviceTracker;
	}

	@Override
	protected void createContents(Composite shell) {
		resultado = new Label(shell, SWT.NONE);
		resultado.setBounds(50, 50, 400, 400);
		resultado .setText(" I Count -> "+dao.count()+"II finOne  1L" + dao.findOne(1L).toString() + "II finOne  2L" + dao.findOne(2L).toString() +"II finOne  3L" + dao.findOne(3L).toString());
	testInsert();
	}

	private void testInsert() {
		LoanUserStudents s = new LoanUserStudents();
		s.setAddress("Adress");
		s.setBirthDate(new Date(2000, 10, 1));
		s.setDNI("00100123445");
		s.setFirstName("MiFirtNAme");
		s.setRemarks("Observtionss");
		s.setSecondName("secondName");
		s.setFirStsurname("firstSurname");
		s.setSex(nomenclators.findOne(28L));
		s.setRegistrationDate(new Date(2014, 10, 1));
		s.setLibrary(dao.findOne(1l).getLibrary());
		s.setEmailAddress("ss@s.ds");
		s.setLoanUserCode("3455");
		s.setExpirationDate(new Date(2044, 10, 1));
		s.setLoanUserState(nomenclators.findOne(1L));
		s.setUniversity("gggg");
		s.setFaculty(nomenclators.findOne(1L));
		s.setDepartment(nomenclators.findOne(1L));
		s.setRegisteredatroom(nomenclators.findOne(1L));
		s.setLoanUserType(nomenclators.findOne(22L));
		dao.save(s);
		
	}


	


}
