package jp.co.tokiomarine_nichido.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import jp.co.tokiomarine_nichido.models.StatusCode;
import jp.co.tokiomarine_nichido.util.DefaultExceptionMapper;
import jp.co.tokiomarine_nichido.util.GsonMessageBodyReaderWriter;
import jp.co.tokiomarine_nichido.util.PropertyManager;

/**
 * 非同期通信Service
 *
 * @author SKK231099 李
 */
@ApplicationScoped
public class RESTAPIDAO {
//    private String SAMLAuth = "";

    @Inject
    private PropertyManager pm;

    private Client restClient;
    private WebTarget base_wt;

    private Gson gson;

    public RESTAPIDAO() {
        gson = new Gson();
    }

    /**
     * RESTクライアントのデフォルト設定。
     */
    @PostConstruct
    private void init() {
        this.restClient = ClientBuilder.newBuilder().register(GsonMessageBodyReaderWriter.class).build();
    }

    private WebTarget getWebTarget() {
        if (base_wt == null) {
            if (pm != null) {
                this.base_wt = this.restClient.target(pm.get("url.fraudScore"))
                                   .property("http.connection.timeout", pm.get("Client.connectTimeout"))
                                   .property("http.receive.timeout", pm.get("Client.readTimeout"));
}
        }
        return this.base_wt;
    }

    /**
     * findAll
     * @param <T>
     * @param type
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findAll() throws Exception {
        try (Response response = getWebTarget().request(MediaType.APPLICATION_JSON).get()) {
            String json = response.readEntity(String.class);
            return (List<Map<String, Object>>) gson.fromJson(json, ArrayList.class);
        } catch(Exception e) {
            DefaultExceptionMapper.status = StatusCode.EXTERNAL_SERVER_ERROR;
            throw new Exception("EXTERNAL_SERVER_ERROR");
        }
    }

    /**
     * findByRelation
     * @param <T>
     * @param relationKey
     * @param relationValue
     * @param type
     * @return List<T>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> findByRelation(String relationKey, String relationValue, Class<T> type) throws Exception {
        try (Response response = getWebTarget().queryParam(relationKey, relationValue)
                                               .request(MediaType.APPLICATION_JSON).get()) {
            return (List<T>) response.readEntity(List.class);
        } catch(Exception e) {
            DefaultExceptionMapper.status = StatusCode.EXTERNAL_SERVER_ERROR;
            throw new Exception("EXTERNAL_SERVER_ERROR");
        }
    }

    /**
     * findById
     * @param relationKey
     * @param relationValue
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findById(String relationKey, String relationValue) throws Exception {
        try (Response response = getWebTarget().queryParam(relationKey, relationValue)
                                               .request(MediaType.APPLICATION_JSON).get()) {
            String json = response.readEntity(String.class);
            return (List<Map<String, Object>>) gson.fromJson(json, ArrayList.class);
        } catch(Exception e) {
            DefaultExceptionMapper.status = StatusCode.EXTERNAL_SERVER_ERROR;
            throw new Exception("EXTERNAL_SERVER_ERROR");
        }
    }
}