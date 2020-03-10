package jp.co.tokiomarine_nichido.exceptions;

/**
 * REST APIの入力値が不正の場合の例外。
 * 
 * @author SKK229873 中山真吾
 *
 */
public class InvalidInputException extends Exception {

	private static final long serialVersionUID = -5280426672115001407L;

	public InvalidInputException(String message) {
		super(message);
	}
}
