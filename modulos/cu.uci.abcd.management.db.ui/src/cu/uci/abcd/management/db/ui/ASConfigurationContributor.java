package cu.uci.abcd.management.db.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Link;

import cu.uci.abcd.management.db.l10n.AbosMessages;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;

public class ASConfigurationContributor extends ContributorPage implements Contributor {
    Link link;
    FillLayout topFillLayout, middleFillLayout;
    GridData topGridData, middleGridData, downButtonsGridData;
    Group topGroup, middleGroup;
    Composite downButtons;
    RowLayout downLayout;
    Button acceptBtn, cancelBtn;
    CRUDTreeTable fdt;
    CRUDTreeTable cba;

    @Override
    public String contributorName() {
	return "Configurar búsqueda avanzada";
    }

    @Override
    public String getID() {
	return "AdministrarBusquedaAvanzadaID";
    }

    @Override
    public Control createUIControl(final Composite parent) {

	// Definicion del Group de los campos a seleccionar para la busqueda
	parent.setLayout(new GridLayout(1, true));
	link = new Link(parent, SWT.NONE);
	topFillLayout = new FillLayout();
	topGridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
	topGroup = new Group(parent, SWT.NONE);
	add(topGroup);
	topGroup.setLayout(topFillLayout);
	topGroup.setLayoutData(topGridData);

	// Datagrid de los campos a seleccionar para la busqueda
	fdt = new CRUDTreeTable(topGroup, SWT.NONE);
	add(fdt);
	ASCEditionArea asc = new ASCEditionArea();
//	fdt.setEntityClass(SFSearchEntity.class);
	
	 //fdt.addActionColumn(new Column(parent.getDisplay(), "arrow_down.png",asc, new TreeColumnListener() { 
		 
		// public void handleEvent(TreeColumnEvent event) { } }));
	 
	/*try {
	    fdt.createTable(null);
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}*/

	// Datos para llenar el datagrid de los campos a seleccionar para la
	// busqueda
/*	fdt.addRow(new SFSearchEntity(001, 0, "CN_v1", true));
	fdt.addRow(new SFSearchEntity(999, 5, "CN_v2", false));
	fdt.addRow(new SFSearchEntity(020, 8, "CN_v3", true));*/

	// Definicion del Group de los criterios de la busqueda avanzada
	middleFillLayout = new FillLayout();
	middleGridData = new GridData(GridData.FILL, GridData.CENTER, true, true);
	middleGroup = new Group(parent, SWT.NONE);
	middleGroup.setLayout(middleFillLayout);
	middleGroup.setLayoutData(middleGridData);

	// Datagrid de los criterios de busqueda
	cba = new CRUDTreeTable(middleGroup, SWT.NONE);
	//cba.setEntityClass((Class<? extends IGridViewEntity>) CSearchEntity.class);
	/*try {
	    cba.createTable();
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}*/

	// Datos para llenar el datagrid de los criterios de busqueda
//	cba.addRow((IGridViewEntity) new CSearchEntity(001, "test", "CN_", "No"));
//	cba.addRow((IGridViewEntity) new CSearchEntity(999, "test", "CN_", "Si"));
//	cba.addRow((IGridViewEntity) new CSearchEntity(020, "test", "CN_", "No"));

	// Composite de botones aceptar y cancelar
	downButtons = new Composite(parent, SWT.NONE);
	downLayout = new RowLayout();
	downButtons.setLayout(downLayout);
	downButtonsGridData = new GridData(GridData.END, GridData.CENTER, true, false);
	downButtonsGridData.horizontalAlignment = SWT.RIGHT;
	downButtons.setLayoutData(downButtonsGridData);
	acceptBtn = new Button(downButtons, SWT.PUSH);
	cancelBtn = new Button(downButtons, SWT.PUSH);
	l10n();
	return parent;
    }

    @Override
    public boolean canClose() {
	return false;
    }

    @Override
    public void l10n() {
	link.setText(AbosMessages.get().SEE_FDT);
	topGroup.setText(MessageUtil.unescape(AbosMessages.get().FIELDS_TO_SELECT));
	middleGroup.setText(MessageUtil.unescape(AbosMessages.get().ADVANCED_SEARCH_CRITERIA));
	acceptBtn.setText(cu.uci.abos.core.l10n.AbosMessages.get().BUTTON_ACCEPT);
	cancelBtn.setText(AbosMessages.get().BUTTON_CANCEL);
    }

    @Override
    public void setViewController(ViewController controller) {
    	this.controller = controller;
    }

}
