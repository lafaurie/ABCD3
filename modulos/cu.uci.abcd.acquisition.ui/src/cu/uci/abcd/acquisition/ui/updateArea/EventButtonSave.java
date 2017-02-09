package cu.uci.abcd.acquisition.ui.updateArea;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.unesco.jisis.corelib.common.FormattedRecord;
import org.unesco.jisis.corelib.common.Global;
import org.unesco.jisis.corelib.record.Field;
import org.unesco.jisis.corelib.record.IField;
import org.unesco.jisis.corelib.record.IRecord;
import org.unesco.jisis.corelib.record.ISubfield;
import org.unesco.jisis.corelib.record.Record;
import org.unesco.jisis.corelib.record.StringOccurrence;
import org.unesco.jisis.corelib.record.Subfield;

import cu.uci.abcd.acquisition.IRegistrationManageAcquisitionService;
import cu.uci.abcd.acquisition.ui.RegisterLogAcquisition;
import cu.uci.abcd.acquisition.ui.controller.AllManagementController;
import cu.uci.abcd.adquisition.l10n.AbosMessages;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.widget.repeatable.field.util.FieldStructure;
import cu.uci.abos.widget.repeatable.field.util.MyDate;
import cu.uci.abos.widget.repeatable.field.util.MyDateTime;
import cu.uci.abos.widget.repeatable.field.util.MyTime;
import cu.uci.abos.widget.repeatable.field.util.SubFieldStructure;
import cu.uci.abos.widget.template.component.TemplateCompound;

public class EventButtonSave implements Listener {

	private static final long serialVersionUID = 1L;

	private TemplateCompound compound;
	private ViewController controller;
	private Library library;
	private String dataBaseNameAcquisition;
	private String dataBaseNameCataloguin;
	private ViewRegisterAcquisitionArea classView;
	private Composite father;
	private RegisterLogAcquisition registerLogAcquisition;
	private ContributorService contributorService;
	private boolean exemplaryRecord;
	private ViewRegisterAcquisitionAreaConsult classViewConsult;
	private ViewRegisterAcquisitionAreaUpdate viewRegisterAcquisitionAreaUpdate;
	private Record lastRecord;
	private boolean isRegister;

	public EventButtonSave(TemplateCompound compound, ViewController controller, Library library, String dataBaseNameAcquisition, String databaseNameCataloguin, RegisterLogAcquisition registerLogAcquisition, ContributorService contributorService, Composite father) {
		this.compound = compound;
		this.controller = controller;
		this.library = library;
		this.dataBaseNameAcquisition = dataBaseNameAcquisition;
		this.dataBaseNameCataloguin = databaseNameCataloguin;
		this.registerLogAcquisition = registerLogAcquisition;
		this.contributorService = contributorService;
		this.father = father;
	}

	public EventButtonSave(TemplateCompound compound, ViewController controller, Library library, String dataBaseName, ViewRegisterAcquisitionAreaUpdate viewRegisterAcquisitionAreaUpdate, ContributorService contributorService, Composite father, Record lastRecord) {
		this.compound = compound;
		this.controller = controller;
		this.library = library;
		this.dataBaseNameAcquisition = dataBaseName;
		this.viewRegisterAcquisitionAreaUpdate = viewRegisterAcquisitionAreaUpdate;
		this.contributorService = contributorService;
		this.father = father;
		this.lastRecord = lastRecord;
	}

	IRecord record;

