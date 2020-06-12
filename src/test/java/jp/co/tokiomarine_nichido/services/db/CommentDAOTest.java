package jp.co.tokiomarine_nichido.services.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

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
public class CommentDAOTest extends DataServiceTest {

    final private String claimId = "00000865432";
    final private String userId = "99999999999";

    public CommentDAOTest() throws Exception {
    }

    /**
     * TEST for Update Comment
     * @throws Exception
     */
    @Test
    public void updateCommentTest() throws Exception {
        System.out.println("updateCommentTest");
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
            System.out.println("CommentTest Update comment.claimId=" + cmt.getClaimId()  + " is OK");
        } else {
            comment.setId(super.getSequenceId("comment_id_seq"));
            Comment cmt = (Comment) super.insertObject(comment);
            System.out.println("CommentTest Insert comment.claimId=" + cmt.getClaimId()  + " is OK");

            final String sql = super.pm.getSql("CommentDao.updateComment");
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("id", cmt.getId());
            param.put("claimId", cmt.getClaimId());
            param.put("comment", "TEST Update Comment");
            Comment cmt2 = (Comment) super.updateObject(cmt, sql, param);
            System.out.println("CommentTest Update comment.claimId=" + cmt2.getClaimId()  + " is OK");
        }
    }

    /**
     * TEST for Get Comment List by ClaimID
     * @throws Exception
     */
    @Test
    public void getCommentsTest() throws Exception {
        System.out.println("getCommentsTest");
        String sql = super.pm.getSql("CommentDao.selectComment");
        Map<String, String> params = new HashMap<String, String>();
        params.put("claimId", claimId);
        super.getListByQuery(Comment.class, sql, params);
        System.out.println("CommentTest getComments comment.claimId=" + claimId + " is OK");
    }

    /**
     * TEST for Get Informattion of Comment
     * @throws Exception
     */
    @Test
    public void getCommentTest() throws Exception {
        System.out.println("getCommentTest");
        Comment comment = new Comment();
        comment.setId(1);
        super.getObject(Comment.class, comment.getId());
        System.out.println("CommentTest getComment comment.id=1 is OK");
    }

    /**
     * TEST for Remove Comment
     * @throws Exception
     */
    @Test
    @SuppressWarnings("rawtypes")
    public void removeCommentTest() throws Exception {
        System.out.println("removeCommentTest");
        String sql = "select id, update_date from comment where user_id = '" + userId + "' limit 1";
        final ObjectMapper om = new ObjectMapper();
        Object obj = super.getObjectByNativeQuery(sql);
        ArrayList list = om.convertValue(obj, ArrayList.class);
        Comment cmt = new Comment();
        cmt.setId(Integer.valueOf(String.valueOf(list.get(0))));
        DateUtil du = new DateUtil();
        cmt.setUpdateDate(du.toTimestamp(Long.valueOf(String.valueOf(list.get(1)))));
        super.deleteObject(cmt);
        System.out.println("CommentTest removeComment comment.userId=" + userId + " limit 1 is OK");
    }
}
