package cu.uci.abcd.acquisition.ui.updateArea;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.springframework.data.domain.Page;

import cu.uci.abcd.acquisition.ui.RegisterDesiderata;
import cu.uci.abcd.acquisition.ui.controller.AllManagementController;
import cu.uci.abcd.adquisition.l10n.AbosMessages;
import cu.uci.abcd.domain.acquisition.AdquisitionNomenclator;
import cu.uci.abcd.domain.acquisition.Desiderata;
import cu.uci.abcd.domain.acquisition.Suggestion;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Worker;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.ui.FragmentPage;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.Column;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeColumnListener;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class DesiderataFragment extends FragmentPage {

	private ViewController controller;

	private ValidatorUtils validator;
	private Composite group;
	private CRUDTreeTable approbedSuggestionTable;
	private CRUDTreeTable associatedSuggestionTable;

	private int page = 0;
	private int size = 10;

	private static String orderByStringSuggestion = "suggestionID";
	private static int direction = 1024;

	private Desiderata desiderata;
	private Label lblDataDesiderata;
	private Label lblTitle;
	private Label lblAuthor;
	private Label lblEditorial;
	// private Label lblUserType;
	private Label lblCityOfPublication;
	private Label lblNumberOfEdition;
	private Label lblPublicationYear;
	private Label lblTome;
	private Label lblVolume;
	private Label lblNumber;
	private Label lblISSN;
	private Label lblISBN;
	private Label lblPrice;
	private Label lblReasonForSuggestions;
	private Label separator;
	private Label list;
	private Label list2;
	private Label lblQuantity;
	private Label lblFind;
	private Label lblCoinType;

	private Text txtFind;
	private Text txtTitle;
	private Text txtAuthor;
	private Text txtEditorial;
	private Text txtPublicationCity;
	private Text txtNumberOfEdition;
	private Text txtTome;
	private Text txtVolume;
	private Text txtNumber;
	private Text txtISBN;
	private Text txtISSN;
	private Text txtPrice;
	private Text txtReasonForSuggestions;

	private Text sp_Quantity;

	// private Combo cbUserType;
	private Combo cbPublicationYear;
	private Combo cbCoinType;

	private Button consult;

	private Button rd_sSugerencias;
	private Button rd_cSugerencias;
	private Label lbRegister;
	private Composite compoSuggestionA;
	private Label separator1;

	private List<Suggestion> listSuggestionSelect = new ArrayList<Suggestion>();
	private String aux;

	private String params;

	private Library library;
	private RegisterDesiderata registerDesiderata;

	private Composite compoButton;
	private Button registerButton;
	private Button cancelButton;
	private List<Suggestion> suggestionList;
	private List<Suggestion> temp;
	private Page<Suggestion> listDB;
	

	public DesiderataFragment(ContributorPage page) {
		this.controller = page.getController();
		this.setDimension(page.getDimension()) ;
	}
	
	public DesiderataFragment(Desiderata desiderata, int dimension,ViewController controller) {
		this.desiderata=desiderata;
		this.setDimension(dimension);
		this.controller = controller;
	}

	public ViewController getController() {
		return controller;
	}

	public void setController(ViewController controller) {
		this.controller = controller;
	}

	public Desiderata getDesiderata() {
		return desiderata;
	}

	public void setDesiderata(Desiderata desiderata) {
		this.desiderata = desiderata;
	}

	public String getAux() {
		return aux;
	}

	public void setAux(String aux) {
		this.aux = aux;
	}


	@Override
	public Control createUIControl(Composite parent) {
		
		library = (Library) SecurityUtils.getService().getPrincipal()
				.getByKey("library");		
		
		addComposite(parent);		

		validator = new ValidatorUtils(new CustomControlDecoration());
		temp = new ArrayList<>();
		
		listSuggestionSelect.clear();
		if (desiderata != null) {

		suggestionList = ((AllManagementController) controller)
					.getSuggestion().findSuggestionByIdDesiderata(
							desiderata.getDesidertaID());
		listSuggestionSelect.addAll(suggestionList);
		}

		group = new Composite(parent, SWT.NORMAL);
		addComposite(group);
		controlMap.put("group", group);
		group.setData(RWT.CUSTOM_VARIANT, "gray_background");

		lbRegister = new Label(group, SWT.NONE);
		addHeader(lbRegister);

		separator1 = new Label(group, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator1);

		rd_sSugerencias = new Button(group, SWT.RADIO);

		rd_sSugerencias.setSelection(true);
		add(rd_sSugerencias);

		rd_cSugerencias = new Button(group, SWT.RADIO);
		add(rd_cSugerencias);

		lblDataDesiderata = new Label(group, SWT.NONE);
		addHeader(lblDataDesiderata);

		lblTitle = new Label(group, SWT.NONE);
		add(lblTitle);

		txtTitle = new Text(group, SWT.NONE);
		controlMap.put("txtTitle", txtTitle);
		add(txtTitle);
		validator.applyValidator(txtTitle, "txtTitle",
				DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(txtTitle,500);
		
		lblAuthor = new Label(group, SWT.NONE);
		add(lblAuthor);

		txtAuthor = new Text(group, SWT.NONE);
		add(txtAuthor);
		validator.applyValidator(txtAuthor,500);

		reset();

		lblEditorial = new Label(group, SWT.NONE);
		add(lblEditorial);

		txtEditorial = new Text(group, SWT.NONE);
		controlMap.put("txtEditorial", txtEditorial);
		add(txtEditorial);
		validator.applyValidator(txtEditorial, 50);

		lblCityOfPublication = new Label(group, SWT.NONE);
		add(lblCityOfPublication);

		txtPublicationCity = new Text(group, SWT.NONE);
		controlMap.put("txtPublicationCity", txtPublicationCity);
		add(txtPublicationCity);
		validator.applyValidator(txtPublicationCity, 50);
		reset();

		lblNumberOfEdition = new Label(group, SWT.NONE);
		add(lblNumberOfEdition);

		txtNumberOfEdition = new Text(group, SWT.NONE);
		controlMap.put("txtNumberOfEdition", txtNumberOfEdition);
		add(txtNumberOfEdition);
		validator.applyValidator(txtNumberOfEdition, "txtNumberOfEdition",
				DecoratorType.ALPHA_NUMERIC, true, 10);

		lblPublicationYear = new Label(group, SWT.NONE);
		add(lblPublicationYear);

		cbPublicationYear = new Combo(group, SWT.READ_ONLY);
		add(cbPublicationYear);

		reset();

		lblTome = new Label(group, SWT.NONE);
		add(lblTome);

		txtTome = new Text(group, SWT.NONE);
		add(txtTome);
		validator.applyValidator(txtTome, "txtTome",
				DecoratorType.ALPHA_SPACES, true,10);

		lblVolume = new Label(group, SWT.NONE);
		add(lblVolume);

		txtVolume = new Text(group, SWT.NONE);
		add(txtVolume);
		validator.applyValidator(txtVolume, "txtVolume",
				DecoratorType.NUMBER_ONLY, true, 10);

		reset();

		lblNumber = new Label(group, SWT.NONE);
		add(lblNumber);

		txtNumber = new Text(group, SWT.NONE);
		add(txtNumber);
		validator.applyValidator(txtNumber, "txtNumber",
				DecoratorType.NUMBER_ONLY, true, 10);

		lblISBN = new Label(group, SWT.NONE);
		add(lblISBN);

		txtISBN = new Text(group, SWT.NONE);
		add(txtISBN);
		validator.applyValidator(txtISBN, 20);

		reset();
		lblISSN = new Label(group, SWT.NONE);
		add(lblISSN);

		txtISSN = new Text(group, SWT.NONE);
		add(txtISSN);
		validator.applyValidator(txtISSN, 20);

		lblPrice = new Label(group, SWT.NONE);
		add(lblPrice);

		txtPrice = new Text(group, SWT.NONE);
		add(txtPrice);
		validator.applyValidator(txtPrice, "txtPrice",
				DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(txtPrice, "txtPrice1", DecoratorType.DOUBLE,
				true);

		reset();

		lblCoinType = new Label(group, SWT.NONE);
		add(lblCoinType);

		cbCoinType = new Combo(group, SWT.READ_ONLY);
		add(cbCoinType);
		initialize(
				cbCoinType,
				((AllManagementController) controller).getSuggestion()
						.findAllNomenclators(library.getLibraryID(),
								Nomenclator.COIN_TYPE));
		validator.applyValidator(cbCoinType, "cbCoinType1",
				DecoratorType.REQUIRED_FIELD, true);

		lblQuantity = new Label(group, SWT.NONE);
		add(lblQuantity);

		sp_Quantity = new Text(group, SWT.NONE);
		add(sp_Quantity);
		validator.applyValidator(sp_Quantity, "sp_Quantity1",
				DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(sp_Quantity, "sp_Quantity",
				DecoratorType.NUMBER_ONLY_NOT_START_WITH_CERO, true, 4);
		reset();

		lblReasonForSuggestions = new Label(group, SWT.NONE);
		add(lblReasonForSuggestions);

		txtReasonForSuggestions = new Text(group, SWT.V_SCROLL | SWT.WRAP);
		add(txtReasonForSuggestions);
		validator.applyValidator(txtReasonForSuggestions, 500);
		
		// -------------Composite suggestion Associated----------------

		compoSuggestionA = new Composite(parent, SWT.NORMAL);
		compoSuggestionA.setVisible(false);
		addComposite(compoSuggestionA);
		compoSuggestionA.setData(RWT.CUSTOM_VARIANT, "gray_background");

		separator = new Label(compoSuggestionA, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);

		lblFind = new Label(compoSuggestionA, SWT.NONE);
		FormDatas.attach(lblFind).atTopTo(separator, 19).atLeft(15);
		// add(lblFind);

		txtFind = new Text(compoSuggestionA, SWT.NONE);
		FormDatas.attach(txtFind).atTopTo(separator, 15).atLeftTo(lblFind, 10)
				.withHeight(12).withWidth(260);
		// add(txtFind);

		reset();
		consult = new Button(compoSuggestionA, SWT.PUSH);
		refresh();
		FormDatas.attach(consult).atLeftTo(txtFind, 10).atTopTo(separator, 15)
				.withHeight(22);
		// add(consult);

		txtFind.addFocusListener(new FocusListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void focusLost(FocusEvent arg0) {
				consult.getShell().setDefaultButton(null);
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				consult.getShell().setDefaultButton(consult);
			}
		
		});

		reset();
		add(new Label(compoSuggestionA, 0), Percent.W100);
		refresh();
		list = new Label(compoSuggestionA, SWT.NORMAL);
		addHeader(list);
		list.setVisible(false);

		// --------------------TABLA DE SUGERENCIAS
		// APROBADAS--------------------------------------
		approbedSuggestionTable = new CRUDTreeTable(compoSuggestionA, SWT.NONE);
		approbedSuggestionTable.setEntityClass(Suggestion.class);
		approbedSuggestionTable.setCancelButtonText("Cancelar");
		approbedSuggestionTable.setVisible(false);
		add(approbedSuggestionTable);

		TreeTableColumn columns[] = { new TreeTableColumn(20, 0, "getTitle"),
				new TreeTableColumn(20, 1, "getAuthor"),
				new TreeTableColumn(20, 2, "getEditorial"),
				new TreeTableColumn(20, 3, "getUser.getUsername"),
				new TreeTableColumn(20, 4, "getRegisterDate") };

	

		// approbedSuggestionTable.setPageSize(10);

		// searchCurrentApprobedSuggestionTable(page, size);

		// ----------------------------------FIN TABLA SUGERENCIAS
		// APROBADAS-------------------------

		// ------------TABLA DE SUGERENCIAS
		// ASOCIADAS-----------------------------------

		list2 = new Label(compoSuggestionA, SWT.NORMAL);
		list2.setVisible(false);
		addHeader(list2);

		associatedSuggestionTable = new CRUDTreeTable(compoSuggestionA,
				SWT.NONE);
		associatedSuggestionTable.setVisible(false);
		associatedSuggestionTable.setEntityClass(Suggestion.class);
		add(associatedSuggestionTable);
		associatedSuggestionTable.setDelete(true);

		
		Column checked = new Column("right-arrow", compoSuggestionA.getDisplay(), new TreeColumnListener() {
			
			@Override
			public void handleEvent(TreeColumnEvent event) {
				
				Suggestion suggestionAssociate = (Suggestion) event.entity.getRow();
					
				searchCurrentApprobedSuggestionTable(0, approbedSuggestionTable.getPageSize());
				
				temp.remove(suggestionAssociate);
				listSuggestionSelect.add(suggestionAssociate);
				
				
				searchCurrentApprobedSuggestionTable(0, approbedSuggestionTable.getPageSize());
				approbedSuggestionTable.getPaginator().goToFirstPage();
				approbedSuggestionTable.refresh();

				page(0, associatedSuggestionTable.getPageSize());
				associatedSuggestionTable.getPaginator().goToFirstPage();
				refresh();
				
			}
		});
		
		checked.setToolTipText(AbosMessages.get().BUTTON_ASSOCIATE_SELECTION);
		checked.setAlignment(SWT.CENTER);
		approbedSuggestionTable.addActionColumn(checked);
		approbedSuggestionTable.createTable(columns);

		approbedSuggestionTable.getPaginator().setPageSize(10);

		TreeTableColumn columns1[] = { new TreeTableColumn(20, 0, "getTitle"),
				new TreeTableColumn(20, 1, "getAuthor"),
				new TreeTableColumn(20, 2, "getEditorial"),
				new TreeTableColumn(20, 3, "getUser.getUsername"),
				new TreeTableColumn(20, 4, "getRegisterDate") };
		associatedSuggestionTable.createTable(columns1);

		associatedSuggestionTable.getPaginator().setPageSize(10);

		associatedSuggestionTable
				.addPageChangeListener(new PageChangeListener() {
					@Override
					public void pageChanged(final PageChangedEvent event) {

						page(event.currentPage - 1, event.pageSize);
					}
				});

		// Eventos del paginado
		approbedSuggestionTable.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(PageChangedEvent event) {

				searchCurrentApprobedSuggestionTable(event.currentPage - 1,
						event.pageSize);
			}
		});
		associatedSuggestionTable.addDeleteListener(new TreeColumnListener() {
			
			@Override
			public void handleEvent(TreeColumnEvent event) {
				Suggestion suggestionEliminate = (Suggestion) event.entity.getRow();
				
				temp.add(suggestionEliminate);
				listSuggestionSelect.remove(suggestionEliminate);
			
				searchCurrentApprobedSuggestionTable(0, approbedSuggestionTable.getPageSize());
				approbedSuggestionTable.getPaginator().goToFirstPage();
				approbedSuggestionTable.refresh();

				page(0, associatedSuggestionTable.getPageSize());
				associatedSuggestionTable.getPaginator().goToFirstPage();
				refresh();
				
			}
		});
		// ----------------------------------FIN TABLA SUGERENCIAS
		// ASOCIADAS-------------------------------------

		// Para cuando seleccione registrar sin sugerencias
		rd_sSugerencias.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				compoSuggestionA.setVisible(false);

				insertComposite(compoSuggestionA, group);

				compoSuggestionA.getShell().layout(true, true);
				compoSuggestionA.getShell().redraw();
				compoSuggestionA.getShell().update();

				if (registerDesiderata != null)
					registerDesiderata.refresh();
				
				refresh();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		// Para cuando seleccione registrar con sugerencias
		rd_cSugerencias.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				compoSuggestionA.setVisible(true);

				insertComposite(compoSuggestionA, group);

				compoSuggestionA.getShell().layout(true, true);
				compoSuggestionA.getShell().redraw();
				compoSuggestionA.getShell().update();

				if (registerDesiderata != null)
					registerDesiderata.refresh();
				refresh();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		// Consultar sugerencias de desideratas
		consult.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				approbedSuggestionTable.setVisible(true);
				list.setVisible(true);
				approbedSuggestionTable.clearRows();
				list2.setVisible(true);
				associatedSuggestionTable.setVisible(true);

				params = (txtFind.getText().length() > 0 ? txtFind.getText()
						: null);

				direction = 1024;
				searchCurrentApprobedSuggestionTable(0,
						approbedSuggestionTable.getPageSize());
				if (approbedSuggestionTable.getRows().isEmpty()) {
					
					if (associatedSuggestionTable.getRows().isEmpty()) {	
					
					approbedSuggestionTable.setVisible(false);
					list.setVisible(false);
					list2.setVisible(false);
					associatedSuggestionTable.setVisible(false);
					}
					RetroalimentationUtils
							.showInformationMessage(
									compoSuggestionA,
									cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
				} else
					approbedSuggestionTable.getPaginator().goToFirstPage();

				if (registerDesiderata != null)
					registerDesiderata.refresh();
				
				refresh();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

		compoButton = new Composite(parent, SWT.NORMAL);
		addComposite(compoButton);
		compoButton.setData(RWT.CUSTOM_VARIANT, "gray_background");

		cancelButton = new Button(compoButton, SWT.NONE);
		cancelButton.setVisible(false);
		add(cancelButton);

		registerButton = new Button(compoButton, SWT.NONE);
		cancelButton.setVisible(false);
		add(registerButton);

		l10n();
		LoadDesiderataData();

		return parent;
	}

	public ValidatorUtils getValidator() {
		return validator;
	}

	public void setValidator(ValidatorUtils validator) {
		this.validator = validator;
	}

	// Buscar las sugerencias aprobadas
	public void searchCurrentApprobedSuggestionTable(int page, int size) {
		listDB = ((AllManagementController) controller)
				.findAllPendingSuggestions(
						((AllManagementController) controller)
								.getSuggestion()
								.getNomenclator(
										AdquisitionNomenclator.SUGGESTION_STATE_APROVED),
						params, page, 20, direction, orderByStringSuggestion);
		
		approbedSuggestionTable.clearRows();
		
		
		if (temp.isEmpty()) {
			temp.addAll(listDB.getContent());
		}
			
		for (int i = 0; i < temp.size(); i++) {
			for (int j = 0; j < listSuggestionSelect.size(); j++) {
				try {
					if (temp.get(i).getSuggestionID().equals(listSuggestionSelect.get(j).getSuggestionID())) {
						
						temp.remove(i--);
					}	
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}			
		}
		
		approbedSuggestionTable.setTotalElements((int) temp.size());
		
		if (temp.size() <= page * size + size) {
			approbedSuggestionTable.setRows(temp.subList(page * size, temp.size()));
		} else {
			approbedSuggestionTable.setRows(temp.subList(page * size, page * size + size));
		}	
		
		associatedSuggestionTable.refresh();
		approbedSuggestionTable.refresh();
		approbedSuggestionTable.getParent().update();

	}

	private void page(int page, int size) {
		associatedSuggestionTable.clearRows();

		associatedSuggestionTable.setTotalElements((int) listSuggestionSelect
				.size());
		if (listSuggestionSelect.size() <= page * size + size) {
			associatedSuggestionTable.setRows(listSuggestionSelect.subList(page
					* size, listSuggestionSelect.size()));
		} else {
			associatedSuggestionTable.setRows(listSuggestionSelect.subList(page
					* size, page * size + size));
		}
		associatedSuggestionTable.refresh();
		approbedSuggestionTable.refresh();
	}

	public void l10n() {
		lbRegister.setText(aux);
		rd_sSugerencias
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_WITHOUT_SUGGESTION_ASSOCIATED));
		rd_cSugerencias
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SUGGESTION_ASSOCIATED));
		lblTitle.setText(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE));
		lblAuthor
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_AUTHOR));
		lblEditorial
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_EDITORIAL));
		lblCityOfPublication
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_CITY));
		lblNumberOfEdition
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_NUMBER_OF_EDITION));
		lblPublicationYear
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_PUBLICATION_YEAR));
		lblTome.setText(MessageUtil.unescape(AbosMessages.get().LABEL_TOME));
		lblVolume
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_VOLUME));
		lblPrice.setText(MessageUtil.unescape(AbosMessages.get().LABEL_PRICE_OF_A_COPY));
		lblReasonForSuggestions
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SUGGESTION_REASON));
		txtFind.setMessage(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE)
				+ " | " + MessageUtil.unescape(AbosMessages.get().LABEL_AUTHOR)
				+ " | "
				+ MessageUtil.unescape(AbosMessages.get().LABEL_EDITORIAL));
		lblQuantity
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_NUMBER_OF_COPIES));
		list.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LIST_OF_APPROVED_SUGGESTION));
		approbedSuggestionTable.setColumnHeaders(Arrays.asList(MessageUtil
				.unescape(AbosMessages.get().LABEL_TITLE), MessageUtil
				.unescape(AbosMessages.get().LABEL_AUTHOR), MessageUtil
				.unescape(AbosMessages.get().LABEL_EDITORIAL), MessageUtil
				.unescape(AbosMessages.get().LABEL_SUGGESTED_BY), MessageUtil
				.unescape(AbosMessages.get().LABEL_DATE_OF_REGISTRATION)));
		associatedSuggestionTable.setColumnHeaders(Arrays.asList(MessageUtil
				.unescape(AbosMessages.get().LABEL_TITLE), MessageUtil
				.unescape(AbosMessages.get().LABEL_AUTHOR), MessageUtil
				.unescape(AbosMessages.get().LABEL_EDITORIAL), MessageUtil
				.unescape(AbosMessages.get().LABEL_SUGGESTED_BY), MessageUtil
				.unescape(AbosMessages.get().LABEL_DATE_OF_REGISTRATION)));
		list2.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LIST_OF_ASSOCIATED_SUGGESTIONS));
		lblDataDesiderata
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_DATA_DESIDERATA));
		lblNumber
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_NUMBER));
		lblISBN.setText(MessageUtil.unescape(AbosMessages.get().LABEL_ISBN));
		lblISSN.setText(MessageUtil.unescape(AbosMessages.get().LABEL_ISSN));
		consult.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_SEARCH));
		lblFind.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEARCH_SUGGESTIONS_BY));
		lblCoinType
				.setText((MessageUtil.unescape(AbosMessages.get().LABEL_COIN)));
		cancelButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CANCEL));

		// initialize(cbUserType, ((AllManagementController)
		// controller).getSuggestion().findAllNomenclators(library.getLibraryID(),Nomenclator.LOANUSER_TYPE));

		for (int i = 1800; i < 2030; i++) {
			String aux = "";
			aux += i;
			cbPublicationYear.add(aux);
		}
	}

	public void LoadDesiderataData() {
		if (desiderata != null) {

			if (desiderata.getEditionNumber() == null)
				txtNumberOfEdition.setText("");
			else
				txtNumberOfEdition.setText(desiderata.getEditionNumber());

			if (desiderata.getAuthor() == null)
				txtAuthor.setText("");
			else
				txtAuthor.setText(desiderata.getAuthor());

			if (desiderata.getEditorial() == null)
				txtEditorial.setText("");
			else
				txtEditorial.setText(desiderata.getEditorial());

			if (desiderata.getIsbn() == null)
				txtISBN.setText("");
			else
				txtISBN.setText(desiderata.getIsbn());

			if (desiderata.getIssn() == null)
				txtISSN.setText("");
			else
				txtISSN.setText(desiderata.getIssn());

			txtNumber.setText(desiderata.getItemNumber());

			txtReasonForSuggestions.setText(desiderata.getMotive());

			txtPrice.setText(desiderata.getPrice().toString());

			if (desiderata.getPublicationCity() == null)
				txtPublicationCity.setText("");
			else
				txtPublicationCity.setText(desiderata.getPublicationCity());

			if (!cbPublicationYear.getText().equals(""))
				desiderata.setPublicationYear(Integer
						.parseInt(cbPublicationYear.getText()
								.replaceAll(" +", " ").trim()));

			sp_Quantity.setText(String.valueOf(desiderata.getQuantity()));

			txtTitle.setText(desiderata.getTitle());

			if (desiderata.getTome() == null)
				txtTome.setText("");
			else
				txtTome.setText(desiderata.getTome());

			UiUtils.initialize(
					cbCoinType,
					((AllManagementController) controller).getSuggestion()
							.findAllNomenclators(library.getLibraryID(),
									Nomenclator.COIN_TYPE));
			UiUtils.selectValue(cbCoinType, desiderata.getCointype());

			if (desiderata.getVolume() == null)
				txtVolume.setText("");
			else
				txtVolume.setText(desiderata.getVolume());

			if (!listSuggestionSelect.isEmpty()) {
				rd_cSugerencias.setSelection(true);
				rd_sSugerencias.setSelection(false);

				list.setVisible(true);
				approbedSuggestionTable.setVisible(true);

				list2.setVisible(true);
				associatedSuggestionTable.setVisible(true);

				compoSuggestionA.setVisible(true);

				insertComposite(compoSuggestionA, group);

				compoSuggestionA.getShell().layout(true, true);
				compoSuggestionA.getShell().redraw();
				compoSuggestionA.getShell().update();

				searchCurrentApprobedSuggestionTable(page, size);
				page(page, size);

				approbedSuggestionTable.getPaginator().goToFirstPage();
				associatedSuggestionTable.getPaginator().goToFirstPage();

			} else {
				rd_sSugerencias.setSelection(true);
				rd_cSugerencias.setSelection(false);

				compoSuggestionA.setVisible(false);

				insertComposite(compoSuggestionA, group);

				compoSuggestionA.getShell().layout(true, true);
				compoSuggestionA.getShell().redraw();
				compoSuggestionA.getShell().update();
			}
		}
	}

	// Limpiar los campos del registro de la desiderata
	public void limpiarCampos() {
		if (validator.decorationFactory.AllControlDecorationsHide()) {
			txtAuthor.setText("");
			txtEditorial.setText("");
			txtFind.setText("");
			txtISBN.setText("");
			txtISSN.setText("");
			txtNumber.setText("");
			txtNumberOfEdition.setText("");
			txtPrice.setText("");
			txtPublicationCity.setText("");
			cbPublicationYear.setText("");
			txtReasonForSuggestions.setText("");
			txtTitle.setText("");
			txtTome.setText("");
			txtVolume.setText("");
			cbCoinType.select(0);
			sp_Quantity.setText("");
		}
	}

	public CRUDTreeTable getApprobedSuggestionTable() {
		return approbedSuggestionTable;
	}

	public void setApprobedSuggestionTable(CRUDTreeTable approbedSuggestionTable) {
		this.approbedSuggestionTable = approbedSuggestionTable;
	}

	public CRUDTreeTable getAssociatedSuggestionTable() {
		return associatedSuggestionTable;
	}

	public void setAssociatedSuggestionTable(
			CRUDTreeTable associatedSuggestionTable) {
		this.associatedSuggestionTable = associatedSuggestionTable;
	}

	public void addOkListener(SelectionListener listener) {
		registerButton.addSelectionListener(listener);
	}

	public void addCancelListener(SelectionListener listener) {
		cancelButton.addSelectionListener(listener);
	}

	public boolean validate() {
		if (getValidator().decorationFactory
				.isRequiredControlDecorationIsVisible()) {
			showErrorMessage(MessageUtil
					.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
		} else if (getAssociatedSuggestionTable().getRows().isEmpty()
				&& rd_cSugerencias.getSelection())
			RetroalimentationUtils
					.showErrorMessage(AbosMessages.get().MESSAGES_ERROR_NO_SUGGESTION_ASSOCIATED);
		else if (getValidator().decorationFactory.AllControlDecorationsHide()) {
			return true;
		}
		else 
			showErrorMessage(MessageUtil
					.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_INCORRECT_DATA));
		return false;
	}
	
	
	public Desiderata llenarDesiderata( ){
		Library	library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
			
		User user = (User)SecurityUtils.getService().getPrincipal().getByKey("user");
        long  idPerson = user.getPerson().getPersonID();
        Worker workerLoggin = ((AllManagementController)controller).getPurchaseRequest().getWorkerByPerson(idPerson);
        Desiderata d;
        if (desiderata !=null) {
		d=desiderata;
	}else{
		 d= new Desiderata();
	}
        
     	java.util.Date fecha=new java.util.Date();
		
		d.setCreationDate(new Date((fecha.getYear()), fecha.getMonth(), fecha.getDate()));
		
			d.setEditionNumber(txtNumberOfEdition.getText().replaceAll(" +", " ").trim());

			d.setAuthor(txtAuthor.getText().replaceAll(" +", " ").trim());

			if (txtEditorial.getText().isEmpty())
				d.setEditorial("");
			else
				d.setEditorial(txtEditorial.getText().replaceAll(" +", " ").trim());

			if (txtISBN.getText().isEmpty())
				d.setIsbn("");
			else
				d.setIsbn(txtISBN.getText().replaceAll(" +", " ").trim());

			if (txtISSN.getText().isEmpty())
				d.setIssn("");
			else
				d.setIssn(txtISSN.getText().replaceAll(" +", " ").trim());

			d.setItemNumber(txtNumber.getText().replaceAll(" +", " ").trim());

			d.setLibrary(library);

			d.setMotive(txtReasonForSuggestions.getText().replaceAll(" +", " ").trim());
			
			d.setPrice(Double.parseDouble(txtPrice.getText().replaceAll(" +", " ").trim()));

			if (txtPublicationCity.getText().isEmpty())
				d.setPublicationCity("");
			else
				d.setPublicationCity(txtPublicationCity.getText().replaceAll(" +", " ").trim());

			if (!cbPublicationYear.getText().isEmpty())
				d.setPublicationYear(Integer.parseInt(cbPublicationYear.getText().replaceAll(" +", " ").trim()));

			d.setQuantity(Integer.parseInt(sp_Quantity.getText().replaceAll(" +", " ").trim()));

			d.setState(((AllManagementController) controller).getSuggestion().getNomenclator(AdquisitionNomenclator.DESIDERATA_STATE_PENDING));
			
			if (txtTitle.getText().isEmpty())
				d.setTitle("");
			else
				d.setTitle(txtTitle.getText().replaceAll(" +", " ").trim());

			if (txtTome.getText().isEmpty())
				d.setTome("");
			else
				d.setTome(txtTome.getText());
	
			d.setCointype( (Nomenclator) UiUtils.getSelected(cbCoinType));
			
			d.setVolume(txtVolume.getText().replaceAll(" +", " ").trim());
			
			d.setCreator(workerLoggin);
			((AllManagementController) controller).getDesiderata().setDesiderata(d);
			if (rd_cSugerencias.getSelection()) 
				{					
						if (desiderata !=null) {					
						
							for(int i=0;i<suggestionList.size();i++)
							{	
								suggestionList.get(i).setState(((AllManagementController)controller).getSuggestion().getNomenclator(AdquisitionNomenclator.SUGGESTION_STATE_APROVED));
								try {									
										suggestionList.get(i).setAssociateDesiderata(null);;														
									} catch (Exception e) {										
									}
								((AllManagementController) controller).getSuggestion().addSuggestion(suggestionList.get(i));
								
							}				
						}
						
						for (int i = 0; i < listSuggestionSelect.size(); i++) {
							getListSuggestionSelect().get(i).setState(((AllManagementController) controller).getSuggestion().getNomenclator(AdquisitionNomenclator.SUGGESTION_STATE_EXECUTED));
							getListSuggestionSelect().get(i).setAssociateDesiderata(d);
							((AllManagementController) controller).getSuggestion().addSuggestion(getListSuggestionSelect().get(i));
							
						}				
				}
			    return d;
			}
	

	public void setOkButtonText(String text){
	    	registerButton.setText(text);
	    }

	
	public void buttonVisible(boolean var)
	{
		registerButton.setVisible(var);
		cancelButton.setVisible(var);
	}
	

	public List<Suggestion> getListSuggestionSelect() {
		return listSuggestionSelect;
	}

	public void setListSuggestionSelect(List<Suggestion> listSuggestionSelect) {
		this.listSuggestionSelect = listSuggestionSelect;
	}

	public List<Suggestion> getSuggestionList() {
		return suggestionList;
	}

	public void setSuggestionList(List<Suggestion> suggestionList) {
		this.suggestionList = suggestionList;
	}
	
		
}
