package cu.uci.abcd.opac.contribution;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
//import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
//import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
 
import cu.uci.abos.ui.api.IContributor;
import cu.uci.abos.ui.api.IViewController;
import cu.uci.abos.util.api.FormDatas;

public class Adquisicion implements IContributor {
	public   Composite result;
	private Map<String, Control> controls;
	
    public Adquisicion(){
		this.controls = new HashMap<String, Control>();
	}
		
	 
	@Override
	public Control createUIControl(final Composite parent) {

		Composite result = new Composite(parent, 0);
		result.setData(RWT.CUSTOM_VARIANT, "workspace_content");
		result.setLayout(new FormLayout());	
		
		FormDatas.attach(result).atLeft(0).atRight(0).atTop(0).atBottom(0);
		
		final Group agregar = new Group(result, SWT.NONE);
		agregar.setLayout(new FormLayout());
		
		
		agregar.setText("Crear Nueva Sugerencia");
				
	      		
		FormDatas.attach(agregar).atTop(0).atLeft(0).atRight(0).withHeight(280);
		
		
		
		Label bookTitleLabel = new Label(agregar, SWT.NORMAL);
		bookTitleLabel.setText("Título:");
		FormDatas.attach(bookTitleLabel).atTopTo(agregar, 5).atLeftTo(agregar, 20);
		

		final Text bookTitleText = new Text(agregar, SWT.NORMAL);
		FormDatas.attach(bookTitleText).atTopTo(bookTitleLabel).atLeftTo(agregar, 10).withWidth(250);
		this.controls.put("bookTitleText", bookTitleText);
		
		
		Label obligado = new Label(agregar, SWT.NORMAL);
		obligado.setText("*");
		obligado.setFont( new Font( parent.getDisplay(), "Arial", 14, SWT.BOLD ) );
		FormDatas.attach(obligado).atTopTo(bookTitleLabel).atLeftTo(bookTitleText, 2); 

		
		
		Label bookAutorLabel = new Label(agregar, SWT.NORMAL);
		bookAutorLabel.setText("Autor:");
		FormDatas.attach(bookAutorLabel).atTopTo(bookTitleText, 10).atLeftTo(agregar, 20); 
		
	 
		final Text bookAutorText = new Text(agregar, SWT.NORMAL);
		FormDatas.attach(bookAutorText).atTopTo(bookAutorLabel).atLeftTo(agregar, 10).withWidth(250);
		this.controls.put("bookAutorText", bookAutorText);
		
			
	
		Label copyrightDateLabel = new Label(agregar, SWT.NORMAL);
		copyrightDateLabel.setText("Fecha de Copyright:");
		FormDatas.attach(copyrightDateLabel).atTopTo(bookAutorText, 10).atLeftTo(agregar, 20); 
		
	
		Text copyrightDateText = new Text(agregar, SWT.NORMAL);
		copyrightDateText.setText("dd/mm/aaaa");
		FormDatas.attach(copyrightDateText).atTopTo(copyrightDateLabel).atLeftTo(agregar, 10).withWidth(250);
		this.controls.put("copyrightDateText", copyrightDateText);
				
				
		Label bookEditorLabel = new Label(agregar, SWT.NORMAL);
		bookEditorLabel.setText("Editor:");
		FormDatas.attach(bookEditorLabel).atTopTo(copyrightDateText, 10).atLeftTo(agregar, 20); 
		
	 
		Text bookEditorText = new Text(agregar, SWT.NORMAL);
		FormDatas.attach(bookEditorText).atTopTo(bookEditorLabel).atLeftTo(agregar, 10).withWidth(250);
		this.controls.put("bookEditorText", bookEditorText);
		
			
		
		Label noteLabel = new Label(agregar, SWT.NORMAL);
		noteLabel.setText("Nota:");
		FormDatas.attach(noteLabel).atTopTo(bookEditorText, 10).atLeftTo(agregar, 20);
		

		Text noteText = new Text(agregar, SWT.NORMAL);
		FormDatas.attach(noteText).atTopTo(noteLabel).atLeftTo(agregar, 10).withWidth(250);
		this.controls.put("noteText", noteText);
				
		 
		
				
		
		Button sugerir = new Button(agregar, SWT.PUSH);
		sugerir.setText("Sugerir");	
		sugerir.setFont( new Font( parent.getDisplay(), "Arial", 14, SWT.BOLD ) );
		FormDatas.attach(sugerir).atBottom(0).atRight(50).withHeight(25).withWidth(65);
		
		
		Button cancelar = new Button(agregar, SWT.PUSH);
		cancelar.setText("Cancelar");	
		cancelar.setFont( new Font( parent.getDisplay(), "Arial", 14, SWT.BOLD ) );
		FormDatas.attach(cancelar).atBottom(0).atRightTo(sugerir,20).withHeight(25).withWidth(65);
				
		
		
		sugerir.addSelectionListener(new SelectionListener() {
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
			
		
		*/
			
		}			
		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			//do nothing
		}
	});
			
		
		
		
		
		
		
		/*
	cancelar.addSelectionListener(new SelectionListener() {
		private static final long serialVersionUID = 1L;
		@Override
		public void widgetSelected(SelectionEvent arg0) {				
	
		parent.dispose();
		  
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
		return "AdquisicionID";
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
		return "Nueva Sugerencia";
	}

	@Override
	public void setViewController(IViewController arg0) {
		// TODO Auto-generated method stub

	}

}
