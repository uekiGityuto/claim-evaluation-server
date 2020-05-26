package jp.co.tokiomarine_nichido.services;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

    public RESTAPIDAO() {
    }

    /**
     * RESTクライアントのデフォルト設定。
     */
    @PostConstruct
    private void init() {
        // TODO:【李】デフォルトタイムアウトをconfig.propertiesから読み込む
        this.restClient = ClientBuilder.newBuilder().register(GsonMessageBodyReaderWriter.class).build();

        this.base_wt = this.restClient.target(pm.get("url.fraudScore"));
    }

    /**
     * findAll
     * @param <T>
     * @param type
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> findAll(Class<T> type) throws Exception {
        try (Response response = base_wt.request(MediaType.APPLICATION_JSON).get()) {
            return (List<T>) response.readEntity(List.class);
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
        try (Response response = base_wt.queryParam(relationKey, relationValue)
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
     * @return String
     * @throws Exception
     */
    public String findById(String relationKey, String relationValue) throws Exception {
        try (Response response = base_wt.queryParam(relationKey, relationValue)
                                        .request(MediaType.APPLICATION_JSON).get()) {
            return response.readEntity(String.class);
        } catch(Exception e) {
            DefaultExceptionMapper.status = StatusCode.EXTERNAL_SERVER_ERROR;
            throw new Exception("EXTERNAL_SERVER_ERROR");
        }
    }
}