package jp.co.tokiomarine_nichido.services;

import java.text.MessageFormat;
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jp.co.tokiomarine_nichido.util.PropertyManager;
import jp.co.tokiomarine_nichido.util.RequestClientWriterInterceptor;
import jp.co.tokiomarine_nichido.util.SignatureCreator;

@ApplicationScoped
public class RestApiClient {
	@Inject
	private PropertyManager pm;
	@Inject
	private SignatureCreator creator;

	private static final Logger logger = LogManager.getLogger(RestApiClient.class);

	public String post(String path, String body) throws Exception {

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
		headers = creator.getAuthorization(headers, body, host, path);

		// hostヘッダを付与するとwarningが出るので以下をセット
		// TODO: hostヘッダがなくともapi gatewayは（少なくともモックでは）認可するので要検討
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");

		// DEBUGログ出力
		// TODO: HTTPリクエスト全量を出せるように要検討
		// logger.debug(pm.get("D016"), path, headers);
		logger.debug(pm.get("D016"), path, body);

		// リクエスト
		Response response = ClientBuilder.newClient().register(RequestClientWriterInterceptor.class)
				.target("https://" + host).path(path)
				.request(MediaType.APPLICATION_JSON)
				.headers(headers)
				.post(Entity.json(body));

		if(response.getStatus() != 200) {
			// TODO: 適切なExceptionを返す
			// TODO: プレースホルダーにセットする情報が十分か要検討。eも渡す必要があるか確認。
			throw new Exception(MessageFormat.format(pm.get("E004"), path, response.getStatus()));
		}

		// responseのbody部を取得し、responseを閉じる
		// （response.reaEntityを実施するとresponseが閉じる）
		String result = response.readEntity(String.class);

		// DEBUGログ出力
		// TODO: HTTPリクエスト全量を出せるように要検討
		// logger.debug(pm.get("D017"), response.getStatus());
		// logger.debug(pm.get("D017"), response.getHeaders());
		logger.debug(pm.get("D017"), path, result);

		return result;
	}
}
