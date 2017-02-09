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
		if (dimension > 720) {
			if (helper.getUbication().equals(1)) {
				helper.setUbication(2);
				paintLeftControl(control, calculatePercent(dimension, percent));
				return (T) this;
			}
			if (helper.getUbication().equals(2)) {
				helper.setUbication(3);
				paintLeftMiddleControl(control, calculatePercent(dimension, percent));
				return (T) this;
			}
			if (helper.getUbication().equals(3)) {
				helper.setUbication(4);
				paintRightMiddleControl(control, calculatePercent(dimension, percent));
				return (T) this;
			}
			if (helper.getUbication().equals(4)) {
				helper.setUbication(1);
				paintRightControl(control, calculatePercent(dimension, percent));
				return (T) this;
			}
		}
		if (dimension > 320) {
			if (helper.getUbication() < 3) {
				helper.setUbication(3);
				paintLeftControl(control, calculatePercent(dimension, percent));
				return (T) this;
			}
			if (helper.getUbication() > 2) {
				helper.setUbication(1);
				paintRightControl(control, calculatePercent(dimension, percent));
				return (T) this;
			}
		}
		return (T) this;
	}

	protected abstract Control paintLeftMiddleControl(Control control, Integer calculatePercent);

	protected abstract Control paintRightMiddleControl(Control control, Integer calculatePercent);

	protected abstract Control paintRightControl(Control control, Integer calculatePercent);

	protected Integer calculatePercent(Integer dimension, Percent percent) {
		float factor = 0.8f;
		if (dimension > 320 && dimension < 720) {
			factor = 0.5f;
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

	protected abstract Control paintLeftControl(Control control, Integer percent);

	public Painter reset() {
		helper.reset();
		return this;
	}

}
