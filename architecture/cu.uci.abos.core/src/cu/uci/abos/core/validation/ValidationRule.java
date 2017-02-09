package cu.uci.abos.core.validation;

import org.eclipse.swt.widgets.Control;

public abstract class ValidationRule {
	
  protected Control[]controls;
  
public ValidationRule setControls(Control[]controls) {
	this.controls = controls;
	return this;
}

 public  abstract boolean validate(String group);
 
 public  abstract String getErrorMessage();
  
}
