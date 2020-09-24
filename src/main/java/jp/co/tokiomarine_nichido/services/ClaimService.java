package jp.co.tokiomarine_nichido.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.google.gson.Gson;

import jp.co.tokiomarine_nichido.models.TargetClaim;
import jp.co.tokiomarine_nichido.models.TargetClaims;
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
	 * @return String
	 * @throws Exception
	 */
	public String getClaimList(TargetClaims bodyObj) throws Exception {
		String path = pm.get("api.path.cliams");

		Gson gson = new Gson();
		String bodyStr = gson.toJson(bodyObj);

		String result = client.inquire(path, bodyStr);
		return result;
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

		String path = pm.get("api.path.scores");

		Gson gson = new Gson();
		String bodyStr = gson.toJson(bodyObj);

		String result = client.inquire(path, bodyStr);

		Scores scores = gson.fromJson(result, Scores.class);
		System.out.println("scores:" + gson.toJson(scores));
		return scores;
	}
}
