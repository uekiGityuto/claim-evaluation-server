package jp.co.tokiomarine_nichido.resources;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jp.co.tokiomarine_nichido.models.TargetClaim;
import jp.co.tokiomarine_nichido.models.User;
import jp.co.tokiomarine_nichido.models.scores.Scores;
import jp.co.tokiomarine_nichido.services.ClaimService;
import jp.co.tokiomarine_nichido.services.RestApiClient;
import jp.co.tokiomarine_nichido.util.PropertyManager;

/**
 * CE-S01スコア詳細画面からのリクエストを受け取るコントローラー。
 *
 * @author SKK231527 植木宥登
 *
 */
@Path("scores")
public class ScoresResource {
	@Inject
	private ClaimService claimService;
	@Inject
	private PropertyManager pm;
	private static final Logger logger = LogManager.getLogger(RestApiClient.class);

	/**
	 * @param request
	 * @param targetClaim
	 * @return IF08スコア照会APIから取得した結果を返す
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Scores get(
			@Context HttpServletRequest request,
			@Context UriInfo uriInfo,
			TargetClaim targetClaim) throws Exception {

		logger.trace(pm.getLogMessage("T004"), uriInfo.getPath());

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		targetClaim.userId = user.getUserId();

		Scores scores = claimService.getScores(targetClaim);

		logger.trace(pm.getLogMessage("T005"), uriInfo.getPath());
		return scores;
	}

}
