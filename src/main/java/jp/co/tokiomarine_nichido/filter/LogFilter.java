package jp.co.tokiomarine_nichido.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jp.co.tokiomarine_nichido.services.RestApiClient;
import jp.co.tokiomarine_nichido.util.PropertyManager;

/**
 * 全アクセス時のログをINFOログとして出力するためのフィルタ。
 *
 * @author SKK231527 植木宥登
 *
 */
@Priority(Priorities.AUTHENTICATION)
public class LogFilter implements ContainerRequestFilter {

	@Inject
	private PropertyManager pm;
	private static final Logger logger = LogManager.getLogger(RestApiClient.class);

	/**
	 * INFOログを出力する。
	 */
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		System.out.println("フィルタ確認1");

		String userId = requestContext.getHeaders().getFirst("Uid");

		// リクエストパスを取得
		String path = requestContext.getUriInfo().getPath();
		String queryParam = "";
		MultivaluedMap<String, String> queryParamMap = requestContext.getUriInfo().getQueryParameters();
		for (String key : queryParamMap.keySet()) {
			queryParam += queryParam.isEmpty() ? "?" : "&";
			queryParam += key + "=" + queryParamMap.getFirst(key);
		}
		path += queryParam;

		// HTTPリクエストボディを取得
		String body = "";
		if (requestContext.getEntityStream() != null) {
			body = getBody(requestContext);
		}

		// INFOログ出力
		logger.info(pm.getLogMessage("I021"), userId, path, body);

		// HTTPリクエストボディを再セット
		requestContext.setEntityStream(new ByteArrayInputStream(body.getBytes("utf-8")));

	}

	/**
	 * HTTPリクエストボディを取得する。
	 *
	 * @param requestContext
	 * @return HTTPリクエストボディ
	 * @throws IOException
	 */
	public String getBody(ContainerRequestContext requestContext) throws IOException {
		try (InputStream is = requestContext.getEntityStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		}

	}

}
