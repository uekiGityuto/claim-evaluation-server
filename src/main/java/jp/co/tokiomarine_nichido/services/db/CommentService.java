package jp.co.tokiomarine_nichido.services.db;

import java.util.ArrayList;
import java.util.List;

import jp.co.tokiomarine_nichido.models.Comment;

/**
 * CommentService
 * @author SKK231099 李
 *
 */
public class CommentService extends DataService{

	public List<Comment> getComments(String claimIds) {
		if (claimIds.length() > 0) {
			// get comment by claim_id
			String sql_claim = "select i from Comment i where claim_id in (" + claimIds + ")";
			return super.getList(sql_claim, Comment.class);
		}
		return new ArrayList<Comment>();
	}
}
