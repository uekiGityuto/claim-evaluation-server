package jp.co.tokiomarine_nichido.filter;

import java.io.IOException;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import jp.co.tokiomarine_nichido.models.User;

@Priority(Priorities.AUTHENTICATION)
public class ApprovedCheckFilter implements ContainerRequestFilter {

	@Context
	HttpServletRequest request;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		System.out.println("フィルタ確認1");

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		System.out.println(user.getUserId());

		if (user.getUserId() == null) {
			requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
					.entity("Not Logged In.").build());
		}
	}

}
