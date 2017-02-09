package cu.uci.abos.widgets.wizard.listeners;

import cu.uci.abos.widgets.wizard.IStep;

public interface IStepChangeListener {
	void handleEvent(IStep currentStep, IStep oldStep, boolean isLast);
}
