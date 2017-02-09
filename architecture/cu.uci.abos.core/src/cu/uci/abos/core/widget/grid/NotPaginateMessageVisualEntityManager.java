package cu.uci.abos.core.widget.grid;

import cu.uci.abos.core.l10n.AbosMessages;

public class NotPaginateMessageVisualEntityManager implements IVisualEntityManager {
	
	private final NotPaginateTable table;

	public NotPaginateMessageVisualEntityManager(NotPaginateTable table) {
		super();
		this.table = table;
		this.table.setVisualEntityManager(this);
	}

	@Override
	public void save(IGridViewEntity entity) {

		if (((BaseEditableArea) table.getActiveArea()).validate()) {
			if (((BaseEditableArea) table.getActiveArea()).sucess()) {
				table.saveEntity(table.getSelectedTreeItem(), entity);
				table.createWatchArea(entity);
				((BaseEditableArea) table.getActiveArea()).showInformationMessage(AbosMessages.get().MSG_INF_UPDATE_DATA);
				table.refresh();
			}

		} else {
			((BaseEditableArea) table.getActiveArea()).showErrorMessage(AbosMessages.get().MSG_ERROR_FIELD_REQUIRED);
		}

	}

	@Override
	public void delete(IGridViewEntity entity) {
		table.refresh();
	}

	@Override
	public void add(IGridViewEntity entity) {
		if (((BaseEditableArea) table.getActiveArea()).validate()) {
			if (((BaseEditableArea) table.getActiveArea()).sucess()) {
				table.addRow(entity);
				table.createWatchArea(entity);
				((BaseEditableArea) table.getActiveArea()).showInformationMessage(AbosMessages.get().MSG_INF_CREATE_NEW_ELEMENT);
				table.refresh();
			}
		} else {
			((BaseEditableArea) table.getActiveArea()).showErrorMessage(AbosMessages.get().MSG_ERROR_FIELD_REQUIRED);
		}
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
