package jp.co.tokiomarine_nichido.services.db;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import jp.co.tokiomarine_nichido.models.Feedback;

/**
 * FeedbackDAOTest
 *
 * @author SKK231099 李
 *
 */
//@RunWith(Arquillian.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FeedbackDAOTest extends DataServiceTest {

    final private String fraudScoreId = "00000000001";

    private static final Logger logger = LogManager.getLogger();

    /**
     * フィードバック保存テスト
     * @throws Exception
     */
    @Test
    public void stage1_updateFeedbackTest() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        Feedback feedback = new Feedback();
        feedback.setFraudScoreId(fraudScoreId);
        feedback.setIsCorrect(true);
        feedback.setComment("TEST Comment");
        Feedback feedback2 = getFeedback(feedback.getFraudScoreId());
        if (feedback2 != null) {
            String sql = super.pm.getSql("FeedbackDao.updateFeedbackComment");
            param.put("fraudScoreId", feedback2.getFraudScoreId());
            param.put("isCorrect", false);
            param.put("comment", "TEST UpdateComment");
            Feedback fb = (Feedback) super.updateObject(feedback2, sql, param);
            logger.info("FeedbackTest updateFeedback fraudScoreId=" + fb.getFraudScoreId() + " is OK");
        } else {
            Feedback fb = (Feedback) super.insertObject(feedback);
            logger.info("FeedbackTest insertFeedback fraudScoreId=" + fb.getFraudScoreId() + " is OK");

            String sql = super.pm.getSql("FeedbackDao.updateFeedbackComment");
            param.put("fraudScoreId", fb.getFraudScoreId());
            param.put("isCorrect", false);
            param.put("comment", "TEST UpdateComment");
            Feedback fb2 = (Feedback) super.updateObject(fb, sql, param);
            logger.info("FeedbackTest updateFeedback fraudScoreId=" + fb2.getFraudScoreId() + " is OK");
        }
    }
    private Feedback getFeedback(String fsId) throws Exception {
        logger.info("fraudScoreId=" + fsId);
        return super.getObject(Feedback.class, fsId);
    }

    /**
     * フィードバック取得テスト
     * @throws Exception
     */
    @Test
    public void stage2_getFeedbackTest() throws Exception {
        logger.info("getFeedbackTest");
        Feedback feedback = new Feedback();
        feedback.setFraudScoreId(fraudScoreId);
        Feedback fb = super.getObject(Feedback.class, feedback.getFraudScoreId());
        logger.info("FeedbackTest getFeedback fraudScoreId=" + fb.getFraudScoreId() + " is OK");
    }

    /**
     * フィードバック削除テスト
     * @throws Exception
     */
    @Test
    public void stage3_removeFeedbackTestData() throws Exception {
        logger.info("removeFeedbackTestData");
        Feedback feedback = super.getObject(Feedback.class, fraudScoreId);
        super.deleteObject(feedback);
        logger.info("FeedbackTest removeFeedbackTestData fraudScoreId=" + feedback.getFraudScoreId() + " is OK");
    }
}