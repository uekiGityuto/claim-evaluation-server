package jp.co.tokiomarine_nichido.resources;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jp.co.tokiomarine_nichido.models.Log;
import jp.co.tokiomarine_nichido.util.PropertyManager;

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
	@Inject
	private PropertyManager pm;
	private static final Logger logger = LogManager.getLogger();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void log(Log log, @Context UriInfo uriInfo) {
		logger.trace(pm.getLogMessage("T004"), uriInfo.getPath());
		logger.log(log.getLogLevel(), log.logMessage);
		logger.trace(pm.getLogMessage("T005"), uriInfo.getPath());
	}
}
