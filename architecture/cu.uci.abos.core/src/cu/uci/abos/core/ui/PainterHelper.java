package cu.uci.abos.core.ui;

import org.eclipse.swt.widgets.Control;

public class PainterHelper {

	private Control top;

	private Control last;

	private Control left;
	
	private Integer counter;

	private Integer dimension;

	private Integer ubication;

	private boolean newLine;

	private Control topByheight;

	public PainterHelper() {
		super();
		ubication = 0;
		counter =0;
		newLine = false;
	}

	public void setCounter(Integer counter) {
		this.counter = counter;
	}

	public Control getTop() {
		if (top == null) {
			throw new RuntimeException("TOP IS NULL");
		}
		if (newLine && topByheight != null) {
			top = topByheight;
		}
		return top;
	}

	public void setTop(Control top) {
		this.top = top;
	}
	
	public void increment(){
		++counter;
	}

	public Control getLast() {
		if (last == null) {
			throw new RuntimeException("LAST IS NULL");
		}
		return last;
	}

	public void setLast(Control last) {
		this.last = last;
		if (last.getBounds().height > topByheight.getBounds().height) {
			topByheight = last;
		}
	}

	public Control getLeft() {
		if (left == null) {
			throw new RuntimeException("LEFT IS NULL");
		}
		return left;
	}

	public void setLeft(Control left) {
		this.left = left;
		topByheight = left;
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
		if (this.ubication >= 100) {
			this.ubication = 0;
		}
		if (this.ubication.equals(0)) {
			newLine = true;
			counter=0;
		} else {
			newLine = false;
		}
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
		setUbication(0);
		counter =0;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public Control getTopByheight() {
		return topByheight;
	}

	public void updateUbication(Integer value) {
		setUbication(getUbication() + value);
	}

}
