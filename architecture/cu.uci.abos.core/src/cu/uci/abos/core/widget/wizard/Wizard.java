package cu.uci.abos.core.widget.wizard;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.widget.wizard.listener.ICancelListener;
import cu.uci.abos.core.widget.wizard.listener.IStepChangeListener;

@SuppressWarnings("serial")
public class Wizard extends Composite {

	private List<IStep> steps;
	private int currentStepIndex;
	private IStep currentStep;
	private Composite clientArea;
	private Composite editionArea;
	private Composite buttonsArea;
	private List<IStepChangeListener> stepChangeListeners;
	private List<ICancelListener> cancelListeners;
	private List<Button> buttons;
	private Button cancelBtn;
	private Button beforeBtn;
	private Button nextBtn;
	private Button finishBtn;
	private Map<String, Object> sharedData;
	private String cancelBtnText;
	private String beforeBtnText;
	private String nextBtnText;
	private String finishBtnText;

	public Wizard(Composite parent, int style) {
		super(parent, style);
		this.steps = new LinkedList<IStep>();
		this.currentStepIndex = -1;
		this.stepChangeListeners = new LinkedList<IStepChangeListener>();
		this.cancelListeners = new LinkedList<ICancelListener>();
		this.buttons = new LinkedList<Button>();
		this.sharedData = new HashMap<String, Object>();
		this.cancelBtnText = "";
		this.beforeBtnText = "";
		this.nextBtnText = "";
		this.finishBtnText = "";
		this.forwardResizedEvent();
	}

	public void createUI() {
		this.setLayout(new FormLayout());
		this.clientArea = new Composite(this, SWT.NONE);
		this.clientArea.setLayout(new FormLayout());
		FormDatas.attach(this.clientArea).atLeft(0).atRight(0);
		this.renderStep(this.currentStepIndex);
	}

