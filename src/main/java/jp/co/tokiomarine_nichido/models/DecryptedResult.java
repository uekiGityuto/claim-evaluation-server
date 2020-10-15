package jp.co.tokiomarine_nichido.models;

import java.text.MessageFormat;
import java.time.Instant;

import com.google.gson.annotations.SerializedName;

import jp.co.tokiomarine_nichido.exceptions.AuthorizationFailedException;
import jp.co.tokiomarine_nichido.util.PropertyManager;

/**
 * 暗号データの復号結果をマッピングするクラス
 *
 * @author SKK231527 植木宥登
 *
 */
public class DecryptedResult {

	// 損業権限フラグ（0:担当者権限、1:損業権限）
	@SerializedName("Authflag")
	private String authFlag;

	// 受付番号
	@SerializedName("ClaimNo")
	private String claimNumber;

	// URL生成時刻
	@SerializedName("CreateDate")
	private String createDate;

	public DecryptedResult(String authFlag, String claimNumber, String createDate) {
		this.authFlag = authFlag;
		this.claimNumber = claimNumber;
		this.createDate = createDate;
	}

	public boolean isAuthority() {
		if (this.authFlag.equals("1")) {
			return true;
		}
		return false;
	}

	// 現在時刻 - URL生成時刻 <= 10であるかどうかチェック
	public boolean isCorrectDate() throws AuthorizationFailedException {
		PropertyManager pm = new PropertyManager();

		Instant createDate = Instant.parse(this.createDate);
		Instant currentDate = Instant.now();
		int timeLimit = Integer.parseInt(pm.get("url.timeLimit"));
		if (createDate.plusSeconds(timeLimit).compareTo(currentDate) < 0) {
			throw new AuthorizationFailedException(
					MessageFormat.format(pm.getLogMessage("E003"), createDate, currentDate, pm.get("url.timeLimit")));
		}
		return true;
	}

	public AuthorizationResult createAuthorizationResult(String userId) {
		return new AuthorizationResult(userId, this.isAuthority(), this.claimNumber);
	}

}
