package jp.co.tokiomarine_nichido.models;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Claim Model
 * @author SKK231099 李
 */

public class Claim {

    private String claimId;
	private String userName;
	private String name;
    private String kind;
    private Date birthday;
    private Timestamp occurenceDate;
    private Timestamp createDate;
    private Timestamp updateDate;

    private List<Comment> commentList;

    public Claim () {
    	commentList = new ArrayList<Comment>();
    }
    public Claim (	String claim_id,
					String user_name,
					String name,
				    String kind,
				    Date birthday,
				    Timestamp occurence_date,
				    Timestamp create_date,
				    Timestamp update_date) {
    	this.claimId = claim_id;
    	this.userName = user_name;
    	this.name = name;
    	this.kind = kind;
    	this.birthday = birthday;
    	this.occurenceDate = occurence_date;
    	this.createDate = create_date;
    	this.updateDate = update_date;
    }


	public String getClaimId() {
		return claimId;
	}
	public void setClaimId(String claimId) {
		this.claimId = claimId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Timestamp getOccurenceDate() {
		return occurenceDate;
	}
	public void setOccurenceDate(Timestamp occurenceDate) {
		this.occurenceDate = occurenceDate;
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
	public List<Comment> getCommentList() {
		return commentList;
	}
	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

}