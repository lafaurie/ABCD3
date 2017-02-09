package cu.uci.abcd.administration.library.ui.model;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.domain.management.library.CirculationRule;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.widget.compoundgroup.CompoundGroup;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class CirculationRuleViewArea extends BaseEditableArea {
	private Label header;
	private Group dataGroup;
	private String titleGroup;
	private List<String> leftList;
	private List<Control> grupControls;
	
	private Composite msg;
	public Composite getMsg() {
		return msg;
	}

	@Override
	public Composite createButtons(Composite parent, IGridViewEntity arg1, IVisualEntityManager arg2) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		return parent;
	}

	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity, IVisualEntityManager manager) {
		CirculationRule circulationRule = (CirculationRule) entity.getRow();
		setDimension(parent.getParent().getParent().getBounds().width);
		addComposite(parent);
		Composite topGroup = new Composite(parent, SWT.NORMAL);
		topGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(topGroup);
		
		msg  = new Composite(topGroup, SWT.NONE);
		msg.setLayout(new FormLayout());
		msg.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(msg).atTopTo(topGroup).withWidth(320).withHeight(50).atRight(0);
		
		
		header = new Label(topGroup, SWT.NORMAL);
		addHeader(header);
		Label separator = new Label(topGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);
		dataGroup = new Group(topGroup, SWT.NORMAL);
		dataGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");
		titleGroup = MessageUtil.unescape(AbosMessages.get().LABEL_CIRCULATION_RULE_DATA);
		add(dataGroup, Percent.W100);
		leftList = new LinkedList<>();
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_MATERIAL_TYPE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_TYPE_OF_USER));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_STATE_OF_THE_RULE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_NORMAL_TIME_LAPSE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_TIME_LAPSE_IN_QUEUE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_LOANS_ALLOWED));
		leftList.add(MessageUtil.unescape(AbosMessages.get().PERMITTED_RENEWALS));
		leftList.add(MessageUtil.unescape(AbosMessages.get().UNIT));
		leftList.add(MessageUtil.unescape(AbosMessages.get().RESERVATIONS_ALLOW_EVEN_IF_USER_IS_LATE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LOAN_SEVERAL_COPIES_OF_THE_SAME_RECORD));
		leftList.add(MessageUtil.unescape(AbosMessages.get().ALLOW_RESERVATIONS));
		leftList.add(MessageUtil.unescape(AbosMessages.get().DAYS_TO_REMOVE_CLASSIFIED_MATERIAL));
		leftList.add(MessageUtil.unescape(AbosMessages.get().PENALTY_FOR_DELAY_DAY));
		leftList.add(MessageUtil.unescape(AbosMessages.get().DAY_PENALTY_FOR_DELAY_IF_RESERVED));
		leftList.add(MessageUtil.unescape(AbosMessages.get().DAY_SUSPENSION_FOR_DAYS_LATE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().DAY_SUSPENSION_FOR_DAYS_PAST_DUE_IF_RESERVED));
		leftList.add(MessageUtil.unescape(AbosMessages.get().PENALTY_FOR_LOSS));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LOST_SUSPENSION_DAY));

		List<String> values = new LinkedList<>();
		values.add(circulationRule.getRecordType().getNomenclatorName());
		values.add(circulationRule.getLoanUserType().getNomenclatorName());
		values.add(circulationRule.getCirculationRuleState().getNomenclatorName());
		values.add((circulationRule.getQuantityDayOnLoan() != null) ? circulationRule.getQuantityDayOnLoan().toString() : "-");
		values.add((circulationRule.getQuantityDayOnLoanIfQueue() != null) ? circulationRule.getQuantityDayOnLoanIfQueue().toString() : "-");
		values.add((circulationRule.getQuantityOfLoanAllowed() != null) ? circulationRule.getQuantityOfLoanAllowed().toString() : "-");
		values.add((circulationRule.getQuantityOfRenewedAllowed() != null) ? circulationRule.getQuantityOfRenewedAllowed().toString() : "-");
		values.add((circulationRule.getQuantityOfRenewedDayAllowed() != null) ? circulationRule.getQuantityOfRenewedDayAllowed().toString() : "-");
		values.add((circulationRule.isReservationDelayAllowed() == true) ? MessageUtil.unescape(AbosMessages.get().YES) : MessageUtil.unescape(AbosMessages.get().NO));
		values.add((circulationRule.isSeveralMaterialsAllowed() == true) ? MessageUtil.unescape(AbosMessages.get().YES) : MessageUtil.unescape(AbosMessages.get().NO));
		values.add((circulationRule.isReservationAllowed() == true) ? MessageUtil.unescape(AbosMessages.get().YES) : MessageUtil.unescape(AbosMessages.get().NO));
		values.add((circulationRule.getQuantityOfDayToPickUpTheMaterial() != null) ? circulationRule.getQuantityOfDayToPickUpTheMaterial().toString() : "-");
		values.add((circulationRule.getAmountPenalty() != null) ? circulationRule.getAmountPenalty().toString() : "-");
		values.add((circulationRule.getAmountPenaltyByReserved() != null) ? circulationRule.getAmountPenaltyByReserved().toString() : "-");
		values.add((circulationRule.getQuantitySuspentionDayByDelay() != null) ? circulationRule.getQuantitySuspentionDayByDelay().toString() : "-");
		values.add((circulationRule.getQuantitySuspentionDayByDelayOfReservation() != null) ? circulationRule.getQuantitySuspentionDayByDelayOfReservation().toString() : "-");
		values.add((circulationRule.getAmountPenaltyByLost() != null) ? circulationRule.getAmountPenaltyByLost().toString() : "-");
		values.add((circulationRule.getLostSuspensionDay() != null) ? circulationRule.getLostSuspensionDay().toString() : "-");

		grupControls = CompoundGroup.printGroup(dataGroup, titleGroup, leftList, values, 450);
		return parent;

		/*
		 * leftList = new ArrayList<>(); loadData(); List<String> rightList =
		 * new ArrayList<>();
		 * rightList.add(circulationRule.getRecordType().getNomenclatorName());
		 * rightList
		 * .add(circulationRule.getLoanUserType().getNomenclatorName());
		 * rightList
		 * .add(circulationRule.getCirculationRuleState().getNomenclatorName());
		 * rightList
		 * .add((circulationRule.getQuantityDayOnLoan()!=null)?circulationRule
		 * .getQuantityDayOnLoan().toString():"-");
		 * rightList.add((circulationRule
		 * .getQuantityDayOnLoanIfQueue()!=null)?circulationRule
		 * .getQuantityDayOnLoanIfQueue().toString():"-");
		 * rightList.add((circulationRule
		 * .getQuantityOfLoanAllowed()!=null)?circulationRule
		 * .getQuantityOfLoanAllowed().toString():"-");
		 * rightList.add((circulationRule
		 * .getQuantityOfRenewedAllowed()!=null)?circulationRule
		 * .getQuantityOfRenewedAllowed().toString():"-");
		 * rightList.add((circulationRule
		 * .getQuantityOfRenewedDayAllowed()!=null)
		 * ?circulationRule.getQuantityOfLoanAllowed().toString():"-");
		 * rightList
		 * .add((circulationRule.isReservationDelayAllowed()==true)?"Si":"No");
		 * rightList
		 * .add((circulationRule.isSeveralMaterialsAllowed()==true)?"Si":"No");
		 * rightList
		 * .add((circulationRule.isReservationAllowed()==true)?"Si":"No");
		 * rightList
		 * .add((circulationRule.getQuantityOfDayToPickUpTheMaterial()!=
		 * null)?circulationRule
		 * .getQuantityOfDayToPickUpTheMaterial().toString():"-");
		 * rightList.add(
		 * (circulationRule.getAmountPenalty()!=null)?circulationRule
		 * .getAmountPenalty().toString():"-");
		 * rightList.add((circulationRule.getAmountPenaltyByReserved
		 * ()!=null)?circulationRule
		 * .getAmountPenaltyByReserved().toString():"-");
		 * rightList.add((circulationRule
		 * .getQuantitySuspentionDayByDelay()!=null
		 * )?circulationRule.getQuantitySuspentionDayByDelay().toString():"-");
		 * rightList
		 * .add((circulationRule.getQuantitySuspentionDayByDelayOfReservation
		 * ()!=
		 * null)?circulationRule.getQuantitySuspentionDayByDelayOfReservation
		 * ().toString():"-");
		 * rightList.add((circulationRule.getAmountPenaltyByLost
		 * ()!=null)?circulationRule.getAmountPenaltyByLost().toString():"-");
		 * 
		 * viewGenericFragment = new ViewGenericFragment(viewGenericName,
		 * viewGenericGroupName, leftList, rightList); parentComposite =
		 * (Composite) viewGenericFragment.createUIControl(shell); return
		 * parentComposite;
		 */
	}

	/*
	 * public void loadData(){ viewGenericName =
	 * MessageUtil.unescape(AbosMessages.get().VIEW_CIRCULATION_RULE);
	 * viewGenericGroupName =
	 * MessageUtil.unescape(AbosMessages.get().LABEL_CIRCULATION_RULE_DATA);
	 * leftList
	 * .add(MessageUtil.unescape(AbosMessages.get().LABEL_MATERIAL_TYPE)+": ");
	 * leftList
	 * .add(MessageUtil.unescape(AbosMessages.get().LABEL_TYPE_OF_USER)+": ");
	 * leftList
	 * .add(MessageUtil.unescape(AbosMessages.get().LABEL_STATE_OF_THE_RULE
	 * )+": ");
	 * leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_NORMAL_TIME_LAPSE
	 * )+": "); leftList.add(MessageUtil.unescape(AbosMessages.get().
	 * LABEL_TIME_LAPSE_IN_QUEUE)+": ");
	 * leftList.add(MessageUtil.unescape(AbosMessages
	 * .get().LABEL_LOANS_ALLOWED)+": ");
	 * leftList.add(MessageUtil.unescape(AbosMessages
	 * .get().PERMITTED_RENEWALS)+": ");
	 * leftList.add(MessageUtil.unescape(AbosMessages.get().UNIT)+": ");
	 * leftList.add(MessageUtil.unescape(AbosMessages.get().
	 * RESERVATIONS_ALLOW_EVEN_IF_USER_IS_LATE)+": ");
	 * leftList.add(MessageUtil.unescape
	 * (AbosMessages.get().LOAN_SEVERAL_COPIES_OF_THE_SAME_RECORD)+": ");
	 * leftList
	 * .add(MessageUtil.unescape(AbosMessages.get().ALLOW_RESERVATIONS)+": ");
	 * leftList.add(MessageUtil.unescape(AbosMessages.get().
	 * DAYS_TO_REMOVE_CLASSIFIED_MATERIAL)+": ");
	 * leftList.add(MessageUtil.unescape
	 * (AbosMessages.get().PENALTY_FOR_DELAY_DAY)+": ");
	 * leftList.add(MessageUtil
	 * .unescape(AbosMessages.get().DAY_PENALTY_FOR_DELAY_IF_RESERVED)+": ");
	 * leftList
	 * .add(MessageUtil.unescape(AbosMessages.get().DAY_SUSPENSION_FOR_DAYS_LATE
	 * )+": "); leftList.add(MessageUtil.unescape(AbosMessages.get().
	 * DAY_SUSPENSION_FOR_DAYS_PAST_DUE_IF_RESERVED)+": ");
	 * leftList.add(MessageUtil
	 * .unescape(AbosMessages.get().PENALTY_FOR_LOSS)+": "); }
	 */
	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public boolean closable() {
		return true;
	}

	@Override
	public void l10n() {
		header.setText(MessageUtil.unescape(AbosMessages.get().VIEW_CIRCULATION_RULE));
		titleGroup = MessageUtil.unescape(AbosMessages.get().LABEL_CIRCULATION_RULE_DATA);
		leftList.clear();
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_MATERIAL_TYPE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_TYPE_OF_USER));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_STATE_OF_THE_RULE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_NORMAL_TIME_LAPSE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_TIME_LAPSE_IN_QUEUE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_LOANS_ALLOWED));
		leftList.add(MessageUtil.unescape(AbosMessages.get().PERMITTED_RENEWALS));
		leftList.add(MessageUtil.unescape(AbosMessages.get().UNIT));
		leftList.add(MessageUtil.unescape(AbosMessages.get().RESERVATIONS_ALLOW_EVEN_IF_USER_IS_LATE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LOAN_SEVERAL_COPIES_OF_THE_SAME_RECORD));
		leftList.add(MessageUtil.unescape(AbosMessages.get().ALLOW_RESERVATIONS));
		leftList.add(MessageUtil.unescape(AbosMessages.get().DAYS_TO_REMOVE_CLASSIFIED_MATERIAL));
		leftList.add(MessageUtil.unescape(AbosMessages.get().PENALTY_FOR_DELAY_DAY));
		leftList.add(MessageUtil.unescape(AbosMessages.get().DAY_PENALTY_FOR_DELAY_IF_RESERVED));
		leftList.add(MessageUtil.unescape(AbosMessages.get().DAY_SUSPENSION_FOR_DAYS_LATE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().DAY_SUSPENSION_FOR_DAYS_PAST_DUE_IF_RESERVED));
		leftList.add(MessageUtil.unescape(AbosMessages.get().PENALTY_FOR_LOSS));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LOST_SUSPENSION_DAY));

		dataGroup.setText(titleGroup);
		CompoundGroup.l10n(grupControls, leftList);
	}
	
	@Override
	public String getID() {
		return "viewCirculationRuleID";
	}

}
