package cu.uci.abcd.management.db.ui;

import javax.persistence.Table;

import org.eclipse.swt.widgets.Composite;
import org.unesco.jisis.corelib.common.FieldDefinitionTable.FieldDefinition;

import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IGridViewEntity;

//extends BaseGridViewEntity implements IGridViewEntity 
public class FDTViewEntity {

	private CRUDTreeTable tabl;
	private FDType fdType;
	private FieldDefinition isisFieldDefinition;

	public FDTViewEntity(int tag, String name, FDType type, boolean indicators, boolean repeatable, boolean firstSubfield, String subfields) {
		super();
		this.isisFieldDefinition = new FieldDefinition(tag, name, Integer.parseInt(type.getId().toString()), indicators, repeatable, firstSubfield, subfields);
		this.fdType = type;	

	}


	//  @TreeTableColumn(percentWidth = 10, index = 0)
	public int getTag() {
		return this.isisFieldDefinition.getTag();
	};

	//@TreeTableColumn(percentWidth = 34, index = 1)
	public String getName() {
		return this.isisFieldDefinition.getName();
	}

	public int getType() {
		return this.isisFieldDefinition.getType();
	}

	//  @TreeTableColumn(percentWidth = 16, index = 2)
	public String getTypeName() {
		return this.fdType.name();
	}

	public FDType getFDType() {
		return this.fdType;
	}

	//  @TreeTableColumn(percentWidth = 10, index = 3)
	public boolean hasIndicators() {
		return this.isisFieldDefinition.hasIndicators();
	}
	//
	//	// @TreeTableColumn(percentWidth = 10, index = 4)
	public boolean isRepeatable() {
		return this.isisFieldDefinition.isRepeatable();
	}

	//	//@TreeTableColumn(percentWidth = 10, index = 5)
	public boolean hasFirstSubfield() {
		return this.isisFieldDefinition.hasFirstSubfield();
	};

	//	// @TreeTableColumn(percentWidth = 10, index = 6)
	public String getSubfields() {
		return this.isisFieldDefinition.getSubfields();
	}

//	public Composite createTable(){
//		//		
//		//		Composite prueba = new Composite(parent, SWT.0);
//		//		
//		//		tabl = new CRUDTreeTable(parent, SWT.NONE);
//		//		FormDatas.attach(tabla).atTopTo(lbCoincidenceList, 10).atLeft(15).atRight(15);
//		//		tabl.setVisible(false);			
//		//		tabl.setEntityClass(LoanObject.class);	
//		//		tabl.setWatch(true, new ViewAreaLoanObject(controller));
//		//		tabla.setUpdate(true, new GestionarImagen());
//		//		tabl.setCancelButtonText("Cancelar");     //For the internationalization. If it is non set, only the icon will show up.
//
//		TreeTableColumn columns [] = {
//				new TreeTableColumn(10, 0, "getTag()"),
//				new TreeTableColumn(34, 1, "getName()"),
//				new TreeTableColumn(16, 2, "name()"),
//				new TreeTableColumn(10, 3, "hasIndicators()"),
//				new TreeTableColumn(10, 4, "isRepeatable()"),
//				new TreeTableColumn(10, 5, "hasFirstSubfield()"),
//				new TreeTableColumn(10, 6, "getSubfields()")};
//
//		tabl.createTable(columns);
//		return tabl;
//	}

	public void setTag(int tag) {
		FieldDefinition copy = new FieldDefinition(
				tag, this.isisFieldDefinition.getName(), this.isisFieldDefinition.getType(), 
				this.isisFieldDefinition.hasIndicators(), this.isisFieldDefinition.isRepeatable(), 
				this.isisFieldDefinition.hasFirstSubfield(), this.isisFieldDefinition.getSubfields());
		this.isisFieldDefinition = copy;
	}

	public void setType(int type) {
		this.isisFieldDefinition.setType(type);
	}

	public void setType(FDType type) {
		this.isisFieldDefinition.setType(Integer.parseInt(type.getId().toString()));
		this.fdType = type;
	}

	public void setIndicators(boolean hasIndicators) {
		this.isisFieldDefinition.setIndicators(hasIndicators);
	}

	public void setSubfields(String subfields) {
		this.isisFieldDefinition.setSubfields(subfields);
	}

	public void setName(String name) {
		FieldDefinition copy = new FieldDefinition(
				this.isisFieldDefinition.getTag(), name, this.isisFieldDefinition.getType(), 
				this.isisFieldDefinition.hasIndicators(), this.isisFieldDefinition.isRepeatable(), 
				this.isisFieldDefinition.hasFirstSubfield(), this.isisFieldDefinition.getSubfields());
		this.isisFieldDefinition = copy;

	}

	public void setRepeatable(boolean repeatable) {
		FieldDefinition copy = new FieldDefinition(
				this.isisFieldDefinition.getTag(), this.isisFieldDefinition.getName(), this.isisFieldDefinition.getType(), 
				this.isisFieldDefinition.hasIndicators(), repeatable, 
				this.isisFieldDefinition.hasFirstSubfield(), this.isisFieldDefinition.getSubfields());
		this.isisFieldDefinition = copy;
	}

	public void setHasFirstSubfield(boolean hasFirstSubfield) {
		FieldDefinition copy = new FieldDefinition(
				this.isisFieldDefinition.getTag(), this.isisFieldDefinition.getName(), this.isisFieldDefinition.getType(), 
				this.isisFieldDefinition.hasIndicators(), this.isisFieldDefinition.isRepeatable(), 
				hasFirstSubfield, this.isisFieldDefinition.getSubfields());
		this.isisFieldDefinition = copy;		
	}

	public boolean equals(IGridViewEntity other) {
		if(this.getTag() == ((FDTViewEntity)other).getTag()) {
			return true;
		}
		return false;
	}
}
