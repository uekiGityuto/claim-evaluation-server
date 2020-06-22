package jp.co.tokiomarine_nichido.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Produces;
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
 * 外部通信Service
 *
 * @author SKK231099 李
 */
@ApplicationScoped
@Produces
public class RESTAPIDAO {
//    private String SAMLAuth = "";

    private PropertyManager pm;

    private Client restClient;
    private WebTarget base_wt;

    private Gson gson;

    public RESTAPIDAO() {
        gson = new Gson();
        pm = new PropertyManager();
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
     * スコアリスト取得
     * @param <T>
     * @param type
     * @return List<Map<String, Object>>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findAll() throws Exception {
        int maxList = Integer.valueOf(pm.get("max.list"));
        try (Response response = getWebTarget().request(MediaType.APPLICATION_JSON).get()) {
            String json = response.readEntity(String.class);
            List<Map<String, Object>> list = (List<Map<String, Object>>) gson.fromJson(json, ArrayList.class);
            if (list != null && list.size() > maxList) {
                return list.subList(0, maxList);
            }
            return list;
        } catch(Exception e) {
            System.out.println(e.toString());
            DefaultExceptionMapper.status = StatusCode.EXTERNAL_SERVER_ERROR;
            throw new Exception("EXTERNAL_SERVER_ERROR");
        }
    }

    /**
     * スコアリスト取得
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
     * スコアリスト取得
     * @param relationKey
     * @param relationValue
     * @return List<Map<String, Object>>
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