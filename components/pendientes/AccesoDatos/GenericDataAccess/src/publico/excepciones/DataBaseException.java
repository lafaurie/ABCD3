package publico.excepciones;

public class DataBaseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6015207017242084172L;
	
	public DataBaseException() {
		super();
	}

	public DataBaseException(String mensaje) {
		super(mensaje);
	}
	
	public DataBaseException(Throwable excepcion, String mensaje){
		super(mensaje, excepcion);
	}

}
