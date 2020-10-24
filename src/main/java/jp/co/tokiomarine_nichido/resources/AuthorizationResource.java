package jp.co.tokiomarine_nichido.resources;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jp.co.tokiomarine_nichido.exceptions.AuthorizationFailedException;
import jp.co.tokiomarine_nichido.models.AuthorizationResult;
import jp.co.tokiomarine_nichido.services.AuthorizationService;
import jp.co.tokiomarine_nichido.services.RestApiClient;
import jp.co.tokiomarine_nichido.util.PropertyManager;

/**
 *  認可処理アプリのコントローラー。
 *
 *  @author SKK231527 植木宥登
 *
 */
@Path("authorize")
public class AuthorizationResource {
	@Inject
	private AuthorizationService authorizeService;
	@Inject
	private PropertyManager pm;
	private static final Logger logger = LogManager.getLogger(RestApiClient.class);

	/**
	 * @param userId ユーザID
	 * @param encryptedParam 暗号データ
	 * @return クエリパラメータにユーザIDと暗号データをセットしたリダイレクト先URL
	 * @throws AuthorizationFailedException
	 * @throws WebApplicationException
	 */
	@GET
	public Response dataFederation(
			@HeaderParam("Uid") String userId,
			@QueryParam("param") String encryptedParam,
			@Context UriInfo uriInfo) throws AuthorizationFailedException, WebApplicationException {

		logger.trace(pm.getLogMessage("T004"), uriInfo.getPath());

		if(userId == null || userId.isEmpty()) {
			logger.trace(pm.getLogMessage("T005"), uriInfo.getPath());
			throw new AuthorizationFailedException(pm.getLogMessage("E019"));
		}

		String uri = pm.get("url.ui");
		try {
			logger.trace(pm.getLogMessage("T005"), uriInfo.getPath());
			return Response.temporaryRedirect(
					new URI(uri + "?Uid=" + userId + "&param=" + encryptedParam)).build();
		} catch (URISyntaxException e) {
			logger.trace(pm.getLogMessage("T005"), uriInfo.getPath());
			throw new WebApplicationException(pm.getLogMessage("E022"), e);
		}
	}

	/**
	 *
	 * 認可処理
	 *
	 * @param encryptedParam 暗号データ
	 * @param userId ユーザID
	 * @return 認可結果
	 * @throws Exception
	 */
	@GET
	@Path("authorize")
	@Produces(MediaType.APPLICATION_JSON)
	public AuthorizationResult authorize(
			@Context HttpServletRequest request,
			@QueryParam("param") String encryptedParam,
			@QueryParam("userId") String userId,
			@Context UriInfo uriInfo) throws Exception {

		logger.trace(pm.getLogMessage("T004"), uriInfo.getPath());

		AuthorizationResult authResult = authorizeService.authorize(encryptedParam, userId, request);

		logger.trace(pm.getLogMessage("T005"), uriInfo.getPath());
		return authResult;
	}
}
