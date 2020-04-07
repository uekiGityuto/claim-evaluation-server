package jp.co.tokiomarine_nichido.services.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Table;

import jp.co.tokiomarine_nichido.models.Feedback;

/**
 * FeedbackService
 *
 * @author SKK231099 李
 *
 */
public class FeedbackService extends DataService {

	private String className = null;
	private String tableName = null;

	public FeedbackService() {
		className = Feedback.class.getName();
		tableName = Feedback.class.getAnnotation(Table.class).name();
	}

	public String getClassName() {
		return className;
	}

	public String getTableName() {
		return tableName;
	}

	public List<Feedback> getFeedbacks(String fraudScoreIds) {
		List<Feedback> feedbacks = null;
		if (fraudScoreIds.length() > 0) {
			// get feedback by fraud_score_id
			String sql_feedback = "select i from Feedback i where fraud_score_id in (" + fraudScoreIds + ")";
			feedbacks = (List<Feedback>) super.getList(sql_feedback, Feedback.class);
		}
		if (feedbacks == null) {
			feedbacks = new ArrayList<Feedback>();
		}
		return feedbacks;
	}

	public Feedback getFeedback(String fraudScoreId) {
		Feedback feedback = null;
		if (fraudScoreId.length() > 0) {
			feedback = (Feedback) super.getObject(fraudScoreId, null, Feedback.class);
		}
		if (feedback == null) {
			feedback = new Feedback();
		}
		return feedback;
	}

	public Boolean updateFeedback(Feedback feedback) {
		Boolean result = super.updateObject(feedback, Feedback.class);
		return result;
	}

	public Boolean updateFeedbackIsCorrect(Feedback feedback) {
		String sql = "update " + this.className + " set isCorrect = :isCorrect where fraudScoreId = :fraudScoreId";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("fraudScoreId", feedback.getFraudScoreId());
		param.put("isCorrect", feedback.getIsCorrect());
		Boolean result = super.updateObject(sql, param);
		return result;
	}

	public Boolean updateFeedbackComment(Feedback feedback) {
		String sql = "update " + this.className + " set comment = :comment where fraudScoreId = :fraudScoreId";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("fraudScoreId", feedback.getFraudScoreId());
		param.put("comment", feedback.getComment());
		Boolean result = super.updateObject(sql, param);
		return result;
	}
}
