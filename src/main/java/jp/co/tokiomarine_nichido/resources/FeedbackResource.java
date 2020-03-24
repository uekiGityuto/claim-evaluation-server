package jp.co.tokiomarine_nichido.resources;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import jp.co.tokiomarine_nichido.models.Feedback;
import jp.co.tokiomarine_nichido.services.FeedbackService;

/**
 *　推論結果取得
 * @author SKK229873 中山真吾
 * @modify SKK231099 李
 */
@Path("/feedback")
public class FeedbackResource {
	@Inject
	private FeedbackService fbs;

	/**
	 * speechIdをキーに、累積されている音声テキストを返す。
	 *
	 * @param feedback
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public void register(Feedback feedback) {
		fbs.register(feedback);
	}
}