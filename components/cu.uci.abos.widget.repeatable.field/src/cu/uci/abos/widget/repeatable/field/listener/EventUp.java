package cu.uci.abos.widget.repeatable.field.listener;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolItem;

import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.widget.repeatable.field.SingleCataloguingComponent;
import cu.uci.abos.widget.repeatable.field.util.FieldStructure;
import cu.uci.abos.widget.repeatable.field.util.SubFieldStructure;

public class EventUp implements Listener {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private FieldStructure fieldStructure;
	private SubFieldStructure subFieldStructure;
	private Composite parent;
	private ExpandItem expandItem;
	private Composite content;
	private boolean isRepeatable;
	private ArrayList<FieldStructure> children;

	private static final long serialVersionUID = 1L;

	public EventUp(FieldStructure fieldStructure, SubFieldStructure subFieldStructure, Composite parent, ExpandItem expandItem,
			Composite content, boolean isRepeatable, ArrayList<FieldStructure> children) {
		this.children = children;
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
		int positionField = children.indexOf(fieldStructure);
		int positionStructure = children.get(positionField).getSubfields().indexOf(subFieldStructure);

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
				assistantList.add(children.get(positionField).getSubfields().get(positionBegin + i));
			}

			for (int i = 0; i < contentSize; i++) {
				children.get(positionField).getSubfields().remove(positionBegin);
			}

			for (int i = 0; i < contentSize; i++) {
				children.get(positionField).getSubfields().add(positionInsert, assistantList.get((assistantList.size() - 1) - i));
			}

			// Change position on the visual component
			Control[] tabListParent = parent.getTabList();
			Control assistan = tabListParent[visualPositionChange];
			tabListParent[visualPositionChange] = tabListParent[visualPosition];
			tabListParent[visualPosition] = assistan;
			parent.setTabList(tabListParent);

			ToolItem down = ((SingleCataloguingComponent)(parent.getTabList()[visualPositionChange])).getDown();
			if(!down.isDisposed())
				down.setEnabled(true);

			if(visualPositionChange == 0){
				ToolItem up = ((SingleCataloguingComponent)(parent.getTabList()[visualPosition])).getUp();
				if(!up.isDisposed())
					up.setEnabled(true);
			}	

			for (int i = 0; i < parent.getTabList().length; i++) {
				if (i == 0) {
					if (isRepeatable)
						FormDatas.attach(parent.getTabList()[i]).atTopTo(parent, 30);
					else
						FormDatas.attach(parent.getTabList()[i]).atTopTo(parent, 5);

					ToolItem up = ((SingleCataloguingComponent)(parent.getTabList()[i])).getUp();
					ToolItem down2 = ((SingleCataloguingComponent)(parent.getTabList()[i])).getDown();

					if(!up.isDisposed())
						up.setEnabled(false);

					if(!down2.isDisposed())
						down2.setEnabled(true);    
				} else {

					FormDatas.attach(parent.getTabList()[i]).atTopTo(parent.getTabList()[i - 1], 0);

					if(i == parent.getTabList().length -1){

						ToolItem up = ((SingleCataloguingComponent)(parent.getTabList()[i])).getUp();
						ToolItem down2 = ((SingleCataloguingComponent)(parent.getTabList()[i])).getDown();

						if(!up.isDisposed())
							up.setEnabled(true);

						if(!down2.isDisposed())
							down2.setEnabled(false);      
					}
				}
			}

			parent.getShell().layout(true, true);
			parent.getShell().redraw();
			parent.getShell().update();

			expandItem.setHeight(parent.computeSize(SWT.DEFAULT, SWT.DEFAULT).y + 5);
		}
	}
}
