package jp.co.tokiomarine_nichido.services;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.arquillian.container.test.api.RunAsClient;

import com.google.gson.Gson;

import jp.co.tokiomarine_nichido.models.Score;
import jp.co.tokiomarine_nichido.util.PropertyManager;

/**
 * TEST for 外部通信Service
 *
 * @author SKK231099 李
 */
@RunAsClient
public class RESTAPIDAOTest {

    final private String claimId = "00000865432";
    final private String relationKey = "claimId";
    final private String relationValue = this.claimId;

    private static final Logger logger = LogManager.getLogger();

    private PropertyManager pm;
    private Gson gson;

    public RESTAPIDAOTest() throws Exception {
        pm = new PropertyManager();
        gson = new Gson();
    }

    private String getScores(Map<String, String> params) throws ClientProtocolException, IOException,
                                                                                                    URISyntaxException {
        HttpClient client = HttpClientBuilder.create().build();
        final int timeout = Integer.valueOf(pm.get("test.connectTimeout"));
        final int rTimeout = Integer.valueOf(pm.get("test.readTimeout"));
        final RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout)
                                                                                          .setConnectionRequestTimeout(rTimeout)
                                                                                          .build();
        URIBuilder uriBuilder = new URIBuilder(pm.get("url.fraudScore"));
        if (params != null) {
            params.forEach((k, v) -> {
                uriBuilder.addParameter(k, v);
            });
        }
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setConfig(config);
        HttpResponse response = client.execute(httpGet);
        logger.info("StatusCode=" + response.getStatusLine().getStatusCode());
        String json = EntityUtils.toString(response.getEntity());
        return json;
    }

    /**
     * AI restAPIからのスコアリスト取得テスト
     * @throws Exception
     */
//    @Test
    @SuppressWarnings("unchecked")
    public void findAllTest() throws Exception {
        logger.info("findAllTest...");
        int maxList = Integer.valueOf(pm.get("max.list"));
        try {
            String json = getScores(null);
            List<Map<String, Object>> list = (List<Map<String, Object>>) gson.fromJson(json, ArrayList.class);
            if (list != null && list.size() > maxList) {
                list.subList(0, maxList);
            } else if (list == null) {
                list = new ArrayList<Map<String, Object>>();
            }
            logger.info("count: " + list.size());
        } catch(Exception e) {
            logger.info(e.toString());
            throw new Exception(e);
        }
        logger.info("findAllTest: OK");
    }

    /**
     * AI restAPIからのスコアリスト取得テスト
     * @throws Exception
     */
//    @Test
    @SuppressWarnings("unchecked")
    public void findByRelationTest() throws Exception {
        logger.info("findByRelationTest...");
        logger.info("relationKey: " + relationKey + ", relationValue: " + relationValue);
        Map<String, String> params = new HashMap<String, String>();
        params.put(relationKey, relationValue);
        try {
            String json = getScores(params);
            List<Score> scores = (List<Score>) gson.fromJson(json, ArrayList.class);
            if (scores == null) {
                scores = new ArrayList<Score>();
            }
            logger.info("count: " + scores.size());
        } catch(Exception e) {
            throw new Exception(e);
        }
        logger.info("findByRelationTest: OK");
    }

    /**
     * AI restAPIからのスコアリスト取得テスト
     * @throws Exception
     */
//    @Test
    @SuppressWarnings("unchecked")
    public void findByIdTest() throws Exception {
        logger.info("findByIdTest...");
        logger.info("relationKey: " + relationKey + ", relationValue: " + relationValue);
        Map<String, String> params = new HashMap<String, String>();
        params.put(relationKey, relationValue);
        try {
            String json = getScores(params);
            List<Map<String, Object>> list = (List<Map<String, Object>>) gson.fromJson(json, ArrayList.class);
            if (list == null) {
                list = new ArrayList<Map<String, Object>>();
            }
            logger.info("count: " + list.size());
        } catch(Exception e) {
            throw new Exception(e);
        }
        logger.info("findByIdTest: OK");
    }
}