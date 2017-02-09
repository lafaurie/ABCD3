package cu.uci.abos.core.widget.compoundlabel;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;

public class CompoundLabelForm extends Composite {
	private static final long serialVersionUID = 1L;
	private int maxLabelWidth = 0;
	private List<Compound> list;

	public CompoundLabelForm(Composite parent, int style) {
		super(parent, style);
		list = new ArrayList<>();
		setLayout(new CompoundLabelFormLayout());
		setData(RWT.CUSTOM_VARIANT, "gray_background");
	}

	public Compound addCompoundLabel(String labelText, CompoundLabelType type) {
		Compound newCompound = new Compound(this, SWT.NONE, type);
		newCompound.setLabelText(labelText);
		int nLabelWidth = newCompound.getLabel().getSize().x;
		if (maxLabelWidth < nLabelWidth) {
			maxLabelWidth = nLabelWidth;
			for (int i = 0; i < list.size(); i++) {
				Compound auxCompound = list.get(i);
				auxCompound.setComponentSpacing(maxLabelWidth);
				Point auxPoint = auxCompound.computeSize(SWT.DEFAULT, SWT.DEFAULT, false);
				auxCompound.setSize(auxPoint);
			}
		} else if (maxLabelWidth > nLabelWidth) {
			newCompound.setComponentSpacing(maxLabelWidth);
			Point auxPoint = newCompound.computeSize(SWT.DEFAULT, SWT.DEFAULT, false);
			newCompound.setSize(auxPoint);
		}
		list.add(newCompound);
		layout(true);
		getParent().layout();
		return newCompound;
	}

	public List<Compound> getCompoundList() {
		return list;
	}
}
