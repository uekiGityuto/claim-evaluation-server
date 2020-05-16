package jp.co.tokiomarine_nichido.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import jp.co.tokiomarine_nichido.models.Comment;
import jp.co.tokiomarine_nichido.models.Feedback;
import jp.co.tokiomarine_nichido.models.Score;
import jp.co.tokiomarine_nichido.services.db.CommentDAO;
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
	private String fraudScoreURL = "";

	@Inject
	private PropertyManager pm;
	@Inject
	private RESTAPIDAO cService;

	@Inject
	private CommentDAO cs;
	@Inject
	private FeedbackService fs;

	public ScoreService() {
	}

	@PostConstruct
	public void init() {
		fraudScoreURL = pm.get("url.fraudScore");
	}

	public List<Score> findAll() {
		return cService.findAll(new GenericType<List<Score>>() {
		});
	}

	public List<Score> findByClaimId(String claimId) {
		List<Score> scores = cService.findByRelation("claim.claimId", claimId, new GenericType<List<Score>>() {
		});

		// 不正請求DBからCommentとFeedbackを結合
		List<Comment> comments = cs.getComments(claimId);
		for (Score score : scores) {
			score.claim.setCommentList(comments);
		}

		return scores;
	}

	public List<Score> getScoreList() {
		// TODO:【李】接続エラーのときに空リストを返すのではなく、Exceptionを投げてDefaultExceptionMapperでエラーのHTTPリスポンスを返すようにする。
		Map<String, String> errMsgList = new HashMap<String, String>();

		List<Score> scoreList = null;
		Response rs = cService.client("get", fraudScoreURL, new HashMap<String, Object>());
		if (rs != null) {
			scoreList = rs.readEntity(new GenericType<List<Score>>() {
			});
		} else {
			if (errMsgList.size() == 0) {
				errMsgList.put("0", "Please Check method and url");
			}
		}

		return scoreList;
	}

	public Boolean updateFeedbackIsCorrect(Feedback feedback) {
		Boolean result = fs.updateFeedbackIsCorrect(feedback);
		return result;
	}

	public Boolean updateFeedbackComment(Feedback feedback) {
		Boolean result = fs.updateFeedbackComment(feedback);
		return result;
	}

	public Comment updateComment(Comment comment) {
		return cs.createOrUpdate(comment);
	}
}
