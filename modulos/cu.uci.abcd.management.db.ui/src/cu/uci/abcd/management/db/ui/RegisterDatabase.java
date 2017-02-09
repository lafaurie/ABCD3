package cu.uci.abcd.management.db.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.unesco.jisis.corelib.sorting.ListSort;

import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.ui.PagePainter;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;

public class RegisterDatabase extends ContributorPage implements Contributor {
	CRUDTreeTable tabla;
	Label databaseNameLabel;
	Label databaseNameComboLabel;
	Text databaseNameInputText;
	Text fieldNameInputText;
	Button cancelBtn;
	Button finish;
	Button next;
	Button back;
	Label buttonSeparator;
	Label databaseHitsLabel;
	Label tag;
	Label namefield;
	PagePainter painter;
	String loan_BD;
	Composite leftComposite;
	Composite centerComposite;
	Composite downComposite;
	RegisterDatabaseName databaseName;
	Composite databaseNameComposite;
	String databaseNameSaved;
	RegisterDatabaseStructure databaseStructure;
	Composite databaseStructureComposite;
    RegisterDatabaseWorksheets worksheets;
	Composite worksheetsComposite;
	RegisterDatabaseSelectionTable selectionTable;
	Composite selectionTableComposite;
	List<Composite> listCompositeCenter = new ArrayList<>();
	List<Label> listLeftLabel = new ArrayList<>();
	
	Label first;
	Label second;
	Label third;
	Label fourth;
	
	List<Auxiliar> listStructure = new ArrayList<>();

	@Override
	public Control createUIControl(Composite parent) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		//*ABAJO**//
		downComposite = new Composite(parent, SWT.BORDER);
		downComposite.setLayout(new FormLayout());
		downComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(downComposite).atBottom(5).atLeft(5).atRight(5).withHeight(35);
		
		cancelBtn =  new Button(downComposite, SWT.NONE);
		cancelBtn.setText("Cancelar");
		FormDatas.attach(cancelBtn).atRight(5).withWidth(80).atBottom(5).atTop(5);
		
		finish =  new Button(downComposite, SWT.NONE);
		finish.setText("Finalizar");
		finish.setEnabled(false);
		FormDatas.attach(finish).atRightTo(cancelBtn, 10).withWidth(80).atBottom(5).atTop(5);
		
		next =  new Button(downComposite, SWT.NONE);
		next.setText("Siguiente");
		FormDatas.attach(next).atRightTo(finish, 10).withWidth(80).atBottom(5).atTop(5);
		
		back =  new Button(downComposite, SWT.NONE);
		back.setText("Atras");
		back.setEnabled(false);
		FormDatas.attach(back).atRightTo(next, 10).withWidth(80).atBottom(5).atTop(5);
		
		
		/**IZQUIERDA**/
		leftComposite = new Composite(parent, SWT.BORDER);
		leftComposite.setLayout(new FormLayout());
		leftComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(leftComposite).atBottomTo(downComposite, 5).atTop(5).atLeft(5).withWidth(160);
		
		first = new Label(leftComposite, SWT.NONE);
		first.setText("1. Entre el nombre de la \n    Base de Datos");
		first.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		FormDatas.attach(first).atTop(15).atLeft(5).atRight(0);
		
		second = new Label(leftComposite, SWT.NONE);
		second.setText("2. Estructura de la \n    Base de Datos");
		FormDatas.attach(second).atTopTo(first, 10).atLeft(5).atRight(0);
		
		third = new Label(leftComposite, SWT.NONE);
		third.setText("3. Datos de la \n    Hoja de Trabajo");
		FormDatas.attach(third).atTopTo(second, 10).atLeft(5).atRight(0);
		
		fourth = new Label(leftComposite, SWT.NONE);
		fourth.setText("4. Tabla de Seleccion \n    de Campo");
		FormDatas.attach(fourth).atTopTo(third, 10).atLeft(5).atRight(0);
		
		listLeftLabel.add(first);
		listLeftLabel.add(second);
		listLeftLabel.add(third);
		listLeftLabel.add(fourth);
		
		
		//**CENTRO **/
		centerComposite = new Composite(parent, SWT.BORDER);
		centerComposite.setLayout(new FormLayout());
		centerComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(centerComposite).atBottomTo(downComposite, 5).atTop(5).atLeftTo(leftComposite).atRight(5);

		databaseName =  new RegisterDatabaseName();
		databaseNameComposite = (Composite) databaseName.createUIControl(centerComposite);
		listCompositeCenter.add(databaseNameComposite);
		
