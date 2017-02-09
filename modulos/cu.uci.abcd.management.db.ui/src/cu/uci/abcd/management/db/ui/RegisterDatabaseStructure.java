package cu.uci.abcd.management.db.ui;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.unesco.jisis.corelib.pft.cisis.DeleteAllFieldsCmd;

import cu.uci.abcd.administration.nomenclators.model.NomenclatorUpdateEditableArea;
import cu.uci.abcd.administration.nomenclators.model.NomenclatorWatchEditableArea;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.management.db.l10n.AbosMessages;
import cu.uci.abos.api.ui.FragmentContributor;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.ui.FormPagePainter;
import cu.uci.abos.core.ui.PagePainter;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.CRUDTreeTableUtils;
import cu.uci.abos.core.widget.grid.MessageVisualEntityManager;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeColumnListener;
import cu.uci.abos.core.widget.grid.TreeTableColumn;

public class RegisterDatabaseStructure extends ContributorPage implements FragmentContributor {
	Label databaseStructureLabel;
	Label buttonSeparator;
	Label tagLabel;
	Spinner spinner;
	Label nameFieldLabel;
	Text nameFieldText;
	Label typeLabel;
	Combo comboType;
	Label indicatorsLabel;
    Button indicatorsCheckbox;
    Label repLabel;
    Button repetCheckbox;
    Label firstSubfieldLabel;
    Button firstSubfieldCheckbox;
    Label subfieldsPatternLabel;
    Text subfieldsPatternText;
    Button  addButton;
    Button deleteButton;
    CRUDTreeTable tabla;
	PagePainter painter;
	Composite up;
	Text nameFieldText1;
	private List<Auxiliar> auxiliarList = new ArrayList<>();
	
	public List<Auxiliar> getAuxiliarList() {
		return auxiliarList;
	}

	public void setAuxiliarList(List<Auxiliar> auxiliarList) {
		this.auxiliarList = auxiliarList;
	}

