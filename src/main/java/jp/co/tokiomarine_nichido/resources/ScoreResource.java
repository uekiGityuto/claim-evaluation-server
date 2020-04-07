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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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

@Path("score")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ScoreResource {
	private ScoreService ss = null;
	private Gson gson = null;
	private ObjectMapper om = null;

	public ScoreResource() {
		ss = new ScoreService();
		gson = new Gson();
		om = new ObjectMapper(); // gsonからjson変換時、timestampのmillisecondが消失される現象対応用
	}

	@GET
	@Path("/list")
	public String getScoreList() throws JsonProcessingException {
		Map<String, List<Score>> map = new HashMap<String, List<Score>>();
		List<Score> scoreList = ss.getScoreList();
		map.put("scoreList", scoreList);
		return om.writeValueAsString(map);
	}

	@GET
	@Path("/detail")
	public String getScore(@QueryParam("claimId") String claimId) throws JsonProcessingException {
		Map<String, Score> map = new HashMap<String, Score>();
		Score score = ss.getScore(claimId);
		map.put("score", score);
		return om.writeValueAsString(map);
	}

	@POST
	@Path("/updateFeedbackIsCorrect")
	public String updateFeedbackIsCorrect(@QueryParam("feedback") String json)
			throws JsonMappingException, JsonProcessingException {
		Feedback feedback = gson.fromJson(json, Feedback.class);
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		Boolean result = ss.updateFeedbackIsCorrect(feedback);
		map.put("update", result);
		return om.writeValueAsString(map);
	}

	@POST
	@Path("/updateFeedbackComment")
	public String updateFeedbackComment(@QueryParam("feedback") String json)
			throws JsonMappingException, JsonProcessingException {
		Feedback feedback = gson.fromJson(json, Feedback.class);
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		Boolean result = ss.updateFeedbackComment(feedback);
		map.put("update", result);
		return om.writeValueAsString(map);
	}

	@POST
	@Path("/updateComment")
//	public Response updateComment(@QueryParam("comment") String json)
	public String updateComment(@QueryParam("comment") String json)
			throws JsonMappingException, JsonProcessingException {
//		Status status = null;
		Comment comment = om.readValue(json, Comment.class);
		Map<String, Object> map = new HashMap<String, Object>();
		Boolean result = ss.updateComment(comment);
		if (result) {
			map.put("update", comment);
//			status = Response.Status.OK;
		} else {
			map.put("update", false);
//			status = Response.Status.;
		}

//		return Response.status(status)
//				       .entity(map);
		return om.writeValueAsString(map);
	}

//	@GET
//	@Path("/test")
//	public String getTest() {
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("test", "value");
//		map.put("key", "val2!!!!!");
//		return om.writeValueAsString(map);
//	}
}
