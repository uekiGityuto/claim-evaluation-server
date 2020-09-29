package jp.co.tokiomarine_nichido.models;

import java.text.MessageFormat;
import java.time.Instant;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

	// @Inject
	// private PropertyManager pm;

	// private static final Logger logger = LogManager.getLogger(DecryptedResult.class);

	// 損業権限フラグ（0:担当者権限、1:損業権限）
	@NotNull
	@Size(min=1)
	@SerializedName("Authflag")
	private String authFlag;

	// 受付番号
	@NotNull
	@Size(min=1)
	@SerializedName("ClaimNo")
	private String claimNumber;

	// URL生成時刻
	@NotNull
	@Size(min=1)
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
		// TODO: Arquillianを利用したテストが出来るようになればInjectするように変更
		PropertyManager pm = new PropertyManager();

		Instant createDate = Instant.parse(this.createDate);
		Instant currentDate = Instant.now();
		if (createDate.plusSeconds(10).compareTo(currentDate) < 0) {
			// TODO: eも渡す必要があるか確認。
			throw new AuthorizationFailedException(
					MessageFormat.format(pm.get("E003"), createDate, currentDate, pm.get("E003.interval")));
		}
		return true;
	}

	public AuthorizationResult createAuthorizationResult(String userId) {
		return new AuthorizationResult(userId, this.isAuthority(), this.claimNumber);
	}

}
