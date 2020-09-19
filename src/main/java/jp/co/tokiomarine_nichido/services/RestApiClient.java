package jp.co.tokiomarine_nichido.services;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import jp.co.tokiomarine_nichido.models.ScoreReqBody;
import jp.co.tokiomarine_nichido.util.RequestClientWriterInterceptor;
import jp.co.tokiomarine_nichido.util.SignatureCreator;

@ApplicationScoped
public class RestApiClient {

	public void inquiry() throws Exception {

		// ヘッダ作成
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
		String apiKey = "p0jFqdShkh3GOUXzjyuCk3x253LywCgp5MakxRlg";
		headers.putSingle("X-API-KEY", apiKey);
		String host = "cmnnnxfwxi.execute-api.ap-northeast-1.amazonaws.com";
		headers.putSingle("HOST", host);
		String requestId = UUID.randomUUID().toString();
		headers.putSingle("X-TMN-REQUEST-ID", requestId);
		OffsetDateTime dateUtc = OffsetDateTime.now(ZoneId.of("UTC"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String globalTranId = "WS-" + dateUtc.format(formatter) + "-" + requestId;
		headers.putSingle("X-TMN-GLOBAL-TRANSACTION-ID", globalTranId);

		// パス作成
		String path = "/prd/inqiry";

		// ボディ作成
		ScoreReqBody bodyObj = new ScoreReqBody("AAA123456", "1234567890");
		Gson gson = new Gson();
		String bodyStr = gson.toJson(bodyObj);

		// API GateWayのIAM認証に必要なヘッダ追加
		SignatureCreator creator = new SignatureCreator();
		headers = creator.getAuthorization(headers, bodyStr, host, path);

		// hostヘッダを付与するとwarningが出るので以下をセット
		// ただし、hostヘッダがなくともapi gatewayは（少なくともモックでは）認可するので要検討
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");

		// リクエスト
		Response response = ClientBuilder.newClient().register(RequestClientWriterInterceptor.class)
				.target("https://" + host).path(path)
				.request(MediaType.APPLICATION_JSON)
							.headers(headers)
							.post(Entity.json(bodyStr));
//							.post(Entity.json(null));

		// TODO: Loggerで実装
		System.out.println("レスポンス結果(status):" + response.getStatus());
		System.out.println("レスポンス結果(body):" + response.readEntity(String.class));
	}
}
