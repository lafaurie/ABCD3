package cu.uci.abcd.acquisition.ui;

import java.util.HashMap;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;

import cu.uci.abcd.acquisition.ui.controller.AllManagementController;
import cu.uci.abcd.acquisition.ui.updateArea.EventButtonSave;
import cu.uci.abcd.acquisition.ui.updateArea.ViewRegisterAcquisitionArea;
import cu.uci.abcd.adquisition.l10n.AbosMessages;
import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.widget.repeatable.field.util.FieldStructure;
import cu.uci.abos.widget.template.component.TemplateCompound;

public class RegisterLogAcquisition extends ContributorPage {

	private ViewRegisterAcquisitionArea classView;
	private Button cancelButton;

	public RegisterLogAcquisition() {
		super();
		properties = new HashMap<String, Object>();
		properties.put(NOT_SCROLLED, Boolean.TRUE);
	}

	private TemplateCompound compound;

	public static final String dataBaseNameAcquisition = "Registro_De_Adquisicion";
	public static final String dataBaseNameCatalogin = "marc21";

	protected String mensaje = "";
	private Composite father;
	private FieldStructure fieldStructure;
	private LoanObject loanObject;
	private List<Nomenclator> acquisitionCoinType;
	private List<Nomenclator> acquisitionVia;
	private Library library;
	private Button btNew;
	private ContributorService contributorService;

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().CONTRIBUTOR_REGISTER_ACQUISITION);
	}

	public int order() {
		return 0;
	}

	@Override
	public String getID() {
		return "addLogAcquisitionID";
	}

	public String containerMenu() {
		return "Adquisición";
	}

	@Override
	public Control createUIControl(Composite parent) {
		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");

		acquisitionCoinType = ((AllManagementController) controller).getSuggestion().findAllNomenclators(library.getLibraryID(), Nomenclator.COIN_TYPE);
		// acquisitionVia=
		// ((AllManagementController)controller).getSuggestion().findAllNomenclators(library.getLibraryID(),
		// Nomenclator.ad);

		contributorService = getContributorService();

		addComposite(parent);

		father = new Composite(parent, 0);
		father.setLayout(new FormLayout());

		FormDatas.attach(father).atRight(0).atLeft(0);
		father.setData(RWT.CUSTOM_VARIANT, "gray_background");
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");

		if (controller != null) {
			try {
				AllManagementController cont = (AllManagementController) controller;

				IJisisDataProvider service = cont.getAcquisition().getService();

				compound = new TemplateCompound(father, 0, dataBaseNameAcquisition, service);

				compound.createComponent();

				compound.setComboLabel(MessageUtil.unescape(AbosMessages.get().LABEL_ACQUISITION_WAY));

				compound.setRecordLabel("");

				compound.setLayout(new FormLayout());
				compound.layout(true, true);

				addComposite(compound);

				btNew = new Button(father, SWT.PUSH);
				btNew.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW));
				FormDatas.attach(btNew).atTopTo(compound, 10).atLeft(30);

				// btNew.addListener(listener);
				btNew.addSelectionListener(new SelectionAdapter() {

					private static final long serialVersionUID = 1L;

					public void widgetSelected(SelectionEvent e) {

						notifyListeners(SWT.Dispose, new Event());
						contributorService.selectContributor("addLogAcquisitionID");
					}
				});

				compound.getButtonSave().addListener(SWT.Selection, new EventButtonSave(compound, controller, library, dataBaseNameAcquisition, dataBaseNameCatalogin, this, contributorService, father));
			
				
				
				/*
				 * {
				 * 
				 * 
				 * 
				 * private static final long serialVersionUID = 1L;
				 * 
				 * @Override public void widgetSelected(SelectionEvent arg0) {
				 * 
				 * boolean validate = compound.validateRecord();
				 * 
				 * if(validate){ ArrayList<FieldStructure> childrens =
				 * compound.getChildrens();
				 * 
				 * if(controller != null){
				 * 
				 * IRegistrationManageAcquisitionService dataBaseManager =
				 * ((AllManagementController)controller).getAcquisition();
				 * 
				 * int fieldCount = childrens.size(); IRecord record =
				 * Record.createRecord();
				 * 
				 * try { int fieldNotEmpty = 0; for (int i = 0; i < fieldCount;
				 * i++) { FieldStructure fieldStructure = childrens.get(i); int
				 * occurrenceCount = 0; int tag = fieldStructure.getTag();
				 * 
				 * if(fieldStructure.isRepeatableField()){ boolean terminate =
				 * false;
				 * 
				 * for (int j = i+1; j < fieldCount && terminate == false; j++)
				 * { int tagj = -1; FieldStructure fieldStructurej =
				 * childrens.get(j);
				 * 
				 * if(fieldStructurej.getSubfields().isEmpty()) tagj =
				 * fieldStructurej.getTag(); else tagj =
				 * fieldStructurej.getSubfields().get(0).getTag();
				 * 
				 * if(tagj == tag) occurrenceCount++; else terminate = true; } }
				 * 
				 * if(occurrenceCount > 0){ int occurrenceBegin = 0; IField
				 * field = null;
				 * 
				 * for (int j = 0; j < occurrenceCount+1; j++) { FieldStructure
				 * fieldStructureSave = childrens.get(i+j); int subFieldsCount =
				 * fieldStructureSave.getSubfields().size();
				 * 
				 * if(subFieldsCount > 0){ ArrayList<Subfield> subfields = new
				 * ArrayList<Subfield>(); int subFieldWithValueCount = 0;
				 * 
				 * for (int k = 0; k < subFieldsCount; k++) { SubFieldStructure
				 * subFieldStructure = fieldStructureSave.getSubfields().get(k);
				 * String value = "";
				 * 
				 * if(subFieldStructure.getControl() instanceof Text) value =
				 * ((Text)subFieldStructure.getControl()).getText(); else
				 * if(subFieldStructure.getControl() instanceof Combo) value =
				 * ((Combo)subFieldStructure.getControl()).getText(); else
				 * if(fieldStructure.getControl() instanceof MyDateTime) value =
				 * ((MyDateTime)fieldStructure.getControl()).getText(); else
				 * if(fieldStructure.getControl() instanceof MyDate) value =
				 * ((MyDate)fieldStructure.getControl()).getText(); else
				 * if(fieldStructure.getControl() instanceof MyTime) value =
				 * ((MyTime)fieldStructure.getControl()).getText();
				 * 
				 * String code = subFieldStructure.getSubFieldCode(); char
				 * realCode = code.charAt(code.length()-1);
				 * 
				 * if(!subFieldStructure.getControl().isDisposed() &&
				 * !value.equals("") && !value.equals(null) &&
				 * !value.equals("Seleccione")){ ISubfield subfield = new
				 * Subfield(realCode, value); subfields.add((Subfield)subfield);
				 * subFieldWithValueCount++; } }
				 * 
				 * if(subFieldWithValueCount > 0){ StringOccurrence occurrence =
				 * new StringOccurrence(); Subfield[] subfieldsArray =
				 * subfields.toArray(new Subfield[subfields.size()]);
				 * occurrence.setSubfields(subfieldsArray);
				 * 
				 * if(0==j){ field = new Field(tag,
				 * Global.FIELD_TYPE_ALPHANUMERIC);
				 * field.setOccurrence(occurrenceBegin, occurrence);
				 * record.addField(field); fieldNotEmpty++; } else{ int
				 * occurrenceCountfieldLast = field.getOccurrenceCount();
				 * field.setOccurrence(occurrenceCountfieldLast, occurrence); }
				 * } } else{ String value = "";
				 * 
				 * if(fieldStructureSave.getControl() instanceof Text) value =
				 * ((Text)fieldStructureSave.getControl()).getText(); else
				 * if(fieldStructureSave.getControl() instanceof Combo) value =
				 * ((Combo)fieldStructureSave.getControl()).getText(); else
				 * if(fieldStructure.getControl() instanceof MyDateTime) value =
				 * ((MyDateTime)fieldStructure.getControl()).getText(); else
				 * if(fieldStructure.getControl() instanceof MyDate) value =
				 * ((MyDate)fieldStructure.getControl()).getText(); else
				 * if(fieldStructure.getControl() instanceof MyTime) value =
				 * ((MyTime)fieldStructure.getControl()).getText();
				 * 
				 * if(!fieldStructureSave.getControl().isDisposed() &&
				 * !value.equals("") && !value.equals(null) &&
				 * !value.equals("Seleccione")){
				 * 
				 * if(0==j){ field = new Field(tag,
				 * Global.FIELD_TYPE_ALPHANUMERIC); field.setFieldValue(value);
				 * record.addField(field); fieldNotEmpty++; } else{ int
				 * occurrenceCountfieldLast = field.getOccurrenceCount();
				 * field.setOccurrence(occurrenceCountfieldLast, value); } } }
				 * occurrenceBegin++; } i+=occurrenceCount; } else{ int
				 * subFieldsCount = fieldStructure.getSubfields().size(); IField
				 * field;
				 * 
				 * if(subFieldsCount > 0){ ArrayList<Subfield> subfields = new
				 * ArrayList<Subfield>(); int subFieldWithValueCount = 0;
				 * 
				 * for (int j = 0; j < subFieldsCount; j++) { SubFieldStructure
				 * subFieldStructure = fieldStructure.getSubfields().get(j);
				 * String value = "";
				 * 
				 * if(subFieldStructure.getControl() instanceof Text) value =
				 * ((Text)subFieldStructure.getControl()).getText(); else
				 * if(subFieldStructure.getControl() instanceof Combo) value =
				 * ((Combo)subFieldStructure.getControl()).getText(); else
				 * if(fieldStructure.getControl() instanceof MyDateTime) value =
				 * ((MyDateTime)fieldStructure.getControl()).getText(); else
				 * if(fieldStructure.getControl() instanceof MyDate) value =
				 * ((MyDate)fieldStructure.getControl()).getText(); else
				 * if(fieldStructure.getControl() instanceof MyTime) value =
				 * ((MyTime)fieldStructure.getControl()).getText();
				 * 
				 * String code = subFieldStructure.getSubFieldCode(); char
				 * realCode = code.charAt(code.length()-1);
				 * 
				 * if(!subFieldStructure.getControl().isDisposed() &&
				 * !value.equals("") && !value.equals(null) &&
				 * !value.equals("Seleccione")){ ISubfield subfield = new
				 * Subfield(realCode, value); subfields.add((Subfield)subfield);
				 * subFieldWithValueCount++;
				 * 
				 * } }
				 * 
				 * if(subFieldWithValueCount > 0){ StringOccurrence occurrence =
				 * new StringOccurrence(); Subfield[] subfieldsArray =
				 * subfields.toArray(new Subfield[subfields.size()]);
				 * occurrence.setSubfields(subfieldsArray);
				 * 
				 * field = new Field(tag, Global.FIELD_TYPE_ALPHANUMERIC);
				 * field.setOccurrence(occurrenceCount, occurrence);
				 * record.addField(field); fieldNotEmpty++; } } else{ String
				 * value = "";
				 * 
				 * if(fieldStructure.getControl() instanceof Text) value =
				 * ((Text)fieldStructure.getControl()).getText(); else
				 * if(fieldStructure.getControl() instanceof Combo) value =
				 * ((Combo)fieldStructure.getControl()).getText(); else
				 * if(fieldStructure.getControl() instanceof MyDateTime) value =
				 * ((MyDateTime)fieldStructure.getControl()).getText(); else
				 * if(fieldStructure.getControl() instanceof MyDate) value =
				 * ((MyDate)fieldStructure.getControl()).getText(); else
				 * if(fieldStructure.getControl() instanceof MyTime) value =
				 * ((MyTime)fieldStructure.getControl()).getText();
				 * 
				 * if(!fieldStructure.getControl().isDisposed() &&
				 * !value.equals("") && !value.equals(null) &&
				 * !value.equals("Seleccione")){ field = new Field(tag,
				 * Global.FIELD_TYPE_ALPHANUMERIC); field.setFieldValue(value);
				 * record.addField(field);
				 * 
				 * fieldNotEmpty++; } } } }
				 * 
				 * if(fieldNotEmpty > 0){
				 * 
				 * 
				 * ((AllManagementController)controller).getAcquisition().
				 * registerAcquisition(record, library.getIsisDefHome());
				 * 
				 * 
				 * Record lastRecord =
				 * dataBaseManager.getLastRecord(DataBaseName); loanObject = new
				 * LoanObject(); for (int i = 0; i < acquisitionCoinType.size();
				 * i++) {
				 * if(lastRecord.getField(22).getStringFieldValue().equals
				 * (acquisitionCoinType.get(i).getNomenclatorName()))
				 * loanObject.
				 * setAcquisitionCoinType(acquisitionCoinType.get(i));
				 * 
				 * } for (int i = 0; i < acquisitionVia.size(); i++) {
				 * if(lastRecord
				 * .getField(22).getStringFieldValue().equals(acquisitionVia
				 * .get(i).getNomenclatorName()))
				 * loanObject.setAcquisitionType(acquisitionVia.get(i));
				 * 
				 * } loanObject.setControlNumber(lastRecord.getField(1).
				 * getStringFieldValue());
				 * loanObject.setTitle(lastRecord.getField
				 * (2).getStringFieldValue());
				 * loanObject.setAuthor(lastRecord.getField
				 * (3).getStringFieldValue());
				 * loanObject.setEditionNumber(Integer
				 * .parseInt(lastRecord.getField(11).getStringFieldValue()));
				 * loanObject.setInventorynumber(null);
				 * //loanObject.setLoanObjectState
				 * (((AllManagementController)controller
				 * ).getSuggestion().getNomenclator
				 * ((AdquisitionNomenclator.SITUATION_PRECATALOGUING)));
				 * loanObject.setPrice(Double.valueOf(lastRecord.getField(21).
				 * getStringFieldValue()));
				 * //if(lastRecord.getField(23).getStringFieldValue
				 * ().equals("Compra")) //
				 * loanObject.setProvider(lastRecord.getField
				 * (24).getStringFieldValue()); //else
				 * if(lastRecord.getField(23)
				 * .getStringFieldValue().equals("Canje"))
				 * //loanObject.setProvider
				 * (lastRecord.getField(31).getStringFieldValue()); // else
				 * if(lastRecord
				 * .getField(23).getStringFieldValue().equals("Donación"))
				 * //loanObject
				 * .setProvider(lastRecord.getField(31).getStringFieldValue());
				 * loanObject
				 * .setQuantity(lastRecord.getField(28).getStringFieldValue());
				 * loanObject
				 * .setRecordType(lastRecord.getField(6).getStringFieldValue());
				 * loanObject.setLoanObjectState(loanObjectState);
				 * loanObject.setRegistrationDate
				 * (lastRecord.getField(20).getStringFieldValue());
				 * loanObject.setRoom
				 * (lastRecord.getField(7).getStringFieldValue());
				 * loanObject.setSituation(situation);
				 * loanObject.setTome(lastRecord
				 * .getField(15).getStringFieldValue());
				 * loanObject.setVolume(lastRecord
				 * .getField(16).getStringFieldValue());
				 * 
				 * 
				 * 
				 * List<String> dataBaseFormats = null;
				 * 
				 * dataBaseFormats =
				 * dataBaseManager.getDatabaseFormats(DataBaseName);
				 * 
				 * String format = dataBaseFormats.get(0);
				 * 
				 * FormattedRecord formattedRecord =
				 * dataBaseManager.getFormattedRecord(DataBaseName, lastRecord,
				 * format);
				 * 
				 * String htmlString = formattedRecord.getRecord();
				 * 
				 * for (int j = 0; j < compound.getChildren().length; j++) {
				 * compound.getChildren()[j].dispose(); } compound.dispose();
				 * 
				 * ContributorService contributorService =
				 * getContributorService();
				 * 
				 * classView = new
				 * ViewRegisterAcquisitionArea(controller,contributorService
				 * ,RegisterLogAcquisition.this);
				 * 
				 * classView.setDataBaseFormats(dataBaseFormats);
				 * classView.setDataBaseName(DataBaseName);
				 * classView.setHtmlString(htmlString);
				 * classView.setLastRecord(lastRecord);
				 * 
				 * classView.createUIControl(father);
				 * 
				 * //MessageDialogUtil.openInformation(father.getDisplay().
				 * getActiveShell(),
				 * MessageUtil.unescape(AbosMessages.get().MESSAGES_INFORMATION
				 * ),
				 * MessageUtil.unescape(AbosMessages.get().MESSAGES_RECORD_ADDED
				 * ), null);
				 * showInformationMessage(AbosMessages.get().MESSAGES_RECORD_ADDED
				 * ); } else
				 * //MessageDialogUtil.openError(father.getDisplay().getActiveShell
				 * (),
				 * MessageUtil.escape(AbosMessages.get().MESSAGES_ERROR),MessageUtil
				 * .unescape(AbosMessages.get().MESSAGES_FAILED_TO_REGISTER),
				 * null);
				 * showErrorMessage(AbosMessages.get().MESSAGES_FAILED_TO_REGISTER
				 * ); }catch(Exception e){
				 * 
				 * }
				 * 
				 * }
				 * 
				 * 
				 * }}
				 * 
				 * @Override public void widgetDefaultSelected(SelectionEvent
				 * arg0) {
				 * 
				 * };
				 */

			} catch (JisisDatabaseException e1) {
				e1.printStackTrace();
			}

		}

		return parent;
	}

	@Override
	public boolean canClose() {
		return false;
	}

	@Override
	public void l10n() {

		// registerButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACCEPT));
		cancelButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CANCEL));
	}

}
