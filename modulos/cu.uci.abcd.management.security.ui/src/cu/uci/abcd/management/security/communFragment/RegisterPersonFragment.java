package cu.uci.abcd.management.security.communFragment;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.rap.fileupload.DiskFileUploadReceiver;
import org.eclipse.rap.fileupload.FileUploadEvent;
import org.eclipse.rap.fileupload.FileUploadHandler;
import org.eclipse.rap.fileupload.FileUploadListener;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.service.ServerPushSession;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.rap.rwt.widgets.DialogUtil;
import org.eclipse.rap.rwt.widgets.FileUpload;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.filedialog.widgets.FileDialog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.common.Picture;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.management.security.l10n.AbosMessages;
import cu.uci.abcd.management.security.ui.RegisterPerson;
import cu.uci.abcd.management.security.ui.controller.PersonViewController;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.FragmentContributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.ui.FormPagePainter;
import cu.uci.abos.core.ui.PagePainter;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.AbosImageUtil;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class RegisterPersonFragment extends ContributorPage implements
		FragmentContributor {

	private ServerPushSession pushSession;
	private FileUpload fileUpload;
	private Image imageToSaved = null;
	public Image getImageToSaved() {
		return imageToSaved;
	}

	public void setImageToSaved(Image imageToSaved) {
		this.imageToSaved = imageToSaved;
	}

	private Image image;
	private Text txtAddress;
	private Text txtFirstName;
	private Text txtSecondName;
	private Text txtFirstLastName;
	private Text txtSecondLastName;
	private Text txtCI;
	private Combo cmbGender;
	private DateTime dtBD;
	private Text emailText;
	private ViewController controller;
	private Map<String, Control> controls = new HashMap<String, Control>();
	private Person person;
	private CustomControlDecoration customControlDecorationFactory;
	private Label firstNameLabel;
	private Label SecondNameLabel;
	private Label FirstSurnameLabel;
	private Label SecondSurnameLabel;
	private Label identificationLabel;
	private Label genderLabel;
	private Label BirthDateLabel;
	private Label emailLabel;
	private Label addressLabel;
	private Button acceptButton;
	private Label pictureLabel;
	private ValidatorUtils validator;
	private Label registerPersonLabel;

	public CustomControlDecoration getCustomControlDecorationFactory() {
		return customControlDecorationFactory;
	}

	public void setCustomControlDecorationFactory(
			CustomControlDecoration customControlDecorationFactory) {
		this.customControlDecorationFactory = customControlDecorationFactory;
	}

	private CRUDTreeTable personTable;
	private IVisualEntityManager manager;

	String urlImage = null;

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	private PagePainter painter;

	private int dimension;
	private RegisterPerson registerPerson;
	private ContributorService contributorService;
	Link linkDelete;

	public RegisterPersonFragment(Person person, ViewController controller,
			int dimension, RegisterPerson registerPerson,
			ContributorService contributorService) {
		this.person = person;
		this.controller = controller;
		this.dimension = dimension;
		this.contributorService = contributorService;
		this.registerPerson = registerPerson;
	}

	public RegisterPersonFragment(Person person, ViewController controller,
			int dimension) {
		this.person = person;
		this.controller = controller;
		this.dimension = dimension;
	}

	Composite register;
	Composite msg;


	public Composite getMsg() {
		return msg;
	}

	public void setMsg(Composite msg) {
		this.msg = msg;
	}

	@Override
	public Control createUIControl(final Composite parent) {

		final Image image = new Image(Display.getDefault(), AbosImageUtil
				.loadImage(null, Display.getCurrent(),
						"abcdconfig/resources/photo.png", false).getImageData()
				.scaledTo(100, 100));

		validator = new ValidatorUtils(new CustomControlDecoration());
		painter = new FormPagePainter();

		painter.setDimension(dimension-5);

		painter.addComposite(parent);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");

		register = new Composite(parent, SWT.NONE);
		painter.addComposite(register);
		register.setData(RWT.CUSTOM_VARIANT, "gray_background");

		registerPersonLabel = new Label(register, SWT.NONE);
		painter.addHeader(registerPersonLabel);
		
		Label separator1 = new Label(register, SWT.SEPARATOR | SWT.HORIZONTAL);
		painter.addSeparator(separator1);
		
		Label personData = new Label(register, SWT.NONE);
		personData.setText(AbosMessages.get().LABEL_PERSON_DATA);
		painter.addHeader(personData);
		
		showFileUpload(register, registerPersonLabel);
		
		linkDelete = new Link(register, SWT.NONE);
		linkDelete.setText("<a>Eliminar imagen</a>");
		FormDatas.attach(linkDelete).atTopTo(pictureLabel, 179)
				.atLeftTo(personData, 226).withHeight(20);
		linkDelete.setVisible(false);

		linkDelete.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = 8413313041460293463L;

			@Override
			public void handleEvent(Event arg0) {
				imageToSaved = null;
				pictureLabel.setImage(image);
				urlImage = null;
				
				linkDelete.setVisible(false);
			}
		});

		

		pictureLabel = new Label(register, SWT.BORDER);
		painter.add(pictureLabel);
		FormDatas.attach(pictureLabel).atTopTo(personData)
				.atLeft(135).withHeight(100).withWidth(100);
		pictureLabel.setImage(image);

		painter.reset();

		firstNameLabel = new Label(register, SWT.NONE);
		firstNameLabel.setText("Primer Nombre");

		painter.add(firstNameLabel);

		txtFirstName = new Text(register, SWT.NONE);
		
		validator.applyValidator(txtFirstName, "txtFirstNameRequired",
				DecoratorType.REQUIRED_FIELD, true);
		
		validator.applyValidator(txtFirstName, "txtFirstNameAlpha",
				DecoratorType.ALPHA_SPACES, true, 25);
		
		
		
		
		painter.add(txtFirstName);
		controls.put("txtFirstName", txtFirstName);

		SecondNameLabel = new Label(register, SWT.NONE);
		painter.add(SecondNameLabel);

		txtSecondName = new Text(register, SWT.NONE);
		validator.applyValidator(txtSecondName, "txtSecondNameAlpha",
				DecoratorType.ALPHA_SPACES, true, 25);
		
		
		
		controls.put("txtSecondName", txtSecondName);
		painter.add(txtSecondName);

		FirstSurnameLabel = new Label(register, SWT.NONE);
		controls.put("txtFirstLastName", txtFirstLastName);
		painter.add(FirstSurnameLabel);

		txtFirstLastName = new Text(register, SWT.NONE);
		
		validator.applyValidator(txtFirstLastName, "txtFirstLastNameRequired",
				DecoratorType.REQUIRED_FIELD, true);
		
		validator.applyValidator(txtFirstLastName, "txtFirstLastNameAlpha",
				DecoratorType.ALPHA_SPACES_GUION, true, 25);
		
		
		painter.add(txtFirstLastName);
		controls.put("txtFirstLastName", txtFirstLastName);

		SecondSurnameLabel = new Label(register, SWT.NONE);
		painter.add(SecondSurnameLabel);

		txtSecondLastName = new Text(register, SWT.NONE);
		controls.put("txtSecondLastName", txtSecondLastName);
		painter.add(txtSecondLastName);
		
		validator.applyValidator(txtSecondLastName, "txtSecondLastNameAlpha",
				DecoratorType.ALPHA_SPACES_GUION, true, 25);
		

		identificationLabel = new Label(register, SWT.NONE);
		painter.add(identificationLabel);

		txtCI = new Text(register, SWT.NONE);
		
		validator.applyValidator(txtCI, "txtCIRequired",
				DecoratorType.REQUIRED_FIELD, true);
		
		validator.applyValidator(txtCI, "txtCIAlpha",
				DecoratorType.ALPHA_NUMERIC, true, 25);
		
		
		controls.put("txtCI", txtCI);
		painter.add(txtCI);

		genderLabel = new Label(register, SWT.NONE);
		painter.add(genderLabel);

		cmbGender = new Combo(register, SWT.SIMPLE | SWT.DROP_DOWN
				| SWT.READ_ONLY);
		
		controls.put("cmbGender", cmbGender);
		painter.add(cmbGender);

		BirthDateLabel = new Label(register, SWT.NONE);
		painter.add(BirthDateLabel);

		dtBD = new DateTime(register, SWT.BORDER | SWT.DROP_DOWN);
		controls.put("dtBD", dtBD);
		painter.add(dtBD);

		emailLabel = new Label(register, SWT.NONE);
		painter.add(emailLabel);

		emailText = new Text(register, SWT.NONE);
		validator.applyValidator(emailText, "emailTextMail",
				DecoratorType.EMAIL, true, 50);
		controls.put("emailText", emailText);
		painter.add(emailText);

		addressLabel = new Label(register, SWT.NONE);
		painter.add(addressLabel);

		txtAddress = new Text(register, SWT.MULTI | SWT.BORDER | SWT.WRAP
				| SWT.V_SCROLL);
		controls.put("txtAddress", txtAddress);
		painter.add(txtAddress);
		validator.applyValidator(txtAddress, 201);

		painter.reset();
		painter.add(new Label(register, SWT.NONE), Percent.W25);
		painter.reset();

		if (person == null) {

			Button cancelBtn = new Button(register, SWT.PUSH);
			cancelBtn.setText(AbosMessages.get().BUTTON_CANCEL);
			painter.add(cancelBtn);

			cancelBtn.addSelectionListener(new SelectionAdapter() {
				private static final long serialVersionUID = 1L;

				@Override
				public void widgetSelected(SelectionEvent e) {

					MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_QUESTION), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_CANCEL_OPERATION),
							new DialogCallback() {
					private static final long serialVersionUID = 1L;
						@Override
						public void dialogClosed(int returnCode) {
							if (returnCode == 0) {
								registerPerson.notifyListeners(
										SWT.Dispose, new Event());									
							}						
						}					
					} );
					

				}
			});

			acceptButton = new Button(register, SWT.PUSH);
			painter.add(acceptButton);
			acceptButton.addSelectionListener(new SelectionAdapter() {

				private static final long serialVersionUID = -6000516465011842023L;

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					
					if (getValidator().decorationFactory
							.isRequiredControlDecorationIsVisible()) {
						RetroalimentationUtils.showErrorMessage(msg, MessageUtil
								.unescape(cu.uci.abos.core.l10n.AbosMessages
										.get().MSG_ERROR_FIELD_REQUIRED));
					} else {
						if (getValidator().decorationFactory
								.AllControlDecorationsHide()) {
							Library library = (Library) SecurityUtils
									.getPrincipal().getByKey("library");

							String identification = txtCI.getText().replaceAll(" +", " ").trim();
							Person existPerson= ((PersonViewController) controller)
									.findPersonByIdentification(identification, library.getLibraryID());
							if (existPerson == null) {
								
							person = new Person();
							
							
							
							person.setFirstName((txtFirstName.getText() != null ? txtFirstName.getText().substring(0, 1).toUpperCase() + txtFirstName.getText().substring(1) : null));
							person.setSecondName((txtSecondName.getText().length()>0 ? txtSecondName.getText().substring(0, 1).toUpperCase() + txtSecondName.getText().substring(1) : null));
							person.setFirStsurname((txtFirstLastName.getText() != null ? txtFirstLastName.getText().replaceAll(" +", " ").trim().substring(0, 1).toUpperCase() + txtFirstLastName.getText().replaceAll(" +", " ").trim().substring(1) : null));
							person.setSecondSurname((txtSecondLastName.getText().length()>0 ? txtSecondLastName.getText().replaceAll(" +", " ").trim().substring(0, 1).toUpperCase() + txtSecondLastName.getText().replaceAll(" +", " ").trim().substring(1) : null));
							person.setDNI(identification);

							Nomenclator sex = (Nomenclator) UiUtils
									.getSelected(cmbGender);

							person.setSex(sex);
							int year = dtBD.getYear() - 1900;
							int mont = dtBD.getMonth();
							int day = dtBD.getDay();
							@SuppressWarnings("deprecation")
							Date birthDate = new Date(year, mont, day);
							person.setBirthDate(birthDate);
							person.setEmailAddress((emailText.getText() != null ? emailText
									.getText() : " "));
							person.setAddress((txtAddress.getText() != null ? txtAddress
									.getText().replaceAll(" +", " ").trim() : " "));
							
							if (urlImage != null) {
								Picture picture = new Picture();
								picture.setUrlImage(urlImage);
								picture.setPictureName(txtCI.getText());
								person.setPhoto(picture);
							}

							person.setLibrary(library);

							Person personSaved = ((PersonViewController) controller)
									.addPerson(person);
							
							registerPerson.notifyListeners(SWT.Dispose, new Event());
							contributorService.selectContributor("viewPerson", personSaved, registerPerson, contributorService);
							
							//person = null;

						
							/*
							viewPersonFragment = new ViewPersonFragment(
									personSaved, true, registerPerson,
									contributorService);
							show = (Composite) viewPersonFragment
									.createUIControl(parent);
							painter.addComposite(show);
							
							ajustRezise(register, 1);
							refresh(show);
							RetroalimentationUtils.showInformationMessage(viewPersonFragment.getMsg(), MessageUtil
									.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_CREATE_NEW_ELEMENT));
							
							*/
						}else{
							RetroalimentationUtils.showErrorShellMessage(
									//saveLibraryFragment.getParent(),
									MessageUtil.unescape(AbosMessages.get().ELEMENT_EXIST));
						}
						} else {
							RetroalimentationUtils.showErrorMessage(msg, MessageUtil
									.unescape(cu.uci.abos.core.l10n.AbosMessages
											.get().MSG_ERROR_INCORRECT_DATA));
						}
					}

				}
			});
			acceptButton.getShell().setDefaultButton(acceptButton);
		}
		
		initialize(cmbGender,
				((PersonViewController) controller).findNomenclatorByCode(null,
						Nomenclator.SEX));
		
		validator.applyValidator(cmbGender, "cmbGenderRequired",
				DecoratorType.REQUIRED_FIELD, true);
		int thisYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new java.util.Date()));
		dtBD.setYear(thisYear-50);
		LoadPersonData();
		
		validator.applyRangeDateValidator(dtBD, "dtBD",
				DecoratorType.DATE_RANGE, -100, 0, 0, -5, 0, 0,true);
		dtBD.setBackground(null);
		
		
		l10n();
		return parent;
	}

	private FileUpload showFileUpload(final Composite register,
			final Label pictureLabel) {
		fileUpload = new FileUpload(register, SWT.PUSH);
		fileUpload.setText(AbosMessages.get().LABEL_EXAMINE);
		FormDatas.attach(fileUpload).atTopTo(pictureLabel, 128)
				.atLeftTo(pictureLabel, 110).withHeight(20);

		fileUpload.addSelectionListener(new SelectionAdapter() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean jpg = fileUpload.getFileName().contains(".jpg");
				boolean png = fileUpload.getFileName().contains(".png");
				boolean bmp = fileUpload.getFileName().contains(".bmp");
				boolean jpeg = fileUpload.getFileName().contains(".jpeg");
				if (jpg || png || bmp || jpeg) {
					final String url = startUploadReceiver();
					pushSession = new ServerPushSession();
					pushSession.start();
					fileUpload.submit(url);
				} else {
					//pictureLabel.setImage(image);
					fileUpload.dispose();
					showFileUpload(register, registerPersonLabel);
					//FormDatas.attach(fileUpload).atTopTo(pictureLabel, 95)
					//.atLeftTo(pictureLabel, 234).withHeight(20);
					refresh(register);
					RetroalimentationUtils.showErrorMessage(msg,
							AbosMessages.get().MESSAGE_NO_PICTURE);
					
				}

			}
		});
