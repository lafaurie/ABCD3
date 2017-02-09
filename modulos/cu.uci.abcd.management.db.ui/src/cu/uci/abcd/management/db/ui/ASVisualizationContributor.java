package cu.uci.abcd.management.db.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

//import cu.uci.abcd.management.db.l10n.AbosMessages;
import cu.uci.abos.core.l10n.AbosMessages;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;

public class ASVisualizationContributor extends ContributorPage implements Contributor {
    Composite top, middleButtons;
    Label lblLastUpdate;
    Table tableSelectedField;
    TableColumn column1, column2;
    TableItem item1, item2, item3;
    FillLayout topFillLayout;
    GridData topGridData, middleButtonsGridData;
    Group topGroup;
    CRUDTreeTable cba;
    RowLayout rowLayout;
    Button updateBtn, exportExcelBtn, exportPdfBtn, closeBtn;

    @Override
    public String contributorName() {
	return "Visualizar Configuraciones de la Búsqueda Avanzada";
    }

    @Override
    public String getID() {
	return "AdministrarBaseDatoID";
    }

    @Override
    public Control createUIControl(final Composite parent) {
	top = new Composite(parent, SWT.NONE);
	top.setLayout(new FormLayout());

	lblLastUpdate = new Label(top, SWT.NONE);
	add(lblLastUpdate);
	//FormDatas.attach(lblLastUpdate).atTop(10).atLeft(10);

	tableSelectedField = new Table(top, SWT.BORDER | SWT.FULL_SELECTION);
	tableSelectedField.setLinesVisible(true);
	column1 = new TableColumn(tableSelectedField, SWT.RIGHT);
	column1.setWidth(155);
	column2 = new TableColumn(tableSelectedField, SWT.NONE);
	column2.setWidth(200);
	item1 = new TableItem(tableSelectedField, SWT.NONE);
	item1.setText(1, "Andy Cabrera Medina");
	item2 = new TableItem(tableSelectedField, SWT.NONE);
	item2.setText(1, "acmedina");
	item3 = new TableItem(tableSelectedField, SWT.NONE);
	item3.setText(1, "26/11/2014 16:20");
	add(tableSelectedField);
	//FormDatas.attach(tableSelectedField).atTop(26).atLeft(20);

	// Definicion del Group de los criterios de busqueda avanzada
	parent.setLayout(new GridLayout(1, true));
	topFillLayout = new FillLayout();
	topGridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
	topGroup = new Group(parent, SWT.NONE);
	topGroup.setLayout(topFillLayout);
	topGroup.setLayoutData(topGridData);

	// Datagrid de los criterios de busqueda avanzada
	cba = new CRUDTreeTable(topGroup, SWT.NONE);
	//cba.setEntityClass((Class<? extends IGridViewEntity>) CSearchEntity.class);
	/*try {
	    cba.createTable();
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}*/

	// Datos para llenar el datagrid de los criterios de busqueda avanzada
//	cba.addRow((IGridViewEntity) new CSearchEntity(001, "Autor personal", "CN_", "Si"));
//	cba.addRow((IGridViewEntity) new CSearchEntity(999, "Autor personal", "CN_", "No"));
//	cba.addRow((IGridViewEntity) new CSearchEntity(952, "Autor personal", "CN_", "Si"));

	// Composite de los botones actualizar, exportar a excel, exportar a pdf
	// y cancelar
	middleButtons = new Composite(parent, SWT.NONE);
	rowLayout = new RowLayout();
	middleButtons.setLayout(rowLayout);
	middleButtonsGridData = new GridData(GridData.END, GridData.CENTER, true, false);
	middleButtonsGridData.horizontalAlignment = SWT.RIGHT;
	middleButtons.setLayoutData(middleButtonsGridData);
	updateBtn = new Button(middleButtons, SWT.PUSH);
	updateBtn.addSelectionListener(new SelectionListener() {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void widgetSelected(SelectionEvent event) {
		// event.getSource().createEditionArea(event.data, event.item,
		// true);
	    }

	    @Override
	    public void widgetDefaultSelected(SelectionEvent arg0) {
	    }
	});
	exportExcelBtn = new Button(middleButtons, SWT.PUSH);
	exportPdfBtn = new Button(middleButtons, SWT.PUSH);
	closeBtn = new Button(middleButtons, SWT.PUSH);
	closeBtn.addSelectionListener(new SelectionListener() {

	    @Override
	    public void widgetSelected(SelectionEvent arg0) {

	    }

	    @Override
	    public void widgetDefaultSelected(SelectionEvent arg0) {

	    }
	});

	l10n();
	return parent;
    }

    @Override
    public boolean canClose() {
	return false;
    }

    @Override
    public void l10n() {
//	lblLastUpdate.setText(MessageUtil.unescape(AbosMessages.get().LAST_UPDATING));
//	item1.setText(0, AbosMessages.get().NAME_LASTNAME);
//	item2.setText(0, AbosMessages.get().USER1);
//	item3.setText(0, AbosMessages.get().DATE);
//	topGroup.setText(MessageUtil.unescape(AbosMessages.get().ADVANCED_SEARCH_CRITERIA));
//	updateBtn.setText(AbosMessages.get().BUTTON_UPDATE);
//	exportExcelBtn.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));
//	exportPdfBtn.setText(AbosMessages.get().BUTTON_EXPORT_TO_PDF);
//	closeBtn.setText(AbosMessages.get().BUTTON_CLOSE);
    }

    @Override
    public void setViewController(ViewController controller) {
	 this.controller = controller;
    }

}
