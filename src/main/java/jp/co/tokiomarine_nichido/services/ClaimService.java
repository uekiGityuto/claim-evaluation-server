package jp.co.tokiomarine_nichido.services;

import java.text.MessageFormat;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import jp.co.tokiomarine_nichido.models.TargetClaim;
import jp.co.tokiomarine_nichido.models.claim_list.ClaimList;
import jp.co.tokiomarine_nichido.models.scores.Scores;
import jp.co.tokiomarine_nichido.models.target_claim_list.TargetClaimList;
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
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
		String bodyStr = gson.toJson(bodyObj);

		// REST APIに事案一覧をリクエスト
		String result = client.post(path, bodyStr);

		// 受信結果をオブジェクトにマッピング
		Scores scores;
		try {
			scores = gson.fromJson(result, Scores.class);
		} catch (JsonSyntaxException | NumberFormatException e) {
			throw new WebApplicationException(
					MessageFormat.format(pm.getLogMessage("E005"), bodyStr, result), e);
		}

		return scores;
	}

	/**
	 * 一覧画面リストデータ取得
	 *
	 * @return IF15事案一覧照会APIから取得した事案一覧
	 * @throws Exception
	 */
	public ClaimList getClaimList(TargetClaimList bodyObj) throws Exception {

		// REST APIにリクエストするための情報をセット
		String path = pm.get("api.path.cliams");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
		String bodyStr = gson.toJson(bodyObj);

		// REST APIにスコア詳細をリクエスト
		String result = client.post(path, bodyStr);

		// 受信結果をオブジェクトにマッピング
		ClaimList claims;
		try {
			claims = gson.fromJson(result, ClaimList.class);
		} catch (JsonSyntaxException | NumberFormatException e) {
			throw new WebApplicationException(
					MessageFormat.format(pm.getLogMessage("E006"), bodyStr, result), e);
		}

		return claims;

	}

}
