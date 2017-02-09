package cu.uci.abos.widget.template.listener;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.widget.template.component.TemplateCompound;

public class EventChangeWorkSheet implements Listener {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private static final long serialVersionUID = 1L;

	private Combo combo;
	private TemplateCompound component;
	private Composite parent;
	private Composite father;
	private Composite boddy;
	private Composite header;
	private String dataBaseName;
	private IJisisDataProvider service;

	public EventChangeWorkSheet(Combo combo, TemplateCompound component, Composite parent, Composite father, Composite boddy,
			Composite header, String dataBaseName, IJisisDataProvider service) {
		this.combo = combo;
		this.component = component;
		this.parent = parent;
		this.father = father;
		this.boddy = boddy;
		this.header = header;
		this.dataBaseName = dataBaseName;
		this.service = service;

	}

	@Override
	public void handleEvent(Event arg0) {

		String workSheetName = combo.getText();

		boddy.dispose();

		Composite newBoddy = new Composite(father, 0);
		newBoddy.setData(RWT.CUSTOM_VARIANT, "gray_background");
		newBoddy.setLayout(new FormLayout());
		FormDatas.attach(newBoddy).atTopTo(header, 5).atBottom(0).atRight(0).atLeft(0);

		try {
			component.createBoddy(newBoddy, parent, dataBaseName, workSheetName, service, combo);
		} catch (JisisDatabaseException e) {
			MessageDialogUtil.openError(parent.getShell(), "Error", "Problema de conección con JISIS", null);
		}

		father.getShell().layout(true, true);
		father.getShell().redraw();
		father.getShell().update();

		component.getShell().layout(true, true);
		component.getShell().redraw();
		component.getShell().update();

		parent.getShell().layout(true, true);
		parent.getShell().redraw();
		parent.getShell().update();

	}

}
