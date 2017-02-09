package cu.uci.abos.core.widget.grid;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;

public class ButtonData {

	private Image icon;
	private SelectionListener listener;

	public ButtonData(Image icon, SelectionListener listener) {
		this.icon = icon;
		this.listener = listener;
	}

	public ButtonData(String resourceKey, Device device, SelectionListener listener) {
		this.icon = new Image(device, RWT.getResourceManager().getRegisteredContent(resourceKey));
		this.listener = listener;
	}

	public Image getIcon() {
		return icon;
	}

	public void setIconName(Image icon) {
		this.icon = icon;
	}

	public SelectionListener getListener() {
		return listener;
	}

	public void setListener(SelectionListener listener) {
		this.listener = listener;
	}
}
