package cu.uci.abcd.acquisition.ui.updateArea;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import cu.uci.abcd.adquisition.l10n.AbosMessages;
import cu.uci.abcd.domain.acquisition.Desiderata;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.BaseGridViewEntity;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class EditDesiderataArea extends BaseEditableArea {

	private Composite compoParent;
	private DesiderataFragment desiderataFragment;
	private int dimension;
	private ViewController controller;
	private Button btnAccept;

	public EditDesiderataArea(ViewController controller, CRUDTreeTable tabla) {
		super();
		this.controller = controller;
	}

	@Override
	public Composite createButtons(Composite parent,
			final IGridViewEntity entity, final IVisualEntityManager manager) {

		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");

		btnAccept = new Button(parent, SWT.PUSH);
		btnAccept.setText(AbosMessages.get().BUTTON_ACCEPT);

		btnAccept.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				if (desiderataFragment.validate()) {
					Desiderata desiderata = desiderataFragment
							.llenarDesiderata();
					showInformationMessage(MessageUtil
							.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_UPDATE_DATA));
					manager.save(new BaseGridViewEntity<Desiderata>(desiderata));
					manager.refresh();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		return parent;
	}

	@Override
	public Composite createUI(final Composite parent, IGridViewEntity entity,
			IVisualEntityManager arg2) {

		dimension = parent.getParent().getParent().getBounds().width;
		Desiderata desiderataToView = (Desiderata) entity.getRow();

		compoParent = new Composite(parent, SWT.NORMAL);
		addComposite(compoParent);
		compoParent.setData(RWT.CUSTOM_VARIANT, "gray_background");

		Composite resize = new Composite(compoParent, 0);
		resize.setVisible(false);
		FormDatas.attach(resize).atTop().atRight(0).withWidth(0)
				.withHeight(Display.getCurrent().getBounds().height - 370);

		desiderataFragment = new DesiderataFragment(desiderataToView,
				dimension, controller);
		refresh();
		desiderataFragment
				.setAux(MessageUtil.unescape(AbosMessages.get().EDIT_DESIDERATA));
		desiderataFragment.createUIControl(compoParent);
		desiderataFragment.buttonVisible(false);

		l10n();

		return parent;
	}

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
		desiderataFragment.l10n();
		desiderataFragment.setOkButtonText(MessageUtil.unescape(AbosMessages
				.get().BUTTON_ACCEPT));
	}
}