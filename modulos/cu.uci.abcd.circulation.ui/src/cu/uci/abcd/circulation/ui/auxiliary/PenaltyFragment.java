package cu.uci.abcd.circulation.ui.auxiliary;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.circulation.l10n.AbosMessages;
import cu.uci.abcd.circulation.ui.RegisterSanction;
import cu.uci.abcd.circulation.ui.controller.AllManagementLoanUserViewController;
import cu.uci.abcd.circulation.ui.listener.EventRegisterSanctions;
import cu.uci.abcd.domain.circulation.Penalty;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.FragmentContributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;

public class PenaltyFragment extends ContributorPage implements FragmentContributor {

	private ViewController controller;
	private Map<String, Control> controls = new HashMap<String, Control>();
	private Penalty penalty;
	private Composite registerPenalty;
	private Label lbCoinType;
	private Label lbSanctionsData;
	private Label lbSanctionsType;
	private Button rdbSuspencion;
	private Button rdbFine;
	private Label lbFrom;
	private Label lbUp;
	private Label lbAmount;
	private Label lbReason;
	private Text txt_monto;
	private Combo comboCoin;
	private DateTime dateTime;
	private DateTime dateTime1;
	private Label separador;
	private Composite compoEnd;
	private Text txtReason;
	private Composite composite;
	private int dimension;
	private Library library;
	private ValidatorUtils validator;
	private RegisterSanction registerSanction;

	public PenaltyFragment(ViewController controller, Penalty penalty, int dimension) {
		this.controller = controller;
		this.penalty = penalty;
		this.dimension = dimension;
	}

	public PenaltyFragment(ViewController controller, Penalty penalty, int dimension, RegisterSanction registerSanction) {
		this.controller = controller;
		this.penalty = penalty;
		this.dimension = dimension;
		this.registerSanction = registerSanction;
	}

