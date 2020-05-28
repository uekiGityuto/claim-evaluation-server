package jp.co.tokiomarine_nichido.services.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.tokiomarine_nichido.models.Feedback;

/**
 * FeedbackService
 *
 * @author SKK231099 李
 *
 */
public class FeedbackDAO extends DataService {

    public List<Feedback> getFeedbacks(String fraudScoreIds) throws Exception {
        String sql_feedback = "from Feedback i where i.fraud_score_id = :fraud_score_id";
        Map<String, String> params = new HashMap<String, String>();
        params.put("claimId", fraudScoreIds);
        return super.getListByQuery(Feedback.class, sql_feedback, params);
    }

    public Feedback getFeedback(String fraudScoreId) throws Exception {
        return super.getObject(Feedback.class, fraudScoreId);
    }

    public Feedback updateFeedback(Feedback feedback) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        if (getFeedback(feedback.getFraudScoreId()) != null) {
            StringBuilder sql = new StringBuilder();
            sql.append("update " + feedback.getClass().getName() + " set ");
            if (feedback.getComment() == null) {
                sql.append("isCorrect = :isCorrect");
                param.put("isCorrect", feedback.getIsCorrect());
            } else {
                sql.append("comment = :comment, isCorrect = :isCorrect");
                param.put("isCorrect", feedback.getIsCorrect());
                param.put("comment", feedback.getComment());
            }
            sql.append(", updateDate = :updateDate where fraudScoreId = :fraudScoreId");
            param.put("fraudScoreId", feedback.getFraudScoreId());
            return (Feedback) super.updateObject(feedback, sql.toString(), param);
        } else {
            return (Feedback) super.insertObject(feedback);
        }
    }
}
