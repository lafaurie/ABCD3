package cu.uci.abos.dataprovider.jisis.domain;

import java.util.ArrayList;
import java.util.List;

import cu.uci.abos.dataprovider.jisis.domain.Esquema.Coleccion.HojaTrabajo.CampoHojaTrabajo;


public class Registro {

	private List<CampoHojaTrabajo> camposHojaTrabajo;

	private Registro() {
		this.camposHojaTrabajo = new ArrayList<CampoHojaTrabajo>();
	}

	public List<CampoHojaTrabajo> getCamposHojaTrabajo() {
		return camposHojaTrabajo;
	}

	public void setCamposHojaTrabajo(List<CampoHojaTrabajo> camposHojaTrabajo) {
		this.camposHojaTrabajo = camposHojaTrabajo;
	}

}
