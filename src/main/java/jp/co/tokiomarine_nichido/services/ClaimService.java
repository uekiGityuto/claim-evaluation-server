package jp.co.tokiomarine_nichido.services;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.google.gson.Gson;

import jp.co.tokiomarine_nichido.models.Score;
import jp.co.tokiomarine_nichido.models.ScoresReqBody;
import jp.co.tokiomarine_nichido.models.User;

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

	/**
	 * 一覧画面リストデータ取得
	 *
	 * @return List<Score>
	 * @throws Exception
	 */
	public List<Score> findAllByGroup() throws Exception {
		List<Score> scores = new ArrayList<Score>();
		return scores;
	}

	/**
	 * 詳細画面データ取得
	 *
	 * @param claimId
	 * @return Score
	 * @throws Exception
	 */
	public String findByClaimNumber(String claimNumber, User user) throws Exception {

		String path = "/prd/inqiry";
		ScoresReqBody bodyObj = new ScoresReqBody(user.getUserId(), claimNumber);
		Gson gson = new Gson();
		String bodyStr = gson.toJson(bodyObj);

		String inqureResult = client.inquireScores(path, bodyStr);
		return inqureResult;
	}
}
