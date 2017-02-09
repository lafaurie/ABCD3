package cu.uci.abos.core.domain;

public class Sort {
	private int page;
	private int size;
	private int direction;
	private String orderByString;

	public Sort(int page, int size) {
		super();
		this.page = page;
		this.size = size;
		this.direction = 1024;
		orderByString = "";
	}

	public int getPage() {
		return page;
	}

	public int getSize() {
		return size;
	}

	public int getDirection() {
		return direction;
	}

	public String getOrderByString() {
		return orderByString;
	}

	public Sort(int page, int size, String orderByString) {
		super();
		this.page = page;
		this.size = size;
		this.direction = 1024;
		this.orderByString = orderByString;
	}

	public Sort(int page, int size, int direction, String orderByString) {
		super();
		this.page = page;
		this.size = size;
		this.direction = 1024;
		this.direction = direction;
		this.orderByString = orderByString;
	}
	
	

}
