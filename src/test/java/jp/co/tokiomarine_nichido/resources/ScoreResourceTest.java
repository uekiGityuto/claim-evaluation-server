package jp.co.tokiomarine_nichido.resources;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;

//import jakarta.ws.rs.core.Response;
import jp.co.tokiomarine_nichido.models.Comment;
import jp.co.tokiomarine_nichido.models.Feedback;
import jp.co.tokiomarine_nichido.util.PropertyManager;

/**
 * TEST for Rest API Request
 *
 * @author SKK231099 李
 *
 */
//@RunWith(Arquillian.class)
@RunAsClient
public class ScoreResourceTest extends JerseyTest {

    private String claimId = "00000865432";
    private String fraudScoreId = "00000000001";

    private static final Logger logger = LogManager.getLogger();

    private String baseUri;
    private String contextUri;

    private Gson gson;
    private PropertyManager pm;

    private static final Map<String, String> param = new HashMap<String, String>();
    static {
        param.put("com.sun.jersey.config.property.packages", "jp.co.tokiomarine_nichido.resources");
    }
    public ScoreResourceTest() throws Exception {
        super(new WebAppDescriptor.Builder(param).build());
        gson = new Gson();
        pm = new PropertyManager();
    }

    @Before
    public void setup() throws Exception {
        baseUri = pm.get("test.base.uri");
        contextUri = pm.get("test.context.uri");
    }

//    @Override
//    public Application configure() {
//        return new ResourceConfig().packages("jp.co.tokiomarine_nichido");
//    }

//    @Test
    public void updateFeedbackTest() throws Exception {
        logger.info("updateFeedbackTest...");
        Feedback feedback = new Feedback();
        feedback.setFraudScoreId(fraudScoreId);
        feedback.setComment("TEST Update Feedback Comment with URL");
        feedback.setIsCorrect(true);

        logger.info("updateFeedbackTest OK");
    }

//    @Test
    public void updateCommentTest() throws Exception {
        logger.info("updateComment");
        Comment comment = new Comment();
        comment.setClaimId(claimId);
        comment.setComment("TEST Comment");
        comment.setUserId("99999999999");
        comment.setUserName("TEST User Name");
        String json = gson.toJson(comment);

    }

    @Test
    public void getScoresTest() throws Exception {
        logger.info("getScoresTest...");
        final String url = baseUri + contextUri + "/scores";
        logger.info("url= " + url );
        Client client = Client.create();
        WebResource resource = client.resource(url);
        ClientResponse response = resource.get(ClientResponse.class);
        logger.info("ScoreResourceTest getScores Status=" + response.getStatus());
    }


//    @Test
    public void removeCommentTest() throws Exception {
        logger.info("removeComment");
        Comment comment = new Comment();
        comment.setClaimId(claimId);
        String json = gson.toJson(comment);

    }

    @Test
    public void testScores() {
        Client client = Client.create();
        String url = pm.get("url.fraudScore");
        logger.info(url);
        WebResource resource = client.resource(url);
        ClientResponse response = resource.get(ClientResponse.class);
        logger.info("TestScores Status=" + response.getStatus());
    }
}
