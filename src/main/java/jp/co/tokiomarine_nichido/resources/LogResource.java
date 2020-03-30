package jp.co.tokiomarine_nichido.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jp.co.tokiomarine_nichido.models.Log;

/**
 * ログリソース。
 *
 * ブラウザのログを、サーバーに集約するために使用する。
 *
 * @author SKK229873 中山真吾
 *
 */
@Path("log")
public class LogResource {
	private static final Logger logger = LogManager.getLogger();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void log(Log log) {
		logger.log(log.getLogLevel(), log.logMessage);
	}
}
