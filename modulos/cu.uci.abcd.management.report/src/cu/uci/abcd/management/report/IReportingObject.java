package cu.uci.abcd.management.report;

import java.util.List;

public class IReportingObject {
	private String headers;
	private List<String> leftValue;
	private List<String> rigthValue;
	
	
	public String getHeaders() {
		return headers;
	}
	public void setHeaders(String headers) {
		this.headers = headers;
	}
	public List<String> getLeftValue() {
		return leftValue;
	}
	public void setLeftValue(List<String> leftValue) {
		this.leftValue = leftValue;
	}
	public List<String> getRigthValue() {
		return rigthValue;
	}
	public void setRigthValue(List<String> rigthValue) {
		this.rigthValue = rigthValue;
	}
}
