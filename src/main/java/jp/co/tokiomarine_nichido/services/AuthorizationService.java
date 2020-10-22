package jp.co.tokiomarine_nichido.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import jp.co.tokiomarine_nichido.models.AuthorizationResult;
import jp.co.tokiomarine_nichido.models.DecryptedResult;
import jp.co.tokiomarine_nichido.models.User;
import jp.co.tokiomarine_nichido.util.PropertyManager;

/**
 * 認可処理を行うクラス。
 *
 * @author SKK231527 植木宥登
 *
 */
@ApplicationScoped
public class AuthorizationService {

	@Inject
	private DecryptionService decryptionService;
	@Inject
	private PropertyManager pm;
	private static final Logger logger = LogManager.getLogger(AuthorizationService.class);

	/**
	 * @param encryptedParam
	 * @param userId
	 * @return 認可結果
	 * @throws Exception
	 */
	public AuthorizationResult authorize(String encryptedParam, String userId, HttpServletRequest request)
			throws Exception {

		// 復号処理
		String decryptedString = decryptionService.decrypt(encryptedParam, userId);

		// 復号結果（JSON）をDecryptedResultオブジェクトにマッピング
		Gson gson = new Gson();
		DecryptedResult decryptedResult = gson.fromJson(decryptedString, DecryptedResult.class);

		// URL生成時刻の比較
		 decryptedResult.isCorrectDate();

		// UserSessionログインユーザ情報にユーザ情報をセット
		User user = new User(userId, decryptedResult.isAuthority());
		HttpSession session = request.getSession();
		session.setAttribute("user", user);

		// 認可結果を返す
		AuthorizationResult authorizationResult = decryptedResult.createAuthorizationResult(userId);

		// INFOログ出力
		logger.info(pm.getLogMessage("I001"), userId);

		return authorizationResult;

	}

}
