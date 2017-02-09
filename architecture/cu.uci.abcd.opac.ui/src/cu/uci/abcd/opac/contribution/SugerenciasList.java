package cu.uci.abcd.opac.contribution;

import java.util.Arrays;

//import javax.swing.plaf.basic.BasicInternalFrameTitlePane.RestoreAction;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import cu.uci.abcd.opac.listener.SugerencyListViewEntity;
import cu.uci.abos.ui.api.IContributor;
import cu.uci.abos.ui.api.IViewController;
import cu.uci.abos.util.api.FormDatas;
import cu.uci.abos.widgets.grid.CRUDTreeTable;
import cu.uci.abos.widgets.grid.TreeColumnEvent;
import cu.uci.abos.widgets.grid.TreeColumnListener;

public class SugerenciasList implements IContributor {

	public Composite result;
	public   Composite detailsView;
	
	
	@Override
	public Control createUIControl(final Composite parent) {

		
		result = new Composite(parent, SWT.V_SCROLL);
		result.setData(RWT.CUSTOM_VARIANT, "workspace_content");
		result.setLayout(new FormLayout());	
		
		
		FormDatas.attach(result).atLeft(0).atRight(0).atTop(0).atBottom(0);
		
		detailsView = new Composite(parent, SWT.V_SCROLL);
		detailsView.setData(RWT.CUSTOM_VARIANT, "workspace_content");
		detailsView.setLayout(new FormLayout());				
	
		   
		FormDatas.attach(detailsView).atLeft(0).atRight(0).atTop(0).atBottom(0);
		
		
		
		final CRUDTreeTable sugerencyList = new CRUDTreeTable(result, SWT.NONE);

		FormDatas.attach(sugerencyList).atTop(5).atRight(0).atLeft(0);
		
		sugerencyList.setEntityClass(SugerencyListViewEntity.class);
		
		sugerencyList.setDelete(true);
		
		
		sugerencyList.setColumnHeaders(Arrays.asList("No","Título", "Autor", "Fecha Copyright", "Editor", "Nota"));	//For the internationalization. If non set, it will show up blank.
		
		try {
			sugerencyList.createTable();
		}
		catch(Exception e) {
		}
		
		
		sugerencyList.addRow(new SugerencyListViewEntity(1, "Sandokan",  "Juan Miguel", "Perez Leal", "15/02/2015"));
		sugerencyList.addRow(new SugerencyListViewEntity(3, "By a Barbie", "Gisselle", "Laffita", "20/02/2015"));				
		sugerencyList.addRow(new SugerencyListViewEntity(4, "Hala Madrid",  "Alberto Alejandro","Arias Benitez", "26/02/2015"));
		sugerencyList.addRow(new SugerencyListViewEntity(5, "Hi World", "Kerlyn", "Brow", "25/02/2015"));					
		
		
		
		

		final Group details = new Group(detailsView, SWT.NONE);
		details.setLayout(new FormLayout());
		
		
		details.setText("Detalles de Sugerencia");
				
	      		
		FormDatas.attach(details).atTop(0).atLeft(0).atRight(0).withHeight(280);
		
		
		

			
		
		Label bookTitleLabel = new Label(details, SWT.NORMAL);
		bookTitleLabel.setText("Título:");
		bookTitleLabel.setFont( new Font( parent.getDisplay(), "Arial", 14, SWT.BOLD ) );
		FormDatas.attach(bookTitleLabel).atTopTo(details, 15).atLeftTo(details, 20);
		

		Label bookTitleContent = new Label(details, SWT.NORMAL);
		FormDatas.attach(bookTitleContent).atTopTo(details, 15).atLeftTo(bookTitleLabel, 5);		
		
		
		Label bookAutorLabel = new Label(details, SWT.NORMAL);
		bookAutorLabel.setText("Autor:");
		bookAutorLabel.setFont( new Font( parent.getDisplay(), "Arial", 14, SWT.BOLD ) );
		FormDatas.attach(bookAutorLabel).atTopTo(bookTitleLabel, 15).atLeftTo(details, 20); 
		
	 
		Label bookAutorContent = new Label(details, SWT.NORMAL);
		FormDatas.attach(bookAutorContent).atTopTo(bookTitleLabel, 15).atLeftTo(bookAutorLabel, 5); 
		
		

		Label copyrightDateLabel = new Label(details, SWT.NORMAL);
		copyrightDateLabel.setText("Fecha de Copyright:");
		copyrightDateLabel.setFont( new Font( parent.getDisplay(), "Arial", 14, SWT.BOLD ) );
		FormDatas.attach(copyrightDateLabel).atTopTo(bookAutorLabel, 15).atLeftTo(details, 20);
		
	
		Label copyrightDateContent = new Label(details, SWT.NORMAL);
		copyrightDateContent.setText("mm/dd/aaaa");
		FormDatas.attach(copyrightDateContent).atTopTo(bookAutorLabel, 15).atLeftTo(copyrightDateLabel, 5);
						
		
		
		Label bookEditorLabel = new Label(details, SWT.NORMAL);
		bookEditorLabel.setText("Editor:");
		bookEditorLabel.setFont( new Font( parent.getDisplay(), "Arial", 14, SWT.BOLD ) );
		FormDatas.attach(bookEditorLabel).atTopTo(copyrightDateLabel, 15).atLeftTo(details, 20); 
		
	 
		Label bookEditorContent = new Label(details, SWT.NORMAL);
		FormDatas.attach(bookEditorContent).atTopTo(copyrightDateLabel, 15).atLeftTo(bookEditorLabel, 5); 
		
			
		
		Label noteLabel = new Label(details, SWT.NORMAL);
		noteLabel.setText("Nota:");
		noteLabel.setFont( new Font( parent.getDisplay(), "Arial", 14, SWT.BOLD ) );
		FormDatas.attach(noteLabel).atTopTo(bookEditorLabel, 10).atLeftTo(details, 20);
		

		Label noteContent = new Label(details, SWT.NORMAL);
		FormDatas.attach(noteContent).atTopTo(bookEditorLabel, 10).atLeftTo(noteLabel, 5);
				
		 
		
			  
		
		Button salir = new Button(details, SWT.PUSH);
		salir.setText("Salir");	
		salir.setFont( new Font( parent.getDisplay(), "Arial", 14, SWT.BOLD ) );
		FormDatas.attach(salir).atBottom(0).atRight(50).withHeight(25).withWidth(65);;
		
		
		Button eliminar = new Button(details, SWT.PUSH);
		eliminar.setText("Elminar");	
		eliminar.setFont( new Font( parent.getDisplay(), "Arial", 14, SWT.BOLD ) );
		FormDatas.attach(eliminar).atBottom(0).atRightTo(salir,20).withHeight(25).withWidth(65);
				
		
		Button editar = new Button(details, SWT.PUSH);
		editar.setText("Editar");	
		editar.setFont( new Font( parent.getDisplay(), "Arial", 14, SWT.BOLD ) );
		FormDatas.attach(editar).atBottom(0).atRightTo(eliminar,20).withHeight(25).withWidth(65);
			 
		
		salir.addSelectionListener(new SelectionListener() {
		private static final long serialVersionUID = 1L;
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			result.setVisible(true);	
			detailsView.setVisible(false);		
			
		}			
		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			//do nothing
		}
	});
	
	editar.addSelectionListener(new SelectionListener() {
		private static final long serialVersionUID = 1L;
		@Override
		public void widgetSelected(SelectionEvent arg0) {
		
			/*	
			
		myLists.setVisible(true);	          
	    myListsTable.setVisible(true);
	    publicLists.setVisible(true);
	    addLists.setVisible(true);
	    agregar.setVisible(false);
			
			*/   
		  
		}			
		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			//do nothing
		}
	});
	
	
	sugerencyList.addDeleteListener(new TreeColumnListener() {
		
		@Override
		public void handleEvent(TreeColumnEvent arg0) {
			arg0.performDelete = true;
		}
	});
	
	
		
		
		
		
		
		
		return result;
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return "listViewSugerenciaID";
	}

	@Override
	public void l10n() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean canClose() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String contributorName() {
		// TODO Auto-generated method stub
		return "Sugerencias";
	}

	@Override
	public void setViewController(IViewController arg0) {
		// TODO Auto-generated method stub

	}

}
