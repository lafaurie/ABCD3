package cu.uci.abos.core.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.FileUpload;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import cu.uci.abos.core.ui.internal.ButtonPainter;
import cu.uci.abos.core.ui.internal.ButtonRadioCheckPainter;
import cu.uci.abos.core.ui.internal.ComboPainter;
import cu.uci.abos.core.ui.internal.CompositePainter;
import cu.uci.abos.core.ui.internal.CrudTablePainter;
import cu.uci.abos.core.ui.internal.DateTimePainter;
import cu.uci.abos.core.ui.internal.GroupPainter;
import cu.uci.abos.core.ui.internal.LabelHeaderPainter;
import cu.uci.abos.core.ui.internal.LabelPainter;
import cu.uci.abos.core.ui.internal.LabelSeparatorPainter;
import cu.uci.abos.core.ui.internal.LinkPainter;
import cu.uci.abos.core.ui.internal.SpinnerPainter;
import cu.uci.abos.core.ui.internal.TabPainter;
import cu.uci.abos.core.ui.internal.TextAreaPainter;
import cu.uci.abos.core.ui.internal.TextPainter;
import cu.uci.abos.core.ui.internal.WizardPainter;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.widget.compoundlabel.CompoundLabel;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.NotPaginateTable;
import cu.uci.abos.core.widget.grid.SecurityCRUDTreeTable;
import cu.uci.abos.core.widget.wizard.Wizard;

public class FormPagePainter implements PagePainter {

	Stack<Composite> composites;
	Map<String, Painter> painters;
	Integer dimension;
	PainterHelper painterHelper;

	public FormPagePainter() {
		super();
		composites = new Stack<Composite>();
		painters = new HashMap<String, Painter>();

		if (Display.getCurrent() != null && !Display.getCurrent().isDisposed()) {
			dimension = Display.getCurrent().getBounds().width;
		} else {
			dimension = 1024;
		}
		painterHelper = new PainterHelper();
		painterHelper.setDimension(dimension);
		createPainters();

	}

	public Integer getDimension() {
		return dimension;
	}

	@Override
	public void setDimension(Integer dimension) {
		painterHelper.setDimension(dimension);
		this.dimension = dimension;
	}

	private void createPainters() {
		painters = new HashMap<String, Painter>();

		painters.put(Text.class.getName(), new TextPainter(painterHelper));
		painters.put(Text.class.getName() + "TextArea", new TextAreaPainter(painterHelper));
		painters.put(Label.class.getName(), new LabelPainter(painterHelper));
		painters.put(CLabel.class.getName(), new LabelPainter(painterHelper));
		painters.put(Label.class.getName() + "Header", new LabelHeaderPainter(painterHelper));
		painters.put(Label.class.getName() + "Separator", new LabelSeparatorPainter(painterHelper));
		painters.put(Combo.class.getName(), new ComboPainter(painterHelper));
		painters.put(Button.class.getName() + "Button", new ButtonRadioCheckPainter(painterHelper));
		painters.put(Button.class.getName(), new ButtonPainter(painterHelper));
		painters.put(CRUDTreeTable.class.getName(), new CrudTablePainter(painterHelper));
		painters.put(SecurityCRUDTreeTable.class.getName(), new CrudTablePainter(painterHelper));
		painters.put(Table.class.getName(), new CrudTablePainter(painterHelper));
		painters.put(NotPaginateTable.class.getName(), new CrudTablePainter(painterHelper));
		painters.put(DateTime.class.getName(), new DateTimePainter(painterHelper));
		painters.put(Link.class.getName(), new LinkPainter(painterHelper));
		painters.put(TabFolder.class.getName(), new TabPainter(painterHelper));
		painters.put(Group.class.getName(), new GroupPainter(painterHelper));
		painters.put(Spinner.class.getName(), new SpinnerPainter(painterHelper));
		painters.put(Wizard.class.getName(), new WizardPainter(painterHelper));
		painters.put(Composite.class.getName(), new CompositePainter(painterHelper));
		painters.put(CompoundLabel.class.getName(), new GroupPainter(painterHelper));
		painters.put(FileUpload.class.getName(), new ComboPainter(painterHelper));

	}

	@Override
	public void addComposite(Composite composite, Percent percent) {
		composite.setLayout(new FormLayout());
		if (composites.isEmpty()) {
			FormDatas.attach(composite).atLeft(0).withWidth(calculatePercent(dimension, percent));
		} else {
			getPainter(composite).paint(composite, dimension, percent);
		}
		composites.push(composite);
	}

	@Override
	public void addComposite(Composite composite) {
		composite.setLayout(new FormLayout());
		if (composites.isEmpty()) {
			if (composite.getVisible()) {
				FormDatas.attach(composite).atLeft(0).atRight(0);
			} else {
				FormDatas.attach(composite).atLeft(0).atRight(0).withHeight(0);
			}

		} else {
			if (composite.getVisible()) {
				FormDatas.attach(composite).atTopTo(composites.peek()).atLeft(0).atRight(0);
			} else {
				FormDatas.attach(composite).atTopTo(composites.peek()).atLeft(0).atRight(0).withHeight(0);
			}

		}
		composites.push(composite);
		painterHelper.setTop(composite);
		painterHelper.setUbication(0);
	}

	@Override
	public void addHeader(Label header) {
		reset();
		painters.get(header.getClass().getName() + "Header").paint(header, dimension, Percent.W100);
		header.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		reset();
	}

