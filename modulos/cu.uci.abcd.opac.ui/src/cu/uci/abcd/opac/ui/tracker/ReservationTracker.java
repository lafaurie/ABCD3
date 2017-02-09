package cu.uci.abcd.opac.ui.tracker;

import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import cu.uci.abcd.opac.IOpacReservationService;
import cu.uci.abos.api.util.ServiceListener;

public class ReservationTracker extends ServiceTracker<IOpacReservationService, IOpacReservationService> {
	ServiceListener<Object> reservationServiceListener;
	IOpacReservationService service;

	public ReservationTracker() {
		super(FrameworkUtil.getBundle(ReservationTracker.class).getBundleContext(), IOpacReservationService.class, null);
	}

	@Override
	public IOpacReservationService addingService(ServiceReference<IOpacReservationService> reference) {
		service = super.addingService(reference);
		if (reservationServiceListener != null) {
			reservationServiceListener.addServiceListener(service);
		}
		return service;
	}

	public void setReservationServiceListener(ServiceListener<Object> reservationServiceListener) {
		this.reservationServiceListener = reservationServiceListener;
		if (service != null) {
			reservationServiceListener.addServiceListener(service);
		}
	}
}