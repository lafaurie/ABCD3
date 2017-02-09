package cu.uci.abcd.cataloguing.listener;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Color;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.springframework.data.domain.Page;

import cu.uci.abcd.cataloguing.ILoanObjectCreation;
import cu.uci.abcd.cataloguing.util.LoanObjectField2;
import cu.uci.abcd.domain.acquisition.Suggestion;
import cu.uci.abcd.domain.cataloguing.CataloguingNomenclator;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Provider;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abos.core.l10n.AbosMessages;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;
import cu.uci.abos.widget.template.util.Util;

public class EventLoanObjectCreation extends ContributorPage implements Listener {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private static final long serialVersionUID = 1L;

	private CRUDTreeTable grid;
	private LoanObjectField2 loanObjectField;
	private ILoanObjectCreation loanObjectCreation;
	private LoanObject loanObject;
	private boolean insert;
	private CTabFolder acquisitionTypeFolder;
	private List<Room> rooms;
	private List<Nomenclator> situations;
	private List<Provider> providers;
	private List<Nomenclator> acquisitionType;
	private List<Nomenclator> acquisitionCoinType;
	private List<Nomenclator> loanObjectType;
	private List<Suggestion> suggestions;
	private boolean first;
	private int direction = 1024;
	private String orderByString = "id";
	private Button saveButton;
	private Button newButton;
	private Composite content;
	private ExpandBar bar;
	private ExpandItem expandItem;
	private ValidatorUtils validator;

	public EventLoanObjectCreation(CRUDTreeTable grid, LoanObjectField2 loanObjectField, ILoanObjectCreation loanObjectCreation, LoanObject loanObject, CTabFolder acquisitionTypeFolder, Button saveButton, Composite content, ExpandBar bar, ExpandItem expandItem, ValidatorUtils validator) {
		this.grid = grid;
		this.loanObjectField = loanObjectField;
		this.loanObjectCreation = loanObjectCreation;
		this.loanObject = loanObject;
		this.insert = true;
		this.setFirst(false);
		this.acquisitionTypeFolder = acquisitionTypeFolder;
		this.saveButton = saveButton;
		this.content = content;
		this.bar = bar;
		this.expandItem = expandItem;
		this.validator = validator;
	}

