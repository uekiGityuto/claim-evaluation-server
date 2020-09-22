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

import jp.co.tokiomarine_nichido.models.TargetClaims;
import jp.co.tokiomarine_nichido.models.User;
import jp.co.tokiomarine_nichido.services.ClaimService;

/**
 * CE-S02事案一覧画面からのリクエストを受け取るコントローラー
 *
 * @author SKK231527 植木宥登
 */
@Path("claims")
public class ClaimsResource {
	@Inject
	private ClaimService claimService;

	/**
	 * 認可済みチェック、損業権限チェックを実施する
	 *
	 * @return チェック結果
	 */
	@GET
	@Path("transition")
	public Response checkAuthFlag() {

		System.out.println("受信確認4");

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
	public String get(
			@Context HttpServletRequest request,
			TargetClaims targetClaims) {

		System.out.println("受信確認5");

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		targetClaims.REQ_USER_ID = user.getUserId();

		try {
			String inqureResult = claimService.getClaimList(targetClaims);
			return inqureResult;
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return null;
	}
}
