package cu.uci.abcd.administration.library.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
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
import cu.uci.abcd.administration.library.ui.model.LibraryAddArea;
import cu.uci.abcd.administration.library.ui.model.LibraryUpdateArea;
import cu.uci.abcd.administration.library.ui.model.LibraryViewArea;
import cu.uci.abcd.administration.library.util.Auxiliary;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.widget.grid.CRUDTreeTableUtils;
import cu.uci.abos.core.widget.grid.IActionCommand;
import cu.uci.abos.core.widget.grid.SecurityCRUDTreeTable;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class ManageLibrary extends ContributorPage implements Contributor {
	private Label manageLibraryLabel;
	private Label searchCriteriaLabel;
	private Label nameLibraryLabel;
	private Text nameLibraryText;
	private Label separator;
	private Button newSearchButton;
	private Button consultButton;
	private Label listLibraryLabel;
	private SecurityCRUDTreeTable tableLibrary;
	private String libraryNameConsult = null;
	private String orderByString = "libraryName";
	private int direction = 1024;
	private List<String> searchCriteriaList = new ArrayList<>();
	private Composite parentTable;
	private ValidatorUtils validator;
	
	@Override
	public void setViewController(ViewController controller) {
		super.controller = controller;
	}

	@Override
	public String contributorName() {
		return AbosMessages.get().LIBRARIES;
	}

	@Override
	public String getID() {
		return "manageLibraryID";
	}

	@Override
	public Control createUIControl(final Composite shell) {

		validator = new ValidatorUtils(new CustomControlDecoration());
		shell.setData(RWT.CUSTOM_VARIANT, "gray_background");

		addComposite(shell);
		
		Composite scroll = new Composite(shell,SWT.NONE);
		scroll.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(scroll).atTopTo(shell, 25).atRight(0).withWidth(0).withHeight(Display.getCurrent().getBounds().height - 172);
		

		manageLibraryLabel = new Label(shell, SWT.NONE);
		addHeader(manageLibraryLabel);
		
		Label separator = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);

		searchCriteriaLabel = new Label(shell, SWT.NONE);
		addHeader(searchCriteriaLabel);

		nameLibraryLabel = new Label(shell, SWT.NONE);
		add(nameLibraryLabel);

		nameLibraryText = new Text(shell, SWT.NONE);
		add(nameLibraryText);
		//validator.applyLengthValidator(control, maxLength);Validator(nameLibraryText, 99);
		br();

		newSearchButton = new Button(shell, SWT.NONE);
		add(newSearchButton);

		consultButton = new Button(shell, SWT.NONE);
		add(consultButton);

		separator = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);

		Composite space = new Composite(shell, SWT.NONE);
		addComposite(space);
		space.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(space).atTopTo(separator).atRight(15).withWidth(300);

		parentTable = new Composite(shell, SWT.NONE);
		parentTable.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(parentTable);

		listLibraryLabel = new Label(parentTable, SWT.NORMAL);
		addHeader(listLibraryLabel);

		tableLibrary = new SecurityCRUDTreeTable(parentTable, SWT.NONE);
		add(tableLibrary);
		tableLibrary.setEntityClass(Library.class);
		tableLibrary.setSearch(false);
		tableLibrary.setSaveAll(false);

		tableLibrary.setAdd(true, new LibraryAddArea(controller, tableLibrary));
		tableLibrary.setWatch(true, new LibraryViewArea(controller));
		tableLibrary.setUpdate(true, new LibraryUpdateArea(controller, tableLibrary));
		tableLibrary.setDelete("deleteLibraryID");

		tableLibrary.setVisible(true);
		tableLibrary.setPageSize(10);
		
        
		tableLibrary.addListener(SWT.Resize, new Listener() {
             private static final long serialVersionUID = 8817895862824622805L;
             @Override
             public void handleEvent(Event event) {
                     refresh();
             }
            });
		
		
		CRUDTreeTableUtils.configUpdate(tableLibrary);
		CRUDTreeTableUtils.configReports(tableLibrary, contributorName(), searchCriteriaList);

		CRUDTreeTableUtils.configRemove(tableLibrary, new IActionCommand() {
			@Override
			public void execute(final TreeColumnEvent event) {
				tableLibrary.destroyEditableArea();
				Library library = (Library) event.entity.getRow();
				Long idLibrary = library.getLibraryID();
				((LibraryViewController) controller).deleteLibraryById(idLibrary);
				//tableLibrary.getPaginator().goToFirstPage();
				
				
			}
		});
		TreeTableColumn columns[] = {
				new TreeTableColumn(35, 0, "getLibraryName"),
				new TreeTableColumn(65, 1, "getAddress") };

		tableLibrary.createTable(columns);

		tableLibrary.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
				if (event.sortData != null) {
					direction = event.sortData.sortDirection;
					switch (event.sortData.columnIndex) {
					case 0:
						orderByString = "libraryName";
						break;
					case 1:
						orderByString = "address";
						break;
					}
				}
				searchLibraries(event.currentPage - 1, event.pageSize);
				refresh();
			}
		});

		consultButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -1030305633320960902L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				
				tableLibrary.destroyEditableArea();
				tableLibrary.clearRows();
				searchCriteriaList.clear();
				libraryNameConsult = nameLibraryText.getText();
				orderByString = "libraryName";
				direction = 1024;
				tableLibrary.getPaginator().goToFirstPage();

				UiUtils.get().addSearchCriteria(searchCriteriaList,
						MessageUtil.unescape(AbosMessages.get().LABEL_NAME),
						nameLibraryText.getText());

				if (tableLibrary.getRows().isEmpty()) {
					RetroalimentationUtils.showInformationMessage(parentTable, cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
				} 
				
			}
		});

		newSearchButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -5179607102055540891L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				searchCriteriaList.clear();
				tableLibrary.destroyEditableArea();
				nameLibraryText.setText("");
				nameLibraryText.setFocus();
				tableLibrary.destroyEditableArea();
				libraryNameConsult = null;
				orderByString = "libraryName";
				direction = 1024;
				Auxiliary.showLabelAndTable(listLibraryLabel, tableLibrary, true);
				tableLibrary.getPaginator().goToFirstPage();
			}
		});
		tableLibrary.getPaginator().goToFirstPage();
		l10n();
		
		
		//consultButton.getShell().setDefaultButton(consultButton);
		return shell;
	
	}

	private void searchLibraries(int page, int size) {
		Page<Library> list = ((LibraryViewController) controller).findByLibraryByParams(libraryNameConsult, page, size, direction, orderByString);
		tableLibrary.clearRows();
		tableLibrary.setTotalElements((int) list.getTotalElements());
		tableLibrary.setRows(list.getContent());
		tableLibrary.refresh();
		
	}

	@Override
	public boolean canClose() {
		return false;
	}

	@Override
	public void l10n() {
		manageLibraryLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_MANAGE_LIBRARIES));
		searchCriteriaLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEARCH_CRITERIA));
		nameLibraryLabel.setText(AbosMessages.get().LABEL_NAME);
		newSearchButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW_SEARCH));
		consultButton.setText(AbosMessages.get().BUTTON_CONSULT);
		listLibraryLabel.setText(AbosMessages.get().LABEL_LIST_OF_MATCHES);
		tableLibrary.setAddButtonText(AbosMessages.get().BUTTON_ADD);
		tableLibrary.setCancelButtonText(AbosMessages.get().BUTTON_CANCEL);
		tableLibrary.setColumnHeaders(Arrays.asList(AbosMessages.get().LABEL_NAME, MessageUtil.unescape(AbosMessages.get().LABEL_ADDREES)));
		tableLibrary.setActionButtonText("exportPDFButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_PDF));
		tableLibrary.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));
		
		listLibraryLabel.getParent().getParent().layout(true, true);
		listLibraryLabel.getParent().getParent().redraw();
		listLibraryLabel.getParent().getParent().update();

		tableLibrary.l10n();
	}
}
