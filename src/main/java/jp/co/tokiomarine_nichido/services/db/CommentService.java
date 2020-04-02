package jp.co.tokiomarine_nichido.services.db;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.tokiomarine_nichido.models.Comment;
import jp.co.tokiomarine_nichido.models.CommentTest;

/**
 * CommentService
 * @author SKK231099 李
 *
 */
public class CommentService extends DataService{

	public List<Comment> getComments(String claimIds) {
		List<Comment> comments = null;
		if (claimIds.length() > 0) {
			String sql = null;
			if (claimIds.contains(",")) {
				sql ="select i from Comment i where i.claimId in (" + claimIds + ") order by i.createDate asc";
			} else {
				sql ="select i from Comment i where i.claimId = '" + claimIds + "' order by i.createDate asc";
			}
			comments = super.getList(sql, Comment.class);
		}
		if (comments == null) {
			comments = new ArrayList<Comment>();
		}
		return comments;
	}

	public Comment getComment(Comment comment) {

		// for test
		CommentTest cmt = super.getObject(comment.getClaimId(), null, CommentTest.class);
		Comment ct = new Comment();
		ct.setClaimId(cmt.getClaimId());
		ct.setIdx(cmt.getIdx());
		ct.setComment(cmt.getComment());
		ct.setUserId(cmt.getUserId());
		ct.setUserName(cmt.getUserName());
		ct.setCreateDate(cmt.getCreateDate());
		ct.setUpdateDate(cmt.getUpdateDate());
		return ct;
//		return super.getObject(comment.getClaimId(), comment.getProperties(), Comment.class);
	}

	public Boolean updateComment(Comment comment) {
		if (comment.getCreateDate() == null) {
			Timestamp now = new Timestamp((new Date()).getTime());
			comment.setCreateDate(now);
		} else {
			Timestamp now = new Timestamp((new Date()).getTime());
			comment.setUpdateDate(now);
		}

		// for test
		CommentTest ct = new CommentTest();
		ct.setClaimId(comment.getClaimId());
		ct.setIdx(comment.getIdx());
		ct.setComment(comment.getComment());
		ct.setUserId(comment.getUserId());
		ct.setUserName(comment.getUserName());
		ct.setCreateDate(comment.getCreateDate());
		ct.setUpdateDate(comment.getUpdateDate());

		return super.updateObject(ct, CommentTest.class);
//		return super.updateObject(ct, Comment.class);
	}
}
