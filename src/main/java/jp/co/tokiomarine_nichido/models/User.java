package jp.co.tokiomarine_nichido.models;

/***
 * ユーザ情報を格納するクラス。
 *
 * @author SKK229783 中山真吾
 * @author SKK231527 植木宥登
 *
 */
//@SessionScoped
public class User {
	// IF09から連携される、抜本認証からのユーザID
	private String userId;

	// GNetから連携される権限（0:担当者権限、1:損業権限）
	private boolean authFlag;

	public User(String userId, boolean authFlag) {
		this.userId = userId;
		this.authFlag = authFlag;
	}

	public String getUserId() {
		return this.userId;
	}

	public boolean isUserId() {
		if (this.userId == null || this.userId.isEmpty()) {
			return false;
		}
		return true;
	}

	public boolean isAuthFlag() {
		return this.authFlag;
	}

}
