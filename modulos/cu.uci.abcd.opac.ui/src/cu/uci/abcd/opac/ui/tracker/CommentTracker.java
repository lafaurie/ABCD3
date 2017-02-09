package cu.uci.abcd.opac.ui.tracker;

import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import cu.uci.abcd.opac.IOpacCommentService;
import cu.uci.abos.api.util.ServiceListener;

public class CommentTracker extends ServiceTracker<IOpacCommentService, IOpacCommentService> {
	ServiceListener<Object> commentServiceListener;
	IOpacCommentService service;

	public CommentTracker() {
		super(FrameworkUtil.getBundle(CommentTracker.class).getBundleContext(), IOpacCommentService.class, null);
	}

	@Override
	public IOpacCommentService addingService(ServiceReference<IOpacCommentService> reference) {
		service = super.addingService(reference);
		if (commentServiceListener != null) {
			commentServiceListener.addServiceListener(service);
		}
		return service;
	}

	public void setCommentServiceListener(ServiceListener<Object> commentServiceListener) {
		this.commentServiceListener = commentServiceListener;
		if (service != null) {
			commentServiceListener.addServiceListener(service);
		}
	}

}
