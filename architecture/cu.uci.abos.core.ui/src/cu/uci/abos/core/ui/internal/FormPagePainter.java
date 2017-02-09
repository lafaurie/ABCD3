package cu.uci.abos.core.ui.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cu.uci.abos.core.ui.PagePainter;
import cu.uci.abos.core.ui.Painter;
import cu.uci.abos.core.ui.PainterHelper;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.util.api.FormDatas;
import cu.uci.abos.widgets.grid.CRUDTreeTable;

public class FormPagePainter implements PagePainter {

	Stack<Composite> composites;
	List<Control> controls;
	Map<String, Painter> painters;
	Integer dimension;
	PainterHelper painterHelper;

	public FormPagePainter() {
		super();
		composites = new Stack<Composite>();
		controls = new ArrayList<Control>();
		painters = new HashMap<String, Painter>();
		dimension = Display.getCurrent().getBounds().width;
		System.out.println("Dimesionnn "+dimension);
		painterHelper = new PainterHelper();
		painterHelper.setDimension(dimension);
		createPainters();

	}

	private void createPainters() {
		painters = new HashMap<String, Painter>();
		
		painters.put(Text.class.getName(), new TextPainter(painterHelper));
		painters.put(Label.class.getName(), new LabelPainter(painterHelper));
		painters.put(Label.class.getName() + "Header", new LabelHeaderPainter(painterHelper));
		painters.put(Label.class.getName() + "Separator", new LabelSeparatorPainter(painterHelper));
		painters.put(Combo.class.getName(), new ComboPainter(painterHelper));
		painters.put(Button.class.getName(), new ButtonPainter(painterHelper));
		painters.put(CRUDTreeTable.class.getName(), new CrudTablePainter(painterHelper));

	}

	@Override
	public void addComposite(Composite composite, Percent percent) {
		composite.setLayout(new FormLayout());
		if (composites.isEmpty()) {
			FormDatas.attach(composite).atLeft(0).withWidth(calculatePercent(dimension, percent));
		} else {
			FormDatas.attach(composite).atTopTo(composites.peek()).atLeft(0).withWidth(calculatePercent(dimension, percent));
		}
		composites.push(composite);
		painterHelper.setTop(composite);
		painterHelper.setUbication(1);
	}

	@Override
	public void addComposite(Composite composite) {
		composite.setLayout(new FormLayout());
		if (composites.isEmpty()) {
			FormDatas.attach(composite).atLeft(0).atRight(0);
		} else {
			FormDatas.attach(composite).atTopTo(composites.peek()).atLeft(0).atRight(0);
		}
		composites.push(composite);
		painterHelper.setTop(composite);
		painterHelper.setUbication(1);
	}

	@Override
	public void addHeader(Label header) {
		reset();
		painters.get(header.getClass().getName() + "Header").paint(header, dimension, Percent.W50);
		header.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		reset();
	}

	@Override
	public void addSeparator(Label separator) {
		reset();
		painters.get(separator.getClass().getName() + "Separator").paint(separator, dimension, Percent.W50);
		reset();
	}

	@Override
	public void add(Control control) {
		if (dimension > 720) {
			System.out.println(control.getParent().getBounds().width + " ADD CONTROLL "+control.getClass().getName());
			painters.get(control.getClass().getName()).paint(control, dimension, Percent.W25);
		} else {
			if (dimension > 320) {
				painters.get(control.getClass().getName()).paint(control, dimension, Percent.W50);
			} else {
				painters.get(control.getClass().getName()).paint(control, dimension, Percent.W100);
			}
		}
	}

	@Override
	public void add(Control control, Percent percent) {
		painters.get(control.getClass().getName()).paint(control, control.getParent().getBounds().width, percent);
	}

	@Override
	public void dispose() {
		while (!composites.isEmpty()) {
			composites.pop().dispose();
		}
	}

	private Integer calculatePercent(Integer dimension, Percent percent) {
		Integer factor = 1;
		if (dimension > 320 && dimension < 720) {
			factor = 1;
		}

		if (dimension < 320) {
			return dimension;
		}
		int value = SWT.DEFAULT;
		switch (percent) {
		case W10:
			value = (int) (factor * dimension * 0.1);
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
		case W50:
			value = (int) (factor * dimension * 0.5);
			break;
		case W75:
			value = (int) (factor * dimension * 0.75);
			break;
		case W100:
			value = (int) (factor * dimension);
			break;
		default:
			break;
		}
		return (value < dimension) ? value : dimension;
	}

	@Override
	public void reset() {
		painterHelper.reset();
	}

}
