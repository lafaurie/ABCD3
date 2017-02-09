package cu.uci.abos.widgets.grid;

public class TreeTableColumn {
	
	public final int percentWidth;
	public final  int index;
	public final String method;
	
	public TreeTableColumn(int percentWidth, int index, String method) {
		super();
		this.percentWidth = percentWidth;
		this.index = index;
		this.method = method;
	}
}
