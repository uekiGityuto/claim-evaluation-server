package jp.co.tokiomarine_nichido.services;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jp.co.tokiomarine_nichido.util.PropertyManager;
import jp.co.tokiomarine_nichido.util.SignatureCreator;

/**
 * REST APIにリクエストするクラス。
 *
 * @author SKK231527 植木宥登
 *
 */
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
		try {
			headers = creator.getAuthorization(headers, body, host, path);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | IllegalStateException
				| URISyntaxException e) {
			throw new Exception(
					MessageFormat.format(pm.getLogMessage("E020"), body), e);
		}

		// hostヘッダを付与するとwarningが出るので以下をセット
		// TODO: hostヘッダがなくともapi gatewayは（少なくともモックでは）認可するので要検討
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");

		// DEBUGログ出力
		logger.debug(pm.getLogMessage("D016"), path, getHeaderString(headers), body);

		// リクエスト
		String scheme = pm.get("api.scheme");
		Response response = ClientBuilder.newClient()
				.target(scheme + "//" + host).path(path)
				.request(MediaType.APPLICATION_JSON)
				.headers(headers)
				.post(Entity.json(body));

		if (response.getStatus() != 200) {
			throw new WebApplicationException(
					MessageFormat.format(pm.getLogMessage("E004"), path,
							getHeaderString(response.getHeaders()), response.getStatus()));
		}

		// レスポンス結果取得
		String result = response.readEntity(String.class);

		// DEBUGログ出力
		logger.debug(pm.getLogMessage("D017"), path, getHeaderString(response.getHeaders()), result);

		return result;

	}

	/**
	 * @param headers
	 * @return headersを文字列に変換して返す
	 */
	private String getHeaderString(MultivaluedMap<String, Object> headers) {
		StringJoiner joiner = new StringJoiner(",", "{", "}");

		for (String key : headers.keySet()) {
			joiner.add(key + "=" + headers.getFirst(key));
		}
		return joiner.toString();
	}

}
