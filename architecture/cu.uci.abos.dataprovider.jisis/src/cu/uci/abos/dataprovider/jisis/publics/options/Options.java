package cu.uci.abos.dataprovider.jisis.publics.options;

public class Options {
	
	String campo, termino;	
	
	public Options(String campo, String termino) {
		super();
		this.campo = campo;
		this.termino = termino;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getTermino() {
		return termino;
	}

	public void setTermino(String termino) {
		this.termino = termino;
	}	

}