	private void renderButtons() {

		if (this.buttonsArea != null) {
			this.buttonsArea.dispose();
		}

		this.buttonsArea = new Composite(this.clientArea, SWT.NONE);
		FormDatas.attach(buttonsArea).atTopTo(editionArea).atLeft(0).atRight(0);
		this.buttonsArea.setLayout(new FormLayout());

		for (Button btn : this.buttons) {
			btn.dispose();
		}

		cancelBtn = new Button(this.buttonsArea, SWT.PUSH);
		FormDatas.attach(cancelBtn).atTop(5).atLeft(5);

		cancelBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent event) {
				cancel();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
				// do nothing
			}
		});

		this.buttons.add(cancelBtn);

		beforeBtn = new Button(this.buttonsArea, SWT.PUSH);
		FormDatas.attach(beforeBtn).atTop(5).atLeftTo(cancelBtn, 5);

		if (this.currentStepIndex == 0) {
			beforeBtn.setEnabled(false);
		}

		beforeBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent event) {
				before();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
				// do nothing
			}
		});

		this.buttons.add(beforeBtn);

		if (this.currentStepIndex < this.steps.size() - 1) {
			nextBtn = new Button(this.buttonsArea, SWT.PUSH);
			FormDatas.attach(nextBtn).atTop(5).atLeftTo(beforeBtn, 5);

			nextBtn.addSelectionListener(new SelectionListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void widgetSelected(SelectionEvent event) {
					next();
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent event) {
					// do nothing
				}
			});
			this.buttons.add(nextBtn);
		} else {
			finishBtn = new Button(this.buttonsArea, SWT.PUSH);
			FormDatas.attach(finishBtn).atTop(5).atLeftTo(beforeBtn, 5);

			finishBtn.addSelectionListener(new SelectionListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void widgetSelected(SelectionEvent event) {
					finish();
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent event) {
					// do nothing
				}
			});
			this.buttons.add(finishBtn);
		}

		this.buttonsArea.pack();
		this.buttonsArea.getShell().layout(true, true);
		this.buttonsArea.getShell().redraw();
		this.buttonsArea.getShell().update();
	}

	private IStep renderStep(int index) {
		if (index >= 0 && index < this.steps.size()) {
			this.currentStepIndex = index;
			IStep oldStep = this.currentStep;
			this.currentStep = this.steps.get(index);
			boolean isLast = (this.steps.size() - 1) == index;
			this.fireStepChangeListeners(oldStep, this.currentStep, isLast);
			if (this.editionArea != null) {
				this.editionArea.dispose();
			}
			this.editionArea = new Composite(this.clientArea, SWT.NONE);
			this.editionArea.setLayout(new FormLayout());
			FormDatas.attach(editionArea).atTop(0).atLeft(0).atRight(0);
			((BaseStep) oldStep).destroyUI();
			this.currentStep.createUI(this.editionArea);
			((BaseStep) this.currentStep).loadData();
			this.renderButtons();
			this.l10n();
			this.refresh();
		}
		return this.currentStep;
	}

	private void fireStepChangeListeners(IStep currentStep, IStep oldStep, boolean isLast) {
		for (IStepChangeListener listener : this.stepChangeListeners) {
			listener.handleEvent(currentStep, oldStep, isLast);
		}
	}

	public void addStepChangeListener(IStepChangeListener listener) {
		this.stepChangeListeners.add(listener);
	}

	public void refresh() {
		this.clientArea.getShell().layout(true, true);
		this.clientArea.redraw();
		this.clientArea.update();
	}

	public void addStep(IStep step) {
		if (this.currentStep == null) {
			this.currentStep = step;
			this.currentStepIndex = 0;
		}
		this.steps.add(step);
	}

	public IStep next() {
		if (this.currentStep.isValid()) {
			return this.renderStep(this.currentStepIndex + 1);
		}
		return this.currentStep;
	}

	public IStep before() {
		return this.renderStep(this.currentStepIndex - 1);
	}

	public void finish() {
		// TODO: to implement.
	}

	public void cancel() {
		this.fireCancelListeners();
	}

	public List<ControlData> getAllControlsData() {
		List<ControlData> result = new LinkedList<ControlData>();
		for (IStep step : this.steps) {
			result.addAll(((BaseStep) step).getControlsData());
		}
		return result;
	}

	public void addCancelListener(ICancelListener listener) {
		this.cancelListeners.add(listener);
	}

	private void fireCancelListeners() {
		for (ICancelListener listener : this.cancelListeners) {
			listener.handleEvent();
		}
	}

	public void setData(String key, Object value) {
		this.sharedData.put(key, value);
	}

	public Object getData(String key) {
		if (this.sharedData.containsKey(key)) {
			return this.sharedData.get(key);
		}
		return null;
	}

	public void setCancelBtnText(String text) {
		this.cancelBtnText = text;
	}

	public void setBeforeBtnText(String text) {
		this.beforeBtnText = text;
	}

	public void setNextBtnText(String text) {
		this.nextBtnText = text;
	}

	public void setFinishBtnText(String text) {
		this.finishBtnText = text;
	}

	public void l10n() {
		if (this.cancelBtn != null && !this.cancelBtn.isDisposed()) {
			this.cancelBtn.setText(this.cancelBtnText);
		}
		if (this.beforeBtn != null && !this.beforeBtn.isDisposed()) {
			this.beforeBtn.setText(this.beforeBtnText);
		}
		if (this.nextBtn != null && !this.nextBtn.isDisposed()) {
			this.nextBtn.setText(this.nextBtnText);
		}
		if (this.finishBtn != null && !this.finishBtn.isDisposed()) {
			this.finishBtn.setText(this.finishBtnText);
		}
		this.currentStep.l10n();
	}

	private void forwardResizedEvent() {
		this.addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (Wizard.this.getParent() != null) {
					Wizard.this.getParent().notifyListeners(SWT.Resize, event);
				}
			}
		});
	}
}
