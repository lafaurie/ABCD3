package cu.uci.abos.core.ui;

import java.util.Stack;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.SecurityCRUDTreeTable;

public class GridPagePainter implements PagePainter {

	private Stack<Composite> composites;
	Integer dimension;
	Integer columnas = 2;

	public GridPagePainter() {
		super();

		if (Display.getCurrent() != null && !Display.getCurrent().isDisposed()) {
			dimension = Display.getCurrent().getBounds().width;
		} else {
			dimension = 1024;
		}
		composites = new Stack<Composite>();
	}

	@Override
	public void addComposite(Composite composite, Percent percent) {
		// percent.getValue()/columnas;
		// FIXME revisa la proporcion del porcento, si la resolucion baja, el
		// porciento crece, si estas en una mediana, duplicas,
		// composite.setLayout(new GridLayout(2))

	}

	@Override
	public void addComposite(Composite composite) {
		GridLayout grid = new GridLayout(getNumberOfColumn(), true);
		grid.marginLeft = 15;
		grid.marginRight = 15;
		composite.setLayout(grid);
		composites.push(composite);

	}

	private int getNumberOfColumn() {
		if (dimension <= 600) {
			return 4;
		}
		if (dimension > 600 && dimension <= 840) {
			return 4;
		}
		return 12;
	}

	private Integer calculatePercent(Percent percent) {

		if (dimension <= 600) {
			return getNumberOfColumn();
		}

		if (dimension > 840) {
			int value = SWT.DEFAULT;
			switch (percent) {
			case W10:
				value = 1;
				break;
			case W15:
				value = 1;
				break;
			case W20:
				value = 2;
				break;
			case W25:
				value = 3;
				break;
			case W33:
				value = 4;
				break;
			case W40:
				value = 5;
				break;
			case W45:
				value = 5;
				break;
			case W50:
				value = 6;
				break;
			case W75:
				value = 9;
				break;
			case W80:
				value = 10;
				break;
			case W90:
				value = 11;
				break;
			case W100:
				value = 12;
				break;
			default:
				break;
			}
			return value;
		}

		int value = SWT.DEFAULT;
		switch (percent) {
		case W10:
			value = 1;
			break;
		case W15:
			value = 1;
			break;
		case W20:
			value = 2;
			break;
		case W25:
			value = 2;
			break;
		case W33:
			value = 2;
			break;
		case W40:
			value = 3;
			break;
		case W45:
			value = 3;
			break;
		case W50:
			value = 4;
			break;
		case W75:
			value = 6;
			break;
		case W80:
			value = 6;
			break;
		case W90:
			value = 7;
			break;
		case W100:
			value = 8;
			break;
		default:
			break;
		}
		return value;

	}

	@Override
	public void insertComposite(Composite composite, Composite top) {
		// FIXME MOSTRARLO Y OCULTARLO

	}

	@Override
	public void addHeader(Label header) {
		this.reset();
		GridData gridData = new GridData(SWT.LEFT, SWT.CENTER, false, false, getNumberOfColumn(), 1);
		header.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		header.setLayoutData(gridData);
		this.reset();
	}

	@Override
	public void addSeparator(Label separator) {
		this.reset();
		GridData gridData = new GridData(SWT.LEFT, SWT.CENTER, true, false, getNumberOfColumn(), 1);
		separator.setLayoutData(gridData);
		this.reset();
	}

	@Override
	public void add(Control control) {
		if (control.getClass().equals(CRUDTreeTable.class) || control.getClass().equals(SecurityCRUDTreeTable.class)) {
			GridData d = new GridData(SWT.LEFT, SWT.CENTER, true, false, calculatePercent(Percent.W100), 1);
			control.setLayoutData(d);
			return;
		}
		if (dimension > 840) {
			if (control.getClass().equals(Group.class)) {
				GridData d = new GridData(SWT.LEFT, SWT.CENTER, true, false, calculatePercent(Percent.W50), 1);
				control.setLayoutData(d);
			} else {
				GridData d = new GridData(SWT.LEFT, SWT.CENTER, true, false, calculatePercent(Percent.W25), 1);
				control.setLayoutData(d);
			}
		} else {
			if (dimension > 600) {
				if (control.getClass().equals(Group.class)) {
					GridData d = new GridData(SWT.LEFT, SWT.CENTER, true, false, calculatePercent(Percent.W100), 1);
					control.setLayoutData(d);
				} else {
					GridData d = new GridData(SWT.LEFT, SWT.CENTER, true, false, calculatePercent(Percent.W50), 1);
					control.setLayoutData(d);
				}
			} else {
				GridData d = new GridData(SWT.LEFT, SWT.CENTER, true, false, calculatePercent(Percent.W100), 1);
				control.setLayoutData(d);
			}
		}
	}

	@Override
	public void add(Control control, Percent percent) {
		if (dimension > 840) {
			GridData d = new GridData(SWT.LEFT, SWT.CENTER, true, false, calculatePercent(percent), 1);
			control.setLayoutData(d);
		} else {
			if (dimension > 600 && dimension <= 840) {
				if (percent.getValue() < Percent.W25.getValue()) {
					GridData d = new GridData(SWT.LEFT, SWT.CENTER, true, false, calculatePercent(Percent.W25), 1);
					control.setLayoutData(d);
				} else {
					if (percent.getValue() >= Percent.W25.getValue() && percent.getValue() < Percent.W50.getValue()) {
						GridData d = new GridData(SWT.LEFT, SWT.CENTER, true, false, calculatePercent(Percent.W50), 1);
						control.setLayoutData(d);
					} else {
						GridData d = new GridData(SWT.LEFT, SWT.CENTER, true, false, calculatePercent(Percent.W100), 1);
						control.setLayoutData(d);
					}
				}
			} else {
				if (percent.getValue() < Percent.W25.getValue()) {
					GridData d = new GridData(SWT.LEFT, SWT.CENTER, true, false, calculatePercent(Percent.W50), 1);
					control.setLayoutData(d);
				} else {
					GridData d = new GridData(SWT.LEFT, SWT.CENTER, true, false, calculatePercent(Percent.W100), 1);
					control.setLayoutData(d);
				}
			}
		}

	}

	@Override
	public void dispose() {
		while (!composites.isEmpty()) {
			composites.pop().dispose();
		}
	}

	@Override
	public void reset() {
		int cant = composites.peek().getChildren().length;
		int resto = cant % getNumberOfColumn();
		int relleno = getNumberOfColumn() - resto;

		while (relleno != 0) {
			new Label(composites.peek(), SWT.NONE);
			relleno--;
		}

	}

	@Override
	public void setDimension(Integer dimension) {
		this.dimension = dimension;

	}
}
