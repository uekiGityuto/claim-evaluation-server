package jp.co.tokiomarine_nichido.services.db;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.DataException;

import jp.co.tokiomarine_nichido.models.Feedback;

/**
 * FeedbackService
 * @author SKK231099 李
 *
 */
public class FeedbackService extends DataService {

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
}
