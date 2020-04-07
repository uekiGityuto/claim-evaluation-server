package jp.co.tokiomarine_nichido.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import jp.co.tokiomarine_nichido.models.Comment;
import jp.co.tokiomarine_nichido.models.Feedback;
import jp.co.tokiomarine_nichido.models.Score;
import jp.co.tokiomarine_nichido.services.db.CommentService;
import jp.co.tokiomarine_nichido.services.db.FeedbackService;
import jp.co.tokiomarine_nichido.util.PropertyManager;

/**
 * ScoreService
 *
 * @author SKK231099 李
 *
 */
public class ScoreService {
	private ClientService cService = null;
	private String gnetApiUri = "";
	private Gson gson = null;
	private CommentService cs = null;
	private FeedbackService fs = null;

	public ScoreService() {
		cService = new ClientService();
		PropertyManager pm = new PropertyManager();
		gnetApiUri = pm.get("url_gnetapi");
		gson = new Gson();
		cs = new CommentService();
		fs = new FeedbackService();
	}

	public List<Score> getScoreList() {
		List<Score> scoreList = new ArrayList<Score>();
		Map<String, String> errMsgList = new HashMap<String, String>();

		Response rs = cService.client("get", gnetApiUri, new HashMap<String, Object>());
		if (rs != null) {
			try {
				String json = rs.readEntity(String.class);
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> objList = (List<Map<String, Object>>) gson.fromJson(json, ArrayList.class);

				for (Map<String, Object> obj : objList) {
					Score score = new Score(obj);
					scoreList.add(score);
				}
			} catch (Exception e) {
				errMsgList.put("status(" + rs.getStatus() + "): ", String.valueOf(rs.getStatusInfo()));
			}
		} else {
			if (errMsgList.size() == 0) {
				errMsgList.put("0", "Please Check method and url");
			}
		}

		return scoreList;
	}

	public Score getScore(String claimId) {
		Score score = null;
		Map<String, String> errMsgList = new HashMap<String, String>();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("claimId", claimId);
		Response rs = cService.client("get", gnetApiUri, param);
		if (rs != null) {
			try {
				String json = rs.readEntity(String.class);

				// TODO: remove this code for json-server after set GNetAPI
				// for json-server
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> list = (ArrayList<Map<String, Object>>) gson.fromJson(json, ArrayList.class);
				score = new Score(list.get(0));

//				score = gzon.fromJson(json, Score.class);

				List<Comment> commentList = cs.getComments(score.getClaimId());
				score.getClaim().setCommentList(commentList);
				Feedback feedback = fs.getFeedback(score.getFraudScoreId());
				score.setFeedback(feedback);

			} catch (Exception e) {
				errMsgList.put("status(" + rs.getStatus() + "): ", String.valueOf(rs.getStatusInfo()));
			}
		} else {
			if (errMsgList.size() == 0) {
				errMsgList.put("0", "Please Check method and url");
			}
		}
		return score;
	}

	public Comment getComment(Comment comment) {
		return cs.getComment(comment);
	}

	public Boolean updateFeedbackIsCorrect(Feedback feedback) {
		Boolean result = fs.updateFeedbackIsCorrect(feedback);
		return result;
	}

	public Boolean updateFeedbackComment(Feedback feedback) {
		Boolean result = fs.updateFeedbackComment(feedback);
		return result;
	}

	public Boolean updateComment(Comment comment) {
		Boolean result = cs.updateComment(comment);
		return result;
	}
}
