package cu.uci.abcd.administration.library.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.administration.library.ui.controller.ScheduleViewController;
import cu.uci.abcd.administration.library.ui.model.HorarySaveArea;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Schedule;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.widget.grid.CRUDTreeTableUtils;
import cu.uci.abos.core.widget.grid.SecurityCRUDTreeTable;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeColumnListener;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class ConfigureSchedule extends ContributorPage implements Contributor {
	Label configureScheduleLabel;
	private SecurityCRUDTreeTable horaryTable;
	private Library library;
	private int direction = 1024;
	private String orderByString = "dayOfWeek.nomenclatorID";
	private List<String> searchCriteriaList = new ArrayList<>();
	
	@Override
	public String contributorName() {
		return AbosMessages.get().HORARY;
	}

	@Override
	public String getID() {
		return "configureSheduleID";
	}
	//FIXME BORRAR CODIGO COMENTARIADO
	@Override
	public Control createUIControl(Composite shell) {
		addComposite(shell);
		shell.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		Composite scroll = new Composite(shell,SWT.NONE);
		scroll.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(scroll).atTopTo(shell, 25).atRight(0).withWidth(0).withHeight(Display.getCurrent().getBounds().height - 172);
		
		
		configureScheduleLabel = new Label(shell, SWT.NORMAL);
		addHeader(configureScheduleLabel);
		
		Label separator = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);
	
		horaryTable = new SecurityCRUDTreeTable(shell, SWT.NONE);
		add(horaryTable);
		horaryTable.setEntityClass(Schedule.class);
		horaryTable.setSearch(false);
		horaryTable.setSaveAll(false);
		horaryTable.setAdd(true, new HorarySaveArea(this, controller, horaryTable, 1));
		horaryTable.setUpdate(true, new HorarySaveArea(this, controller, horaryTable, 2));
		horaryTable.setDelete("deleteSheduleID");
		horaryTable.setVisible(true);
		horaryTable.setPageSize(10);

		CRUDTreeTableUtils.configUpdate(horaryTable);
		CRUDTreeTableUtils.configReports(horaryTable, contributorName(),
				searchCriteriaList);
		
		horaryTable.addDeleteListener(new TreeColumnListener() {
			public void handleEvent(final TreeColumnEvent event) {
				MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_QUESTION), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_DELET_DATA),
						new DialogCallback() {

							private static final long serialVersionUID = 8415736231132589115L;

							@Override
							public void dialogClosed(int returnCode) {
								if (returnCode == 0) {
									Schedule schedule = (Schedule) event.entity.getRow();
									Long idSchedule = schedule.getWorkScheduleID();
									((ScheduleViewController) controller)
											.deleteScheduleById(idSchedule);
									RetroalimentationUtils.showInformationMessage((cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_DELETE));
									//searchHorary();
									horaryTable.getPaginator().goToFirstPage();
								}
							}
						});
			}
		});
/*
		CRUDTreeTableUtils.configRemove(horaryTable, new IActionCommand() {
			@Override
			public void execute(final TreeColumnEvent event) {
				horaryTable.destroyEditableArea();
				Schedule schedule = (Schedule) event.entity.getRow();
				Long idSchedule = schedule.getWorkScheduleID();
				((ScheduleViewController) controller)
						.deleteScheduleById(idSchedule);
			}
		});
*/
		TreeTableColumn columns[] = {
				new TreeTableColumn(34, 0, "getDayOfWeek.getNomenclatorName"),
				new TreeTableColumn(33, 1, "getFromTimeToString"),
				new TreeTableColumn(33, 2, "getToTimeToString") };
		horaryTable.createTable(columns);

		horaryTable.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
				if (event.sortData != null) {
					direction = event.sortData.sortDirection;
					switch (event.sortData.columnIndex) {
					case 0:
						orderByString = "dayOfWeek.nomenclatorID";
						break;
					case 1:
						orderByString = "startHour";
						break;
					case 2:
						orderByString = "endhour";
						break;
					}
				}
				searchHorary(event.currentPage - 1, event.pageSize);
			}
		});
		
		horaryTable.getPaginator().goToFirstPage();
        //searchHorary();
		l10n();
		return shell;
	}

	public void searchHorary(int page, int size) {
		orderByString = "dayOfWeek.nomenclatorID";
		direction = 1024;
		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		List<Schedule> list = ((ScheduleViewController) controller).findAll(library.getLibraryID());
		
		horaryTable.setTotalElements((int) list.size());
		
		if (list.size() <= page * size + size) {
			horaryTable.setRows(list.subList(page * size, list.size()));
		} else {
			horaryTable.setRows(list.subList(page * size, page * size + size));
		}
		horaryTable.refresh();
		
		//horaryTable.clearRows();
		//horaryTable.setRows(list);
		//horaryTable.refresh();
	}

	@Override
	public boolean canClose() {
		return false;
	}
	//FIXME BORRAR CODIGO COMENTARIADO
	@Override
	public void l10n() {
		configureScheduleLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_CONFIGURE_HORARY));
		//weeklyScheduleLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_WEEKLY_SCHEDULE));
		horaryTable.setCancelButtonText(MessageUtil.unescape(AbosMessages.get().BUTTON_CANCEL));
		horaryTable.setAddButtonText(MessageUtil.unescape(AbosMessages.get().BUTTON_ADD));
		horaryTable.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_DAY_OF_WEEK), MessageUtil.unescape(AbosMessages.get().LABEL_HOUR_FROM),
				MessageUtil.unescape(AbosMessages.get().LABEL_HOUR_TO)));
		
		horaryTable.setActionButtonText("exportPDFButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_PDF));
		horaryTable.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));

		horaryTable.getParent().layout(true, true);
		horaryTable.getParent().redraw();
		horaryTable.getParent().update();
		
		horaryTable.l10n();
		
	}

	@Override
	public void setViewController(ViewController controller) {
		super.controller = controller;

	}
}
