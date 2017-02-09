package cu.uci.abos.widgets.ncombo;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

@SuppressWarnings("serial")
public class NCombo extends Combo {

	private List<INomenclator> nomenclators;
	private INomenclator selected;
	
	public NCombo(Composite parent, int style) {
		super(parent, style);
		this.nomenclators = new LinkedList<INomenclator>();
		this.addSelectionListener(new SelectionListener() {			
			@Override
			public void widgetSelected(SelectionEvent event) {
				int selectedIndex = getSelectionIndex();
				selected = nomenclators.get(selectedIndex);
				setText(selected.getName());
			}			
			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
				selected = nomenclators.get(0);
				setText(selected.getName());
			}
		});
	}

	public void setNomenclators(List<INomenclator> nomenclators) {
		this.nomenclators = nomenclators;
		String[] names = new String[nomenclators.size()];
		for(int i = 0; i < nomenclators.size(); i++) {
			INomenclator nomenclator = nomenclators.get(i);
			names[i] = nomenclator.getName();
		}
		this.setItems(names);
		this.select(0);		
	}
	
	public void setNomenclators(@SuppressWarnings("rawtypes") Class<? extends Enum> enumClass) {
		Object[] nomenclators = enumClass.getEnumConstants();
		String[] names = new String[nomenclators.length];
		for(int i = 0; i < nomenclators.length; i++) {
			INomenclator nomenclator = (INomenclator) nomenclators[i];
			this.nomenclators.add(nomenclator);
			names[i] = nomenclator.getName();
		}
		this.setItems(names);
		this.select(0);
	}
	
	public List<INomenclator> getNomenclators() {
		return this.nomenclators;
	}
	
	public INomenclator getSelectedNomenclator() {
		if(this.selected == null) {
			selected = nomenclators.get(0);
		}
		return selected;
	}
	
	public void select(INomenclator nomenclator) {
		for(int i = 0; i < this.nomenclators.size(); i++) {
			INomenclator n = this.nomenclators.get(i);
			if(n.getId().equals(nomenclator.getId())) {
				this.select(i);
				this.selected = n;
				return;
			}
		}
	}
}
