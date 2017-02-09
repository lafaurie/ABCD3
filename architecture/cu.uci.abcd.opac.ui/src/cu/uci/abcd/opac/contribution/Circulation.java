package cu.uci.abcd.opac.contribution;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//import javax.naming.LimitExceededException;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.opac.listener.SelectionListViewEntity;
import cu.uci.abos.ui.api.IContributor;
import cu.uci.abos.ui.api.IViewController;
import cu.uci.abos.util.api.FormDatas;
import cu.uci.abos.widgets.grid.CRUDTreeTable;
import cu.uci.abos.widgets.grid.Column;
import cu.uci.abos.widgets.grid.TreeColumnEvent;
import cu.uci.abos.widgets.grid.TreeColumnListener;

public class Circulation implements IContributor {

	public Composite result;
	public Composite examplesListCompo; 		
	private Map<String, Control> controls;
	
    public Circulation(){
		this.controls = new HashMap<String, Control>();
	}
    
	
	@Override
	public Control createUIControl(Composite parent) {

		Composite result = new Composite(parent, 0);
		result.setData(RWT.CUSTOM_VARIANT, "workspace_content");
		result.setLayout(new FormLayout());	
		
		FormDatas.attach(result).atLeft(0).atRight(0).atTop(0).atBottom(0);
		
		final Group agregar = new Group(result, SWT.NONE);
		agregar.setLayout(new FormLayout());
		
		
		agregar.setText("Crear Nueva Reservación");
				
	      		
		FormDatas.attach(agregar).atTop(0).atLeft(0).atRight(0).withHeight(80);
		
		/*
		
		*Titulo del libro
		*Autor del libro
		*Usuario que la realiza
		*Foto
		*Nombre y Apellidos
		*Identificación
		*Reservar
		Prioridad 
		Fecha de reservación
		Fecha de actualizado
		Hora de actualizado
		Fecha de Vencimiento
		Estado	
		  
		
		*/	

		Label priorityLabel = new Label(agregar, SWT.NORMAL);
		priorityLabel.setText("Prioridad");
		FormDatas.attach(priorityLabel).atTopTo(agregar).atRight(110);
		
		
		final Combo priorityCombo = new Combo(agregar, SWT.NORMAL);
		priorityCombo.setLayoutData(new FormData());
		priorityCombo.setItems(new String[] {"Alta","Media","Baja"});
		priorityCombo.setText("Alta");		
		this.controls.put("priorityCombo", priorityCombo);
		

		
		
		Label bookAutorLabel = new Label(agregar, SWT.NORMAL);
		bookAutorLabel.setText("Autor:");
		FormDatas.attach(bookAutorLabel).atTopTo(agregar).atRight(380); 
		
	
		final Text bookAutorText = new Text(agregar, SWT.NORMAL);
		FormDatas.attach(bookAutorText).atTopTo(bookAutorLabel).atRightTo(priorityCombo, 20).withWidth(200);
		this.controls.put("bookAutorText", bookAutorText);
		
				
		Label bookTitleLabel = new Label(agregar, SWT.NORMAL);
		bookTitleLabel.setText("Título:");
		FormDatas.attach(bookTitleLabel).atTopTo(agregar).atLeftTo(agregar, 30);
		

		final Text bookTitleText = new Text(agregar, SWT.NORMAL);
		FormDatas.attach(bookTitleText).atTopTo(bookTitleLabel).atLeftTo(agregar, 10).withWidth(160).atRightTo(bookAutorText, 30);
		this.controls.put("bookTitleText", bookTitleText);
			
		
		FormDatas.attach(priorityCombo).atTopTo(priorityLabel).atRight(10).withWidth(160);
		
		  
		
		
		
		Button generar = new Button(agregar, SWT.PUSH);
		generar.setText("Generar");	
		generar.setFont( new Font( parent.getDisplay(), "Arial", 14, SWT.BOLD ) );
		FormDatas.attach(generar).atBottom(0).atLeft(10).withHeight(25).withWidth(70);
		

		Button cleanFields = new Button(agregar, SWT.PUSH);
		cleanFields.setText("Limpiar");	
		cleanFields.setFont( new Font( parent.getDisplay(), "Arial", 14, SWT.BOLD ) );
		FormDatas.attach(cleanFields).atBottom(0).atLeftTo(generar, 10).withHeight(25).withWidth(70);
		
		
		
		
		examplesListCompo = new Composite(result, SWT.NONE);
		examplesListCompo.setBackground(parent.getBackground());
		examplesListCompo.setLayout(new FormLayout());
		examplesListCompo.setVisible(false);
		FormDatas.attach(examplesListCompo).atLeft(0).atRight(0).atTopTo(agregar, 5).atBottom(0);
 
		

		final CRUDTreeTable examplesList = new CRUDTreeTable(examplesListCompo, SWT.NONE);

		FormDatas.attach(examplesList).atTop(0).atLeft(0).atRight(0);
		
		examplesList.setEntityClass(SelectionListViewEntity.class);
		
		
		
		
		examplesList.setDelete(true);
		
		examplesList.addActionColumn(new Column(parent.getDisplay(), "arrow_down.png", new TreeColumnListener() {
			public void handleEvent(TreeColumnEvent event) {
				final Shell shell = new Shell(examplesListCompo.getDisplay());
				shell.setLayout(new GridLayout());
				shell.setText("Hacer algo");
				shell.setSize(400, 100);
				shell.setLocation(600, 200);
				Label lbl = new Label(shell, SWT.NONE);
				lbl.setText("Hacer algo");
				Button closeBtn = new Button(shell, SWT.PUSH);
				closeBtn.setText("Cerrar");
				closeBtn.addSelectionListener(new SelectionListener() {
					private static final long serialVersionUID = 1L;
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						shell.close();
						shell.dispose();
					}						
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
					}
				});
				shell.open();
			}
		}));
		
		examplesList.setColumnHeaders(Arrays.asList("No","Nombre de la lista", "Contenido", "Ordenado por", "Tipo"));	//For the internationalization. If non set, it will show up blank.
		
		
		
		try {
			examplesList.createTable();
		}
		catch(Exception e) {
		}
		
		
		examplesList.addRow(new SelectionListViewEntity(1, "sdfsdf",  0, "dfgdfgd","apellidouug"));
		examplesList.addRow(new SelectionListViewEntity(2, "identisdfsdfficación", 0,  "dfgdfgd","identificación"));				
		examplesList.addRow(new SelectionListViewEntity(1, "fdgdfg",  0,"dfgdfgd","apellidouug"));
		examplesList.addRow(new SelectionListViewEntity(2, "dfgdfgd", 0, "dfgdfgd","identificación"));					
		
		
		 
		
		
		
		//////////// Listenerssss  ////////////////////////
		
		generar.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				examplesListCompo.setVisible(true);

			}			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				//do nothing
			}
		});
		
		cleanFields.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				examplesListCompo.setVisible(false);
				priorityCombo.setText("Alta");
				bookTitleText.setText("");
				bookAutorText.setText("");
				bookTitleText.setFocus();

			}			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				//do nothing
			}
		});
	
		  
	/*	
		Button guardar = new Button(agregar, SWT.PUSH);
		guardar.setText("Guardar");	
		guardar.setFont( new Font( parent.getDisplay(), "Arial", 16, SWT.BOLD ) );
		FormDatas.attach(guardar).atBottom(0).atRight(100);
		
		
		Button cancelar = new Button(agregar, SWT.PUSH);
		cancelar.setText("Cancelar");	
		cancelar.setFont( new Font( parent.getDisplay(), "Arial", 16, SWT.BOLD ) );
		FormDatas.attach(cancelar).atBottom(0).atRightTo(guardar,20);
				
		
	
		
		
		
		guardar.addSelectionListener(new SelectionListener() {
		private static final long serialVersionUID = 1L;
		@Override
		public void widgetSelected(SelectionEvent arg0) {
		
			/*
			
			SelectionListViewEntity selectionListViewEntity = new SelectionListViewEntity();
			selectionListViewEntity.setName(((Text)controls.get("nombreListaText")).getText());			
			selectionListViewEntity.setCantRegistros(0);
			
			
			
			
			if((((Combo)controls.get("ordenarListaCombo")).getSelectionIndex()) == 0)	
				selectionListViewEntity.setOrdenado("Titulo");
			else if((((Combo)controls.get("ordenarListaCombo")).getSelectionIndex()) == 1)
				selectionListViewEntity.setOrdenado("Autor");
			else if((((Combo)controls.get("ordenarListaCombo")).getSelectionIndex()) == 2)
				selectionListViewEntity.setOrdenado("Fecha de Copyright");
			else if((((Combo)controls.get("ordenarListaCombo")).getSelectionIndex()) == 3)
				selectionListViewEntity.setOrdenado("Categoría");
			
			
			if(((Combo)controls.get("tipoListaCombo")).getSelectionIndex() == 0)
				selectionListViewEntity.setTipo("Privada");
			else if(((Combo)controls.get("tipoListaCombo")).getSelectionIndex() == 1)
				selectionListViewEntity.setTipo("Publica");			
			
			
		myListsTable.addRow(new SelectionListViewEntity(1,selectionListViewEntity.getName(),0,selectionListViewEntity.getOrdenado(),selectionListViewEntity.getTipo()));   
		
			
		myLists.setVisible(true);	          
	    myListsTable.setVisible(true);
	    publicLists.setVisible(true);
	    addLists.setVisible(true);
	    agregar.setVisible(false);
			
		
		
			
		}			
		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			//do nothing
		}
	});
	
	cancelar.addSelectionListener(new SelectionListener() {
		private static final long serialVersionUID = 1L;
		@Override
		public void widgetSelected(SelectionEvent arg0) {
		
			/*	
			
		myLists.setVisible(true);	          
	    myListsTable.setVisible(true);
	    publicLists.setVisible(true);
	    addLists.setVisible(true);
	    agregar.setVisible(false);
			
			
		  
		}			
		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			//do nothing
		}
	});
	
	*/
		
		
		return result;
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return "CirculationID";
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
		return "Reservar";
	}

	@Override
	public void setViewController(IViewController arg0) {
		// TODO Auto-generated method stub

	}

}
