package cu.uci.abcd.management.db.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import cu.uci.abos.core.ui.PagePainter;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.TreeTableColumn;



//extends BaseGridViewEntity implements IGridViewEntity 
public class CSearchEntity {

    private int idFST;
    private String nombreCampo;
    private String prefijo;
    private String ordenable;
    private CRUDTreeTable tabla;
    PagePainter painter;

    public CSearchEntity(int idFST, String nombreCampo, String prefijo, String ordenable) {
	super();
	this.idFST = idFST;
	this.nombreCampo = nombreCampo;
	this.prefijo = prefijo;
	this.ordenable = ordenable;
    }

    public CSearchEntity() {
    }

   // @TreeTableColumn(header = "ID FST", percentWidth = 10, index = 0)
    public int getIdFST() {
	return idFST;
    }

    public void setIdFST(int idFST) {
	this.idFST = idFST;
    }

    //@TreeTableColumn(header = "Nombre del Campo", percentWidth = 45, index = 1)
    public String getNombreCampo() {
	return nombreCampo;
    }

    public void setNombreCampo(String nombreCampo) {
	this.nombreCampo = nombreCampo;
    }

   // @TreeTableColumn(header = "Prefijo", percentWidth = 25, index = 2)
    public String getPrefijo() {
	return prefijo;
    }

    public void setPrefijo(String prefijo) {
	this.prefijo = prefijo;
    }

   // @TreeTableColumn(header = "Ordenable", percentWidth = 20, index = 3)
    public String getOrdenable() {
	return ordenable;
    }

    public Composite createTable(Composite parent){
		//		
				Composite prueba = new Composite(parent, SWT.NONE);
				painter.addComposite(prueba);
				tabla = new CRUDTreeTable(prueba, SWT.NONE);
				painter.add(tabla);
		//		
		//		tabl = new CRUDTreeTable(parent, SWT.NONE);
		//		FormDatas.attach(tabla).atTopTo(lbCoincidenceList, 10).atLeft(15).atRight(15);
		//		tabl.setVisible(false);			
		//		tabl.setEntityClass(LoanObject.class);	
		//		tabl.setWatch(true, new ViewAreaLoanObject(controller));
		//		tabla.setUpdate(true, new GestionarImagen());
		//		tabl.setCancelButtonText("Cancelar");     //For the internationalization. If it is non set, only the icon will show up.

		TreeTableColumn columns [] = {
				new TreeTableColumn(10, 0, "getIdFST()"),
				new TreeTableColumn(45, 1, "getNombreCampo()"),
				new TreeTableColumn(25, 2, "getPrefijo()"),
				new TreeTableColumn(20, 3, "getOrdenable()")};

		tabla.createTable(columns);
		return tabla;
	}
    public void setOrdenable(String ordenable) {
	this.ordenable = ordenable;
    }	
}
