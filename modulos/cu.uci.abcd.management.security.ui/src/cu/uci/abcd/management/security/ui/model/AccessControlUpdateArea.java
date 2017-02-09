package cu.uci.abcd.management.security.ui.model;

import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abcd.domain.management.security.AccessRecord;
import cu.uci.abcd.management.security.communFragment.RegisterAccessControlFragment;
import cu.uci.abcd.management.security.ui.controller.AccessRecordViewController;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.BaseGridViewEntity;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class AccessControlUpdateArea extends BaseEditableArea {

	private ViewController controller;
	private int dimension;
	private AccessRecord accessRecord;
	private RegisterAccessControlFragment saveAccessControlFragment;
	private Composite parentComposite;
	private Map<String, Control> controlsMaps;
	private CRUDTreeTable accessControlTable;
	
	public AccessControlUpdateArea(ViewController controller, CRUDTreeTable accessControlTable) {
		this.controller = controller;
		this.accessControlTable = accessControlTable;
	}
	
	@Override
	public boolean closable() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Composite createButtons(Composite parent, IGridViewEntity entity,
			final IVisualEntityManager manager) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		Button saveBtn = new Button(parent, SWT.PUSH);
		saveBtn.setText("Aceptar");
		saveBtn.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 2615553092700551346L;

			@Override
			public void widgetSelected(SelectionEvent e) {

				Composite msg = saveAccessControlFragment.getRegister();
				
				if (saveAccessControlFragment.getPerson() != null) {
					if (saveAccessControlFragment.getValidator().decorationFactory
							.isRequiredControlDecorationIsVisible()) {
						RetroalimentationUtils.showErrorMessage(msg, MessageUtil
								.unescape(cu.uci.abos.core.l10n.AbosMessages
										.get().MSG_ERROR_FIELD_REQUIRED));
					} else {
						if (saveAccessControlFragment.getValidator().decorationFactory
								.AllControlDecorationsHide()) {
							accessRecord.setPerson(saveAccessControlFragment.getPerson());
							//int selectedIndex = ((Combo) controlsMaps.get("rommVisited"))
								//	.getSelectionIndex();
							//@SuppressWarnings("unchecked")
							//Room room = ((LinkedList<Room>) ((Combo) controlsMaps.get("rommVisited"))
								//	.getData()).get(selectedIndex);
							Room room = (Room) UiUtils.getSelected((Combo) controlsMaps.get("rommVisited"));
							
							accessRecord.setRoom(room);
							accessRecord.setLibrary(saveAccessControlFragment.getLibrary());
							User authenticatedUser = (User) SecurityUtils
									.getPrincipal().getByKey("user");
							accessRecord
									.setAuthenticatedUser(authenticatedUser);

							//Timestamp sqlDate = new Timestamp(new Date().getTime());
							//accessRecord.setAccessDate(sqlDate);
							accessRecord.setMotivation(((Text) controlsMaps.get("motivo")).getText().replaceAll(" +", " ").trim());

							
							AccessRecord accessrecordeSaved = ((AccessRecordViewController) controller)
									.addAccessRecord(accessRecord);
							accessRecord = null;

							manager.save(new BaseGridViewEntity<AccessRecord>(
									accessrecordeSaved));
							manager.refresh();
							
							Composite viewSmg = ((AccessControlViewArea)accessControlTable.getActiveArea()).getViewAccessControlFragment().getMsg();
							
							RetroalimentationUtils.showInformationMessage(viewSmg, MessageUtil
									.unescape(cu.uci.abos.core.l10n.AbosMessages
											.get().MSG_INF_UPDATE_DATA));

						} else {
							RetroalimentationUtils.showErrorMessage(msg, MessageUtil
									.unescape(cu.uci.abos.core.l10n.AbosMessages
											.get().MSG_ERROR_INCORRECT_DATA));

						}
					}
				} else {
					
					RetroalimentationUtils.showErrorMessage(msg, MessageUtil
							.unescape("Debe seleccionar una persona a acceder"));
				}
			
			}
		});
		return parent;
	}

	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity,
			IVisualEntityManager arg2) {
		dimension = parent.getParent().getParent().getBounds().width;
		addComposite(parent);
		AccessRecord accessRecordToView = (AccessRecord) entity.getRow();
		accessRecord = ((AccessRecordViewController) controller).readAccessRecord(accessRecordToView.getAccessRecordnID());
		Person person = accessRecord.getPerson();
		Library library = (Library) SecurityUtils
				.getPrincipal().getByKey("library");
		saveAccessControlFragment = new RegisterAccessControlFragment(accessRecord, controller, dimension, library, person);
		parentComposite = (Composite) saveAccessControlFragment
				.createUIControl(parent);
		controlsMaps = saveAccessControlFragment.getControls();
		return parentComposite;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void l10n() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getID() {
		return "addAccessControlID";
	}

}
