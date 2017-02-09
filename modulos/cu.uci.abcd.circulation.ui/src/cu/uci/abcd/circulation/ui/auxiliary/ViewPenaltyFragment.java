package cu.uci.abcd.circulation.ui.auxiliary;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.circulation.l10n.AbosMessages;
import cu.uci.abcd.circulation.ui.ConsultSanction;
import cu.uci.abcd.circulation.ui.RegisterSanction;
import cu.uci.abcd.circulation.ui.controller.AllManagementLoanUserViewController;
import cu.uci.abcd.domain.circulation.CirculationNomenclator;
import cu.uci.abcd.domain.circulation.Penalty;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Worker;
import cu.uci.abcd.domain.util.Auxiliary;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.FormPagePainter;
import cu.uci.abos.core.ui.FragmentPage;
import cu.uci.abos.core.ui.PagePainter;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;
import cu.uci.abos.core.widget.compoundgroup.CompoundGroup;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;

public class ViewPenaltyFragment extends FragmentPage {

	private ViewController controller;
	private Penalty penalty;
	private Composite compoView;
	private Button btnPay;
	private Button btnClose;
	private Button btnNew;
	private PagePainter painter;
	private Composite registerPenalty;

	private String lastStringLoanUser;
	private String lastStringPenalty;
	private int dimension;
	private RegisterSanction registerSanctionClass;
	private ContributorService contributorService;

	private Label lbUpdatePenalty;

	private Label lbPaychecknumber;
	private Text txtPaychecknumber;
	private ValidatorUtils validator;
	private CRUDTreeTable tabla;
	private ConsultSanction consultSanction;

	private List<Control> grupControlsLoanUser = new ArrayList<>();
	private List<Control> grupControlsPenalty = new ArrayList<>();
	private Group loanUserGroup;
	private Group penaltyGroup;
	private List<String> leftListLoanUser = new LinkedList<>();
	private List<String> leftListPenalty = new LinkedList<>();
	private User user;
	private ViewLoanUserFragment viewLoanUserFragment;
	List<String> rigthListPenalty;
	int dias;

	// private Composite msg;
	public ViewPenaltyFragment(ViewController controller) {
		this.setController(controller);
	}

	public ViewPenaltyFragment(ViewController controller, Penalty penalty,
			int dimension, CRUDTreeTable tabla,
			ConsultSanction consultSanction,
			ViewLoanUserFragment viewLoanUserFragment) {
		this.setController(controller);
		this.setPenalty(penalty);
		this.dimension = dimension;
		this.tabla = tabla;
		this.consultSanction = consultSanction;
		this.viewLoanUserFragment = viewLoanUserFragment;
	}

	public ViewPenaltyFragment(ViewController controller, Penalty penalty,
			int dimension, CRUDTreeTable tabla, ConsultSanction consultSanction) {
		this.setController(controller);
		this.setPenalty(penalty);
		this.dimension = dimension;
		this.tabla = tabla;
		this.consultSanction = consultSanction;
	}

	public ViewPenaltyFragment(ViewController controller, Penalty penalty,
			Composite registerPenalty, int dimension,
			RegisterSanction registerSanctionClass,
			ContributorService contributorService) {
		this.setController(controller);
		this.setPenalty(penalty);
		this.registerPenalty = registerPenalty;
		this.dimension = dimension;
		this.registerSanctionClass = registerSanctionClass;
		this.contributorService = contributorService;
	}

