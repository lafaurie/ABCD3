package cu.uci.abcd.opac.ui.tracker;

import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import cu.uci.abcd.opac.IOpacNomenclatorService;
import cu.uci.abos.api.util.ServiceListener;

public class NomenclatorTracker extends ServiceTracker<IOpacNomenclatorService, IOpacNomenclatorService> {
	ServiceListener<Object> nomenclatorServiceListener;
	IOpacNomenclatorService service;

	public NomenclatorTracker() {
		super(FrameworkUtil.getBundle(NomenclatorTracker.class).getBundleContext(), IOpacNomenclatorService.class, null);
	}

	@Override
	public IOpacNomenclatorService addingService(ServiceReference<IOpacNomenclatorService> reference) {
		service = super.addingService(reference);
		if (nomenclatorServiceListener != null) {
			nomenclatorServiceListener.addServiceListener(service);
		}
		return service;
	}

	public void setNomenclatorServiceListener(ServiceListener<Object> nomenclatorServiceListener) {
		this.nomenclatorServiceListener = nomenclatorServiceListener;
		if (service != null) {
			nomenclatorServiceListener.addServiceListener(service);
		}
	}
}