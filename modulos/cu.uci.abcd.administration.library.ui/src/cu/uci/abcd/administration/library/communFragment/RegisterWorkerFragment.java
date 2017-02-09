package cu.uci.abcd.administration.library.communFragment;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.springframework.data.domain.Page;

import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.administration.library.ui.RegisterWorker;
import cu.uci.abcd.administration.library.ui.controller.LibraryViewController;
import cu.uci.abcd.administration.library.util.Auxiliary;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abcd.domain.management.library.Worker;
import cu.uci.abcd.management.security.IPersonService;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.FragmentContributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.FormPagePainter;
import cu.uci.abos.core.ui.FragmentPage;
import cu.uci.abos.core.ui.PagePainter;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.AbosImageUtil;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.ServiceProviderUtil;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.Column;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeColumnListener;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class RegisterWorkerFragment extends FragmentPage implements FragmentContributor {
	private Label registerWorkerLabel;
	private ViewController controller;
	private Library library;
	private Label roomSurface;
	private Label aviableReadingSets;
	private Label surface;
	private Label roomName;
	private Combo workerTypeCombo;
	private DateTime registerDateTime;
	private Text descriptionText;
	private Button saveBtn;
	private Button cancelBtn;
	private Label workerDataListLabel;
	private Label workerTypeLabel;
	private Label registerDateLabel;
	private Label descriptionLabel;
	private Combo activityCombo;
	private Label activityLabel;
	private Person person;
	private Label personData;
	private Label allRoomLabel; 
	private ViewWorkerFragment viewWorkerFragment;
	private List<Room> asignedRooms = new ArrayList<>();
	private List<Room> listRooms = new ArrayList<>();
	private List<Button> listCheck = new ArrayList<>();
	private Composite parent;
	
	private String orderByStringPerson = "firstName";
	private Label sugestLabel;
	private Text searchText;
	private Button searchButton;
	private CRUDTreeTable tablePerson;
	private PagePainter painter;
	private IPersonService personService;
	private String searchTextConsult = null;
	private RegisterWorker registerWorker;
	private Worker worker;
	private ContributorService contributorService;
	private Composite personDataComposite;
	private Composite viewPersonDataComposite;
	private Composite searchPersonComposite;
	private Composite tablePersonComposite;
	private Composite workerDataComposite;
	private int heightTablePerson;
	private Label fullName;
	private Label user;
	private Label birthday;
	private Label sex;
	private Label identification;
	private Label pictureLabel;
	private ValidatorUtils validator;
	private Group personGroup;
	private Label fullNameLabel;
	private Label userLabel;
	private Label birthdayLabel;
	private Label sexLabel;
	private Label identificationLabel;
	private Button unassociate;
	private Composite register;
	
	@SuppressWarnings("unused")
	private int heightPersonDataComposite;
	@SuppressWarnings("unused")
	private int heightViewPersonDataComposite;
	@SuppressWarnings("unused")
	private int heightSearchPersonComposite;
	@SuppressWarnings("unused")
	private int heightTablePersonComposite;
	@SuppressWarnings("unused")
	private int heightWorkerDataComposite;
	@SuppressWarnings("unused")
	private int direction = 1024;
	@SuppressWarnings("unused")
	private String orderByString = "roomName";
	@SuppressWarnings("unused")
	private int tableHeight;
	@SuppressWarnings("unused")
	private Composite show;
	
	public RegisterWorkerFragment(Worker worker, ViewController controller, Library library,
			Person person) {
		this.worker = worker;
		this.controller = controller;
		this.library = library;
		this.person = person;
		this.personService = ServiceProviderUtil
				.getService(IPersonService.class);
	}
	
	public RegisterWorkerFragment(Worker worker, ViewController controller, Library library,
			Person person, RegisterWorker registerWorker, ContributorService contributorService) {
		this.worker = worker;
		this.controller = controller;
		this.library = library;
		this.person = person;
		this.personService = ServiceProviderUtil
				.getService(IPersonService.class);
		this.registerWorker = registerWorker;
		this.contributorService = contributorService;
	}

	
	
	

	
	//FIXME CLASE EXTENSA 708 LINEAS
	@Override
	public Control createUIControl(final Composite parent) {
		this.parent = parent;
		listRooms = ((LibraryViewController) controller).getAllManagementLibraryViewController().getRoomService().findAll(library.getLibraryID());
		  if(worker!=null){
			  setAsignedRooms(worker.getRooms());
			}
		
		
				
				
		painter = new FormPagePainter();
		parent.setLayout(new FormLayout());
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(parent).atLeft(0).atRight(0);

		validator = new ValidatorUtils(new CustomControlDecoration());
/*
		int total = parent.getDisplay().getBounds().width;
		double middle1 = (total * 0.375);
		int middle = Integer.valueOf((int) Math.round(middle1));
		*/
		register = new Composite(parent, SWT.NONE);
		register.setData(RWT.CUSTOM_VARIANT, "gray_background");
		painter.addComposite(register);
		
		registerWorkerLabel = new Label(register, SWT.NONE);
		registerWorkerLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(registerWorkerLabel).atTopTo(register, 25).atLeft(15);

		Label separator = new Label(register, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormDatas.attach(separator).atTopTo(registerWorkerLabel, 15).atLeft(15).atRight(15);
		
		personDataComposite = new Composite(register, SWT.NONE);
		personDataComposite.setLayout(new FormLayout());
		personDataComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(personDataComposite).atTopTo(separator, 5).atLeft(15)
				.atRight(15);

		viewPersonDataComposite = new Composite(personDataComposite, SWT.NONE);
		viewPersonDataComposite.setLayout(new FormLayout());
		viewPersonDataComposite.setVisible(false);
		viewPersonDataComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(viewPersonDataComposite).atTopTo(personDataComposite).withHeight(100);

		//personDataLabel = new Label(viewPersonDataComposite, SWT.NONE);
		//FormDatas.attach(personDataLabel).atTopTo(viewPersonDataComposite, 15)
			//	.atLeft(0);

		personGroup = new Group(viewPersonDataComposite, SWT.NONE);
		personGroup.setLayout(new FormLayout());
		personGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(personGroup).atTopTo(viewPersonDataComposite, 5).atLeft(0);

		pictureLabel = new Label(personGroup, SWT.BORDER);
		FormDatas.attach(pictureLabel).atTopTo(personGroup, 10).atLeft(15);
		Image image = new Image(Display.getDefault(), AbosImageUtil
				.loadImage(null, Display.getCurrent(),
						"abcdconfig/resources/photo.png", false).getImageData()
				.scaledTo(100, 100));
		pictureLabel.setImage(image);

		fullNameLabel = new Label(personGroup, SWT.NONE);
		fullNameLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(fullNameLabel).atTopTo(personGroup, 10)
				.atLeftTo(pictureLabel, 40);

		fullName = new Label(personGroup, SWT.NONE);
		FormDatas.attach(fullName).atTopTo(personGroup, 10)
				.atLeftTo(fullNameLabel, 5).atRight(15);

		Label firstSeparator = new Label(personGroup, SWT.SEPARATOR
				| SWT.HORIZONTAL);
		FormDatas.attach(firstSeparator).atTopTo(fullName, 1)
				.atLeftTo(pictureLabel, 5).atRight(5);

		userLabel = new Label(personGroup, SWT.NONE);
		userLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(userLabel).atTopTo(fullNameLabel, 6)
				.atRightTo(fullName, 5);

		user = new Label(personGroup, SWT.NONE);
		FormDatas.attach(user).atTopTo(fullNameLabel, 6).atLeftTo(userLabel, 5);

		Label secondSeparator = new Label(personGroup, SWT.SEPARATOR
				| SWT.HORIZONTAL);
		FormDatas.attach(secondSeparator).atTopTo(user, 1)
				.atLeftTo(pictureLabel, 5).atRight(5);

		birthdayLabel = new Label(personGroup, SWT.NONE);
		birthdayLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(birthdayLabel).atTopTo(userLabel, 6)
				.atRightTo(user, 5);

		birthday = new Label(personGroup, SWT.NONE);
		FormDatas.attach(birthday).atTopTo(userLabel, 6)
				.atLeftTo(birthdayLabel, 5);

		Label thirdSeparator = new Label(personGroup, SWT.SEPARATOR
				| SWT.HORIZONTAL);
		FormDatas.attach(thirdSeparator).atTopTo(birthday, 1)
				.atLeftTo(pictureLabel, 5).atRight(5);

		sexLabel = new Label(personGroup, SWT.NONE);
		sexLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(sexLabel).atTopTo(birthdayLabel, 6)
				.atRightTo(birthday, 5);

		sex = new Label(personGroup, SWT.NONE);
		FormDatas.attach(sex).atTopTo(birthdayLabel, 6).atLeftTo(sexLabel, 5);

		Label fourthSeparator = new Label(personGroup, SWT.SEPARATOR
				| SWT.HORIZONTAL);
		FormDatas.attach(fourthSeparator).atTopTo(sex, 1)
				.atLeftTo(pictureLabel, 5).atRight(5);

		identificationLabel = new Label(personGroup, SWT.NONE);
		identificationLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(identificationLabel).atTopTo(sexLabel, 6)
				.atRightTo(sex, 5);

		identification = new Label(personGroup, SWT.NONE);
		FormDatas.attach(identification).atTopTo(sexLabel, 6)
				.atLeftTo(identificationLabel, 5);

		unassociate = new Button(viewPersonDataComposite, SWT.NONE);
		FormDatas.attach(unassociate).atTopTo(personGroup, -25).withHeight(23)
				.atLeftTo(personGroup, 5);
		unassociate.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 7932356419459592823L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				dissociate();
			}
		});
		//FIXME BORRAR CODIGO COMENTARIADO
		searchPersonComposite = new Composite(personDataComposite, SWT.NONE);
		searchPersonComposite.setLayout(new FormLayout());
		searchPersonComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(searchPersonComposite).atTopTo(personDataComposite);

		personData = new Label(searchPersonComposite, SWT.NONE);
		personData.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(personData).atTopTo(parent, 15).atLeft(0);

		sugestLabel = new Label(searchPersonComposite, SWT.NONE);
		FormDatas.attach(sugestLabel).atTopTo(personData, 10).atLeft(0);

		searchText = new Text(searchPersonComposite, 0);
		FormDatas.attach(searchText).atTopTo(sugestLabel, 5).withHeight(10)
				.atLeft(0).withWidth(250);
		
		searchText.addFocusListener(new FocusListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void focusLost(FocusEvent arg0) {
				searchButton.getShell().setDefaultButton(null);
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				searchButton.getShell().setDefaultButton(searchButton);
			}
		});
		
		searchButton = new Button(searchPersonComposite, SWT.NONE);
		FormDatas.attach(searchButton).atTopTo(sugestLabel, 5).withHeight(20)
				.atLeftTo(searchText, 10);

		//searchButton.getShell().setDefaultButton(searchButton);
		
		searchButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -8115718469128343452L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				Search();
			}
		});

		tablePersonComposite = new Composite(personDataComposite, SWT.NONE);
		tablePersonComposite.setLayout(new FormLayout());
		tablePersonComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(tablePersonComposite).atTopTo(searchPersonComposite, 15)
				.atLeft(0).atRight(0);
		tablePersonComposite.setVisible(false);

		tablePerson = new CRUDTreeTable(tablePersonComposite, SWT.NONE);
		tablePerson.setEntityClass(Person.class);
		tablePerson.setSearch(false);
		tablePerson.setSaveAll(false);
		tablePerson.setPageSize(10);

		tablePerson.addListener(SWT.Resize, new Listener() {
            private static final long serialVersionUID = 8817895862824622805L;
            @Override
            public void handleEvent(Event event) {
                    refresh();
            }
           });
		
  
  
		Column columnPerson = new Column("associate", parent.getDisplay(),
				new TreeColumnListener() {
					public void handleEvent(TreeColumnEvent event) {

						Long idPerson = ((Person) event.entity.getRow())
								.getPersonID();
						Worker workerSearch = ((LibraryViewController) controller)
								.getAllManagementLibraryViewController()
								.getWorkerService()
								.readWorkerByPerson(idPerson);
						
						if (workerSearch == null || ( workerSearch!=null && worker!=null && workerSearch.getPerson().getPersonID()==worker.getPerson().getPersonID() ) ) {
							person = (Person) event.entity.getRow();
							associate();
							showPerson();
							
							workerDataComposite.setVisible(true);
							//refresh(workerDataComposite);
						} else {
							/*
							MessageDialogUtil
									.openInformation(Display.getCurrent()
											.getActiveShell(),
											MessageUtil
											.unescape(cu.uci.abos.core.l10n.AbosMessages
													.get().MESSAGE_INFORMATION),
													MessageUtil
													.unescape(AbosMessages.get().ALERT_PERSON_IS_WORKER),
											null);edfsd
											*/
											RetroalimentationUtils.showErrorShellMessage(
													//register, 
													MessageUtil
													.unescape(AbosMessages.get().ALERT_PERSON_IS_WORKER));
						}
					}
				});
		
		columnPerson.setToolTipText(MessageUtil.unescape(AbosMessages.get().BUTTON_ASSOCIATE));
		tablePerson.addActionColumn(columnPerson);

		TreeTableColumn columnsPerson[] = {
				new TreeTableColumn(60, 0, "getFullName"),
				new TreeTableColumn(20, 1, "getDNI"),
				new TreeTableColumn(20, 2, "getSex.getNomenclatorDescription") };
		tablePerson.createTable(columnsPerson);

		tablePerson.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
				if (event.sortData != null) {
					direction = event.sortData.sortDirection;
					switch (event.sortData.columnIndex) {
					case 1:
						orderByString = "dni";
						break;
					}
				}
				searchPerson(event.currentPage - 1, event.pageSize);
				int rowsCount = tablePerson.getRows().size();

				heightTablePerson = (rowsCount * 28) + 110;
				ajustRezise(tablePersonComposite, heightTablePerson);
				ajustRezise(personDataComposite, getHeightSearchPersonComposite() + getHeightTablePersonComposite() + 10);
				refresh(personDataComposite.getParent().getShell());
				
				refresh();
			}

		});

		FormDatas.attach(tablePerson).atTopTo(tablePersonComposite).atLeft()
				.atRight();
			/*
		tablePerson.setActionDenied(new IActionDenied() {
			@Override
			public boolean isDenied(Column column, Row row) {
				Person person = (Person) row;
				Long idPerson = person.getPersonID();
				Worker workerSearch = ((LibraryViewController) controller)
						.getAllManagementLibraryViewController()
						.getWorkerService().readWorkerByPerson(idPerson);
				if (column.getIndex() == 3) {
					if (workerSearch == null
							|| (workerSearch != null && worker!=null && workerSearch
									.getPerson().getPersonID() == worker
									.getPerson().getPersonID())) {
						return false;
					}
				}
				return true;
			}
		});
		*/
		//FIXME BORRAR CODIGO COMENTARIADO
		
		workerDataComposite = new Composite(register, SWT.NONE);
		workerDataComposite.setLayout(new FormLayout());
		workerDataComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(workerDataComposite).atTopTo(personDataComposite, 5)
				.atLeft(0).atRight(15);
		workerDataComposite.setVisible(false);

		workerDataListLabel = new Label(workerDataComposite, SWT.NORMAL);
		FormDatas.attach(workerDataListLabel).atTopTo(workerDataComposite, 15).atLeft(15);
		workerDataListLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		//painter.addHeader(workerDataListLabel);
		
		
		
		
		workerTypeCombo = new Combo(workerDataComposite, SWT.READ_ONLY);
		painter.add(workerTypeCombo);
		
		
		
        workerTypeLabel = new Label(workerDataComposite, SWT.NONE | SWT.WRAP);
        painter.add(workerTypeLabel, Percent.W15);
		

		registerDateTime = new DateTime(workerDataComposite, SWT.BORDER
				| SWT.DROP_DOWN | SWT.WRAP);
		painter.add(registerDateTime, Percent.W15);
		
		validator.applyRangeDateValidator(registerDateTime, "registerDateTime1",
				DecoratorType.DATE_RANGE, -50, 0, 0, 0, 0, 0, true);

		registerDateTime.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -5179607102055540891L;
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(!validator.decorationFactory.getControlByKey("registerDateTime1").isVisible()){
					registerDateTime.setBackground(null);
				}
				
			}

		});
		
		registerDateLabel = new Label(workerDataComposite, SWT.NONE | SWT.WRAP);
		painter.add(registerDateLabel, Percent.W15);
		
		int derecha = parent.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		int middle = derecha/2;
		
		
		
        if( ((FormPagePainter) painter).getDimension()<1110 ){
        	
        	FormDatas.attach(workerTypeLabel).atTopTo(workerDataListLabel, 20)
    		.atLeft(15);
        	
        	FormDatas.attach(workerTypeCombo).atTopTo(workerDataListLabel, 15)
    		.atRight(15).withWidth(middle).withHeight(23);
        	
    		FormDatas.attach(registerDateLabel).atTopTo(workerTypeCombo, 20).atLeft(15);
    		
    		FormDatas.attach(registerDateTime).atTopTo(workerTypeCombo, 15)
    		.atRight(15).withWidth(middle).withHeight(25);
    		
		}else{
			FormDatas.attach(workerTypeCombo).atTopTo(workerDataListLabel, 15)
			.atRight(middle+9).withWidth(240).withHeight(23);
			FormDatas.attach(workerTypeLabel).atTopTo(workerDataListLabel, 20)
			.atRightTo(workerTypeCombo, 5).atLeft(1);
			FormDatas.attach(registerDateTime).atTopTo(workerDataListLabel, 15)
			.atRight(12).withWidth(252).withHeight(25);
			FormDatas.attach(registerDateLabel).atTopTo(workerDataListLabel, 20).atLeftTo(workerTypeCombo)
					.atRightTo(registerDateTime, 5);
		}

		painter.reset();
		
		descriptionText = new Text(workerDataComposite, SWT.NONE | SWT.WRAP);
	    painter.add(descriptionText);
	    validator.applyValidator(descriptionText, 250);
	    //validator.applyValidator(descriptionText, "descriptionText1",
				//DecoratorType.ALL_BUT_NO_OTHER_REPEAT_CONSECUTIVE, true, 250);

		descriptionLabel = new Label(workerDataComposite, SWT.NONE | SWT.WRAP);
		painter.add(descriptionLabel, Percent.W15);
		activityCombo = new Combo(workerDataComposite, SWT.READ_ONLY);
		painter.add(activityCombo, Percent.W15);
		activityLabel = new Label(workerDataComposite, SWT.NONE | SWT.WRAP);
		painter.add(activityLabel, Percent.W15);
		
		
		 if( ((FormPagePainter) painter).getDimension()<1110 ){
	        	
			
			 FormDatas.attach(activityLabel).atTopTo(registerDateTime, 20)
				.atLeft(15);
			 
			 FormDatas.attach(activityCombo).atTopTo(registerDateTime, 15)
				.withWidth(middle).withHeight(23).atRight(15);
			 
			
			FormDatas.attach(descriptionLabel).atTopTo(activityCombo, 20)
							.atLeft(15);
			
			FormDatas.attach(descriptionText).atTopTo(activityCombo, 15)
					.withHeight(30).withWidth(middle-20).withHeight(45).atRight(15);
			
			
					
	    		
			}else{
				FormDatas.attach(activityCombo).atTopTo(workerTypeCombo, 15)
				.withWidth(240).withHeight(23).atRight(middle+9);
				
			FormDatas.attach(activityLabel).atTopTo(workerTypeCombo, 20)
				.atRightTo(activityCombo, 5).atLeft(1);
			
			FormDatas.attach(descriptionText).atTopTo(workerTypeCombo, 15)
					.withHeight(30).withWidth(230).withHeight(45).atRight(12);
			
			FormDatas.attach(descriptionLabel).atTopTo(workerTypeCombo, 20)
							.atRightTo(descriptionText, 5).atLeftTo(activityCombo, 5);
					
			}
		 
        
		
		
		
		
		allRoomLabel = new Label(workerDataComposite, SWT.NONE);
		allRoomLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(allRoomLabel).atTopTo(descriptionText, 10)
		.atLeft(15);
		
		
		Composite top = new Composite(workerDataComposite, SWT.BORDER);
		top.setLayout(new FormLayout());
		top.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(top).atTopTo(allRoomLabel, 5).atLeft(10).atRight(0);
		
		Composite left = new Composite(top, SWT.BORDER);
		left.setLayout(new FormLayout());
		left.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(left).atTopTo(top).atLeft(0).withWidth(80);
		
		final Button checked = new Button(left, SWT.CHECK);
		FormDatas.attach(checked).atLeftTo(left, 30).atTop(5).atBottom(5).withWidth(80);
		checked.addSelectionListener(new SelectionAdapter() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				asignedRooms.clear();
				if (checked.getSelection()) {
					asignedRooms = listRooms;
					for (int i = 0; i < listCheck.size(); i++) {
						listCheck.get(i).setSelection(true);
					}
				} else {
					//listRooms.clear();
					for (int i = 0; i < listCheck.size(); i++) {
						listCheck.get(i).setSelection(false);
					}
				}
			}

		});
		
		Composite right = new Composite(top, SWT.BORDER);
		right.setLayout(new FormLayout());
		right.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(right).atTopTo(top).atBottom(0).atRight().withWidth(140);
		
		roomSurface = new Label(right, SWT.NONE);
		roomSurface.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(roomSurface).atLeftTo(right, 10).atTopTo(right, 10).withWidth(140);
		
		Composite aviableReadingSetsComposite = new Composite(top, SWT.BORDER);
		aviableReadingSetsComposite.setLayout(new FormLayout());
		aviableReadingSetsComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(aviableReadingSetsComposite).atTopTo(top).atBottom(0).atRightTo(right).withWidth(140);
		
		aviableReadingSets = new Label(aviableReadingSetsComposite, SWT.NONE);
		aviableReadingSets.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(aviableReadingSets).atLeftTo(aviableReadingSetsComposite, 10).atTopTo(aviableReadingSetsComposite, 10).withWidth(140);
		
		Composite surfaceComposite = new Composite(top, SWT.BORDER);
		surfaceComposite.setLayout(new FormLayout());
		surfaceComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(surfaceComposite).atTopTo(top).atBottom(0).atRightTo(aviableReadingSetsComposite).withWidth(140);
		
		surface = new Label(surfaceComposite, SWT.NONE);
		surface.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(surface).atLeftTo(surfaceComposite, 10).atTopTo(surfaceComposite, 10).withWidth(140);
		
		
		Composite nameComposite = new Composite(top, SWT.BORDER);
		nameComposite.setLayout(new FormLayout());
		nameComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(nameComposite).atTopTo(top).atBottom(0).atLeftTo(left).atRightTo(surfaceComposite);
		
		roomName = new Label(nameComposite, SWT.NONE);
		roomName.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(roomName).atTopTo(nameComposite, 9).atLeftTo(left, 10);
		
		
		for (int i = 0; i < listRooms.size(); i++) {
			top = buildTable(top, listRooms.get(i));
		}
		
		
		/*
		 new TreeTableColumn(50, 0, "getRoomName"),
		//		new TreeTableColumn(30, 1, "getAvailableReadingSeatsToString"),
		//		new TreeTableColumn(20, 2, "getSurfaceToString") };
		 */
		
		
		//allRoomsTable = new CRUDTreeTable(workerDataComposite, SWT.NONE);
		//FormDatas.attach(allRoomsTable).atTopTo(allRoomLabel, 5).atLeft(0)
		//.atRight(middle + 5);
		
		//allRoomsTable.setEntityClass(Room.class);
