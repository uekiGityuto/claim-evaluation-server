package jp.co.tokiomarine_nichido.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
	// TODO: 【李】【中山】millisend問題は別途調査
	// private ObjectMapper om = new ObjectMapper(); //
	// gsonからjson変換時、timestampのmillisecondが消失される現象対応用

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Score> getScoreList() {
		return ss.findAll();
	}

	@GET
	@Path("/{claimId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Score getScore(@PathParam("claimId") String claimId) {
		return ss.findById(claimId);
	}

	@POST
	@Path("/{claimId}/feedback")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean updateFeedbackIsCorrect(Feedback feedback) {
		return ss.updateFeedbackIsCorrect(feedback);
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
	public boolean updateComment(Comment comment) {
		return ss.updateComment(comment);
	}
}
