package jp.co.tokiomarine_nichido.services.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Produces;

import jp.co.tokiomarine_nichido.models.Comment;

/**
 * CommentDAO
 *
 * @author SKK231099 李
 *
 */
@Produces
public class CommentDAO extends DataService {

    /**
     * コメントリスト取得
     * @param claimId
     * @return List<Comment>
     * @throws Exception
     */
    public List<Comment> getComments(String claimId) throws Exception {
        String sql = super.pm.getSql("CommentDao.selectComment");
        Map<String, String> params = new HashMap<String, String>();
        params.put("claimId", claimId);
        return super.getListByQuery(Comment.class, sql, params);
    }

    /**
     * コメント取得
     * @param comment
     * @return Comment
     * @throws Exception
     */
    public Comment getComment(Comment comment) throws Exception {
        return super.getObject(Comment.class, comment.getId());
    }

    /**
     * コメント保存
     * @param comment
     * @return Comment
     * @throws Exception
     */
    public Comment updateComment(Comment comment) throws Exception {
        if (getComment(comment) != null) {
            final String sql = super.pm.getSql("CommentDao.updateComment");
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("id", comment.getId());
            param.put("claimId", comment.getClaimId());
            param.put("comment", comment.getComment());
            return (Comment) super.updateObject(comment, sql, param);
        } else {
            comment.setId(super.getSequenceId("comment_id_seq"));
            return (Comment) super.insertObject(comment);
        }
    }

    /**
     * コメント削除
     * @param comment
     * @return Integer
     * @throws Exception
     */
    public Integer removeComment(Comment comment) throws Exception {
        super.deleteObject(comment);
        return comment.getId();
    }
}
