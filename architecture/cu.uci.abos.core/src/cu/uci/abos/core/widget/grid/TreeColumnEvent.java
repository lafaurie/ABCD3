package cu.uci.abos.core.widget.grid;

import org.eclipse.swt.widgets.TreeItem;

public class TreeColumnEvent {
	public TreeTable source;
	public TreeItem item;
	public IGridViewEntity entity;
	public IEditableArea editableArea;
	public boolean showEditableArea;
	public boolean performDelete;
}
