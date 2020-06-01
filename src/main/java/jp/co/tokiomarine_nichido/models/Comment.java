package jp.co.tokiomarine_nichido.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * comment for claim
 *
 * @author SKK231099 李
 */
@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name = "comment", indexes = { @Index(name = "idx_comment", columnList = "id, claim_id", unique = true) })
public class Comment extends BaseEntity {
    @Id
    @SequenceGenerator(name = "comment_id_gen", sequenceName = "comment_id_seq")
    @Column(name = "id", columnDefinition="serial", updatable=false)
    @QueryParam("id")
    private Integer id;

    @Column(name = "claim_id", columnDefinition = "varchar(11)", nullable = false, updatable=false)
    @PathParam("claimId")
    private String claimId;

    @Column(name = "comment", columnDefinition = "varchar(512)")
    @QueryParam("comment")
    private String comment;

    @Column(name = "user_id", columnDefinition = "varchar(11)")
    @QueryParam("userId")
    private String userId;

    @Column(name = "user_name", columnDefinition = "varchar(128)")
    @QueryParam("userName")
    private String userName;

    public Comment() {
    }

    @Override
    public Object getPrimaryKey() {
        return this.id;
    }

    @Override
    public Comment copy() {
        Comment cmt = new Comment();
        cmt.id = this.id;
        cmt.claimId = this.claimId;
        cmt.comment = this.comment;
        cmt.userId = this.userId;
        cmt.userName = this.userName;
        cmt.createDate = this.createDate;
        cmt.updateDate = this.updateDate;
        return cmt;
    }
}
