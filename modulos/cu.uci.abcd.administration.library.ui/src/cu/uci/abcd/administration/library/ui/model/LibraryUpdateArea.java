package cu.uci.abcd.administration.library.ui.model;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import cu.uci.abcd.administration.library.communFragment.RegisterLibraryFragment;
import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.administration.library.ui.controller.LibraryViewController;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.BaseGridViewEntity;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class LibraryUpdateArea extends BaseEditableArea {

	private RegisterLibraryFragment saveLibraryFragment;
	private Composite parentComposite;
	private Library library;
	private ViewController controller;
	private Button saveButton;
	private int dimension;
	private CRUDTreeTable tableLibrary;

	public LibraryUpdateArea(ViewController controller, CRUDTreeTable tableLibrary) {
		super();
		this.controller = controller;
		this.tableLibrary = tableLibrary;
	}

	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity,
			IVisualEntityManager manager) {
		dimension = parent.getParent().getParent().getBounds().width;
		addComposite(parent);
		Library libraryToView = (Library) entity.getRow();
		library = ((LibraryViewController) controller)
				.getLibraryById(libraryToView.getLibraryID());
		saveLibraryFragment = new RegisterLibraryFragment(library, dimension, 2);
		parentComposite = (Composite) saveLibraryFragment
				.createUIControl(parent);
		return parentComposite;
	}

	@Override
	public Composite createButtons(final Composite parent,
			IGridViewEntity entity, final IVisualEntityManager manager) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		saveButton = new Button(parent, SWT.PUSH);
		saveButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (saveLibraryFragment.getValidator().decorationFactory
						.isRequiredControlDecorationIsVisible()) {
					RetroalimentationUtils.showErrorShellMessage(
							//saveLibraryFragment.getParent(), 
							MessageUtil
							.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				} else {
					if (saveLibraryFragment.getValidator().decorationFactory
							.AllControlDecorationsHide()) {
						String libraryName = saveLibraryFragment
								.getNameLibraryText().getText().replaceAll(" +", " ").trim();
						
						Library existLibraryWithThisName = ((LibraryViewController) controller).findLibraryByName(libraryName);
						if( existLibraryWithThisName==null || library.getLibraryID()==existLibraryWithThisName.getLibraryID()  ){
						
							String isisHome = saveLibraryFragment
								.getIsisHomeText().getText();
							Library existLibraryWithThisDefHome = ((LibraryViewController) controller)
									.findLibraryByHome(isisHome);
							if (existLibraryWithThisDefHome == null  || library.getIsisDefHome()==existLibraryWithThisDefHome.getIsisDefHome()  ) {
							
							String libraryAddress = saveLibraryFragment
								.getAddressLibraryText().getText().replaceAll(" +", " ").trim();
						
						
						
						library.setLibraryName(libraryName);
						library.setAddress(libraryAddress);
						library.setIsisDefHome(isisHome);
						library.setEnabled(isisHome.length()>0 );
						
						Library librarySaved = ((LibraryViewController) controller)
								.saveLibrary(library);
						library = null;
						manager.save(new BaseGridViewEntity<Library>(
								librarySaved));
						manager.refresh();
						
						Composite viewSmg = ((LibraryViewArea)tableLibrary.getActiveArea()).getMsg();
						
						RetroalimentationUtils.showInformationMessage(viewSmg, MessageUtil
								.unescape(cu.uci.abos.core.l10n.AbosMessages
										.get().MSG_INF_UPDATE_DATA));
						
						tableLibrary.getPaginator().goToFirstPage();
						}else{
							RetroalimentationUtils.showErrorShellMessage(
									//saveLibraryFragment.getParent(),
									MessageUtil.unescape(AbosMessages.get().ISIS_HOME_USED));
						}
					}else{
						RetroalimentationUtils.showErrorShellMessage(
								//saveLibraryFragment.getParent(), 
								MessageUtil
								.unescape(AbosMessages
										.get().ELEMENT_EXIST));
					}
					} else {
						RetroalimentationUtils.showErrorShellMessage(
								//saveLibraryFragment.getParent(), 
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
	public boolean closable() {
		return true;
	}

	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public void l10n() {
		saveButton
				.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACEPT));
		saveButton.getParent().layout(true, true);
		saveButton.getParent().redraw();
		saveButton.getParent().update();
		saveLibraryFragment.l10n();
	}
	
	@Override
	public String getID() {
		return "updateLibraryID";
	}
}
