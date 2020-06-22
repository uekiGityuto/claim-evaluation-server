package jp.co.tokiomarine_nichido.resources;

import java.sql.Timestamp;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
//import com.sun.jersey.test.framework.JerseyTest;
//import com.sun.jersey.test.framework.WebAppDescriptor;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//import jakarta.ws.rs.core.Response;
import jp.co.tokiomarine_nichido.models.Comment;
import jp.co.tokiomarine_nichido.models.Feedback;
import jp.co.tokiomarine_nichido.models.StatusCode;
import jp.co.tokiomarine_nichido.util.DateUtil;
import jp.co.tokiomarine_nichido.util.PropertyManager;

/**
 * TEST for Rest API Request
 *
 * @author SKK231099 李
 *
 */
@RunAsClient
//@RunWith(Arquillian.class)
public class ScoreResourceTest extends JerseyTest {

    private String claimId = "00000865432";
    private String fraudScoreId = "00000000001";

    private static final Logger logger = LogManager.getLogger();

    private String baseUri;
    private String contextUri;
    private String scoresPreUri;

    private Gson gson;
    private PropertyManager pm;

    public ScoreResourceTest() throws Exception {
        gson = new GsonBuilder().setDateFormat(DateUtil.DATE_FORMAT).create();
        pm = new PropertyManager();
    }

    @Override
    protected Application configure() {
        return new ResourceConfig().packages("jp.co.tokiomarine_nichido.resources");
    }
    @Override
    protected DeploymentContext configureDeployment() {
        return DeploymentContext.builder(configure()).build();
    }

    @Before
    public void setup() throws Exception {
        baseUri = pm.get("test.base.uri");
        contextUri = pm.get("test.context.uri");
        scoresPreUri = baseUri + contextUri + "/scores";
    }

    private HttpResponse getResponse(String url, String method, Object entity) throws Exception {
        if (url == null) {
            throw new Exception("url is null");
        } else if (method == null) {
            throw new Exception("method is null");
        } else if ("post".equals(method.toLowerCase()) && entity == null) {
            throw new Exception("params are null");
        }
        method = method.toLowerCase();
        logger.info("Method: " + method);
        HttpClient client = HttpClientBuilder.create().build();
        final int timeout = Integer.valueOf(pm.get("test.connectTimeout"));
        final int rTimeout = Integer.valueOf(pm.get("test.readTimeout"));
        final RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout)
                                                                                          .setConnectionRequestTimeout(rTimeout).build();
        HttpResponse response;
        if ("get".equals(method)) {
          HttpGet httpGet = new HttpGet(url);
          httpGet.setConfig(config);
          response = client.execute(httpGet);
        } else if ("post".equals(method)) {
          HttpPost httpPost = new HttpPost(url);
          httpPost.setConfig(config);
          String json = gson.toJson(entity);
          logger.info(json);
          StringEntity se = new StringEntity(json);
          se.setContentType(MediaType.APPLICATION_JSON);
          httpPost.setEntity(se);
          logger.info(httpPost.getEntity());
          response = client.execute(httpPost);
        } else {
          response = (HttpResponse) new HttpResponseException(404, "Select method which one in ['get', 'post']");
        }
        return response;
    }

    /**
     * フィードバック保存RestAPI呼び出しテスト
     * @throws Exception
     */
    @Test
    public void updateFeedbackTest() throws Exception {
        logger.info("updateFeedbackTest...");
        DateUtil du = new DateUtil();
        Feedback feedback = new Feedback();
        feedback.setFraudScoreId(fraudScoreId);
        feedback.setComment("TEST Update Feedback Comment with URL");
        feedback.setIsCorrect(true);
        Timestamp ts = du.toTimestamp("2020-06-15T09:59:00.000");
        logger.info("timestamp= " + String.valueOf(ts));
        feedback.setCreateDate(ts);
        feedback.setUpdateDate(ts);
        final String url = scoresPreUri + "/" + claimId + "/updateFeedbackComment";
        logger.info("url= " + url);
        HttpResponse response = getResponse(url, "post", feedback);
        if (StatusCode.SUCCESS == response.getStatusLine().getStatusCode()) {
            logger.info("ScoreResourceTest updateFeedbackTest is OK");
        } else {
            throw new Exception("ScoreResourceTest updateFeedbackTest Failed: " + response.getStatusLine().toString());
        }
    }

    /**
     * コメント保存RestAPI呼び出しテスト
     * @throws Exception
     */
    @Test
    public void updateCommentTest() throws Exception {
        logger.info("updateCommentTest...");
        DateUtil du = new DateUtil();
        Comment comment = new Comment();
        comment.setId(999999999);
        comment.setClaimId(claimId);
        comment.setComment("TEST Comment");
        comment.setUserId("99999999999");
        comment.setUserName("TEST User Name");
        Timestamp ts = du.toTimestamp("2020-06-15T09:59:00.000");
        logger.info("timestamp= " + String.valueOf(ts));
        comment.setCreateDate(ts);
        comment.setUpdateDate(ts);
        final String url = scoresPreUri + "/" + claimId + "/updateComment";
        logger.info("url= " + url);
        HttpResponse response = getResponse(url, "post", comment);
        if (StatusCode.SUCCESS == response.getStatusLine().getStatusCode()) {
            logger.info("ScoreResourceTest updateCommentTest is OK");
        } else {
            throw new Exception("ScoreResourceTest updateCommentTest Failed: " + response.getStatusLine().toString());
        }
    }

    /**
     * スコアリスト取得RestAPI呼び出しテスト
     * @throws Exception
     */
    @Test
    public void getScoresTest() throws Exception {
        logger.info("getScoresTest...");
        final String url = scoresPreUri;
        logger.info("url= " + url );
        HttpResponse response = getResponse(url, "get", null);
        if (StatusCode.SUCCESS == response.getStatusLine().getStatusCode()) {
            logger.info("ScoreResourceTest getScoresTest is OK");
        } else {
            throw new Exception("ScoreResourceTest getScoresTest Failed: " + response.getStatusLine().toString());
        }
    }

    /**
     * スコア取得RestAPI呼び出しテスト
     * @throws Exception
     */
    @Test
    public void getScoreTest() throws Exception {
        logger.info("getScoreTest...");
        final String url = scoresPreUri + "/" + claimId;
        logger.info("url= " + url);
        HttpResponse response = getResponse(url, "get", null);
        if (StatusCode.SUCCESS == response.getStatusLine().getStatusCode()) {
            logger.info("ScoreResourceTest getScoreTest is OK");
        } else {
            throw new Exception("ScoreResourceTest getScoreTest Failed: " + response.getStatusLine().toString());
        }
    }

    /**
     * コメント削除RestAPI呼び出しテスト
     * @throws Exception
     */
    @Test
    public void removeCommentTest() throws Exception {
        logger.info("removeCommentTest...");
        Comment comment = new Comment();
        comment.setClaimId(claimId);
        comment.setComment("removeCommentTest");
        final String url = scoresPreUri + "/" + claimId + "/removeComment";
        logger.info("url= " + url);
        HttpResponse response = getResponse(url, "post", comment);
        if (StatusCode.SUCCESS == response.getStatusLine().getStatusCode()) {
            logger.info("ScoreResouceTest removeCommentTest is OK");
        } else {
            throw new Exception("ScoreResourceTest removeCommentTest Failed: " + response.getStatusLine().toString());
        }
    }
}