	@Override
	public void addSeparator(Label separator) {
		reset();
		painters.get(separator.getClass().getName() + "Separator").paint(separator, dimension, Percent.W100);
		separator.setData(RWT.CUSTOM_VARIANT, "separator");
		reset();
	}

	@Override
	public void add(Control control) {
		if (control.getClass().equals(CRUDTreeTable.class) || control.getClass().equals(SecurityCRUDTreeTable.class) || control.getClass().equals(NotPaginateTable.class)) {
			getPainter(control).paint(control, dimension, Percent.W100);
			return;
		}
		if (dimension > 840) {
			if (control.getClass().equals(Group.class)) {
				getPainter(control).paint(control, dimension, Percent.W50);
			} else {
				getPainter(control).paint(control, dimension, Percent.W25);
			}
		} else {
			if (dimension > 600) {
				if (control.getClass().equals(Group.class)) {
					getPainter(control).paint(control, dimension, Percent.W100);
				} else {
					getPainter(control).paint(control, dimension, Percent.W50);
				}
			} else {
				getPainter(control).paint(control, dimension, Percent.W100);
			}
		}
	}

	@Override
	public void add(Control control, Percent percent) {
		if (dimension > 840) {
			getPainter(control).paint(control, dimension, percent);
		} else {
			if (dimension > 600 && dimension <= 840) {
				if (percent.getValue() < Percent.W25.getValue()) {
					getPainter(control).paint(control, dimension, Percent.W25);
				} else {
					if (percent.getValue() >= Percent.W25.getValue() && percent.getValue() < Percent.W50.getValue()) {
						getPainter(control).paint(control, dimension, Percent.W50);
					} else {
						getPainter(control).paint(control, dimension, Percent.W100);
					}
				}
			} else {
				if (percent.getValue() < Percent.W25.getValue()) {
					getPainter(control).paint(control, dimension, Percent.W50);
				} else {
					getPainter(control).paint(control, dimension, Percent.W100);
				}
			}
		}

	}

	@Override
	public void dispose() {
		while (!composites.isEmpty()) {
			try {
				if (!composites.peek().isDisposed()) {
					composites.pop().dispose();
				}
			} catch (Exception e) {
				//TODO Tragamiento de Dispose
			}
		}
	}

	private Integer calculatePercent(Integer dimension, Percent percent) {
		float factor = 1f;

		if (dimension < 600) {
			return dimension - 50;
		}
		int value = SWT.DEFAULT;
		switch (percent) {
		case W10:
			value = (int) (factor * dimension * 0.1);
			break;
		case W15:
			value = (int) (factor * dimension * 0.15);
			break;
		case W20:
			value = (int) (factor * dimension * 0.2);
			break;
		case W25:
			value = (int) (factor * dimension * 0.25);
			break;
		case W33:
			value = (int) (factor * dimension * 0.33);
			break;
		case W40:
			value = (int) (factor * dimension * 0.40);
			break;
		case W45:
			value = (int) (factor * dimension * 0.45);
			break;
		case W50:
			value = (int) (factor * dimension * 0.5);
			break;
		case W75:
			value = (int) (factor * dimension * 0.75);
			break;
		case W80:
			value = (int) (factor * dimension * 0.8);
			break;
		case W90:
			value = (int) (factor * dimension * 0.9);
			break;
		case W100:
			value = (int) (factor * dimension);
			break;
		default:
			break;
		}
		return (value < dimension) ? value - 5 : dimension - 30;
	}

	@Override
	public void reset() {
		painterHelper.reset();
	}

	@Override
	public void insertComposite(Composite composite, Composite top) {

		Stack<Composite> temp = new Stack<Composite>();
		boolean encontrado = false;

		while (!composites.isEmpty()) {
			Composite tope = composites.peek();
			temp.push(composites.pop());
			if (tope.equals(composite)) {
				encontrado = true;
				if (!composites.isEmpty()) {
					if (composite.getVisible()) {
						FormDatas.attach(composite).atTopTo(composites.peek()).atLeft(0).atRight(0);
					} else {
						FormDatas.attach(composite).atTopTo(composites.peek()).atLeft(0).atRight(0).withHeight(0);
					}
				} else {
					if (composite.getVisible()) {
						FormDatas.attach(composite).atLeft(0).atRight(0);
					} else {
						FormDatas.attach(composite).atLeft(0).atRight(0).withHeight(0);
					}
				}
				Composite aux = temp.pop();
				if (!temp.isEmpty()) {
					FormDatas.attach(temp.peek()).atTopTo(composite).atLeft(0).atRight(0);
				}
				temp.push(aux);
			}
		}

		while (!temp.isEmpty()) {
			composites.push(temp.pop());
		}

		if (!encontrado) {
			addComposite(composite);
		}
	}

	private Painter getPainter(Control control) {
		// System.out.println(control.getClass().getName() + " style " +
		// control.getStyle());
		if (control.getClass().equals(Text.class) && (control.getStyle() == 33570820 || control.getStyle() == 37765124)) {
			return painters.get(control.getClass().getName());
		}
		if (control.getClass().equals(Text.class)) {
			return painters.get(control.getClass().getName() + "TextArea");
		}
		if (control.getClass().equals(Button.class) && control.getStyle() != 50331656) {
			return painters.get(control.getClass().getName() + "Button");
		}
		return painters.get(control.getClass().getName());
	}

}
