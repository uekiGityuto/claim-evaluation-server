package jp.co.tokiomarine_nichido.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.google.gson.Gson;

import jp.co.tokiomarine_nichido.models.Comment;
import jp.co.tokiomarine_nichido.models.Feedback;
import jp.co.tokiomarine_nichido.models.Score;
import jp.co.tokiomarine_nichido.services.db.CommentDAO;
import jp.co.tokiomarine_nichido.services.db.FeedbackDAO;

/**
 * ScoreService
 *
 * @author SKK231099 李
 *
 */
public class ScoreService {
    private Gson gson;
    @Inject
    private RESTAPIDAO restapiDao;

    @Inject
    private CommentDAO cd;

    @Inject
    private FeedbackDAO fd;

    public ScoreService() {
        gson = new Gson();
    }

    public List<Score> findAll() throws Exception {
        return restapiDao.findAll(Score.class);
    }

    public List<Score> findListByClaimId(String claimId) throws Exception {
        return restapiDao.findByRelation("claim.claimId", claimId, Score.class);
    }

    @SuppressWarnings("unchecked")
    public Score findById(String claimId) throws Exception {
        // 複数のScoreをもらう場合
        Score score = null;
        String json = restapiDao.findById("claim.claimId", claimId);
        List<Map<String, Object>> objList = (List<Map<String, Object>>) gson.fromJson(json, ArrayList.class);
        List<Score> scores = new ArrayList<Score>();
        for (Map<String, Object> obj : objList) {
            scores.add(new Score(obj));
        }
        if (scores.size() > 0) {
            if (scores.size() > 1) {
                scores.sort(Comparator.comparing(Score::getFraudScoreId).reversed());
            }
            score = scores.get(0);
            List<Comment> commentList = cd.getComments(score.getClaimId());
            score.getClaim().setCommentList(commentList);
            Feedback feedback = fd.getFeedback(score.getFraudScoreId());
            score.setFeedback(feedback);
        }
        return score;
    }

    public Feedback updateFeedback(Feedback feedback) throws Exception {
        return fd.updateFeedback(feedback);
    }

    public Comment updateComment(Comment comment) throws Exception {
        return cd.updateComment(comment);
    }
}
