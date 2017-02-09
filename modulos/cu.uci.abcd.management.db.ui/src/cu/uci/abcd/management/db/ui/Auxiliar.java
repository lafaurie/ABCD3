package cu.uci.abcd.management.db.ui;

import java.io.Serializable;

import cu.uci.abos.core.domain.Row;

public class Auxiliar implements Serializable, Row{
	
	private int etiqueta;
	private String nombre;
	private String subcampos;
	private boolean indicadores;
	private boolean repetible;
	private boolean primersubcampo;
	private Type type;
	
	public Auxiliar(int etiqueta, String nombre, String subcampos,
			boolean indicadores, boolean repetible, boolean primersubcampo,
			Type type) {
		super();
		this.etiqueta = etiqueta;
		this.nombre = nombre;
		this.subcampos = subcampos;
		this.indicadores = indicadores;
		this.repetible = repetible;
		this.primersubcampo = primersubcampo;
		this.type = type;
	}

	public int getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(int etiqueta) {
		this.etiqueta = etiqueta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSubcampos() {
		return subcampos;
	}

	public void setSubcampos(String subcampos) {
		this.subcampos = subcampos;
	}

	public boolean isIndicadores() {
		return indicadores;
	}

	public void setIndicadores(boolean indicadores) {
		this.indicadores = indicadores;
	}

	public boolean isRepetible() {
		return repetible;
	}

	public void setRepetible(boolean repetible) {
		this.repetible = repetible;
	}

	public boolean isPrimersubcampo() {
		return primersubcampo;
	}

	public void setPrimersubcampo(boolean primersubcampo) {
		this.primersubcampo = primersubcampo;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public Object getRowID() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	
	
}
