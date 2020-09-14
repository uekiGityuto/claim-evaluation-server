package jp.co.tokiomarine_nichido.models;

/**
 * 認可処理の結果をまとめるクラス
 *
 * @author SKK231527 植木宥登
 *
 */
public class AuthResult {

	private String userId;
	private boolean authFlag;
	private String claimNumber;

	public AuthResult(String userId, boolean authFlag, String claimNumber) {
		this.userId = userId;
		this.authFlag = authFlag;
		this.claimNumber = claimNumber;
	}
}
