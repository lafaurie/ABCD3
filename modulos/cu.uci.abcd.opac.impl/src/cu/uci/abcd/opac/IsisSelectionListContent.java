package cu.uci.abcd.opac;
//FIXME FALTAN COMENTARIOS DE INTERFACE
import java.util.ArrayList;
import java.util.List;

public class IsisSelectionListContent {

	private List<String> controlNumber = new ArrayList<String>();
	private String dataBaseName;
	private String isisHome;    
	
	public IsisSelectionListContent(List<String> controlNumber, String dataBaseName, String isisHome) {
		this.controlNumber = controlNumber;	
		this.dataBaseName = dataBaseName;
		this.isisHome = isisHome;
	}	
	
	public List<String> getControlNumber() {
		return controlNumber;
	}

	public void setControlNumber(List<String> controlNumber) {
		this.controlNumber = controlNumber;
	}

	public String getDataBaseName() {
		return dataBaseName;
	}

	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}

	public String getIsisHome() {
		return isisHome;
	}

	public void setIsisHome(String isisHome) {
		this.isisHome = isisHome;
	}	
}
