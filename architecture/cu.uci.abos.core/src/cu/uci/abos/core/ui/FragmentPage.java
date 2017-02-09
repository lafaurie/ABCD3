package cu.uci.abos.core.ui;

import java.util.Collection;

import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.internal.events.EventUtil;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import cu.uci.abos.api.ui.AbstractFragmentContributor;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.util.ServiceProvider;
import cu.uci.abos.core.event.EventTable;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;

public class FragmentPage extends AbstractFragmentContributor {
	private PagePainter painter;
	private ValidatorUtils validator;
	private EventTable eventTable;
	private ContributorService contributorService;
	
	public void applyValidator(Control control, String key, DecoratorType type, boolean visible, int maxLength) {
		validator.applyValidator(control, key, type, visible, maxLength);
	}


	public void applyValidator(Control control, String key, DecoratorType type, boolean visible) {
		validator.applyValidator(control, key, type, visible);
	}


	public FragmentPage() {
		super();
		painter = new FormPagePainter();
		validator = new ValidatorUtils(new CustomControlDecoration());
	}


	public void addComposite(Composite composite, Percent percent) {
		painter.addComposite(composite, percent);
	}


	public void addComposite(Composite composite) {
		painter.addComposite(composite);
	}


	public void insertComposite(Composite composite, Composite top) {
		painter.insertComposite(composite, top);
	}


	public void addHeader(Label header) {
		painter.addHeader(header);
	}


	public void addSeparator(Label separator) {
		painter.addSeparator(separator);
	}


	public void add(Control control) {
		painter.add(control);
	}


	public void add(Control control, Percent percent) {
		painter.add(control, percent);
	}


	public void dispose() {
		if (this.controlMap!=null) {
			this.controlMap.clear();	
		}
		painter.dispose();
	}


	public void reset() {
		painter.reset();
	}


	public void setDimension(Integer dimension) {
		painter.setDimension(dimension);
	}
	
	public void showErrorMessage(String message) {
		RetroalimentationUtils.showErrorMessage(message);
	}

	public void showInformationMessage(String message) {
		RetroalimentationUtils.showInformationMessage(message);
	}

	public void showWarningMessage(String message) {
		RetroalimentationUtils.showWarningMessage(message);
	}

	public void showQuestionMessage(String message, DialogCallback callback) {
		RetroalimentationUtils.showQuestionMessage(message, callback);
	}

	public void showConfirmationMessage(String message, DialogCallback callback) {
		RetroalimentationUtils.showConfirmationMessage(message, callback);
	}
	
	public void applyValidator(Control control, DecoratorType type, boolean visible) {
		validator.applyValidator(control, control.toString() + type.toString(), type, visible);
	}

	public void initialize(Control control, Collection<?> items) {
		UiUtils.initialize(control, items);
	}

	public void refresh() {
		if (contributorService!=null) {
			contributorService.refresh();
		}else{
			BundleContext bc = FrameworkUtil.getBundle(FragmentPage.class).getBundleContext();
			ServiceReference<ServiceProvider> ref = bc.getServiceReference(ServiceProvider.class);
			bc.getService(ref).get(ContributorService.class).refresh();
			
		}
	}

	public void refresh(Control control) {
		control.getParent().layout(true, true);
	}
	

	public void addListener(int eventType, Listener listener) {
		if (listener != null) {
			getEventTable().hook(eventType, listener);
		}
	}

	public void removeListener(int eventType, Listener listener) {
		if (listener != null) {
			if (eventTable != null) {
				eventTable.unhook(eventType, listener);
			}
		}
	}

	public void notifyListeners(int eventType, Event event) {
		if (event.time == 0) {
			event.time = EventUtil.getLastEventTime();
			event.type = eventType;
		}
		sendEvent(event);
	}

	private EventTable getEventTable() {
		if (eventTable == null) {
			eventTable = new EventTable();
		}
		return eventTable;
	}

	private void sendEvent(Event event) {
		if (eventTable != null) {
			eventTable.sendEvent(event);
		}
	}


	public void setContributorService(ContributorService contributorService) {
		this.contributorService = contributorService;
	}
	
	public ContributorService getContributorService() {
		return contributorService;
	}



	@Override
	public Control createUIControl(Composite parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void l10n() {
		// TODO Auto-generated method stub
		
	}


}
