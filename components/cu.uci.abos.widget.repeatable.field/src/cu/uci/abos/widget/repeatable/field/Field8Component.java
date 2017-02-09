package cu.uci.abos.widget.repeatable.field;

import java.util.ArrayList;

import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.widget.repeatable.field.domain.FieldDomain;
import cu.uci.abos.widget.repeatable.field.util.FieldStructure;

public class Field8Component extends Composite {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private static final long serialVersionUID = 1L;

	private ArrayList<FieldStructure> children;
	private FieldStructure fieldStructure;

	public Field8Component(Composite parent, int style, ArrayList<FieldDomain> fieldsDomain,
			ArrayList<FieldStructure> children) {
		super(parent, style);
		this.children = children;
		int fieldCount = fieldsDomain.size();

		for (int i = 0; i < fieldCount; i++) {
			//implementar cada campo

			FieldDomain fieldDomain = fieldsDomain.get(i);

			boolean repeatedField = fieldDomain.isRepeatableField();
			int tag = fieldDomain.getTag();
			Control control = null;

			fieldStructure = new FieldStructure(tag, control, repeatedField);
			children.add(fieldStructure);

			SingleField8Component singleComponent = new SingleField8Component(this, style, fieldDomain,
					children, fieldStructure, control);
			singleComponent.setLayout(new FormLayout());

			if(i==0){
				FormDatas.attach(this.getTabList()[i]).atTopTo(this, 5);	
			}
			else{
				FormDatas.attach(this.getTabList()[i]).atTopTo(this.getTabList()[i-1], 10);
			}
		}
		layout(true);
	}

	public ArrayList<FieldStructure> getChildrens(){
		return this.children;
	}
}
