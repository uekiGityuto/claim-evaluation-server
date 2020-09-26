package jp.co.tokiomarine_nichido.util;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jp.co.tokiomarine_nichido.exceptions.AuthorizationFailedException;

/**
 * JAX-RSアプリの中で発生した全ての例外処理を担う。
 *
 * @author SKK229873 中山真吾
 * @author SKK231527 植木宥登
 *
 */
@Provider
public class DefaultExceptionMapper implements ExceptionMapper<Throwable> {
	private static final Logger logger = LogManager.getLogger(DefaultExceptionMapper.class);

	/**
	 * exceptionのタイプに応じて、HTTPレスポンスコードとボディを設定。
	 */
	@Override
	public Response toResponse(Throwable exception) {
		Response response = null;

		if (exception instanceof AuthorizationFailedException) {
			logger.error(exception);
			response = Response.status(Response.Status.UNAUTHORIZED).build();
		} else {
			logger.error(exception);
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}
}