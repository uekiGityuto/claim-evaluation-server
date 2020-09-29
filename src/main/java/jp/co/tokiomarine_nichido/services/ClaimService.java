package jp.co.tokiomarine_nichido.services;

import java.text.MessageFormat;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import jp.co.tokiomarine_nichido.exceptions.AuthorizationFailedException;
import jp.co.tokiomarine_nichido.models.TargetClaim;
import jp.co.tokiomarine_nichido.models.TargetClaimList;
import jp.co.tokiomarine_nichido.models.claim_list.ClaimList;
import jp.co.tokiomarine_nichido.models.scores.Scores;
import jp.co.tokiomarine_nichido.util.PropertyManager;

/**
 * RestApiClientを利用するクラス
 *
 * @author SKK231527 植木宥登
 *
 */
@ApplicationScoped
public class ClaimService {
	@Inject
	private RestApiClient client;
	@Inject
	private PropertyManager pm;

	/**
	 * 一覧画面リストデータ取得
	 *
	 * @return IF15事案一覧照会APIから取得した事案一覧
	 * @throws Exception
	 */
	public ClaimList getClaimList(TargetClaimList bodyObj) throws Exception {

		// REST APIにリクエストするための情報をセット
		String path = pm.get("api.path.cliams");
		Gson gson = new Gson();
		String bodyStr = gson.toJson(bodyObj);

		// REST APIにスコア詳細をリクエスト
		String result = client.post(path, bodyStr);

		// 受信結果をオブジェクトにマッピング
		ClaimList claims;
		try {
			claims = gson.fromJson(result, ClaimList.class);
			// TODO: catchする例外が十分かどうか確認
		} catch (JsonSyntaxException | NumberFormatException e) {
			// TODO: プレースホルダーにセットする情報が十分か要検討。eも渡す必要があるか確認。
			throw new AuthorizationFailedException(MessageFormat.format(pm.get("E005"), bodyStr,result));
		}

		// 受信結果のValidatation
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<ClaimList>> validationResult = validator.validate(claims);
		if (validationResult.size() != 0) {
			// TODO: プレースホルダーにセットする情報が十分か要検討。eも渡す必要があるか確認。
			throw new AuthorizationFailedException(MessageFormat.format(pm.get("E005"), bodyStr, result));
		}

		return claims;

	}

	/**
	 * 詳細画面データ取得
	 *
	 * @param claimNumber
	 * @param user
	 * @return IF08スコア照会APIから取得したスコア詳細
	 * @throws Exception
	 */
	public Scores getScores(TargetClaim bodyObj) throws Exception {

		// REST APIにリクエストするための情報をセット
		String path = pm.get("api.path.scores");
		Gson gson = new Gson();
		String bodyStr = gson.toJson(bodyObj);

		// REST APIに事案一覧をリクエスト
		String result = client.post(path, bodyStr);

		// 受信結果をオブジェクトにマッピング
		Scores scores;
		try {
			scores = gson.fromJson(result, Scores.class);
			// TODO: catchする例外が十分かどうか確認
		} catch (JsonSyntaxException | NumberFormatException e) {
			// TODO: プレースホルダーにセットする情報が十分か要検討。eも渡す必要があるか確認。
			throw new AuthorizationFailedException(MessageFormat.format(pm.get("E006"), bodyStr,result));
		}

		// 受信結果のValidatation
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Scores>> validationResult = validator.validate(scores);
		if (validationResult.size() != 0) {
			// TODO: プレースホルダーにセットする情報が十分か要検討。eも渡す必要があるか確認。
			throw new AuthorizationFailedException(MessageFormat.format(pm.get("E006"), bodyStr,result));
		}

		return scores;
	}
}
