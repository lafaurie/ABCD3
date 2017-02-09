package cu.uci.abos.core.widget.grid;

public class NotPaginateVisualEntityManager implements IVisualEntityManager {

	private final NotPaginateTable table;

	public NotPaginateVisualEntityManager(NotPaginateTable table) {
		super();
		this.table = table;
		this.table.setVisualEntityManager(this);
	}

	@Override
	public void save(IGridViewEntity entity) {
		table.saveEntity(table.getSelectedTreeItem(), entity);
		table.createWatchArea(entity);
		table.refresh();
	}

	@Override
	public void delete(IGridViewEntity entity) {
		table.refresh();
	}

	@Override
	public void add(IGridViewEntity entity) {
		table.addRow(entity);
		table.createWatchArea(entity);
		table.refresh();
	}

	@Override
	public void refresh() {
		table.refresh();
	}

	@Override
	public void goToLastPage() {
		table.refresh();
	}

}
