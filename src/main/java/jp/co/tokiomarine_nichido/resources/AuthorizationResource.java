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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import jp.co.tokiomarine_nichido.models.AuthResult;
import jp.co.tokiomarine_nichido.services.AuthorizationService;
import jp.co.tokiomarine_nichido.util.PropertyManager;

/**
 *  認可処理アプリのコントローラー
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

	/**
	 * @param userId ユーザID
	 * @param encryptedParam 暗号データ
	 * @return クエリパラメータにユーザIDと暗号データをセットしたリダイレクト先URL
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response dataFederation(
			@HeaderParam("Uid") String userId,
			@QueryParam("param") String encryptedParam) {

		System.out.println("受信確認1");

		String uri = pm.get("url.webServer");
		try {
			return Response.temporaryRedirect(
					new URI(uri + "?Uid=" + userId + "&param=" + encryptedParam)).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
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
	public AuthResult authorize(
			@Context HttpServletRequest request,
			@QueryParam("param") String encryptedParam,
			@QueryParam("userId") String userId) throws Exception {

		System.out.println("受信確認2");

		AuthResult authResult = authorizeService.authorize(encryptedParam, userId, request);

		return authResult;
	}
}
