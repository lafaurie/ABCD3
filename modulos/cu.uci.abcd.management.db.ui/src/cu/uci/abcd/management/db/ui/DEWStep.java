//package cu.uci.abcd.management.db.ui.contributors;
//
//import java.util.Arrays;
//
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.layout.FormLayout;
//import org.eclipse.swt.widgets.Composite;
//import org.eclipse.swt.widgets.Control;
//import org.eclipse.swt.widgets.Group;
//
//import cu.uci.abcd.management.db.l10n.AbosMessages;
//import cu.uci.abos.core.widget.grid.CRUDTreeTable;
//
//
//public class DEWStep extends BaseStep implements IStep {
//
//    private Group fdtGroup;
//    private Group pruebaGroup;
//    private Group wdGroup;
//    private CRUDTreeTable fdt;
//    private CRUDTreeTable wd;
//
//    public DEWStep(Wizard wizard) {
//	super(wizard);
//    }
//
//    public Control createUI(Composite parent) {
//
//	this.createFieldDefinitionTable(parent);
//	this.createWorksheetDefinitionTable(parent);
//	this.createValidationGroup(parent);
//
//	/*
//	 * 
//	 * topGroupMenu.setText("Acciones");
//	 * 
//	 * Label selectLabel = new Label(topGroupMenu, SWT.NONE);
//	 * selectLabel.setText("Seleccione:");
//	 * 
//	 * Combo wdCmb = new Combo(topGroupMenu, SWT.BORDER); wdCmb.setItems(new
//	 * String[] { "eber_worksheet", "otro_worksheet", });
//	 * wdCmb.setText("eber_worksheet");
//	 * 
//	 * Button newBtn = new Button(topGroupMenu, SWT.PUSH);
//	 * newBtn.setText("Nuevo");
//	 * 
//	 * Button deleteBtn = new Button(topGroupMenu, SWT.PUSH);
//	 * deleteBtn.setText("Eliminar");
//	 * 
//	 * Button restoreBtn = new Button(topGroupMenu, SWT.PUSH);
//	 * restoreBtn.setText("Restaurar");
//	 */
//
//	/*
//	 * Button selectAllBtn = new Button(middleButtons, SWT.PUSH);
//	 * selectAllBtn.setText("Seleccionar todos");
//	 * 
//	 * Button deselectAllBtn = new Button(middleButtons, SWT.PUSH);
//	 * deselectAllBtn.setText("Deseleccionar todos");
//	 * 
//	 * parent.pack();
//	 */
//
//	return parent;
//    }
//
//    private Group createFieldDefinitionTable(Composite parent) {
//	fdtGroup = new Group(parent, SWT.NONE);
//	
//	fdtGroup.setLayout(new FormLayout());
//	//FormDatas.attach(fdtGroup).atTop(0).atLeft(0).atRight(0);
//
//	fdt = new CRUDTreeTable(fdtGroup, SWT.NONE);
//	//FormDatas.attach(fdt).atRight(0).atLeft(0);
//	//fdt.setEntityClass(FDTViewEntity.class);
//	fdt.setPageSize(10);
///*
//	fdt.addPageChangeListener(new PageChangeListener() {
//	    @Override
//	    public void pageChanged(final PageChangedEvent event) {
//		Page<FDTViewEntity> query = MockDao.findAllFDTs(event.startIndex, event.pageSize, event.sortData);
//		fdt.setTotalElements(query.getTotalElements());
//		fdt.setRows(query.getElements());
//	    }
//	});
//
//	fdt.addDeleteListener(new TreeColumnListener() {
//	    @Override
//	    public void handleEvent(TreeColumnEvent event) {
//		MockDao.delete(event.entity);
//		event.performDelete = true;
//	    }
//	});
//*/
///*	try {
//	    fdt.createTable();
//	} catch (Exception e) {
//	}*/
//
//	return fdtGroup;
//    }
//
//    private void createWorksheetDefinitionTable(Composite parent) {
//	wdGroup = new Group(parent, SWT.NONE);
//	wdGroup.setLayout(new FormLayout());
////	FormDatas.attach(wdGroup).atTopTo(fdtGroup, 0).atLeft(0).atRight(0);
//
//	wd = new CRUDTreeTable(wdGroup, SWT.NONE);
//	//FormDatas.attach(wd).atRight(0).atLeft(0);
////	wd.setEntityClass(DEWViewEntity.class);
////	wd.setDelete(true);
////	wd.setPageSize(10);
///*
//	wd.addPageChangeListener(new PageChangeListener() {
//	    @Override
//	    public void pageChanged(PageChangedEvent event) {
//		Page<DEWViewEntity> query = MockDao.findAllWDs(event.startIndex, event.pageSize, event.sortData);
//		wd.setTotalElements(query.getTotalElements());
//		wd.setRows(query.getElements());
//	    }
//	});*/
//
//	//wd.addDeleteListener(new TreeColumnListener() {
////	    @Override
////	    public void handleEvent(TreeColumnEvent event) {
////		event.performDelete = true;
////	    }
////	});
//
//	/*try {
//	    wd.createTable();
//	} catch (Exception e) {
//	}*/
//	
//    }
//
//
//    private void createValidationGroup(Composite parent) {
//	/*
//	 * FillLayout bottomFillLayout = new FillLayout(); GridData
//	 * bottomGridData = new GridData(GridData.FILL, GridData.CENTER, true,
//	 * false); Group bottomGroup = new Group(parent, SWT.NONE);
//	 * bottomGroup.setText("Validación a nivel de registro");
//	 * bottomGroup.setLayout(bottomFillLayout);
//	 * bottomGroup.setLayoutData(bottomGridData); new Text(bottomGroup,
//	 * SWT.BORDER);
//	 */
//    }
//
//    @Override
//    public boolean isValid() {
//	return true;
//    }
//
//    @Override
//    public void l10n() {
//	fdtGroup.setText(AbosMessages.get().GROUP_TABLE_FDT);
//	fdt.setCancelButtonText(AbosMessages.get().BUTTON_CANCEL_FDT);
//	fdt.setSearchHintText(AbosMessages.get().MESSAGE_SEARCH_HINT_FDT);
//	fdt.setAddButtonText(AbosMessages.get().BUTTON_ADD_FDT);
//	fdt.setSaveAllButtonText(AbosMessages.get().BUTTON_SAVE_ALL_FDT);
//	fdt.setColumnHeaders(Arrays.asList(AbosMessages.get().HEADER_TAG_FDT, AbosMessages.get().HEADER_NAME_FDT, AbosMessages.get().HEADER_TYPE, AbosMessages.get().HEADER_INDICATORS,
//		AbosMessages.get().HEADER_REP, AbosMessages.get().HEADER_FIRST_SUBFIELD, AbosMessages.get().HEADER_SUBFIELDS_PATTERN_FDT));
//	fdt.l10n();
//	wdGroup.setText(AbosMessages.get().GROUP_TABLE_WD);
//	wd.setColumnHeaders(Arrays.asList(AbosMessages.get().HEADER_NODES_WD, AbosMessages.get().HEADER_NODE_TYPE_WD, AbosMessages.get().HEADER_INDICATORS, AbosMessages.get().HEADER_REP,
//		AbosMessages.get().HEADER_FIRST_SUBFIELD, AbosMessages.get().HEADER_TYPE, AbosMessages.get().HEADER_PROMPT_WD, AbosMessages.get().HEADER_DEFAULT_VALUE_WD,
//		AbosMessages.get().HEADER_DISPLAY_CONTROL_WD, AbosMessages.get().HEADER_HELP_MSG_WD, AbosMessages.get().HEADER_PICK_LIST_WD, AbosMessages.get().HEADER_VALUE_FORMAT_WD));
//    }
//
//}