	// FIXME METODO COMPLEJO
	@Override
	public Control createUIControl(final Composite parent) {

		user = (User) SecurityUtils.getService().getPrincipal()
				.getByKey("user");

		painter = new FormPagePainter();
		painter.setDimension(dimension);

		validator = new ValidatorUtils(new CustomControlDecoration());

		painter.addComposite(parent);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");

		compoView = new Composite(parent, SWT.NORMAL);
		painter.addComposite(compoView);
		compoView.setData(RWT.CUSTOM_VARIANT, "gray_background");

		lbUpdatePenalty = new Label(compoView, SWT.NONE);
		painter.addHeader(lbUpdatePenalty);

		Label separator = new Label(compoView, SWT.SEPARATOR | SWT.HORIZONTAL);
		painter.addSeparator(separator);

		// Gruop de Usuario de Prestamo
		loanUserGroup = new Group(compoView, SWT.NORMAL);
		painter.add(loanUserGroup);

		leftListLoanUser = new LinkedList<>();
		leftListLoanUser.add(AbosMessages.get().TABLE_NAME_AND_LAST_NAME);
		leftListLoanUser
				.add(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));
		leftListLoanUser.add(AbosMessages.get().LABEL_USER);
		leftListLoanUser
				.add(MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE));
		leftListLoanUser.add(AbosMessages.get().LABEL_TYPE_OF_USER);
		leftListLoanUser.add(AbosMessages.get().LABEL_STATE);
		leftListLoanUser
				.add(MessageUtil.unescape(AbosMessages.get().LABEL_VALID_UNTIL));

		// Gruop de Sanciones
		penaltyGroup = new Group(compoView, SWT.NORMAL);
		painter.add(penaltyGroup);

		leftListPenalty = new LinkedList<>();
		leftListPenalty
				.add(MessageUtil.unescape(AbosMessages.get().LABEL_TYPE_OF_SANCTION));
		leftListPenalty.add(AbosMessages.get().LABEL_STATE);
		leftListPenalty.add(AbosMessages.get().LABEL_FROM);
		leftListPenalty
				.add(MessageUtil.unescape(AbosMessages.get().LABEL_DURATION));
		leftListPenalty.add(AbosMessages.get().LABEL_AMOUNT);
		leftListPenalty.add(AbosMessages.get().LABEL_REASON);
		if (penalty.getPaychecknumber() != null)
			leftListPenalty
					.add(MessageUtil.unescape(AbosMessages.get().LABEL_PAY_CHECK_NUMBER));
		leftListPenalty.add(AbosMessages.get().LABEL_OPERATOR);

		painter.reset();

		painter.add(new Label(compoView, 0), Percent.W100);

		lbPaychecknumber = new Label(compoView, 0);
		lbPaychecknumber.setVisible(false);
		painter.add(lbPaychecknumber);
		FormDatas.attach(lbPaychecknumber).atTopTo(penaltyGroup,
		 25).atLeft(15);

		txtPaychecknumber = new Text(compoView, 0);
		txtPaychecknumber.setVisible(false);
		painter.add(txtPaychecknumber);
		 FormDatas.attach(txtPaychecknumber).atTopTo(penaltyGroup,
		 20).atLeftTo(lbPaychecknumber, 15).withWidth(250).withHeight(12);

		validator.applyValidator(txtPaychecknumber, "txtPaychecknumber",
				DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(txtPaychecknumber, "txtPaychecknumber1",
				DecoratorType.ALPHA_NUMERIC, true, 50);

		painter.reset();
		painter.add(new Label(compoView, 0), Percent.W100);
		// painter.add(new Label(compoView, 0), Percent.W100);

		btnClose = new Button(compoView, SWT.PUSH);
		painter.add(btnClose);
		FormDatas.attach(btnClose).atTopTo(txtPaychecknumber,
				 15).atRight(15);

		btnNew = new Button(compoView, SWT.PUSH);
		painter.add(btnNew);
		FormDatas.attach(btnNew).atTopTo(txtPaychecknumber,
				 15).atRightTo(btnClose, 15);

		btnPay = new Button(compoView, SWT.PUSH);
		painter.add(btnPay);
		FormDatas.attach(btnPay).atTopTo(txtPaychecknumber,
		 15).atRightTo(btnNew, 15);

		painter.reset();
		painter.add(new Label(compoView, 0), Percent.W100);

		if (registerPenalty != null) {
			btnNew.setVisible(true);
			btnClose.setVisible(true);
		} else {
			btnNew.setVisible(false);
			btnClose.setVisible(false);
		}
		btnPay.setVisible(false);

		final Penalty penaltyData = LoadPenaltyData(penalty);
		User userP = ((AllManagementLoanUserViewController) controller)
				.getManageLoanUser().findUserByPersonID(
						penaltyData.getLoanUser().getPersonID());

		long a = (Long) penaltyData.getExpirationDate().getTime();
		long b = (Long) penaltyData.getEffectiveDate().getTime();
		dias = (int) ((a - b) / (1000 * 60 * 60 * 24));

		List<String> rigthListLoanUser = new LinkedList<>();
		rigthListLoanUser.add(penaltyData.getLoanUser().fullName());
		rigthListLoanUser.add(penaltyData.getLoanUser().getDNI());
		if (userP != null) {
			rigthListLoanUser.add(userP.getUsername());
		} else
			rigthListLoanUser.add(" - ");

		rigthListLoanUser.add(penaltyData.getLoanUser().getLoanUserCode());
		rigthListLoanUser.add(penaltyData.getLoanUser().getLoanUserType()
				.getNomenclatorName());
		rigthListLoanUser.add(penaltyData.getLoanUser().getLoanUserState()
				.getNomenclatorName());
		rigthListLoanUser.add(Auxiliary.FormatDate(penaltyData.getLoanUser()
				.getExpirationDate()));
		lastStringLoanUser = MessageUtil
				.unescape(AbosMessages.get().LABEL_USER_LOAN_DATA);

		grupControlsLoanUser = CompoundGroup.printGroup(penaltyData
				.getLoanUser().getPhoto().getImage(), loanUserGroup,
				lastStringLoanUser, leftListLoanUser, rigthListLoanUser);

		rigthListPenalty = new LinkedList<>();
		rigthListPenalty.add(penaltyData.getPenaltyType().getNomenclatorName());
		rigthListPenalty
				.add(penaltyData.getPenaltyState().getNomenclatorName());
		rigthListPenalty.add(new SimpleDateFormat("dd-MM-yyyy")
				.format(penaltyData.getEffectiveDate()));
		rigthListPenalty.add(Integer.toString(dias) + " dias");

		if (penalty.getPenaltyType().getNomenclatorID()
				.equals(CirculationNomenclator.PENALTY_TYPE_FINE)
				&& (penalty
						.getPenaltyState()
						.getNomenclatorID()
						.equals(CirculationNomenclator.PENALTY_STATE_PENDING_PAYMENT) || penalty
						.getPenaltyState()
						.getNomenclatorID()
						.equals(CirculationNomenclator.PENALTY_STATE_PAID))) {

			rigthListPenalty.add(penaltyData.getAmount().toString() + " "
					+ penaltyData.getCoinType().getNomenclatorName());
			

		} else
			rigthListPenalty.add("    -    ");

		if (penalty.getMotivation() == null) {
			rigthListPenalty.add("    -    ");
		} else
			rigthListPenalty.add(penaltyData.getMotivation());

		if (penalty.getPaychecknumber() != null)
			rigthListPenalty.add(penaltyData.getPaychecknumber());

		rigthListPenalty.add(penaltyData.getLibrarian().getPerson()
				.getFullName());

		lastStringPenalty = MessageUtil
				.unescape(AbosMessages.get().LABEL_DATA_SANCTION);
		
		if (dimension <840) {
			grupControlsPenalty = CompoundGroup.printGroup(penaltyGroup,
					lastStringPenalty, leftListPenalty, rigthListPenalty, 300);
		}else
		grupControlsPenalty = CompoundGroup.printGroup(penaltyGroup,
				lastStringPenalty, leftListPenalty, rigthListPenalty);

		if (penalty
				.getPenaltyState()
				.getNomenclatorID()
				.equals(CirculationNomenclator.PENALTY_STATE_PENDING_PAYMENT)) {
			btnPay.setVisible(true);
			lbPaychecknumber.setVisible(true);
			txtPaychecknumber.setVisible(true);
			refresh();
		}
		else
		{
			
			btnPay.setVisible(false);
			lbPaychecknumber.setVisible(false);
			txtPaychecknumber.setVisible(false);
			refresh();
		}
		
		/*
		 * if (viewLoanUserFragment != null) { btnPay.setVisible(false);
		 * lbPaychecknumber.setVisible(false);
		 * txtPaychecknumber.setVisible(false); }
		 */

		if (user.getPerson() == null) {
			btnPay.setVisible(false);
			lbPaychecknumber.setVisible(false);
			txtPaychecknumber.setVisible(false);
		} else {
			Worker workerLoggin = ((AllManagementLoanUserViewController) controller)
					.getManagePerson().findWorkerbyPersonID(
							user.getPerson().getPersonID());
			if (workerLoggin == null) {
				btnPay.setVisible(false);
				lbPaychecknumber.setVisible(false);
				txtPaychecknumber.setVisible(false);
			}
		}
		// **********************

		btnPay.addSelectionListener(new SelectionListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				if (validator.decorationFactory
						.isRequiredControlDecorationIsVisible()) {
					RetroalimentationUtils
							.showErrorShellMessage(cu.uci.abos.core.l10n.AbosMessages
									.get().MSG_ERROR_FIELD_REQUIRED);

				} else if (validator.decorationFactory
						.AllControlDecorationsHide()) {
					Nomenclator statePay = ((AllManagementLoanUserViewController) controller)
							.getManageLoanUser().findByID(
									Nomenclator.PENALTY_STATE_PAID);

					java.util.Date fecha = new java.util.Date();
					Date fechaSQL = new Date(fecha.getTime());

					Penalty penaltyPay = penalty;
					penaltyPay.setPenaltyState(statePay);
					penaltyPay.setLibrarian(user);
					penaltyPay.setCancellationdate(fechaSQL);
					penaltyPay.setPaychecknumber(txtPaychecknumber.getText());

					((AllManagementLoanUserViewController) controller)
							.getManagePenalty().addPenalty(penaltyPay);

					RetroalimentationUtils
							.showInformationShellMessage(AbosMessages.get().MSJE_FINE_PAID);

					btnPay.setVisible(false);
					lbPaychecknumber.setVisible(false);
					txtPaychecknumber.setVisible(false);
					refresh();

					try {

						if (registerPenalty == null
								&& viewLoanUserFragment == null) {
							consultSanction.searchPenalty(0, tabla
									.getPaginator().getPageSize());
							tabla.getPaginator().goToFirstPage();
						} else if (viewLoanUserFragment != null) {
							viewLoanUserFragment.initializeGridPenalty(
									penaltyData.getLoanUser(), 0, 10);
							viewLoanUserFragment
									.initializeGridPenaltyHistorico(
											penaltyData.getLoanUser(), 0, 10);
							viewLoanUserFragment.getTableActualPenalty()
									.getPaginator().goToFirstPage();
							viewLoanUserFragment.getTableHitorialPenalty()
									.getPaginator().goToFirstPage();

						}

						leftListPenalty.clear();
						rigthListPenalty.clear();

						leftListPenalty = new LinkedList<>();
						leftListPenalty.add(MessageUtil.unescape(AbosMessages
								.get().LABEL_TYPE_OF_SANCTION));
						leftListPenalty.add(AbosMessages.get().LABEL_STATE);
						leftListPenalty.add(AbosMessages.get().LABEL_FROM);
						leftListPenalty.add(MessageUtil.unescape(AbosMessages
								.get().LABEL_DURATION));
						leftListPenalty.add(AbosMessages.get().LABEL_AMOUNT);
						leftListPenalty.add(AbosMessages.get().LABEL_REASON);
						leftListPenalty.add(MessageUtil.unescape(AbosMessages
								.get().LABEL_PAY_CHECK_NUMBER));
						leftListPenalty.add(AbosMessages.get().LABEL_OPERATOR);

						rigthListPenalty = new LinkedList<>();
						rigthListPenalty.add(penaltyPay.getPenaltyType()
								.getNomenclatorName());
						rigthListPenalty.add(penaltyPay.getPenaltyState()
								.getNomenclatorName());
						rigthListPenalty.add(new SimpleDateFormat("dd-MM-yyyy")
								.format(penaltyPay.getEffectiveDate()));
						rigthListPenalty.add(Integer.toString(dias) + " dias");
						rigthListPenalty.add(penalty.getAmount().toString()	+ " "+ penalty.getCoinType().getNomenclatorName());
						rigthListPenalty.add(penaltyPay.getMotivation());

						if (penalty.getPaychecknumber() != null)
							rigthListPenalty.add(penaltyPay.getPaychecknumber());

						rigthListPenalty.add(penaltyPay.getLibrarian()
								.getPerson().getFullName());

						lastStringPenalty = MessageUtil.unescape(AbosMessages
								.get().LABEL_DATA_SANCTION);

						cleanComponentParent();
						createUIControl(parent);

						// CompoundGroup.printGroup(penaltyGroup,
						// lastStringPenalty, leftListPenalty,
						// rigthListPenalty);
						// CompoundGroup.l10n(grupControlsPenalty,
						// leftListPenalty);
						refresh();
						refresh(parent);

					} catch (Exception e) {
						e.printStackTrace();
					}

				} else
					RetroalimentationUtils
							.showErrorShellMessage(cu.uci.abos.core.l10n.AbosMessages
									.get().MSG_ERROR_INCORRECT_DATA);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

		btnNew.addSelectionListener(new SelectionAdapter() {

			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {

				registerSanctionClass.notifyListeners(SWT.Dispose, new Event());
				contributorService.selectContributor("addPenaltyID");
			}
		});

		btnClose.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 2615553092700551346L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				registerSanctionClass.notifyListeners(SWT.Dispose, new Event());
			}
		});

		l10n();
		return parent;
	}

	public void cleanComponentParent() {
		try {
			Control[] temp = compoView.getChildren();
			for (int i = 0; i < temp.length; i++)
				temp[i].dispose();

		} catch (Exception e) {
		}
	}

	public void refresh() {
		compoView.computeSize(compoView.getBounds().width, SWT.DEFAULT);
		compoView.pack();
		compoView.layout(true, true);
		compoView.update();
		compoView.redraw();
	}

	@SuppressWarnings("unused")
	public Penalty LoadPenaltyData(Penalty penaltyLoaded) {
		if (!(penaltyLoaded == null)) {
			Penalty penalty;
			if (!(penaltyLoaded.getPenaltyID() == null)) {
				Long idPenalty = penaltyLoaded.getPenaltyID();
				penalty = ((AllManagementLoanUserViewController) controller)
						.getManagePenalty().findOnePenalty(idPenalty);

			} else {
				penalty = penaltyLoaded;
			}

		}
		return penalty;
	}

	@Override
	public String getID() {
		return null;
	}

	@Override
	public void l10n() {

		lbPaychecknumber
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_PAY_CHECK_NUMBER));
		lbUpdatePenalty
				.setText(MessageUtil.unescape(AbosMessages.get().VIEW_PENALTY));
		btnPay.setText(AbosMessages.get().BUTTON_PAY);
		btnClose.setText(AbosMessages.get().BUTTON_CLOSE);
		btnNew.setText(AbosMessages.get().BUTTON_NEW);

		leftListLoanUser.clear();
		leftListLoanUser.add(AbosMessages.get().TABLE_NAME_AND_LAST_NAME);
		leftListLoanUser
				.add(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));
		leftListLoanUser.add(AbosMessages.get().LABEL_USER);
		leftListLoanUser
				.add(MessageUtil.unescape(AbosMessages.get().LABEL_USER_CODE));
		leftListLoanUser.add(AbosMessages.get().LABEL_TYPE_OF_USER);
		leftListLoanUser.add(AbosMessages.get().LABEL_STATE);
		leftListLoanUser
				.add(MessageUtil.unescape(AbosMessages.get().LABEL_VALID_UNTIL));
		CompoundGroup.l10n(grupControlsLoanUser, leftListLoanUser);

		leftListPenalty.clear();
		leftListPenalty
				.add(MessageUtil.unescape(AbosMessages.get().LABEL_TYPE_OF_SANCTION));
		leftListPenalty.add(AbosMessages.get().LABEL_STATE);
		leftListPenalty.add(AbosMessages.get().LABEL_FROM);
		leftListPenalty
				.add(MessageUtil.unescape(AbosMessages.get().LABEL_DURATION));
		leftListPenalty.add(AbosMessages.get().LABEL_AMOUNT);
		leftListPenalty.add(AbosMessages.get().LABEL_REASON);

		if (penalty.getPaychecknumber() != null)
			leftListPenalty
					.add(MessageUtil.unescape(AbosMessages.get().LABEL_PAY_CHECK_NUMBER));

		leftListPenalty.add(AbosMessages.get().LABEL_OPERATOR);
		CompoundGroup.l10n(grupControlsPenalty, leftListPenalty);

		lastStringLoanUser = MessageUtil
				.unescape(AbosMessages.get().LABEL_USER_LOAN_DATA);
		loanUserGroup.setText(lastStringLoanUser);

		lastStringPenalty = MessageUtil
				.unescape(AbosMessages.get().LABEL_DATA_SANCTION);
		penaltyGroup.setText(lastStringPenalty);

		refresh();
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

	public Penalty getPenalty() {
		return penalty;
	}

	public void setPenalty(Penalty penalty) {
		this.penalty = penalty;
	}

	public Composite getRegisterPenalty() {
		return registerPenalty;
	}

	public void setRegisterPenalty(Composite registerPenalty) {
		this.registerPenalty = registerPenalty;
	}
}
