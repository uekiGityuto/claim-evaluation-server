package jp.co.tokiomarine_nichido.filter;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import jp.co.tokiomarine_nichido.resources.ClaimListResource;
import jp.co.tokiomarine_nichido.resources.ScoresResource;

/**
 * フィルタを動的に登録するクラス。
 *
 * @author SKK231527 植木宥登
 *
 */
@Provider
public class FilterDynamicFeature implements DynamicFeature {

	/**
	 * フィルタを登録する。
	 */
	@Override
	public void configure(ResourceInfo resourceInfo, FeatureContext context) {

		context.register(LogFilter.class);

		if(resourceInfo.getResourceClass().equals(ScoresResource.class) ||
				resourceInfo.getResourceClass().equals(ClaimListResource.class)	) {
			context.register(ApprovedCheckFilter.class);
		}

		if(resourceInfo.getResourceClass().equals(ClaimListResource.class)	) {
			context.register(AuthFlagCheckFilter.class);
		}
	}

}
