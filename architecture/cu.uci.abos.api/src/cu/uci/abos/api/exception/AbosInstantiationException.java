package cu.uci.abos.api.exception;

public class AbosInstantiationException extends Exception {

	private static final long serialVersionUID = -2959886016590898742L;

	public AbosInstantiationException() {
		super();
	}

	public AbosInstantiationException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AbosInstantiationException(String message, Throwable cause) {
		super(message, cause);
	}

	public AbosInstantiationException(String message) {
		super(message);
	}

	public AbosInstantiationException(Throwable cause) {
		super(cause);
	}

}
