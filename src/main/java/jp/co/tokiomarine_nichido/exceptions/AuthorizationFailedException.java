package jp.co.tokiomarine_nichido.exceptions;

/**
 * 認可が失敗したときに投げられる例外。
 *
 * @author SKK231527 植木宥登
 *
 */
public class AuthorizationFailedException extends Exception {

	private static final long serialVersionUID = 1759096597793494710L;

	public AuthorizationFailedException() {
		super();
	}

	public AuthorizationFailedException(String message) {
		super(message);
	}

	public AuthorizationFailedException(String message, Throwable cause) {
		super(message, cause);
	}


}
