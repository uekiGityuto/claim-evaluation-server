package jp.co.tokiomarine_nichido.services.db;

import java.util.List;

import javax.inject.Inject;

import org.jboss.weld.junit5.auto.WeldJunit5AutoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import jp.co.tokiomarine_nichido.models.Comment;

@ExtendWith(WeldJunit5AutoExtension.class)
public class CommentDAOTest {


    @Inject
    CommentDAO testSubject;

    @Test
    public void testGetComments() throws Exception {
        List<Comment> comments = testSubject.getComments("1");

        for (Comment comment : comments) {
            System.out.println(comment.getComment());
        }
    }

    @Test
    public void testCreateOrUpdate() throws Exception {
        throw new RuntimeException("not yet implemented");
    }
}
