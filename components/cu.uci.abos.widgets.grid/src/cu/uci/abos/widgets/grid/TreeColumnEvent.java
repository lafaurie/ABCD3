package cu.uci.abos.widgets.grid;

import org.eclipse.swt.widgets.TreeItem;

public class TreeColumnEvent {
	public CRUDTreeTable source;
	public TreeItem item;
	public IGridViewEntity entity;
	public IEditableArea editableArea;
	public boolean showEditableArea;
	public boolean performDelete;
}