	@Override
	public Control createUIControl(Composite parent) {
		
		painter = new FormPagePainter();
		up = new Composite(parent, SWT.NONE);
		painter.addComposite(up);
		up.setData(RWT.CUSTOM_VARIANT, "gray_background");

		databaseStructureLabel = new Label(up, SWT.NONE);
		painter.addHeader(databaseStructureLabel);
		
		buttonSeparator = new Label(up, SWT.HORIZONTAL | SWT.SEPARATOR);
		painter.addSeparator(buttonSeparator);
		
		tagLabel = new Label(up, SWT.NONE);
		painter.add(tagLabel, Percent.W10);
	
        spinner = new Spinner(up, SWT.BORDER);
        spinner.setMinimum(1);	
        spinner.setMaximum(2147483647);
        painter.add(spinner, Percent.W10);
        
        nameFieldLabel = new Label(up, SWT.NONE);
        painter.add(nameFieldLabel);
		
		nameFieldText = new Text(up, SWT.NONE);
		painter.add(nameFieldText);
		
		painter.reset();
		
		typeLabel = new Label(up, SWT.NONE);
		painter.add(typeLabel, Percent.W10);
		
		comboType  = new Combo(up, SWT.READ_ONLY);
		painter.add(comboType, Percent.W10);
		
	  
		comboType.add("-Seleccione-");
	    comboType.add(Type.ALPHANUMERIC.toString());
	    comboType.add(Type.ALPHEBETIC.toString());
	    comboType.add(Type.NUMERIC.toString());
	    comboType.add(Type.PATTERN.toString());
	    comboType.add(Type.DATE.toString());
	    comboType.add(Type.TIME.toString());
	    comboType.add(Type.BLOB.toString());
	    comboType.add(Type.URL.toString());
	    comboType.add(Type.DOC.toString());
	
	    comboType.select(0);
	    
		subfieldsPatternLabel = new Label(up, SWT.NONE);
		painter.add(subfieldsPatternLabel);
		
		nameFieldText1 = new Text(up, SWT.NONE);
		painter.add(nameFieldText1);
		
		painter.reset();
		
		indicatorsLabel = new Label(up, SWT.NONE);
		painter.add(indicatorsLabel, Percent.W10);
		
		indicatorsCheckbox = new Button(up, SWT.CHECK);
		painter.add(indicatorsCheckbox, Percent.W10);
		
		repLabel = new Label(up, SWT.NONE);
		painter.add(repLabel);
		
		repetCheckbox = new Button(up, SWT.CHECK);
		painter.add(repetCheckbox);
		
		painter.reset();
		
		firstSubfieldLabel = new Label(up, SWT.NONE);
		painter.add(firstSubfieldLabel);
		
		firstSubfieldCheckbox = new Button(up, SWT.CHECK);
		painter.add(firstSubfieldCheckbox);		
		
		painter.reset();
			
		deleteButton = new Button(up, SWT.NONE);
		painter.add(deleteButton);	
		
		addButton = new Button(up, SWT.NONE);
		painter.add(addButton);
		
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int etiqueta = Integer.parseInt(spinner.getText());
				String nombre = nameFieldText.getText() ;
				String subcampos = nameFieldText1.getText();
				
				boolean primersubcampo = firstSubfieldCheckbox.getSelection();
		        boolean indicadores = indicatorsCheckbox.getSelection();
				boolean repetible = repetCheckbox.getSelection();
				
				Auxiliar auxiliar = new Auxiliar(etiqueta, nombre, subcampos,
						indicadores, repetible, primersubcampo, Type.valueOf(comboType.getText()) );
				
				boolean flag = false;
				for (int i = 0; i < auxiliarList.size(); i++) {
					if( auxiliarList.get(i).getEtiqueta()== etiqueta){
						flag = true;
						auxiliarList.get(i).setNombre(auxiliar.getNombre());
						auxiliarList.get(i).setIndicadores(auxiliar.isIndicadores());
						auxiliarList.get(i).setPrimersubcampo(auxiliar.isPrimersubcampo());
						auxiliarList.get(i).setRepetible(auxiliar.isRepetible());
						auxiliarList.get(i).setSubcampos(auxiliar.getSubcampos());
						auxiliarList.get(i).setType(auxiliar.getType());
				}}
				if(!flag){
					auxiliarList.add(auxiliar);
				}
					tabla.setRows(auxiliarList);
					tabla.refresh();
					
				
				
			}}
		);
	
		
		tabla = new CRUDTreeTable(up, SWT.NONE);
		tabla.setEntityClass(RegisterDatabaseStructure.class);

        TreeTableColumn tableColumns[] = {
                new TreeTableColumn(10, 0, "getEtiqueta"),
                new TreeTableColumn(15, 1, "getNombre"),
                new TreeTableColumn(15, 2, "getSubcampos"),
                new TreeTableColumn(15, 3, "getType"),
                new TreeTableColumn(15, 4, "isIndicadores"),
                new TreeTableColumn(15, 5, "isRepetible"),
                new TreeTableColumn(15, 6, "isPrimersubcampo") };

        tabla.createTable(tableColumns);        
        tabla.setPageSize(10);
        painter.add(tabla);


        tabla.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_TAG),
        		MessageUtil.unescape(AbosMessages.get().LABEL_FIELD), 
        		MessageUtil.unescape(AbosMessages.get().LABEL_SUBFIELDS_PATTERN),
        		MessageUtil.unescape(AbosMessages.get().LABEL_TYPE),
        		MessageUtil.unescape(AbosMessages.get().LABEL_INDICATORS),
        		MessageUtil.unescape(AbosMessages.get().LABEL_REPEATABLE),
        		MessageUtil.unescape(AbosMessages.get().LABEL_FIRST_SUBFIELD)));
		
        
    	deleteButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {	
			
			
		    
			//tabla.clearRows(); //Borra todo, no el que esta seleccionado
			//tabla.refresh();
                }});
        
    	
    	
    	
				
		l10n();
		return up;
	}

	@Override
	public void l10n() {
		databaseStructureLabel.setText(MessageUtil.escape((AbosMessages.get().DATABASE_STRUCTURE_LABEL)));	
		tagLabel.setText(MessageUtil.escape((AbosMessages.get().LABEL_TAG)));
		nameFieldLabel.setText(MessageUtil.escape((AbosMessages.get().LABEL_FIELD)));
		subfieldsPatternLabel.setText(MessageUtil.escape((AbosMessages.get().LABEL_SUBFIELDS_PATTERN)));
		typeLabel.setText(MessageUtil.escape((AbosMessages.get().LABEL_TYPE)));
		indicatorsLabel.setText(MessageUtil.escape((AbosMessages.get().LABEL_INDICATORS)));
		repLabel.setText(MessageUtil.escape((AbosMessages.get().LABEL_REPEATABLE)));
		firstSubfieldLabel.setText(MessageUtil.escape((AbosMessages.get().LABEL_FIRST_SUBFIELD)));
		addButton.setText(MessageUtil.escape((AbosMessages.get().BUTTON_ADD)));
		deleteButton.setText(MessageUtil.escape((AbosMessages.get().BUTTON_DELETE)));
	
		
		//initialize(comboType, FDType.FDTValues());
		
	}

	
	
	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Control getControl(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String contributorName() {
		// TODO Auto-generated method stub
		return null;
	}


}
