package jp.co.tokiomarine_nichido.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.tokiomarine_nichido.util.DateUtil;

/**
 * 推論結果Score
 *
 * @modify SKK231099 李
 */
public class Score {

	private String fraudScoreId;
	private Integer score;
	private Timestamp createDate;
	private String claimId;

	private Claim claim;
	private List<Reason> reasons = new ArrayList<>();
	private Feedback feedback;

	private DateUtil dateUtil;

	public Score() {
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
			Reason reason = new Reason(reasonMap);
			reasons.add(reason);
		}

		this.fraudScoreId = fraudScoreId;
		this.score = (int) Math.round(Double.valueOf(scoreStr));
		dateUtil = new DateUtil();
		this.createDate = dateUtil.toTimestamp(createDate);
		this.claimId = claimId;
		this.claim = claim;
		this.reasons = reasons;
	}

	public String getFraudScoreId() {
		return fraudScoreId;
	}

	public void setFraudScoreId(String fraudScoreId) {
		this.fraudScoreId = fraudScoreId;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getClaimId() {
		return claimId;
	}

	public void setClaimId(String claimId) {
		this.claimId = claimId;
	}

	public Claim getClaim() {
		return claim;
	}

	public void setClaim(Claim claim) {
		this.claim = claim;
	}

	public List<Reason> getReasons() {
		return reasons;
	}

	public void setReasons(List<Reason> reasons) {
		this.reasons = reasons;
	}

	public Feedback getFeedback() {
		return feedback;
	}

	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}
}
