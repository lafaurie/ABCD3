package cu.uci.abcd.management.db.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.management.db.l10n.AbosMessages;
import cu.uci.abos.core.widget.grid.IEditableArea;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;
public class FDTAddArea implements IEditableArea {

	private Map<String, Control> controls;
	private Group formGroup;
	private Label tagLbl;
	private Label nameLbl;
	private Label typeLbl;
	private Label indicatorsLbl;
	private Label repLbl;
	private Label firstSubfieldLbl;
	private Label subfieldPatternLbl;
	private Combo typeCmb;
	private Spinner tagSpn;
	private Text nameTxt;
	private Button firstSubfieldChk;
	private Text subfieldPatternTxt;
	private Button repChk;
	private Button indicatorsChk;
	private Button saveBtn;
	private Combo combo;
	
	public FDTAddArea()  {
		this.controls = new HashMap<String, Control>();
	}
	
	@Override
	public Composite createUI(final Composite parent, IGridViewEntity entity, IVisualEntityManager manager) {
		
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		//FormDatas.attach(parent).atLeft(0).atRight(0);
		
	    combo = new Combo(parent, SWT.READ_ONLY);
	   // FormDatas.attach(combo).atTop(0).atLeft(0).atRight(0);
		
		formGroup = new Group(parent, SWT.NONE);
    	//FormDatas.attach(formGroup).atTop(0).atLeft(0).atRight(0);
    	formGroup.setLayout(new FormLayout());		
		
		tagLbl = new Label(formGroup, SWT.NONE);
		//FormDatas.attach(tagLbl).atTop(0).atLeft(5);
		
		tagSpn = new Spinner(formGroup, SWT.BORDER);
		//FormDatas.attach(tagSpn).atTopTo(tagLbl, 5).atLeft(5).withWidth(40);
		tagSpn.setValues(0, 0, Integer.MAX_VALUE, 0, 1, 1);
	
		nameLbl = new Label(formGroup, SWT.NONE);
		//FormDatas.attach(nameLbl).atTop(0).atLeftTo(tagSpn, 10);		
		
		nameTxt = new Text(formGroup, SWT.BORDER);
		//FormDatas.attach(nameTxt).atTopTo(nameLbl, 5).atLeftTo(tagSpn, 10).withWidth(150);
	//
		typeLbl = new Label(formGroup, SWT.NONE);
		//FormDatas.attach(typeLbl).atTop(0).atLeftTo(nameTxt, 10);		
		
		//Combo nomenclador. Permite trabajar con entidades.
		//Acepta también enums, siempre que estos implementen la interfaz INomenclator. 
		//También acepta colecciones de INomenclator. Ver implementación de FDType.
//		typeCmb = new NCombo(formGroup, SWT.BORDER);
//		typeCmb.setNomenclators(FDType.class);
//		FormDatas.attach(typeCmb).atTopTo(typeLbl, 5).atLeftTo(nameTxt, 10);
		
		indicatorsLbl = new Label(formGroup, SWT.NONE);
		//FormDatas.attach(indicatorsLbl).atTop(0).atLeftTo(typeCmb, 20);		
		
		indicatorsChk = new Button(formGroup, SWT.CHECK);
		//FormDatas.attach(indicatorsChk).atTopTo(indicatorsLbl, 5).atLeftTo(typeCmb, 20);
		
		repChk = new Button(formGroup, SWT.CHECK);
		//FormDatas.attach(repChk).atTopTo(indicatorsLbl, 5).atLeftTo(indicatorsLbl, 20);
		
		repLbl = new Label(formGroup, SWT.NONE);
		//FormDatas.attach(repLbl).atTop(0).atLeftTo(indicatorsLbl, 20);		
		
		firstSubfieldLbl = new Label(formGroup, SWT.NONE);
		//FormDatas.attach(firstSubfieldLbl).atTop(0).atLeftTo(repLbl, 60);		
		
		firstSubfieldChk = new Button(formGroup, SWT.CHECK);
		//FormDatas.attach(firstSubfieldChk).atTopTo(repLbl, 5).atLeftTo(repLbl, 60);
		
		subfieldPatternTxt = new Text(formGroup, SWT.BORDER);
		//FormDatas.attach(subfieldPatternTxt).atTopTo(firstSubfieldLbl, 5).atLeftTo(firstSubfieldLbl, 20).withWidth(100);
		
		subfieldPatternLbl = new Label(formGroup, SWT.NONE);
		//FormDatas.attach(subfieldPatternLbl).atTop(0).atLeftTo(firstSubfieldLbl, 20);		
		
		l10n();
		return parent;
	}
	
	@Override
	public Composite createButtons(Composite parent, final IGridViewEntity entity, final IVisualEntityManager manager) {
		
		saveBtn = new Button(parent, SWT.PUSH);
		//saveBtn.setText("Guardar");
		
//		saveBtn.addSelectionListener(new SelectionListener() {
//			private static final long serialVersionUID = 1L;
//			@Override
//			public void widgetSelected(SelectionEvent event) {
//				FDTViewEntity fdtEntity = new FDTViewEntity(
//						Integer.parseInt(tagSpn.getText()), 
//						nameTxt.getText(), 
////						(FDType)typeCmb.getSelectedNomenclator(), 
//						indicatorsChk.getSelection(), 
//						repChk.getSelection(), 
//						firstSubfieldChk.getSelection(), 
//						subfieldPatternTxt.getText()
//				);
//				//Sustituir por consulta para insertar. No olvidar siempre ir a la última página al adicionar.
//				MockDao.insert(fdtEntity);
//				manager.goToLastPage();
//			}			
//			@Override
//			public void widgetDefaultSelected(SelectionEvent arg0) {
//				//do nothing
//			}			
//		});
		
		l10n();
		return parent;
	}

	@Override
	public boolean isValid() {
		// TODO Iterar el mapa de controles y verificar su validez.
		return false;
	}
///FIXEDDDDDD
	public Control getControl(String key) {
		return this.controls.get(key);
	}

	@Override
	public boolean closable() {
		return true;
	}

	@Override
	public void l10n() {		
		formGroup.setText(AbosMessages.get().GROUP_EDITABLE_AREA_FDT);
		tagLbl.setText(AbosMessages.get().HEADER_TAG_FDT);
		nameLbl.setText(AbosMessages.get().HEADER_NAME_FDT);
		typeLbl.setText(AbosMessages.get().HEADER_TYPE);
		indicatorsLbl.setText(AbosMessages.get().HEADER_INDICATORS);
		repLbl.setText(AbosMessages.get().HEADER_REP);
		firstSubfieldLbl.setText(AbosMessages.get().HEADER_FIRST_SUBFIELD);
		subfieldPatternLbl.setText(AbosMessages.get().HEADER_SUBFIELDS_PATTERN_FDT);
		saveBtn.setText(AbosMessages.get().BUTTON_ADD_FDT);
	}
}
