package jp.co.tokiomarine_nichido.services.db;

import java.util.ArrayList;
import java.util.List;

import jp.co.tokiomarine_nichido.models.Feedback;

/**
 * FeedbackService
 * @author SKK231099 李
 *
 */
public class FeedbackService extends DataService {

	public List<Feedback> getFeedbacks(String fraudScoreIds) {
		if (fraudScoreIds.length() > 0) {
			// get feedback by fraud_score_id
			String sql_feedback = "select i from Feedback i where fraud_score_id in (" + fraudScoreIds + ")";
			return (List<Feedback>) super.getList(sql_feedback, Feedback.class);
		}
		return new ArrayList<Feedback>();
	}

	public Feedback getFeedback(String fraudScoreId) {
		if (fraudScoreId.length() > 0) {
			return (Feedback) super.getObject(fraudScoreId, Feedback.class);
		}
		return new Feedback();
	}
}
