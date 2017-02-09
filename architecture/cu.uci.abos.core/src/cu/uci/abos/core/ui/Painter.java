package cu.uci.abos.core.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;

public abstract class Painter implements ControlPainter {

	protected PainterHelper helper;

	public Painter(PainterHelper helper) {
		super();
		this.helper = helper;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ControlPainter> T paint(Control control, Integer dimension, Percent percent) {
		
		if (helper.getUbication().equals(0)) {
			paintLeftControl(control, calculatePercent(dimension, percent));
			helper.increment();
			helper.updateUbication(percent.getValue());
			return retorno();
		}
		if ((helper.getUbication() + percent.getValue()) <= Percent.W50.getValue()) {
			paintLeftMiddleControl(control, calculatePercent(dimension, percent));
			helper.increment();
			helper.updateUbication(percent.getValue());
			return retorno();
		}
		if ((helper.getUbication() + percent.getValue()) <= Percent.W75.getValue()) {
			paintRightMiddleControl(control, calculatePercent(dimension, percent));
			helper.increment();
			helper.updateUbication(percent.getValue());
			return retorno();
		}
		if ((helper.getUbication() + percent.getValue()) <= Percent.W100.getValue()) {
			paintRightControl(control, calculatePercent(dimension, percent));
			reset();
			return (T) this;
		}
		if ((helper.getUbication() + percent.getValue()) > Percent.W100.getValue()) {
			reset();
			paintLeftControl(control, calculatePercent(dimension, percent));
			helper.increment();
			helper.updateUbication(percent.getValue());
			return retorno();
		}

		return retorno();
	}

	@SuppressWarnings("unchecked")
	private <T extends ControlPainter> T retorno() {
		
		// cell phones, landscape and portrait
		if (helper.getDimension() <= 600) {
			reset();
			return (T) this;
		}
		
		// tablet, landscape and portrait
		if (helper.getDimension() <=840 && helper.getCounter() % 2 == 0) {
			reset();
			return (T) this;
		}
	
		// pc,
		if ( helper.getDimension() > 840 && helper.getUbication().compareTo(Percent.W75.getValue()) > 0 && helper.getCounter() % 2 == 0) {
			reset();
			return (T) this;
		}
		return (T) this;
	}

	protected abstract Control paintLeftControl(Control control, Integer percent);

	protected abstract Control paintLeftMiddleControl(Control control, Integer calculatePercent);

	protected abstract Control paintRightMiddleControl(Control control, Integer calculatePercent);

	protected abstract Control paintRightControl(Control control, Integer calculatePercent);

	protected Integer calculatePercent(Integer dimension, Percent percent) {
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

	public Painter reset() {
		helper.reset();
		return this;
	}

	public void setTop(Control top) {
		helper.setTop(top);
	}

	public void setLast(Control last) {
		helper.setLast(last);
	}

	public void setUbication(Integer ubication) {
		helper.setUbication(ubication);
	}

}