/*
		Column checkedAllRooms = new Column("sright-arrow",
				parent.getDisplay(), new TreeColumnListener() {
					public void handleEvent(TreeColumnEvent event) {
						Room room = (Room) event.entity.getRow();
						
						boolean contain = false;
						for (int i = 0; i < asignedRooms.size(); i++) {
							if( asignedRooms.get(i).getRoomID() == room.getRoomID() ){
								contain = true;
							}
						}
						
						if (!contain) {
							asignedRooms.add(room);

							selectedRoomsTable.setRows(asignedRooms);
							selectedRoomsTable.refresh();
						} else {
							MessageDialogUtil.openInformation(
									Display.getCurrent().getActiveShell(),
									MessageUtil
											.unescape(cu.uci.abos.core.l10n.AbosMessages
													.get().MESSAGE_INFORMATION),
													MessageUtil
													.unescape(AbosMessages
															.get().ROOM_IS_ASIGNED),
									null);
						}
					}

				});
		allRoomsTable.addActionColumn(checkedAllRooms);
		*/
		//TreeTableColumn columnsAllRooms[] = {
		//		new TreeTableColumn(50, 0, "getRoomName"),
		//		new TreeTableColumn(30, 1, "getAvailableReadingSeatsToString"),
		//		new TreeTableColumn(20, 2, "getSurfaceToString") };

		//allRoomsTable.createTable(columnsAllRooms);
		

		//allRoomsTable.getPaginator().setPageSize(10);
		//allRoomsTable.addPageChangeListener(new PageChangeListener() {
			//@Override
			//public void pageChanged(final PageChangedEvent event) {

				//if (event.sortData != null) {
				//	direction = event.sortData.sortDirection;
				//	switch (event.sortData.columnIndex) {
				//	case 0:
				//		orderByString = "roomName";
					//	break;
					//case 1:
					//	orderByString = "availableReadingSeats";
					//	break;
					//case 2:
					//	orderByString = "surface";
					//	break;
					//}
				//}
				//searchRooms(event.currentPage - 1, event.pageSize);
				
			//}

		//});
		//FIXME BORRAR CODIGO COMENTARIADO

		//selectedRoomsLabel = new Label(workerDataComposite, SWT.NONE);
		//FormDatas.attach(selectedRoomsLabel).atTopTo(descriptionText, 10)
		//.atLeft(middle + 35);
		
		//selectedRoomsTable = new CRUDTreeTable(workerDataComposite, SWT.NONE);
		//FormDatas.attach(selectedRoomsTable).atTopTo(selectedRoomsLabel, 5)
		//.atLeft(middle + 5).atRight(0);
		//getMapControls().put("selectedRoomsTable", selectedRoomsTable);

		//selectedRoomsTable.setEntityClass(Room.class);
		//selectedRoomsTable.setDelete(true);

		//TreeTableColumn columnsSelectedRooms[] = {
		//		new TreeTableColumn(50, 0, "getRoomName"),
		//		new TreeTableColumn(30, 1, "getAvailableReadingSeatsToString"),
		//		new TreeTableColumn(20, 2, "getSurfaceToString") };

		//selectedRoomsTable.createTable(columnsSelectedRooms);
		

		//selectedRoomsTable.addDeleteListener(new TreeColumnListener() {
			//public void handleEvent(final TreeColumnEvent event) {

				//Room entity = (Room) event.entity.getRow();

				//asignedRooms.remove(entity);
				//selectedRoomsTable.setRows(asignedRooms);
				//selectedRoomsTable.refresh();
				//event.performDelete = true;

			//}
		//});

		if (worker == null) {
			cancelBtn = new Button(workerDataComposite, SWT.PUSH);
			FormDatas.attach(cancelBtn).atTopTo(top, 5).atRight(0).withHeight(23);
			
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
								registerWorker.notifyListeners(
											SWT.Dispose, new Event());									
							}						
						}					
					} );	
				}
			});
			/*
			cancelBtn.addSelectionListener(new SelectionAdapter() {
				
				private static final long serialVersionUID = 1L;

				@Override
				public void widgetSelected(SelectionEvent e) {
					MessageDialogUtil.openConfirm(
							Display.getCurrent().getActiveShell(),
							MessageUtil
									.unescape(cu.uci.abos.core.l10n.AbosMessages
											.get().MESSAGE_QUESTION),
							MessageUtil
									.unescape(cu.uci.abos.core.l10n.AbosMessages
											.get().MSG_WARN_DELET_DATA),
							new DialogCallback() {
								
								private static final long serialVersionUID = 1L;

								@Override
								public void dialogClosed(int returnCode) {
									if (returnCode == 0) {
										registerWorker.notifyListeners(
												SWT.Dispose, new Event());
									}
								}
							});
				}
			});
			
			*/
			
			saveBtn = new Button(workerDataComposite, SWT.PUSH);
			FormDatas.attach(saveBtn).atTopTo(top, 5)
					.atRightTo(cancelBtn, 5).withHeight(23);

			saveBtn.addSelectionListener(new SelectionAdapter() {
				private static final long serialVersionUID = 2615553092700551346L;

				@Override
				public void widgetSelected(SelectionEvent e) {
					if (getValidator().decorationFactory
							.isRequiredControlDecorationIsVisible()) {
						RetroalimentationUtils.showErrorShellMessage(
								//register, 
								MessageUtil
								.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
					} else {
						if (getValidator().decorationFactory
								.AllControlDecorationsHide()) {
							
							if(Auxiliary.dateLessOrEqualToday(registerDateTime, MessageUtil
									.unescape(AbosMessages.get().REGISTER_DATE_LESS_OR_EQUAL_CURRENT_DATE))){
							
									
							if (person != null) {
								if(Auxiliary.getDate(registerDateTime).after(person.getBirthDate())){
								//if(person.getBirthDate().before(Auxiliary.getDate(registerDateTime))){
								
							Long personId = person.getPersonID();
							
							Nomenclator workerType;
							if (UiUtils.getSelected(workerTypeCombo) == null) {
								workerType = null;
							} else {
								workerType = (Nomenclator) UiUtils.getSelected(workerTypeCombo);
							}
							
							Nomenclator workerActivity;
							if (UiUtils.getSelected(activityCombo) == null) {
								workerActivity = null;
							} else {
								workerActivity = (Nomenclator) UiUtils.getSelected(activityCombo);
							}
							
							String description = descriptionText.getText().replaceAll(" +", " ").trim();

							int fromYear = registerDateTime.getYear() - 1900;
							int fromMonth = registerDateTime.getMonth();
							int fromDay = registerDateTime.getDay();
							@SuppressWarnings("deprecation")
							Date registerDate = new Date(fromYear, fromMonth,
									fromDay);

							Person personWorker = personService
									.findOnePerson(personId);
							Worker worker = new Worker();
							worker.setRegisterDate(registerDate);
							worker.setObservation(description);
							worker.setWorkerType(workerType);
							worker.setPerson(personWorker);
							worker.setWorkerActivity(workerActivity);
							worker.setRooms(asignedRooms);
							worker.setLibrary(getPerson().getLibrary());

							Worker WorkerSaved = ((LibraryViewController) controller)
									.saveWorker(worker);

							registerWorker.notifyListeners(SWT.Dispose, new Event());
							contributorService.selectContributor("viewWorker", WorkerSaved, registerWorker, contributorService);
							}else{
								registerDateTime.setBackground(new Color(registerDateTime.getDisplay(), 255, 204, 153));
								RetroalimentationUtils
								.showErrorShellMessage(MessageUtil
										.unescape(AbosMessages
												.get().REGISTER_DATE_MORE_BIRTHDATE));
							}
						}else{
							
							RetroalimentationUtils.showErrorShellMessage(
									//register, 
									MessageUtil
									.unescape(AbosMessages.get().SHOULD_SELECT_PERSON_AS_WORKER));
							
						}
							
						}
						} else {
							RetroalimentationUtils.showErrorMessage(
									//register, 
									MessageUtil
									.unescape(cu.uci.abos.core.l10n.AbosMessages
											.get().MSG_ERROR_INCORRECT_DATA));

							
							//FIXME BORRAR CODIGO COMENTARIADO
						}
				}
						/*
					} else {
						MessageDialogUtil.openError(
								Display.getCurrent().getActiveShell(),
								MessageUtil
										.unescape(cu.uci.abos.core.l10n.AbosMessages
												.get().MESSAGE_ERROR),
								MessageUtil.unescape(AbosMessages.get().SHOULD_SELECT_PERSON),
								null);
					}
					*/

				}
			});
			
			Label espacio1 = new Label(workerDataComposite, SWT.NORMAL);
			FormDatas.attach(espacio1).atTopTo(cancelBtn);
			//espacio1.setText("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
			
			//saveBtn.getShell().setDefaultButton(saveBtn);
		} 
		else {
			Label espacio = new Label(workerDataComposite, SWT.NORMAL);
			FormDatas.attach(espacio).atTopTo(descriptionLabel, 40);
		}
		//allRoomsTable.getPaginator().goToFirstPage();

		initialize(workerTypeCombo, ((LibraryViewController) controller)
				.findNomenclatorByCode(library.getLibraryID(), Nomenclator.WORKER_TYPE));
		validator.applyValidator(workerTypeCombo, "workerTypeCombo",
				DecoratorType.REQUIRED_FIELD, false);
		
		initialize(activityCombo, ((LibraryViewController) controller)
				.findNomenclatorByCode(library.getLibraryID(), Nomenclator.WORKER_ACTIVITY));
		validator.applyValidator(activityCombo, "activityComboRequired",
				DecoratorType.REQUIRED_FIELD, false);
		
		loadWorkerData();
		l10n();
		
		return parent;
	}

	public void loadWorkerData(){
		//initialize(workerTypeCombo, ((LibraryViewController) controller)
			//	.findNomenclatorByCode(library.getLibraryID(), Nomenclator.WORKER_TYPE));
		
		
		
		if (worker != null) {
			
			workerDataComposite.setVisible(true);
			
			UiUtils.selectValue(workerTypeCombo, worker.getWorkerType());
			UiUtils.selectValue(activityCombo, worker.getWorkerActivity());
			
			getDescriptionText().setText(worker.getObservation());
			java.util.Date utilDate = new java.util.Date(worker.getRegisterDate().getTime());
			int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(utilDate));
			int month = Integer.parseInt(new SimpleDateFormat("MM").format(utilDate));
			int day = Integer.parseInt(new SimpleDateFormat("dd").format(utilDate));

			getRegisterDateTime().setDate(year, month - 1, day);
			
			//getSelectedRoomsTable().setRows(getAsignedRooms());
			//getSelectedRoomsTable().refresh();
			
			//for (int i = 0; i < asignedRooms.size(); i++) {
			  	
			//}
			
			associate();
			showPerson();
			
		}
	}
	
	//public CRUDTreeTable getSelectedRoomsTable() {
	//	return selectedRoomsTable;
	//}

	//public void setSelectedRoomsTable(CRUDTreeTable selectedRoomsTable) {
	//	this.selectedRoomsTable = selectedRoomsTable;
	//}

	public Combo getWorkerTypeCombo() {
		return workerTypeCombo;
	}

	public void setWorkerTypeCombo(Combo workerTypeCombo) {
		this.workerTypeCombo = workerTypeCombo;
	}

	public Combo getActivityCombo() {
		return activityCombo;
	}

	public void setActivityCombo(Combo activityCombo) {
		this.activityCombo = activityCombo;
	}

	public Text getDescriptionText() {
		return descriptionText;
	}

	public void setDescriptionText(Text descriptionText) {
		this.descriptionText = descriptionText;
	}

	public DateTime getRegisterDateTime() {
		return registerDateTime;
	}
	//FIXME BORRAR CODIGO COMENTARIADO
	public void setRegisterDateTime(DateTime registerDateTime) {
		this.registerDateTime = registerDateTime;
	}
