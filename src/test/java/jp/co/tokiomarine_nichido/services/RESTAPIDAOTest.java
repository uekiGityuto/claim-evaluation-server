package jp.co.tokiomarine_nichido.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.arquillian.container.test.api.RunAsClient;

import com.google.gson.Gson;

import jp.co.tokiomarine_nichido.models.Score;
import jp.co.tokiomarine_nichido.models.StatusCode;
import jp.co.tokiomarine_nichido.util.DefaultExceptionMapper;
import jp.co.tokiomarine_nichido.util.GsonMessageBodyReaderWriter;
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

    private Client restClient;
    private WebTarget base_wt;

    public RESTAPIDAOTest() throws Exception {
        pm = new PropertyManager();
        gson = new Gson();
    }

    private WebTarget getWebTarget() throws Exception {
        if (base_wt == null) {
            if (pm != null) {
                if (this.restClient== null) {
                    ClientBuilder cb = ClientBuilder.newBuilder();
                    cb.register(GsonMessageBodyReaderWriter.class);
                    this.restClient = cb.build();
                }
                this.base_wt = this.restClient.target(pm.get("url.fraudScore"))
                                   .property("http.connection.timeout", pm.get("Client.connectTimeout"))
                                   .property("http.receive.timeout", pm.get("Client.readTimeout"));
            }
        }
        return this.base_wt;
    }

    /**
     * TEST for Get Score List by ClaimId Group
     * @throws Exception
     */
//    @Test
    @SuppressWarnings("unchecked")
    public void findAllTest() throws Exception {
        logger.info("findAllTest...");
        int maxList = Integer.valueOf(pm.get("max.list"));
        try (Response response = getWebTarget().request(MediaType.APPLICATION_JSON).get()) {
            String json = response.readEntity(String.class);
            List<Map<String, Object>> list = (List<Map<String, Object>>) gson.fromJson(json, ArrayList.class);
            if (list != null && list.size() > maxList) {
                list.subList(0, maxList);
            } else if (list == null) {
                list = new ArrayList<Map<String, Object>>();
            }
            logger.info("count: " + list.size());
        } catch(Exception e) {
            logger.info(e.toString());
            DefaultExceptionMapper.status = StatusCode.EXTERNAL_SERVER_ERROR;
            throw new Exception("EXTERNAL_SERVER_ERROR");
        }
        logger.info("findAllTest: OK");
    }

    /**
     * TEST for Get Score List by ClaimID (List<T>)
     * @throws Exception
     */
//    @Test
    @SuppressWarnings("unchecked")
    public void findByRelationTest() throws Exception {
        logger.info("findByRelationTest...");
        logger.info("relationKey: " + relationKey + ", relationValue: " + relationValue);
        try (Response response = getWebTarget().queryParam(relationKey, relationValue)
                                                                       .request(MediaType.APPLICATION_JSON).get()) {
            List<Score> scores = (List<Score>) response.readEntity(List.class);
            if (scores == null) {
                scores = new ArrayList<Score>();
            }
            logger.info("count: " + scores.size());
        } catch(Exception e) {
            DefaultExceptionMapper.status = StatusCode.EXTERNAL_SERVER_ERROR;
            throw new Exception("EXTERNAL_SERVER_ERROR");
        }
        logger.info("findByRelationTest: OK");
    }

    /**
     * TEST for Get Score List by ClaimID (List<Map<String, Object>>)
     * @throws Exception
     */
//    @Test
    @SuppressWarnings("unchecked")
    public void findByIdTest() throws Exception {
        logger.info("findByIdTest...");
        logger.info("relationKey: " + relationKey + ", relationValue: " + relationValue);
        try (Response response = getWebTarget().queryParam(relationKey, relationValue)
                                                                       .request(MediaType.APPLICATION_JSON).get()) {
            String json = response.readEntity(String.class);
            List<Map<String, Object>> list = (List<Map<String, Object>>) gson.fromJson(json, ArrayList.class);
            if (list == null) {
                list = new ArrayList<Map<String, Object>>();
            }
            logger.info("count: " + list.size());
        } catch(Exception e) {
            DefaultExceptionMapper.status = StatusCode.EXTERNAL_SERVER_ERROR;
            throw new Exception("EXTERNAL_SERVER_ERROR");
        }
        logger.info("findByIdTest: OK");
    }
}