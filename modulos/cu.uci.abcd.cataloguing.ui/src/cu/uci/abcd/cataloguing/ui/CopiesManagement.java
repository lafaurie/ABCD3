package cu.uci.abcd.cataloguing.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.TreeItem;
import org.springframework.data.domain.Page;

import cu.uci.abcd.cataloguing.ILoanObjectCreation;
import cu.uci.abcd.cataloguing.controller.ProxyController;
import cu.uci.abcd.cataloguing.listener.EventBackAllRecords;
import cu.uci.abcd.cataloguing.listener.EventLoanObjectCreation;
import cu.uci.abcd.cataloguing.listener.EventStartRecord;
import cu.uci.abcd.cataloguing.util.LoanObjectField2;
import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.domain.acquisition.Suggestion;
import cu.uci.abcd.domain.circulation.Penalty;
import cu.uci.abcd.domain.circulation.Reservation;
import cu.uci.abcd.domain.circulation.Transaction;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Provider;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.l10n.AbosMessages;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.Column;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.MessageVisualEntityManager;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeColumnListener;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;
import cu.uci.abos.l10n.cataloguing.MessageUtil;
import cu.uci.abcd.domain.cataloguing.*;

public class CopiesManagement extends ContributorPage {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private LoanObject loanObject;
	private ProxyController proxyController;
	private long mfn;
	private int width;
	private int height;
	private ProxyController controller;
	private CRUDTreeTable grid;
	private ILoanObjectCreation loanObjectCreation;
	private int direction = 1024;
	private String orderByString = "id";
	private LoanObjectField2 loanObjectField;
	private final int BUY = 0;
	private final int DONATION = 1;
	private final int EXCHANGE = 2;
	private Composite contentPage;
	private CTabFolder acquisitionTypeFolder;
	private Text volumeText;
	private ExpandBar bar;
	private ExpandItem expandItem;
	private long removedID;
	private String controlNumber;
	private String dataBaseName;
	private IJisisDataProvider service;
	private ValidatorUtils validator;
	private AllRecordsView allRecordsView;

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setController(ProxyController controller) {
		this.controller = controller;
		this.loanObjectCreation = controller.getLoanObjectCreationService();
	}