/*
	public void loadWorkerType() {
		List<Nomenclator> listWorkerType = ((LibraryViewController) controller)
				.findNomenclatorByCode(Nomenclator.WORKER_TYPE);
		String[] comboStrings = new String[listWorkerType.size()];
		List<Nomenclator> type = new LinkedList<Nomenclator>();
		for (int i = 0; i < listWorkerType.size(); i++) {
			Nomenclator workerType = listWorkerType.get(i);
			type.add(workerType);
			comboStrings[i] = workerType.getNomenclatorName();
		}
		workerTypeCombo.setData(type);
		workerTypeCombo.setItems(comboStrings);

	}
	
	public void loadWorkerActivity() {
		List<Nomenclator> listWorkerActivity = ((LibraryViewController) controller)
				.findNomenclatorByCode(Nomenclator.ACTIVITY);
		String[] comboStrings = new String[listWorkerActivity.size()];
		List<Nomenclator> activity = new LinkedList<Nomenclator>();
		for (int i = 0; i < listWorkerActivity.size(); i++) {
			Nomenclator workerActivity = listWorkerActivity.get(i);
			activity.add(workerActivity);
			comboStrings[i] = workerActivity.getNomenclatorName();
		}
		activityCombo.setData(activity);
		activityCombo.setItems(comboStrings);

	}
*/
	//private void searchRooms(int page, int size) {
		//allRoomsTable.clearRows();
		//Page<Room> list = ((LibraryViewController) controller)
		//		.getAllManagementLibraryViewController().getRoomService()
		//		.findAll(library, null, page, size, size, orderByString);
		//allRoomsTable.getPaginator().setTotalElements(
		//		(int) list.getTotalElements());
		//allRoomsTable.setRows(list.getContent());
		//allRoomsTable.refresh();

	//}

	@Override
	public String getID() {
		return null;
	}

	
	//FIXME BORRAR CODIGO COMENTARIADO
	@Override
	public Control getControl(String arg0) {
		return null;
	}

	//public Map<String, Control> getMapControls() {
	//	return controls;
	//}

	//public void setMapControls(Map<String, Control> controls) {
	//	this.controls = controls;
	//}

	//public Map<String, TableItem> getControlsTableItem() {
	//	return controlsTableItem;
	//}

	//public void setControlsTableItem(Map<String, TableItem> controlsTableItem) {
	//	this.controlsTableItem = controlsTableItem;
	//}

	private void searchPerson(int page, int size) {
		tablePerson.clearRows();
		Library library = (Library) SecurityUtils
				.getService().getPrincipal()
				.getByKey("library");
		Page<Person> list = personService.findAll(library, searchTextConsult,
				page, size, size, orderByStringPerson);
		tablePerson.setTotalElements((int) list.getTotalElements());
		tablePerson.setRows(list.getContent());
		tablePerson.refresh();
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

	public void Search() {
		viewPersonDataComposite.setVisible(false);
		tablePersonComposite.setVisible(true);
		tablePerson.clearRows();
		searchTextConsult = (searchText.getText() != "") ? searchText.getText()
				: null;
		orderByStringPerson = "firstName";
		direction = 1024;
		tablePerson.getPaginator().goToFirstPage();
		
		if (tablePerson.getRows().isEmpty()) {
			RetroalimentationUtils.showInformationMessage(register, cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NO_COINCIDENCES_FOUND);
		} 
		
	}

	public void associate() {
		
		fullName.setText(getPerson().getFullName());
		Image image;
		if (getPerson().getPhoto() == null) {
			image = new Image(Display.getDefault(), AbosImageUtil
					.loadImage(null, Display.getCurrent(),
							"abcdconfig/resources/photo.png", false)
					.getImageData().scaledTo(100, 100));
		} else {
			image = getPerson().getPhoto().getImage();
		}
		pictureLabel.setImage(image);
		user.setText((getPerson().getUser() != null) ? getPerson().getUser().getUsernameToString(): "-");
		birthday.setText((getPerson().getBirthDate() != null) ? new SimpleDateFormat(
				"dd-MM-yyyy").format(getPerson().getBirthDate()) : "-");

		identification.setText(getPerson().getDNI());
		sex.setText(getPerson().getSex().getNomenclatorName());

		viewPersonDataComposite.setVisible(true);
		//if (worker != null) {
			//ajustRezise(viewPersonDataComposite, 115);
		//}else{
		ajustRezise(viewPersonDataComposite, 175);
		//}
		refresh(personDataComposite.getParent().getShell());
		
		
		
		//ajustRezise(personDataComposite, getHeightSearchPersonComposite() + getHeightTablePersonComposite());
		//refresh(personDataComposite.getParent().getShell());
		
		

		viewPersonDataComposite.layout(true, true);
		viewPersonDataComposite.redraw();
		viewPersonDataComposite.update();

		searchPersonComposite.setVisible(false);
		tablePersonComposite.setVisible(false);

	}

	public void showPerson(){
		heightTablePerson = tablePerson.getSize().y;
		ajustRezise(personDataComposite, getHeightViewPersonDataComposite() + 5);
		refresh(personDataComposite.getParent().getShell());
		
		//refresh();
	}
	
	public void dissociate() {
		if(worker==null){
		workerDataComposite.setVisible(false);
		//tablePersonComposite.get
		}
		viewPersonDataComposite.setVisible(false);
		searchPersonComposite.setVisible(true);
		tablePersonComposite.setVisible(true);
		person = null;
		
		heightTablePersonComposite = heightTablePerson;
		ajustRezise(personDataComposite, getHeightSearchPersonComposite() + getHeightTablePersonComposite()+15 );
		refresh(personDataComposite.getParent().getShell());
		
		refresh();
		
	}
	//FIXME BORRAR CODIGO COMENTARIADO
	
	@Override
	public void l10n() {
		tablePerson.l10n();
		//allRoomsTable.l10n();
		//selectedRoomsTable.l10n();
		if (worker == null) {
			registerWorkerLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_REGISTER_WORKER));
		}else{
			registerWorkerLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_UPDATE_WORKER));
		}
		
		roomSurface.setText(MessageUtil.unescape(AbosMessages.get().LABEL_USER_PC_QUANTITY));
		surface.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SURFACE));
		allRoomLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_ALL_ROOMS));
		roomName.setText(MessageUtil.unescape(AbosMessages.get().LABEL_NAME));
		aviableReadingSets.setText(MessageUtil.unescape(AbosMessages.get().LABEL_READING_SETS));
		
		//selectedRoomsLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SELECTED_ROOMS));
		
		//personDataLabel
				//.setText(MessageUtil.unescape(AbosMessages.get().VIEW_PERSON));
		personGroup
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_PERSON_DATA));
		fullNameLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_NAME_AND_LASTNAME)
						+ " : ");
		userLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_USER)
				+ " : ");
		birthdayLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_BIRTHDAY)
						+ " : ");
		sexLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_SEX)
				+ " : ");
		identificationLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION)
						+ " : ");
		unassociate
				.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_UNASSOCIATE));

		//indicateLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_PERSON_DATA));
		personData.setText(MessageUtil.unescape(AbosMessages.get().LABEL_PERSON_DATA));
		
		sugestLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_INDICATE_PERSON_AS_WORKER));
		
		searchText
				.setMessage(MessageUtil.unescape(AbosMessages.get().LABEL_NAME)
						+ " | "
						+ MessageUtil.unescape(AbosMessages.get().LABEL_LAST_NAME)
						+ " | "
						+ MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION));
		
		searchButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_SEARCH));
		
		workerDataListLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_WORKER_DATA));
		workerTypeLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_WORKER_TYPE));
		descriptionLabel
				.setText(MessageUtil.unescape(AbosMessages.get().LABEL_OBSERVATIONS));
		activityLabel.setText(MessageUtil.unescape(AbosMessages.get().ACTIVITY));
		
		registerDateLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_REGISTER_DATE));
		/*
		allRoomsTable.setColumnHeaders(Arrays.asList(
				MessageUtil.unescape(AbosMessages.get().LABEL_NAME),
				MessageUtil.unescape(AbosMessages.get().LABEL_READING_SETS),
				MessageUtil.unescape(AbosMessages.get().LABEL_SURFACE)));
*/
		tablePerson.setColumnHeaders(Arrays.asList(MessageUtil
				.unescape(AbosMessages.get().LABEL_NAME_AND_LASTNAME),
				MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFICATION),
				MessageUtil.unescape(AbosMessages.get().LABEL_SEX)));
