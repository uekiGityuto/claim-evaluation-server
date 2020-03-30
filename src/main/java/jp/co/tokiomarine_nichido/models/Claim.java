package jp.co.tokiomarine_nichido.models;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.tokiomarine_nichido.util.DateUtil;

/**
 * Claim Model
 * @author SKK231099 李
 */

public class Claim extends BasicClass {

    private String claimId;
	private String userName;
	private String name;
    private String kind;
    private Date birthday;
    private Timestamp occurenceDate;
    private Timestamp createDate;
    private Timestamp updateDate;

    private List<Comment> commentList;

    private DateUtil dateUtil;

    public Claim () {
    	commentList = new ArrayList<Comment>();
    }
    public Claim (	String claimId,
					String userName,
					String name,
				    String kind,
				    Date birthday,
				    Timestamp occurenceDate,
				    Timestamp createDate,
				    Timestamp updateDate) {
    	this.claimId = claimId;
    	this.userName = userName;
    	this.name = name;
    	this.kind = kind;
    	this.birthday = birthday;
    	this.occurenceDate = occurenceDate;
    	this.createDate = createDate;
    	this.updateDate = updateDate;
    	commentList = new ArrayList<Comment>();
    }
    public Claim (Map<String, Object> obj) {
    	this.claimId = String.valueOf(obj.get("claimId"));
    	this.userName = String.valueOf(obj.get("userName"));
    	this.name = String.valueOf(obj.get("name"));
    	this.kind = String.valueOf(obj.get("kind"));
		dateUtil = new DateUtil();
    	this.birthday = dateUtil.toDate(String.valueOf(obj.get("birthday")));
    	this.occurenceDate = dateUtil.toTimestamp(String.valueOf(obj.get("occurenceDate")));
    	this.createDate = dateUtil.toTimestamp(String.valueOf(obj.get("createDate")));
    	this.updateDate = dateUtil.toTimestamp(String.valueOf(obj.get("updateDate")));
    	commentList = new ArrayList<Comment>();
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
	public void addComment(Comment comment) {
		this.commentList.add(comment);
	}

	@Override
	public String getPrimaryKey() {
		return this.claimId;
	}

	@Override
	public Map<String, Object> getProperties() {
		return null;
	}

	@Override
	public void setParams(Object obj) {
		Claim claim = (Claim) obj;
    	this.claimId = claim.getClaimId();
    	this.userName = claim.getUserName();
    	this.name = claim.getName();
    	this.kind = claim.getKind();
    	this.birthday = claim.getBirthday();
    	this.occurenceDate = claim.getOccurenceDate();
    	this.createDate = claim.getCreateDate();
    	this.updateDate = claim.getUpdateDate();
	    commentList = new ArrayList<Comment>();

	}

}