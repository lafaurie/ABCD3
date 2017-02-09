package cu.uci.abcd.demo.ui;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import cu.uci.abcd.demo.domain.common.Person;
import cu.uci.abcd.demo.ui.controller.DemoViewController;
import cu.uci.abcd.demo.ui.domain.PersonView;
import cu.uci.abos.l10n.AbosMessages;
import cu.uci.abos.ui.api.IContributor;
import cu.uci.abos.ui.api.IViewController;
import cu.uci.abos.util.api.FormDatas;
import cu.uci.abos.widgets.grid.CRUDTreeTable;

public class DemoView implements IContributor {
	private IViewController controller;

	@Override
	public Control createUIControl(Composite parent) {
		FormLayout personLayout = new FormLayout();
		personLayout.marginWidth = personLayout.marginHeight = 0;
		parent.setLayout(personLayout);

		CRUDTreeTable personTable = new CRUDTreeTable(parent, SWT.NONE);
		personTable.setEntityClass(PersonView.class);
		personTable.setAdd(true, new PersonAddArea(controller));
		FormDatas.attach(personTable).atLeft(0).atRight(0);
		try {
			personTable.setColumnHeaders(Arrays.asList(
					AbosMessages.get().DEMO_GRID_COLUMN_ID,
					AbosMessages.get().DEMO_GRID_COLUMN_FIRST_NAME,
					AbosMessages.get().DEMO_GRID_COLUMN_LAST_NAME,
					AbosMessages.get().DEMO_GRID_COLUMN_BIRTH_DATE));
			personTable.createTable();
			initializeGrid(personTable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parent;
	}

	@Override
	public String getID() {
		return "RegistrarTrabajadoresBibliotecaID";
	}

	@Override
	public void l10n() {

	}

	@Override
	public String contributorName() {
		return "Registrar trabajadores";
	}

	@Override
	public boolean canClose() {
		return false;
	}

	@Override
	public void setViewController(IViewController controller) {
		this.controller = controller;
	}

	private void initializeGrid(CRUDTreeTable table) {
		Collection<Person> listPerson = ((DemoViewController) controller)
				.findAll();
		for (Person aux : listPerson) {
			System.out.println(aux.getFirstName() + " " + aux.getLastName());
			PersonView person = new PersonView(aux.getFirstName(),
					aux.getLastName(), aux.getBirthDate());
			person.setResourceID(aux.getResourceID());
			table.addRow(person);
		}
	}
}
