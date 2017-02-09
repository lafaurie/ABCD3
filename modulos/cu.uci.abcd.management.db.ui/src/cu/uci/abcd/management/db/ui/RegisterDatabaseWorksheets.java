package cu.uci.abcd.management.db.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import cu.uci.abcd.management.db.l10n.AbosMessages;
import cu.uci.abos.api.ui.FragmentContributor;
import cu.uci.abos.core.ui.FormPagePainter;
import cu.uci.abos.core.ui.PagePainter;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.NotPaginateTable;
import cu.uci.abos.core.widget.grid.TreeTableColumn;

public class RegisterDatabaseWorksheets implements FragmentContributor{

	PagePainter painter;
	Composite up;
	Composite leftComposite;
	Composite centerComposite;
	Composite downComposite;
	NotPaginateTable tabla;
	Label databaseStructureLabel;
	Label tagLabel;
	Label nameFieldLabel;
	Label subfieldsPatternLabel;
	Label typeLabel;
	Label indicatorsLabel;
	Label repLabel;
	Label firstSubfieldLabel;
	Label addButton;
	Label deleteButton;
	List<Auxiliar> listStructure = new ArrayList<>();
	
	public List<Auxiliar> getListStructure() {
		return listStructure;
	}

	public void setListStructure(List<Auxiliar> listStructure) {
		this.listStructure = listStructure;
	}

	public RegisterDatabaseWorksheets(List<Auxiliar> listStructure) {
		this.listStructure = listStructure;
	}

	public RegisterDatabaseWorksheets() {
		
	}


	@Override
	public Control createUIControl(Composite parent) {
		painter = new FormPagePainter();
		up = new Composite(parent, SWT.NONE);
		painter.addComposite(up);
		up.setData(RWT.CUSTOM_VARIANT, "gray_background");

		Composite centerComposite = new Composite(parent, SWT.BORDER);
		centerComposite.setLayout(new FormLayout());
		centerComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(centerComposite).atBottomTo(downComposite, 5).atTop(5).atLeftTo(leftComposite).atRight(5);

		
		
		Label field = new Label(up, SWT.NONE);
		field.setText("Fields Defined in the FDT:");
		painter.addHeader(field);
		
		tabla = new NotPaginateTable(up, SWT.NONE);
		tabla.setEntityClass(RegisterDatabaseStructure.class);

		TreeTableColumn tableColumns[] = {
                new TreeTableColumn(10, 0, "getEtiqueta"),
                new TreeTableColumn(35, 1, "getNombre"),
                new TreeTableColumn(10, 2, "getSubcampos"),
                new TreeTableColumn(10, 3, "getType"),
                new TreeTableColumn(10, 4, "isIndicadores"),
                new TreeTableColumn(10, 5, "isRepetible"),
                new TreeTableColumn(15, 6, "isPrimersubcampo") };

        tabla.createTable(tableColumns);        
        painter.add(tabla);


        tabla.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_TAG),
        		MessageUtil.unescape(AbosMessages.get().LABEL_FIELD), 
        		MessageUtil.unescape(AbosMessages.get().LABEL_TYPE),
        		MessageUtil.unescape(AbosMessages.get().LABEL_INDICATORS),
        		MessageUtil.unescape(AbosMessages.get().LABEL_REPEATABLE),
        		MessageUtil.unescape(AbosMessages.get().LABEL_FIRST_SUBFIELD),
        		MessageUtil.unescape(AbosMessages.get().LABEL_SUBFIELDS_PATTERN)));
        		
       	
     
		l10n();
		
		return up;
	}


	
	public void updateTable(){
		tabla.setRows(listStructure);
		tabla.refresh();
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void l10n() {
		
		
	}

	@Override
	public Control getControl(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}

