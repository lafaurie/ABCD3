package cu.uci.abcd.opac.ui.model;

import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.opac.SelectionList;
import cu.uci.abcd.opac.l10n.AbosMessages;
import cu.uci.abcd.opac.ui.controller.SelectionListViewController;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abcd.opac.ui.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.BaseGridViewEntity;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class SelectionListUpdateArea extends BaseEditableArea {

	private User user;
	private ViewController controller;
	private SelectionList selectionList;
	private CRUDTreeTable myListsTable;

	private Label selectionListNameLb;
	private Label TipoLista;

	private Text selectionListNameTxt;
	private Combo selectionListCategoryCb;

	private Button aceptBtn;

	private ValidatorUtils validator;

	public SelectionListUpdateArea(ViewController controller, CRUDTreeTable myListsTable) {
		this.controller = controller;
		this.myListsTable = myListsTable;
	}

	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity, IVisualEntityManager manager) {

		try {
			user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");
		} catch (Exception e) {
			e.printStackTrace();
		}

		validator = new ValidatorUtils(new CustomControlDecoration());

		selectionList = ((SelectionListViewController) controller).findSelectionList(((SelectionList) entity.getRow()).getId());

		addComposite(parent);

		selectionListNameLb = new Label(parent, SWT.NORMAL);
		add(selectionListNameLb);

		selectionListNameTxt = new Text(parent, SWT.NORMAL);
//		selectionListNameTxt.setTextLimit(20);
		add(selectionListNameTxt);
		selectionListNameTxt.setText(selectionList.getSelectionListName());
		validator.applyValidator(selectionListNameTxt, "selectionListName", DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(selectionListNameTxt, "selectionListName1", DecoratorType.ALPHA_NUMERICS_SPACES, true, 20);

		br();

		TipoLista = new Label(parent, SWT.NORMAL);
		add(TipoLista);

		selectionListCategoryCb = new Combo(parent, SWT.READ_ONLY);
		add(selectionListCategoryCb);
		validator.applyValidator(selectionListCategoryCb, "selectionListCategoryCb", DecoratorType.REQUIRED_FIELD, true);
		initialize(selectionListCategoryCb, ((SelectionListViewController) controller).findAllNomencaltors(Nomenclator.CATEGORY_SELECTION_LIST));
		UiUtils.selectValue(selectionListCategoryCb, selectionList.getCategory());

		l10n();
		return parent;
	}

	@Override
	public Composite createButtons(final Composite parent, IGridViewEntity entity, final IVisualEntityManager manager) {

		aceptBtn = new Button(parent, SWT.PUSH);
		aceptBtn.setText((AbosMessages.get().ACCEPT));

		aceptBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent event) {

				if (validator.decorationFactory.isRequiredControlDecorationIsVisible()) {
					showErrorMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				} else if (validator.decorationFactory.AllControlDecorationsHide()) {
      
					boolean selectionBoolean = ((SelectionListViewController) controller).findAllSelectionListsByName(selectionListNameTxt.getText().replaceAll(" +", " ").trim(), user.getUserID().longValue(), selectionList.getId());
    
					if (selectionBoolean == true) {
						showErrorMessage(MessageUtil.unescape(AbosMessages.get().MSG_ERROR_MODIF_SELECTION_LIST_EXISTS));
					} else {

						try {

							Nomenclator nomenclator = null;

							if (selectionListCategoryCb.getSelectionIndex() == 1)
								nomenclator = ((SelectionListViewController) controller).findNomenclator(Nomenclator.CATEGORY_SELECTION_PRIVATE);
							else
								nomenclator = ((SelectionListViewController) controller).findNomenclator(Nomenclator.CATEGORY_SELECTION_PUBLIC);

							selectionList.setCategory(nomenclator);

							selectionList.setActionDate(new java.sql.Date(new Date().getTime()));
							selectionList.setSelectionListName(selectionListNameTxt.getText().replaceAll(" +", " ").trim());

							((SelectionListViewController) controller).addSelectionList(selectionList);

							BaseGridViewEntity<SelectionList> selectionListGridViewEntity = new BaseGridViewEntity<SelectionList>(selectionList);
							manager.save(selectionListGridViewEntity);
							showInformationMessage(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_UPDATE_DATA);
							myListsTable.destroyEditableArea();
							myListsTable.refresh();

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else {

					showErrorMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_INCORRECT_DATA));
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

		selectionListNameTxt.setText(selectionList.getSelectionListName().replaceAll(" +", " ").trim());
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
		selectionListNameLb.setText(MessageUtil.unescape((AbosMessages.get().LABEL_NAME_LIST)));
		TipoLista.setText(MessageUtil.unescape((AbosMessages.get().LABEL_KIND_LIST)));

	}
}
