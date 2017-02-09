package cu.uci.abcd.management.db.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.management.db.l10n.AbosMessages;
import cu.uci.abos.core.ui.FormPagePainter;
import cu.uci.abos.core.ui.PagePainter;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;
import cu.uci.abos.core.widget.grid.IEditableArea;

public class ASCEditionArea implements IEditableArea {

    private Map<String, Control> controls;
    Composite result;
    Table table;
    TableColumn column1, column2;    
    TableItem item1, item2;
    Label lab, lab1;
    Text txt;
    Button check;
    PagePainter painter;

    public ASCEditionArea() {
    	this.controls = new HashMap<String, Control>();
    }

    @Override
    public Composite createUI(final Composite parent, IGridViewEntity entity, IVisualEntityManager manager) {  
    	painter = new FormPagePainter();
		result = new Composite(parent, SWT.None);
		result.setLayout(new FormLayout());	
		painter.addComposite(result);
		
		lab1 = new Label(result, SWT.NONE);	
		painter.add(lab1);
		//FormDatas.attach(lab1).atTopTo(result, 5).atLeft(5);
		
		table = new Table(result, SWT.BORDER | SWT.FULL_SELECTION);
		painter.add(table);
		table.setLinesVisible(true);
		column1 = new TableColumn(table, SWT.RIGHT);
		
		column1.setWidth(100);
		column2 = new TableColumn(table, SWT.NONE);
		column2.setWidth(70);
		item1 = new TableItem(table, SWT.NONE);		
		item1.setText(1,"001");
		item2 = new TableItem(table, SWT.NONE);		
		item2.setText(1,"CN_");
		painter.add(table);
		//FormDatas.attach(table).atTopTo(lab1, 2).atLeft(5);
		
		lab = new Label(result, SWT.NONE);	
		painter.add(lab);
//		FormDatas.attach(lab).atTopTo(table, 10).atLeft(5);
	
		txt = new Text(result, SWT.NONE);	
		painter.add(txt);
		//FormDatas.attach(txt).atTopTo(lab, 1).atLeft(5).withHeight(12);
	
		check = new Button(result, SWT.CHECK);
		painter.add(check);
		//FormDatas.attach(check).atTopTo(txt, 5).atLeft(5);
	    l10n();
		return parent;
    }

    @Override
    public Composite createButtons(Composite parent, final IGridViewEntity entity, final IVisualEntityManager manager) {
	    Button addBtn = new Button(parent, SWT.PUSH);
		addBtn.setText(cu.uci.abos.core.l10n.AbosMessages.get().BUTTON_ADD);
	
//		addBtn.addSelectionListener(new SelectionListener() {
//		    private static final long serialVersionUID = 1L;
////	
//		    @Override
//		    public void widgetSelected(SelectionEvent arg0) {
//			
//	    }
//
//	    @Override
//	    public void widgetDefaultSelected(SelectionEvent arg0) {
//	    	// do nothing
//	    }
//	});

	return parent;
    }
    
	@Override
	public void l10n() {
		item1.setText(0,AbosMessages.get().ID_FST);
		item2.setText(0,AbosMessages.get().PREFIJO);
		lab.setText(AbosMessages.get().NOMBRE_CAMPO);
		txt.setText(MessageUtil.unescape(AbosMessages.get().NUMERO_CONTROL));
		check.setText(AbosMessages.get().ORDENABLE);
		lab1.setText(AbosMessages.get().SELECTED_FIELD);
	}

	@Override
	public boolean isValid() {
		// TODO Iterar el mapa de controles y verificar su validez.
		return false;
	}

	public Control getControl(String key) {
		return this.controls.get(key);
	}

	public void dispose() {
		for(Control control : controls.values()) {
			control.dispose();
		}
	}

	@Override
	public boolean closable() {
		// TODO Auto-generated method stub
		return false;
	}
}
