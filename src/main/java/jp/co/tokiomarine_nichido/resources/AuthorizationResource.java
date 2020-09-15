package jp.co.tokiomarine_nichido.resources;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import jp.co.tokiomarine_nichido.models.AuthResult;
import jp.co.tokiomarine_nichido.models.DecryptedResult;
import jp.co.tokiomarine_nichido.models.User;
import jp.co.tokiomarine_nichido.services.DecryptionService;

/**
 *  認可処理アプリのコントローラー
 *
 *  @author SKK231527 植木宥登
 *
 */
@Path("authorization")
public class AuthorizationResource {
	@Inject
	private DecryptionService decryptionService;
	@Inject
	private User user;

	/**
	 * @param userId ユーザID
	 * @param encryptedParam 暗号データ
	 * @return リダイレクト先URL
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("federation")
	public Response dataFederation(
			@HeaderParam("Uid") String userId,
			@QueryParam("param") String encryptedParam) {

		System.out.println("受信確認");
		String uri = "";
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
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("auth")
	public AuthResult authorize(
			@QueryParam("param") String encryptedParam,
			@QueryParam("Uid") String userId) {

		// 復号処理
		String decryptedString = decryptionService.decrypt(encryptedParam);
		if (decryptedString.isEmpty()) {
			throw new WebApplicationException(Response.Status.FORBIDDEN);
		}

		// 復号結果（JSON）をDecryptedResultオブジェクトにマッピング
		Gson gson = new Gson();
		DecryptedResult result = gson.fromJson(decryptedString, DecryptedResult.class);

		// URL生成時刻の比較
		if (!result.isCorrectDate()) {
			throw new WebApplicationException(Response.Status.FORBIDDEN);
		}

		// UserSessionログインユーザ情報にユーザ情報をセット
		user.userId = userId;
		user.authFlag = result.isAuthority();

		return result.createAuthResult(userId);
	}
}
