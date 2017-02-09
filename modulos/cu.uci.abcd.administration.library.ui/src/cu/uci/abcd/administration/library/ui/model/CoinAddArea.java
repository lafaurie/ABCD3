package cu.uci.abcd.administration.library.ui.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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

public class CoinAddArea extends BaseEditableArea{
	private ViewController controller;
	private Coin coin = null;
	private RegisterCoinFragment saveCoinFragment;
	private Composite parentComposite;
	private Button saveButton;
	private int dimension;
	private CRUDTreeTable tableCoin;

	public CoinAddArea(ViewController controller, CRUDTreeTable tableCoin) {
		this.controller = controller;
		this.tableCoin = tableCoin;
		coin = null;
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
		saveCoinFragment = new RegisterCoinFragment(coin, controller, dimension, 1);
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
					
					
					
					String coinName = saveCoinFragment.getCoinNameText().getText().replaceAll(" +", " ").trim();
					Nomenclator coinType = (Nomenclator) UiUtils.getSelected(saveCoinFragment.getCoinIdText());
					String exchangeRate = saveCoinFragment.getExchangeRateText().getText();
					exchangeRate = exchangeRate.replace(",", ".");
					int fromYear = saveCoinFragment.getLastUpdateDateTime().getYear() - 1900;
					int fromMonth = saveCoinFragment.getLastUpdateDateTime().getMonth();
					int fromDay = saveCoinFragment.getLastUpdateDateTime().getDay();
					@SuppressWarnings("deprecation")
					Date lastUpdate = new Date(fromYear, fromMonth, fromDay);
					Library library = (Library) SecurityUtils
							.getService().getPrincipal()
							.getByKey("library");
					
					Coin exist = ((CoinViewController) controller).findCoinByName(coinName, library.getLibraryID());
					
					if( exist==null ){
					List<Coin> allCoins = ((CoinViewController) controller)
							.findAll();
					List<Nomenclator> allCoinsNomenclator = new ArrayList<>();
					for (Coin coin : allCoins) {
						allCoinsNomenclator.add(coin.getCoinType());
					}
					if (!allCoinsNomenclator.contains(coinType)) {
							coin = new Coin();
						coin.setCoinName(coinName);
						coin.setCoinType(coinType);
						coin.setExchangeRate(exchangeRate);
						coin.setUpdatedDate(lastUpdate);
						coin.setLibrary(library);
						Coin coinSaved = ((CoinViewController) controller)
								.saveCoin(coin);
						if (coinSaved!=null) {
							manager.add(new BaseGridViewEntity<Coin>(coinSaved));
							manager.refresh();
							
							Composite viewSmg = ((CoinViewArea)tableCoin.getActiveArea()).getMsg();

							RetroalimentationUtils.showInformationMessage(viewSmg, MessageUtil
									.unescape(cu.uci.abos.core.l10n.AbosMessages
											.get().MSG_INF_CREATE_NEW_ELEMENT));

							coin = null;
							
							tableCoin.getPaginator().goToFirstPage();
						
						}else{
							String msgShow = cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NOT_SAVED_ITEM;
							
							RetroalimentationUtils
								.showErrorShellMessage(
										
										msgShow);
						}
					} else {
						RetroalimentationUtils.showErrorShellMessage(
								MessageUtil
								.unescape(AbosMessages
										.get().COIN_EXIST_COIN_TYPE));
					}
				}else{
					RetroalimentationUtils.showErrorShellMessage(
							MessageUtil
							.unescape(AbosMessages
									.get().ELEMENT_EXIST));
				}
					
				} else {
					RetroalimentationUtils.showErrorShellMessage(
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
		return "addCoinID";
	}
	/*
	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	*/

}
