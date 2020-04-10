package jp.co.tokiomarine_nichido.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.client.rx.rxjava.RxObservable;

import jp.co.tokiomarine_nichido.util.PropertyManager;
import rx.Observable;

/**
 * 非同期通信Service
 * 
 * @author SKK231099 李
 */
@ApplicationScoped
public class ClientService {
	private String SAMLAuth = "";

	@Inject
	private PropertyManager pm;
	private String url;

	public ClientService() {
	}

	/**
	 * 
	 * @param <T>
	 * @param key
	 * @param type
	 * @return
	 */
	public <T> T findById(String id, Class<T> type) {
		Response response = client("get", String.join("/", pm.get("url.scores"), id), new HashMap<String, Object>());

		return response.readEntity(type);
	}

	/**
	 * 
	 * @param <T>
	 * @param type
	 * @return
	 */
	public <T> List<T> findAll(Class<T> type) {
		return client("get", pm.get("url.scores"), new HashMap<String, Object>()).readEntity(List.class);
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

	private String createQueryParameter(Map<String, String> params) {
		return params.entrySet().stream().map(e -> String.join("=", e.getKey(), e.getValue()))
				.reduce((s1, s2) -> String.join("&", s1, s2)).map(s -> "?" + s).orElse("");
	}

	public Observable<Response> rxCient(String method, String url, MultivaluedMap<String, Object> params) {

		Observable<Response> ob = null;

		if (method != null && url != null) {

			MultivaluedMap<String, Object> headerMap = new MultivaluedHashMap<String, Object>();
			// TODO: getAuthKey()
//			SAMLAuth =
			headerMap.add("Authorization", SAMLAuth);
			headerMap.add("Content-type", MediaType.APPLICATION_JSON);

			String lMethod = method.toLowerCase();
			if (lMethod.equals("get")) {
				StringBuilder urlParam = new StringBuilder();
				params.forEach((key, values) -> {
					if (urlParam.length() == 0) {
						urlParam.append("?");
					} else {
						urlParam.append("&");
					}
					urlParam.append(key + "=" + String.valueOf(values.get(0)));
				});
				url += urlParam;
				ob = RxObservable.from(new JerseyClientBuilder().build()).target(url).request().headers(headerMap).rx()
						.get();
			} else if (lMethod.equals("post")) {
				Form form = new Form();
				params.forEach((key, values) -> {
					form.param(key, String.valueOf(values.get(0)));
				});
				Entity<Form> entity = Entity.entity(form, MediaType.APPLICATION_JSON_TYPE);
				ob = RxObservable.from(new JerseyClientBuilder().build()).target(url).request().headers(headerMap).rx()
						.post(entity);
			}
		}

		return ob;
	}
}
