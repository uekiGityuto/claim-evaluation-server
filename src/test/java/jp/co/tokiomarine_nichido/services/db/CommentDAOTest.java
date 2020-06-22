package jp.co.tokiomarine_nichido.services.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.tokiomarine_nichido.models.Comment;
import jp.co.tokiomarine_nichido.util.DateUtil;

/**
 * CommentDAOTest
 *
 * @author SKK231099 李
 *
 */
//@RunWith(Arquillian.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CommentDAOTest extends DataServiceTest {

    final private String claimId = "00000865432";
    final private String userId = "99999999999";

    private static final Logger logger = LogManager.getLogger();

    public CommentDAOTest() throws Exception {
    }

    /**
     * コメント保存テスト
     * @throws Exception
     */
    @Test
    public void stage1_updateCommentTest() throws Exception {
        logger.info("updateCommentTest");
        Comment comment = new Comment();
        comment.setId(null);
        comment.setClaimId(claimId);
        comment.setComment("TEST Comment");
        comment.setUserId(userId);
        comment.setUserName("TEST User Name");
        if (super.getObject(Comment.class, comment.getId()) != null) {
            final String sql = super.pm.getSql("CommentDao.updateComment");
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("id", comment.getId());
            param.put("claimId", comment.getClaimId());
            param.put("comment", comment.getComment());
            Comment cmt = (Comment) super.updateObject(comment, sql, param);
            logger.info("CommentTest Update comment.claimId=" + cmt.getClaimId()  + " is OK");
        } else {
            comment.setId(super.getSequenceId("comment_id_seq"));
            Comment cmt = (Comment) super.insertObject(comment);
            logger.info("CommentTest Insert comment.claimId=" + cmt.getClaimId()  + " is OK");

            final String sql = super.pm.getSql("CommentDao.updateComment");
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("id", cmt.getId());
            param.put("claimId", cmt.getClaimId());
            param.put("comment", "TEST Update Comment");
            Comment cmt2 = (Comment) super.updateObject(cmt, sql, param);
            logger.info("CommentTest Update comment.claimId=" + cmt2.getClaimId()  + " is OK");
        }
    }

    /**
     * コメントリスト取得テスト
     * @throws Exception
     */
    @Test
    public void stage2_getCommentsTest() throws Exception {
        logger.info("getCommentsTest");
        String sql = super.pm.getSql("CommentDao.selectComment");
        Map<String, String> params = new HashMap<String, String>();
        params.put("claimId", claimId);
        List<Comment> cmtList = super.getListByQuery(Comment.class, sql, params);
        logger.info("CommentTest getComments commentList.size=" + cmtList.size() + " is OK");
    }

    /**
     * コメント取得テスト
     * @throws Exception
     */
    @Test
    public void stage3_getCommentTest() throws Exception {
        logger.info("getCommentTest");
        String sql = super.pm.getSql("CommentDao.selectComment");
        Map<String, String> params = new HashMap<String, String>();
        params.put("claimId", claimId);
        List<Comment> cmtList = super.getListByQuery(Comment.class, sql, params);
        logger.info("comment id=" + cmtList.get(0).getId());
        Comment cmt = super.getObject(Comment.class, cmtList.get(0).getId());
        logger.info("CommentTest getComment comment.id=" + cmt.getId() + " is OK");
    }

    /**
     * コメント削除テスト
     * @throws Exception
     */
    @Test
    @SuppressWarnings("rawtypes")
    public void stage4_removeCommentTest() throws Exception {
        logger.info("removeCommentTest");
        String sql = "select id, update_date from comment where user_id = '" + userId + "' limit 1";
        final ObjectMapper om = new ObjectMapper();
        Object obj = super.getObjectByNativeQuery(sql);
        ArrayList list = om.convertValue(obj, ArrayList.class);
        Comment cmt = new Comment();
        cmt.setId(Integer.valueOf(String.valueOf(list.get(0))));
        DateUtil du = new DateUtil();
        cmt.setUpdateDate(du.toTimestamp(Long.valueOf(String.valueOf(list.get(1)))));
        super.deleteObject(cmt);
        logger.info("CommentTest removeComment comment.userId=" + userId + " limit 1 is OK");
    }
}
