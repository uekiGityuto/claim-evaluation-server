package jp.co.tokiomarine_nichido.resources;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import jp.co.tokiomarine_nichido.models.Claim;
import jp.co.tokiomarine_nichido.services.AuthorizationService;

/**
 * リクエストを受け取り、IF15事案一覧照会APIから取得した結果を返すクラス
 *
 * @author SKK231527 植木宥登
 */
@Path("claims")
public class ClaimsResource {
	@Inject
	private AuthorizationService authorizeService;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public List<Claim> get(
			@Context HttpServletRequest request,
			@QueryParam("claimNumber") String claimNumber) {

		return null;
	}
}
