package jp.co.tokiomarine_nichido.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import jp.co.tokiomarine_nichido.models.Claim;

/**
 * （事案一覧照会するREST Resource）
 *
 */
@Path("claim")
public class ClaimResource {

	@GET
	public List<Claim> get(@QueryParam("claimNumber") String claimNumber /* TODO: 【植木さん】その他検索項目を追加 */) {
		// TODO: 【植木さん】実装
		return null;
	}
}
