//package cu.uci.abcd.management.db.ui.contributors;
//
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.layout.FillLayout;
//import org.eclipse.swt.layout.GridData;
//import org.eclipse.swt.layout.GridLayout;
//import org.eclipse.swt.layout.RowLayout;
//import org.eclipse.swt.widgets.Button;
//import org.eclipse.swt.widgets.Composite;
//import org.eclipse.swt.widgets.Control;
//import org.eclipse.swt.widgets.Group;
//import org.eclipse.swt.widgets.Label;
//import org.eclipse.swt.widgets.Text;
//
//import cu.uci.abos.widgets.grid.CRUDTreeTable;
//import cu.uci.abos.widgets.grid.TreeColumnEvent;
//import cu.uci.abos.widgets.grid.TreeColumnListener;
//import cu.uci.abos.widgets.wizard.BaseStep;
//import cu.uci.abos.widgets.wizard.IStep;
//import cu.uci.abos.widgets.wizard.Wizard;
//
//public class FSTStep extends BaseStep implements IStep {
//
//    public FSTStep(Wizard wizard) {
//	super(wizard);
//    }
//
//    @Override
//    public boolean isValid() {
//	// for(Control control : this.controls.values()) {
//	// TODO: validation logic here.
//	// }
//	return true;
//    }
//
//    @Override
//    public Control createUI(Composite parent) {
//
//	parent.setLayout(new GridLayout(1, true));
//
//	GridLayout topGridLayout = new GridLayout(4, false);
//	GridData topGridData = new GridData(GridData.FILL, GridData.CENTER, false, false);
//	Group topGroup = new Group(parent, SWT.NONE);
//	topGroup.setText("Acciones");
//	topGroup.setLayout(topGridLayout);
//	topGroup.setLayoutData(topGridData);
//
//	Button restoreBtn = new Button(topGroup, SWT.PUSH);
//	restoreBtn.setText("Restaurar");
//
//	Button saveBtn = new Button(topGroup, SWT.PUSH);
//	saveBtn.setText("Salvar");
//
//	Button closeBtn = new Button(topGroup, SWT.PUSH);
//	closeBtn.setText("Cerrar");
//
//	FillLayout middleFillLayout = new FillLayout();
//	GridData middleGridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
//	Group middleGroup = new Group(parent, SWT.NONE);
//	middleGroup.setText("Campos disponibles");
//	middleGroup.setLayout(middleFillLayout);
//	middleGroup.setLayoutData(middleGridData);
//
//	final CRUDTreeTable fdt = new CRUDTreeTable(middleGroup, SWT.NONE);
//	fdt.setEntityClass(FDTViewEntity.class);
//
///*	fdt.addPageChangeListener(new PageChangeListener() {
//
//	    @Override
//	    public void pageChanged(final PageChangedEvent event) {
//		Page<FDTViewEntity> query = MockDao.findAllFDTs(event.startIndex, event.pageSize, event.sortData);
//		fdt.setTotalElements(query.getTotalElements());
//		fdt.setRows(query.getElements());
//
//	    }
//	});*/
//
//	// fdt.setEditonArea(new FDTEditionArea());
//
//	fdt.addUpdateListener(new TreeColumnListener() {
//	    public void handleEvent(TreeColumnEvent event) {
//		// hacer algo con los datos.
//	    }
//	});
//
//	fdt.addDeleteListener(new TreeColumnListener() {
//	    @Override
//	    public void handleEvent(TreeColumnEvent event) {
//	//	MockDao.delete(event.entity);
//		event.performDelete = true;
//	    }
//	});
//
//	Composite middleButtons = new Composite(parent, SWT.NONE);
//	RowLayout rowLayout = new RowLayout();
//	middleButtons.setLayout(rowLayout);
//	GridData middleButtonsGridData = new GridData(GridData.END, GridData.CENTER, true, false);
//	middleButtonsGridData.horizontalAlignment = SWT.LEFT;
//	middleButtons.setLayoutData(middleButtonsGridData);
//
//	Button selectBtn = new Button(middleButtons, SWT.PUSH);
//	selectBtn.setText("Seleccionar");
//
//	Label separator = new Label(middleButtons, SWT.NONE);
//	separator.setText("   ");
//
//	Button storeIndexBtn = new Button(middleButtons, SWT.CHECK);
//	storeIndexBtn.setText("Guardar el registro en el índice");
//
//	Button catchAllBtn = new Button(middleButtons, SWT.CHECK);
//	catchAllBtn.setText("Convertir en campo catch-all");
//
//	FillLayout middle2FillLayout = new FillLayout();
//	GridData middle2GridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
//	Group middle2Group = new Group(parent, SWT.NONE);
//	middle2Group.setText("Campos seleccionados");
//	middle2Group.setLayout(middle2FillLayout);
//	middle2Group.setLayoutData(middle2GridData);
//
//	CRUDTreeTable fst = new CRUDTreeTable(middle2Group, SWT.NONE);
//
//	fst.addRow(new FSTViewEntity(0, "nombre", 1, "v1"));
//	fst.addRow(new FSTViewEntity(1, "apellidos", 1, "v2"));
//	fst.addRow(new FSTViewEntity(2, "identificación", 1, "v3"));
//	fst.addRow(new FSTViewEntity(3, "sexo", 1, "v4"));
//
//	// fst.setEditonArea(new FSTEditionArea());
//
//	fst.addUpdateListener(new TreeColumnListener() {
//	    public void handleEvent(TreeColumnEvent event) {
//		// hacer algo con los datos.
//	    }
//	});
//
//	fst.addDeleteListener(new TreeColumnListener() {
//	    public void handleEvent(TreeColumnEvent event) {
//		// hacer algo con los datos.
//	    }
//	});
//
//	FillLayout bottomFillLayout = new FillLayout();
//	GridData bottomGridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
//	Group bottomGroup = new Group(parent, SWT.NONE);
//	bottomGroup.setText("Fromato de vista por defecto");
//	bottomGroup.setLayout(bottomFillLayout);
//	bottomGroup.setLayoutData(bottomGridData);
//
//	new Text(bottomGroup, SWT.BORDER);
//
//	parent.pack();
//
//	return parent;
//    }
//
//    @Override
//    public void l10n() {
//	// TODO Auto-generated method stub
//
//    }
//}
