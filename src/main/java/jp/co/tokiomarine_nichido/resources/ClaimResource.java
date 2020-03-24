package jp.co.tokiomarine_nichido.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;

import com.google.gson.Gson;

import jp.co.tokiomarine_nichido.models.Claim;
import jp.co.tokiomarine_nichido.models.Feedback;
import jp.co.tokiomarine_nichido.models.Result;
import jp.co.tokiomarine_nichido.models.Score;
import jp.co.tokiomarine_nichido.services.DataService;
import jp.co.tokiomarine_nichido.services.ObservableClientService;
import jp.co.tokiomarine_nichido.util.PropertyManager;

/**
 * for Angular 一覧画面、詳細画面  Data
 * @author SKK231099 李
 *
 */

@Path("claim")
public class ClaimResource {
	private DataService ds = null;
	private Gson gson = null;
	private ObservableClientService ob = null;
	String gnetApiUri = "";

	public ClaimResource() {
		ds = new DataService();
		gson = new Gson();
		ob = new ObservableClientService();
		PropertyManager pm = new PropertyManager();
		gnetApiUri = pm.get("url_gnetapi");
	}

//	@POST
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public String getClaimList() throws Exception {
		List<Score> scoreList = new ArrayList<Score>();
		StringBuilder claimIds = new StringBuilder();
		StringBuilder fraudScoreIds = new StringBuilder();
		String claimId;
		String fraudScoreId;

//		Result result = ob.rxCient("post", gnetApiUri, new MultivaluedHashMap<String, Object>());
		Result result = ob.rxCient("get", gnetApiUri, new MultivaluedHashMap<String, Object>());
		if (result.isSuccess()) {
			@SuppressWarnings("unchecked")
			List<Object> objList = (List<Object>) result.getData();
			for (Object obj : objList) {
				Score score = (Score) obj; // or make setScore(...)
				scoreList.add(score);
				claimIds.append("'" + score.getClaimId() + "'");
				fraudScoreIds.append("'" + score.getFraudScoreId() + "'");
			}

			List<Claim> claimList = new ArrayList<Claim>();
			List<Feedback> feedbackList = new ArrayList<Feedback>();
			if (claimIds.length() > 0) {
				// get comment by claim_id
				claimIds.substring(0, claimIds.length() - 1);
				String sql_claim = "select i from Comment i where claim_id in (" + claimIds + ")";
				claimList = ds.getList(sql_claim, Claim.class);
			}
			if (fraudScoreIds.length() > 0) {
				// get feedback by fraud_score_id
				fraudScoreIds.substring(0, fraudScoreIds.length() - 1);
				String sql_feedback = "select i from Feedback i where fraud_score_id in (" + fraudScoreIds + ")";
				feedbackList = ds.getList(sql_feedback, Feedback.class);
			}

			for (Score score : scoreList) {
				claimId = score.getClaimId();
				fraudScoreId = score.getFraudScoreId();
				for (Claim claim : claimList) {
					if (claimId.equals(claim.getClaimId())) {
						score.setClaim(claim);
					}
				}
				for (Feedback feedback : feedbackList) {
					if (fraudScoreId.equals(feedback.getFraudScoreId())) {
						score.setFeedback(feedback);
					}
				}
			}
		}

		Map<String, List<Score>> map = new HashMap<String, List<Score>>();
		map.put("scoreList", scoreList);
		return gson.toJson(map);
	}

//	@POST
	@GET
	@Path("/detail")
	@Produces(MediaType.APPLICATION_JSON)
	public String getClaim(@QueryParam("claimId") String claimId) {
		MultivaluedHashMap<String, Object> param = new MultivaluedHashMap<String, Object>();
		param.add("claimId", claimId);

//		Result result = ob.rxCient("post", gnetApiUri, param);
		Result result = ob.rxCient("get", gnetApiUri, param);
		Score score = new Score();
		if (result.isSuccess()) {
			score = (Score) result.getData();
			@SuppressWarnings("unused")
			Claim claim = ds.getObject(score.getClaimId(), Claim.class);
			@SuppressWarnings("unused")
			Feedback feedback = ds.getObject(score.getFraudScoreId(), Feedback.class);
		}

		Map<String, Score> map = new HashMap<String, Score>();
		map.put("score", score);
		return gson.toJson(map);
	}

//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public String getTest() {
//		Gson gson = new Gson();
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("test", "value");
//		map.put("key", "val2!!!!!");
//		return gson.toJson(map);
//	}
}