		databaseStructure =  new RegisterDatabaseStructure();
		databaseStructureComposite = (Composite) databaseStructure.createUIControl(centerComposite);
		listCompositeCenter.add(databaseStructureComposite);
		databaseStructureComposite.setVisible(false);
		
		worksheets =  new RegisterDatabaseWorksheets();
		worksheetsComposite = (Composite) worksheets.createUIControl(centerComposite);
		listCompositeCenter.add(worksheetsComposite);
		worksheetsComposite.setVisible(false);
		
		selectionTable =  new RegisterDatabaseSelectionTable();
		selectionTableComposite = (Composite) selectionTable.createUIControl(centerComposite);
		listCompositeCenter.add(selectionTableComposite);
		selectionTableComposite.setVisible(false);
		
		
		next.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;
			@Override
			public void widgetSelected(SelectionEvent e) {
				steps(1);
				
			}
		});
		
		back.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;
			@Override
			public void widgetSelected(SelectionEvent e) {
				steps(2);
			}
			
		});
		
		l10n();
		return parent;
	}

	@Override
	public String getID() {
		return "AdministrarBaseDatoID";
	}

	private void steps(int id) {
		int visible = 0;
		int pos = 0;
		for (int i = 0; i < listCompositeCenter.size(); i++) {
			if (listCompositeCenter.get(i).isVisible()) {
				if(id==1){
					visible = i + 1;
				}else{
					visible = i - 1;
				}
				pos = i;
			}
		}
		hideAll();
		if (visible == 3) {
			next.setEnabled(false);
			finish.setEnabled(true);
		}else{
			next.setEnabled(true);
			finish.setEnabled(false);
		}
		if (visible > 0) {
			back.setEnabled(true);
		}else{
			back.setEnabled(false);
		}
		listCompositeCenter.get(visible).setVisible(true);
		disableAllLeftLabel();
		switch (visible) {
		case 0:
			first.setData(RWT.CUSTOM_VARIANT, "groupLeft");
			break;
		case 1:
			second.setData(RWT.CUSTOM_VARIANT, "groupLeft");
			break;
		case 2:
			third.setData(RWT.CUSTOM_VARIANT, "groupLeft");
			break;
		case 3:
			fourth.setData(RWT.CUSTOM_VARIANT, "groupLeft");
			break;
		}
		
		if( listCompositeCenter.get(pos).equals(databaseNameComposite) ){
			databaseNameSaved = databaseName.getDatabaseNameText().getText();
			
		}
		if( listCompositeCenter.get(pos).equals(databaseStructureComposite)  ){
			//coger datos del 2do composite
			listStructure = databaseStructure.getAuxiliarList();
			worksheets.setListStructure(listStructure);
			worksheets.updateTable();
         
		}
		
		if( listCompositeCenter.get(pos).equals(worksheetsComposite)  ){
			//coger datos del 3er composite
		}
		
		if( listCompositeCenter.get(pos).equals(selectionTableComposite)  ){
			//coger datos del 4to composite
		}
	}
	
	private void hideAll() {
		for (int i = 0; i < listCompositeCenter.size(); i++) {
			listCompositeCenter.get(i).setVisible(false);
		}
		
	}
	private void disableAllLeftLabel(){
		for (int i = 0; i < listLeftLabel.size(); i++) {
			listLeftLabel.get(i).setData(RWT.CUSTOM_VARIANT, null);
		}
	}
	

	@Override
	public void l10n() {

		//cancelBtn.setText(MessageUtil.escape((AbosMessages.get().BUTTON_CANCEL)));
		//finish.setText(MessageUtil.escape(AbosMessages.get().BUTTON_FINISH));
		//next.setText(MessageUtil.escape(AbosMessages.get().BUTTON_NEXT));
		//back.setText(MessageUtil.escape(AbosMessages.get().BUTTON_BACK));
		//databaseNameLabel.setText(MessageUtil.escape((AbosMessages.get().NEW_DATABASE_NAME_LABEL)));
		//databaseNameInputText.setMessage(MessageUtil.unescape((AbosMessages.get().NEW_DATABASE_NAME_LABEL)));
		
		databaseName.l10n();
		databaseStructure.l10n();
		worksheets.l10n();
		selectionTable.l10n();
	} 

	@Override
	public String contributorName() {
		return "Nueva";
	}

	@Override
	public boolean canClose() {
		return true;
	}

	@Override
	public void setViewController(ViewController controller) {
		this.controller = controller;
	}
}
