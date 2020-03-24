package jp.co.tokiomarine_nichido.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

/**
 * 推論結果に対するフィードバック。
 *
 * @author SKK229873 中山真吾
 * @modifier SKK231099 李 : JPA連携
 */
@Entity
@Immutable
@Table(name="feedback",
       indexes= {@Index(name="idx_feedback", columnList= "fraud_score_id", unique=true)})
public class Feedback {

	@Id
	@Column(name="fraud_score_id", nullable = false)
	private String fraudScoreId;

	// 未評価の場合、null
	@Column(name="is_correct")
	private Boolean isCorrect;

	@Column(name="comment")
	private String comment;


	public String getFraudScoreId() {
		return fraudScoreId;
	}
	public void setFraudScoreId(String fraudScoreId) {
		this.fraudScoreId = fraudScoreId;
	}
	public Boolean getIsCorrect() {
		return isCorrect;
	}
	public void setIsCorrect(Boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

}
