package cu.uci.abos.core.widget.paginator;

import cu.uci.abos.core.widget.grid.SortData;

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

	public int getSortDirection() {
		return sortData != null ? sortData.sortDirection : 1024;
	}

	public String getSortExpression() {
		return sortData != null ? parseExpression(sortData.sortExpression) : "";
	}

	String parseExpression(String expression) {
		String methods[] = expression.split("\\.");
		StringBuilder sb = new StringBuilder();
		if (methods.length > 0) {
			for (int i = 0; i < methods.length; i++) {
				if (methods[i].startsWith("get")) {
					sb.append(Character.toLowerCase(methods[i].charAt(3)));
					sb.append(methods[i].substring(4));
					if ((i + 1) < methods.length) {
						sb.append(".");
					}
				}
			}
		} else {
			if (expression.startsWith("get")) {
				sb.append(Character.toLowerCase(expression.charAt(3)));
				sb.append(expression.substring(4));
			}
		}
		return sb.toString();
	}
}