	// FIXME METODO COMPLEJO
	@Override
	public Control createUIControl(final Composite parent) {
		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
    
		setDimension(dimension);
		addComposite(parent);

		validator = new ValidatorUtils(new CustomControlDecoration());

		registerPenalty = new Composite(parent, SWT.NORMAL);
		addComposite(registerPenalty);
		controls.put("registerPenalty", registerPenalty);
		registerPenalty.setData(RWT.CUSTOM_VARIANT, "gray_background");				
		
		separador = new Label(registerPenalty, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separador);

		lbSanctionsData = new Label(registerPenalty, SWT.WRAP);
		addHeader(lbSanctionsData);

		lbSanctionsType = new Label(registerPenalty, SWT.WRAP);
		add(lbSanctionsType);

		rdbSuspencion = new Button(registerPenalty, SWT.RADIO);
		controls.put("rdbSuspencion", rdbSuspencion);
		add(rdbSuspencion);
		FormDatas.attach(rdbSuspencion).atLeftTo(lbSanctionsType, 10).atTop(70);
		
		rdbFine = new Button(registerPenalty, SWT.RADIO);
		controls.put("rdbFine", rdbFine);
		add(rdbFine);
		FormDatas.attach(rdbFine).atLeftTo(rdbSuspencion, 10).atTop(70);

		br();

		rdbSuspencion.setSelection(true);

		lbFrom = new Label(registerPenalty, SWT.WRAP);
		add(lbFrom);
		dateTime = new DateTime(registerPenalty, SWT.BORDER | SWT.DROP_DOWN);
		controls.put("dateTime", dateTime);
		add(dateTime);
		dateTime.setEnabled(false);
		//validator.applyRangeDateValidator(dateTime, "dateTime",
		//		DecoratorType.DATE_RANGE, 0, 0, -1, 0, 0, 0, true);
		
		lbUp = new Label(registerPenalty, SWT.WRAP);
		add(lbUp);
		dateTime1 = new DateTime(registerPenalty, SWT.BORDER | SWT.DROP_DOWN);
		controls.put("dateTime1", dateTime1);
		add(dateTime1);
		validator.applyRangeDateValidator(dateTime1, "dateTime1",
				DecoratorType.DATE_RANGE, 0, 0, 0, 50, 0, 0, true);
		// ------- Aparece si es seleccionado multa---------
		composite = new Composite(parent, SWT.NONE);
		composite.setVisible(false);
		addComposite(composite);
		composite.setData(RWT.CUSTOM_VARIANT, "gray_background");

		lbAmount = new Label(composite, SWT.WRAP);
		add(lbAmount);
		txt_monto = new Text(composite, SWT.NONE);
		controls.put("txt_monto", txt_monto);
		add(txt_monto);
		validator.applyValidator(txt_monto, "txt_monto", DecoratorType.DOUBLE, true,6);
		validator.applyValidator(txt_monto, "txt_monto1", DecoratorType.REQUIRED_FIELD, true);

		lbCoinType = new Label(composite, SWT.WRAP);
		add(lbCoinType);
		comboCoin = new Combo(composite, SWT.READ_ONLY);
		controls.put("comboCoin", comboCoin);
		add(comboCoin);
		initialize(comboCoin, ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByNomenclator(library.getLibraryID(), Nomenclator.COIN_TYPE));
		validator.applyValidator(comboCoin, "comboCoin", DecoratorType.REQUIRED_FIELD, true);

		rdbFine.addListener(SWT.Selection, new EventRegisterSanctions(composite, registerPenalty, registerSanction, rdbFine, this));

		// ----------------------------------------------------------------------------
		compoEnd = new Composite(parent, SWT.NORMAL);
		addComposite(compoEnd);
		compoEnd.setData(RWT.CUSTOM_VARIANT, "gray_background");

		lbReason = new Label(compoEnd, SWT.WRAP);
		add(lbReason);

		txtReason = new Text(compoEnd, SWT.V_SCROLL | SWT.WRAP);
		controls.put("txtReason", txtReason);
		add(txtReason);		
		
		validator.applyValidator(txtReason, "txtReason", DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(txtReason, 500);

		br();

		
		LoadPenaltyData();
		l10n();
		
		return parent;
	}

	@Override
	public String getID() {
		return null;
	}

	@Override
	public void l10n() {
		lbSanctionsData.setText(MessageUtil.unescape(AbosMessages.get().LABEL_DATA_SANCTION));
		lbSanctionsType.setText(MessageUtil.unescape(AbosMessages.get().LABEL_TYPE_OF_SANCTION));

		lbFrom.setText(AbosMessages.get().LABEL_FROM);
		lbUp.setText(AbosMessages.get().LABEL_UP);
		lbAmount.setText(AbosMessages.get().LABEL_AMOUNT);
		lbReason.setText(AbosMessages.get().LABEL_REASON);

		rdbSuspencion.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SUSPENCION));
		rdbFine.setText(AbosMessages.get().LABEL_FINE);
		lbCoinType.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_COIN_TYPE));

		if (penalty != null && penalty.getPenaltyType().getNomenclatorID().equals(Nomenclator.PENALTY_TYPE_FINE)) {
			UiUtils.selectValue(comboCoin, penalty.getCoinType());
		}

		if (registerSanction != null)
			registerSanction.refresh();

	}

	@Override
	public Control getControl(String arg0) {
		return null;
	}

	private void LoadPenaltyData() {
		if (penalty != null) {

			int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(penalty.getEffectiveDate()));
			int month = Integer.parseInt(new SimpleDateFormat("MM").format(penalty.getEffectiveDate()));
			int day = Integer.parseInt(new SimpleDateFormat("dd").format(penalty.getEffectiveDate()));
			dateTime.setDate(year, month - 1, day);

			int year1 = Integer.parseInt(new SimpleDateFormat("yyyy").format(penalty.getExpirationDate()));
			int month1 = Integer.parseInt(new SimpleDateFormat("MM").format(penalty.getExpirationDate()));
			int day1 = Integer.parseInt(new SimpleDateFormat("dd").format(penalty.getExpirationDate()));
			dateTime1.setDate(year1, month1 - 1, day1);

			if (penalty.getPenaltyType().getNomenclatorID().equals(Nomenclator.PENALTY_TYPE_SUSPENCION)) {
				
				rdbSuspencion.setSelection(true);
				rdbFine.setSelection(false);
				
				composite.setVisible(false);
			} else {
				rdbSuspencion.setSelection(false);
				rdbFine.setSelection(true);
				composite.setVisible(true);

				insertComposite(composite, registerPenalty);

				composite.getShell().layout(true, true);
				composite.getShell().redraw();
				composite.getShell().update();				

				String[] nums = penalty.getAmount().toString().split("\\.");
				if(nums[1].equals("0")){
					txt_monto.setText(nums[0]);
	            }else{
	            	txt_monto.setText(penalty.getAmount().toString());
	            }				
			
				initialize(comboCoin, ((AllManagementLoanUserViewController) controller).getManageLoanUser().findByNomenclator(library.getLibraryID(), Nomenclator.COIN_TYPE));
				//UiUtils.selectValue(comboCoin, penalty.getCoinType());
			}

			if (penalty.getMotivation() == null) {
				txtReason.setText("");
			} else
				txtReason.setText(penalty.getMotivation());
			
			if (registerSanction != null)
				registerSanction.refresh();
		}
	}

	public void cleanField(){
		
		rdbSuspencion.setSelection(true);
		
		txtReason.setText("");
		composite.setVisible(false);

		insertComposite(composite, registerPenalty);

		composite.getShell().layout(true, true);
		composite.getShell().redraw();
		composite.getShell().update();				

	}
	public Map<String, Control> getControls() {
		return controls;
	}

	public void setControls(Map<String, Control> controls) {
		this.controls = controls;
	}

	@Override
	public String contributorName() {
		return null;
	}

	public ValidatorUtils getValidator() {
		return validator;
	}

	public void setValidator(ValidatorUtils validator) {
		this.validator = validator;
	}
}
