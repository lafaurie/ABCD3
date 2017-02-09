package cu.uci.abos.core.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

public interface PagePainter {
	
	void addComposite(Composite composite, Percent percent);

	void addComposite(Composite composite);
	
	void insertComposite(Composite composite,Composite top);

	void addHeader(Label header);

	void addSeparator(Label separator);

	void add(Control control);

	void add(Control control, Percent percent);
	
	void dispose();
	
	void reset();
	
	void setDimension(Integer dimension);

}
