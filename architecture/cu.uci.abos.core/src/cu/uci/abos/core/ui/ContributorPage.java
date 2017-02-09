package cu.uci.abos.core.ui;

import java.util.Collection;
import java.util.Map;

import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.events.EventUtil;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.event.EventTable;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;

/**
 * 
 * @author sergio
 * 
 */
public abstract class ContributorPage implements Contributor {
	
	public static final String NOT_SCROLLED ="NOT_SCROLLED";

	private PagePainter painter;

	private ValidatorUtils validator;

	private EventTable eventTable;

	protected ViewController controller;
	
	protected Map<String, Object> properties;

	private ContributorService contributorService;

	private Composite parent;

	private int heigth = 100;

	public int getHeigth() {
		return heigth;
	}

	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}

	public ContributorPage() {
		super();
		painter = new FormPagePainter();
		validator = new ValidatorUtils(new CustomControlDecoration());
	}

	public PagePainter getPainter() {
		return painter;
	}

	public void setPainter(PagePainter painter) {
		this.painter = painter;
	}

	@Override
	public String getID() {
		return this.getClass().getName() + "ID";
	}

	@Override
	public boolean canClose() {
		return true;
	}

	public void addComposite(Composite composite, Percent percent) {
		painter.addComposite(composite, percent);
	}

	public void addComposite(Composite composite) {
		painter.addComposite(composite);
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

	public void add(Control control, DecoratorType type) {
		painter.add(control);
		validator.applyValidator(control, control.toString() + type.toString(), type, true);
	}

	public void add(Control control, Percent percent) {
		painter.add(control, percent);
	}

	public void applyValidator(Control control, DecoratorType type, boolean visible, int maxLength) {
		validator.applyValidator(control, control.toString() + type.toString(), type, visible, maxLength);
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
		final Rectangle r = parent.getClientArea();
		parent.setSize(parent.computeSize(r.width, SWT.DEFAULT));
		parent.layout(true, true);
	}

	public void refresh(Control control) {
		final Rectangle r = control.getParent().getClientArea();
		control.getParent().setSize(control.getParent().computeSize(r.width, SWT.DEFAULT));
		control.getParent().layout();
	}

	public void br() {
		painter.reset();
	}

	@Override
	public void setViewController(ViewController controller) {
		this.controller = controller;
	}

	public void insertComposite(Composite composite, Composite top) {
		painter.insertComposite(composite, top);
	}

	@Override
	public void l10n() {

	}

	@Override
	public String contributorName() {
		return getClass().getName();
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

	public void setDimension(Integer dimension) {
		painter.setDimension(dimension);
	}

	public void setContributorService(ContributorService contributorService) {
		this.contributorService = contributorService;
	}

	public int getDimension() {
		return ((FormPagePainter) painter).getDimension();
	}

	public void navigate(String contributorID) {
		contributorService.selectContributor(contributorID);
	}
	
	public ViewController getController() {
		return controller;
	}

	public void navigate(String contributorID, Object ...parameters) {
		contributorService.selectContributor(contributorID,parameters);
	}

	public ContributorService getContributorService() {
		return contributorService;
	}

	public void setParameters(Object... parameters) {

	}

	public Control build(Composite parent) {
		this.parent = parent;
		return createUIControl(parent);
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	
}
