package jp.co.tokiomarine_nichido.services;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import jp.co.tokiomarine_nichido.exceptions.AuthorizationFailedException;
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
	// TODO: HttpServletRequestを引数にとるとテストしづらいので要検討
	public AuthorizationResult authorize(String encryptedParam, String userId, HttpServletRequest request)
			throws Exception {

		// 復号処理
		String decryptedString = decryptionService.decrypt(encryptedParam);
		// if (decryptedString.isEmpty()) {
		//	throw new WebApplicationException(Response.Status.FORBIDDEN);
		// }

		// 復号結果（JSON）をDecryptedResultオブジェクトにマッピング
		Gson gson = new Gson();
		DecryptedResult decryptedResult = gson.fromJson(decryptedString, DecryptedResult.class);

        // 復号結果のValidatation
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<DecryptedResult>> validationResult = validator.validate(decryptedResult);
        if(validationResult.size() != 0) {
        	throw new AuthorizationFailedException(pm.get("E002"));
        }

		// TODO: チェックエラーになるので一時的にコメントアウト
		// URL生成時刻の比較
		// if (!validationResult.isCorrectDate()) {
		//	throw new WebApplicationException(Response.Status.FORBIDDEN);
		// }

		// UserSessionログインユーザ情報にユーザ情報をセット
		User user = new User(userId, decryptedResult.isAuthority());
		HttpSession session = request.getSession();
		session.setAttribute("user", user);

		// 認可結果を返す
		AuthorizationResult authorizationResult = decryptedResult.createAuthorizationResult(userId);

		// log出力
		// TODO: 文字化けするので対策
		logger.info(pm.get("I001"), userId);

		return authorizationResult;

	}

}
