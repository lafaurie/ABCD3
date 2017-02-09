package cu.uci.abos.widgets.grid.demo;

import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;

import cu.uci.abos.l10n.management.db.AbosMessages;
import cu.uci.abos.ui.api.IContributor;
import cu.uci.abos.ui.api.IViewController;
import cu.uci.abos.util.api.FormDatas;
import cu.uci.abos.widgets.grid.ButtonData;
import cu.uci.abos.widgets.grid.CRUDTreeTable;
import cu.uci.abos.widgets.grid.Column;
import cu.uci.abos.widgets.grid.TreeColumnEvent;
import cu.uci.abos.widgets.grid.TreeColumnListener;
import cu.uci.abos.widgets.paginator.PageChangeListener;
import cu.uci.abos.widgets.paginator.PageChangedEvent;


public class CRUDTreeTableDemoContributor implements IContributor {

	private Group fdtGroup;
	private CRUDTreeTable fdt;	
	
	@Override
	public String contributorName() {
		return "Demo tabla";
	}

	@Override
	public String getID() {
		return "CRUDTreeTableDemoID";
	}

	@Override
	public Control createUIControl(final Composite parent) {		
		this.createFieldDefinitionTable(parent);	
		parent.notifyListeners(SWT.Resize, new Event());
		l10n();
		return parent;
	}

	@Override
	public boolean canClose() {
		return true;
	}

	@Override
	public void l10n() {
		fdtGroup.setText(AbosMessages.get().GROUP_TABLE_FDT);
		fdt.setCancelButtonText(AbosMessages.get().BUTTON_CANCEL_FDT);
		fdt.setSearchHintText(AbosMessages.get().MESSAGE_SEARCH_HINT_FDT);
		fdt.setAddButtonText(AbosMessages.get().BUTTON_ADD_FDT);
		fdt.setSaveAllButtonText(AbosMessages.get().BUTTON_SAVE_ALL_FDT);
		//fdt.setActionButtonText("restoreBtn", AbosMessages.get().BUTTON_RESTORE_FDT);
		fdt.setColumnHeaders(Arrays.asList(AbosMessages.get().HEADER_TAG_FDT, AbosMessages.get().HEADER_NAME_FDT, 
				AbosMessages.get().HEADER_TYPE, AbosMessages.get().HEADER_INDICATORS, 
				AbosMessages.get().HEADER_REP, AbosMessages.get().HEADER_FIRST_SUBFIELD, 
				AbosMessages.get().HEADER_SUBFIELDS_PATTERN_FDT));
		fdt.l10n();
	}

	@Override
	public void setViewController(IViewController controller) {
		// TODO Auto-generated method stub		
	}	
	
	@SuppressWarnings("serial")
	private Group createFieldDefinitionTable(Composite parent) {
    	fdtGroup = new Group(parent, SWT.NONE);
    	fdtGroup.setLayout(new FormLayout());
    	FormDatas.attach(fdtGroup).atTop(0).atLeft(0).atRight(0);    	
		
		fdt = new CRUDTreeTable(fdtGroup, SWT.NONE);
		FormDatas.attach(fdt).atRight(0).atLeft(0);
		fdt.setEntityClass(FDTViewEntity.class);
		
		//OJO: el orden en que se dibujan las columnas de acción depende del orden de las llamadas a setDelete, setUpdate, setWatch y addActionColumn.
		
		fdt.setDelete(true);
		fdt.setUpdate(true, new FDTUpdateArea());
		fdt.setAdd(true, new FDTAddArea());
		fdt.setSaveAll(true);
		fdt.setSearch(true);
		fdt.setPageSize(10);    //Tamaño de página. Próximamente podrá modificarse directamente con un combo desde la barra de paginado.
		
		fdt.addActionColumn(new Column("left-arrow", parent.getDisplay(), new TreeColumnListener() {			
			@Override
			public void handleEvent(TreeColumnEvent event) {
				//Hacer algo.				
			}
		}));
		
		fdt.addActionButton("pdfButton", new ButtonData("file-pdf", parent.getDisplay(), new SelectionListener() {			
			@Override
			public void widgetSelected(SelectionEvent event) {
				// TODO Auto-generated method stub
				
			}			
			@Override
			public void widgetDefaultSelected(SelectionEvent event) {				
			}
		}));
		
		fdt.addPageChangeListener(new PageChangeListener() {			
			@Override
			public void pageChanged(final PageChangedEvent event) {				
				//Simulacion de consulta. Utilizar los datos en el evento para paginar la misma.
				//Utilizar el sortData del evento para saber qué columna se está ordenando y en qué dirección.
				//Por supuesto que sería con orderBy.
				Page<FDTViewEntity> query = MockDao.findAll(event.startIndex, event.pageSize, event.sortData);
				fdt.setTotalElements(query.getTotalElements());
				fdt.setRows(query.getElements());
			}
		});
		
		fdt.addDeleteListener(new TreeColumnListener() {
			@Override
			public void handleEvent(TreeColumnEvent event) {
				MockDao.delete(event.entity);
				event.performDelete = true;
			}
		});
				
		try {
			fdt.createTable();
		}
		catch(Exception e) {
		}
		
		fdt.setActionButtonText("pdfButton", "Exportar a PDF");
		
		return fdtGroup;
    }
}
