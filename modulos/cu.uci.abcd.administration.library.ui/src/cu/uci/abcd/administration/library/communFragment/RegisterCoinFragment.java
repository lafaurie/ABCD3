package cu.uci.abcd.administration.library.communFragment;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.administration.library.ui.controller.CoinViewController;
import cu.uci.abcd.administration.library.util.Auxiliary;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Coin;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.FragmentContributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.ui.FormPagePainter;
import cu.uci.abos.core.ui.PagePainter;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class RegisterCoinFragment extends ContributorPage implements FragmentContributor {

	private ViewController controller;
	private Coin coin;
	private Text coinNameText;
	private Label registerCoinLabel;
	private Label coinNameLabel;
	private Label coinIdLabel;
	private Combo coinIdText;
	private Label exchangeRateLabel;
	private Text exchangeRateText;
	private Composite parent;
	private DateTime lastUpdateDateTime;
	private Label lastUpdateLabel;
	private Label CoinData;
	private PagePainter painter;
	private ValidatorUtils validator;
	private int dimension;
	private int option;
	private Library library;

	public RegisterCoinFragment(Coin coin, ViewController controller, int dimension, int option) {
		this.coin = coin;
		this.controller = controller;
		this.dimension = dimension;
		this.option = option;
	}

	private Date consultDate = Auxiliary.getDateToday();
	@Override
	public Control createUIControl(Composite shell) {
		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		painter = new FormPagePainter();
		validator = new ValidatorUtils(new CustomControlDecoration());
		painter.setDimension(dimension);
		painter.addComposite(shell);
		shell.setData(RWT.CUSTOM_VARIANT, "gray_background");
		parent = new Composite(shell, SWT.NORMAL);
		painter.addComposite(parent);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		registerCoinLabel = new Label(parent, SWT.NONE);
		painter.addHeader(registerCoinLabel);
		Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		painter.addSeparator(separator);

		CoinData = new Label(parent, SWT.NONE);
		painter.addHeader(CoinData);

		coinNameLabel = new Label(parent, SWT.NONE);
		painter.add(coinNameLabel);

		coinNameText = new Text(parent, SWT.NONE);
		painter.add(coinNameText);
		
		validator.applyValidator(coinNameText, "coinNameLettersSpaces", DecoratorType.ALPHA_SPACES, true, 30);
		validator.applyValidator(coinNameText, "coinNameRequired", DecoratorType.REQUIRED_FIELD, true);
		
		coinIdLabel = new Label(parent, SWT.NONE);
		painter.add(coinIdLabel);

		coinIdText = new Combo(parent, SWT.READ_ONLY);
		painter.add(coinIdText);
		

		exchangeRateLabel = new Label(parent, SWT.NONE);
		painter.add(exchangeRateLabel);

		exchangeRateText = new Text(parent, SWT.NONE);
		painter.add(exchangeRateText);
		
		validator.applyValidator(exchangeRateText, "exchangeRateDouble", DecoratorType.DOUBLE, true, 6);
		validator.applyValidator(exchangeRateText, "exchangeRateRequired", DecoratorType.REQUIRED_FIELD, true);
		
		lastUpdateLabel = new Label(parent, SWT.NONE);
		painter.add(lastUpdateLabel);

		lastUpdateDateTime = new DateTime(parent, SWT.BORDER | SWT.DROP_DOWN);
		painter.add(lastUpdateDateTime);
		
		validator.applyRangeDateValidator(lastUpdateDateTime, "lastUpdateDateTime1",
				DecoratorType.DATE_RANGE, -50, 0, 0, 0, 0, 0, true);
		
		initialize(coinIdText,((CoinViewController) controller).getAllManagementLibraryViewController().getLibraryService().findNomenclatorByCode(library.getLibraryID(), Nomenclator.COIN_TYPE));
		validator.applyValidator(coinIdText, "coinIdText", DecoratorType.REQUIRED_FIELD, true);
		LoadCoinData();
		l10n();
		return shell;
	}

	private void LoadCoinData() {
		if (coin != null) {
			coinNameText.setText(coin.getCoinName());
			UiUtils.selectValue(coinIdText, coin.getCoinType());
			exchangeRateText.setText(coin.getExchangeRate());
			java.util.Date utilDate = new java.util.Date(coin.getUpdatedDate().getTime());
			int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(utilDate));
			int month = Integer.parseInt(new SimpleDateFormat("MM").format(utilDate));
			int day = Integer.parseInt(new SimpleDateFormat("dd").format(utilDate));
			lastUpdateDateTime.setDate(year, month - 1, day);
		}
	}

	@Override
	public String getID() {
		return null;
	}

	@Override
	public void l10n() {
		if (option == 1) {
			registerCoinLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_REGISTER_COIN));
		} else {
			registerCoinLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_UPDATE_COIN));
		}
		CoinData.setText(MessageUtil.unescape(AbosMessages.get().LABEL_COIN_DATA));
		coinNameLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_NAME_COIN));
		exchangeRateLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_EXCHANGE_RATE));
		coinIdLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFIER_COIN));
		lastUpdateLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LAST_UPDATE));
		parent.getParent().layout(true, true);
		parent.getParent().redraw();
		parent.getParent().update();

	}

	@Override
	public Control getControl(String arg0) {
		return null;
	}

	public ViewController getController() {
		return controller;
	}

	public void setController(ViewController controller) {
		this.controller = controller;
	}

	public ValidatorUtils getValidator() {
		return validator;
	}

	public void setValidator(ValidatorUtils validator) {
		this.validator = validator;
	}

	public Text getCoinNameText() {
		return coinNameText;
	}

	public void setCoinNameText(Text coinNameText) {
		this.coinNameText = coinNameText;
	}

	public Combo getCoinIdText() {
		return coinIdText;
	}

	public void setCoinIdText(Combo coinIdText) {
		this.coinIdText = coinIdText;
	}

	public Text getExchangeRateText() {
		return exchangeRateText;
	}

	public void setExchangeRateText(Text exchangeRateText) {
		this.exchangeRateText = exchangeRateText;
	}

	public DateTime getLastUpdateDateTime() {
		return lastUpdateDateTime;
	}

	public void setLastUpdateDateTime(DateTime lastUpdateDateTime) {
		this.lastUpdateDateTime = lastUpdateDateTime;
	}

	public Date getConsultDate() {
		return consultDate;
	}

	public void setConsultDate(Date consultDate) {
		this.consultDate = consultDate;
	}
	
	public Composite getParent() {
		return parent;
	}

}
