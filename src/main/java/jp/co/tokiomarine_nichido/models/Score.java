package jp.co.tokiomarine_nichido.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.tokiomarine_nichido.util.DateUtil;
import lombok.Data;

/**
 * 推論結果Score
 *
 * @modify SKK231099 李
 */
@Data
public class Score {

    private String fraudScoreId;
    private Integer score;
    private Timestamp createDate;
    private String claimId;

    private Claim claim;
    private List<Reason> reasons;
    private Feedback feedback;

    private DateUtil dateUtil;

    public Score() {
        claim = new Claim();
        reasons = new ArrayList<Reason>();
        feedback = new Feedback();

    }

    public Score(String fraudScoreId, Integer score, Timestamp createDate, String claimId, Claim claim) {
        this.fraudScoreId = fraudScoreId;
        this.score = score;
        this.createDate = createDate;
        this.claimId = claimId;
        this.claim = claim;
        reasons = new ArrayList<Reason>();
        feedback = new Feedback();
    }

    public Score(Score obj) {
        this.fraudScoreId = obj.fraudScoreId;
        this.score = obj.score;
        this.createDate = obj.createDate;
        this.claimId = obj.claimId;
        this.claim = obj.claim;
        reasons = new ArrayList<Reason>();
        feedback = new Feedback();
    }

    public Score(Map<String, Object> obj) {
        String fraudScoreId = String.valueOf(obj.get("fraudScoreId"));
        String scoreStr = String.valueOf(obj.get("score"));
        String createDate = String.valueOf(obj.get("createDate"));
        String claimId = String.valueOf(obj.get("claimId"));
        @SuppressWarnings("unchecked")
        Map<String, Object> claimMap = (Map<String, Object>) obj.get("claim");
        Claim claim = new Claim(claimMap);
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> reasonList = (List<Map<String, Object>>) obj.get("reasons");
        List<Reason> reasons = new ArrayList<Reason>();
        for (Map<String, Object> reasonMap : reasonList) {
            reasons.add(new Reason(reasonMap));
        }

        this.fraudScoreId = fraudScoreId;
        this.score = (int) Math.round(Double.valueOf(scoreStr));
        dateUtil = new DateUtil();
        this.createDate = dateUtil.toTimestamp(createDate);
        this.claimId = claimId;
        this.claim = claim;
        this.reasons = reasons;
    }

}
