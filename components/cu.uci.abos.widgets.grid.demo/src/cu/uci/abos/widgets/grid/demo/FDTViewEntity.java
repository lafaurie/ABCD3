package cu.uci.abos.widgets.grid.demo;

import org.unesco.jisis.corelib.common.FieldDefinitionTable.FieldDefinition;

import cu.uci.abos.widgets.grid.BaseGridViewEntity;
import cu.uci.abos.widgets.grid.IGridViewEntity;
import cu.uci.abos.widgets.grid.TreeTableColumn;

//Fíjense en este patrón (Wrapper). Siempre heredan de BaseGridViewEntity. Luego encapsulan la entidad de base de datos.
//En mi caso es una entidad de JISIS (FieldDefinition), la cual no podría anotar directamente aunque quisiera.
//Próximamente se podrá controlar las columnas que se muestran y las que no.
//No olvidar implementar el método equals. Este se utiliza en el componente internamente.
public class FDTViewEntity extends BaseGridViewEntity implements IGridViewEntity {

	private FDType fdType;
	private FieldDefinition isisFieldDefinition;
	
    public FDTViewEntity(int tag, String name, FDType type, boolean indicators, boolean repeatable, boolean firstSubfield, String subfields) {
    	super();
    	this.isisFieldDefinition = new FieldDefinition(tag, name, Integer.parseInt(type.getId().toString()), indicators, repeatable, firstSubfield, subfields);
    	this.fdType = type;
    }

    @TreeTableColumn(percentWidth = 10, index = 0)
    public int getTag() {
    	return this.isisFieldDefinition.getTag();
    };

    @TreeTableColumn(percentWidth = 34, index = 1)
    public String getName() {
    	return this.isisFieldDefinition.getName();
    }
    
    public int getType() {
    	return this.isisFieldDefinition.getType();
    }
    
    @TreeTableColumn(percentWidth = 16, index = 2)
    public String getTypeName() {
    	return this.fdType.name();
    }
    
    public FDType getFDType() {
    	return this.fdType;
    }

    @TreeTableColumn(percentWidth = 10, index = 3)
    public boolean hasIndicators() {
    	return this.isisFieldDefinition.hasIndicators();
    }

    @TreeTableColumn(percentWidth = 10, index = 4)
    public boolean isRepeatable() {
    	return this.isisFieldDefinition.isRepeatable();
    }

    @TreeTableColumn(percentWidth = 10, index = 5)
    public boolean hasFirstSubfield() {
    	return this.isisFieldDefinition.hasFirstSubfield();
    };

    @TreeTableColumn(percentWidth = 10, index = 6)
    public String getSubfields() {
    	return this.isisFieldDefinition.getSubfields();
    }
    
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
	
	@Override
	public boolean equals(IGridViewEntity other) {
		if(this.getTag() == ((FDTViewEntity)other).getTag()) {
			return true;
		}
		return false;
	}
}
