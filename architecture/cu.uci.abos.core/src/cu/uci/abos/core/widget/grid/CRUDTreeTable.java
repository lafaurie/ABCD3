package cu.uci.abos.core.widget.grid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import cu.uci.abos.core.domain.Row;
import cu.uci.abos.core.l10n.AbosMessages;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PaginatorBar;

public class CRUDTreeTable extends Composite implements TreeTable {

	private static final long serialVersionUID = -5262924899912696650L;

	private Composite parent;
	private Tree tree;
	private List<IGridViewEntity> entities;

	private List<Column> columns, actionColumns;
	private IActionDenied actionDenied;
	private List<TreeColumn> treeColumns;
	private Image addImg, saveImg, checkedImg, uncheckedImg;
	private Composite clientComposite, contentComposite, editionComposite;
	private IEditableArea updateArea, addArea, watchArea;
	private IEditableArea activeArea;
	private Column watchColumn, updateColumn, deleteColumn;
	private boolean add, saveAll, search;
	private IGridViewEntity selectedEntity;
	private TreeItem selectedTreeItem;
	private ToolItem searchItem;
	private Class<?> entityClass;
	private IVisualEntityManager visualEntityManager;
	private List<String> columnHeaders;
	private String cancelButtonText, searchHintText, addButtonText, saveAllButtonText;
	private ToolItem addButton, saveAllButton;
	private Text searchText;
	private Button cancelButton;
	private Control lastButton;
	private Map<String, ButtonData> actionButtonsData;
	private Map<String, ToolItem> actionButtons;
	private SortData sortData;
	private PaginatorBar paginatorBar;
	private int pageSize;
	private List<PageChangeListener> pageChangedListeners;

	public CRUDTreeTable(Composite parent, int style) {
		super(parent, style);

		this.parent = parent;
		this.entityClass = null;
		this.entities = new LinkedList<>();
		this.actionColumns = new LinkedList<Column>();
		this.treeColumns = new LinkedList<>();
		this.columnHeaders = new LinkedList<>();
		this.pageChangedListeners = new LinkedList<>();
		this.actionButtonsData = new HashMap<>();
		this.actionButtons = new HashMap<>();

		this.add = false;
		this.search = false;
		this.saveAll = false;
		this.checkedImg = new Image(parent.getDisplay(), RWT.getResourceManager().getRegisteredContent("checked"));
		this.uncheckedImg = new Image(parent.getDisplay(), RWT.getResourceManager().getRegisteredContent("unchecked"));
		this.addImg = new Image(parent.getDisplay(), RWT.getResourceManager().getRegisteredContent("add"));
		this.saveImg = new Image(parent.getDisplay(), RWT.getResourceManager().getRegisteredContent("save"));
		this.cancelButtonText = "";
		this.addButtonText = "";
		this.searchHintText = "";
		this.saveAllButtonText = "";

		this.visualEntityManager = new VisualEntityManager(this);
		this.forwardResizedEvent();
	}

	public void setVisualEntityManager(IVisualEntityManager visualEntityManager) {
		this.visualEntityManager = visualEntityManager;
	}

	public IVisualEntityManager getVisualEntityManager() {
		return visualEntityManager;
	}