	@Override
	public Control createUIControl(Composite arg0) {

		validator = new ValidatorUtils(new CustomControlDecoration());

		Composite parent = new Composite(arg0, 0);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		parent.setLayout(new FormLayout());

		Composite father = new Composite(parent, SWT.BORDER);
		father.setData(RWT.CUSTOM_VARIANT, "gray_background");
		father.setLayout(new FormLayout());
		FormDatas.attach(father).atLeft(0).atRight(0).atTop(0).withWidth(width + 10).withHeight(height + 10);

		Label up = new Label(father, 0);

		String title = cu.uci.abos.l10n.cataloguing.AbosMessages.get().LABEL_COPIES_FOR + loanObject.getTitle() + cu.uci.abos.l10n.cataloguing.AbosMessages.get().LABEL_RECORD_NUMBER + String.valueOf(mfn) + ")";

		int size = title.length();

		if (size > 71) {
			title = title.substring(0, 65) + "(...)";
		}
		up.setText(title);

		FormDatas.attach(up).atTopTo(father, 11).atLeftTo(father, 175).withWidth(455);

		ToolBar toolBar = new ToolBar(father, SWT.WRAP | SWT.FLAT);
		FormDatas.attach(toolBar).atTopTo(father, 0).atRight(5);

		ToolItem back = new ToolItem(toolBar, 0);
		Image backImage = new Image(father.getDisplay(), RWT.getResourceManager().getRegisteredContent("left-arrow"));
		back.setImage(backImage);
		back.setToolTipText(cu.uci.abos.l10n.cataloguing.AbosMessages.get().TOOL_ITEM_BACK_VIEW);

		back.addListener(SWT.Selection, new EventBackAllRecords(parent, dataBaseName, service, proxyController, false, allRecordsView));

		ToolItem plus = new ToolItem(toolBar, 0);
		Image image = new Image(father.getDisplay(), RWT.getResourceManager().getRegisteredContent("list-alt"));
		plus.setImage(image);
		plus.setToolTipText(cu.uci.abos.l10n.cataloguing.AbosMessages.get().TOOL_ITEM_MAIN_VIEW);

		plus.addListener(SWT.Selection, new EventStartRecord(parent, controller));

		CTabFolder tabFolder = new CTabFolder(father, 0);
		tabFolder.setLayout(new FormLayout());
		tabFolder.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(tabFolder).atTopTo(father, 2).atLeftTo(father, 5).atRight(5).withHeight((height) / 2);

		CTabItem tabItem = new CTabItem(tabFolder, 0);
		tabItem.setText(cu.uci.abos.l10n.cataloguing.AbosMessages.get().TAB_ITEM_PROCESSED_COPIES);
		tabItem.setShowClose(false);
		tabFolder.setSelection(0);

		Composite page = new Composite(tabFolder, SWT.HORIZONTAL | SWT.VERTICAL);
		page.setLayout(new FillLayout());

		grid = new CRUDTreeTable(page, 0);

		grid.setEntityClass(LoanObject.class);
		grid.setVisualEntityManager(new MessageVisualEntityManager(grid));

		Column editColumn = new Column("pencil", arg0.getDisplay(), new TreeColumnListener() {
			@Override
			public void handleEvent(TreeColumnEvent event) {
				// event
				TreeItem selectedTreeItem = event.item;
				IGridViewEntity selectedEntity = (IGridViewEntity) selectedTreeItem.getData("entity");
				LoanObject loanObject = selectedEntity.getRow();

				eventEdit(loanObject);

				contentPage.layout(true, true);
				contentPage.redraw();
				contentPage.update();

				expandItem.setControl(contentPage);

				bar.layout(true, true);
				bar.redraw();
				bar.update();
			}
		});

		Column removedColumn = new Column("redcross", arg0.getDisplay(), new TreeColumnListener() {
			@Override
			public void handleEvent(TreeColumnEvent event) {

				// event
				TreeItem selectedTreeItem = event.item;
				IGridViewEntity selectedEntity = (IGridViewEntity) selectedTreeItem.getData("entity");
				LoanObject loanObject = selectedEntity.getRow();

				eventRemoved(loanObject);
			}
		});

		editColumn.setToolTipText(cu.uci.abos.l10n.cataloguing.AbosMessages.get().GRID_ITEM_EDIT);
		editColumn.setAlignment(SWT.CENTER);
		grid.addActionColumn(editColumn);

		removedColumn.setToolTipText(cu.uci.abos.l10n.cataloguing.AbosMessages.get().GRID_ITEM_REMOVE);
		removedColumn.setAlignment(SWT.CENTER);
		grid.addActionColumn(removedColumn);

		TreeTableColumn columns[] = { new TreeTableColumn(20, 0, "getRoom"), new TreeTableColumn(20, 1, "getInventorynumber"), new TreeTableColumn(20, 2, "getLoanObjectState"), new TreeTableColumn(20, 3, "getAcquisitionCoinType"), new TreeTableColumn(20, 4, "getPrice") };

		grid.createTable(columns);
		grid.setPageSize(5);

		Control[] array = grid.getPaginator().getChildren();
		array[2].setVisible(false);

		grid.setColumnHeaders(Arrays.asList(MessageUtil.unescape(cu.uci.abos.l10n.cataloguing.AbosMessages.get().GRID_COLUMN_HEADER_LOCATION), MessageUtil.unescape(cu.uci.abos.l10n.cataloguing.AbosMessages.get().GRID_COLUMN_HEADER_INVENTARY_NUMBER),
				MessageUtil.unescape(cu.uci.abos.l10n.cataloguing.AbosMessages.get().GRID_COLUMN_HEADER_STATE), cu.uci.abos.l10n.cataloguing.AbosMessages.get().GRID_COLUMN_HEADER_COIN_TYPE, cu.uci.abos.l10n.cataloguing.AbosMessages.get().GRID_COLUMN_HEADER_PRICE));

		searchLoanObjects(0, grid.getPageSize(), loanObject.getControlNumber());

		grid.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {

				searchLoanObjects(event.currentPage - 1, event.pageSize, loanObject.getControlNumber());
			}
		});

		grid.getPaginator().goToFirstPage();

		bar = new ExpandBar(father, SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		bar.setData(RWT.CUSTOM_VARIANT, "gray_background");
		bar.setLayout(new FormLayout());
		FormDatas.attach(bar).atTopTo(tabFolder, 5).atLeft(0).atRight(0).withHeight(height / 2 - 30);

		expandItem = new ExpandItem(bar, SWT.PUSH);

		bar.addExpandListener(new ExpandListener() {

			/**
			 * Created by Basilio Puentes Rodríguez
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void itemExpanded(ExpandEvent arg0) {

			}

			@Override
			public void itemCollapsed(ExpandEvent arg0) {
				ExpandItem expandItem = (ExpandItem) arg0.item;
				expandItem.setExpanded(true);
			}
		});

		contentPage = new Composite(bar, 0);
		contentPage.setLayout(new FormLayout());
		FormDatas.attach(contentPage).atLeft(4).atRight(0).atTop(0);

		// Labels

		// mandatory
		Label locationLabel = new Label(contentPage, 0);
		locationLabel.setText(MessageUtil.unescape(cu.uci.abos.l10n.cataloguing.AbosMessages.get().LABEL_LOCATION));

		Label situationLabel = new Label(contentPage, 0);
		situationLabel.setText(MessageUtil.unescape(cu.uci.abos.l10n.cataloguing.AbosMessages.get().LABEL_SITUATION));

		Label providerLabel = new Label(contentPage, 0);
		providerLabel.setText(MessageUtil.unescape(cu.uci.abos.l10n.cataloguing.AbosMessages.get().LABEL_PROVIDER_DONATION));

		// mandatory
		Label acquisitionCoinTypeLabel = new Label(contentPage, 0);
		acquisitionCoinTypeLabel.setText(cu.uci.abos.l10n.cataloguing.AbosMessages.get().LABEL_COIN_TYPE);

		// mandatory
		Label loanObjectTypeLabel = new Label(contentPage, 0);
		loanObjectTypeLabel.setText(MessageUtil.unescape(cu.uci.abos.l10n.cataloguing.AbosMessages.get().LABEL_LOAN_OBJECT_TYPE));

		// mandatory
		Label inventoryNumberLabel = new Label(contentPage, 0);
		inventoryNumberLabel.setText(cu.uci.abos.l10n.cataloguing.AbosMessages.get().LABEL_INVENTARY_NUMBER);

		Label tomeLabel = new Label(contentPage, 0);
		tomeLabel.setText(cu.uci.abos.l10n.cataloguing.AbosMessages.get().LABEL_TOME);

		Label volumeLabel = new Label(contentPage, 0);
		volumeLabel.setText(cu.uci.abos.l10n.cataloguing.AbosMessages.get().LABEL_VOLUME);

		// mandatory
		Label acquisitionTypeLabel = new Label(contentPage, 0);
		acquisitionTypeLabel.setText(MessageUtil.unescape(cu.uci.abos.l10n.cataloguing.AbosMessages.get().LABEL_ACQUISITION_TYPE));

		// Controls
		loanObjectField = new LoanObjectField2();

		Combo locationCombo = new Combo(contentPage, SWT.READ_ONLY);
		loanObjectField.setLocationCombo(locationCombo);

		Combo situationCombo = new Combo(contentPage, SWT.READ_ONLY);
		loanObjectField.setSituationCombo(situationCombo);

		Combo providerCombo = new Combo(contentPage, SWT.READ_ONLY);
		loanObjectField.setProviderCombo(providerCombo);

		Combo acquisitionCoinTypeCombo = new Combo(contentPage, SWT.READ_ONLY);
		loanObjectField.setAcquisitionCoinType(acquisitionCoinTypeCombo);

		Combo loanObjectTypeCombo = new Combo(contentPage, SWT.READ_ONLY);
		loanObjectField.setLoanObjectType(loanObjectTypeCombo);

		Text inventoryNumberText = new Text(contentPage, 0);
		validator.applyValidator(inventoryNumberText, 20);
		loanObjectField.setInventoryNumberText(inventoryNumberText);
		validator.applyValidator(inventoryNumberText, "txt_inventarynumber", DecoratorType.REQUIRED_FIELD, true);

		Text tomeText = new Text(contentPage, 0);
		validator.applyValidator(tomeText, 10);
		loanObjectField.setTomeText(tomeText);

		volumeText = new Text(contentPage, 0);
		validator.applyValidator(volumeText, 10);
		loanObjectField.setVolumeText(volumeText);

		acquisitionTypeFolder = new CTabFolder(contentPage, 0);
		loanObjectField.setTabFolder(acquisitionTypeFolder);
		acquisitionTypeFolder.setLayout(new FormLayout());

		ILoanObjectCreation loanObjectService = proxyController.getLoanObjectCreationService();

		List<Nomenclator> acquisitionType = new ArrayList<>();

		acquisitionType.add(loanObjectService.getNomenclator(CataloguingNomenclator.ACQUISITION_TYPE_BUY));
		acquisitionType.add(loanObjectService.getNomenclator(CataloguingNomenclator.ACQUISITION_TYPE_DONATION));
		acquisitionType.add(loanObjectService.getNomenclator(CataloguingNomenclator.ACQUISITION_TYPE_EXCHANGE));

		List<Nomenclator> acquisitionCoinTypeNomenclator = loanObjectService.getNomenclatorByParent(CataloguingNomenclator.ACQUISITION_COIN_TYPE);

		CTabItem buyItem = new CTabItem(acquisitionTypeFolder, 0);
		buyItem.setText(acquisitionType.get(0).getNomenclatorName());

		CTabItem donationItem = new CTabItem(acquisitionTypeFolder, 0);
		donationItem.setText(acquisitionType.get(1).getNomenclatorName());

		CTabItem redeemendItem = new CTabItem(acquisitionTypeFolder, 0);
		redeemendItem.setText(acquisitionType.get(2).getNomenclatorName());

		Composite buyComposite = new Composite(acquisitionTypeFolder, 0);
		buyComposite.setLayout(new FormLayout());

		Composite donationComposite = new Composite(acquisitionTypeFolder, 0);
		donationComposite.setLayout(new FormLayout());

		Composite exchangedComposite = new Composite(acquisitionTypeFolder, 0);
		exchangedComposite.setLayout(new FormLayout());

		// Labels
		Label noRecommendLabel = new Label(buyComposite, 0);
		noRecommendLabel.setText(MessageUtil.unescape(cu.uci.abos.l10n.cataloguing.AbosMessages.get().LABEL_RECOMENDATION));

		Label buyPriceLabel = new Label(buyComposite, 0);
		buyPriceLabel.setText(cu.uci.abos.l10n.cataloguing.AbosMessages.get().LABEL_PRICE);

		Label conditionsLabel = new Label(donationComposite, 0);
		conditionsLabel.setText(cu.uci.abos.l10n.cataloguing.AbosMessages.get().LABEL_CONDITIONS);

		Label donationEstimatedPriceLabel = new Label(donationComposite, 0);
		donationEstimatedPriceLabel.setText(cu.uci.abos.l10n.cataloguing.AbosMessages.get().LABEL_ESTIMATE_PRICE);

		Label redeemendByLabel = new Label(exchangedComposite, 0);
		redeemendByLabel.setText(cu.uci.abos.l10n.cataloguing.AbosMessages.get().LABEL_REDEEMEND_BY);

		Label redeemendEstimatedPriceLabel = new Label(exchangedComposite, 0);
		redeemendEstimatedPriceLabel.setText(cu.uci.abos.l10n.cataloguing.AbosMessages.get().LABEL_ESTIMATE_PRICE);

		// Controls
		Combo noRecommendCombo = new Combo(buyComposite, SWT.READ_ONLY);
		loanObjectField.setNoRecommendCombo(noRecommendCombo);

		Text priceText = new Text(buyComposite, 0);
		validator.applyValidator(priceText, "txt_price1", DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(priceText, "txt_price11", DecoratorType.DOUBLE, true, 10);
		loanObjectField.setPriceText(priceText);

		Text conditionsText = new Text(donationComposite, 0);
		validator.applyValidator(conditionsText, 200);
		loanObjectField.setConditionsText(conditionsText);

		Text donationEstimatedPriceText = new Text(donationComposite, 0);
		validator.applyValidator(donationEstimatedPriceText, "txt_price2", DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(donationEstimatedPriceText, "txt_price22", DecoratorType.DOUBLE, true, 10);
		loanObjectField.setDonationEstimatedPriceText(donationEstimatedPriceText);

		Text exchangedByText = new Text(exchangedComposite, 0);
		validator.applyValidator(exchangedByText, 50);
		loanObjectField.setRedeemendByText(exchangedByText);

		Text exchangedEstimatedPriceText = new Text(exchangedComposite, 0);
		validator.applyValidator(exchangedEstimatedPriceText, "txt_price3", DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(exchangedEstimatedPriceText, "txt_price33", DecoratorType.DOUBLE, true, 10);
		loanObjectField.setRedeemendEstimatedPriceText(exchangedEstimatedPriceText);

		buyItem.setControl(buyComposite);
		donationItem.setControl(donationComposite);
		redeemendItem.setControl(exchangedComposite);

		// values of the combos

		// Rooms
		List<Room> rooms = loanObjectService.getRooms();
		initialize(locationCombo, rooms);
		validator.applyValidator(locationCombo, "cmb_location", DecoratorType.REQUIRED_FIELD, true);

		// Situations
		List<Nomenclator> situations = loanObjectService.getNomenclatorByParent(CataloguingNomenclator.SITUATION);
		initialize(situationCombo, situations);

		// providers
		List<Provider> providers = loanObjectService.getProviders();
		initialize(providerCombo, providers);

		// AcquisitionCoinType
		initialize(acquisitionCoinTypeCombo, acquisitionCoinTypeNomenclator);
		validator.applyValidator(acquisitionCoinTypeCombo, "cmb_acquisitionCoinType", DecoratorType.REQUIRED_FIELD, true);

		// LoanObjectType
		List<Nomenclator> loanObjectTypes = loanObjectService.getNomenclatorByParent(CataloguingNomenclator.LOAN_OBJECT_TYPE);
		initialize(loanObjectTypeCombo, loanObjectTypes);
		validator.applyValidator(loanObjectTypeCombo, "cmb_loanObjectType", DecoratorType.REQUIRED_FIELD, true);

		// noRecommend
		List<Suggestion> suggestions = loanObjectService.getSuggestion();
		int suggestionSize = suggestions.size();
		List<String> titles = new ArrayList<String>();

		for (int i = 0; i < suggestionSize; i++) {
			Suggestion suggestion = suggestions.get(i);
			titles.add(suggestion.getTitle());
		}

		initialize(noRecommendCombo, titles);

		// Ubicacion de los controls
		FormDatas.attach(locationCombo).withWidth(270).withHeight(23).atLeftTo(contentPage, 280).atTopTo(contentPage, 15);
		FormDatas.attach(situationCombo).withWidth(270).withHeight(23).atLeftTo(contentPage, 280).atTopTo(locationCombo, 10);
		FormDatas.attach(providerCombo).withWidth(270).withHeight(23).atLeftTo(contentPage, 280).atTopTo(situationCombo, 10);
		FormDatas.attach(acquisitionCoinTypeCombo).withHeight(23).withWidth(270).atLeftTo(contentPage, 280).atTopTo(providerCombo, 10);
		FormDatas.attach(loanObjectTypeCombo).withHeight(23).withWidth(270).atLeftTo(contentPage, 280).atTopTo(acquisitionCoinTypeCombo, 10);
		FormDatas.attach(inventoryNumberText).withWidth(250).withHeight(10).atLeftTo(contentPage, 280).atTopTo(loanObjectTypeCombo, 15);
		FormDatas.attach(tomeText).withWidth(250).withHeight(10).atLeftTo(contentPage, 280).atTopTo(inventoryNumberText, 10);
		FormDatas.attach(volumeText).withWidth(250).withHeight(10).atLeftTo(contentPage, 280).atTopTo(tomeText, 10);

		FormDatas.attach(acquisitionTypeFolder).withHeight(112).withWidth(400).atTopTo(volumeText, 10).atLeftTo(contentPage, 150);

		FormDatas.attach(noRecommendCombo).withWidth(270).withHeight(23).atLeftTo(buyComposite, 110).atTopTo(buyComposite, 24);
		FormDatas.attach(priceText).withWidth(250).withHeight(10).atLeftTo(buyComposite, 110).atTopTo(noRecommendCombo, 24);

		FormDatas.attach(conditionsText).withWidth(250).withHeight(10).atLeftTo(donationComposite, 110).atTopTo(donationComposite, 24);
		FormDatas.attach(donationEstimatedPriceText).withWidth(250).withHeight(10).atLeftTo(donationComposite, 110).atTopTo(conditionsText, 24);

		FormDatas.attach(exchangedByText).withWidth(250).withHeight(10).atLeftTo(exchangedComposite, 110).atTopTo(exchangedComposite, 24);
		FormDatas.attach(exchangedEstimatedPriceText).withWidth(250).withHeight(10).atLeftTo(exchangedComposite, 110).atTopTo(exchangedByText, 24);

		// Ubicacion de los labels

		FormDatas.attach(locationLabel).atRightTo(locationCombo, 10).atTopTo(contentPage, 20);
		FormDatas.attach(situationLabel).atRightTo(situationCombo, 10).atTopTo(locationLabel, 20);
		FormDatas.attach(providerLabel).atRightTo(providerCombo, 10).atTopTo(situationLabel, 19);
		FormDatas.attach(acquisitionCoinTypeLabel).atRightTo(acquisitionCoinTypeCombo, 10).atTopTo(providerLabel, 19);
		FormDatas.attach(loanObjectTypeLabel).atRightTo(loanObjectTypeCombo, 10).atTopTo(acquisitionCoinTypeLabel, 19);
		FormDatas.attach(inventoryNumberLabel).atRightTo(inventoryNumberText, 10).atTopTo(loanObjectTypeLabel, 19);
		FormDatas.attach(tomeLabel).atRightTo(tomeText, 10).atTopTo(inventoryNumberLabel, 16);
		FormDatas.attach(volumeLabel).atRightTo(volumeText, 10).atTopTo(tomeLabel, 16);

		FormDatas.attach(acquisitionTypeLabel).atTopTo(volumeLabel, 23).atRightTo(acquisitionTypeFolder, 10);

		FormDatas.attach(noRecommendLabel).atTopTo(buyComposite, 28).atRightTo(noRecommendCombo, 10);
		FormDatas.attach(buyPriceLabel).atTopTo(noRecommendLabel, 30).atRightTo(priceText, 10);

		FormDatas.attach(conditionsLabel).atTopTo(donationComposite, 28).atRightTo(conditionsText, 10);
		FormDatas.attach(donationEstimatedPriceLabel).atTopTo(conditionsLabel, 30).atRightTo(donationEstimatedPriceText, 10);

		FormDatas.attach(redeemendByLabel).atTopTo(exchangedComposite, 28).atRightTo(exchangedByText, 10);
		FormDatas.attach(redeemendEstimatedPriceLabel).atTopTo(redeemendByLabel, 30).atRightTo(exchangedEstimatedPriceText, 10);

		Button buttonSave = new Button(contentPage, SWT.PUSH | SWT.CENTER);
		buttonSave.setText(cu.uci.abos.l10n.cataloguing.AbosMessages.get().BUTTON_INSERT_COPIE);
		buttonSave.pack();
		loanObjectField.setButtonSave(buttonSave);

		EventLoanObjectCreation event = new EventLoanObjectCreation(grid, loanObjectField, loanObjectService, loanObject, acquisitionTypeFolder, buttonSave, contentPage, bar, expandItem, validator);

		event.setAcquisitionCoinType(acquisitionCoinTypeNomenclator);
		event.setAcquisitionType(acquisitionType);
		event.setProviders(providers);
		event.setRooms(rooms);
		event.setSituations(situations);
		event.setSuggestions(suggestions);
		event.setFirst(true);
		event.setLoanObjectType(loanObjectTypes);

		buttonSave.addListener(SWT.Selection, event);

		tabItem.setControl(page);

		FormDatas.attach(buttonSave).atTopTo(volumeText, 125).atLeftTo(acquisitionTypeFolder, 2);

		expandItem.setHeight(contentPage.computeSize(SWT.DEFAULT, SWT.DEFAULT).y - 30);
		expandItem.setControl(contentPage);
		expandItem.setExpanded(true);

		return arg0;
	}

	@Override
	public String getID() {

		return "ExemplaryInsert2ID";
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

		return "ExemplaryInsert2";
	}

	public void setLoanObject(LoanObject loanObject) {
		this.loanObject = loanObject;
	}

	public void setProxyController(ProxyController proxyController) {
		this.proxyController = proxyController;

	}

	@Override
	public void setViewController(ViewController arg0) {

	}

	public long getMfn() {
		return mfn;
	}

	public void setMfn(long mfn) {
		this.mfn = mfn;
	}

	private void searchLoanObjects(int page, int size, String controlNumber) {
		grid.clearRows();
		grid.refresh();

		ILoanObjectCreation newService = proxyController.getLoanObjectCreationService();

		Page<LoanObject> loanObjects = newService.findAllByControlNumber(controlNumber, page, size, direction, orderByString);

		grid.setTotalElements((int) loanObjects.getTotalElements());
		if ((int) loanObjects.getTotalElements() > 0) {
			grid.setRows(loanObjects.getContent());
			grid.refresh();
		}
	}

	private void eventEdit(LoanObject loanObject) {

		Nomenclator state = loanObject.getLoanObjectState();

		if (state.getNomenclatorID() == CataloguingNomenclator.LOANOBJECT_STATE_BORROWED) {
			RetroalimentationUtils.showErrorMessage("El elemento no se puede editar porque está prestado.");
		} else {
			Room room = loanObject.getRoom();
			Combo roomCombo = loanObjectField.getLocationCombo();

			List<Room> rooms = loanObjectCreation.getRooms();
			int roomSize = rooms.size();

			loanObjectField.resetValues(roomCombo);
			roomCombo.add(cu.uci.abos.l10n.cataloguing.AbosMessages.get().VALUE_COMBO_SELECT);

			int roomPosition = -1;
			for (int i = 0; i < roomSize; i++) {
				String value = rooms.get(i).getRoomName();
				roomCombo.add(value);
				if (value.equals(room.getRoomName()))
					roomPosition = i + 1;
			}
			if (roomPosition == -1)
				roomCombo.select(0);
			else
				roomCombo.select(roomPosition);

			// situation
			Nomenclator situationNomenclator = loanObject.getSituation();
			Combo situationCombo = loanObjectField.getSituationCombo();

			List<Nomenclator> situations = loanObjectCreation.getNomenclatorByParent(CataloguingNomenclator.SITUATION);
			int situationSize = situations.size();

			loanObjectField.resetValues(situationCombo);
			situationCombo.add(cu.uci.abos.l10n.cataloguing.AbosMessages.get().VALUE_COMBO_SELECT);

			int situationPosition = -1;
			for (int i = 0; i < situationSize; i++) {
				String value = situations.get(i).getNomenclatorName();
				situationCombo.add(value);
				if (situationNomenclator != null) {
					if (value.equals(situationNomenclator.getNomenclatorName()))
						situationPosition = i + 1;
				}
			}
			if (situationPosition == -1)
				situationCombo.select(0);
			else
				situationCombo.select(situationPosition);

			// provider
			List<Provider> providers = loanObjectCreation.getProviders();
			Provider provider = loanObject.getProvider();
			Combo providerCombo = loanObjectField.getProviderCombo();

			int providerSize = providers.size();

			loanObjectField.resetValues(providerCombo);
			providerCombo.add(cu.uci.abos.l10n.cataloguing.AbosMessages.get().VALUE_COMBO_SELECT);

			int providerPosition = -1;
			for (int i = 0; i < providerSize; i++) {
				String value = providers.get(i).getName();
				providerCombo.add(value);
				if (provider != null) {
					if (value.equals(provider.getName()))
						providerPosition = i + 1;
				}
			}
			if (providerPosition == -1)
				providerCombo.select(0);
			else
				providerCombo.select(providerPosition);

			// acquisitionCoinType
			Nomenclator acquisitionCoinTypeNomenclator = loanObject.getAcquisitionCoinType();
			Combo acquisitionCoinTypeCombo = loanObjectField.getAcquisitionCoinType();

			List<Nomenclator> acquisitionCoinTypes = loanObjectCreation.getNomenclatorByParent(CataloguingNomenclator.ACQUISITION_COIN_TYPE);
			int acquisitionCoinTypeSize = acquisitionCoinTypes.size();

			loanObjectField.resetValues(acquisitionCoinTypeCombo);
			acquisitionCoinTypeCombo.add(cu.uci.abos.l10n.cataloguing.AbosMessages.get().VALUE_COMBO_SELECT);

			int acquisitionCoinTypePosition = -1;
			for (int i = 0; i < acquisitionCoinTypeSize; i++) {
				String value = acquisitionCoinTypes.get(i).getNomenclatorName();
				acquisitionCoinTypeCombo.add(value);
				if (acquisitionCoinTypeNomenclator != null) {
					if (value.equals(acquisitionCoinTypeNomenclator.getNomenclatorName()))
						acquisitionCoinTypePosition = i + 1;
				}

			}
			if (acquisitionCoinTypePosition == -1)
				acquisitionCoinTypeCombo.select(0);
			else
				acquisitionCoinTypeCombo.select(acquisitionCoinTypePosition);

			// loanObjectType
			Nomenclator loanObjectTypeNomenclator = loanObject.getRecordType();
			Combo loanObjectTypeCombo = loanObjectField.getLoanObjectType();

			List<Nomenclator> loanObjectTypes = loanObjectCreation.getNomenclatorByParent(CataloguingNomenclator.LOAN_OBJECT_TYPE);
			int loanObjectSize = loanObjectTypes.size();

			loanObjectField.resetValues(loanObjectTypeCombo);
			loanObjectTypeCombo.add(cu.uci.abos.l10n.cataloguing.AbosMessages.get().VALUE_COMBO_SELECT);

			int loanObjectPosition = -1;
			for (int i = 0; i < loanObjectSize; i++) {
				String value = loanObjectTypes.get(i).getNomenclatorName();
				loanObjectTypeCombo.add(value);
				if (loanObjectTypeNomenclator != null) {
					if (value.equals(loanObjectTypeNomenclator.getNomenclatorName()))
						loanObjectPosition = i + 1;
				}
			}
			if (loanObjectPosition == -1)
				loanObjectTypeCombo.select(0);
			else
				loanObjectTypeCombo.select(loanObjectPosition);

			// inventoryNumber
			String inventoryNumber = loanObject.getInventorynumber();
			if (!inventoryNumber.equals("") && inventoryNumber != null)
				loanObjectField.getInventoryNumberText().setText(inventoryNumber);

			// tome
			String tome = loanObject.getTome();
			if (null != tome)
				loanObjectField.getTomeText().setText(tome);

			// volume
			String volume = loanObject.getVolume();
			if (null != volume)
				loanObjectField.getVolumeText().setText(volume);

			// acquisitionType
			List<Nomenclator> acquisitionType = new ArrayList<>();

			acquisitionType.add(loanObjectCreation.getNomenclator(CataloguingNomenclator.ACQUISITION_TYPE_BUY));
			acquisitionType.add(loanObjectCreation.getNomenclator(CataloguingNomenclator.ACQUISITION_TYPE_DONATION));
			acquisitionType.add(loanObjectCreation.getNomenclator(CataloguingNomenclator.ACQUISITION_TYPE_EXCHANGE));

			int acquisitionTypePosition = positionAcquisitionType(loanObject);
			if (acquisitionTypePosition != -1) {
				acquisitionTypeFolder.setSelection(acquisitionTypePosition);
			}

			// price
			double price = loanObject.getPrice();

			List<Suggestion> suggestions = loanObjectCreation.getSuggestion();

			if (acquisitionTypePosition == BUY) {

				// price
				loanObjectField.getPriceText().setText(String.valueOf(price));

				// noRecommend
				Suggestion suggestion = loanObject.getRecommendation();
				Combo suggestionCombo = loanObjectField.getNoRecommendCombo();

				int suggestionSize = suggestions.size();

				loanObjectField.resetValues(suggestionCombo);
				suggestionCombo.add(cu.uci.abos.l10n.cataloguing.AbosMessages.get().VALUE_COMBO_SELECT);

				int suggestionPosition = -1;
				for (int i = 0; i < suggestionSize; i++) {
					String value = suggestions.get(i).getTitle();
					suggestionCombo.add(value);
					if (suggestion != null) {
						if (value.equals(suggestion.getTitle()))
							suggestionPosition = i + 1;
					}
				}
				if (suggestionPosition == -1)
					suggestionCombo.select(0);
				else
					suggestionCombo.select(suggestionPosition);
			} else if (acquisitionTypePosition == DONATION) {

				// conditions
				String conditions = loanObject.getConditions();
				if (null != conditions)
					loanObjectField.getConditionsText().setText(conditions);

				// donationEstimatedPrice
				loanObjectField.getDonationEstimatedPriceText().setText(String.valueOf(price));
			} else if (acquisitionTypePosition == EXCHANGE) {

				// redeemendBy
				String exchange = loanObject.getExchangedBy();
				if (null != exchange)
					loanObjectField.getRedeemendByText().setText(exchange);

				// redeemendEstimatedPrice
				loanObjectField.getRedeemendEstimatedPriceText().setText(String.valueOf(price));
			}

			// removed and creating a new button
			loanObjectField.getButtonSave().dispose();

			Button newButton = new Button(contentPage, SWT.PUSH | SWT.CENTER);
			newButton.setText(cu.uci.abos.l10n.cataloguing.AbosMessages.get().BUTTON_SAVE_CHANGES);
			newButton.pack();

			loanObjectField.setButtonSave(newButton);

			EventLoanObjectCreation event = new EventLoanObjectCreation(grid, loanObjectField, loanObjectCreation, loanObject, acquisitionTypeFolder, loanObjectField.getButtonSave(), contentPage, bar, expandItem, validator);

			event.setIsert(false);
			event.setAcquisitionCoinType(acquisitionCoinTypes);
			event.setAcquisitionType(acquisitionType);
			event.setProviders(providers);
			event.setRooms(rooms);
			event.setSituations(situations);
			event.setSuggestions(suggestions);
			event.setLoanObjectType(loanObjectTypes);

			loanObjectField.getButtonSave().addListener(SWT.Selection, event);

			FormDatas.attach(loanObjectField.getButtonSave()).atTopTo(volumeText, 125).atLeftTo(acquisitionTypeFolder, 2);
		}
	}

	public void eventRemoved(LoanObject loanObject) {

		Nomenclator state = loanObject.getLoanObjectState();

		if (state.getNomenclatorID() == CataloguingNomenclator.LOANOBJECT_STATE_BORROWED) {
			RetroalimentationUtils.showErrorMessage("El elemento no se puede eliminar porque está prestado.");
		} else {
			controlNumber = loanObject.getControlNumber();
			removedID = loanObject.getLoanObjectID();

			Button button = loanObjectField.getButtonSave();

			if (!button.getText().equals(cu.uci.abos.l10n.cataloguing.AbosMessages.get().BUTTON_SAVE_CHANGES)) {

				MessageDialogUtil.openQuestion(contentPage.getShell(), cu.uci.abos.l10n.cataloguing.AbosMessages.get().MESSAGE_QUESTION, MessageUtil.unescape(cu.uci.abos.l10n.cataloguing.AbosMessages.get().MESSAGE_DO_YOU_WANT_TO_REMOVE_THE_ELEMENT), new DialogCallback() {
					/**
					 * Created by Basilio Puentes Rodríguez
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void dialogClosed(int arg0) {
						if (arg0 == 0) {

							List<Transaction> transactions = loanObjectCreation.findTranssactionByLoanObject(removedID);
							List<Penalty> penalties = loanObjectCreation.findPenaltyByLoanObject(removedID);

							List<Reservation> reservations = loanObjectCreation.findAllReservations();
							boolean canErase = true;

							int reservationsSize = reservations.size();
							for (int i = 0; i < reservationsSize; i++) {
								Reservation currentReservation = reservations.get(i);

								List<LoanObject> loanOjts = currentReservation.getReservationList();
								int loanObjectsSize = loanOjts.size();
								for (int j = 0; j < loanObjectsSize; j++) {
									LoanObject currentLoanObject = loanOjts.get(j);
									if (currentLoanObject.getLoanObjectID() == removedID) {
										canErase = false;
										break;
									}
								}
								if (canErase == false)
									break;
							}

							// Can be erase because has not transaction,
							// penalties and reservations
							if (transactions.size() == 0 && penalties.size() == 0 && canErase) {
								loanObjectCreation.removedLoanObject(removedID);

								searchLoanObjects(0, grid.getPageSize(), controlNumber);
								int currentPage = 0;
								currentPage = grid.getPaginator().getCurrentPage();
								grid.getPaginator().goToPage(currentPage);
								grid.refresh();

								RetroalimentationUtils.showInformationMessage(AbosMessages.get().MSG_INF_DELETE_ONE_ITEM);
							} else {
								RetroalimentationUtils.showErrorMessage("El elemento que desea eliminar se está usando.");
							}
						}
					}
				});
			} else
				RetroalimentationUtils.showErrorShellMessage(MessageUtil.unescape(cu.uci.abos.l10n.cataloguing.AbosMessages.get().MESSAGE_CANT_NOT_REMOVE_IF_EDITING));
		}
	}

	private int positionAcquisitionType(LoanObject loanObject) {
		int result = -1;

		long acquisitionType = loanObject.getAcquisitionType().getNomenclatorID();

		if (acquisitionType == CataloguingNomenclator.ACQUISITION_TYPE_BUY)
			result = BUY;
		else if (acquisitionType == CataloguingNomenclator.ACQUISITION_TYPE_DONATION)
			result = DONATION;
		else if (acquisitionType == CataloguingNomenclator.ACQUISITION_TYPE_EXCHANGE)
			result = EXCHANGE;

		return result;
	}

	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}

	public void setService(IJisisDataProvider service) {
		this.service = service;
	}

	public ValidatorUtils getValidator() {
		return this.validator;
	}

	public AllRecordsView getAllRecordsView() {
		return this.allRecordsView;
	}

	public void setAllRecordsView(AllRecordsView allRecordsView) {
		this.allRecordsView = allRecordsView;
	}
}
