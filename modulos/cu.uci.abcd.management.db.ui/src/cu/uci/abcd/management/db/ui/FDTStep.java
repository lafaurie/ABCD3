package cu.uci.abcd.management.db.ui;

import java.util.Arrays;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;

import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.management.db.l10n.AbosMessages;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeColumnListener;
import cu.uci.abos.core.widget.wizard.BaseStep;
import cu.uci.abos.core.widget.wizard.IStep;
import cu.uci.abos.core.widget.wizard.Wizard;

public class FDTStep extends BaseStep implements IStep {

	private Group fdtGroup;
	private CRUDTreeTable fdt;
	
    public FDTStep(Wizard wizard) {
    	super(wizard);
    }

    @Override
    public boolean isValid() {
		// for(Control control : this.controls.values()) {
		// TODO: validation logic here.
		// }
    	return true;
    }

    @Override
    public Control createUI(Composite parent) {
		this.createFieldDefinitionTable(parent);	
		parent.notifyListeners(SWT.Resize, new Event());
		return parent;
    }

	private Group createFieldDefinitionTable(Composite parent) {
		
		FormLayout form = new FormLayout();
		parent.setLayout(form);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		//FormDatas.attach(parent).atLeft(0).atRight(0);
//		
		fdtGroup = new Group(parent, SWT.NONE);
    	fdtGroup.setLayout(new FormLayout());
    	//FormDatas.attach(fdtGroup).atTop(0).atLeft(0).atRight(0);    	
		
		fdt = new CRUDTreeTable(fdtGroup, SWT.NONE);
		//FormDatas.attach(fdt).atRight(0).atLeft(0);
		fdt.setEntityClass(FDTViewEntity.class);		
		fdt.setUpdate(true, new FDTUpdateArea());
		fdt.setDelete(true);
		fdt.setAdd(true, new FDTAddArea());
		fdt.setPageSize(10);	//Tamaño de página. Próximamente podrá modificarse directamente con un combo desde la barra de paginado.

		//TreeTableColumn columns [] = {}
//		fdt.addPageChangeListener(new PageChangeListener() {			
//			@Override
//			public void pageChanged(final PageChangedEvent event) {				
//				//Simulacion de consulta. Utilizar los datos en el evento para paginar la misma.
//				//Utilizar el sortData del evento para saber qué columna se está ordenando y en qué dirección.
//				//Por supuesto que sería con orderBy.
//				Page<FDTViewEntity> query = MockDao.findAllFDTs(event.startIndex, event.pageSize, event.sortData);
//				fdt.setTotalElements((int)query.getTotalElements());
//				//fdt.setRows(query);
//			}
//		});
		
		fdt.addDeleteListener(new TreeColumnListener() {
			@Override
			public void handleEvent(TreeColumnEvent event) {
		//		MockDao.delete(event.entity);
				event.performDelete = true;
			}
		});
				
//		try {
//			fdt.createTable();
//		}
//		catch(Exception e) {
//		}
		
		return fdtGroup;
    }

	@Override
	public void l10n() {
		fdtGroup.setText(AbosMessages.get().GROUP_TABLE_FDT);
		fdt.setCancelButtonText(AbosMessages.get().BUTTON_CANCEL_FDT);
		fdt.setSearchHintText(AbosMessages.get().MESSAGE_SEARCH_HINT_FDT);
		fdt.setAddButtonText(AbosMessages.get().BUTTON_ADD_FDT);
		fdt.setSaveAllButtonText(AbosMessages.get().BUTTON_SAVE_ALL_FDT);
		fdt.setActionButtonText("restoreBtn", AbosMessages.get().BUTTON_RESTORE_FDT);
		fdt.setColumnHeaders(Arrays.asList(AbosMessages.get().HEADER_TAG_FDT, AbosMessages.get().HEADER_NAME_FDT, 
				AbosMessages.get().HEADER_TYPE, AbosMessages.get().HEADER_INDICATORS, 
				AbosMessages.get().HEADER_REP, AbosMessages.get().HEADER_FIRST_SUBFIELD, 
				AbosMessages.get().HEADER_SUBFIELDS_PATTERN_FDT));
		fdt.l10n();
	}
}
