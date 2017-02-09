package cu.uci.abcd.springdata.test;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.dao.common.NomenclatorDAO;

@SuppressWarnings("rawtypes")
public abstract class DataTest<T extends PagingAndSortingRepository> extends AbstractEntryPoint {

	protected ServiceTracker<T, T> serviceTracker;
	protected T dao;
	protected NomenclatorDAO nomenclators;

	public DataTest() {
		super();
		ServiceTracker<NomenclatorDAO, NomenclatorDAO> stnomenclator = new ServiceTracker<>(FrameworkUtil.getBundle(NomenclatorDAO.class).getBundleContext(), NomenclatorDAO.class, null);
		stnomenclator.open();
		nomenclators = stnomenclator.getService();
		stnomenclator.close();
		getServiceTracker().open();
		dao = getServiceTracker().getService();
		getServiceTracker().close();

	}

	public void bind(T dao) {
		this.dao = dao;
	}

	public Iterable<?> testFinAll() {
		return dao.findAll();
	}

	public Page testFinAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	public long testCount() {
		return dao.count();
	}

	public T getDao() {
		return dao;
	}

	public void setDao(T dao) {
		this.dao = dao;
	}

	public abstract ServiceTracker<T, T> getServiceTracker();

}
