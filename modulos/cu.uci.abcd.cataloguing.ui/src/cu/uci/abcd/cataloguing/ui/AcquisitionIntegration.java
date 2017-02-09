package cu.uci.abcd.cataloguing.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.TreeItem;
import org.springframework.data.domain.Page;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.cataloguing.ILoanObjectCreation;
import cu.uci.abcd.cataloguing.controller.ProxyController;
import cu.uci.abcd.cataloguing.listener.EventAllwaysOpen;
import cu.uci.abcd.cataloguing.listener.EventSaveRecord;
import cu.uci.abcd.cataloguing.listener.EventStartRecord;
import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.Column;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.MessageVisualEntityManager;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeColumnListener;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;
import cu.uci.abos.l10n.cataloguing.AbosMessages;
import cu.uci.abos.l10n.cataloguing.MessageUtil;
import cu.uci.abos.widget.template.component.TemplateCompound;
import cu.uci.abos.widget.template.util.Util;

public class AcquisitionIntegration extends ContributorPage {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private ProxyController controller;
	private ILoanObjectCreation loanObjectCreation;
	private IJisisDataProvider service;
	private CRUDTreeTable grid;
	private int direction = 1024;
	private String orderByString = "id";
	private int width;
	private int height;
	private String dataBaseName;
	private Composite view;
	private ExpandItem expandItem;
	private Composite recordsParent;

	public AcquisitionIntegration() {
		super();
		properties = new HashMap<String, Object>();
		properties.put(NOT_SCROLLED, Boolean.FALSE);
	}

