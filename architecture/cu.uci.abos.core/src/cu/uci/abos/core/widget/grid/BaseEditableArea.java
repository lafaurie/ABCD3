package cu.uci.abos.core.widget.grid;

import java.util.Collection;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.events.EventUtil;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.api.util.ServiceProvider;
import cu.uci.abos.core.event.EventTable;
import cu.uci.abos.core.ui.FormPagePainter;
import cu.uci.abos.core.ui.PagePainter;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;

public abstract class BaseEditableArea implements IEditableArea {

	protected ValidatorUtils validator;
	protected ViewController controller;
	private PagePainter painter;
	private EventTable eventTable;
	private Composite message;

	@Override
	public void initialize() {
		validator = new ValidatorUtils(new CustomControlDecoration());
	}

	public BaseEditableArea() {
		super();
		painter = new FormPagePainter();
		
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

	public void showErrorMessage(String message) {
			RetroalimentationUtils.showErrorShellMessage(message);
	}

	public void showInformationMessage(String message) {
			RetroalimentationUtils.showInformationShellMessage(message);
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

	public void applyValidatorWithLength(Control control, DecoratorType type, boolean visible, int maxLength) {
		validator.applyValidator(control, control.toString() + type.toString(), type, visible, maxLength);
	}

	public BaseEditableArea setDimension(Integer dimension) {
		painter.setDimension(dimension);
		return this;
	}

	public void initialize(Control control, Collection<?> items) {
		UiUtils.initialize(control, items);
	}

	public void refresh() {
		BundleContext bc = FrameworkUtil.getBundle(BaseEditableArea.class).getBundleContext();
		ServiceReference<ServiceProvider> ref = bc.getServiceReference(ServiceProvider.class);
		bc.getService(ref).get(ContributorService.class).refresh();
	}

	public void refresh(Control control) {
		control.getParent().layout(true, true);
	}

	public void br() {
		painter.reset();
	}

	public void setViewController(ViewController controller) {
		this.controller = controller;
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

	@Override
	public Composite createButtons(Composite parent, IGridViewEntity entity, IVisualEntityManager manager) {
		return parent;
	}

	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public boolean closable() {
		return true;
	}

	public String getID() {
		return "";
	}

	public Composite getMessage() {
		return message;
	}

	public void buildMessage(Composite parent) {
		this.message = new Composite(parent, SWT.NONE);
		message.setLayout(new FormLayout());
		message.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(message).atTopTo(parent).withWidth(320).withHeight(50).atRight(0);
	}
	
	public boolean validate(){
		return validator.decorationFactory.AllControlDecorationsHide();
	}
	
	public boolean sucess(){
		return true;
	}
	
}
