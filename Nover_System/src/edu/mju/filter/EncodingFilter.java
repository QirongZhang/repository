package edu.mju.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

public class EncodingFilter implements Filter {
	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
	@Override
	public void destroy() {

	}
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String method = request.getMethod();
		if (method.equals("POST")) {
			request.setCharacterEncoding("UTF-8");
		} else {
			/**
			 * GET请求
			 */
			request = new GetRequest(request);
		}
		response.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response);
	}

	class GetRequest extends HttpServletRequestWrapper {

		public GetRequest(HttpServletRequest request) {
			super(request);
		}

		@Override
		public String getParameter(String name) {
			String oldValue = super.getParameter(name);
			String newValue = null;
			if (oldValue != null && oldValue != "") {
				try {
					newValue = new String(oldValue.getBytes("ISO-8859-1"),
							"UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			return newValue;
		}

		@Override
		public String[] getParameterValues(String name) {
			String[] oldVaueArray = super.getParameterValues(name);
			String[] newValueArray = null;
			if (oldVaueArray != null && oldVaueArray.length > 0) {
				newValueArray = new String[oldVaueArray.length];
				for (int i = 0; i < oldVaueArray.length; i++) {
					String str = oldVaueArray[i];
					if (str != null && !str.equals("")) {
						try {
							String new_str = new String(
									str.getBytes("ISO-8859-1"), "UTF-8");
							newValueArray[i] = new_str;
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}
				}
			}
			return newValueArray;
		}
	}

}
