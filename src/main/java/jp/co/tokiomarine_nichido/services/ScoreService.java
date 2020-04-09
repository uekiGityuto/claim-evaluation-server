package jp.co.tokiomarine_nichido.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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
@ApplicationScoped
public class ScoreService {
	private String gnetApiUri = "";
	private Gson gson = new Gson();

	@Inject
	private PropertyManager pm;
	@Inject
	private ClientService cService;
	private CommentService cs = null;
	private FeedbackService fs = null;

	public ScoreService() {
	}

	@PostConstruct
	public void init() {
		gnetApiUri = pm.get("url.scores");
		cs = new CommentService();
		fs = new FeedbackService();
	}

	public List<Score> getScoreList() {
		// TODO:【李】接続エラーのときに空リストを返すのではなく、Exceptionを投げてDefaultExceptionMapperでエラーのHTTPリスポンスを返すようにする。
		Map<String, String> errMsgList = new HashMap<String, String>();

		List<Score> scoreList = null;
		Response rs = cService.client("get", gnetApiUri, new HashMap<String, Object>());
		if (rs != null) {
			scoreList = rs.readEntity(List.class);
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
				List<Map<String, Object>> list = gson.fromJson(json, ArrayList.class);
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
