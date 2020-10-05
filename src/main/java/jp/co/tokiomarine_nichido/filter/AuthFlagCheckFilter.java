package jp.co.tokiomarine_nichido.filter;

import java.io.IOException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jp.co.tokiomarine_nichido.models.User;
import jp.co.tokiomarine_nichido.services.RestApiClient;
import jp.co.tokiomarine_nichido.util.PropertyManager;

/**
 * 損業権限チェックフィルタ
 *
 * @author SKK231527 植木宥登
 *
 */
@Priority(Priorities.AUTHORIZATION)
public class AuthFlagCheckFilter implements ContainerRequestFilter {

	@Inject
	private PropertyManager pm;
	private static final Logger logger = LogManager.getLogger(RestApiClient.class);
	@Context
	HttpServletRequest request;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		System.out.println("フィルタ確認2");

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (user == null || !user.isAuthFlag()) {
			String userId = requestContext.getHeaders().getFirst("Uid");
			logger.error(pm.getLogMessage("E012"), userId);
			requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
		}
	}

}
