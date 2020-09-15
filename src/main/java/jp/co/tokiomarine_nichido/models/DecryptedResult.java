package jp.co.tokiomarine_nichido.models;

import java.time.Instant;

/**
 * 暗号データの復号結果をマッピングするクラス
 *
 * @author SKK231527 植木宥登
 *
 */
public class DecryptedResult {

	// GNetから連携される権限（0:担当者権限、1:損業権限）
	private String AuthFlag;
	public  String ClaimNo;
	private String CreateDate;

	public DecryptedResult(String AuthFlag, String ClaimNo, String CreateDate) {
		// 損業権限フラグ
		this.AuthFlag = AuthFlag;
		// 受付番号
		this.ClaimNo = ClaimNo;
		// URL生成時刻
		this.CreateDate = CreateDate;
	}

	public boolean isAuthority() {
		if( this.AuthFlag.equals("1")) {
			return true;
		}
		return false;
	}

	// 現在時刻 - URL生成時刻 <= 10であるかどうかチェック
	public boolean isCorrectDate() {
		Instant createDate = Instant.parse(this.CreateDate);
		Instant currentDate = Instant.now();
		if (createDate.plusSeconds(10).compareTo(currentDate) >= 0) {
			return true;
		};
		return false;
	}

	public AuthResult createAuthResult (String userId) {
		return new AuthResult(userId, this.isAuthority(), this.ClaimNo);
	}

}
