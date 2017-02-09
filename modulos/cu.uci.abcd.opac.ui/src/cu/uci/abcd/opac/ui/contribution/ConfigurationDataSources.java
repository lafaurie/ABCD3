package cu.uci.abcd.opac.ui.contribution;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.opac.OpacDataSources;
import cu.uci.abcd.opac.l10n.AbosMessages;
import cu.uci.abcd.opac.ui.SecurityUtils;
import cu.uci.abcd.opac.ui.controller.AllManagementOpacViewController;
import cu.uci.abos.core.security.LoginService;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.Column;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeColumnListener;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class ConfigurationDataSources extends ContributorPage {

	private Label libraryLB;
	private Combo libraryCB;
	private LoginService loginService;
	private Label configurationHeader;
	private CRUDTreeTable databaseUse;
	private CRUDTreeTable databaseAvailable;
	private Library library;
	private Label databaseUseLb;
	private Label label;
	private Label databaseAvailableLB;
	private Button saveBt;
	private List<String> dataBaseName;
	private List<OpacDataSources> opacDataSourcesList = new ArrayList<OpacDataSources>();
	private List<OpacDataSources> opacDataSourcesInUse = new ArrayList<OpacDataSources>();
	private int testSize = 0;
	List<OpacDataSources> dataSourcesDelete = new ArrayList<>();;

	private OpacDataSources opacDataSources;
	private Page<OpacDataSources> listdataBase;
	private static String orderByString = "databaseName";
	private static int direction = 1024;

	@Override
	public Control createUIControl(final Composite parent) {

		loginService = SecurityUtils.getService();
		opacDataSourcesList.clear();

		addComposite(parent);
      
		parent.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
		parent.setLayout(new FormLayout());
		    
		Composite resize = new Composite(parent, 0);
		resize.setVisible(false);
		FormDatas.attach(resize).atTop().withWidth(0).withHeight(Display.getCurrent().getBounds().height - 100);

		
		configurationHeader = new Label(parent, 0);
		addHeader(configurationHeader);

		
		libraryLB = new Label(parent, SWT.NORMAL);
		add(libraryLB);

		libraryCB = new Combo(parent, SWT.READ_ONLY);
		add(libraryCB);
		
		br();
			
		saveBt = new Button(parent, SWT.PUSH);
		add(saveBt);
		saveBt.setVisible(false);

		br();

		label = new Label(parent, SWT.NORMAL);
		add(label, Percent.W100);

		databaseAvailableLB = new Label(parent, SWT.NORMAL);
		add(databaseAvailableLB);
		databaseAvailableLB.setText(MessageUtil.unescape(AbosMessages.get().DATABASE_AVAILABLE));
		databaseAvailableLB.setVisible(false);

		br();
		
		Image addDatabaseImg = new Image(parent.getDisplay(), RWT.getResourceManager().getRegisteredContent("right-arrow"));

		Column addDataBaseColumn = new Column(addDatabaseImg, new TreeColumnListener() {

			@Override
			public void handleEvent(TreeColumnEvent event) {
				try {

					OpacDataSources dataSources = ((OpacDataSources) event.entity.getRow());

					for (int i = 0; i < opacDataSourcesInUse.size(); i++)
						if (dataSources.getDatabaseName().equals(opacDataSourcesInUse.get(i).getDatabaseName()))
							throw new Exception();

					opacDataSourcesInUse.add(dataSources);

					databaseUse.clearRows();
					databaseUse.setTotalElements(opacDataSourcesInUse.size());
					databaseUse.setRows(opacDataSourcesInUse);
					databaseUse.refresh();

					databaseUse.getPaginator().goToFirstPage();

				} catch (Exception e) {
					showErrorMessage("La base de datos seleccionada ya está en uso.");
				}
			}
		});

		addDataBaseColumn.setToolTipText("Vincular");

		databaseAvailable = new CRUDTreeTable(parent, SWT.NONE);
		databaseAvailable.setEntityClass(OpacDataSources.class);
		databaseAvailable.addActionColumn(addDataBaseColumn);

		TreeTableColumn databaseAvailableColumns[] = { new TreeTableColumn(100, 0, "getDatabaseName") };

		databaseAvailable.createTable(databaseAvailableColumns);
		databaseAvailable.setPageSize(10);
		add(databaseAvailable);
		databaseAvailable.setVisible(false);
		
		br();
		
		databaseUseLb = new Label(parent, SWT.NORMAL);
		add(databaseUseLb);
		databaseUseLb.setText(MessageUtil.unescape(AbosMessages.get().DATABASE_USE));
		databaseUseLb.setVisible(false);

		databaseUse = new CRUDTreeTable(parent, SWT.NONE);
		databaseUse.setEntityClass(OpacDataSources.class);
		databaseUse.setDelete(true);

		TreeTableColumn databaseUseColumns[] = { new TreeTableColumn(100, 0, "getDatabaseName") };

		databaseUse.createTable(databaseUseColumns);
		databaseUse.setPageSize(10);
		add(databaseUse);
		databaseUse.setVisible(false);
	
		databaseUse.addDeleteListener(new TreeColumnListener() {
			@Override
			public void handleEvent(final TreeColumnEvent event) {
				try {

					databaseUse.clearRows();
					testSize = opacDataSourcesInUse.size();
					opacDataSourcesInUse.remove(event.entity.getRow());

					if (testSize == opacDataSourcesInUse.size())
						for (int i = 0; i < opacDataSourcesInUse.size(); i++) {
							if (opacDataSourcesInUse.get(i).getOpacDataSourcesID() == ((OpacDataSources) event.entity.getRow()).getOpacDataSourcesID()) {
								opacDataSourcesInUse.remove(i);
								dataSourcesDelete.add((OpacDataSources) event.entity.getRow());
							}
						}
					else {
						dataSourcesDelete.add((OpacDataSources) event.entity.getRow());
					}

					databaseUse.setTotalElements(opacDataSourcesInUse.size());
					databaseUse.setRows(opacDataSourcesInUse);
					databaseUse.refresh();

					databaseUse.getPaginator().goToFirstPage();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		databaseUse.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {

				databaseUse.getPaginator().goToPage(event.currentPage - 1);
				refresh();
			}
		});

		databaseUse.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();

			}
		});

		databaseAvailable.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();

			}
		});

		databaseAvailable.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {

				databaseAvailable.getPaginator().goToPage(event.currentPage - 1);
				refresh();
			}
		});

		databaseAvailable.addListener(SWT.Resize, new Listener() {

			private static final long serialVersionUID = -2654809274899010553L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();
			}
		});

		databaseUse.addListener(SWT.Resize, new Listener() {

			private static final long serialVersionUID = -2654809274899010553L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();
			}
		});

		libraryCB.addSelectionListener(new SelectionListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				library = (Library) UiUtils.getSelected(libraryCB);

				try {

					dataBaseName = new ArrayList<String>();
					opacDataSourcesList = new ArrayList<OpacDataSources>();
					dataBaseName = ((AllManagementOpacViewController) controller).getDataBaseNames(library.getIsisDefHome());
					opacDataSourcesInUse = ((AllManagementOpacViewController) controller).getAllDataSourcesByLibrary(library.getLibraryID());

				} catch (Exception e) {
					e.printStackTrace();
				}

				if (libraryCB.getSelectionIndex() != 0) {
					if (dataBaseName.isEmpty()) {
						showErrorMessage(MessageUtil.unescape(AbosMessages.get().MSG_ERROR_DATABASE));
						databaseUseLb.setVisible(false);
						databaseAvailableLB.setVisible(false);
						databaseAvailable.setVisible(false);
						databaseUse.setVisible(false);
//						saveBt.setVisible(false);

					} else {

						for (int i = 0; i < dataBaseName.size(); i++) {

							opacDataSources = new OpacDataSources();
							opacDataSources.setDatabaseName(dataBaseName.get(i));
							opacDataSources.setLibrary(library);

							opacDataSourcesList.add(opacDataSources);
						}

						databaseAvailable.clearRows();
						databaseAvailable.setRows(opacDataSourcesList);
						databaseAvailable.getPaginator().setTotalElements(opacDataSourcesList.size());
						databaseAvailable.refresh();
						databaseAvailable.getPaginator().goToFirstPage();

						searchDatabaseUse(0, databaseUse.getPaginator().getPageSize());
						databaseUse.getPaginator().goToFirstPage();

						databaseUseLb.setVisible(true);
						databaseAvailableLB.setVisible(true);
						databaseAvailable.setVisible(true);
						databaseUse.setVisible(true);
						saveBt.setVisible(true);
					}

				}

				else {
					databaseUseLb.setVisible(false);
					databaseAvailableLB.setVisible(false);
					databaseAvailable.setVisible(false);
					databaseUse.setVisible(false);
					saveBt.setVisible(false);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		saveBt.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				try {

					for (int i = 0; i < dataSourcesDelete.size(); i++) {

						((AllManagementOpacViewController) controller).deleteOpacDataSouce(dataSourcesDelete.get(i));
					}

					for (int i = 0; i < opacDataSourcesInUse.size(); i++) {
						opacDataSourcesList.get(i).setLibrary(library);
						((AllManagementOpacViewController) controller).addOpacDataSources(opacDataSourcesInUse.get(i));
					}

					dataSourcesDelete.clear();
					showInformationMessage(AbosMessages.get().MSG_INF_CONFIGURATION_DATASOURCES);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		loadLibraries();
		l10n();		
		return parent;
	}

	private void searchDatabaseUse(int page, int size) {

		databaseUse.clearRows();
		listdataBase = ((AllManagementOpacViewController) controller).findAllDataSourcesByLibrary(library.getLibraryID(), page, size, direction, orderByString);
		databaseUse.getPaginator().setTotalElements((int) listdataBase.getTotalElements());
		databaseUse.setRows(listdataBase.getContent());
		databaseUse.refresh();
	}

	/*
	 * private void createTableForSend(int page, int size) {
	 * databaseUse.setTotalElements((int) opacDataSourcesInUse.size()); if
	 * (opacDataSourcesInUse.size() <= page * size + size) {
	 * databaseUse.setRows(opacDataSourcesInUse.subList(page * size,
	 * opacDataSourcesInUse.size())); } else {
	 * databaseUse.setRows(opacDataSourcesInUse.subList(page * size, page * size
	 * + size)); } databaseUse.refresh(); }
	 */

	private void loadLibraries() {
		UiUtils.initialize(libraryCB, loginService.loadParams(), MessageUtil.unescape(AbosMessages.get().COMBO_LIBRARYS));
		if (RWT.getSettingStore().getAttribute("library") != null) {
			libraryCB.select(Integer.parseInt(RWT.getSettingStore().getAttribute("library")));
		}
	}

	@Override
	public String getID() {
		return "ConfigurationDataSourcesID";
	}

	@Override
	public void l10n() {
		libraryLB.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LIBRARYS));
		configurationHeader.setText(MessageUtil.unescape(AbosMessages.get().CONFIGURATION_HEADER));
		saveBt.setText(MessageUtil.unescape(AbosMessages.get().SAVE_BUTON));
		refresh();
	}

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().UI_DATA_SOURCE_CONFIGURATION);
	}

}