/*
		selectedRoomsTable.setColumnHeaders(Arrays.asList(
				MessageUtil.unescape(AbosMessages.get().LABEL_NAME),
				MessageUtil.unescape(AbosMessages.get().LABEL_READING_SETS),
				MessageUtil.unescape(AbosMessages.get().LABEL_SURFACE)));
		*/
		if (worker == null) {
			saveBtn.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACEPT));
			cancelBtn
					.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CANCEL));
		}
		
		if(viewWorkerFragment!=null){
			viewWorkerFragment.l10n();
			}
		
		//FIXME BORRAR CODIGO COMENTARIADO
		workerDataComposite.getParent().getParent().layout(true, true);
		workerDataComposite.getParent().getParent().redraw();
		workerDataComposite.getParent().getParent().update();
		
		//initialize(workerTypeCombo, ((LibraryViewController) controller)
			//	.findNomenclatorByCode(Nomenclator.WORKER_TYPE));
		
		//initialize(activityCombo, ((LibraryViewController) controller)
			//	.findNomenclatorByCode(Nomenclator.WORKER_ACTIVITY));
		
		//workerDataComposite.getParent().getParent().layout(true, true);
		//workerDataComposite.getParent().getParent().redraw();
		//workerDataComposite.getParent().getParent().update();
		
	}
	
	public Composite buildTable(Composite downTo, final Room room){
		Composite top1 = new Composite(workerDataComposite, SWT.NONE);
		top1.setLayout(new FormLayout());
		top1.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(top1).atTopTo(downTo).atLeft(10).atRight(0);
		
		Composite left1 = new Composite(top1, SWT.BORDER);
		left1.setLayout(new FormLayout());
		FormDatas.attach(left1).atTopTo(top1).atLeft(0).withWidth(81).withHeight(30);
		
		final Button checked1 = new Button(left1, SWT.CHECK);
		FormDatas.attach(checked1).atLeftTo(left1, 30).atTop(5).atBottom(5).withWidth(80);
		checked1.setData(room);
		listCheck.add(checked1);
		for (int i = 0; i < asignedRooms.size(); i++) {
			if(asignedRooms.get(i).getRoomID()==room.getRoomID()){
				checked1.setSelection(true);
			}
		}
		
		if(asignedRooms.contains(room)){
			checked1.setSelection(true);
		}
		//if(asignedRooms.contains(room)){
		//	checked1.setSelection(true);
		//}
		
		checked1.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (checked1.getSelection()) {
					asignedRooms.add(room);
				} else {
					for (int i = 0; i < asignedRooms.size(); i++) {
						if(asignedRooms.get(i).getRoomID()==room.getRoomID()){
							asignedRooms.remove(i);
						}
					}
					
				}
			}

		});
		
		Composite right1 = new Composite(top1, SWT.BORDER);
		right1.setLayout(new FormLayout());
		FormDatas.attach(right1).atTopTo(top1).atRight().atBottom(0).withWidth(141).withHeight(25);
		
		Label roomUserPc = new Label(right1, SWT.NONE);
		roomUserPc.setText(room.getUserPcQuantityToString());
		FormDatas.attach(roomUserPc).atLeftTo(right1, 20).atTopTo(right1, 10).withWidth(140);
		
		Composite aviableReadingSetsComposite1 = new Composite(top1, SWT.BORDER);
		aviableReadingSetsComposite1.setLayout(new FormLayout());
		FormDatas.attach(aviableReadingSetsComposite1).atTopTo(top1).atBottom(0).atRightTo(right1).withWidth(140);
		
		Label aviableReadingSets = new Label(aviableReadingSetsComposite1, SWT.NONE);
		aviableReadingSets.setText(room.getAvailableReadingSeatsToString());
		FormDatas.attach(aviableReadingSets).atLeftTo(aviableReadingSetsComposite1, 20).atTopTo(aviableReadingSetsComposite1, 10).withWidth(140);
		
		Composite surfaceComposite1 = new Composite(top1, SWT.BORDER);
		surfaceComposite1.setLayout(new FormLayout());
		FormDatas.attach(surfaceComposite1).atTopTo(top1).atBottom(0).atRightTo(aviableReadingSetsComposite1).withWidth(140);
		
		Label surface = new Label(surfaceComposite1, SWT.NONE);
		surface.setText(room.getSurfaceToString());
		FormDatas.attach(surface).atLeftTo(surfaceComposite1, 20).atTopTo(surfaceComposite1, 10).withWidth(140);
		
		
		
		Composite nameComposite = new Composite(top1, SWT.BORDER);
		nameComposite.setLayout(new FormLayout());
		FormDatas.attach(nameComposite).atTopTo(top1).atBottom(0).atLeftTo(left1).withWidth(350).atRightTo(aviableReadingSetsComposite1);
		
		Label roomName = new Label(nameComposite, SWT.NONE);
		roomName.setText(room.getRoomName());
		FormDatas.attach(roomName).atTopTo(nameComposite, 9).atLeftTo(left1, 20);
		
		return top1;
	}
	
	
	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}
	
	public Composite getParent() {
		return parent;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public List<Room> getAsignedRooms() {
		return asignedRooms;
	}

	public void setAsignedRooms(List<Room> asignedRooms) {
		this.asignedRooms = asignedRooms;
	}
	
	public int getHeightTablePerson() {
		return heightTablePerson;
	}

	public void setHeightTablePerson(int heightTablePerson) {
		this.heightTablePerson = heightTablePerson;
	}

	public int getHeightPersonDataComposite() {
		return personDataComposite.getSize().y;
	}

	public int getHeightViewPersonDataComposite() {
		return viewPersonDataComposite.getSize().y;
	}

	public int getHeightSearchPersonComposite() {
		return searchPersonComposite.getSize().y;
	}

	public int getHeightTablePersonComposite() {
		return tablePersonComposite.getSize().y;
	}

	public int getHeightWorkerDataComposite() {
		return workerDataComposite.getSize().y;
	}
	
	public ValidatorUtils getValidator() {
		return validator;
	}

	public void setValidator(ValidatorUtils validator) {
		this.validator = validator;
	}
	
	public Composite getRegister() {
		return register;
	}

	public void setRegister(Composite register) {
		this.register = register;
	}
}
