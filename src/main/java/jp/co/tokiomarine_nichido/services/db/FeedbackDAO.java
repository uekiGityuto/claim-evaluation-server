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
        String sql_feedback = super.pm.getSql("FeedbackDao.selectFeedback");
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
