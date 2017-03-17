package edu.mju.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

public class StrutsFilter extends StrutsPrepareAndExecuteFilter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		/**
		 * 判断请求路径是否包含Servlet的关键字。
		 */
		HttpServletRequest request = (HttpServletRequest) req;
		System.out.println(request.getRequestURI());
		String requestURI = request.getRequestURI();
		if (requestURI.indexOf("/servlet") != -1) {
			chain.doFilter(req, res);
		} else if (requestURI.indexOf("/ueditor1.3.6") != -1) {
			chain.doFilter(req, res);
		} else {
			super.doFilter(req, res, chain);
		}
	}
}
