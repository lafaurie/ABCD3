package cu.uci.abos.core.widget.grid;

import java.util.List;

import cu.uci.abos.core.widget.paginator.PageChangeListener;

public interface TreeTable {
	List<String> getColumnHeaders();

	String[][] getCellValues();

	void refresh();

	void addDeleteListener(TreeColumnListener listener);

	void addUpdateListener(TreeColumnListener listener);

	void addPageChangeListener(PageChangeListener listener);

	void addActionButton(String key, ButtonData buttonData);

	void setActionButtonText(String key, String text);

	void setAddButtonText(String text);
	
	void onPageChange() ;
	
	void clearRows() ;


}