	@SuppressWarnings("serial")
	public void createTable(TreeTableColumn[] configuration) {

		if (this.entityClass == null) {
			throw new RuntimeException("You must set entity class before creating the tree table.");
		}

		this.setLayout(new FormLayout());
		this.clientComposite = new Composite(this, SWT.NONE);
		FormDatas.attach(this.clientComposite).atLeft(0).atRight(0);
		this.clientComposite.setLayout(new FormLayout());
		this.clientComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		this.contentComposite = new Composite(this.clientComposite, SWT.NONE);
		FormDatas.attach(this.contentComposite).atTop().atLeft(0).atRight(0);
		this.contentComposite.setLayout(new FormLayout());

		ToolBar toolBar1 = null;
		ToolBar toolBar2 = null;

		if (this.add || this.saveAll || this.actionButtonsData.size() > 0) {
			toolBar2 = new ToolBar(contentComposite, SWT.NONE);
			FormDatas.attach(toolBar2).atTop(0).atRight(0);
		}

		if (this.search) {
			toolBar1 = new ToolBar(contentComposite, SWT.NONE);
			FormDatas.attach(toolBar1).atTop(5).atLeft(0);
			this.searchItem = new ToolItem(toolBar1, SWT.SEPARATOR);
			Text text = new Text(toolBar1, SWT.BORDER);
			text.setMessage(this.searchHintText);
			this.searchText = text;
			searchItem.setControl(text);
			searchItem.setWidth(10);
		}

		for (Entry<String, ButtonData> entry : this.actionButtonsData.entrySet()) {
			ToolItem customButton = new ToolItem(toolBar2, SWT.PUSH);
			this.actionButtons.put(entry.getKey(), customButton);
			ButtonData buttonData = entry.getValue();
			if (buttonData.getListener() != null) {
				customButton.addSelectionListener(buttonData.getListener());
			}
			if (buttonData.getIcon() != null) {
				customButton.setImage(buttonData.getIcon());
			}
		}

		if (this.add) {
			ToolItem addItem = new ToolItem(toolBar2, SWT.PUSH);
			addItem.setToolTipText(this.addButtonText);
			addItem.setImage(this.addImg);
			this.addButton = addItem;
			addItem.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent event) {
					createAddArea();
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent event) {
				}
			});
		}

		if (this.saveAll) {
			ToolItem saveAllItem = new ToolItem(toolBar2, SWT.PUSH);
			saveAllItem.setToolTipText(this.saveAllButtonText);
			saveAllItem.setImage(this.saveImg);
			this.saveAllButton = saveAllItem;
		}

		Composite treeContainer = new Composite(this.contentComposite, SWT.NONE);
		treeContainer.setLayout(new FormLayout());

		this.tree = new Tree(treeContainer, SWT.BORDER | SWT.FULL_SELECTION | SWT.SCROLL_LOCK);

		FormDatas.attach(tree).atLeft(0).atRight(0);

		if (toolBar2 != null) {
			FormDatas.attach(treeContainer).atTopTo(toolBar2, 0).atLeft(0).atRight(0);
		} else if (toolBar1 != null) {
			FormDatas.attach(treeContainer).atTopTo(toolBar1, 5).atLeft(0).atRight(0);
		} else {
			FormDatas.attach(treeContainer).atTop(0).atLeft(0).atRight(0);
		}

		this.tree.setLinesVisible(true);
		this.tree.setHeaderVisible(true);

		this.columns = new LinkedList<Column>();

		for (TreeTableColumn config : configuration) {
			Column columnData = new Column(config.percentWidth, config.index);
			columnData.setDataExtractor(config.method);
			columnData.setParam(config.param);
			columnData.setNotDefinedMsg(config.notDefinedMsg);
			columnData.setOrder(config.order);
			columns.add(columnData);
		}

		for (int i = 0; i < columns.size(); i++) {
			Column columnData = columns.get(i);
			TreeColumn treeColumn = this.createTreeColumn(columnData, columnData.isOrder());
			String header = "";
			if (i < this.columnHeaders.size()) {
				header = this.columnHeaders.get(i);
			}
			columnData.setHeader(header);
			treeColumn.setText(header);
			this.treeColumns.add(treeColumn);
		}

		int columnCount = this.tree.getColumnCount();

		for (int i = 0; i < this.actionColumns.size(); i++) {
			Column columnData = this.actionColumns.get(i);
			columnData.setIndex(columnCount + i);
			this.createTreeColumn(columnData, false);
		}

		if (!this.actionColumns.isEmpty()) {

			tree.addListener(SWT.MouseDown, new Listener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void handleEvent(Event event) {
					Point pt = new Point(event.x, event.y);
					Rectangle clientArea = tree.getClientArea();
					Queue<TreeItem> itemsQueue = new LinkedList<TreeItem>(Arrays.asList(tree.getItems()));

					boolean handled = false;
					while (!itemsQueue.isEmpty() && !handled) {
						TreeItem treeItem = (TreeItem) itemsQueue.poll();
						itemsQueue.addAll(Arrays.asList(treeItem.getItems()));
						boolean visible = false;
						int firstActionColumnIndex = actionColumns.get(0).getIndex();
						for (int j = firstActionColumnIndex; j < tree.getColumnCount(); j++) {
							Rectangle cellBounds = treeItem.getBounds(j);
							if (cellBounds.contains(pt)) {
								Column actionColumn = actionColumns.get(j - firstActionColumnIndex);
								if (treeItem.getData("denied" + new Integer(actionColumn.getIndex()).toString()) != null) {
									handled = true;
									break;
								}
								TreeColumnEvent eventData = new TreeColumnEvent();
								eventData.item = treeItem;
								eventData.source = CRUDTreeTable.this;
								eventData.entity = (IGridViewEntity) treeItem.getData("entity");
								eventData.showEditableArea = true;
								selectedEntity = eventData.entity;
								actionColumn.fireListeners(eventData);
								handled = true;
								break;
							}
							if (!visible && cellBounds.intersects(clientArea)) {
								visible = true;
							}
							if (!visible) {
								break;
							}
						}
					}
				}
			});
		}

		tree.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent event) {
				if (!event.item.isDisposed()) {
					selectedEntity = ((IGridViewEntity) event.item.getData("entity"));
				} else {
					selectedEntity = null;
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		this.addControlListener(new ControlListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void controlResized(ControlEvent event) {
				CRUDTreeTable.this.controlResized(event);
			}

			@Override
			public void controlMoved(ControlEvent arg0) {
			}
		});

		paginatorBar = new PaginatorBar(treeContainer, SWT.NONE);
		FormDatas.attach(paginatorBar).atTopTo(this.tree, 0).atLeft(0).atRight(0);
		paginatorBar.setPageSize(this.pageSize);
		paginatorBar.addAllPageChangedListeners(this.pageChangedListeners);
		this.paginatorBar.goToFirstPage();
	}

	private TreeColumn createTreeColumn(final Column columnData, boolean order) {
		final TreeColumn treeColumn = new TreeColumn(tree, SWT.NONE);
		treeColumn.setWidth(20);
		treeColumn.setData("percent", columnData.getPercentWidth());
		treeColumn.setMoveable(columnData.getMoveable());
		treeColumn.setAlignment(columnData.getAlignment());
		treeColumn.setResizable(false);
		treeColumn.setImage(columnData.getImage());

		treeColumn.setToolTipText(columnData.getToolTipText());
		if (order) {
			treeColumn.addSelectionListener(new SelectionListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent event) {
					Tree tree = treeColumn.getParent();
					int sortDirection = -1;
					if (treeColumn == tree.getSortColumn()) {
						sortDirection = tree.getSortDirection() == SWT.UP ? SWT.DOWN : SWT.UP;
					} else {
						tree.setSortColumn(treeColumn);
						sortDirection = SWT.DOWN;
					}
					tree.setSortDirection(sortDirection);
					sortData = new SortData();
					sortData.sortDirection = sortDirection;
					sortData.columnHeader = columnData.getHeader();
					sortData.columnIndex = columnData.getIndex();
					sortData.sortExpression = columnData.getDataExtractor();
					paginatorBar.setSortData(sortData);
					paginatorBar.goToFirstPage();
				}

				@Override
				public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent arg0) {
				}
			});
		}

		return treeColumn;
	}

	public void addRow(IGridViewEntity entity) {
		if (entity.getParent() == null) {
			this.entities.add(entity);
			this.createTreeItem(entity, null);
			this.setTotalElements(getPaginator().getTotalElements() + 1);
		}
	}

	public void removeRow(IGridViewEntity entity) {
		if (entity != null) {
			if (this.entities.remove(entity)) {
				for (int i = 0; i < this.tree.getItems().length; i++) {
					TreeItem item = this.tree.getItems()[i];
					if (item.getData("entity").equals(entity)) {
						item.dispose();
						break;
					}
				}
				this.setTotalElements(this.paginatorBar.getTotalElements() - 1);
				this.paginatorBar.refresh();
			}
		}
	}

	public <TE extends IGridViewEntity> void addRows(List<TE> entities) {
		for (IGridViewEntity entity : entities) {
			if (entity.getParent() == null) {
				this.entities.add(entity);
			}
			this.createTreeItem(entity, null);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <TE extends Row> void setRows(List<TE> entities) {
		this.clearRows();
		List<IGridViewEntity> gridEntities = new ArrayList<IGridViewEntity>();
		for (Row row : entities) {
			gridEntities.add(new BaseGridViewEntity(row));
		}
		this.addRows(gridEntities);
	}

	public List<IGridViewEntity> getRows() {
		List<IGridViewEntity> result = new LinkedList<>();
		for (TreeItem treeItem : this.tree.getItems()) {
			result.add((IGridViewEntity) treeItem.getData("entity"));
		}
		return result;
	}

	private void createTreeItem(IGridViewEntity entity, TreeItem parent) {
		TreeItem item = null;
		TreeItem parentItem = parent;
		if (entity.getParent() != null) {
			if (parentItem != null) {
				item = new TreeItem(parentItem, SWT.NONE);
			} else {
				Queue<TreeItem> queue = new LinkedList<TreeItem>();
				queue.addAll(Arrays.asList(this.tree.getItems()));
				while (!queue.isEmpty()) {
					TreeItem treeItem = queue.poll();
					if (((IGridViewEntity) treeItem.getData("entity")).equals(entity.getParent())) {
						parentItem = treeItem;
						item = new TreeItem(parentItem, SWT.NONE);
						break;
					}
					queue.addAll(Arrays.asList(treeItem.getItems()));
				}
			}
		} else {
			item = new TreeItem(tree, SWT.NONE);
		}
		for (int i = 0; i < this.columns.size(); i++) {
			Column columnData = this.columns.get(i);
			String text = String.valueOf(columnData.getCellData(entity));
			if (text.toLowerCase().equals("true") || text.toLowerCase().equals("false")) {
				Image img = Boolean.parseBoolean(text) ? checkedImg : uncheckedImg;
				item.setImage(i, img);
			} else {
				item.setText(i, text);
			}
			item.setData(String.valueOf(i), text);
		}

		for (int i = 0; i < actionColumns.size(); i++) {
			Column actionColumn = actionColumns.get(i);
			if (actionDenied != null) {
				if (actionDenied.isDenied(actionColumn, entity.getRow())) {
					item.setData("denied" + new Integer(actionColumn.getIndex()).toString(), Boolean.TRUE);
				} else {
					item.setImage(actionColumn.getIndex(), actionColumn.getImage());
				}
			} else {
				item.setImage(actionColumn.getIndex(), actionColumn.getImage());
			}
		}

		if (item != null) {
			item.setData("entity", entity);
		}

		for (IGridViewEntity child : entity.getChildren()) {
			createTreeItem(child, item);
		}
	}

	public List<IGridViewEntity> getEntities() {
		List<IGridViewEntity> result = new LinkedList<>();
		if (this.tree != null && this.tree.getItems() != null) {
			for (TreeItem treeItem : this.tree.getItems()) {
				result.add((IGridViewEntity) treeItem.getData("entity"));
			}
		}
		return result;
	}

	public void refresh() {
		if (!CRUDTreeTable.this.tree.isDisposed()) {
			this.setSize(this.computeSize(this.getClientArea().width, SWT.DEFAULT));
			this.layout(true, true);
			this.update();
			this.redraw();
		}

	}

	private void controlResized(ControlEvent event) {
		int actionsWidth = this.actionColumns.size() * 20;
		int parentWidth = this.getSize().x - actionsWidth;
		int sum = 0;
		for (TreeColumn treeColumn : this.treeColumns) {
			if (!treeColumn.isDisposed()) {
				int percent = (int) treeColumn.getData("percent");
				int percentWidth = (int) (percent * parentWidth / 100);
				treeColumn.setWidth(percentWidth);
				sum += percentWidth;
			}
		}

		if (sum != parentWidth) {
			TreeColumn lastColumn = this.treeColumns.get(this.treeColumns.size() - 1);
			lastColumn.setWidth(lastColumn.getWidth() + (parentWidth - sum));
		}

		int searchWidth = 40 * parentWidth / 100;

		if (searchWidth > 300) {
			searchWidth = 300;
		} else if (searchWidth < 100) {
			searchWidth = 100;
		}

		if (this.searchItem != null && !this.searchItem.isDisposed()) {
			this.searchItem.setWidth(searchWidth);
		}
		this.refresh();
	}

	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}

	public void setColumnHeaders(List<String> headers) {
		this.columnHeaders = headers;
		for (int i = 0; i < this.treeColumns.size(); i++) {
			treeColumns.get(i).setText(columnHeaders.get(i));
		}
	}

	public List<String> getColumnHeaders() {
		return this.columnHeaders;
	}

	public void setCancelButtonText(String text) {
		this.cancelButtonText = text;
		if (this.cancelButton != null && !this.cancelButton.isDisposed()) {
			this.cancelButton.setText(text);
		}
	}

	public void setAddButtonText(String text) {
		this.addButtonText = text;
		if (this.addButton != null && !this.addButton.isDisposed()) {
			this.addButton.setToolTipText(text);
		}
	}

	public void setSearchHintText(String text) {
		this.searchHintText = text;
		if (this.searchText != null && !this.searchText.isDisposed()) {
			this.searchText.setMessage(text);
		}
	}

	public void setSaveAllButtonText(String text) {
		this.saveAllButtonText = text;
		if (this.saveAllButton != null && !this.saveAllButton.isDisposed()) {
			this.saveAllButton.setToolTipText(text);
		}
	}

	public void setAdd(boolean add, IEditableArea editableArea) {
		this.add = add;
		this.addArea = editableArea;
	}

	public void setWatch(boolean watch, IEditableArea editableArea) {
		if (watch && this.watchColumn == null) {
			Image eyeImg = new Image(parent.getDisplay(), RWT.getResourceManager().getRegisteredContent("eye"));
			this.watchArea = editableArea;
			this.watchColumn = new Column(eyeImg, editableArea, new TreeColumnListener() {
				public void handleEvent(TreeColumnEvent event) {
					if (event.showEditableArea) {
						selectedTreeItem = event.item;
						selectedEntity = (IGridViewEntity) selectedTreeItem.getData("entity");
						createEditableArea(event.editableArea, event.entity, visualEntityManager);
					}
				}
			});
			this.watchColumn.setToolTipText(AbosMessages.get().BUTTON_DETAILS);
			this.watchColumn.setAlignment(SWT.CENTER);
			if (deleteColumn != null && updateColumn != null) {
				this.actionColumns.add(actionColumns.size() - 2, this.watchColumn);
			} else {
				if (deleteColumn == null && updateColumn == null) {
					this.actionColumns.add(this.watchColumn);
				} else {
					this.actionColumns.add(actionColumns.size() - 1, this.watchColumn);
				}
			}

		}
	}

	public void setUpdate(boolean update, IEditableArea editableArea) {
		if (update && this.updateColumn == null) {
			Image pencilImg = new Image(parent.getDisplay(), RWT.getResourceManager().getRegisteredContent("pencil"));
			this.updateColumn = new Column(pencilImg, editableArea, new TreeColumnListener() {
				public void handleEvent(TreeColumnEvent event) {
					if (event.showEditableArea) {
						selectedTreeItem = event.item;
						selectedEntity = (IGridViewEntity) selectedTreeItem.getData("entity");
						createEditableArea(event.editableArea, event.entity, visualEntityManager);
					}
				}
			});
			this.updateColumn.setToolTipText(AbosMessages.get().BUTTON_EDIT);
			this.updateColumn.setAlignment(SWT.CENTER);
			if (deleteColumn != null) {
				this.actionColumns.add(actionColumns.size() - 1, this.updateColumn);
			} else {
				this.actionColumns.add(this.updateColumn);
			}

		}
	}

	public void setDelete(boolean delete) {
		if (delete && this.deleteColumn == null) {
			Image redCrossImg = new Image(parent.getDisplay(), RWT.getResourceManager().getRegisteredContent("redcross"));
			this.deleteColumn = new Column(redCrossImg, new TreeColumnListener() {
				public void handleEvent(TreeColumnEvent event) {
					destroyEditableArea();
					if (event.performDelete) {
						event.item.dispose();
						if (paginatorBar.getCurrentPage() > paginatorBar.getTotalPages()) {
							paginatorBar.goToLastPage();
						} else {
							paginatorBar.goToPage(paginatorBar.getCurrentPage());
						}
						event.source.refresh();
					}
				}
			});
			deleteColumn.setToolTipText(AbosMessages.get().BUTTON_REMOVED);
			deleteColumn.setAlignment(SWT.CENTER);
			this.actionColumns.add(deleteColumn);
		}
	}

	public void setSearch(boolean search) {
		this.search = search;
	}

	public void setSaveAll(boolean saveAll) {
		this.saveAll = saveAll;
	}

	public IGridViewEntity getSelectedEntity() {
		return selectedEntity;
	}

	public void setCustomActionColumns(List<Column> actionColumns) {
		this.actionColumns = actionColumns;
	}

	public void addActionColumn(Column column) {
		column.addListener(new TreeColumnListener() {
			public void handleEvent(TreeColumnEvent event) {
				if (event.showEditableArea) {
					selectedTreeItem = event.item;
					selectedEntity = (IGridViewEntity) selectedTreeItem.getData("entity");
					createEditableArea(event.editableArea, event.entity, visualEntityManager);
				}
			}
		});
		if (deleteColumn != null && watchColumn != null && updateColumn != null) {
			this.actionColumns.add(actionColumns.size() - 3, column);
		} else {
			if ((deleteColumn != null && watchColumn != null) || (watchColumn != null && updateColumn != null) || (deleteColumn != null && updateColumn != null)) {
				this.actionColumns.add(actionColumns.size() - 2, column);
			} else {
				if ((deleteColumn != null) || (watchColumn != null) || (updateColumn != null)) {
					this.actionColumns.add(actionColumns.size() - 1, column);
				} else {
					this.actionColumns.add(column);
				}
			}
		}

	}

	public void createUpdateArea(IGridViewEntity entity, final TreeItem treeItem) {
		if (this.updateArea != null) {
			this.selectedTreeItem = treeItem;
			createEditableArea(this.updateArea, entity, this.visualEntityManager);
		}
	}

	public void createWatchArea(IGridViewEntity entity) {
		if (this.watchArea != null) {
			createEditableArea(this.watchArea, entity, this.visualEntityManager);
		}
	}

	public void createAddArea() {
		this.selectedTreeItem = null;
		if (this.addArea != null) {
			createEditableArea(this.addArea, null, this.visualEntityManager);
		}
	}

	public int getHeight() {
		return (this.entities.size() * 28) + 70;
	}

	public void destroyEditableArea() {
		if (this.editionComposite != null && !this.editionComposite.isDisposed()) {
			this.editionComposite.dispose();
		}
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public void createEditableArea(IEditableArea area, IGridViewEntity entity, IVisualEntityManager manager) {
		if (editionComposite != null && !editionComposite.isDisposed()) {
			editionComposite.dispose();
		}
		if (area != null) {
			this.editionComposite = new Composite(this.clientComposite, SWT.BORDER);
			this.editionComposite.setLayout(new FormLayout());
			FormDatas.attach(this.editionComposite).atTopTo(this.contentComposite, 5).atLeft(0).atRight(0).atBottom(15);

			Composite editionControls = new Composite(this.editionComposite, SWT.NONE);
			editionControls.setLayout(new FormLayout());
			FormDatas.attach(editionControls).atTop(0).atLeft(0).atRight(0);

			Composite editionButtons = new Composite(this.editionComposite, SWT.NONE);
			editionButtons.setLayout(new FormLayout());
			FormDatas.attach(editionButtons).atTopTo(editionControls).atLeft(0).atRight(0);
			editionControls.getBounds().width = clientComposite.getBounds().width;
			area.initialize();
			area.createUI(editionControls, entity, manager);
			Composite buttonComposite = area.createButtons(editionButtons, entity, manager);

			List<Control> controls = new LinkedList<>();
			if (buttonComposite.getChildren() != null) {
				for (Control control : buttonComposite.getChildren()) {
					controls.add(control);
				}
			}

			this.activeArea = area;
			if (area.closable()) {
				Button closeBtn = new Button(editionButtons, SWT.PUSH);
				if (area == watchArea) {
					closeBtn.setText(AbosMessages.get().BUTTON_CLOSE);
					closeBtn.addSelectionListener(new SelectionListener() {
						private static final long serialVersionUID = 1L;

						@Override
						public void widgetSelected(SelectionEvent event) {
							if (editionComposite != null && !editionComposite.isDisposed()) {
								editionComposite.dispose();
							}
							CRUDTreeTable.this.activeArea = null;
							CRUDTreeTable.this.notifyListeners(SWT.Resize, new Event());
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent event) {
						}
					});
				} else {
					if (this.cancelButtonText != null && !this.cancelButtonText.isEmpty())
						closeBtn.setText(this.cancelButtonText);
					else
						closeBtn.setText(AbosMessages.get().BUTTON_CANCEL);

					closeBtn.addSelectionListener(new SelectionListener() {
						private static final long serialVersionUID = 1L;

						@Override
						public void widgetSelected(SelectionEvent event) {

							MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(AbosMessages.get().MESSAGE_QUESTION), MessageUtil.unescape(AbosMessages.get().MSG_WARN_CANCEL_OPERATION), new DialogCallback() {
								private static final long serialVersionUID = -7808192281183547550L;

								@Override
								public void dialogClosed(int returnCode) {
									if (returnCode == 0) {
										if (editionComposite != null && !editionComposite.isDisposed()) {
											try {
												if (editionComposite != null && !editionComposite.isDisposed()) {
													Control[] childrens = editionComposite.getChildren();
													for (int i = 0; i < childrens.length; i++) {
														if (childrens[i] != null && !childrens[i].isDisposed()) {
															childrens[i].dispose();
														}
													}
												}
											} catch (Exception e) {
												// TODO: Tragamiento de
												// excepciones
											}
											editionComposite.dispose();
										}
										CRUDTreeTable.this.activeArea = null;
										CRUDTreeTable.this.notifyListeners(SWT.Resize, new Event());
									}
								}
							});
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent event) {
						}
					});
				}

				FormDatas.attach(closeBtn).atRight(15).withHeight(23).atBottom(10).atTop(10);

				this.cancelButton = closeBtn;
				this.lastButton = closeBtn;

				for (Control control : controls) {
					addFootButton(control);
				}
			}

			this.parent.getShell().layout();
			this.parent.getShell().redraw();
			this.parent.getShell().update();

			this.clientComposite.layout(true, true);
			this.clientComposite.redraw();
			this.clientComposite.update();

			this.editionComposite.addDisposeListener(new DisposeListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void widgetDisposed(DisposeEvent event) {
					lastButton = null;
					try {
						if (editionComposite != null && !editionComposite.isDisposed()) {
							Control[] childrens = editionComposite.getChildren();
							for (int i = 0; i < childrens.length; i++) {
								if (childrens[i] != null && !childrens[i].isDisposed()) {
									childrens[i].dispose();
								}
							}
						}
					} catch (Exception e) {
						// TODO: Tragamiento de excepciones
					}

					parent.pack(true);
					parent.getParent().pack(true);
					CRUDTreeTable.this.getShell().layout(true, true);
					CRUDTreeTable.this.getShell().redraw();
					CRUDTreeTable.this.getShell().update();

				}
			});

			this.activeArea.l10n();
		}
		if (cancelButton != null && !cancelButton.isDisposed()) {
			cancelButton.forceFocus();
		}
		CRUDTreeTable.this.getShell().layout(true, true);
		CRUDTreeTable.this.getShell().update();
		CRUDTreeTable.this.getShell().redraw();

	}

	public void saveEntity(TreeItem treeItem, IGridViewEntity entity) {
		for (int i = 0; i < columns.size(); i++) {
			try {
				Column column = columns.get(i);
				String text = String.valueOf(column.getCellData(entity));
				if (text.toLowerCase().equals("true") || text.toLowerCase().equals("false")) {
					Image img = Boolean.parseBoolean(text) ? checkedImg : uncheckedImg;
					treeItem.setImage(i, img);
				} else {
					treeItem.setText(i, text);
				}
				treeItem.setData(String.valueOf(i), text);
			} catch (Exception e) {
			}
		}
		treeItem.setData("entity", entity);
	}

	public void addUpdateListener(TreeColumnListener listener) {
		if (this.updateColumn != null) {
			this.updateColumn.addListener(listener);
		}
	}

	public void addDeleteListener(TreeColumnListener listener) {
		if (this.deleteColumn != null) {
			this.deleteColumn.addListener(listener);
		}
	}

	public void clearRows() {
		this.entities.clear();
		for (TreeItem treeItem : this.tree.getItems()) {
			treeItem.dispose();
		}
		this.refresh();
	}

	public String[][] getCellValues() {
		String[][] result = new String[this.tree.getItems().length][this.columnHeaders.size()];
		for (int i = 0; i < this.tree.getItems().length; i++) {
			for (int j = 0; j < this.columnHeaders.size(); j++) {
				result[i][j] = this.tree.getItem(i).getText(j);
			}
		}
		return result;
	}

	public void addActionButton(String key, ButtonData buttonData) {
		this.actionButtonsData.put(key, buttonData);
	}

	public void setActionButtonText(String key, String text) {
		if (this.actionButtons.containsKey(key)) {
			this.actionButtons.get(key).setToolTipText(text);
		}
	}

	public void l10n() {
		if (this.activeArea != null && this.editionComposite != null && !this.editionComposite.isDisposed()) {
			try {
				this.activeArea.l10n();
			} catch (Exception e) {
			}
			this.refresh();
		}

	}

	public PaginatorBar getPaginator() {
		return this.paginatorBar;
	}

	private void forwardResizedEvent() {
		this.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = -426351555819171155L;

			@Override
			public void handleEvent(Event event) {
				if (CRUDTreeTable.this.getParent() != null) {
					CRUDTreeTable.this.getParent().notifyListeners(SWT.Resize, event);
				}
			}
		});
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		if (this.paginatorBar != null) {
			this.paginatorBar.setPageSize(pageSize);
		}
	}

	public void addPageChangeListener(PageChangeListener listener) {
		this.pageChangedListeners.add(listener);
		if (this.paginatorBar != null) {
			this.paginatorBar.addPageChangeListener(listener);
		}
	}

	public void setTotalElements(int totalElements) {
		if (this.paginatorBar != null) {
			this.paginatorBar.setTotalElements(totalElements);
		}
	}

	public void addFootButton(Control button) {
		if (lastButton != null) {
			FormDatas.attach(button).atRightTo(lastButton, 15).withHeight(23).atBottom(10).atTop(10);
		} else {
			FormDatas.attach(button).atRight(15).withHeight(23).atBottom(10).atTop(10);
		}
		lastButton = button;
	}

	public int getPageSize() {
		if (pageSize == 0) {
			setPageSize(10);
		}
		return pageSize;
	}

	public void setActionDenied(IActionDenied actionDenied) {
		this.actionDenied = actionDenied;
	}

	@Override
	public void onPageChange() {
		this.getPaginator().onPageChange();
	}

	public IEditableArea getActiveArea() {
		return activeArea;
	}

	public TreeItem getSelectedTreeItem() {
		return selectedTreeItem;
	}

	public void setSelectedTreeItem(TreeItem selectedTreeItem) {
		this.selectedTreeItem = selectedTreeItem;
	}

}