	@Override
	public void handleEvent(Event arg0) {

		boolean result = loanObjectField.validate();
		int positionAcquisitionType = acquisitionTypeFolder.getSelectionIndex();

		if (result) {
			Map<String, ControlDecoration> controls = validator.decorationFactory.getListMapControlDecorations();

			// mandatoryValidation
			ControlDecoration inventaryNumberD = controls.get("txt_inventarynumber");

			ControlDecoration price1D = controls.get("txt_price1");
			ControlDecoration price2D = controls.get("txt_price2");
			ControlDecoration price3D = controls.get("txt_price3");

			ControlDecoration locationD = controls.get("cmb_location");
			ControlDecoration acquisitionCoinTypeD = controls.get("cmb_acquisitionCoinType");
			ControlDecoration loanObjectTypeD = controls.get("cmb_loanObjectType");

			boolean priceValueD = true;

			switch (positionAcquisitionType) {
			case 0:
				if (!price1D.isVisible())
					priceValueD = false;
				break;

			case 1:
				if (!price2D.isVisible())
					priceValueD = false;
				break;

			case 2:
				if (!price3D.isVisible())
					priceValueD = false;
				break;
			}

			boolean inventaryNumberValueD = inventaryNumberD.isVisible();
			boolean locationValueD = locationD.isVisible();
			boolean acquisitionCoinTypeValueD = acquisitionCoinTypeD.isVisible();
			boolean loanObjectTypeValueD = loanObjectTypeD.isVisible();

			if (priceValueD || inventaryNumberValueD || locationValueD || acquisitionCoinTypeValueD || loanObjectTypeValueD) {
				validator.decorationFactory.AllControlDecorationsHide();
				RetroalimentationUtils.showErrorShellMessage(AbosMessages.get().MSG_ERROR_FIELD_REQUIRED);
			} else {
				if (validator.decorationFactory.AllControlDecorationsHide()) {

					if (!first && insert) {
						String title = loanObject.getTitle();
						String author = loanObject.getAuthor();
						Nomenclator recordType = loanObject.getRecordType();
						String dataBaseName = loanObject.getIsisDataBaseName();
						String controlNumber = loanObject.getControlNumber();
						Library userLogLibrary = loanObject.getLibraryOwner();
						String editionNumber = loanObject.getEditionNumber();
						loanObject = new LoanObject();
						loanObject.setTitle(title);
						loanObject.setAuthor(author);
						loanObject.setIsisDataBaseName(dataBaseName);
						loanObject.setControlNumber(controlNumber);
						loanObject.setLibraryOwner(userLogLibrary);
						loanObject.setRecordType(recordType);
						loanObject.setEditionNumber(editionNumber);
					}

					// library
					Library library = Util.getLibrary();
					loanObject.setLibraryOwner(library);

					// location(Room)
					int roomPosition = loanObjectField.getLocationCombo().getSelectionIndex();
					Room room = rooms.get(roomPosition - 1);
					loanObject.setRoom(room);

					// situation
					Nomenclator situation = null;
					int situationPosition = loanObjectField.getSituationCombo().getSelectionIndex();
					if (situationPosition != 0) {
						situation = situations.get(situationPosition - 1);
						loanObject.setSituation(situation);

						// loanObject state
						if (situation.getNomenclatorID().equals(CataloguingNomenclator.SITUATION_LOANOBJECT)) {
							loanObject.setLoanObjectState(loanObjectCreation.getNomenclator(CataloguingNomenclator.LOANOBJECT_STATE_AVAILABLE));
						} else if (situation.getNomenclatorID().equals(CataloguingNomenclator.SITUATION_LOST)) {
							loanObject.setLoanObjectState(loanObjectCreation.getNomenclator(CataloguingNomenclator.LOANOBJECT_STATE_LOST));
						} else
							loanObject.setLoanObjectState(loanObjectCreation.getNomenclator(CataloguingNomenclator.LOANOBJECT_STATE_NOT_AVAILABLE));
					} else {
						loanObject.setSituation(null);
						loanObject.setLoanObjectState(loanObjectCreation.getNomenclator(CataloguingNomenclator.LOANOBJECT_STATE_NOT_AVAILABLE));
					}

					// provider
					int providerPosition = loanObjectField.getProviderCombo().getSelectionIndex();
					if (providerPosition != 0) {
						Provider provider = providers.get(providerPosition - 1);
						loanObject.setProvider(provider);
					}

					// acquisitionCoinType
					int acquisitionCoinTypePosition = loanObjectField.getAcquisitionCoinType().getSelectionIndex();
					Nomenclator acquisitionCoinTypeNomenclator = acquisitionCoinType.get(acquisitionCoinTypePosition - 1);
					loanObject.setAcquisitionCoinType(acquisitionCoinTypeNomenclator);

					// loanObjectType
					int loanObjectTypePosition = loanObjectField.getLoanObjectType().getSelectionIndex();
					Nomenclator loanObjectTypeNomenclator = loanObjectType.get(loanObjectTypePosition - 1);
					loanObject.setRecordType(loanObjectTypeNomenclator);

					// inventoryNumber
					String inventoryNumberValue = loanObjectField.getInventoryNumberText().getText();
					loanObject.setInventorynumber(inventoryNumberValue);

					// tome
					String tomeValue = loanObjectField.getTomeText().getText();
					if (!tomeValue.equals("") && tomeValue != null)
						loanObject.setTome(tomeValue);

					// volume
					String volumeValue = loanObjectField.getVolumeText().getText();
					if (!volumeValue.equals("") && volumeValue != null)
						loanObject.setVolume(volumeValue);

					// acquisitionType
					int acquisitionTypePosition = acquisitionTypeFolder.getSelectionIndex();
					Nomenclator acquisitionTypeNomenclator = acquisitionType.get(acquisitionTypePosition);
					loanObject.setAcquisitionType(acquisitionTypeNomenclator);

					if (positionAcquisitionType == 0) {

						// noRecommend
						int suggestionPosition = loanObjectField.getNoRecommendCombo().getSelectionIndex();
						if (suggestionPosition != 0) {
							Suggestion suggestion = suggestions.get(suggestionPosition - 1);
							loanObject.setRecommendation(suggestion);
						}

						// price
						String buyPriceValue = loanObjectField.getPriceText().getText();

						loanObject.setPrice(Double.parseDouble(buyPriceValue));
						loanObject.setConditions(null);
					} else if (positionAcquisitionType == 1) {

						// conditions
						String conditionsValue = loanObjectField.getConditionsText().getText();
						if (!conditionsValue.equals("") && !conditionsValue.equals(null)) {
							loanObject.setConditions(conditionsValue);
						}

						// donationEstimatedPrice
						String donationEstimatedPriceValue = loanObjectField.getDonationEstimatedPriceText().getText();
						loanObject.setPrice(Double.parseDouble(donationEstimatedPriceValue));

						// loanObject.setPurchaseOrder(null);
						loanObject.setRecommendation(null);
						// loanObject.setExchangedBy(null);
					} else if (positionAcquisitionType == 2) {

						// redeemendBy
						String redeemendByValue = loanObjectField.getRedeemendByText().getText();
						if (!redeemendByValue.equals("") && !redeemendByValue.equals(null)) {
							loanObject.setExchangedBy(redeemendByValue);
						}

						// redeemendEstimatedPrice)
						String redeemendEstimatedPriceValue = loanObjectField.getRedeemendEstimatedPriceText().getText();

						loanObject.setPrice(Double.parseDouble(redeemendEstimatedPriceValue));

						// loanObject.setPurchaseOrder(null);
						loanObject.setRecommendation(null);
						loanObject.setConditions(null);
					}

					if (insert) {
						// registrationDate
						java.util.Date date = new java.util.Date();
						Date sqlDate = new Date(date.getTime());
						loanObject.setRegistrationDate(sqlDate);
						loanObject.setCatalogued(true);
					}

					loanObjectCreation.addLoanObject(loanObject);
					this.first = false;

					// views
					grid.refresh();					
					searchLoanObjects(0, grid.getPageSize(), loanObject.getControlNumber());

					grid.addPageChangeListener(new PageChangeListener() {
						@Override
						public void pageChanged(PageChangedEvent event) {
							try {
								searchLoanObjects(event.currentPage - 1, event.pageSize, loanObject.getControlNumber());
								refresh();
							} catch (Exception e) {

							}

						}
					});

					if (!insert) {
						int currentPage = grid.getPaginator().getCurrentPage();
						grid.getPaginator().goToPage(currentPage);
					} else
						grid.getPaginator().goToLastPage();

					grid.refresh();

					loanObjectField.resetValues();

					saveButton.dispose();
					newButton = new Button(content, SWT.PUSH);
					newButton.setText(cu.uci.abos.l10n.cataloguing.AbosMessages.get().BUTTON_INSERT_COPIE);
					newButton.pack();
					loanObjectField.setButtonSave(newButton);

					EventLoanObjectCreation insertEvent = new EventLoanObjectCreation(grid, loanObjectField, loanObjectCreation, loanObject, acquisitionTypeFolder, newButton, content, bar, expandItem, validator);
					loanObjectField.setButtonSave(newButton);

					insertEvent.setAcquisitionCoinType(acquisitionCoinType);
					insertEvent.setAcquisitionType(acquisitionType);
					insertEvent.setProviders(providers);
					insertEvent.setRooms(rooms);
					insertEvent.setSituations(situations);
					insertEvent.setSuggestions(suggestions);
					insertEvent.setLoanObjectType(loanObjectType);

					newButton.addListener(SWT.Selection, insertEvent);

					FormDatas.attach(newButton).atTopTo(loanObjectField.getVolumeText(), 125).atLeftTo(acquisitionTypeFolder, 2);

					content.layout(true, true);
					content.redraw();
					content.update();

					expandItem.setControl(content);

					bar.layout(true, true);
					bar.redraw();
					bar.update();

					// updates values of the combos
					this.comboValuesUpdate(positionAcquisitionType);

					if (insert)
						RetroalimentationUtils.showInformationMessage(AbosMessages.get().MSG_INF_CREATE_NEW_ELEMENT);
					else
						RetroalimentationUtils.showInformationMessage(AbosMessages.get().MSG_INF_UPDATE_DATA);
				} else
					RetroalimentationUtils.showErrorShellMessage(AbosMessages.get().MSG_ERROR_INCORRECT_DATA);
			}
		} else {
			RetroalimentationUtils.showErrorShellMessage(AbosMessages.get().MSG_ERROR_FIELD_REQUIRED);

			validator.decorationFactory.AllControlDecorationsHide();
		}
		try {
			grid.getPaginator().goToFirstPage();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void setIsert(boolean insert) {
		this.insert = insert;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public List<Nomenclator> getSituations() {
		return situations;
	}

	public void setSituations(List<Nomenclator> situations) {
		this.situations = situations;
	}

	public List<Provider> getProviders() {
		return providers;
	}

	public void setProviders(List<Provider> providers) {
		this.providers = providers;
	}

	public List<Nomenclator> getAcquisitionType() {
		return acquisitionType;
	}

	public void setAcquisitionType(List<Nomenclator> acquisitionType) {
		this.acquisitionType = acquisitionType;
	}

	public List<Nomenclator> getAcquisitionCoinType() {
		return acquisitionCoinType;
	}

	public void setAcquisitionCoinType(List<Nomenclator> acquisitionCoinType) {
		this.acquisitionCoinType = acquisitionCoinType;
	}

	public List<Suggestion> getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(List<Suggestion> suggestions) {
		this.suggestions = suggestions;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public void setLoanObjectType(List<Nomenclator> loanObjectType) {
		this.loanObjectType = loanObjectType;
	}

	public List<Nomenclator> getLoanObjectType() {
		return this.loanObjectType;
	}

	private void searchLoanObjects(int page, int size, String controlNumber) {
		grid.clearRows();

		Page<LoanObject> loanObjects = loanObjectCreation.findAllByControlNumber(controlNumber, page, size, direction, orderByString);

		grid.setTotalElements((int) loanObjects.getTotalElements());
		if ((int) loanObjects.getTotalElements() > 0) {
			grid.setRows(loanObjects.getContent());
			grid.refresh();
		}
	}

	private void comboValuesUpdate(int tabFolderSelected) {
		// location
		Combo locationCombo = loanObjectField.getLocationCombo();
		List<Room> rooms = loanObjectCreation.getRooms();
		initialize(locationCombo, rooms);
		locationCombo.setBackground(new Color(bar.getDisplay(), 239, 239, 239));

		// situation
		Combo situationCombo = loanObjectField.getSituationCombo();
		List<Nomenclator> situations = loanObjectCreation.getNomenclatorByParent(CataloguingNomenclator.SITUATION);
		initialize(situationCombo, situations);

		// provider
		Combo providerCombo = loanObjectField.getProviderCombo();
		List<Provider> providers = loanObjectCreation.getProviders();
		initialize(providerCombo, providers);

		// acquisitionCoinType
		Combo acquisitionCoinType = loanObjectField.getAcquisitionCoinType();
		List<Nomenclator> acquisitionCoinTypeNomenclator = loanObjectCreation.getNomenclatorByParent(CataloguingNomenclator.ACQUISITION_COIN_TYPE);
		initialize(acquisitionCoinType, acquisitionCoinTypeNomenclator);
		acquisitionCoinType.setBackground(new Color(bar.getDisplay(), 239, 239, 239));

		// loanObjectType
		Combo loanObjectType = loanObjectField.getLoanObjectType();
		List<Nomenclator> loanObjectTypes = loanObjectCreation.getNomenclatorByParent(CataloguingNomenclator.LOAN_OBJECT_TYPE);
		initialize(loanObjectType, loanObjectTypes);
		loanObjectType.setBackground(new Color(bar.getDisplay(), 239, 239, 239));

		// inventaryNumber
		Text inventaryNumber = loanObjectField.getInventoryNumberText();
		inventaryNumber.setBackground(new Color(bar.getDisplay(), 239, 239, 239));

		// noRecommend
		Combo noRecommend = loanObjectField.getNoRecommendCombo();
		List<Suggestion> suggestions = loanObjectCreation.getSuggestion();
		int suggestionSize = suggestions.size();
		List<String> titles = new ArrayList<String>();

		for (int i = 0; i < suggestionSize; i++) {
			Suggestion suggestion = suggestions.get(i);
			titles.add(suggestion.getTitle());
		}
		initialize(noRecommend, titles);

		// prices
		Text price = null;

		switch (tabFolderSelected) {
		case 0:
			price = loanObjectField.getPriceText();
			break;

		case 1:
			price = loanObjectField.getDonationEstimatedPriceText();
			break;

		case 2:
			price = loanObjectField.getRedeemendEstimatedPriceText();
			break;
		}
		price.setBackground(new Color(bar.getDisplay(), 239, 239, 239));
	}

	@Override
	public Control createUIControl(Composite arg0) {

		return null;
	}

}
