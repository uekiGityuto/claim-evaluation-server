package jp.co.tokiomarine_nichido.util;

import javax.persistence.OptimisticLockException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jp.co.tokiomarine_nichido.exceptions.AuthenticationFailedException;
import jp.co.tokiomarine_nichido.exceptions.InvalidInputException;

/**
 * JAX-RSアプリの中で発生した全ての例外処理を担う。
 *
 * @author SKK229873 中山真吾
 * @add    SKK231099 李　: Exception Status
 *
 */
@Provider
public class DefaultExceptionMapper implements ExceptionMapper<Throwable> {
	private static final Logger logger = LogManager.getLogger(DefaultExceptionMapper.class);
	static public Integer status; // ADD(SKK230199)

	/**
	 * exceptionのタイプに応じて、HTTPレスポンスコードとボディを設定。
	 */
	@Override
	public Response toResponse(Throwable exception) {
		Response res = null;

		if (exception instanceof AuthenticationFailedException) {
			res = Response.status(Response.Status.UNAUTHORIZED).header("WWW-Authenticate", "Basic realm=app").build();
		} else if (exception instanceof InvalidInputException) {
			res = Response.status(Response.Status.PRECONDITION_FAILED).entity(exception.getMessage()).build();
		} else if (exception instanceof OptimisticLockException) {
			res = Response.status(Response.Status.PRECONDITION_FAILED).entity(exception.getMessage()).build();
		} else {
	        // ADD(SKK230199)
		    if (status != null) {
	            res = Response.status(status).entity(exception.getMessage()).build();
	        }
		    // END
		    else {
    			logger.error(exception);

    			String message = ExceptionUtil.getMessage(exception);
    			res = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
	        }
		}
		return res;
	}
}