package jp.co.tokiomarine_nichido.resources;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import jp.co.tokiomarine_nichido.models.User;
import jp.co.tokiomarine_nichido.services.ClaimService;

/**
 * リクエストを受け取り、IF08スコア照会APIから取得した結果を返すクラス
 *
 * @author SKK231527 植木宥登
 *
 */
@Path("scores")
public class ScoresResource {
	@Inject
	private ClaimService claimService;

//	@POST
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String get(
			@Context HttpServletRequest request,
			@QueryParam("claimNumber") String claimNumber) {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		String inqureResult;
		try {
			inqureResult = claimService.findByClaimNumber(claimNumber, user);
			return inqureResult;
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return null;
	}
}
