package cu.uci.abcd.statistic.ui.model;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import cu.uci.abcd.domain.statistic.Indicator;
import cu.uci.abcd.statistic.ui.l10n.AbosMessages;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.widget.compoundgroup.CompoundGroup;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class IndicatorWatchEditableArea extends BaseEditableArea {

	private Label headerLabel;
	private String dataString;
	private Composite top;
	private List<Control> grupControls;
	private Group dataGroup;

	private List<String> leftList1;

	public IndicatorWatchEditableArea(ViewController controller) {
		super();
		this.controller = controller;
	}
	 @Override
	    public String getID() {
	        return "viewIndicatorID";
	   }


	@Override
	public Composite createButtons(Composite parent, final IGridViewEntity entity, final IVisualEntityManager manager) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		return parent;
	}

	@Override
	public Composite createUI(final Composite parent, IGridViewEntity entity, IVisualEntityManager arg2) {
		Indicator indicatorEntity = (Indicator) entity.getRow();
		setDimension(parent.getParent().getParent().getBounds().width);
		top = parent;
		addComposite(parent);

		Composite topGroup = new Composite(parent, SWT.NORMAL);
		topGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(topGroup);
		
		buildMessage(topGroup);

		headerLabel = new Label(topGroup, SWT.NORMAL);
		addHeader(headerLabel);

		Label separator = new Label(topGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);

		dataGroup = new Group(topGroup, SWT.NORMAL);
		dataGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");
		dataString = AbosMessages.get().LABEL_INDICATOR_DATA;
		add(dataGroup);

		leftList1 = new LinkedList<>();
		leftList1.add(AbosMessages.get().TABLE_NAME);
		leftList1.add(MessageUtil.unescape(AbosMessages.get().LABEL_NUM_INDICATOR));
		leftList1.add(AbosMessages.get().LABEL_CONSULT_QUERY);
		List<String> values = new LinkedList<>();
		values.add(indicatorEntity.getNameIndicator());
		values.add(indicatorEntity.getIndicatorId());
		values.add(indicatorEntity.getQueryText());
		grupControls = CompoundGroup.printGroup(dataGroup, dataString, leftList1, values);

		return parent;
	}

	public void dispose() {
		if (top != null && !top.isDisposed()) {
			top.dispose();
		}
	}

	@Override
	public void l10n() {
		headerLabel.setText(AbosMessages.get().LABEL_VIEW_INDICATOR);
		dataString = AbosMessages.get().LABEL_INDICATOR_DATA;
		leftList1.clear();
		leftList1.add(AbosMessages.get().TABLE_NAME);
		leftList1.add(MessageUtil.unescape(AbosMessages.get().LABEL_NUM_INDICATOR));
		leftList1.add(AbosMessages.get().LABEL_CONSULT_QUERY);
		dataGroup.setText(dataString);
		CompoundGroup.l10n(grupControls, leftList1);
	}

}
