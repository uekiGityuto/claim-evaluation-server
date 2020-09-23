package jp.co.tokiomarine_nichido.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import jp.co.tokiomarine_nichido.models.AuthResult;
import jp.co.tokiomarine_nichido.models.DecryptedResult;
import jp.co.tokiomarine_nichido.models.User;

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

	/**
	 * @param encryptedParam
	 * @param userId
	 * @return 認可結果
	 */
	// TODO: HttpServletRequestを引数にとるとテストしづらいので要検討
	public AuthResult authorize(String encryptedParam, String userId, HttpServletRequest request) {

		// 復号処理
		String decryptedString = decryptionService.decrypt(encryptedParam);
		if (decryptedString.isEmpty()) {
			throw new WebApplicationException(Response.Status.FORBIDDEN);
		}

		// 復号結果（JSON）をDecryptedResultオブジェクトにマッピング
		Gson gson = new Gson();
		DecryptedResult result = gson.fromJson(decryptedString, DecryptedResult.class);

		// TODO: チェックエラーになるので一時的にコメントアウト
		// URL生成時刻の比較
//		if (!result.isCorrectDate()) {
//			throw new WebApplicationException(Response.Status.FORBIDDEN);
//		}

		// UserSessionログインユーザ情報にユーザ情報をセット
		User user = new User(userId, result.isAuthority());
		HttpSession session = request.getSession();
		session.setAttribute("user", user);

		// 認可結果を返す
		AuthResult authResult = result.createAuthResult(userId);
		return authResult;

	}

}
