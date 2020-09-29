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

/**
 * 認可済みチェックフィルタ
 *
 * @author SKK231527 植木宥登
 *
 */
@Priority(Priorities.AUTHENTICATION)
public class ApprovedCheckFilter implements ContainerRequestFilter {

	@Context
	HttpServletRequest request;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		System.out.println("フィルタ確認1");

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (!user.isUserId()) {
			// TODO: 例外をThrowするように変更
			requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
		}
	}

}
