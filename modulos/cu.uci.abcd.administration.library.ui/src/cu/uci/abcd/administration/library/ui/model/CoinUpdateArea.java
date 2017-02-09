package cu.uci.abcd.administration.library.ui.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import cu.uci.abcd.administration.library.communFragment.RegisterCoinFragment;
import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.administration.library.ui.controller.CoinViewController;
import cu.uci.abcd.administration.library.util.Auxiliary;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Coin;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.BaseGridViewEntity;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class CoinUpdateArea extends BaseEditableArea {

	private ViewController controller;
	private Coin coin = null;
	private RegisterCoinFragment saveCoinFragment;
	private Composite parentComposite;
	private Button saveButton;
	private int dimension;
	private CRUDTreeTable tableCoin;

	public CoinUpdateArea(ViewController controller, CRUDTreeTable tableCoin) {
		super();
		this.controller = controller;
		this.tableCoin = tableCoin;
	}

	@Override
	public boolean closable() {
		return true;
	}
	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity,
			IVisualEntityManager manager) {
		dimension = parent.getParent().getParent().getBounds().width;
		addComposite(parent);
		Coin coinToView = (Coin) entity.getRow();
		coin = ((CoinViewController) controller).getCoinById(coinToView
				.getCoinID());
		saveCoinFragment = new RegisterCoinFragment(coin, controller,
				dimension, 2);
		parentComposite = (Composite) saveCoinFragment.createUIControl(parent);
		return parentComposite;
	}

	@Override
	public Composite createButtons(Composite parent, IGridViewEntity entity,
			final IVisualEntityManager manager) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		saveButton = new Button(parent, SWT.PUSH);
		saveButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -5190720210118399943L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (saveCoinFragment.getValidator().decorationFactory
						.isRequiredControlDecorationIsVisible()) {
					RetroalimentationUtils.showErrorShellMessage(
							MessageUtil
							.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				} else {
					if (saveCoinFragment.getValidator().decorationFactory
							.AllControlDecorationsHide()) {
						
						if (Auxiliary.dateLessOrEqualToday(saveCoinFragment
								.getLastUpdateDateTime(), MessageUtil.unescape(AbosMessages
								.get().LABEL_LAST_UPDATE_TO_ACTUAL_DATE))) {
							
						String coinName = saveCoinFragment.getCoinNameText()
								.getText().replaceAll(" +", " ").trim();
						
						Nomenclator coinType = (Nomenclator) UiUtils
								.getSelected(saveCoinFragment.getCoinIdText());

						String exchangeRate = saveCoinFragment
										.getExchangeRateText().getText();
						exchangeRate = exchangeRate.replace(",", ".");
						
						int fromYear = saveCoinFragment.getLastUpdateDateTime()
								.getYear() - 1900;
						int fromMonth = saveCoinFragment
								.getLastUpdateDateTime().getMonth();
						int fromDay = saveCoinFragment.getLastUpdateDateTime()
								.getDay();
						@SuppressWarnings("deprecation")
						Date lastUpdate = new Date(fromYear, fromMonth, fromDay);

						Library library = (Library) SecurityUtils.getService()
								.getPrincipal().getByKey("library");
						
						Coin exist = ((CoinViewController) controller).findCoinByName(coinName, library.getLibraryID());
						if( exist==null || coin.getCoinID()==exist.getCoinID()  ){
						List<Coin> allCoins = ((CoinViewController) controller)
								.findAll();
						List<Nomenclator> allCoinsNomenclator = new ArrayList<>();
						for (Coin coin : allCoins) {
							allCoinsNomenclator.add(coin.getCoinType());
						}

						allCoinsNomenclator.remove(coin.getCoinType());

						if (!allCoinsNomenclator.contains(coinType)) {

							coin.setCoinName(coinName);
							coin.setCoinType(coinType);
							coin.setExchangeRate(exchangeRate);
							coin.setUpdatedDate(lastUpdate);
							coin.setLibrary(library);
							Coin coinSaved = ((CoinViewController) controller)
									.saveCoin(coin);
							//manager.save(new BaseGridViewEntity<Coin>(coinSaved));
							//manager.refresh();
							coin = null;
							
							if (coinSaved!=null) {
								manager.save(new BaseGridViewEntity<Coin>(coinSaved));
								manager.refresh();
								Composite viewSmg = ((CoinViewArea)tableCoin.getActiveArea()).getMsg();

								RetroalimentationUtils.showInformationMessage(viewSmg, MessageUtil
										.unescape(cu.uci.abos.core.l10n.AbosMessages
												.get().MSG_INF_UPDATE_DATA));

								coin = null;
								
								//tableCoin.getPaginator().goToPage(tableCoin.getPaginator().getCurrentPage());
								
							}else{
								String msgShow = cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NOT_SAVED_ITEM;
								
								RetroalimentationUtils
									.showErrorShellMessage(
											//saveCoinFragment.getParent(), 
											msgShow);
							}
							
							
							
							
							/*
							List<Coin> listAllCoins = ((CoinViewController) controller).findCoinByLibrary(library.getLibraryID());
							
							String pickList = listAllCoins.toString();
							pickList = pickList.replace("[", "");
							pickList = pickList.replace("]", "");
							String databaseName = "Registro_De_Adquisicion";
							String isisDefHome = library.getIsisDefHome();
							String workSheetNameDonation = "Donación";
							String workSheetNameCompra = "Compra";
							String workSheetNameDefault = "Default worksheet";
							String workSheetNameCanje = "Canje";
							try {
								
								WorksheetDef workSheetDonation = ((CoinViewController) controller).getAllManagementLibraryViewController().getLibraryService()
										.getWorksheet(
												workSheetNameDonation,
												databaseName, isisDefHome);
								workSheetDonation.getFieldByTag(22).setPickList(pickList);
								((CoinViewController) controller).getAllManagementLibraryViewController().getLibraryService()
										.updateWorksheet(workSheetDonation);

								
								WorksheetDef workSheetCompra = ((CoinViewController) controller).getAllManagementLibraryViewController().getLibraryService()
										.getWorksheet(workSheetNameCompra,
												databaseName, isisDefHome);
								workSheetCompra.getFieldByTag(22).setPickList(pickList);
								((CoinViewController) controller).getAllManagementLibraryViewController().getLibraryService()
										.updateWorksheet(workSheetCompra);

								
								WorksheetDef workSheetDefault = ((CoinViewController) controller).getAllManagementLibraryViewController().getLibraryService()
										.getWorksheet(workSheetNameDefault,
												databaseName, isisDefHome);
								workSheetDefault.getFieldByTag(22).setPickList(pickList);
								((CoinViewController) controller).getAllManagementLibraryViewController().getLibraryService()
										.updateWorksheet(workSheetDefault);

								
								WorksheetDef workSheetCanje = ((CoinViewController) controller).getAllManagementLibraryViewController().getLibraryService()
										.getWorksheet(workSheetNameCanje,
												databaseName, isisDefHome);
								workSheetCanje.getFieldByTag(22).setPickList(pickList);
								((CoinViewController) controller).getAllManagementLibraryViewController().getLibraryService()
										.updateWorksheet(workSheetCanje);
								
							} catch (Exception e1) {
								RetroalimentationUtils
										.showErrorMessage("JISIS is down");
							}
							
							*/
						} else {
							RetroalimentationUtils.showErrorShellMessage(
									//saveCoinFragment.getParent(), 
									MessageUtil
									.unescape(AbosMessages
											.get().COIN_EXIST_COIN_TYPE));
						}
					}else{
						RetroalimentationUtils.showErrorShellMessage(
								//saveCoinFragment.getParent(), 
								MessageUtil
								.unescape(AbosMessages
										.get().ELEMENT_EXIST));
					}
					}
					} else {
						RetroalimentationUtils.showErrorShellMessage(
								//saveCoinFragment.getParent(), 
								MessageUtil
								.unescape(cu.uci.abos.core.l10n.AbosMessages
										.get().MSG_ERROR_INCORRECT_DATA));
					}
				}
			}
		});
		return parent;
	}

	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public void l10n() {
		saveButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACEPT));
		saveButton.getParent().layout(true, true);
		saveButton.getParent().redraw();
		saveButton.getParent().update();
		saveCoinFragment.l10n();
	}
	
	@Override
	public String getID() {
		return "updateCoinID";
	}
}
