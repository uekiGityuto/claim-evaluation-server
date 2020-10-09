package jp.co.tokiomarine_nichido.models;

/**
 * 認可処理の結果をまとめるクラス
 *
 * @author SKK231527 植木宥登
 *
 */
public class AuthorizationResult {

	public String userId;
	public boolean authFlag;
	public String claimNumber;

	public AuthorizationResult(String userId, boolean authFlag, String claimNumber) {
		this.userId = userId;
		this.authFlag = authFlag;
		this.claimNumber = claimNumber;
	}
}
