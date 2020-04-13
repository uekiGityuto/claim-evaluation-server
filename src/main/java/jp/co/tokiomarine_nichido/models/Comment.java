package jp.co.tokiomarine_nichido.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * comment for claim
 *
 * @author SKK231099 李
 */
@Entity
// TODO: indexはclaimIDに対して付与する。
@Table(name = "comment", indexes = { @Index(name = "idx_comment", columnList = "id", unique = true) })
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", columnDefinition = "integer", nullable = false, unique = true)
	public Integer id;

	@Exclude
	@Column(name = "claim_id", columnDefinition = "varchar(11)", nullable = false)
	public String claimId;

	@Column(name = "comment", columnDefinition = "varchar(512)")
	public String comment;

	@Column(name = "user_id", columnDefinition = "varchar(11)")
	public String userId;

	@Column(name = "user_name", columnDefinition = "varchar(128)")
	public String userName;

	@Column(name = "create_date", updatable = false)
	@CreationTimestamp
	public Timestamp createDate;

	@Column(name = "update_date")
	@UpdateTimestamp
	@Version
	public Timestamp updateDate;
}
