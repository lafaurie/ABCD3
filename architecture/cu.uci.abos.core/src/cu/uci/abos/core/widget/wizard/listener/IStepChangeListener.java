package cu.uci.abos.core.widget.wizard.listener;

import cu.uci.abos.core.widget.wizard.IStep;

public interface IStepChangeListener {
	void handleEvent(IStep currentStep, IStep oldStep, boolean isLast);
}
