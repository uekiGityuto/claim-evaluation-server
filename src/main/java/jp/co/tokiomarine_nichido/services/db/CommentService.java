package jp.co.tokiomarine_nichido.services.db;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Table;

import jp.co.tokiomarine_nichido.models.Comment;

/**
 * CommentService
 *
 * @author SKK231099 李
 *
 */
public class CommentService extends DataService {
	private String className = null;
	private String tableName = null;

	public CommentService() {
		this.className = Comment.class.getName();
		this.tableName = Comment.class.getAnnotation(Table.class).name();
	}

	public String getClassName() {
		return className;
	}

	public String getTableName() {
		return tableName;
	}

	public List<Comment> getComments(String claimIds) {
		List<Comment> comments = null;
		if (claimIds.length() > 0) {
			String sql = null;
			if (claimIds.contains(",")) {
				sql = "select i from " + className + " i where i.claimId in (" + claimIds + ")";
			} else {
				sql = "select i from " + className + " i where i.claimId = '" + claimIds + "'";
			}
			comments = super.getList(sql, Comment.class);
		}
		if (comments == null) {
			comments = new ArrayList<Comment>();
		}
		return comments;
	}

	public Comment getComment(Comment comment) {
		return super.getObject(comment.getPrimaryKey(), comment.getProperties(), Comment.class);
	}

	public Boolean updateComment(Comment comment) {
		if (comment.getCreateDate() == null) {
			Timestamp now = new Timestamp((new Date()).getTime());
			comment.setCreateDate(now);
		}
		if (comment.getIdx() == null && comment.getClaimId() != null) {
			final String sql = "select i.idx from " + tableName + " i where i.claim_id = '" + comment.getClaimId()
					+ "' order by i.idx desc limit 1";
			Integer idx = super.getObjectByNativeQuery(sql, Integer.class);
			if (idx == null) {
				idx = 1;
			} else {
				idx += 1;
			}
			comment.setIdx(idx);
		}
		return super.updateObject(comment, Comment.class);
	}
}
