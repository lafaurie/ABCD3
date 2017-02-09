package cu.uci.abcd.administration.library.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.springframework.data.domain.Page;

import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.administration.library.ui.controller.LibraryViewController;
import cu.uci.abcd.administration.library.ui.model.RoomUpdateArea;
import cu.uci.abcd.administration.library.ui.model.RoomViewArea;
import cu.uci.abcd.administration.library.util.Auxiliary;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
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

public class ConsultRoom extends ContributorPage implements Contributor {
	private Label consultRoomLabel;
	private Label searchCriteriaLabel;
	private Label nameLabel;
	private Text nameText;
	private Button newSearchButton;
	private Button consultButton;
	private SecurityCRUDTreeTable roomTable;
	private Library library;
	private String orderByString = "roomName";
	private int direction = 1024;
	private Label roomList;
	private String roomNameConsult;
	private Label separador;
	private List<String> searchCriteriaList = new ArrayList<>();
	Composite right;
	private ValidatorUtils validator;

	@Override
	public String contributorName() {
		return AbosMessages.get().CONSULT_ROOM;
	}
	@Override
	public String getID() {
		return "consultRoomID";
	}
	
	@Override
	public Control createUIControl(Composite shell) {
		validator = new ValidatorUtils(new CustomControlDecoration());

		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		
        addComposite(shell);
		shell.setData(RWT.CUSTOM_VARIANT, "gray_background");

		Composite scroll = new Composite(shell,SWT.NONE);
		scroll.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(scroll).atTopTo(shell, 25).atRight(0).withWidth(0).withHeight(Display.getCurrent().getBounds().height - 172);
		
		
		consultRoomLabel= new Label(shell, SWT.NONE);
		addHeader(consultRoomLabel);
		
		Label separator = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);
		
		searchCriteriaLabel = new Label(shell, SWT.NONE);
		addHeader(searchCriteriaLabel);
		
		nameLabel = new Label(shell, SWT.NONE);
		add(nameLabel);

		nameText = new Text(shell, SWT.NONE);
		add(nameText);
		//validator.applyValidator(nameText, 49);
		br();

		newSearchButton = new Button(shell, SWT.NONE);
		add(newSearchButton);
		
		consultButton = new Button(shell, SWT.NONE);
		add(consultButton);
		br();
		
