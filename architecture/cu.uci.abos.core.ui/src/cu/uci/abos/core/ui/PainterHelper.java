package cu.uci.abos.core.ui;

import org.eclipse.swt.widgets.Control;

public class PainterHelper {

	private Control top;

	private Control last;

	private Control left;

	private Integer dimension;

	private Integer ubication;

	public PainterHelper() {
		super();
		ubication = 1;
	}

	public Control getTop() {
		if (top==null) {
			throw new RuntimeException("TOP IS NULL");
		}
		return top;
	}

	public void setTop(Control top) {
		
		this.top = top;
	}

	public Control getLast() {
		if (last==null) {
			throw new RuntimeException("LAST IS NULL");
		}
		return last;
	}

	public void setLast(Control last) {
		this.last = last;
	}

	public Control getLeft() {
		if (left==null) {
			throw new RuntimeException("LEFT IS NULL");
		}
		return left;
	}

	public void setLeft(Control left) {
		this.left = left;
	}

	public Integer getDimension() {
		return dimension;
	}

	public void setDimension(Integer dimension) {
		this.dimension = dimension;
	}

	public Integer getUbication() {
		return ubication;
	}

	public void setUbication(Integer ubication) {
		this.ubication = ubication;
	}

	public PainterHelper(Control top, Control last, Control left, Integer dimension, Integer ubication) {
		super();
		this.top = top;
		this.last = last;
		this.left = left;
		this.dimension = dimension;
		this.ubication = ubication;
	}

	public void reset() {
		ubication = 1;
		if (left != null) {
			top = left;
		}
	}
}
