package jp.co.tokiomarine_nichido.services;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.client.rx.rxjava.RxObservable;

import rx.Observable;


/**
 * 非同期通信Service
 * @author SKK231099 李
 */
public class ObservableClientService {
	private String SAMLAuth = "";

	public ObservableClientService() {
	}

	public Observable<Response> rxCient(String method, String url, MultivaluedMap<String, Object> params) {

		Observable<Response> ob = null;

		if (method != null && url != null) {

			MultivaluedMap<String, Object> headerMap = new MultivaluedHashMap<String, Object>();
			// TODO: getAuthKey()
//			SAMLAuth =
			headerMap.add("Authorization", SAMLAuth);
			headerMap.add("Content-type", MediaType.APPLICATION_FORM_URLENCODED);


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
				ob = RxObservable
						.from(new JerseyClientBuilder().build())
						.target(url)
						.request()
						.headers(headerMap)
						.rx()
						.get();
			} else if (lMethod.equals("post")) {
				Form form = new Form();
				params.forEach((key, values) -> {
					form.param(key, String.valueOf(values.get(0)));
				});
				Entity<Form> entity = Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE);
				ob = RxObservable
						.from(new JerseyClientBuilder().build())
						.target(url)
						.request()
						.headers(headerMap)
						.rx()
						.post(entity);
			}
		}

		return ob;
	}
}
