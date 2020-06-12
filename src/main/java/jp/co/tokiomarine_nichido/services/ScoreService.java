package jp.co.tokiomarine_nichido.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Produces;

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
@Produces
public class ScoreService {
    @Inject
    private RESTAPIDAO restapiDao;

    @Inject
    private CommentDAO cd;

    @Inject
    private FeedbackDAO fd;

    public ScoreService() {
    }

    /**
     * 一覧画面リストデータ取得
     * @return List<Score>
     * @throws Exception
     */
    public List<Score> findAllByGroup() throws Exception {
        List<Score> scores = new ArrayList<Score>();
        List<Map<String, Object>> list = restapiDao.findAll();
        Map<String, Score> map = new HashMap<String, Score>();
        list.forEach(obj -> {
            Score score = new Score(obj);
            if (map.containsKey(score.getClaimId())) {
                if (map.get(score.getClaimId()).getFraudScoreId().compareTo(score.getFraudScoreId()) < 0) {
                    map.replace(score.getClaimId(), score);
                }
            } else {
                map.put(score.getClaimId(), score);
            }
        });
        map.forEach((key, score) -> {
            scores.add(score);
        });
        return scores;
    }

    /**
     * Get Score List by ClaimID
     * @param claimId
     * @return List<Score>
     * @throws Exception
     */
    public List<Score> findListByClaimId(String claimId) throws Exception {
        return (List<Score>) restapiDao.findByRelation("claim.claimId", claimId, Score.class);
    }

    /**
     * 詳細画面データ取得
     * @param claimId
     * @return Score
     * @throws Exception
     */
    public Score findById(String claimId) throws Exception {
        // 複数のScoreをもらう場合
        Score score = null;
        List<Map<String, Object>> objList = restapiDao.findById("claim.claimId", claimId);
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
            score.setHistory(scores.subList(1, scores.size()));
        }
        return score;
    }

    /**
     * Feedback insert, update
     * @param feedback
     * @return Feedback
     * @throws Exception
     */
    public Feedback updateFeedback(Feedback feedback) throws Exception {
        return fd.updateFeedback(feedback);
    }

    /**
     * Insert/Update Comment
     * @param comment
     * @return Comment
     * @throws Exception
     */
    public Comment updateComment(Comment comment) throws Exception {
        return cd.updateComment(comment);
    }

    /**
     * Remove Comment
     * @param comment
     * @return Integer
     * @throws Exception
     */
    public Integer removeComment(Comment comment) throws Exception {
        return cd.removeComment(comment);
    }
}
