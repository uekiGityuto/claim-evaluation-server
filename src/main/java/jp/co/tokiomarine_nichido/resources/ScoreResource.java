package jp.co.tokiomarine_nichido.resources;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import jp.co.tokiomarine_nichido.models.Score;
import jp.co.tokiomarine_nichido.services.ScoreService;

/**
 * for Angular 一覧画面、詳細画面 Data
 *
 * @author SKK231099 李
 *
 */
@Path("score-history")
public class ScoreResource {
	@Inject
	private ScoreService ss;

	// TODO: 【植木さん】全般的に修正・実装
	@Path("{claimNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Score> getScore(@PathParam("claimNumber") @DefaultValue("") String claimNumber) throws Exception {
		if (claimNumber.isEmpty()) {
			return null;
		}
		return ss.findListByClaimId(claimNumber);
	}

	@Path("{claimNumber}/{scoringDate}")
	@Produces(MediaType.APPLICATION_JSON)
	public Score get(@PathParam("claimNumber") String claimNumber, @PathParam("scoringDate") Date scoringDate) {
		return null;
	}
}
