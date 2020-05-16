package jp.co.tokiomarine_nichido.services;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import jp.co.tokiomarine_nichido.util.GsonMessageBodyReaderWriter;
import jp.co.tokiomarine_nichido.util.PropertyManager;

/**
 * 非同期通信Service
 * 
 * @author SKK231099 李
 */
@ApplicationScoped
public class RESTAPIDAO {
	private String SAMLAuth = "";

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
		this.restClient = ClientBuilder.newBuilder()
				.connectTimeout(Long.parseLong(pm.get("restClient.connectTimeout")), TimeUnit.SECONDS)
				.readTimeout(Long.parseLong(pm.get("restClient.readTimeout")), TimeUnit.SECONDS)
				.register(GsonMessageBodyReaderWriter.class).build();

		this.base_wt = this.restClient.target(pm.get("url.fraudScore"));
	}

	/**
	 * 
	 * @param <T>
	 * @param key
	 * @param type
	 * @return
	 */
	public <T> T findById(String id, Class<T> type) {
		// TODO: 【李】ヘッダーの内容は別途検討。API Gateway経由になる予定で、トークン認証があるかも。
		try (Response response = base_wt.path(id).request(MediaType.APPLICATION_JSON).get()) {
			return response.readEntity(type);
		}
	}

	/**
	 * 
	 * @param <T>
	 * @param type
	 * @return
	 */
	public <T> List<T> findAll(GenericType<List<T>> type) {
		try (Response response = base_wt.request(MediaType.APPLICATION_JSON).get()) {
			return response.readEntity(type);
		}
	}

	public <T> List<T> findByRelation(String relationKey, String relationValue, GenericType<List<T>> type) {
		try (Response response = base_wt.queryParam(relationKey, relationValue).request(MediaType.APPLICATION_JSON)
				.get()) {
			return response.readEntity(type);
		}
	}

	public Response client(String method, String url, Map<String, Object> params) {
		Response rs = null;

		// TODO: 【李】ネストが増えると可読性がさがるので、if(method == null || url == null) return nullにする。
		if (method != null && url != null) {
			MultivaluedMap<String, Object> headerMap = new MultivaluedHashMap<String, Object>();
			// TODO: getAuthKey()
//			SAMLAuth =
			headerMap.add("Authorization", SAMLAuth);
			headerMap.add("Content-type", MediaType.APPLICATION_JSON);

			String lMethod = method.toLowerCase();
			if (lMethod.equals("get")) {
				StringBuilder urlParam = new StringBuilder();
				params.forEach((key, value) -> {
					if (urlParam.length() == 0) {
						urlParam.append("?");
					} else {
						urlParam.append("&");
					}
					urlParam.append(key + "=" + value);
				});
				url += urlParam;
				rs = ClientBuilder.newClient().target(url).request(MediaType.APPLICATION_JSON).headers(headerMap).get();
			} else if (lMethod.equals("post")) {
				Form form = new Form();
				params.forEach((key, value) -> {
					form.param(key, String.valueOf(value));
				});
				Entity<Form> entity = Entity.entity(form, MediaType.APPLICATION_JSON_TYPE);
				rs = ClientBuilder.newClient().target(url).request(MediaType.APPLICATION_JSON).headers(headerMap)
						.post(entity);
			}
		}

		return rs;
	}
}
