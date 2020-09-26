package jp.co.tokiomarine_nichido.models;

import java.time.Instant;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jp.co.tokiomarine_nichido.exceptions.AuthorizationFailedException;
import jp.co.tokiomarine_nichido.util.PropertyManager;

/**
 * 暗号データの復号結果をマッピングするクラス
 *
 * @author SKK231527 植木宥登
 *
 */
public class DecryptedResult {

//	@Inject
//	private PropertyManager pm;

	private static final Logger logger = LogManager.getLogger(DecryptedResult.class);

	// GNetから連携される権限（0:担当者権限、1:損業権限）
	private String Authflag;
	public  String ClaimNo;
	private String CreateDate;

	public DecryptedResult(String Authflag, String ClaimNo, String CreateDate) {
		// 損業権限フラグ
		this.Authflag = Authflag;
		// 受付番号
		this.ClaimNo = ClaimNo;
		// URL生成時刻
		this.CreateDate = CreateDate;
	}

	public boolean isAuthority() {
		if( this.Authflag.equals("1")) {
			return true;
		}
		return false;
	}

	// 現在時刻 - URL生成時刻 <= 10であるかどうかチェック
	public boolean isCorrectDate() throws AuthorizationFailedException {
		// TODO: Arquillianを利用したテストが出来るようになればInjectするように変更
		PropertyManager pm = new PropertyManager();

		Instant createDate = Instant.parse(this.CreateDate);
		Instant currentDate = Instant.now();
		if (createDate.plusSeconds(10).compareTo(currentDate) < 0) {
//			logger.error(pm.get("E003"),createDate, currentDate);
			// TODO: プレースホルダーをセットすると無駄に複雑になる。要相談。
			throw new AuthorizationFailedException(pm.get("E003"));
		};
		return true;
	}

	public AuthResult createAuthResult (String userId) {
		return new AuthResult(userId, this.isAuthority(), this.ClaimNo);
	}

}
