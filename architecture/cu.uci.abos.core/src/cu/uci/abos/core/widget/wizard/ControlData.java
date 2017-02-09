package cu.uci.abos.core.widget.wizard;

public class ControlData {

	private String label;
	private Object value;

	public ControlData(String label, Object value) {
		this.label = label;
		this.value = value;
	}

	public ControlData(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Object getValue() {
		return this.value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
