package cu.uci.abcd.opac.ui.tracker;

import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.opac.IOpacLibraryService;
import cu.uci.abos.api.util.ServiceListener;

public class LibraryTracker  extends ServiceTracker<IOpacLibraryService, IOpacLibraryService> {
	ServiceListener<Object> libraryServiceListener;
	IOpacLibraryService service;

	public LibraryTracker() {
		super(FrameworkUtil.getBundle(Library.class).getBundleContext(), IOpacLibraryService.class, null);
	}

	@Override
	public IOpacLibraryService addingService(ServiceReference<IOpacLibraryService> reference) {
		service = super.addingService(reference);
		if (libraryServiceListener != null) {
			libraryServiceListener.addServiceListener(service);
		}
		return service;
	}

	public void setLibraryService(ServiceListener<Object> libraryServiceListener) {
		this.libraryServiceListener = libraryServiceListener;
		if (service != null) {
			libraryServiceListener.addServiceListener(service);
		}
	}   
}