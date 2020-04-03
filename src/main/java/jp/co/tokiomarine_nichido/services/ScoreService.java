package jp.co.tokiomarine_nichido.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import jp.co.tokiomarine_nichido.models.Claim;
import jp.co.tokiomarine_nichido.models.Comment;
import jp.co.tokiomarine_nichido.models.Feedback;
import jp.co.tokiomarine_nichido.models.Score;
import jp.co.tokiomarine_nichido.services.db.CommentService;
import jp.co.tokiomarine_nichido.services.db.FeedbackService;
import jp.co.tokiomarine_nichido.util.PropertyManager;

/**
 * ScoreService
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
				@SuppressWarnings("unused")
				String claimId;
				String fraudScoreId;
				StringBuilder fraudScoreIds = new StringBuilder();
//				StringBuilder claimIds = new StringBuilder();
//				List<Comment> commentList = new ArrayList<Comment>();
				List<Feedback> feedbackList = new ArrayList<Feedback>();
				String json = rs.readEntity(String.class);
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> objList = (List<Map<String, Object>>) gson.fromJson(json, ArrayList.class);

				for (Map<String, Object> obj : objList) {
					Score score =  new Score(obj);
					scoreList.add(score);
					fraudScoreIds.append("'" + score.getFraudScoreId() + "',");
//					claimIds.append("'" + score.getClaimId() + "',");
				}
//				if (claimIds.length() > 0) {
//					claimIds = new StringBuilder(claimIds.substring(0, claimIds.length() - 1));
//					commentList = cs.getComments(String.valueOf(claimIds));
//				}
				if (fraudScoreIds.length() > 0) {
					fraudScoreIds = new StringBuilder(fraudScoreIds.substring(0, fraudScoreIds.length() - 1));
					feedbackList = fs.getFeedbacks(String.valueOf(fraudScoreIds));
				}

				for (Score score : scoreList) {
					fraudScoreId = score.getFraudScoreId();
//					claimId = score.getClaimId();
//					Claim claim = score.getClaim();
//					for (Comment comment : commentList) {
//						if (comment.getClaimId().equals(claim.getClaimId())) {
//							claim.addComment(comment);
//						}
//					}
					for (Feedback feedback : feedbackList) {
						if (fraudScoreId.equals(feedback.getFraudScoreId())) {
							score.setFeedback(feedback);
							break;
						}
					}
				}
			} catch(Exception e) {
				errMsgList.put("status(" + rs.getStatus() + "): ", String.valueOf(rs.getStatusInfo()));
			}
		} else {
			if (errMsgList.size() == 0) {
				errMsgList.put("0", "Please Check method and url");
			}
		}

		return scoreList;
	}

//	public void getScore(AsyncResponse ar, String claimId) {
	public Score getScore(String claimId) {
		Score score = null;
		Map<String, String> errMsgList = new HashMap<String, String>();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("claimId", claimId);
		Response rs = cService.client("get", gnetApiUri, param);
		if (rs != null) {
			try {
				String json = rs.readEntity(String.class);

				// for json-server
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> list = (ArrayList<Map<String, Object>>) gson.fromJson(json, ArrayList.class);
				score = new Score(list.get(0));

//				score = gzon.fromJson(json, Score.class);

				List<Comment> commentList = cs.getComments(score.getClaimId());
				score.getClaim().setCommentList(commentList);
				Feedback feedback = fs.getFeedback(score.getFraudScoreId());
				score.setFeedback(feedback);


			} catch(Exception e) {
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

	public Boolean updateFeedback(Feedback feedback) {
		Boolean result = fs.updateFeedback(feedback);
		return result;
	}

	public Boolean updateComment(Comment comment) {
		Boolean result = cs.updateComment(comment);
		return result;
	}
}
