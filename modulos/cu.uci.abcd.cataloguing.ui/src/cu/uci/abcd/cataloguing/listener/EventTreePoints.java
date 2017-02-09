package cu.uci.abcd.cataloguing.listener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abos.core.util.FormDatas;

public class EventTreePoints implements Listener {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private static final long serialVersionUID = 1L;

	private ExpandItem expandItem;
	private Composite parent;
	private Composite father;

	public EventTreePoints(ExpandItem expandItem, Composite parent, Composite father){
		this.expandItem = expandItem;
		this.parent = parent;
		this.father = father;
	}


	@Override
	public void handleEvent(Event arg0) {
		// TODO Auto-generated method stub

		boolean open = father.getVisible();

		if(!open){

			father.setVisible(true);
			FormDatas.attach(father).withWidth(father.computeSize(SWT.DEFAULT, SWT.DEFAULT).x).withHeight(father.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);

			int leftMargin = 0;

			father.getParent().setSize(father.computeSize(SWT.DEFAULT, SWT.DEFAULT).x, SWT.DEFAULT);

			int parentWidth = father.getParent().getSize().x;

			int rest = 1000 - parentWidth;

			leftMargin = rest/2;

			FormDatas.attach(father).atTopTo(father.getParent(), 45).atLeftTo(father.getParent(), leftMargin);
		}
		else{

			father.setVisible(false);   
			FormDatas.attach(father).withWidth(0).withHeight(0);  

		}

		father.getShell().layout(true, true);
		father.getShell().redraw();
		father.getShell().update();

		parent.getShell().layout(true, true);
		parent.getShell().redraw();
		parent.getShell().update();

		expandItem.setHeight(parent.computeSize(SWT.DEFAULT,
				SWT.DEFAULT).y+5);

	}

}
