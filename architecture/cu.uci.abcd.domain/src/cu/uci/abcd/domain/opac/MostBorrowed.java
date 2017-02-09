package cu.uci.abcd.domain.opac;

public class MostBorrowed {
	
	private String title;	
	private String controlNumber;
	private int borrowedQuantity;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getControlNumber() {
		return controlNumber;
	}
	public void setControlNumber(String controlNumber) {
		this.controlNumber = controlNumber;
	}
	public int getBorrowedQuantity() {
		return borrowedQuantity;
	}
	public void setBorrowedQuantity(int borrowedQuantity) {
		this.borrowedQuantity = borrowedQuantity;
	}

}