return fileUpload;
	}

	private void LoadPersonData() {
		
		
		
		

		if (person != null) {
			if( person.getPhoto().getPictureID()!=null ){
				setUrlImage(person.getPhoto().getUrlImage());
			}
			txtFirstName.setText(person.getFirstName() != null ? person
					.getFirstName() : "");
			txtSecondName.setText(person.getSecondName() != null ? person
					.getSecondName() : "");
			txtFirstLastName.setText(person.getFirstSurname() != null ? person
					.getFirstSurname() : "");
			txtSecondLastName
					.setText(person.getSecondSurname() != null ? person
							.getSecondSurname() : "");
			txtCI.setText(person.getDNI() != null ? person.getDNI() : "");
			txtAddress.setText((person.getAddress() == null ? "" : person
					.getAddress()));
			emailText.setText((person.getEmailAddress() == null ? "" : person
					.getEmailAddress()));

			UiUtils.selectValue(cmbGender, person.getSex());

			java.util.Date utilDate = new java.util.Date(person.getBirthDate()
					.getTime());

			int year = Integer.parseInt(new SimpleDateFormat("yyyy")
					.format(utilDate));
			int month = Integer.parseInt(new SimpleDateFormat("MM")
					.format(utilDate));
			int day = Integer.parseInt(new SimpleDateFormat("dd")
					.format(utilDate));

			dtBD.setDate(year, month - 1, day);

			Image image = person.getPhoto().getImage();

			pictureLabel.setImage(image);
			
			if(person.getPhoto().getPictureID()!=null){
				linkDelete.setVisible(true);
			}else{
				linkDelete.setVisible(false);
			}
			

		}
	}

	@SuppressWarnings("unused")
	private void openFileDialog(Shell shell) {
		final FileDialog fileDialog = new FileDialog(shell, SWT.NONE);

		fileDialog.setText(AbosMessages.get().MESSAGE_FILE_PICTURE);

		DialogUtil.open(fileDialog, new DialogCallback() {
			private static final long serialVersionUID = 1080789552039834679L;

			public void dialogClosed(int returnCode) {
				if (returnCode == 32) {
					if (fileDialog.getFileName().length() > 0) {
						urlImage = fileDialog.getFileName();
						Image image = new Image(Display.getDefault(),
								AbosImageUtil
										.loadImage(null, Display.getCurrent(),
												urlImage, false).getImageData()
										.scaledTo(100, 100));
						pictureLabel.setImage(image);
						//linkDelete.setVisible(true);
					}

				}
			}
		});
	}

	@SuppressWarnings("unused")
	private final void ClearForm() {
		txtFirstName.setText("");
		txtSecondName.setText("");
		txtAddress.setText("");
		txtCI.setText("");
		txtFirstLastName.setText("");
		emailText.setText("");
		txtSecondLastName.setText("");

	}

	@Override
	public String getID() {
		return "registerPersonID1";
	}

	@Override
	public void l10n() {
		if(person==null ){
			registerPersonLabel.setText("REGISTRAR PERSONA");
		}else{
			registerPersonLabel.setText("MODIFICAR PERSONA");
		}
		
		
		firstNameLabel
				.setText(MessageUtil.unescape(AbosMessages.get().FIRST_NAME));
		SecondNameLabel
				.setText(MessageUtil.unescape(AbosMessages.get().SECOND_NAME));
		FirstSurnameLabel
				.setText(MessageUtil.unescape(AbosMessages.get().FIRST_SURNAME));
		SecondSurnameLabel
				.setText(MessageUtil.unescape(AbosMessages.get().SECOND_SURNAME));
		identificationLabel
				.setText(MessageUtil.unescape(AbosMessages.get().IDENTIFICATION));
		genderLabel.setText(MessageUtil.unescape(AbosMessages.get().SEX));
		BirthDateLabel
				.setText(MessageUtil.unescape(AbosMessages.get().BIRTHDAY));
		emailLabel.setText(MessageUtil.unescape(AbosMessages.get().EMAIL));
		addressLabel.setText(MessageUtil.unescape(AbosMessages.get().ADDRESS));
		if (person == null) {
			acceptButton
					.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACEPT));
		}

		registerPersonLabel.getParent().layout(true, true);
		registerPersonLabel.getParent().redraw();
		registerPersonLabel.getParent().update();
	}

	@Override
	public Control getControl(String arg0) {
		return null;
	}

	public Map<String, Control> getControls() {
		return controls;
	}

	public void setControls(Map<String, Control> controls) {
		this.controls = controls;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public IVisualEntityManager getManager() {
		return manager;
	}

	public void setManager(IVisualEntityManager manager) {
		this.manager = manager;
	}

	public CRUDTreeTable getPersonTable() {
		return personTable;
	}

	public void setPersonTable(CRUDTreeTable personTable) {
		this.personTable = personTable;
	}

	public ValidatorUtils getValidator() {
		return validator;
	}

	public void setValidator(ValidatorUtils validator) {
		this.validator = validator;
	}

	public void ajustRezise(Composite parent, int height) {
		parent.setSize(parent.getSize().x, height);
		FormData tempo = (FormData) parent.getLayoutData();
		tempo.height = height;
		parent.setLayoutData(tempo);

		parent.setLayoutData(tempo);
		parent.layout(true, true);
		parent.redraw();
		parent.update();
	}

	public void refresh(Composite parent) {
		parent.layout(true, true);
		parent.redraw();
		parent.update();
	}

	private String startUploadReceiver() {
		final DiskFileUploadReceiver receiver = new DiskFileUploadReceiver();
		// receiver.receive(dataStream, details);
		FileUploadHandler uploadHandler = new FileUploadHandler(receiver);
		uploadHandler.addUploadListener(new FileUploadListener() {

			public void uploadProgress(FileUploadEvent event) {
				System.out.println("SUBIENDO");

			}

			public void uploadFailed(FileUploadEvent event) {
				// addToLog( "upload failed: " + event.getException() );
			}

			public void uploadFinished(FileUploadEvent event) {
				System.out.println("PATH: "
						+ receiver.getTargetFiles()[0].getAbsolutePath());
				urlImage = receiver.getTargetFiles()[0].getAbsolutePath();
				Image image = new Image(Display.getDefault(), AbosImageUtil
						.loadImage(null, Display.getDefault(), urlImage, false)
						.getImageData().scaledTo(100, 100));
				imageToSaved = image;
				showImage(image);
			}
		});

		return uploadHandler.getUploadUrl();
	}

	private void showImage(final Image image) {

		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				pictureLabel.setImage(image);
				fileUpload.dispose();
				pushSession.stop();
				showFileUpload(register, registerPersonLabel);
				refresh(register);
				
				linkDelete.setVisible(true);
			}
		});

	}
}
