package jp.co.tokiomarine_nichido.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import jp.co.tokiomarine_nichido.models.User;
import jp.co.tokiomarine_nichido.util.DecryptionService;

@Path("authorization")
public class AuthorizationResource {
	@Inject
	private DecryptionService ds;

	@GET
	public User authorize(@QueryParam("param") String encryptedParam) {
		// TODO: 【植木さん】実装
		String param = ds.decrypt(encryptedParam);

		return null;
	}
}
