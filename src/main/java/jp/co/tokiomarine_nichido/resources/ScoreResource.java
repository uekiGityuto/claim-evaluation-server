package jp.co.tokiomarine_nichido.resources;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Asynchronous;
import javax.enterprise.context.Dependent;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;

import jp.co.tokiomarine_nichido.services.ScoreService;

/**
 * for Angular 一覧画面、詳細画面  Data
 * @author SKK231099 李
 *
 */

@Path("score")
//@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//@Produces(MediaType.APPLICATION_FORM_URLENCODED)
public class ScoreResource {
	private ScoreService ss = null;

	public ScoreResource() {
		ss = new ScoreService();
	}

	@GET
	@Path("/list")
	@Asynchronous
	public void getScoreList(@Suspended final AsyncResponse ar) {
		String test = "wefasdfasdf";
		ar.resume(test);
//		ss.getScoreList(ar);
//		ar.setTimeout(10, SECONDS);
	}

	@GET
	@Path("/detail")
	public void getScore(@Suspended final AsyncResponse ar, @QueryParam("claimId") String claimId) {
		ss.getScore(ar, claimId);
	}

	@GET
	@Path("/test")
	public String getTest() {
		Gson gson = new Gson();
		Map<String, String> map = new HashMap<String, String>();
		map.put("test", "value");
		map.put("key", "val2!!!!!");
		return gson.toJson(map);
	}
}
