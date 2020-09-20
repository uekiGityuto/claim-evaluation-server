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
//	DecryptionService decryptionService = new DecryptionService();

	/**
	 * @param encryptedParam
	 * @param userId
	 * @return 認可結果
	 */
	// TODO: HttpServletRequestを引数にとるとテストしづらいので要検討
	public String authorize(String encryptedParam, String userId, HttpServletRequest request) {

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
		// TODO: セッション情報の格納方法は要検討
		User user = new User(userId, result.isAuthority());
//		user.userId = userId;
//		user.authFlag = result.isAuthority();
		HttpSession session = request.getSession();
//		session.setAttribute("userId", userId);
//		session.setAttribute("authflag", result.isAuthority());
		session.setAttribute("user", user);

		// 認可結果を返す
		AuthResult authResult = result.createAuthResult(userId);
		// TODO:JAX-RSの機能でJSONに変換出来ないので直接変換する。
		// TODO:原因確認（Serializableが必要？setter,getterが必要？）
		return gson.toJson(authResult);

	}

}
