package cu.uci.abos.core.widget.grid;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.widgets.Display;

import cu.uci.abos.core.domain.Row;
import cu.uci.abos.core.l10n.AbosMessages;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.widget.grid.listener.DeleteColumnListener;
import cu.uci.abos.core.widget.grid.listener.PDFTableListener;
import cu.uci.abos.core.widget.grid.listener.TablePageChangeListener;
import cu.uci.abos.core.widget.grid.listener.UpdateColumnListener;
import cu.uci.abos.core.widget.grid.listener.XLSTableListener;

public class CRUDTreeTableUtils {

	public static void configRemove(TreeTable table, final IActionCommand command) {
		table.addDeleteListener(new DeleteColumnListener(command));
	}

	public static void configUpdate(final TreeTable table) {
		table.addUpdateListener(new UpdateColumnListener(table));
	}

	public static void configAdd(TreeTable table) {
		table.setAddButtonText(AbosMessages.get().BUTTON_ADD);
	}

	public static void configOrder(final TreeTable table, final IPaginatorCommand command) {
		table.addPageChangeListener(new TablePageChangeListener(command));
	}

	public static void configExportXLS(final TreeTable table, final String name, final List<String> criterias, String header) {
		table.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));
		table.addActionButton("exportExcelButton", new ButtonData("file-excel", Display.getCurrent(), new XLSTableListener(criterias, table, name, header)));
	}

	public static void configExportPDF(final TreeTable table, final String name, final List<String> criterias, String header) {
		table.setActionButtonText("exportPDFButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_PDF));
		table.addActionButton("exportPDFButton", new ButtonData("file-pdf", Display.getCurrent(), new PDFTableListener(criterias, table, name, header)));
	}

	public static void configReports(final TreeTable table, final String name, final List<String> criterias) {
		configReports(table, name, criterias, null);
	}
	
	public static void configReports(final TreeTable table, final String name, final List<String> criterias, final String header) {
		configExportPDF(table, name, criterias, header);
		configExportXLS(table, name, criterias, header);
	}

	public static void removeFrom(final CRUDTreeTable originalTable, final CRUDTreeTable selectedTable) {
		for (Iterator<IGridViewEntity> iterator = selectedTable.getEntities().iterator(); iterator.hasNext();) {
			IGridViewEntity iGridViewEntity = (IGridViewEntity) iterator.next();
			originalTable.removeRow(iGridViewEntity);
		}
		originalTable.refresh();
	}

	public static TreeColumnListener move(final CRUDTreeTable selectedTable) {
		TreeColumTableListener listener = new TreeColumTableListener() {
			private CRUDTreeTable table;

			public void setTable(CRUDTreeTable table) {
				this.table = table;
			}

			public void handleEvent(TreeColumnEvent event) {
				table.addRow(new BaseGridViewEntity<>(event.entity.getRow()));
				table.refresh();
				((CRUDTreeTable) event.source).removeRow(event.entity);
				if (((CRUDTreeTable) event.source).getEntities().isEmpty()&& ((CRUDTreeTable) event.source).getPaginator().getTotalElements()>0) {
						((CRUDTreeTable) event.source).getPaginator().goToFirstPage();
				}
				((CRUDTreeTable) event.source).refresh();
				table.getShell().layout(true, true);
				table.getShell().redraw();
				table.getShell().update();
			}

		};
		listener.setTable(selectedTable);
		return listener;
	}

	public static <T extends Row> TreeColumnListener move(final CRUDTreeTable selectedTable, final Collection<T> source) {
		TreeColumTableListener listener = new TreeColumTableListener() {
			private CRUDTreeTable table;

			public void setTable(CRUDTreeTable table) {
				this.table = table;
			}

			@SuppressWarnings("unchecked")
			public void handleEvent(TreeColumnEvent event) {
				table.addRow(new BaseGridViewEntity<>(event.entity.getRow()));
				source.add((T) event.entity.getRow());
				table.getPaginator().goToFirstPage();
				table.refresh();

				((CRUDTreeTable) event.source).removeRow(event.entity);
				if (((CRUDTreeTable) event.source).getEntities().isEmpty()&&((CRUDTreeTable) event.source).getPaginator().getTotalElements()>0 ) {
						((CRUDTreeTable) event.source).getPaginator().goToFirstPage();
				}
				((CRUDTreeTable) event.source).refresh();
				table.getShell().layout(true, true);
				table.getShell().redraw();
				table.getShell().update();
			}
		};
		listener.setTable(selectedTable);
		return listener;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Row> void move(final CRUDTreeTable table, final TreeColumnEvent event, final Collection<T> source) {
		table.addRow(new BaseGridViewEntity<>(event.entity.getRow()));
		source.add((T) event.entity.getRow());
		table.getPaginator().goToFirstPage();
		table.refresh();

		((CRUDTreeTable) event.source).removeRow(event.entity);
		if (((CRUDTreeTable) event.source).getEntities().isEmpty()&&((CRUDTreeTable) event.source).getPaginator().getTotalElements()>0 ) {
				((CRUDTreeTable) event.source).getPaginator().goToFirstPage();
		}
		((CRUDTreeTable) event.source).refresh();
		table.getShell().layout(true, true);
		table.getShell().redraw();
		table.getShell().update();

	}

	public static <T extends Row> TreeColumnListener moveRemove(final CRUDTreeTable selectedTable, final Collection<T> source) {
		TreeColumTableListener listener = new TreeColumTableListener() {
			private CRUDTreeTable table;

			public void setTable(CRUDTreeTable table) {
				this.table = table;
			}

			public void handleEvent(TreeColumnEvent event) {
				table.addRow(new BaseGridViewEntity<>(event.entity.getRow()));
				source.remove(event.entity.getRow());
				table.getPaginator().goToFirstPage();
				table.refresh();

				((CRUDTreeTable) event.source).removeRow(event.entity);
				if (((CRUDTreeTable) event.source).getEntities().isEmpty()&& ((CRUDTreeTable) event.source).getPaginator().getTotalElements()>0) {
						((CRUDTreeTable) event.source).getPaginator().goToFirstPage();
				}
				((CRUDTreeTable) event.source).refresh();
				table.getShell().layout(true, true);
				table.getShell().redraw();
				table.getShell().update();
			}
		};
		listener.setTable(selectedTable);
		return listener;
	}

	public static <T extends Row> TreeColumnListener addRemove(final CRUDTreeTable selectedTable, final Collection<T> source, final Collection<T> toList) {
		TreeColumTableListener listener = new TreeColumTableListener() {
			private CRUDTreeTable table;

			public void setTable(CRUDTreeTable table) {
				this.table = table;
			}

			@SuppressWarnings("unchecked")
			public void handleEvent(TreeColumnEvent event) {
				if (!toList.contains(event.entity.getRow())) {
					toList.add((T) event.entity.getRow());	
				}
				table.clearRows();

				((CRUDTreeTable) event.source).removeRow(event.entity);
				source.remove(event.entity.getRow());
				if (((CRUDTreeTable) event.source).getEntities().isEmpty()&& ((CRUDTreeTable) event.source).getPaginator().getTotalElements()>0 ) {
						((CRUDTreeTable) event.source).getPaginator().goToFirstPage();
				}
				((CRUDTreeTable) event.source).refresh();
				table.getPaginator().goToFirstPage();
				table.refresh();
				table.getShell().layout(true, true);
				table.getShell().redraw();
				table.getShell().update();
			}
		};
		listener.setTable(selectedTable);
		return listener;
	}

}