		separador = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separador);
		
		right =  new Composite(shell, SWT.NONE);
		addComposite(right);
		right.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(right).atTopTo(separador, 10).atRight(15).withWidth(300).withHeight(5);

       
		roomList = new Label(shell, SWT.NORMAL);
		addHeader(roomList);

		roomTable = new SecurityCRUDTreeTable(shell, SWT.NONE);
		add(roomTable);
		roomTable.setEntityClass(Room.class);
		roomTable.setSearch(false);
		roomTable.setSaveAll(false);
		roomTable.setWatch(true, new RoomViewArea());
		roomTable.setUpdate(true, new RoomUpdateArea(controller, roomTable));
		roomTable.setDelete("deleteRoomID");
		roomTable.setVisible(true);
		roomTable.setPageSize(10);
		
		roomTable.addListener(SWT.Resize, new Listener() {
            private static final long serialVersionUID = 8817895862824622805L;
            @Override
            public void handleEvent(Event event) {
                    refresh();
            }
           });
		
		CRUDTreeTableUtils.configUpdate(roomTable);
		CRUDTreeTableUtils.configReports(roomTable, contributorName(),
				searchCriteriaList);
		
		roomTable.addDeleteListener(new TreeColumnListener() {
			public void handleEvent(final TreeColumnEvent event) {
				MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_QUESTION), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_DELET_DATA),
						new DialogCallback() {

							private static final long serialVersionUID = 8415736231132589115L;

							@Override
							public void dialogClosed(int returnCode) {
								if (returnCode == 0) {
									Room room = (Room) event.entity.getRow();
									switch (((LibraryViewController) controller).deleteRoom(room)) {
									case 1:
										RetroalimentationUtils.showInformationMessage((cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_ERROR_USED_DATA));
										break;

									case 2:
										RetroalimentationUtils.showInformationMessage((cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NOT_DELETE_ONE_ITEM));
										break;
										
									case 3:
										RetroalimentationUtils.showInformationMessage((cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_DELETE));
										
										break;
									}
									//if(((ProviderViewController) controller)
									//		.deleteProviderByProvider(provider)){
										
									//}else{
										
									//}
								    //int b = a;
									//searchProviders(0, tableProvider.getPaginator().getPageSize());
									//searchRooms(0, roomTable.getPaginator().getPageSize());
									roomTable.getPaginator().goToPage(roomTable.getPaginator().getCurrentPage());
								}
							}
						});
			
				

			}
		});
		
		/*
		CRUDTreeTableUtils.configRemove(roomTable, new IActionCommand() {
			@Override
			public void execute(final TreeColumnEvent event) {
				roomTable.destroyEditableArea();
				Room room = (Room) event.entity.getRow();
				Long idRoom = room.getRoomID();
				((LibraryViewController) controller)
						.getAllManagementLibraryViewController().getRoomService().deleteRoom(idRoom);
				
				searchRooms(0, roomTable.getPaginator().getPageSize());
				
				List<Room> allRooms = ((LibraryViewController) controller).getAllManagementLibraryViewController().getRoomService().findAll(library
						.getLibraryID());
						
				String pickList = allRooms.toString();
				pickList = pickList.replace("[", "");
				pickList = pickList.replace("]", "");
				String databaseName = "Registro_De_Adquisicion";
				String isisDefHome = library.getIsisDefHome();
				String workSheetNameDonation = "Donación";
				String workSheetNameCompra = "Compra";
				String workSheetNameDefault = "Default worksheet";
				String workSheetNameCanje = "Canje";
				try {
					
					WorksheetDef workSheetDonation = ((LibraryViewController) controller)
							.getWorksheet(
									workSheetNameDonation,
									databaseName, isisDefHome);
					workSheetDonation.getFieldByTag(7).getSubFieldByIndex(0).setPickList(pickList);
					((LibraryViewController) controller)
							.updateWorksheet(workSheetDonation);

					
					WorksheetDef workSheetCompra = ((LibraryViewController) controller)
							.getWorksheet(workSheetNameCompra,
									databaseName, isisDefHome);
					workSheetCompra.getFieldByTag(7).getSubFieldByIndex(0).setPickList(pickList);
					((LibraryViewController) controller)
							.updateWorksheet(workSheetCompra);

					
					WorksheetDef workSheetDefault = ((LibraryViewController) controller)
							.getWorksheet(workSheetNameDefault,
									databaseName, isisDefHome);
					workSheetDefault.getFieldByTag(7).getSubFieldByIndex(0).setPickList(pickList);
					((LibraryViewController) controller)
							.updateWorksheet(workSheetDefault);

					
					WorksheetDef workSheetCanje = ((LibraryViewController) controller)
							.getWorksheet(workSheetNameCanje,
									databaseName, isisDefHome);
					workSheetCanje.getFieldByTag(7).getSubFieldByIndex(0).setPickList(pickList);
					((LibraryViewController) controller)
							.updateWorksheet(workSheetCanje);
					
				} catch (Exception e1) {
					RetroalimentationUtils
							.showErrorMessage("JISIS is down");
				}
				
			}
		});
		*/
		TreeTableColumn columns[] = {
				new TreeTableColumn(39, 0, "getRoomName"),
				new TreeTableColumn(18, 1, "getSurfaceToString"),
				new TreeTableColumn(22, 2, "getAvailableReadingSeatsToString"),
				new TreeTableColumn(21, 3, "getUserPcQuantityToString") };

			roomTable.createTable(columns);
		
		
		roomTable.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
                   if(event.sortData!=null){
					direction = event.sortData.sortDirection;
					  switch (event.sortData.columnIndex) {
						case 0: orderByString = "roomName";
							break;
						case 1: orderByString = "availableReadingSeats";
							break;
						case 2: orderByString = "surface";
						    break;
						}
				}
                   searchRooms(event.currentPage - 1, event.pageSize);
                   refresh();
				
			}
		});
		
		consultButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 428739188163080088L;
			@Override
			public void widgetSelected(SelectionEvent e) {
				roomTable.destroyEditableArea();
				roomTable.clearRows();
				roomNameConsult = nameText.getText();
				Auxiliary.showLabelAndTable(roomList, roomTable, true);
				//searchRooms(0, roomTable.getPaginator().getPageSize());
				roomTable.getPaginator().goToFirstPage();
				searchCriteriaList.clear();
				UiUtils.get().addSearchCriteria(searchCriteriaList, nameLabel.getText(), nameText.getText());
				
				refresh();
				
				if (roomTable.getRows().isEmpty()) {
					RetroalimentationUtils
							.showInformationMessage(
									right,
									cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
					roomList.setVisible(false);
					roomTable.setVisible(false);
				}else{
					roomList.setVisible(true);
					roomTable.setVisible(true);
				}
			}

		});
		
		newSearchButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -5179607102055540891L;
			@Override
			public void widgetSelected(SelectionEvent e) {
				searchCriteriaList.clear();
				roomTable.destroyEditableArea();
				nameText.setText("");
				nameText.setFocus();
				Auxiliary.showLabelAndTable(roomList, roomTable, false);
			}

		});
		
		roomTable.getPaginator().goToFirstPage();
		Auxiliary.showLabelAndTable(roomList, roomTable, false);
		l10n();
		refresh();
		//consultButton.getShell().setDefaultButton(consultButton);
		return shell;
	}

	private void searchRooms(int page, int size) {
		Page<Room> list = ((LibraryViewController) controller)
				.findRoomByParams(library, roomNameConsult,
						page, size, direction, orderByString);
		roomTable.clearRows();
		roomTable.setTotalElements((int) list.getTotalElements());
		roomTable.setRows(list.getContent());
		roomTable.refresh();
		
		
		
		refresh();
	}
	
	@Override
	public boolean canClose() {
		return false;
	}
	//FIXME BORRAR CODIGO COMENTARIADO
	@Override
	public void l10n() {
		consultRoomLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_CONSULT_ROOM));
		searchCriteriaLabel
		.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEARCH_CRITERIA));
		nameLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_NAME));
		newSearchButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW_SEARCH));
		consultButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CONSULT));
		
		roomList.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LIST_OF_MATCHES));
		roomTable.setAddButtonText(AbosMessages.get().BUTTON_ADD);
		roomTable.setCancelButtonText(MessageUtil.unescape(AbosMessages.get().BUTTON_CANCEL));
		roomTable.setColumnHeaders(Arrays.asList(
				MessageUtil.unescape(AbosMessages.get().LABEL_NAME),
				MessageUtil.unescape(AbosMessages.get().LABEL_SURFACE),
				MessageUtil.unescape(AbosMessages.get().LABEL_READING_SETS),
				MessageUtil.unescape(AbosMessages.get().LABEL_USER_PC_QUANTITY))); // For
		
		
		
		
																			// the
		roomTable.setActionButtonText("exportPDFButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_PDF));
		roomTable.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));
															// internationalization.

		roomList.getParent().layout(true, true);
		roomList.getParent().redraw();
		roomList.getParent().update();
		roomTable.l10n();
	}

	@Override
	public void setViewController(ViewController controller) {
		super.controller = controller;
	}

}
