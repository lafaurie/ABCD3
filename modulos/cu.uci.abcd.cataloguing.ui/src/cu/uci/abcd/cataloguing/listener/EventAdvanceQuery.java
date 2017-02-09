package cu.uci.abcd.cataloguing.listener;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.cataloguing.controller.ProxyController;
import cu.uci.abcd.cataloguing.ui.RecordQuery;
import cu.uci.abcd.cataloguing.ui.ReferenceView;
import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.Option;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.OptionAND;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.OptionNOT;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.OptionOR;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.widget.advanced.query.QueryComponent;
import cu.uci.abos.widget.advanced.query.domain.QueryStructure;
import cu.uci.abos.widget.template.util.Util;

public class EventAdvanceQuery implements Listener {

	/**
  *
  */
	private static final long serialVersionUID = 1L;

	private QueryComponent component;
	private boolean validate = true;
	private IJisisDataProvider service;
	private List<Record> records;
	private String databaseName;
	private String isisDefHome;
	private Composite content;
	private Composite view;
	private ProxyController controller;
	private boolean flagOr = true;

	private ArrayList<QueryStructure> plusTemp;

	private List<Option> options = new ArrayList<Option>();

	private Option option;
	private OptionAND optionAND;
	private OptionOR optionOR;
	private OptionNOT optionNOT;

	public EventAdvanceQuery(QueryComponent component, ProxyController controller, String databaseName, Composite content, Composite view) {
		this.component = component;
		this.controller = controller;
		this.service = controller.getDataBaseManagerService().getService();
		this.databaseName = databaseName;
		this.isisDefHome = Util.getDefHome();
		this.records = null;
		this.content = content;
		this.view = view;
	}

	@Override
	public void handleEvent(Event arg0) {

		options.removeAll(options);
		validate = true;
		flagOr = true;

		ArrayList<QueryStructure> children = component.getChildrens();
		int childrenCount = children.size();

		for (int i = 0; i < childrenCount; i++) {
			String term = children.get(i).getTerm().getText();
			String clave = children.get(i).getClave().getText();

			if (clave.equals("Título")) {
				if (term.contains(" ")) {
					if (!term.contains("\""))
						term = "\"" + term + "\"";
				}
				children.get(i).getTerm().setText(term);
			}

			if (term == null || term.equals("")) {
				validate = false;
				break;
			}
		}

		if (validate) {

			ArrayList<String> recordsType = new ArrayList<String>();

			Control[] boys = content.getChildren();
			int countBoys = boys.length;

			for (int i = 0; i < countBoys; i++) {
				Button currentButton = (Button) boys[i];
				if (currentButton.getSelection())
					recordsType.add(currentButton.getText());
			}

			plusTemp = component.getChildrens();

			for (int i = 0; i < plusTemp.size(); i++)
				try {

					if (options.size() == 0) {
						option = new Option(getAdvanceFilter(plusTemp.get(i).getClave().getSelectionIndex()), plusTemp.get(i).getTerm().getText());
						option.setGroup(1);
						options.add(option);
					} else
						addOption(plusTemp.get(i - 1).getAndOr().getSelectionIndex(), getAdvanceFilter(plusTemp.get(i).getClave().getSelectionIndex()), plusTemp.get(i).getTerm().getText(), 1);

				} catch (Exception e) {
					e.printStackTrace();
				}

			for (int i = 0; i < recordsType.size(); i++) {
				if (flagOr) {
					option = new Option("3006", recordsType.get(i));
					option.setGroup(2);
					options.add(option);
					flagOr = false;
				} else {
					option = new OptionOR("3006", recordsType.get(i));
					option.setGroup(2);
					options.add(option);
				}

			}

			try {
				records = service.findByOptions(options, databaseName, isisDefHome);

				if (records == null || records.size() == 0) {
					RetroalimentationUtils.showErrorShellMessage("No se encontró ningún resultado.");
				} else {
					// erase view
					Composite superArg0 = view.getParent();
					view.dispose();

					superArg0.getShell().layout(true, true);
					superArg0.getShell().redraw();
					superArg0.getShell().update();

					RecordQuery recordQuery = new RecordQuery();
					recordQuery.setRecords(records);
					recordQuery.setController(controller);
					recordQuery.setDataBaseName(databaseName);
					recordQuery.setReferenceView(ReferenceView.Advance);

					recordQuery.createUIControl(superArg0);
     
					superArg0.getShell().layout(true, true);
					superArg0.getShell().redraw();
					superArg0.getShell().update();
				}
			} catch (Exception e) {
				RetroalimentationUtils.showErrorShellMessage("No se encontró ningún resultado.");

			}

		} else
			RetroalimentationUtils.showErrorShellMessage("Debe proporcionar un criterio de búsqueda válido.");
	}

	public void setComponent(QueryComponent component) {
		this.component = component;
	}

	public String getAdvanceFilter(int selection) {

		// ** Standard marc21**\\
		/*
		 * 0 -- Title 245 1 -- Author 100 2 -- Materia 650 3 -- ISBN 20 4 --
		 * ISSN 22 5 -- Sígnatura topográfica 50 6 -- Nombre de la
		 * institución.110 7 -- Editorial 8 -- Edición 250 9 -- Lugar de
		 * publicación 260 10 -- Número/Volumen 26 11 -- Título de la Serie 490
		 */

		switch (selection) {
		case 0:
			return "245";
		case 1:
			return "100";
		case 2:
			return "650";
		case 3:
			return "20";
		case 4:
			return "22";
		case 5:
			return "50";
		case 6:
			return "110";
		case 7:
			return "20";
		case 8:
			return "250";
		case 9:
			return "260";
		case 10:
			return "26";
		case 11:
			return "490";
		default:
			return "245";

		}
	}

	private void addOption(int selection, String filter, String term, int group) {

		if (selection == 0) {
			optionAND = new OptionAND(filter, term);
			optionAND.setGroup(group);
			options.add(optionAND);
		} else if (selection == 1) {
			optionOR = new OptionOR(filter, term);
			optionOR.setGroup(group);
			options.add(optionOR);
		} else {
			optionNOT = new OptionNOT(filter, term);
			optionNOT.setGroup(group);
			options.add(optionNOT);
		}
	}

}
