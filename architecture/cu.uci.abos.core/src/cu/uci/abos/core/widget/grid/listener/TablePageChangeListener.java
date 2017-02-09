package cu.uci.abos.core.widget.grid.listener;

import cu.uci.abos.core.widget.grid.IPaginatorCommand;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class TablePageChangeListener implements PageChangeListener {
	private final IPaginatorCommand command;

	public TablePageChangeListener(IPaginatorCommand command) {
		this.command = command;
	}

	@Override
	public void pageChanged(final PageChangedEvent event) {
		if (event.sortData != null) {
			command.excecute(event);
		}
	}
}