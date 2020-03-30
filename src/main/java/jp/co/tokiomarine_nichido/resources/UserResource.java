package jp.co.tokiomarine_nichido.resources;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import jp.co.tokiomarine_nichido.models.User;
import jp.co.tokiomarine_nichido.services.SAMLService;

/***
 * 損害担当者のユーザー情報を取得、追加するサービスクラス
 *
 * @author SKK229873 中山真吾
 */
@ApplicationScoped
@Path("user")
public class UserResource {
	@Inject
	private SAMLService ss;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@QueryParam("id") String userId) throws Exception {
		if (userId == null || userId == "") {
			return null;
		} else {
			return ss.get();
		}
	}
}