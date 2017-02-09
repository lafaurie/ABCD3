package cu.uci.abcd.demo.ui;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.demo.domain.common.Person;
import cu.uci.abcd.demo.ui.controller.DemoViewController;
import cu.uci.abcd.demo.ui.domain.PersonView;
import cu.uci.abos.l10n.AbosMessages;
import cu.uci.abos.ui.api.IViewController;
import cu.uci.abos.util.api.FormDatas;
import cu.uci.abos.widgets.grid.IEditableArea;
import cu.uci.abos.widgets.grid.IGridViewEntity;
import cu.uci.abos.widgets.grid.IVisualEntityManager;

public class PersonAddArea implements IEditableArea {
	private Map<String, Control> controls;
	private IViewController controller;

	public PersonAddArea(IViewController controller) {
		controls = new HashMap<String, Control>();
		this.controller = controller;
	}

	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity,
			IVisualEntityManager manager) {
		//parent.setLayout(new FormLayout());
		Composite groupArea = parent;
//		groupArea.setLayout(new FormLayout());
//		FormDatas.attach(groupArea).atLeft(0).atRight(0);
		//groupArea.setText("Registrar persona");
		//controls.put("groupArea", groupArea);
		// Composite elements
		Label firtsName = new Label(groupArea, SWT.NONE | SWT.BORDER);
		firtsName.setText("Nombres :");
		FormDatas.attach(firtsName).atTop(20);
		controls.put("firtsName", firtsName);
		Text firtsNameText = new Text(groupArea, SWT.NONE);
		controls.put("firtsNameText", firtsNameText);
		FormDatas.attach(firtsNameText).withWidth(computeSize(firtsNameText))
				.atLeftTo(firtsName, 5).atTopTo(firtsName, 0, SWT.CENTER);
		Label lastName = new Label(groupArea, SWT.NONE | SWT.BORDER);
		lastName.setText("Apellidos :");
		FormDatas.attach(lastName).atTopTo(firtsName, 20);
		controls.put("lastName", lastName);
		Text lastNameText = new Text(groupArea, SWT.NONE);
		FormDatas.attach(lastNameText).withWidth(computeSize(lastNameText))
				.atLeftTo(lastName, 5).atTopTo(lastName, 0, SWT.CENTER);
		controls.put("lastNameText", lastNameText);
		Label birthDate = new Label(groupArea, SWT.NONE);
		birthDate.setText(AbosMessages.get().DEMO_LABEL_BIRTH_DATE);
		FormDatas.attach(birthDate).atTopTo(lastName, 20);
		DateTime date = new DateTime(groupArea, SWT.DATE | SWT.DROP_DOWN);
		FormDatas.attach(date).withWidth(computeSize(lastNameText))
				.atLeftTo(birthDate, 5).atTopTo(birthDate, 0, SWT.CENTER);
		controls.put("birthDate", date);
		return parent;
	}

	@Override
	public Composite createButtons(Composite parent, IGridViewEntity entity,
			final IVisualEntityManager manager) {
		Button saveBtn = new Button(parent, SWT.PUSH);
		saveBtn.setText("Registrar");
		saveBtn.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				Person person = new PersonView(((Text) controls
						.get("firtsNameText")).getText(), ((Text) controls
						.get("lastNameText")).getText(), Date
						.valueOf("1985-03-06"));
				Person auxPerson = new Person();
				auxPerson.setFirstName(person.getFirstName());
				auxPerson.setLastName(person.getLastName());
				auxPerson.setBirthDate(person.getBirthDate());
				((DemoViewController) controller).save(auxPerson);
				manager.add((IGridViewEntity) person);
				// clean the controls
				((Text) controls.get("firtsNameText")).setText("");
				((Text) controls.get("lastNameText")).setText("");
			}

		});
		return parent;
	}

	@Override
	public void dispose() {
		for (Control control : controls.values()) {
			control.dispose();
		}
	}

	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public Control getControl(String key) {
		return this.controls.get(key);
	}

	private int computeSize(Text text) {
		GC gc = new GC(text);
		FontMetrics fm = gc.getFontMetrics();
		int charWidth = fm.getAverageCharWidth();
		int width = text.computeSize(charWidth * 8, SWT.DEFAULT).x;
		gc.dispose();
		return width;
	}

}
