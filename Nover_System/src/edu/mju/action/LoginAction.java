package edu.mju.action;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import edu.mju.pojo.AdminBean;
import edu.mju.service.AdminService;
import edu.mju.util.BaseAction;

@ParentPackage(value = "struts-default")
@Namespace(value = "/")
@Action(value = "LoginAction", results = { 
		@Result(name = "index-admin",  location = "/view/users/index-admin.jsp" ,type="redirect" ),
		@Result(name = "login", location = "/view/login.jsp")
})
public class LoginAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private AdminService adminService;
	
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}
	
	/**
	 * 创建一个session
	 */
	ActionContext context = ActionContext.getContext();
	Map session = context.getSession();

	public String exit() throws Exception {
		session.remove("user");
		return "exit";
	}
	
	@Override
	public String execute() throws Exception {
		String user_name = request.getParameter("user_name");
		String password = request.getParameter("password");
		String user = request.getParameter("user");
		
		System.out.println("username = "+user_name);
		System.out.println("password = "+password);
		System.out.println("user = "+user);
		
		boolean flag = false;
		
		if (user.equals("1")) {
			
		}else if (user.equals("2")) {
			
		}else if (user.equals("3")) {
			List<AdminBean> adminList = adminService.adminlist();
			AdminBean adminBean = null;
			adminBean = (AdminBean) session.get("adminBean");
			if (adminBean == null) {
				for (AdminBean admin : adminList) {
					if (!user_name.equals(admin.getAdmin_name())
							|| !password.equals(admin.getPassword())) {
						flag = false;
					}else {
						session.put("admin", admin);
						flag = true;
						break;
					}
				}
			} else  flag = true;
			 
			if (flag)  return "index-admin";
			else  return "login";
			
		}
		return NONE;
	}
}
