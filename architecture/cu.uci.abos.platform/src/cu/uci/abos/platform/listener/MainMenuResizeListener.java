package cu.uci.abos.platform.listener;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abos.core.util.AbosImageUtil;

public class MainMenuResizeListener implements Listener {

	private static final long serialVersionUID = 1L;

	public MainMenuResizeListener() {

	}

	@Override
	public void handleEvent(Event e) {
		Composite composite = ((Control) e.widget).getParent();
		FormData temporalData = (FormData) composite.getLayoutData();
		Control button = (Control) e.widget;
		if (temporalData.width == 230) {

			FormData data = (FormData) button.getLayoutData();
			data.left = new FormAttachment(5);
			data.right = null;
			button.setLayoutData(data);
            button.setSize(5, 5);
			Image image = AbosImageUtil.loadImage(null, Display.getCurrent(), "abcdconfig/resources/angle-double-right.png", false);
			button.setBackgroundImage(image);
			button.pack(true);
			button.redraw();
			button.update();
			composite.setLayoutData(temporalData);
			Composite temp = ((Control) e.widget).getParent();

			Control[] hijosControls = temp.getChildren();
			hijosControls[1].setVisible(false);
			temporalData.width = 10;
			composite.setSize(10, temporalData.height);
			composite.setLayoutData(temporalData);
			composite.computeSize(10, temporalData.height, true);
			composite.pack(true);
			composite.redraw();
			composite.update();
			composite.getShell().layout(true, true);
			composite.getShell().redraw();
			composite.getShell().update();
			
			

		} else {

			FormData data = (FormData) button.getLayoutData();
			data.left = new FormAttachment(composite, 220);
			button.setLayoutData(data);

			Image image = AbosImageUtil.loadImage(null, Display.getCurrent(), "abcdconfig/resources/angle-double-left.png", false);
			button.setBackgroundImage(image);

			temporalData.width = 230;
			composite.setLayoutData(temporalData);
			Composite temp = ((Button) e.widget).getParent();
			Control[] hijosControls = temp.getChildren();
			hijosControls[1].setVisible(true);
			FormData data2 = (FormData) temp.getLayoutData();
			Integer height = data2.height;
			composite.setSize(230, height);

			composite.getShell().layout(true, true);
			composite.getShell().redraw();
			composite.getShell().update();

		}

	}

}
