package cu.uci.abcd.cataloguing.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.unesco.jisis.corelib.common.FormattedRecord;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.cataloguing.controller.ProxyController;
import cu.uci.abcd.cataloguing.listener.EventAllwaysOpen;
import cu.uci.abcd.cataloguing.listener.EventBack;
import cu.uci.abcd.cataloguing.ui.ReferenceView;
import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.l10n.cataloguing.AbosMessages;
import cu.uci.abos.l10n.cataloguing.MessageUtil;
import cu.uci.abos.widget.show.content.ShowContent;
import cu.uci.abos.widget.show.content.domain.ShowContentEvent;
import cu.uci.abos.widget.show.content.showContentPaginatorEvent.PageChangeEvent;
import cu.uci.abos.widget.template.util.Util;

public class RecordQuery extends ContributorPage {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */
	private Composite view;
	private ProxyController controller;
	private ToolBar bar;
	private String dataBaseName;
	private ReferenceView referenceView;
	private ExpandItem expandItem;
	private Composite recordsParent;

	public void setController(ProxyController controller) {
		this.controller = controller;
	}

	private List<Record> records;

	public void setRecords(List<Record> records) {
		this.records = records;
	}

	@Override
	public Control createUIControl(Composite superArg0) {

		final int width = superArg0.getShell().getBounds().width - 290;
		final int height = superArg0.getShell().getBounds().height - 150;

		view = new Composite(superArg0, SWT.BORDER);
		view.setLayout(new FormLayout());
		view.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(view).withWidth(width).withHeight(height);

		bar = new ToolBar(view, SWT.WRAP | SWT.FLAT);
		ToolItem back = new ToolItem(bar, 0);
		back.setToolTipText(AbosMessages.get().TOOL_ITEM_BACK_VIEW);
		Image image = new Image(view.getDisplay(), RWT.getResourceManager().getRegisteredContent("left-arrow"));
		back.setImage(image);
		FormDatas.attach(bar).atTop(0).atLeft(5);
		back.addListener(SWT.Selection, new EventBack(view, controller, referenceView));

		ExpandBar expandBar = new ExpandBar(view, SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		FormDatas.attach(expandBar).atLeft(0).atRight(0).atTopTo(bar, 0).atBottom(0);

		expandItem = new ExpandItem(expandBar, 0);
		expandItem.setText(AbosMessages.get().EXPAND_QUERY_RESULT);
		expandBar.addExpandListener(new EventAllwaysOpen());

		recordsParent = new Composite(expandBar, 0);
		recordsParent.setLayout(new FormLayout());
		recordsParent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(recordsParent).atTop(0).atLeft(0).atRight(0);
		// FormDatas.attach(recordsParent).atTop(0).atLeft(0);

		if (records != null && records.size() > 0) {
			final ShowContent showComponent = new ShowContent(recordsParent, 0, records);
			FormDatas.attach(showComponent).atLeft(0).atRight(0).atTop(-55).atBottom(0);

			Image actionImg = new Image(view.getDisplay(), RWT.getResourceManager().getRegisteredContent("selection-list"));
			showComponent.createButton(actionImg, AbosMessages.get().GRID_ITEM_SEE);

			showComponent.addListener(3, new Listener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void handleEvent(Event event) {

					String controlNumber = ((ShowContentEvent) event).standarRecordIsis.getControlNumber();

					if (controlNumber.equals("") || controlNumber == null) {
						RetroalimentationUtils.showErrorShellMessage(MessageUtil.unescape(AbosMessages.get().MESSAGE_THE_REGISTER_HAS_NOT_CONTROL_NUMBER));
					} else {
						List<String> controlNumbers = new ArrayList<String>();
						controlNumbers.add(controlNumber);

						List<Record> records = null;

						IJisisDataProvider service = null;

						try {
							service = controller.getDataBaseManagerService().getService();
							records = service.findByRecordNumber(controlNumbers, dataBaseName, Util.getDefHome());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						Record record = null;

						if (records != null) {
							record = records.get(0);
						}

						// comprobar si tiene formato de visualizacion
						String isisDefHome = Util.getDefHome();
						String htmlString = null;

						List<String> dataBaseFormats = null;
						try {
							dataBaseFormats = service.getDatabaseFormats(dataBaseName, isisDefHome);
							String format = dataBaseFormats.get(0);

							FormattedRecord formattedRecord = service.getFormattedRecord(dataBaseName, record, format, isisDefHome);
							if (formattedRecord != null)
								htmlString = formattedRecord.getRecord();
						} catch (JisisDatabaseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						if (htmlString != null) {
							// erase current view
							Composite superArg0 = view.getParent();
							view.dispose();

							superArg0.getShell().layout(true, true);
							superArg0.getShell().redraw();
							superArg0.getShell().update();

							AllRecordsView allRecordsView = new AllRecordsView();

							allRecordsView.setCurrentRecord(record);
							allRecordsView.setWidth(width - 5);
							allRecordsView.setCurrentView("RAW");
							allRecordsView.setHeight(height - 15);
							allRecordsView.setDataBaseName(dataBaseName);
							allRecordsView.setService(controller.getDataBaseManagerService().getService());
							allRecordsView.setProxyController((ProxyController) controller);

							allRecordsView.createUIControl(superArg0);

							superArg0.setData(RWT.CUSTOM_VARIANT, "gray_background");
							superArg0.layout(true, true);
							superArg0.redraw();
							superArg0.update();
						} else
							RetroalimentationUtils.showErrorShellMessage(MessageUtil.unescape(AbosMessages.get().MESSAGE_THE_REGISTER_HAS_NOT_VISUALIZATION_FORMAT));
					}
				}
			});

			showComponent.showContentPaginator.setTotalElements(records.size());

			showComponent.showContentPaginator.addPageChangeListener(new cu.uci.abos.widget.show.content.showContentPaginatorEvent.PageChangeListener() {

				@Override
				public void pageChanged(PageChangeEvent event) {

					try {
						showComponent.makeContent(view, records, dataBaseName);

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

						refresh();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			try {
				showComponent.showContentPaginator.goToFirstPage();
			} catch (Exception e) {
				e.printStackTrace();
			}

			view.notifyListeners(SWT.Resize, new Event());
		} else {
			Browser browser = new Browser(recordsParent, SWT.LEFT | SWT.WRAP);
			browser.setText(MessageUtil.unescape(AbosMessages.get().BROWSER_NO_RESULT_FOUND_WITH_THAT_SEARCH_CRITERIA));
			FormDatas.attach(browser).atTop(5).atLeft(5).atRight(5).withHeight(20);
		}

		expandItem.setHeight(recordsParent.computeSize(SWT.DEFAULT, SWT.DEFAULT).y + 5);

		// SetControl
		expandItem.setControl(recordsParent);
		expandItem.setExpanded(true);

		superArg0.getShell().layout(true, true);
		superArg0.getShell().redraw();
		superArg0.getShell().update();

		return superArg0;
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

	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}

	public void setReferenceView(ReferenceView referenceView) {
		this.referenceView = referenceView;
	}
}
