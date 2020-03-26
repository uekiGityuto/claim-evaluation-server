package jp.co.tokiomarine_nichido.services;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * CORSFilter
 * Enable it for Servlet 3.x implementations
 * @author SKK231099 李
 */
//@WebFilter(urlPatterns = "/*")
public class CORSFilter implements Filter {

	public CORSFilter() {}

    @Override
	public void destroy() {}

    @Override
	public void doFilter(ServletRequest rq, ServletResponse rp, FilterChain fc) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) rq;
		HttpServletResponse response = (HttpServletResponse) rp;

		if (!response.containsHeader("Access-Control-Allow-Origin")) {
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST, DELETE");
			response.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
			response.addHeader("Access-Control-Allow-Credentials", "true");
		}

		switch (request.getMethod()) {
			case "OPTIONS":
				response.setStatus(HttpServletResponse.SC_OK);
				return;
		}

		fc.doFilter(request, response);
	}

    @Override
	public void init(FilterConfig fConfig) throws ServletException {}

}
