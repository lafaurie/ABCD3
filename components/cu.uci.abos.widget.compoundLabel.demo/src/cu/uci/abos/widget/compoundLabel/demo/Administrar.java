package cu.uci.abos.widget.compoundLabel.demo;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import cu.uci.abos.l10n.AbosMessages;
import cu.uci.abos.ui.api.IContributor;
import cu.uci.abos.util.api.FormDatas;
import cu.uci.abos.widget.compoundLabel.CompoundLabel;
import cu.uci.abos.widget.compoundLabel.ControlType;



public class Administrar implements IContributor {

	public Composite result;
	public Label label;
	public Button radioButton;
	@Override
	public String contributorName() {
		// TODO Auto-generated method stub
		return AbosMessages.get().CONTRIBUTOR_REGISTER_BOOK;
	}

	@Override
	public int order() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public Control createUIControl(Composite parent) {
		
		Group result=new Group(parent, SWT.None);
		result.setLayout(new FormLayout());
		result.setText("Compound Label Demo");
		
		
		parent.setData(RWT.CUSTOM_VARIANT,"gray_background");
		
	     FormDatas.attach(result).atLeft(0).atRight(0);
		
		CompoundLabel cLabel=new CompoundLabel(result, SWT.NONE);
		cLabel.setText("Hola");
		
		
		CompoundLabel cLabel1=new CompoundLabel(result, SWT.NONE);
		cLabel1.setText("HolaMundo");
		
		
		
		CompoundLabel cLabel13=new CompoundLabel(result, SWT.NONE);
		cLabel13.setText("Hola123");
			
		
		CompoundLabel dateTime=new CompoundLabel(result, SWT.NONE, ControlType.DatePicker);
		dateTime.setText("Fecha Prestamos");
		
		
		CompoundLabel comboLabel=new CompoundLabel(result, SWT.NONE, ControlType.Combo);
		((Combo)comboLabel.getControl()).setItems(new String[] { "Contiene", "Invitado", "Revisor"});
		comboLabel.setText("Prueba..");
	
		
		Integer mayor=dateTime.getLabel().getSize().x;	
		
		//primera fila
		FormDatas.attach(cLabel).atTopTo(result, 10);
		Integer widthClabel=cLabel.getLabel().getSize().x;
		cLabel.setPosition(SWT.LEFT, (mayor-widthClabel)+10);		
		FormDatas.attach(cLabel1).atLeftTo(cLabel, 10).atTop(10);
		cLabel1.setPosition(SWT.LEFT, 10);	
		//segunda fila
		FormDatas.attach(cLabel13).atTopTo(cLabel1, 10);
		Integer width=cLabel13.getLabel().getSize().x;
		cLabel13.setPosition(SWT.LEFT, (mayor-width)+10);
		
		FormDatas.attach(dateTime).atTopTo(cLabel13, 10);
		dateTime.setPosition(SWT.LEFT, 10);	
		
		FormDatas.attach(comboLabel).atTopTo(dateTime, 10);
		Integer width1=comboLabel.getLabel().getSize().x;
		comboLabel.setPosition(SWT.LEFT, (mayor-width1)+10);
		
		
		CompoundLabel top=new CompoundLabel(parent, SWT.NONE);
		top.setText("Hola");
		top.setPosition(SWT.TOP, 5);
		FormDatas.attach(top).atTopTo(result,20);
		
		CompoundLabel top1=new CompoundLabel(parent, SWT.NONE,ControlType.DatePicker);
		top1.setText("Fecha Devolucion");
		top1.setPosition(SWT.TOP, 5);
		FormDatas.attach(top1).atTopTo(result,20).atLeftTo(top, 50);
		
		CompoundLabel top2=new CompoundLabel(parent, SWT.NONE,ControlType.Combo);
		((Combo)top2.getControl()).setItems(new String[] { "Contiene", "Invitado", "Revisor"});
		top2.setText("Estado");
		top2.setPosition(SWT.TOP, 5);
		FormDatas.attach(top2).atTopTo(result,20).atLeftTo(top1, 50);
		
		

		CompoundLabel top3=new CompoundLabel(parent, SWT.NONE);
		top3.setText("Hola");
		top3.setPosition(SWT.TOP, 5);
		FormDatas.attach(top3).atTopTo(result,20).atLeftTo(top2, 50);
		
		
	
		return parent;
		
	}
	
	

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return "RegistrarLibroID";
	}

	@Override
	public String containerMenu() {
		// TODO Auto-generated method stub
		
		label.setText(AbosMessages.get().LABEL_AUTHOR);
		radioButton.setText(AbosMessages.get().RADIO_AUTHORITIES);
		return "Administración";
	}

	@Override
	public boolean canClose() {
		// TODO Auto-generated method stub
		return false;
	}

}
