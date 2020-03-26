package jp.co.tokiomarine_nichido.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.container.AsyncResponse;
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
import rx.Observable;

/**
 * ScoreService
 * @author SKK231099 李
 *
 */
public class ScoreService {
	private ObservableClientService ob = null;
	private String gnetApiUri = "";
	private Gson gson = null;
	private CommentService cs = null;
	private FeedbackService fs = null;

	public ScoreService() {
		ob = new ObservableClientService();
		PropertyManager pm = new PropertyManager();
		gnetApiUri = pm.get("url_gnetapi");
		gson = new Gson();
		cs = new CommentService();
		fs = new FeedbackService();
	}

	public void getScoreList(AsyncResponse ar) {
		Map<String, List<Score>> map = new HashMap<String, List<Score>>();
		Map<String, String> errMsgList = new HashMap<String, String>();

		Observable<Response> OR = ob.rxCient("get", gnetApiUri, new MultivaluedHashMap<String, Object>());
		if (OR != null) {
			OR.subscribe(rp -> {
				try {
					@SuppressWarnings("unused")
					String claimId;
					String fraudScoreId;
					StringBuilder claimIds = new StringBuilder();
					StringBuilder fraudScoreIds = new StringBuilder();
					List<Score> scoreList = new ArrayList<Score>();
					List<Comment> commentList = new ArrayList<Comment>();
					List<Feedback> feedbackList = new ArrayList<Feedback>();
					String json = rp.readEntity(String.class);
					@SuppressWarnings("unchecked")
					List<Map<String, Object>> objList = (List<Map<String, Object>>) gson.fromJson(json, ArrayList.class);

					for (Map<String, Object> obj : objList) {
						Score score =  new Score(obj);
						scoreList.add(score);
						claimIds.append("'" + score.getClaimId() + "',");
						fraudScoreIds.append("'" + score.getFraudScoreId() + "',");
					}
					if (claimIds.length() > 0) {
						claimIds = new StringBuilder(claimIds.substring(0, claimIds.length() - 1));
						commentList = cs.getComments(String.valueOf(claimIds));
					}
					if (fraudScoreIds.length() > 0) {
						fraudScoreIds = new StringBuilder(fraudScoreIds.substring(0, fraudScoreIds.length() - 1));
						feedbackList = fs.getFeedbacks(String.valueOf(fraudScoreIds));
					}

					for (Score score : scoreList) {
						claimId = score.getClaimId();
						fraudScoreId = score.getFraudScoreId();
						Claim claim = score.getClaim();
						for (Comment comment : commentList) {
							if (comment.getClaimId().equals(claim.getClaimId())) {
								claim.addComment(comment);
							}
						}
						for (Feedback feedback : feedbackList) {
							if (fraudScoreId.equals(feedback.getFraudScoreId())) {
								score.setFeedback(feedback);
								break;
							}
						}
					}

					map.put("scoreList", scoreList);
					ar.resume(map);

				} catch(Exception e) {
					errMsgList.put("status(" + rp.getStatus() + "): ", String.valueOf(rp.getStatusInfo()));
				}

			}, throwable -> {
				errMsgList.put("1", "Please Check method and url");
			});
		} else {
			if (errMsgList.size() == 0) {
				errMsgList.put("0", "Please Check method and url");
			}
		}
	}

	public void getScore(AsyncResponse ar, String claimId) {
		Map<String, String> errMsgList = new HashMap<String, String>();
		MultivaluedHashMap<String, Object> param = new MultivaluedHashMap<String, Object>();
		param.add("claimId", claimId);
		Observable<Response> OR = ob.rxCient("get", gnetApiUri, param);
		if (OR != null) {
			OR.subscribe(rp -> {
				try {
					String json = rp.readEntity(String.class);
					Score score = (Score) gson.fromJson(json, Score.class);

					List<Comment> commentList = cs.getComments("'" + score.getClaimId() + "'");
					score.getClaim().setCommentList(commentList);
					Feedback feedback = fs.getFeedback(score.getFraudScoreId());
					score.setFeedback(feedback);

					Map<String, Score> map = new HashMap<String, Score>();
					map.put("score", score);
					ar.resume(map);

				} catch(Exception e) {
					errMsgList.put("status(" + rp.getStatus() + "): ", String.valueOf(rp.getStatusInfo()));
				}

			}, throwable -> {
				errMsgList.put("1", "Please Check method and url");
			});
		} else {
			if (errMsgList.size() == 0) {
				errMsgList.put("0", "Please Check method and url");
			}
		}
	}
}
