package cu.uci.abos.widgets.paginator;

import cu.uci.abos.widgets.grid.SortData;

public class PageChangedEvent {
	public int oldPage;
	public int currentPage;
	public boolean isFirst;
	public boolean isLast;
	public int totalPages;
	public int pageSize;
	public int startIndex;
	public int endIndex;
	public int totalElements;
	public SortData sortData;
}
