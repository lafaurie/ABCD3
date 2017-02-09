//package cu.uci.abcd.management.db.ui.contributors;
//
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.widgets.Composite;
//import org.unesco.jisis.corelib.common.IWorksheetDef;
//
//import controller.ManageDatabasesController;
//import cu.uci.abos.widgets.grid.BaseGridViewEntity;
//import cu.uci.abos.widgets.grid.CRUDTreeTable;
//import cu.uci.abos.widgets.grid.IGridViewEntity;
//import cu.uci.abos.widgets.grid.TreeTableColumn;
//
////extends BaseGridViewEntity implements IWorksheetDef, IGridViewEntity 
//
//
//@SuppressWarnings("serial")
//public class DEWViewEntity {
//
//	private CRUDTreeTable tabla;
//    private FDTViewEntity fdtViewEntity;
//    private String description;
//    private String displayControl;
//    private String defaultValue;
//    private String helpMessage;
//    private String validationFormat;
//    private String pickList;
//    private String subfieldCode;
//
//    public DEWViewEntity(FDTViewEntity fdtViewEntity, String subfieldCode, String description, String displayControl, String defaultValue, String helpMsg, String valFormat, String pickList) {
//    	super();
//    	this.fdtViewEntity = fdtViewEntity;
//		this.subfieldCode = subfieldCode;
//		this.description = description;
//		this.displayControl = displayControl;
//		this.defaultValue = defaultValue;
//		this.helpMessage = helpMsg;
//		this.validationFormat = valFormat;
//		this.pickList = pickList;
//    }
//
//    //@TreeTableColumn(index = 0, percentWidth = 8)
//    public String getNodeName() {
//		if (this.isSubfield())
//		    return this.subfieldCode;
//		else
//		    return "tag " + this.fdtViewEntity.getTag();
//    }
//
//   // @TreeTableColumn(index = 1, percentWidth = 8)
//    public boolean isSubfield() {
//    	return this.subfieldCode != "" && this.subfieldCode != null;
//    }
//
//    // @TreeTableColumn(index = 2, percentWidth = 8)
//    public boolean hasIndicators() {
//    	return this.fdtViewEntity.hasIndicators();
//    }
//
//   // @TreeTableColumn(index = 3, percentWidth = 8)
//    public boolean isRepeatable() {
//    	return this.fdtViewEntity.isRepeatable();
//    }
//
//    //@TreeTableColumn(index = 4, percentWidth = 8)
//    public boolean hasFirstSubfield() {
//    	return this.fdtViewEntity.hasFirstSubfield();
//    }
//
//   // @TreeTableColumn(index = 5, percentWidth = 8)
//    public int getType() {
//    	return this.fdtViewEntity.getType();
//    }
//
//   // @TreeTableColumn(index = 6, percentWidth = 12)
//    public String getDescription() {
//    	return this.description;
//    }
//
//  //  @TreeTableColumn(index = 7, percentWidth = 8)
//    public String getDefaultValue() {
//    	return this.defaultValue;
//    }
//
//   // @TreeTableColumn(index = 8, percentWidth = 8)
//    public String getDisplayControl() {
//    	return this.displayControl;
//    }
//
//   // @TreeTableColumn(index = 9, percentWidth = 8)
//    public String getHelpMessage() {
//    	return this.helpMessage;
//    }
//
//    //@TreeTableColumn(index = 10, percentWidth = 8)
//    public String getPickList() {
//    	return this.pickList;
//    }
//
//   // @TreeTableColumn(index = 11, percentWidth = 8)
//    public String getValidationFormat() {
//    	return this.validationFormat;
//    }
//
//    public void setValidationFormat(String format) {
//    	this.validationFormat = format;
//    }
//    
//    public Composite createTable(){
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
//				new TreeTableColumn(8, 0, "getNodeName()"),
//				new TreeTableColumn(8, 1, "isSubfield()"),
//				new TreeTableColumn(8, 2, "hasIndicators()"),
//				new TreeTableColumn(8, 3, "isRepeatable()"),
//				new TreeTableColumn(8, 4, "hasFirstSubfield()"),
//				new TreeTableColumn(8, 5, "getType()"),
//				new TreeTableColumn(12,6, "getDescription()"),
//				new TreeTableColumn(8, 7, "getDefaultValue()"),
//				new TreeTableColumn(8, 8, "getDisplayControl()"),
//				new TreeTableColumn(8, 9, "getHelpMessage()"),
//				new TreeTableColumn(8,10, "getPickList()"),
//				new TreeTableColumn(8,11, "getValidationFormat()")};
//
//		tabla.createTable(columns);
//		return tabla;
//	}
//
//    public void setDefaultValue(String value) {
//    	this.defaultValue = value;
//    }
//
//    public void setHelpMessage(String message) {
//    	this.helpMessage = message;
//    }
//    
//    public void setPickList(String pickList) {
//    	this.pickList = pickList;
//    }
//
//
//    public int getTag() {
//    	return this.fdtViewEntity.getTag();
//    }
//
//    public void setDescription(String description) {
//    	this.description = description;
//    }
//
//    public void setTag(int tag) {
//    	this.fdtViewEntity.setTag(tag);
//    }
//
//    public String getSubfieldCode() {
//    	return this.subfieldCode;
//    }
//
//    public void setSubfieldCode(String code) {
//    	this.subfieldCode = code;
//    }
//
//    public void setDisplayControl(String control) {
//    	this.displayControl = control;
//    }
//
//	public boolean equals(IGridViewEntity otherEntity) {
//		System.out.println("Comparando entidades..." + this.fdtViewEntity.getTag() + " con " + ((DEWViewEntity)otherEntity).fdtViewEntity.getTag());
//		if(otherEntity.getClass() != DEWViewEntity.class) {
//			return false;
//		}
//		return this.fdtViewEntity.equals(((DEWViewEntity)otherEntity).fdtViewEntity);
//	}
//}
