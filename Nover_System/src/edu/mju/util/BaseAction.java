package edu.mju.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.util.ServletContextAware;

import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseAction extends ActionSupport implements ServletRequestAware,
		ServletResponseAware, ServletContextAware {

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected ServletContext context;
	protected HttpSession session;
	protected ServletContext servletContext;

	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;

	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
		if (this.request != null) {
			this.session = this.request.getSession();
		}
	}
	
	@Override
	public abstract String execute() throws Exception;
}