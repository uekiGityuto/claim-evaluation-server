package jp.co.tokiomarine_nichido.services;
import jp.co.tokiomarine_nichido.models.*;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.rx.rxjava.RxObservable;
import rx.Observable;
import com.google.gson.Gson;


/**
 * 非同期通信Service
 * @author SKK231099 李
 */
public class ObservableClientService {
	private String SAMLAuth = "";
	Gson gson = null;

	public ObservableClientService() {
		gson = new Gson();
	}

	public Result rxCient(String method, String url, MultivaluedMap<String, Object> params) {

		Result result = new Result();
		Map<String, String> errMsgList = new HashMap<String, String>();

		if (method != null && url != null) {

			MultivaluedMap<String, Object> headerMap = new MultivaluedHashMap<String, Object>();
			// TODO: getAuthKey()
//			SAMLAuth =
			headerMap.add("Authorization", SAMLAuth);

			Observable<Response> ob = null;

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
						.newClient()
						.target(url)
						.request()
						.accept(MediaType.APPLICATION_JSON)
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
						.newClient()
						.target(url)
						.request()
						.accept(MediaType.APPLICATION_JSON)
						.headers(headerMap)
						.rx()
						.post(entity);
			}

			ob.subscribe(rp -> {
//				gson.fromJson(rp.getEntity(), type)
				result.setData(rp);
				result.setSuccess(true);
				if (!result.isSuccess()) {
					errMsgList.put("status(" + rp.getStatus() + "): ", String.valueOf(rp.getStatusInfo()));
				}
			});
		}

		if (!result.isSuccess()) {
			if (errMsgList.size() == 0) {
				errMsgList.put("0", "Please Check method and url");
			}
		}
		result.setErrMsgList(errMsgList);
		return result;

	}
}
