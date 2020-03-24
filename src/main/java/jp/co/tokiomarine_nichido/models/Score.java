package jp.co.tokiomarine_nichido.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 推論結果Score
 *
 * @modify SKK231099 李　
 */
public class Score {

	private String fraudScoreId;
    private Integer score;
    private Timestamp creatDate;
	private String claimId;

	private Claim claim;
	private List<Reason> reasons;
	private Feedback feedback;

	public Score() {
		claim = new Claim();
		reasons = new ArrayList<Reason>();
		feedback = new Feedback();
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
	public Timestamp getCreatDate() {
		return creatDate;
	}
	public void setCreatDate(Timestamp creatDate) {
		this.creatDate = creatDate;
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
