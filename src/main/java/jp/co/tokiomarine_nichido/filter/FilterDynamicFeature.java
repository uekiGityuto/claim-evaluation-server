package jp.co.tokiomarine_nichido.filter;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import jp.co.tokiomarine_nichido.resources.ClaimsResource;
import jp.co.tokiomarine_nichido.resources.ScoresResource;

@Provider
public class FilterDynamicFeature implements DynamicFeature {

	@Override
	public void configure(ResourceInfo resourceInfo, FeatureContext context) {

		System.out.println("フィルタ登録呼び出し1");

		if(resourceInfo.getResourceClass().equals(ScoresResource.class) ||
				resourceInfo.getResourceClass().equals(ClaimsResource.class)	) {
			context.register(ApprovedCheckFilter.class);
		}

		System.out.println("フィルタ登録呼び出し2");

		if(resourceInfo.getResourceClass().equals(ClaimsResource.class)	) {
			context.register(AuthFlagCheckFilter.class);
		}
	}

}
