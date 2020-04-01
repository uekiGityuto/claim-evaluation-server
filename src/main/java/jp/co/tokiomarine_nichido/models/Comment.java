package jp.co.tokiomarine_nichido.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

/**
 * comment for claim
 * @author SKK231099 李
 */
@Entity
@Immutable
@IdClass(PK_Comment.class)
@Table(name="comment",
       indexes= {@Index(name="idx_comment", columnList="claim_id, idx", unique=true)})
public class Comment extends BasicClass {
	@Id
	@Column(name="claim_id", nullable = false)
	private String claimId;
	@Id
	@Column(name="idx", nullable = false)
	private String idx;
	@Column(name="comment")
	private String comment;
	@Column(name="user_name")
	private String userName;
	@Column(name="create_date")
	private Timestamp createDate;
	@Column(name="update_date")
	private Timestamp updateDate;

	public String getClaimId() {
		return claimId;
	}
	public void setClaimId(String claimId) {
		this.claimId = claimId;
	}
	public String getIdx() {
		return idx;
	}
	public void setIdx(String idx) {
		this.idx = idx;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String getIdNames() {
		return "claimId,idx";
	}

	@Override
	public String getPrimaryKey() {
		return this.claimId;
	}

	@Override
	public Map<String, Object> getProperties() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idx", this.idx);
		return map;
	}

	@Override
	public void setParams(Object obj) {
		Comment comment = (Comment) obj;
		this.comment = comment.getComment();
		this.userName = comment.getUserName();
		this.createDate = comment.getCreateDate();
		this.updateDate = comment.getUpdateDate();
	}

	@Override
	public Object getValue(String fieldName) {
		Object rtn = "";
		switch (fieldName) {
		case "claimId":
			rtn = this.claimId;
			break;
		case "idx":
			rtn = this.idx;
			break;
		case "comment":
			rtn = this.comment;
			break;
		case "userName":
			rtn = this.userName;
			break;
		}

		return rtn;
	}
}
