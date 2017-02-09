package cu.uci.abcd.management.db.ui;

import java.util.Arrays;


import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.management.db.l10n.AbosMessages;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IEditableArea;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeColumnListener;


public class Administrar_Hoja_Trabajo extends ContributorPage implements Contributor {

	private Group fdtGroup;
	private CRUDTreeTable fdt;

	Label lbSearchCriteria;
	Label lbTitle;
	Label lbAuthor;
	Label lbControlNumber;
	Label lbObjectType;
	Label lbRange;
	Label lbStateObject;
	Label lbLocation;
	Label lbFrom;
	Label lbUp;
	Label lbCoincidenceList;
	Link link;
	Text txtTitle;
	Text txtAuthor;
	Text txtControlNumber;
	Button btnConsult;
	Button btnNewSearch;
	Combo comboObjectType;
	Combo comboObjectState;

	String title = null;
	String author = null;
	String control_number = null;

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().WORKSHEET_MANAGEMENT_HEADER);
	}

	@Override
	public String getID() {
		return "manageWorksheetID";
	}

	@Override
	public Control createUIControl(Composite parent) {
		
		addComposite(parent);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
//		parent.setLayout(new FormLayout());
//		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");

		lbSearchCriteria = new Label(parent, SWT.NONE);
		add(lbSearchCriteria);
		//lbSearchCriteria.setText("Criterio de B�squeda");
		lbTitle = new Label(parent, SWT.NONE); 
		add(lbTitle);
		//lbTitle.setText("Hoja de Trabajo");
		lbAuthor = new Label(parent, SWT.NONE);
//		lbAuthor.setText("Tipo de Registro");
//		FormDatas.attach(lbSearchCriteria).atTopTo(parent, 5).atLeftTo(parent, 15);
//		FormDatas.attach(lbTitle).atTopTo(lbSearchCriteria, 5).atLeftTo(parent, 15);
//		FormDatas.attach(lbAuthor).atTopTo(lbSearchCriteria, 5).atLeftTo(lbTitle, 90);

		txtTitle = new Text(parent, SWT.NORMAL);
		add(txtTitle);
		comboObjectType = new Combo(parent, SWT.NONE);
		add(comboObjectType);

//		FormDatas.attach(txtTitle).atTopTo(lbTitle, 5).atLeftTo(parent, 15).withWidth(130).withHeight(10);
//		FormDatas.attach(comboObjectType).atTopTo(lbAuthor, 5).atLeftTo(txtTitle, 30).withWidth(150).withHeight(23);

		Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);
//		FormDatas.attach(separador).atTopTo(comboObjectType, 15).atLeft(5).atRight(5);

		// --------------------------------------------

		lbControlNumber = new Label(parent, SWT.NONE);
		add(lbControlNumber);
//		lbControlNumber.setText("Lista de Hojas de Trabajo");

//		FormDatas.attach(lbControlNumber).atTopTo(separator, 5).atLeftTo(parent, 15);
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
		fdt.setActionButtonText("restoreBtn",AbosMessages.get().BUTTON_RESTORE_FDT);
		fdt.setColumnHeaders(Arrays.asList(AbosMessages.get().HEADER_TAG_FDT, (MessageUtil.unescape(AbosMessages.get().WORKSHEET_HEADER)), "Tipo de Registro", "Repetible", "Predeterminada", "Subcampos", "C�digo"));
		fdt.l10n();
	}

	@Override
	public void setViewController(ViewController controller) {
		// TODO Auto-generated method stub
	}

	@SuppressWarnings("serial")
	private Group createFieldDefinitionTable(Composite parent) {

		fdtGroup = new Group(parent, SWT.NONE);
		fdtGroup.setLayout(new FormLayout());
		add(fdtGroup);
//		FormDatas.attach(fdtGroup).atTop(125).atLeft(0).atRight(0);

		fdt = new CRUDTreeTable(fdtGroup, SWT.NONE);
		add(fdt);
	//	fdt.setEntityClass(FDTViewEntity.class);

		// OJO: el orden en que se dibujan las columnas de acción depende del
		// orden de las llamadas a setDelete, setUpdate, setWatch y
		// addActionColumn.

		fdt.setDelete(true);
		fdt.setUpdate(true, (IEditableArea) new FDTUpdateArea());
//		tabla.setUpdate(true, new NomenclatorUpdateEditableArea(controller, items, nomenclatorTypes));
//		tabla.setSaveAll(false);
//		tabla.setAdd(true, new NomenclatorAddEditableArea(controller, items, nomenclatorTypes));
//		tabla.setWatch(true, new NomenclatorWatchEditableArea(controller));
//		tabla.setUpdate(true, new NomenclatorUpdateEditableArea(controller, items, nomenclatorTypes));
//		tabla.setDelete(true);
//		tabla.setVisible(true);
//		tabla.setPageSize(10);
		//fdt.setAdd(true,(IEditableArea) new FDTAddArea());
		//fdt.setWatch(true, (IEditableArea) new FDTUpdateArea());
		fdt.setSaveAll(true);
		fdt.setSearch(true);
		fdt.setPageSize(10); 

		// directamente con un combo desde la barra de
		// paginado.
		//	
		//	  fdt.addActionColumn(new Column(parent.getDisplay(), "left-arrow", new
		//	  TreeColumnListener() {
		//	  
		//	  @Override public void handleEvent(TreeColumnEvent event) { //Hacer algo. 
		//		  } }));
		//	  
		//	  
		//	  fdt.addActionButton("pdfButton", new ButtonData("file-pdf", new SelectionListener() {
		//	  
		//	  @Override public void widgetSelected(SelectionEvent event) { // TODO
		//	  Auto-generated method stub
		//	  
		//	  }
		//	  
		//	  @Override public void widgetDefaultSelected(SelectionEvent event) { }
		//	  }));

		/*
	fdt.addPageChangeListener(new PageChangeListener() {
	    @Override
	    public void pageChanged(final PageChangedEvent event) {
		// Simulacion de consulta. Utilizar los datos en el evento para
		// paginar la misma.
		// Utilizar el sortData del evento para saber qué columna se
		// está ordenando y en qué dirección.
		// Por supuesto que sería con orderBy.

		 * Page<FDTViewEntity> query = MockDao.findAll(event.startIndex,
		 * event.pageSize, event.sortData);
		 * fdt.setTotalElements(query.getTotalElements());
		 * fdt.setRows(query.getElements());

	    }
	});*/

		fdt.addDeleteListener(new TreeColumnListener() {
			@Override
			public void handleEvent(TreeColumnEvent event) {
				//MockDao.delete(event.entity);
				event.performDelete = true;
			}
		});

		try {
			//	    fdt.createTable();
			//	 searchCurrentLoansTable(0, currentLoansTable.getPaginator().getPageSize());
		} catch (Exception e) {
		}

		//	fdt.setActionButtonText("pdfButton", "Exportar a PDF");

		return fdtGroup;
	}
}
