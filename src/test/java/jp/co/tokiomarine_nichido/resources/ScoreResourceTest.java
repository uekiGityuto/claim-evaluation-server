package jp.co.tokiomarine_nichido.resources;

import jp.co.tokiomarine_nichido.models.Comment;
import jp.co.tokiomarine_nichido.models.Feedback;
import jp.co.tokiomarine_nichido.services.ScoreService;

/**
 * TEST for Rest API Request
 *
 * @author SKK231099 李
 *
 */
//@RunWith(Arquillian.class)
public class ScoreResourceTest {

    private String claimId;
    private String fraudScoreId;
    private ScoreService ss;

    public ScoreResourceTest() throws Exception {
        claimId = "00000865432";
        fraudScoreId = "00000000001";
        ss = new ScoreService();
    }


//    @Test
    public void updateFeedbackTest() throws Exception {
        System.out.println("updateFeedback");
        Feedback feedback = new Feedback();
        feedback.setFraudScoreId(fraudScoreId);
        feedback.setComment("TEST Feedback Comment");
        feedback.setIsCorrect(true);
    }

//    @Test
    public void updateCommentTest() throws Exception {
        System.out.println("updateComment");
        Comment comment = new Comment();
        comment.setClaimId(claimId);
        comment.setComment("TEST Comment");
        comment.setUserId("99999999999");
        comment.setUserName("TEST User Name");

    }

//    @Test
    public void getScoresTest() throws Exception {
        System.out.println("■■■■■■■ getScores");
        ss.findAllByGroup();

    }

//    @Test
    public void getScoreTest() throws Exception {
        System.out.println("getScore");

    }

//    @Test
    public void removeCommentTest() throws Exception {
        System.out.println("removeComment");
        Comment comment = new Comment();
        comment.setClaimId(claimId);

    }
}
