package cu.uci.abcd.administration.nomenclators.model;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import cu.uci.abcd.administration.nomenclators.l10n.AbosMessages;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.widget.compoundgroup.CompoundGroup;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class NomenclatorWatchEditableArea extends BaseEditableArea {

	private Label headerLabel;
	private String dataString;
	private Composite top;
	private Composite topGroup;
	private List<String> leftList1;
	private List<Control> grupControls;
	private Group dataGroup;

	public NomenclatorWatchEditableArea(ViewController controller) {
		super();
		this.controller = controller;
	}

	@Override
	public String getID() {
		return "viewNomenclatorID";
	}

	@Override
	public Composite createUI(final Composite parent, IGridViewEntity entity, IVisualEntityManager manager) {
		Nomenclator fdtEntity = (Nomenclator) entity.getRow();
		top = parent;
		setDimension(parent.getParent().getParent().getBounds().width);
		addComposite(parent);

		topGroup = new Composite(parent, SWT.NORMAL);
		topGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(topGroup);
		
		buildMessage(topGroup);

		headerLabel = new Label(topGroup, SWT.NORMAL);
		addHeader(headerLabel);

		Label separator = new Label(topGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);

		dataGroup = new Group(topGroup, SWT.NORMAL);
		dataGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");
		dataString = AbosMessages.get().LABEL_NOMENCLATOR_DATA;

		add(dataGroup);
		leftList1 = new LinkedList<>();
		leftList1.add(AbosMessages.get().LABEL_NOMENCLATOR_TYPE);
		leftList1.add(AbosMessages.get().LABEL_VALUE);
		leftList1.add(MessageUtil.unescape(AbosMessages.get().LABEL_DESCRIPTION));
		List<String> values = new LinkedList<>();
		values.add(fdtEntity.getNomenclator().getNomenclatorName());
		values.add(fdtEntity.getNomenclatorName());
		values.add(fdtEntity.getNomenclatorDescription());
		grupControls = CompoundGroup.printGroup(dataGroup, dataString, leftList1, values);

		return parent;
	}

	@Override
	public Composite createButtons(Composite parent, final IGridViewEntity entity, final IVisualEntityManager manager) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		return parent;
	}

	public void dispose() {
		if (top != null && !top.isDisposed()) {
			top.dispose();
		}
	}

	public void l10n() {
		headerLabel.setText(AbosMessages.get().NAME_UI_WATCH_NOMENCLATOR);
		dataString = AbosMessages.get().LABEL_NOMENCLATOR_DATA;
		leftList1.clear();
		leftList1.add(AbosMessages.get().LABEL_NOMENCLATOR_TYPE);
		leftList1.add(AbosMessages.get().LABEL_VALUE);
		leftList1.add(MessageUtil.unescape(AbosMessages.get().LABEL_DESCRIPTION));
		dataGroup.setText(dataString);
		CompoundGroup.l10n(grupControls, leftList1);
	}

}
