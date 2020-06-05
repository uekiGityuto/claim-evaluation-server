package jp.co.tokiomarine_nichido.services.db;

import java.util.HashMap;
import java.util.Map;

import jp.co.tokiomarine_nichido.models.Feedback;

/**
 * FeedbackService
 *
 * @author SKK231099 李
 *
 */
public class FeedbackDAO extends DataService {

    /**
     * Get Feedback by fraudScoreId
     * @param fraudScoreId
     * @return Feedback
     * @throws Exception
     */
    public Feedback getFeedback(String fraudScoreId) throws Exception {
        return super.getObject(Feedback.class, fraudScoreId);
    }

    /**
     * Update Feedback
     * @param feedback
     * @return Feedback
     * @throws Exception
     */
    public Feedback updateFeedback(Feedback feedback) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        if (getFeedback(feedback.getFraudScoreId()) != null) {
            String sql = null;
            if (feedback.getComment() == null) {
                sql = super.pm.getSql("FeedbackDao.updateFeedbackIsCorrect");
                param.put("isCorrect", feedback.getIsCorrect());
            } else {
                sql = super.pm.getSql("FeedbackDao.updateFeedbackComment");
                param.put("isCorrect", feedback.getIsCorrect());
                param.put("comment", feedback.getComment());
            }
            param.put("fraudScoreId", feedback.getFraudScoreId());
            return (Feedback) super.updateObject(feedback, sql, param);
        } else {
            return (Feedback) super.insertObject(feedback);
        }
    }
}
