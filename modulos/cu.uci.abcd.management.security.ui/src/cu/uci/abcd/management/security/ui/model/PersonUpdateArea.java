package cu.uci.abcd.management.security.ui.model;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.common.Picture;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.management.security.communFragment.RegisterPersonFragment;
import cu.uci.abcd.management.security.l10n.AbosMessages;
import cu.uci.abcd.management.security.ui.controller.PersonViewController;
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

public class PersonUpdateArea extends BaseEditableArea {

	private ViewController controller;
	private RegisterPersonFragment updatePerson;
	private Person person;
	private Composite parentComposite;
	private Map<String, Control> controlsMaps;
	private CRUDTreeTable personTable;

	public PersonUpdateArea(ViewController controller, CRUDTreeTable personTable) {
		this.controller = controller;
		this.setPersonTable(personTable);
	}

	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity, IVisualEntityManager manager) {
		person = (Person) entity.getRow();
		int dimension = parent.getParent().getParent().getParent().getBounds().width;
		updatePerson = new RegisterPersonFragment(person, controller, dimension);
		parentComposite = (Composite) updatePerson.createUIControl(parent);
		controlsMaps = updatePerson.getControls();
		//loadDataPerson();
		return parentComposite;
	}

	@Override
	public Composite createButtons(Composite parent, IGridViewEntity entity, final IVisualEntityManager manager) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		Button saveBtn = new Button(parent, SWT.PUSH);
		saveBtn.setText("Aceptar");
		saveBtn.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 2615553092700551346L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(updatePerson.getValidator().decorationFactory.getControlByKey("dtBD").isVisible()){
					((DateTime) controlsMaps.get("dtBD")).setBackground(new Color(((DateTime) controlsMaps.get("dtBD")).getDisplay(), 255, 204, 153));
				}
				
				if (updatePerson.getValidator().decorationFactory
						.isRequiredControlDecorationIsVisible()) {
					RetroalimentationUtils.showErrorMessage(updatePerson.getMsg(), MessageUtil
							.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				}else{
					if (updatePerson.getValidator().decorationFactory
							.AllControlDecorationsHide()) {
						Library library = (Library) SecurityUtils.getPrincipal().getByKey("library");
						String identification = ((Text) controlsMaps.get("txtCI")).getText().replaceAll(" +", " ").trim();
						//Text tata = (Text) controlsMaps.get("txtFirstName");
						
						Person existPerson = ((PersonViewController) controller).findPersonByIdentification(identification, library.getLibraryID());
						if( existPerson==null || person.getPersonID()==existPerson.getPersonID() ){
							person.setFirstName((((Text) controlsMaps
									.get("txtFirstName")).getText() != null ? ((Text) controlsMaps
									.get("txtFirstName")).getText().replaceAll(" +", " ").trim()
									.substring(0, 1).toUpperCase()
									+ ((Text) controlsMaps.get("txtFirstName"))
											.getText().replaceAll(" +", " ").trim().substring(1) : null));
							
							//person.setSecondName((((Text) controlsMaps
							//		.get("")).getText() != null ? ((Text) controlsMaps
							//		.get("txtSecondName")).getText() : " "));
							
							
							
							person.setSecondName((((Text) controlsMaps
									.get("txtSecondName")).getText().length()>0 ? ((Text) controlsMaps
									.get("txtSecondName")).getText().replaceAll(" +", " ").trim()
									.substring(0, 1).toUpperCase()
									+ ((Text) controlsMaps.get("txtSecondName"))
											.getText().replaceAll(" +", " ").trim().substring(1) : null));
											
							
							
							//person.setFirStsurname((((Text) controlsMaps
								//	.get("txtFirstLastName")).getText() != null ? ((Text) controlsMaps
								//	.get("txtFirstLastName")).getText()
								//	.replaceAll(" +", " ").trim() : " "));
							
							person.setFirStsurname((((Text) controlsMaps
									.get("txtFirstLastName")).getText() != null ? ((Text) controlsMaps
									.get("txtFirstLastName")).getText().replaceAll(" +", " ").trim()
									.substring(0, 1).toUpperCase()
									+ ((Text) controlsMaps.get("txtFirstLastName"))
											.getText().replaceAll(" +", " ").trim().substring(1) : null));
							
							
							//person.setSecondSurname((((Text) controlsMaps
							//		.get("txtSecondLastName")).getText() != null ? ((Text) controlsMaps
							//		.get("txtSecondLastName")).getText()
							//		.replaceAll(" +", " ").trim() : " "));
							
							person.setSecondSurname((((Text) controlsMaps
									.get("txtSecondLastName")).getText().length()>0 ? ((Text) controlsMaps
									.get("txtSecondLastName")).getText().replaceAll(" +", " ").trim()
									.substring(0, 1).toUpperCase()
									+ ((Text) controlsMaps.get("txtSecondLastName"))
											.getText().replaceAll(" +", " ").trim().substring(1) : null));
						
						person.setDNI(identification);
                        /*
						int selectedIndex = ((Combo) controlsMaps.get("cmbGender")).getSelectionIndex();
						@SuppressWarnings("unchecked")
						Nomenclator sex = ((LinkedList<Nomenclator>) ((Combo) controlsMaps.get("cmbGender")).getData()).get(selectedIndex);
						*/
						Nomenclator sex = (Nomenclator) UiUtils.getSelected((Combo) controlsMaps.get("cmbGender"));
						person.setSex(sex);

						int year = ((DateTime) controlsMaps.get("dtBD")).getYear() - 1900;
						int mont = ((DateTime) controlsMaps.get("dtBD")).getMonth();
						int day = ((DateTime) controlsMaps.get("dtBD")).getDay();
						@SuppressWarnings("deprecation")
						Date birthDate = new Date(year, mont, day);

						person.setBirthDate(birthDate);
						
						
						person.setAddress((((Text) controlsMaps.get("txtAddress")).getText() != null ? ((Text) controlsMaps.get("txtAddress")).getText().replaceAll(" +", " ").trim() : " "));
						person.setEmailAddress((((Text) controlsMaps.get("emailText")).getText() != null ? ((Text) controlsMaps.get("emailText")).getText() : " "));

						
						person.setLibrary(library);

						
						//if(){
							
						//}
						//updatePerson.setImageToSaved(person.getPhoto().getImage());
						//updatePerson.setUrlImage(person.getPhoto().getUrlImage());
						//person.getPhoto().
						
						
						
						if(updatePerson.getUrlImage()==null){
							//Picture picture = new Picture();
							
							//.setUrlImage(updatePerson.getUrlImage());
						//person.getPhoto().setPictureName(((Text) controlsMaps.get("txtCI")).getText());
							
							person.setPhoto(null);
							//
						}else{
							//Picture picture = new Picture();
							//picture.setUrlImage(urlImage);
							//picture.setPictureName(txtCI.getText());
							//person.setPhoto(picture);
							if(person.getPhoto().getPictureID()==null){
								Picture picture = new Picture();
								picture.setUrlImage(updatePerson.getUrlImage());
								picture.setPictureName(person.getDNI());
								person.setPhoto(picture);
							}else{
							person.getPhoto().setUrlImage(updatePerson.getUrlImage());
						}
						}
						
						
						
						Person personSaved = ((PersonViewController) controller).addPerson(person);
						person = null;
						
						((Text) controlsMaps.get("txtFirstName")).setText("");
						((Text) controlsMaps.get("txtSecondName")).setText("");
						((Text) controlsMaps.get("txtFirstLastName")).setText("");
						((Text) controlsMaps.get("txtSecondLastName")).setText("");
						((Text) controlsMaps.get("txtCI")).getText();
						((Combo) controlsMaps.get("cmbGender")).clearSelection();
						((Text) controlsMaps.get("emailText")).setText("");
						((Text) controlsMaps.get("txtAddress")).getText();
						
						
						manager.save(new BaseGridViewEntity<Person>(
								personSaved));
						manager.refresh();
						
						Composite viewSmg = ((PersonViewArea)personTable.getActiveArea()).getMsg();
						
						RetroalimentationUtils.showInformationMessage(viewSmg, MessageUtil
								.unescape(cu.uci.abos.core.l10n.AbosMessages
										.get().MSG_INF_UPDATE_DATA));
					}else{
						RetroalimentationUtils.showErrorShellMessage(
								//saveLibraryFragment.getParent(), 
								MessageUtil
								.unescape(AbosMessages
										.get().ELEMENT_EXIST));
					}
					}else{
						RetroalimentationUtils.showErrorMessage(updatePerson.getMsg(), MessageUtil
								.unescape(cu.uci.abos.core.l10n.AbosMessages
										.get().MSG_ERROR_INCORRECT_DATA));
					}
				}
			}
		});
		return parent;

	}

	@SuppressWarnings("unused")
	private void loadDataPerson() {

		((Text) controlsMaps.get("txtFirstName")).setText(person.getFirstName());
		((Text) controlsMaps.get("txtSecondName")).setText(person.getSecondName());
		((Text) controlsMaps.get("txtFirstLastName")).setText(person.getFirstSurname());
		((Text) controlsMaps.get("txtSecondLastName")).setText(person.getSecondSurname());
		((Text) controlsMaps.get("txtCI")).setText(person.getDNI());
		((Text) controlsMaps.get("txtAddress")).setText((person.getAddress() == null ? "" : person.getAddress()));
		((Text) controlsMaps.get("emailText")).setText((person.getEmailAddress() == null ? "" : person.getEmailAddress()));
		//updatePerson.loadSex();
		int pos = -1;
		for (int i = 0; i < ((Combo) controlsMaps.get("cmbGender")).getItemCount(); i++) {
			if (((Combo) controlsMaps.get("cmbGender")).getItems()[i] == person.getSex().getNomenclatorName()) {
				pos = i;
			}
		}
		((Combo) controlsMaps.get("cmbGender")).select(pos);

		// int fromYear = person.getBirthDate().getYear();
		// int fromMonth = person.getBirthDate().getMonth();
		// int fromDay = person.getBirthDate().getDay();
		// Date startDate = new Date(fromYear, fromMonth, fromDay);

		java.util.Date utilDate = new java.util.Date(person.getBirthDate().getTime());

		int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(utilDate));
		int month = Integer.parseInt(new SimpleDateFormat("MM").format(utilDate));
		int day = Integer.parseInt(new SimpleDateFormat("dd").format(utilDate));

		((DateTime) controlsMaps.get("dtBD")).setDate(year, month - 1, day);

	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closable() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void l10n() {
		// TODO Auto-generated method stub

	}

	public CRUDTreeTable getPersonTable() {
		return personTable;
	}

	public void setPersonTable(CRUDTreeTable personTable) {
		this.personTable = personTable;
	}
	
	@Override
	public String getID() {
		return "updatePersonID";
	}

}