	@Override
	public void handleEvent(Event arg0) {

		boolean validate = compound.validateRecord();

		if (validate) {

			ArrayList<FieldStructure> childrens = compound.getChildrens();

			if (controller != null) {

				IRegistrationManageAcquisitionService dataBaseManager = ((AllManagementController) controller).getAcquisition();

				int fieldCount = childrens.size();

				if (lastRecord != null) {
					record = lastRecord;
					isRegister = false;
				} else {
					record = Record.createRecord();
					isRegister = true;
				}

				try {
					int fieldNotEmpty = 0;
					for (int i = 0; i < fieldCount; i++) {
						FieldStructure fieldStructure = childrens.get(i);
						int occurrenceCount = 0;
						int tag = fieldStructure.getTag();

						if (fieldStructure.isRepeatableField()) {
							boolean terminate = false;

							for (int j = i + 1; j < fieldCount && terminate == false; j++) {
								int tagj = -1;
								FieldStructure fieldStructurej = childrens.get(j);

								if (fieldStructurej.getSubfields().isEmpty())
									tagj = fieldStructurej.getTag();
								else
									tagj = fieldStructurej.getSubfields().get(0).getTag();

								if (tagj == tag)
									occurrenceCount++;
								else
									terminate = true;
							}
						}

						if (occurrenceCount > 0) {
							int occurrenceBegin = 0;
							IField field = null;

							for (int j = 0; j < occurrenceCount + 1; j++) {
								FieldStructure fieldStructureSave = childrens.get(i + j);
								int subFieldsCount = fieldStructureSave.getSubfields().size();

								if (subFieldsCount > 0) {
									ArrayList<Subfield> subfields = new ArrayList<Subfield>();
									int subFieldWithValueCount = 0;

									for (int k = 0; k < subFieldsCount; k++) {
										SubFieldStructure subFieldStructure = fieldStructureSave.getSubfields().get(k);
										String value = "";

										if (subFieldStructure.getControl() instanceof Text)
											value = ((Text) subFieldStructure.getControl()).getText();
										else if (subFieldStructure.getControl() instanceof Combo)
											value = ((Combo) subFieldStructure.getControl()).getText();
										else if (fieldStructure.getControl() instanceof MyDateTime)
											value = ((MyDateTime) fieldStructure.getControl()).getText();
										else if (fieldStructure.getControl() instanceof MyDate)
											value = ((MyDate) fieldStructure.getControl()).getText();
										else if (fieldStructure.getControl() instanceof MyTime)
											value = ((MyTime) fieldStructure.getControl()).getText();

										String code = subFieldStructure.getSubFieldCode();
										char realCode = code.charAt(code.length() - 1);

										if (!subFieldStructure.getControl().isDisposed() && !value.equals("") && !value.equals(null) && !value.equals("Seleccione")) {
											ISubfield subfield = new Subfield(realCode, value);
											subfields.add((Subfield) subfield);
											subFieldWithValueCount++;
										}
									}

									if (subFieldWithValueCount > 0) {
										StringOccurrence occurrence = new StringOccurrence();
										Subfield[] subfieldsArray = subfields.toArray(new Subfield[subfields.size()]);
										occurrence.setSubfields(subfieldsArray);

										if (0 == j) {
											field = new Field(tag, Global.FIELD_TYPE_ALPHANUMERIC);
											field.setOccurrence(occurrenceBegin, occurrence);

											if (isRegister)
												record.addField(field);
											else
												record.setField(field);

											fieldNotEmpty++;
										} else {
											int occurrenceCountfieldLast = field.getOccurrenceCount();
											field.setOccurrence(occurrenceCountfieldLast, occurrence);
										}
									}
								} else {
									String value = "";

									if (fieldStructureSave.getControl() instanceof Text)
										value = ((Text) fieldStructureSave.getControl()).getText();
									else if (fieldStructureSave.getControl() instanceof Combo)
										value = ((Combo) fieldStructureSave.getControl()).getText();
									else if (fieldStructure.getControl() instanceof MyDateTime)
										value = ((MyDateTime) fieldStructure.getControl()).getText();
									else if (fieldStructure.getControl() instanceof MyDate)
										value = ((MyDate) fieldStructure.getControl()).getText();
									else if (fieldStructure.getControl() instanceof MyTime)
										value = ((MyTime) fieldStructure.getControl()).getText();

									if (!fieldStructureSave.getControl().isDisposed() && !value.equals("") && !value.equals(null) && !value.equals("Seleccione")) {

										if (0 == j) {
											field = new Field(tag, Global.FIELD_TYPE_ALPHANUMERIC);
											field.setFieldValue(value);
											if (isRegister)
												record.addField(field);
											else
												record.setField(field);

											fieldNotEmpty++;
										} else {
											int occurrenceCountfieldLast = field.getOccurrenceCount();
											field.setOccurrence(occurrenceCountfieldLast, value);
										}
									}
								}
								occurrenceBegin++;
							}
							i += occurrenceCount;
						} else {
							int subFieldsCount = fieldStructure.getSubfields().size();
							IField field;

							if (subFieldsCount > 0) {
								ArrayList<Subfield> subfields = new ArrayList<Subfield>();
								int subFieldWithValueCount = 0;

								for (int j = 0; j < subFieldsCount; j++) {
									SubFieldStructure subFieldStructure = fieldStructure.getSubfields().get(j);
									String value = "";

									if (subFieldStructure.getControl() instanceof Text)
										value = ((Text) subFieldStructure.getControl()).getText();
									else if (subFieldStructure.getControl() instanceof Combo)
										value = ((Combo) subFieldStructure.getControl()).getText();
									else if (fieldStructure.getControl() instanceof MyDateTime)
										value = ((MyDateTime) fieldStructure.getControl()).getText();
									else if (fieldStructure.getControl() instanceof MyDate)
										value = ((MyDate) fieldStructure.getControl()).getText();
									else if (fieldStructure.getControl() instanceof MyTime)
										value = ((MyTime) fieldStructure.getControl()).getText();

									String code = subFieldStructure.getSubFieldCode();
									char realCode = code.charAt(code.length() - 1);

									if (!subFieldStructure.getControl().isDisposed() && !value.equals("") && !value.equals(null) && !value.equals("Seleccione")) {
										ISubfield subfield = new Subfield(realCode, value);
										subfields.add((Subfield) subfield);
										subFieldWithValueCount++;

									}
								}

								if (subFieldWithValueCount > 0) {
									StringOccurrence occurrence = new StringOccurrence();
									Subfield[] subfieldsArray = subfields.toArray(new Subfield[subfields.size()]);
									occurrence.setSubfields(subfieldsArray);

									field = new Field(tag, Global.FIELD_TYPE_ALPHANUMERIC);
									field.setOccurrence(occurrenceCount, occurrence);
									if (isRegister)
										record.addField(field);
									else
										record.setField(field);

									fieldNotEmpty++;
								}
							} else {
								String value = "";

								if (fieldStructure.getControl() instanceof Text)
									value = ((Text) fieldStructure.getControl()).getText();
								else if (fieldStructure.getControl() instanceof Combo)
									value = ((Combo) fieldStructure.getControl()).getText();
								else if (fieldStructure.getControl() instanceof MyDateTime)
									value = ((MyDateTime) fieldStructure.getControl()).getText();
								else if (fieldStructure.getControl() instanceof MyDate)
									value = ((MyDate) fieldStructure.getControl()).getText();
								else if (fieldStructure.getControl() instanceof MyTime)
									value = ((MyTime) fieldStructure.getControl()).getText();

								if (!fieldStructure.getControl().isDisposed() && !value.equals("") && !value.equals(null) && !value.equals("Seleccione")) {
									field = new Field(tag, Global.FIELD_TYPE_ALPHANUMERIC);
									field.setFieldValue(value);
									if (isRegister)
										record.addField(field);
									else
										record.setField(field);

									fieldNotEmpty++;
								}
							}
						}
					}

					if (fieldNotEmpty > 0) {

						((AllManagementController) controller).getAcquisition().registerAcquisition(record, dataBaseNameAcquisition, library.getIsisDefHome());
						Record lastRecord1;
						if (isRegister) {
							lastRecord1 = dataBaseManager.getLastRecord(dataBaseNameAcquisition, library.getIsisDefHome());

						} else
							lastRecord1 = (Record) record;

						IRecord record1 = Record.createRecord();
						record1.getField(1).setFieldValue(lastRecord1.getField(1).getFieldValue());
						record1.getField(245).setFieldValue("^a" + lastRecord1.getField(2).getFieldValue());
						record1.getField(100).setFieldValue("^a" + lastRecord1.getField(3).getFieldValue());

						String tempRecordType = (String) (lastRecord1.getField(6).getFieldValue());

						tempRecordType = (String) tempRecordType.subSequence(0, tempRecordType.length() - 1);

						if (tempRecordType.equals("Tesis"))
							record1.getField(3006).setFieldValue("Tesis");
						else
							record1.getField(3006).setFieldValue(returnRecordTypeStandar(tempRecordType));

						((AllManagementController) controller).getAcquisition().registerAcquisition(record1, dataBaseNameCataloguin, library.getIsisDefHome());

						/***/

						try {

							LoanObject loanObject = new LoanObject();
							loanObject.setInventorynumber("FillInInvenNumber");
							loanObject.setRoom(findRoom(lastRecord1));

							java.util.Date date = new java.util.Date();
							Date registerDate = new Date(date.getTime());

							loanObject.setRegistrationDate(registerDate);
							loanObject.setRecordType(findRecordTypeNomenclator(lastRecord1));
							loanObject.setLoanObjectState(findLoanObjectStateNomenclator());

							loanObject.setCatalogued(false);

							loanObject.setSituation(findLoanObjectSituationNomenclator());

							try {

								// controlNumber
								loanObject.setControlNumber(record1.getField(1).getStringFieldValue());

							} catch (Exception e) {
								// TODO: handle exception
							}

							try {
								// title
								Field field = (Field) record1.getField(245);
								StringOccurrence occurrence = (StringOccurrence) field.getOccurrence(0);
								Subfield[] subFieldsRecord = occurrence.getSubfields();
								int count1 = subFieldsRecord.length;
								for (int i = 0; i < count1; i++) {
									if ('a' == subFieldsRecord[i].getSubfieldCode()) {
										loanObject.setTitle(subFieldsRecord[i].getData());
										break;
									}
								}

							} catch (Exception e) {
								// TODO: handle exception
							}

							try {

								// author
								Field field2 = (Field) record1.getField(100);
								StringOccurrence occurrence2 = (StringOccurrence) field2.getOccurrence(0);
								if (occurrence2 != null) {
									Subfield[] subFieldsRecord2 = occurrence2.getSubfields();
									int count2 = subFieldsRecord2.length;
									for (int i = 0; i < count2; i++) {
										if ('a' == subFieldsRecord2[i].getSubfieldCode()) {
											loanObject.setAuthor(subFieldsRecord2[i].getData());
											break;
										}
									}
								}
							} catch (Exception e) {
								// TODO: handle exception
							}

							// isisDataBaseName
							loanObject.setIsisDataBaseName(dataBaseNameCataloguin);

							// editionNumber
							Field field3 = (Field) record1.getField(250);
							StringOccurrence occurrence3 = (StringOccurrence) field3.getOccurrence(0);
							if (occurrence3 != null) {
								Subfield[] subFieldsRecord3 = occurrence3.getSubfields();
								int count3 = subFieldsRecord3.length;
								for (int i = 0; i < count3; i++) {
									if ('a' == subFieldsRecord3[i].getSubfieldCode()) {
										loanObject.setEditionNumber(subFieldsRecord3[i].getData());
										break;
									}
								}
							}

							try {
								loanObject.setPrice(Double.parseDouble(lastRecord1.getField(21).getStringFieldValue()));
							} catch (Exception e) {
								loanObject.setPrice((double) 1);
							}

							((AllManagementController) controller).getAcquisitionLoanObjectService().addLoanObject(loanObject);

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						/***/

						List<String> dataBaseFormats = null;

						dataBaseFormats = dataBaseManager.getDatabaseFormats(dataBaseNameAcquisition, library.getIsisDefHome());

						String format = dataBaseFormats.get(0);

						FormattedRecord formattedRecord = dataBaseManager.getFormattedRecord(dataBaseNameAcquisition, lastRecord1, format, library.getIsisDefHome());

						String htmlString = formattedRecord.getRecord();

						for (int j = 0; j < compound.getChildren().length; j++) {
							compound.getChildren()[j].dispose();
						}
						compound.dispose();

						classView = new ViewRegisterAcquisitionArea(controller, contributorService, registerLogAcquisition);

						classView.setDataBaseFormats(dataBaseFormats);
						classView.setDataBaseName(dataBaseNameAcquisition);
						classView.setHtmlString(htmlString);
						classView.setLastRecord(lastRecord1);

						classView.createUIControl(father);

						if (registerLogAcquisition != null) {

							RetroalimentationUtils.showInformationMessage(AbosMessages.get().MESSAGES_RECORD_ADDED);

						} else if (viewRegisterAcquisitionAreaUpdate != null) {
							RetroalimentationUtils.showInformationMessage(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_UPDATE_DATA);

						}

					} else
						RetroalimentationUtils.showErrorMessage(AbosMessages.get().MESSAGES_FAILED_TO_REGISTER);
				} catch (Exception e) {

				}

			}

		}
	}

	private Nomenclator findRecordTypeNomenclator(Record lastRecord1) {

		List<Nomenclator> temp = ((AllManagementController) controller).getSuggestion().findAllNomenclators(library.getLibraryID(), Nomenclator.LOANOBJECT_TYPE);

		try {
			for (int i = 0; i < temp.size(); i++)
				if (lastRecord1.getField(6).getStringFieldValue() == temp.get(i).getNomenclatorDescription())
					return temp.get(i);

		} catch (Exception e) {
			// TODO: handle exception
		}

		return temp.get(0);
	}

	private Nomenclator findLoanObjectStateNomenclator() {

		List<Nomenclator> temp = ((AllManagementController) controller).getSuggestion().findAllNomenclators(library.getLibraryID(), Nomenclator.LOANOBJECT_STATE);

		for (int i = 0; i < temp.size(); i++)
			if (Nomenclator.LOANOBJECT_STATE_EXPURGO == temp.get(i).getNomenclatorID())
				return temp.get(i);

		return temp.get(0);
	}

	private Room findRoom(Record lastRecord1) {

		List<Room> temp = ((AllManagementController) controller).getAcquisitionRoomService().findAll(library.getLibraryID());

		try {

			for (int i = 0; i < temp.size(); i++)
				if (temp.get(i).getRoomName().equals(lastRecord1.getField(6).getStringFieldValue()))
					return temp.get(i);

		} catch (Exception e) {
			// TODO: handle exception
		}

		return temp.get(0);
	}

	private Nomenclator findLoanObjectSituationNomenclator() {

		List<Nomenclator> temp = ((AllManagementController) controller).getSuggestion().findAllNomenclators(library.getLibraryID(), Nomenclator.LOANOBJECT_SITUATION);

		for (int i = 0; i < temp.size(); i++)
			if (Nomenclator.LOANOBJECT_SITUATION_PRECATALOGUING == temp.get(i).getNomenclatorID())
				return temp.get(i);

		return temp.get(0);
	}

	private String returnRecordTypeStandar(String recordType) {

		if (recordType.equals("Revista"))
			return "Revista";

		return "Libro";

	}
}