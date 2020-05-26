package jp.co.tokiomarine_nichido.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.ws.rs.QueryParam;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 推論結果に対するフィードバック。
 *
 * @author SKK229873 中山真吾
 * @modifier SKK231099 李 : JPA連携
 */
@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name = "feedback", indexes = { @Index(name = "idx_feedback", columnList = "fraud_score_id", unique = true) })
public class Feedback extends BaseEntity {
    @Id
    @Column(name = "fraud_score_id", columnDefinition = "varchar(11)", nullable = false)
    @QueryParam("fraudScoreId")
    private String fraudScoreId;

    @Column(name = "is_correct")
    @QueryParam("isCorrect")
    private Boolean isCorrect; // 未評価の場合、null

    @Column(name = "comment", columnDefinition = "varchar(300)")
    @QueryParam("comment")
    private String comment;

    public Feedback() {
    }

    @Override
    public Object getPrimaryKey() {
        return this.fraudScoreId;
    }
}
