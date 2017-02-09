package cu.uci.abcd.statistic.ui.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import cu.uci.abcd.domain.common.Statistic;
import cu.uci.abcd.statistic.ui.l10n.AbosMessages;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class TableWatchEditableArea extends BaseEditableArea {

	private Map<String, Control> controls;
	private ViewController controller;
	TabItem tabItemAdminTable;

	public TableWatchEditableArea(ViewController controller) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean closable() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Composite createButtons(Composite arg0, IGridViewEntity arg1, IVisualEntityManager arg2) {

		return null;
	}

	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity, IVisualEntityManager manage) {
		// TODO Auto-generated method stub

		Statistic statistic = (Statistic) entity.getRow();

		FormLayout form = new FormLayout();
		parent.setLayout(form);
		FormDatas.attach(parent).atRight(0).atLeft(0);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");

		TabFolder tabFolder = new TabFolder(parent, SWT.NONE);
		FormDatas.attach(tabFolder).atTopTo(parent, 1).atLeft(0).atRight(0);

		Composite compoLoanUser = new Composite(tabFolder, SWT.None);
		compoLoanUser.setData(RWT.CUSTOM_VARIANT, "gray_background");

		tabItemAdminTable = new TabItem(tabFolder, SWT.None);
		tabItemAdminTable.setControl(compoLoanUser);

		compoLoanUser.setLayout(new FormLayout());
		FormDatas.attach(compoLoanUser).atTopTo(tabFolder, 1).atLeft(0).atRight(0);

		final Composite grupo = new Composite(compoLoanUser, SWT.None);
		grupo.setData(RWT.CUSTOM_VARIANT, "gray_background");
		grupo.setLayout(new FormLayout());
		FormDatas.attach(grupo).atLeft(0).atRight(0).atTopTo(compoLoanUser, 15).atLeft(0);

		int total = parent.getDisplay().getBounds().width;
		double middle1 = (total * 0.375);
		int middle = Integer.valueOf((int) Math.round(middle1));

		String src = RWT.getResourceManager().getLocation("default-photo");

		String lastString = AbosMessages.get().LABEL_DATE_OF_TABLE;
		Group personData = new Group(grupo, SWT.NORMAL);
		personData.setLayout(form);
		FormDatas.attach(personData).atTopTo(grupo, 15).atRight(middle);

		List<String> leftList = new LinkedList<>();
		leftList.add(AbosMessages.get().TABLE_NAME);
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_NAME_DATABASE));
		leftList.add(AbosMessages.get().LABEL_DESCRIPTION);

		return parent;
	}

	public Control getControl(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void l10n() {
		// TODO Auto-generated method stub
		tabItemAdminTable.setText(MessageUtil.unescape(AbosMessages.get().LABEL_DATE_OF_TABLE));
	}

}
