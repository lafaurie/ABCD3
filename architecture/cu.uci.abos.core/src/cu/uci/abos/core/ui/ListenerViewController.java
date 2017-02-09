package cu.uci.abos.core.ui;

import org.eclipse.swt.widgets.Listener;

import cu.uci.abos.api.ui.ViewController;

public interface ListenerViewController extends ViewController {

	public Listener buildListener(Class<? extends Listener > clazz);
	
}