	public void setProxyController(ProxyController controller) {
		this.controller = controller;
		this.loanObjectCreation = controller.getLoanObjectCreationService();
		this.service = controller.getDataBaseManagerService().getService();
	}

	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}

	@Override
	public Control createUIControl(Composite arg0) {

		this.width = arg0.getShell().getBounds().width - 320;
		this.height = arg0.getShell().getBounds().height - 150;

		view = new Composite(arg0, 0);
		view.setLayout(new FormLayout());
		view.setData(RWT.CUSTOM_VARIANT, "gray_background");

		FormDatas.attach(view).atLeft(0).atRight(0).atTop(0).atBottom(0).withWidth(width).withHeight(height);

		ToolBar bar = new ToolBar(view, SWT.WRAP | SWT.FLAT);

		ToolItem startView = new ToolItem(bar, 0);
		Image startViewImage = new Image(view.getDisplay(), RWT.getResourceManager().getRegisteredContent("list-alt"));
		startView.setImage(startViewImage);
		startView.setToolTipText(AbosMessages.get().TOOL_ITEM_MAIN_VIEW);

		FormDatas.attach(bar).atTop(0).atLeft(5);

		startView.addListener(SWT.Selection, new EventStartRecord(view, (ProxyController) controller));

		ExpandBar expandBar = new ExpandBar(view, SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		FormDatas.attach(expandBar).atLeft(0).atRight(0).atTopTo(bar, 0).atBottom(0);
		expandItem = new ExpandItem(expandBar, SWT.NONE);
		expandItem.setText(MessageUtil.unescape(AbosMessages.get().EXPAND_ITEM_PRECATALOGUING_LOAN_OBJECT_LIST));
		expandBar.addExpandListener(new EventAllwaysOpen());

		recordsParent = new Composite(expandBar, 0);
		recordsParent.setLayout(new FormLayout());
		recordsParent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(recordsParent).atTop(0).atLeft(0);

		grid = new CRUDTreeTable(recordsParent, 0);

		grid.setEntityClass(LoanObject.class);
		grid.setVisualEntityManager(new MessageVisualEntityManager(grid));

		Column cataloguingColumn = new Column("tag", arg0.getDisplay(), new TreeColumnListener() {
			@Override
			public void handleEvent(TreeColumnEvent event) {
				// event
				TreeItem selectedTreeItem = event.item;
				IGridViewEntity selectedEntity = (IGridViewEntity) selectedTreeItem.getData("entity");
				LoanObject loanObject = selectedEntity.getRow();
				String controlNumber = loanObject.getControlNumber();
				try {

					try {
						String isisDefHome = Util.getDefHome();
						List<String> controlNumbers = new ArrayList<String>();
						controlNumbers.add(controlNumber);

						List<Record> records = service.findByRecordNumber(controlNumbers, dataBaseName, isisDefHome);
						Record record = records.get(0);

						// erase view
						final Composite superArg0 = view.getParent();
						view.dispose();

						superArg0.getShell().layout(true, true);
						superArg0.getShell().redraw();
						superArg0.getShell().update();

						final TemplateCompound component = new TemplateCompound(superArg0, 0, record, dataBaseName, service);
						component.createEditComponent();
						component.setLayout(new FormLayout());
						FormDatas.attach(component).atTopTo(superArg0, 0).atLeftTo(superArg0, 0);

						Button buttonSave = component.getButtonSave();

						buttonSave.addListener(SWT.Selection, new EventSaveRecord(controller, dataBaseName, component, record, true));

						ToolItem back = component.getBackItem();
						back.addListener(SWT.Selection, new Listener() {

							/**
						 * 
						 */
							private static final long serialVersionUID = 1L;

							@Override
							public void handleEvent(Event arg0) {

								component.dispose();
								superArg0.getShell().layout(true, true);
								superArg0.getShell().redraw();
								superArg0.getShell().update();

								AcquisitionIntegration ai = new AcquisitionIntegration();
								ai.setDataBaseName(dataBaseName);
								ai.setHeigth(height);
								ai.setProxyController(controller);

								ai.createUIControl(superArg0);

								superArg0.getShell().layout(true, true);
								superArg0.getShell().redraw();
								superArg0.getShell().update();
							}
						});

					} catch (JisisDatabaseException e) {
						RetroalimentationUtils.showErrorShellMessage("Problema de conección con JISIS");
					}
					   
					grid.refresh();

				} catch (Exception e) {
				}
			}
		});

		cataloguingColumn.setToolTipText(AbosMessages.get().GRID_ITEM_CATALOG);
		cataloguingColumn.setAlignment(SWT.CENTER);
		grid.addActionColumn(cataloguingColumn);

		TreeTableColumn columns[] = { new TreeTableColumn(20, 0, "getLoanObjectID"), new TreeTableColumn(20, 1, "getControlNumber"), new TreeTableColumn(20, 2, "getTitle"), new TreeTableColumn(20, 3, "getAuthor"), new TreeTableColumn(20, 4, "getSituation") };

		grid.createTable(columns);
		grid.setPageSize(10);

		grid.setColumnHeaders(Arrays.asList(AbosMessages.get().GRID_COLUMN_HEADER_ID, MessageUtil.unescape(AbosMessages.get().GRID_COLUMN_HEADER_CONTROL_NUMBER), MessageUtil.unescape(AbosMessages.get().GRID_COLUMN_HEADER_TITLE), AbosMessages.get().GRID_COLUMN_HEADER_AUTHOR,
				MessageUtil.unescape(AbosMessages.get().GRID_COLUMN_HEADER_SITUATION)));

		searchLoanObjects(0, grid.getPageSize());

		FormDatas.attach(grid).atLeft(0).atRight(0).atTop(0);

		grid.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {

				searchLoanObjects(event.currentPage - 1, event.pageSize);
				expandItem.setHeight(recordsParent.computeSize(SWT.DEFAULT, SWT.DEFAULT).y + 5);

				// SetControl
				expandItem.setControl(recordsParent);
				expandItem.setExpanded(true);

				view.getShell().layout(true, true);
				view.getShell().redraw();
				view.getShell().update();

				view.getParent().update();
				view.getParent().redraw();
				view.getParent().notifyListeners(SWT.Resize, new Event());
			}
		});

		grid.getPaginator().goToFirstPage();

		expandItem.setHeight(recordsParent.computeSize(SWT.DEFAULT, SWT.DEFAULT).y + 5);

		// SetControl
		expandItem.setControl(recordsParent);
		expandItem.setExpanded(true);

		arg0.getShell().layout(true, true);
		arg0.getShell().redraw();
		arg0.getShell().update();

		return arg0;
	}

	private void searchLoanObjects(int page, int size) {
		grid.clearRows();
		Page<LoanObject> loanObjects = loanObjectCreation.findAllByPrecataloguing(page, size, direction, orderByString);

		grid.setTotalElements((int) loanObjects.getTotalElements());
		if ((int) loanObjects.getTotalElements() > 0) {
			grid.setRows(loanObjects.getContent());
			grid.refresh();
		}
	}

	@Override
	public String getID() {
		return null;
	}

	@Override
	public void l10n() {
	}

	@Override
	public boolean canClose() {
		return false;
	}

	@Override
	public String contributorName() {
		return null;
	}

	@Override
	public void setViewController(ViewController arg0) {
	}
}
