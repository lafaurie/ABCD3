package cu.uci.abcd.springdata.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

import cu.uci.abcd.dao.circulation.LoanUserDAO;

public class LoanUserDataTest extends DataTest<LoanUserDAO> {
	
	private Label resultado;

	@Override
	public ServiceTracker<LoanUserDAO, LoanUserDAO> getServiceTracker() {
		if (serviceTracker == null) {
			serviceTracker = new ServiceTracker<LoanUserDAO, LoanUserDAO>(FrameworkUtil.getBundle(LoanUserDAO.class).getBundleContext(), LoanUserDAO.class, null);
		}
		return serviceTracker;
	}

	@Override
	protected void createContents(Composite shell) {
		resultado = new Label(shell, SWT.NONE);
		resultado.setBounds(50, 50, 400, 400);
		resultado .setText(" I Count -> "+dao.count()+"II finOne " + dao.findOne(3L).getFirstName());
	
	}


	


}
