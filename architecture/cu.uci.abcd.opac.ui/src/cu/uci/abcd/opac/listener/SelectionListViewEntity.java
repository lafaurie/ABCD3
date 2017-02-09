package cu.uci.abcd.opac.listener;

import java.util.List;

import cu.uci.abos.widgets.grid.IGridViewEntity;
import cu.uci.abos.widgets.grid.TreeTableColumn;

public class SelectionListViewEntity implements IGridViewEntity {
	
	private int tag;
	private String name;
	private int cantRegistros;
	private String ordenado;
	private String tipo;
	
	
	public SelectionListViewEntity(int tag, String name, int cantRegistros, String ordenado, String tipo) {
		this.tag = tag;
		this.name = name;
		this.cantRegistros = cantRegistros;
		this.ordenado = ordenado;
		this.tipo = tipo;
	}
	
	public SelectionListViewEntity() {
	}

	@TreeTableColumn(percentWidth=10, index=0)
	public int getTag() {
		return tag;
	}
	public void setTag(int tag) {
		this.tag = tag;
	}
	@TreeTableColumn(percentWidth = 50, index=1)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@TreeTableColumn(percentWidth = 10, index=2)
	public int getCantRegistros() {
		return cantRegistros;
	}
	public void setCantRegistros(int cantRegistros) {
		this.cantRegistros = cantRegistros;
	}	
	@TreeTableColumn(percentWidth = 10, index=3)
	public String getOrdenado() {
		return ordenado;
	}
	public void setOrdenado(String ordenado) {
		this.ordenado = ordenado;
	}
	@TreeTableColumn(percentWidth = 10, index=4)
	public String getTipo() {
		return tipo;
	}
	@TreeTableColumn(percentWidth = 10, index=5)
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	

	@Override
	public void addChild(IGridViewEntity child) {
		// do nothing		
	}

	@Override
	public List<IGridViewEntity> getChildren() {
		// do nothing
		return null;
	}
	
	
}
