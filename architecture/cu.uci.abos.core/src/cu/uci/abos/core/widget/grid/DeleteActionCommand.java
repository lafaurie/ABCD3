package cu.uci.abos.core.widget.grid;

import cu.uci.abos.core.domain.Row;

public abstract class DeleteActionCommand implements IActionCommand {

	@Override
	public void execute(TreeColumnEvent event) {
		delete(event.entity.getRow());
	}

	public abstract void delete(Row entity);

}
