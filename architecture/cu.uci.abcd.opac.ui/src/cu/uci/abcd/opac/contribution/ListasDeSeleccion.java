package cu.uci.abcd.opac.contribution;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
//import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
//import org.eclipse.swt.widgets.TreeItem;


import cu.uci.abcd.opac.listener.SelectionListViewEntity;
import cu.uci.abos.ui.api.IContributor;
import cu.uci.abos.ui.api.IViewController;
import cu.uci.abos.util.api.FormDatas;
import cu.uci.abos.widgets.grid.CRUDTreeTable;
import cu.uci.abos.widgets.grid.Column;
//import cu.uci.abos.widgets.grid.IVisualEntityManager;
import cu.uci.abos.widgets.grid.TreeColumnEvent;
import cu.uci.abos.widgets.grid.TreeColumnListener;


public class ListasDeSeleccion implements IContributor {
	
	public   Composite result;	
	private Map<String, Control> controls;
	
	public ListasDeSeleccion() {
		this.controls = new HashMap<String, Control>();
	}
	

	@Override
	public Control createUIControl(final Composite parent) {
		
		 
				
		result = new Composite(parent, SWT.V_SCROLL);
		result.setData(RWT.CUSTOM_VARIANT, "workspace_content");
		result.setLayout(new FormLayout());	
		
		
		FormDatas.attach(result).atLeft(0).atRight(0).atTop(0).atBottom(0);
		
		
		
		
		//// Area de Agregar la Lista
		
		final Group agregar = new Group(result, SWT.NONE);
		agregar.setLayout(new FormLayout());
		
		
		agregar.setText("Nueva Lista de Selección");
				
	      		
		FormDatas.attach(agregar).atTop(0).atLeft(0).atRight(0).withHeight(200);;
		
				
				
		Label nombreLista = new Label(agregar, SWT.NORMAL);
        nombreLista.setText("Nombre de la Lista:");
		FormDatas.attach(nombreLista).atTopTo(agregar, 5).atLeftTo(agregar, 20); 
		
		final Text nombreListaText = new Text(agregar, SWT.NORMAL);
		FormDatas.attach(nombreListaText).atTopTo(nombreLista).atLeftTo(agregar, 10).withWidth(250);
		this.controls.put("nombreListaText", nombreListaText);
		
		Label obligado = new Label(agregar, SWT.NORMAL);
		obligado.setText("*");
		obligado.setFont( new Font( parent.getDisplay(), "Arial", 14, SWT.BOLD ) );
		FormDatas.attach(obligado).atTopTo(nombreLista).atLeftTo(nombreListaText, 2); 
		
		
		Label TipoLista = new Label(agregar, SWT.NORMAL);
		TipoLista.setText("Tipo de Lista:");
		FormDatas.attach(TipoLista).atTopTo(nombreListaText, 10).atLeftTo(agregar, 20);
		
		final Combo TipoListaCombo = new Combo(agregar, SWT.NORMAL);
		TipoListaCombo.setLayoutData(new FormData());
		TipoListaCombo.setItems(new String[] {"privada","publica"});
		TipoListaCombo.setText("privada");
		FormDatas.attach(TipoListaCombo).atTopTo(TipoLista).atLeftTo(agregar, 10).withWidth(270);
		this.controls.put("tipoListaCombo", TipoListaCombo);
			
		
		Label ordenarLista = new Label(agregar, SWT.NORMAL);
        ordenarLista.setText("Ordenar Lista por:");
		FormDatas.attach(ordenarLista).atTopTo(TipoListaCombo, 10).atLeftTo(agregar, 20);
		
	
		final Combo ordenarListaCombo = new Combo(agregar, SWT.NORMAL);
		ordenarListaCombo.setLayoutData(new FormData());
		ordenarListaCombo.setItems(new String[] {"Título","Autor","Fecha de Copyright","Categoría"});
		ordenarListaCombo.setText("Título");
		FormDatas.attach(ordenarListaCombo).atTopTo(ordenarLista).atLeftTo(agregar, 10).withWidth(270);
		this.controls.put("ordenarListaCombo", ordenarListaCombo);
		
		
		
		Button guardar = new Button(agregar, SWT.PUSH);
		guardar.setText("Guardar");	 
		guardar.setFont( new Font( parent.getDisplay(), "Arial", 14, SWT.BOLD ) );
		FormDatas.attach(guardar).atBottom(0).atRight(50).withHeight(25).withWidth(65);
		
		Button cancelar = new Button(agregar, SWT.PUSH);
		cancelar.setText("Cancelar");		
		cancelar.setFont( new Font( parent.getDisplay(), "Arial", 14, SWT.BOLD ) );
		FormDatas.attach(cancelar).atBottom(0).atRightTo(guardar,20).withHeight(25).withWidth(65);
			
		
	    
	    agregar.setVisible(false);
	    
	    
	    //// Tablas de Listas de Seleccion
	    
	    
		         
	    final Button myLists = new Button(result, SWT.ARROW_DOWN);
	    myLists.setText("Sus Listas");
	    myLists.setFont( new Font( parent.getDisplay(), "Arial", 14, SWT.BOLD ) );
		FormDatas.attach(myLists).atTopTo(result, 10).atLeftTo(result, 20).withHeight(25).withWidth(85);		
		
			
			
		final Button publicLists = new Button(result, SWT.ARROW_DOWN);
		publicLists.setText("Listas Publicas");
		publicLists.setFont( new Font( parent.getDisplay(), "Arial", 14, SWT.BOLD ) );
	    FormDatas.attach(publicLists).atTopTo(result, 10).atLeftTo(myLists, 0).withHeight(25).withWidth(115);
	    
	    
	     
	    final Button addLists = new Button(result, SWT.CONTROL);
	    addLists.setText("Nueva Lista");
	    addLists.setFont( new Font( parent.getDisplay(), "Arial", 14, SWT.BOLD ) );
	    FormDatas.attach(addLists).atTopTo(myLists, 5).atLeftTo(result, 20).withHeight(25).withWidth(90);
	    
		
		final CRUDTreeTable myListsTable = new CRUDTreeTable(result, SWT.NONE);

		FormDatas.attach(myListsTable).atTopTo(addLists, 5).atRight(0).atLeft(0);
		
		myListsTable.setEntityClass(SelectionListViewEntity.class);
		
		myListsTable.setDelete(true);
		
		myListsTable.addActionColumn(new Column(parent.getDisplay(), "arrow_down.png", new TreeColumnListener() {
			public void handleEvent(TreeColumnEvent event) {
				final Shell shell = new Shell(parent.getDisplay());
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
		
		myListsTable.setColumnHeaders(Arrays.asList("No","Nombre de la lista", "Contenido", "Ordenado por", "Tipo"));	//For the internationalization. If non set, it will show up blank.
		
		try {
			myListsTable.createTable();
		}
		catch(Exception e) {
		}
		
		
		myListsTable.addRow(new SelectionListViewEntity(1, "sdfsdf",  0, "dfgdfgd","apellidouug"));
		myListsTable.addRow(new SelectionListViewEntity(2, "identisdfsdfficación", 0,  "dfgdfgd","identificación"));				
		myListsTable.addRow(new SelectionListViewEntity(1, "fdgdfg",  0,"dfgdfgd","apellidouug"));
		myListsTable.addRow(new SelectionListViewEntity(2, "dfgdfgd", 0, "dfgdfgd","identificación"));					
		
		
		 
		
		final CRUDTreeTable publicListsTable = new CRUDTreeTable(result, SWT.NONE);;

		FormDatas.attach(publicListsTable).atTopTo(myLists,0).atRight(0).atLeft(0);
		
		publicListsTable.setEntityClass(SelectionListViewEntity.class);		
		
		publicListsTable.setDelete(true);
		
		publicListsTable.addActionColumn(new Column(parent.getDisplay(), "arrow_down.png", new TreeColumnListener() {
			public void handleEvent(TreeColumnEvent event) {
				final Shell shell = new Shell(parent.getDisplay());
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
		
		publicListsTable.setColumnHeaders(Arrays.asList("No","Nombre de la lista", "Contenido", "Ordenado por", "Tipo"));	//For the internationalization. If non set, it will show up blank.
		
		try {
			publicListsTable.createTable();
		}
		catch(Exception e) {
		}
		 
		
		publicListsTable.addRow(new SelectionListViewEntity(1, "apellidouug",  0, "apellidouug","apellidouug"));
		publicListsTable.addRow(new SelectionListViewEntity(2, "identificación", 0, "apellidouug","identificación"));				
		publicListsTable.addRow(new SelectionListViewEntity(1, "apellidouug",  0, "apellidouug","apellidouug"));
		publicListsTable.addRow(new SelectionListViewEntity(2, "identificación", 0,  "apellidouug","identificación"));					
			
		
		
		
		
		publicListsTable.setVisible(false);
		
		
		
		
		
		myLists.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			   		
				publicListsTable.setVisible(false);
			    myListsTable.setVisible(true);	
			    addLists.setVisible(true);
			   
				//TODO: invocar adicion en el negocio.
			}			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				//do nothing
			}
		});
		
		
				
		publicLists.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void widgetSelected(SelectionEvent arg0) {

			   if(publicLists.setFocus()== true)
			    {
				    publicListsTable.setVisible(true);				   
			    	myListsTable.setVisible(false);
			    	addLists.setVisible(false);
			    }
			    	
				//TODO: invocar adicion en el negocio.
			}			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				//do nothing
			}
		});
		
		  
		
		
		guardar.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				if(((Text)controls.get("nombreListaText")).getText() == ""){
				
						final Shell shell = new Shell(parent.getDisplay());
						shell.setLayout(new GridLayout());
						shell.setText("Alerta");
						shell.setSize(400, 100);
						shell.setLocation(600, 200);
						Label lbl = new Label(shell, SWT.NONE);
						lbl.setText("Existen campos obligatorios vacios");
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
				else
				{			
				
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
				
		    
		    		    
		    nombreListaText.setText("");
			TipoListaCombo.setText("privada");
		    ordenarListaCombo.setText("Título");
				
				}
					
				
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
		
		addLists.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				myLists.setVisible(false);	          
		        myListsTable.setVisible(false);
		        myLists.setVisible(false);
		        publicListsTable.setVisible(false);
		        addLists.setVisible(false);
		        agregar.setVisible(true);
	           
	           
	         
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});	
		
		
		myListsTable.addDeleteListener(new TreeColumnListener() {
			
			@Override
			public void handleEvent(TreeColumnEvent arg0) {
				arg0.performDelete = true;
			}
		});
		
        publicListsTable.addDeleteListener(new TreeColumnListener() {
			
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
		return "ListasDeSeleccionID";
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
		return "Listas";
	}

	@Override
	public void setViewController(IViewController arg0) {
		// TODO Auto-generated method stub

	}

}
