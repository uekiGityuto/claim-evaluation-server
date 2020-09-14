package jp.co.tokiomarine_nichido.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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
		this.AuthFlag = AuthFlag;
		this.ClaimNo = ClaimNo;
		this.CreateDate = CreateDate;
	}

	public boolean isAuthority() {
		if( this.AuthFlag.equals("1")) {
			return true;
		}
		return false;
	}

	public boolean isCorrectDate(LocalDateTime currentDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		LocalDateTime createDate = LocalDateTime.parse(this.CreateDate, formatter);
		if (ChronoUnit.SECONDS.between(createDate, currentDate) <= 10) {
			return true;
		};
		return false;
	}

	public AuthResult createAuthResult (String userId) {
		return new AuthResult(userId, this.isAuthority(), this.ClaimNo);
	}

}
