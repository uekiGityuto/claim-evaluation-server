package jp.co.tokiomarine_nichido.resources;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jp.co.tokiomarine_nichido.models.User;
import jp.co.tokiomarine_nichido.models.claim_list.ClaimList;
import jp.co.tokiomarine_nichido.models.target_claim_list.TargetClaimList;
import jp.co.tokiomarine_nichido.services.ClaimService;
import jp.co.tokiomarine_nichido.services.RestApiClient;
import jp.co.tokiomarine_nichido.util.PropertyManager;

/**
 * CE-S02事案一覧画面からのリクエストを受け取るコントローラー
 *
 * @author SKK231527 植木宥登
 */
@Path("claims")
public class ClaimListResource {
	@Inject
	private ClaimService claimService;
	@Inject
	private PropertyManager pm;
	private static final Logger logger = LogManager.getLogger(RestApiClient.class);

	/**
	 * 認可済みチェック、損業権限チェックを実施する
	 *
	 * @return チェック結果
	 */
	@GET
	@Path("transition")
	public Response checkAuthFlag(@Context UriInfo uriInfo) {

		logger.trace(pm.getLogMessage("T004"), uriInfo.getPath());
		logger.trace(pm.getLogMessage("T005"), uriInfo.getPath());

		// フィルタでチェックOKとなっているためHTTPステータス200を返す
		return Response.status(Response.Status.OK).build();
	}

	/**
	 * @param request
	 * @param targetClaims
	 * @return IF15事案一覧照会APIから取得した結果を返す
	 */
	@POST
	@Path("get")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ClaimList get(
			@Context HttpServletRequest request,
			@Context UriInfo uriInfo,
			TargetClaimList targetClaims) throws Exception {

		logger.trace(pm.getLogMessage("T004"), uriInfo.getPath());

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		targetClaims.userId = user.getUserId();

		ClaimList claims = claimService.getClaimList(targetClaims);

		logger.trace(pm.getLogMessage("T005"), uriInfo.getPath());
		return claims;
	}

}
