package jp.co.tokiomarine_nichido.services;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import jp.co.tokiomarine_nichido.util.PropertyManager;
import jp.co.tokiomarine_nichido.util.RequestClientWriterInterceptor;
import jp.co.tokiomarine_nichido.util.SignatureCreator;

@ApplicationScoped
public class RestApiClient {
	@Inject
	private PropertyManager pm;
	@Inject
	private SignatureCreator creator;


	public String inquire(String path, String body) throws Exception {

		// ヘッダ作成
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
		String apiKey = pm.get("api.apikey");
		headers.putSingle("X-API-KEY", apiKey);
		String host = pm.get("api.host");
		headers.putSingle("HOST", host);
		String requestId = UUID.randomUUID().toString();
		headers.putSingle("X-TMN-REQUEST-ID", requestId);
		OffsetDateTime dateUtc = OffsetDateTime.now(ZoneId.of("UTC"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String globalTranId = pm.get("systemId") + "-" + dateUtc.format(formatter) + "-" + requestId;
		headers.putSingle("X-TMN-GLOBAL-TRANSACTION-ID", globalTranId);

		// API GateWayのIAM認証に必要なヘッダ追加
//		SignatureCreator creator = new SignatureCreator();
		headers = creator.getAuthorization(headers, body, host, path);

		// hostヘッダを付与するとwarningが出るので以下をセット
		// TODO: hostヘッダがなくともapi gatewayは（少なくともモックでは）認可するので要検討
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");

		// リクエスト
		Response response = ClientBuilder.newClient().register(RequestClientWriterInterceptor.class)
				.target("https://" + host).path(path)
				.request(MediaType.APPLICATION_JSON)
							.headers(headers)
							.post(Entity.json(body));
//							.post(Entity.json(null));

		// TODO: Loggerで実装
//		System.out.println("レスポンス結果(status):" + response.getStatus());
//		System.out.println("レスポンス結果(body):" + response.readEntity(String.class));

		// responseのbody部を取得し、responseを閉じる
		// （response.reaEntityを実施するとresponseが閉じる）
		String result = response.readEntity(String.class);

		return result;
	}
}
