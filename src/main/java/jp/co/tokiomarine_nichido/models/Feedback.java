package jp.co.tokiomarine_nichido.models;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 推論結果に対するフィードバック。
 *
 * @author SKK229873 中山真吾
 * @modifier SKK231099 李 : JPA連携
 */
@Entity
@Table(name = "feedback", indexes = { @Index(name = "idx_feedback", columnList = "fraud_score_id", unique = true) })
public class Feedback extends BasicClass {

	@Id
	@Column(name = "fraud_score_id", columnDefinition = "varchar(11)", nullable = false)
	private String fraudScoreId;

	// 未評価の場合、null
	@Column(name = "is_correct")
	private Boolean isCorrect;

	@Column(name = "comment", columnDefinition = "varchar(300)")
	private String comment;

	public Feedback() {
	}

	public Feedback(String fraudScoreId, String isCorrect, String comment) {
		this.fraudScoreId = fraudScoreId;
		this.isCorrect = Boolean.valueOf(isCorrect);
		this.comment = comment;
	}

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

	@Override
	public String getIdNames() {
		return "fraudScoreId";
	}

	@Override
	public String getPrimaryKey() {
		return this.fraudScoreId;
	}

	@Override
	public Map<String, Object> getProperties() {
		return null;
	}

	@Override
	public void setParams(Object obj) {
		Feedback feedback = (Feedback) obj;
		this.isCorrect = Boolean.valueOf(feedback.getIsCorrect());
		this.comment = feedback.getComment();
	}

	@Override
	public Object getValue(String fieldName) {
		Object rtn = "";
		switch (fieldName) {
		case "fraudScoreId":
			rtn = this.fraudScoreId;
			break;
		case "isCorrect":
			rtn = this.isCorrect;
			break;
		case "comment":
			rtn = this.comment;
			break;
		}

		return rtn;
	}

	@Override
	public void setValue(String fieldName, Object value) {
		switch (fieldName) {
		case "fraudScoreId":
			this.fraudScoreId = value == null ? null : String.valueOf(value);
			break;
		case "isCorrect":
			this.isCorrect = value == null ? null : Boolean.valueOf(String.valueOf(value));
			break;
		case "comment":
			this.comment = value == null ? null : String.valueOf(value);
			break;
		}
	}

}
