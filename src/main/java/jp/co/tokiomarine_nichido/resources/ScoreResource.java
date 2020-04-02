package jp.co.tokiomarine_nichido.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import jp.co.tokiomarine_nichido.models.Feedback;
import jp.co.tokiomarine_nichido.models.Comment;
import jp.co.tokiomarine_nichido.models.Score;
import jp.co.tokiomarine_nichido.services.ScoreService;

/**
 * for Angular 一覧画面、詳細画面  Data
 * @author SKK231099 李
 *
 */

@Path("score")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ScoreResource {
	private ScoreService ss = null;
	private Gson gson = null;

	public ScoreResource() {
		ss = new ScoreService();
		gson = new Gson();
	}

	@GET
	@Path("/list")
	public String getScoreList() {
		Map<String, List<Score>> map = new HashMap<String, List<Score>>();
		List<Score> scoreList = ss.getScoreList();
		map.put("scoreList", scoreList);
		return gson.toJson(map);
	}

	@GET
	@Path("/detail")
	public String getScore(@QueryParam("claimId") String claimId) {
		Map<String, Score> map = new HashMap<String, Score>();
		Score score = ss.getScore(claimId);
		map.put("score", score);
		return gson.toJson(map);
	}

	@POST
	@Path("/updateFeedback")
	public String updateFeedback(@QueryParam("feedback") String json) {
		Feedback feedback = gson.fromJson(json, Feedback.class);
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		Boolean result = ss.updateFeedback(feedback);
		map.put("update", result);
		return gson.toJson(map);
	}

	@POST
	@Path("/updateComment")
	public String updateComment(@QueryParam("comment") String json) {
		Comment comment = gson.fromJson(json, Comment.class);
		Map<String, Object> map = new HashMap<String, Object>();
		Boolean result = ss.updateComment(comment);
		if (result) {
			Comment cmt = ss.getComment(comment);
			map.put("update", cmt);
		} else {
			map.put("update", false);
		}
		return gson.toJson(map);
	}

//	@GET
//	@Path("/test")
//	public String getTest() {
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("test", "value");
//		map.put("key", "val2!!!!!");
//		return gson.toJson(map);
//	}
}
