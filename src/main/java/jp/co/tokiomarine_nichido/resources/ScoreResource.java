package jp.co.tokiomarine_nichido.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import jp.co.tokiomarine_nichido.models.Comment;
import jp.co.tokiomarine_nichido.models.Feedback;
import jp.co.tokiomarine_nichido.models.Score;
import jp.co.tokiomarine_nichido.services.ScoreService;

/**
 * for Angular 一覧画面、詳細画面 Data
 *
 * @author SKK231099 李
 *
 */

@Path("scores")
public class ScoreResource {
	@Inject
	private ScoreService ss;
	private Gson gson = new Gson();
	private ObjectMapper om = new ObjectMapper(); // gsonからjson変換時、timestampのmillisecondが消失される現象対応用

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getScoreList() throws JsonProcessingException {
		Map<String, List<Score>> map = new HashMap<String, List<Score>>();
		List<Score> scoreList = ss.getScoreList();
		map.put("scoreList", scoreList);
		return om.writeValueAsString(map);
	}

	@GET
	@Path("/{claimId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getScore(@PathParam("claimId") String claimId) throws JsonProcessingException {
		Map<String, Score> map = new HashMap<String, Score>();
		Score score = ss.getScore(claimId);
		map.put("score", score);
		return om.writeValueAsString(map);
	}

	@POST
	@Path("/{claimId}/feedback")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateFeedbackIsCorrect(@QueryParam("feedback") String json)
			throws JsonMappingException, JsonProcessingException {
		Feedback feedback = gson.fromJson(json, Feedback.class);
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		Boolean result = ss.updateFeedbackIsCorrect(feedback);
		map.put("update", result);
		return om.writeValueAsString(map);
	}

	// TODO: 【李】POST /{claimId}/Feedbackと統合する。
//	@POST
//	@Path("/updateFeedbackComment")
//	public String updateFeedbackComment(@QueryParam("feedback") String json)
//			throws JsonMappingException, JsonProcessingException {
//		Feedback feedback = gson.fromJson(json, Feedback.class);
//		Map<String, Boolean> map = new HashMap<String, Boolean>();
//		Boolean result = ss.updateFeedbackComment(feedback);
//		map.put("update", result);
//		return om.writeValueAsString(map);
//	}

	@POST
	@Path("/{claimId}/comment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateComment(@QueryParam("comment") String json)
			throws JsonMappingException, JsonProcessingException {
		Comment comment = om.readValue(json, Comment.class);
		Map<String, Object> map = new HashMap<String, Object>();
		Boolean result = ss.updateComment(comment);
		if (result) {
			map.put("update", comment);
		} else {
			map.put("update", false);
		}

		return om.writeValueAsString(map);
	}
}
