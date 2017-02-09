package cu.uci.abos.widget.show.content.showContentPaginatorEvent;


public class PageChangeEvent {
	
	public int oldPage;
	public int currentPage;
	public boolean isFirst;
	public boolean isLast;
	public int totalPages;
	public int pageSize;
	public int startIndex;
	public int endIndex;
	public int totalElements;
	
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
