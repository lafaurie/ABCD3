package cu.uci.abos.widgets.grid.demo;

import java.util.List;

public class Page<T> {

	private List<T> elements;
	private int totalElements;
	
	public Page(List<T> elements, int totalElements) {
		super();
		this.elements = elements;
		this.totalElements = totalElements;
	}
	
	public List<T> getElements() {
		return elements;
	}
	public int getTotalElements() {
		return totalElements;
	}
	public int getPageElementsCount() {
		return this.elements.size();
	}
}
