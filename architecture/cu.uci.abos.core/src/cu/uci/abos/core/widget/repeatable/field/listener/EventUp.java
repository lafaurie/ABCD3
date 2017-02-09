package cu.uci.abos.core.widget.repeatable.field.listener;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.widget.repeatable.field.CataloguingComponent;
import cu.uci.abos.core.widget.repeatable.field.util.FieldStructure;
import cu.uci.abos.core.widget.repeatable.field.util.SubFieldStructure;

public class EventUp implements Listener {

	private FieldStructure fieldStructure;
	private SubFieldStructure subFieldStructure;
	private Composite parent;
	private ExpandItem expandItem;
	private Composite content;
	private boolean isRepeatable;

	private static final long serialVersionUID = 1L;

	public EventUp(FieldStructure fieldStructure, SubFieldStructure subFieldStructure, Composite parent, ExpandItem expandItem, Composite content, boolean isRepeatable) {

		this.fieldStructure = fieldStructure;
		this.subFieldStructure = subFieldStructure;
		this.parent = parent;
		this.expandItem = expandItem;
		this.content = content;
		this.isRepeatable = isRepeatable;
	}

	@Override
	public void handleEvent(Event arg0) {
		int visualPosition = 0;
		boolean positionFounded = false;
		int positionField = CataloguingComponent.childrens.indexOf(fieldStructure);
		int positionStructure = CataloguingComponent.childrens.get(positionField).getSubfields().indexOf(subFieldStructure);

		for (int i = 0; i < parent.getTabList().length && positionFounded == false; i++) {
			if (parent.getTabList()[i].equals(content)) {
				positionFounded = true;
				visualPosition = i;
			}
		}

		if (visualPosition != 0) {
			Composite contentChange = (Composite) parent.getTabList()[visualPosition - 1];
			int visualPositionChange = visualPosition - 1;

			// size of the contents
			int contentSize = ((Composite) content.getTabList()[0]).getTabList().length;
			int contentChangeSize = ((Composite) contentChange.getTabList()[0]).getTabList().length;

			// Change position on the structure
			int positionInsert = positionStructure - (contentSize - 1) - contentChangeSize;
			int positionBegin = positionStructure - (contentSize - 1);
			ArrayList<SubFieldStructure> assistantList = new ArrayList<SubFieldStructure>();

			for (int i = 0; i < contentSize; i++) {
				assistantList.add(CataloguingComponent.childrens.get(positionField).getSubfields().get(positionBegin + i));
			}

			for (int i = 0; i < contentSize; i++) {
				CataloguingComponent.childrens.get(positionField).getSubfields().remove(positionBegin);
			}

			for (int i = 0; i < contentSize; i++) {
				CataloguingComponent.childrens.get(positionField).getSubfields().add(positionInsert, assistantList.get((assistantList.size() - 1) - i));
			}

			// Change position on the visual component
			Control[] tabListParent = parent.getTabList();
			Control assistan = tabListParent[visualPositionChange];
			tabListParent[visualPositionChange] = tabListParent[visualPosition];
			tabListParent[visualPosition] = assistan;
			parent.setTabList(tabListParent);

			for (int i = 0; i < parent.getTabList().length; i++) {
				if (i == 0) {
					if (isRepeatable)
						FormDatas.attach(parent.getTabList()[i]).atTopTo(parent, 45);
					else
						FormDatas.attach(parent.getTabList()[i]).atTopTo(parent, 5);
				} else {

					FormDatas.attach(parent.getTabList()[i]).atTopTo(parent.getTabList()[i - 1], 10);
				}
			}

			parent.getShell().layout(true, true);
			parent.getShell().redraw();
			parent.getShell().update();

			expandItem.setHeight(parent.computeSize(SWT.DEFAULT, SWT.DEFAULT).y + 5);
		}
	}
}
