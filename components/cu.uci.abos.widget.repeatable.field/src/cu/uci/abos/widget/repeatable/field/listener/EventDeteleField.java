package cu.uci.abos.widget.repeatable.field.listener;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Listener;
import cu.uci.abos.widget.repeatable.field.util.FieldStructure;
import cu.uci.abos.widget.repeatable.field.util.SubFieldStructure;
import cu.uci.abos.core.util.FormDatas;

public class EventDeteleField implements Listener {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private static final long serialVersionUID = 1L;

	private Composite parent;
	private Composite father;
	private ExpandItem expandItem;
	private Composite content;
	private SubFieldStructure subFieldStructure;
	private FieldStructure topComponent;
	private ArrayList<FieldStructure> children;

	public EventDeteleField(Composite parent, ExpandItem expandItem, Composite father, Composite content, 
			SubFieldStructure subFieldStructure, FieldStructure topComponent, ArrayList<FieldStructure> children) {
		this.parent = parent;
		this.expandItem = expandItem;
		this.father = father;
		this.content = content;
		this.subFieldStructure = subFieldStructure;
		this.topComponent = topComponent;
		this.children = children;
	}

	@Override
	public void handleEvent(Event arg0) {

		content.dispose();

		int positionField = children.indexOf(topComponent);

		ArrayList<SubFieldStructure> subFields = children.get(positionField).getSubfields();

		int positionSubField = subFields.indexOf(subFieldStructure);

		subFields.remove(positionSubField);

		int childrenCount = father.getChildren().length;

		for (int i = 0; i < childrenCount; i++) {

			if (i == 0) {
				if(childrenCount==1)
					FormDatas.attach(father.getChildren()[i]).atTopTo(father, 0);
				else
					FormDatas.attach(father.getChildren()[i]).atTopTo(father, 0).atLeftTo(father, 80);
			}
			else if(i == childrenCount-1){
				FormDatas.attach(father.getChildren()[i]).atTopTo(father.getChildren()[i - 1], 10);
			}
			else {
				FormDatas.attach(father.getChildren()[i]).atTopTo(father.getChildren()[i - 1], 10).atLeftTo(father, 80);
			}
		}

		father.getShell().layout(true, true);
		father.getShell().redraw();
		father.getShell().update();

		parent.getShell().layout(true, true);
		parent.getShell().redraw();
		parent.getShell().update();

		expandItem.setHeight(parent.computeSize(SWT.DEFAULT, SWT.DEFAULT).y + 5);

	}

}
