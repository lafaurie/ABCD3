package cu.uci.abos.core.widget.grid.listener;

import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeColumnListener;
import cu.uci.abos.core.widget.grid.TreeTable;

public class UpdateColumnListener implements TreeColumnListener {
	private final TreeTable table;

	public UpdateColumnListener(TreeTable table) {
		this.table = table;
	}

	public void handleEvent(TreeColumnEvent event) {
		table.refresh();
	}
}
