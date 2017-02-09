package cu.uci.abcd.cataloguing.listener;

import java.util.HashMap;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.cataloguing.IDataBaseManager;
import cu.uci.abcd.cataloguing.IExemplaryRecord;
import cu.uci.abcd.cataloguing.controller.ProxyController;
import cu.uci.abcd.cataloguing.ui.RecordQuery;
import cu.uci.abcd.cataloguing.ui.ReferenceView;
import cu.uci.abcd.cataloguing.util.QueryStructure;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.Option;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.l10n.cataloguing.AbosMessages;
import cu.uci.abos.widget.template.util.Util;

/**
 * 
 * @author Basilio Puentes Rodríguez
 * 
 */

public class EventRecordQuery implements Listener {

	private static final long serialVersionUID = 1L;

	private ProxyController controller;
	private String dataBaseName;
	private Composite view;
	private QueryStructure queryStructure;
	private String title;
	private String author;
	private String editorial;
	private String publicationPlace;
	private String recordType;
	private Map<String, String> fieldsWithValues;

	public EventRecordQuery(ProxyController controller, String dataBaseName, Composite view, QueryStructure queryStructure) {
		this.controller = controller;
		this.dataBaseName = dataBaseName;
		this.view = view;
		this.queryStructure = queryStructure;
	}

	@Override
	public void handleEvent(Event arg0) {

		this.fieldsWithValues = new HashMap<String, String>();

		getValues();

		if (notTerm())
			RetroalimentationUtils.showErrorShellMessage("Debe proporcionar algún criterio de búsqueda.");
		else {
			List<Record> records = buildQuery();

			if (records == null || records.size() == 0) {
				RetroalimentationUtils.showErrorShellMessage("No se encontró ningún resultado.");
			} else {
				// Paso a la vista
				Composite superArg0 = view.getParent();
				view.dispose();

				superArg0.getShell().layout(true, true);
				superArg0.getShell().redraw();
				superArg0.getShell().update();

				RecordQuery recordQuery = new RecordQuery();
				recordQuery.setRecords(records);
				recordQuery.setController(controller);
				recordQuery.setReferenceView(ReferenceView.Register);
				recordQuery.setDataBaseName(dataBaseName);

				recordQuery.createUIControl(superArg0);

				superArg0.getShell().layout(true, true);
				superArg0.getShell().redraw();
				superArg0.getShell().update();
			}
		}
	}

	private List<Record> buildQuery() {

		List<Record> records = null;

		IDataBaseManager dataBaseManager = controller.getDataBaseManagerService();
		IExemplaryRecord exemplaryRecord = controller.getExemplaryRecordService();
		String value = null;
		String key = null;
		try {
			if (fieldsWithValues.size() == 1) {
				// consulta de un solo termino
				if (fieldsWithValues.containsKey("245")) {
					key = "245";
					value = fieldsWithValues.get("245");
				} else if (fieldsWithValues.containsKey("100")) {
					value = fieldsWithValues.get("100");
					key = "100";
				} else if (fieldsWithValues.containsKey("8")) {
					value = fieldsWithValues.get("8");
					key = "8";
				} else if (fieldsWithValues.containsKey("260")) {
					value = fieldsWithValues.get("260");
					key = "260";
				} else if (fieldsWithValues.containsKey("3006")) {
					value = fieldsWithValues.get("3006");
					key = "3006";
				}   

				try {

					Option option = new Option(key, value);
					List<Option> options = new LinkedList<Option>();
					options.add(option);

					records = dataBaseManager.findByOptions(options, dataBaseName);

				} catch (JisisDatabaseException e) {
					RetroalimentationUtils.showErrorShellMessage("Problema de conección con JISIS");
				}

			} else {

				try {
					String isisDefHome = Util.getDefHome();
					records = exemplaryRecord.findByOptionsExemplaryRecords(fieldsWithValues, dataBaseName, dataBaseManager, isisDefHome);

				} catch (JisisDatabaseException e) {
					RetroalimentationUtils.showErrorShellMessage("Problema de conección con JISIS");
				}

			}
		} catch (Exception e) {
			RetroalimentationUtils.showErrorShellMessage("No se encontró ningún resultado.");

		}

		return records;
	}

	private void getValues() {
		title = queryStructure.getTitleText().getText();

		if (title.contains(" ")) {
			if (!title.contains("\""))
				title = "\"" + title + "\"";
		}

		author = queryStructure.getAuthorText().getText();
		editorial = queryStructure.getEditorialText().getText();
		publicationPlace = queryStructure.getPublicationYearText().getText();

		recordType = queryStructure.getRecordTypeCombo().getText();
	}

	private boolean notTerm() {
		boolean response;

		boolean titleVal = false;
		boolean authorVal = false;
		boolean editorialVal = false;
		boolean publicationPlaceVal = false;
		boolean recordTypeVal = false;

		if (title != null && !title.equals("")) {
			fieldsWithValues.put("245", title);
			titleVal = true;
		} else
			titleVal = false;

		if (author != null && !author.equals("")) {
			fieldsWithValues.put("100", author);
			authorVal = true;
		} else
			authorVal = false;

		if (editorial != null && !editorial.equals("")) {
			fieldsWithValues.put("8", editorial);
			editorialVal = true;
		} else
			editorialVal = false;

		if (publicationPlace != null && !publicationPlace.equals("")) {
			fieldsWithValues.put("260", publicationPlace);
			publicationPlaceVal = true;
		} else
			publicationPlaceVal = false;

		if (!AbosMessages.get().VALUE_COMBO_SELECT.equals(recordType)) {
			fieldsWithValues.put("3006", recordType);
			recordTypeVal = true;
		} else
			recordTypeVal = false;

		if (!titleVal && !authorVal && !editorialVal && !publicationPlaceVal && !recordTypeVal)
			response = true;
		else
			response = false;

		return response;
	}
}
