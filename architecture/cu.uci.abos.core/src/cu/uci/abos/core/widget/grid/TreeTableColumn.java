package cu.uci.abos.core.widget.grid;

public class TreeTableColumn {
	
	public final int percentWidth;
	public final  int index;
	public final  boolean order;
	public final String method;
	public final Object[] param;
	public final String notDefinedMsg;
	
	public TreeTableColumn(int percentWidth, int index, String method) {
		super();
		this.percentWidth = percentWidth;
		this.index = index;
		this.method = method;
		param =null;
		order =false;
		notDefinedMsg=null;
	}
	
	public TreeTableColumn(int percentWidth, int index, String method, Object[] param) {
		super();
		this.percentWidth = percentWidth;
		this.index = index;
		this.method = method;
		this.param = param;
		order =false;
		notDefinedMsg=null;
	}

	public TreeTableColumn(int percentWidth, int index, String method, String notDefinedMsg) {
		super();
		this.percentWidth = percentWidth;
		this.index = index;
		this.method = method;
		order =false;
		param =null;
		this.notDefinedMsg = notDefinedMsg;
	}
	
	public TreeTableColumn(int percentWidth, int index, String method, String notDefinedMsg, boolean order) {
		super();
		this.percentWidth = percentWidth;
		this.index = index;
		this.method = method;
		this.order =order;
		param =null;
		this.notDefinedMsg = notDefinedMsg;
	}

	public TreeTableColumn(int percentWidth, int index, boolean order, String method, Object[] param, String notDefinedMsg) {
		super();
		this.percentWidth = percentWidth;
		this.index = index;
		this.order = order;
		this.method = method;
		this.param = param;
		this.notDefinedMsg = notDefinedMsg;
	}

	public TreeTableColumn(int percentWidth, int index, boolean order, String method, Object[] param) {
		super();
		this.percentWidth = percentWidth;
		this.index = index;
		this.order = order;
		this.method = method;
		this.param = param;
		this.notDefinedMsg = null;
	}

	public TreeTableColumn(int percentWidth, int index, String method,boolean order) {
		super();
		this.percentWidth = percentWidth;
		this.index = index;
		this.order = order;
		this.method = method;
		this.notDefinedMsg = null;
		this.param =null;
	}

	public TreeTableColumn(int percentWidth, int index, String method, Object[] param, String notDefinedMsg) {
		super();
		this.percentWidth = percentWidth;
		this.index = index;
		this.method = method;
		this.param = param;
		order =false;
		this.notDefinedMsg = notDefinedMsg;
	}
}
