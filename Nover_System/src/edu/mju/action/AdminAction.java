package edu.mju.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;

import edu.mju.pojo.AdminBean;
import edu.mju.pojo.RoleBean;
import edu.mju.service.AdminService;
import edu.mju.service.JudgeService;
import edu.mju.service.RoleService;
import edu.mju.util.BaseAction;

@ParentPackage(value = "struts-default")
@Namespace(value = "/")
@Action(value = "AdminAction", results = {
		@Result(name = "add", location = "/view/users/admin/add.jsp"),
		@Result(name = "list", location = "/view/users/admin/list.jsp"),
		@Result(name = "confirm", location = "/view/users/admin/confirm.jsp"),
		@Result(name = "edit", location = "/view/users/admin/edit.jsp") })
public class AdminAction extends BaseAction implements ModelDriven<AdminBean> {

	private static final long serialVersionUID = 1797625823611184033L;
	private AdminBean adminBean;

	public AdminBean getAdminBean() {
		return adminBean;
	}

	public void setAdminBean(AdminBean adminBean) {
		this.adminBean = adminBean;
	}

	@Autowired
	private AdminService adminService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private JudgeService judgeService;

	/**
	 * 添加管理员
	 * 
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {

		String admin_select_role = request.getParameter("admin_select_role");
		System.out.println(admin_select_role);
		System.out.println(adminBean.getAdmin_name() + adminBean.getAdmin_id()
				+ adminBean.getPassword() + adminBean.getPhone_num()
				+ adminBean.getSex() + "----");
		adminService.addAdmin(this.adminBean, admin_select_role);
		return "confirm";
	}

	public String showAddJsp() throws Exception {
		ActionContext context = ActionContext.getContext();
		ValueStack valueStack = context.getValueStack();

		List<RoleBean> roleList = roleService.findRoleList();
		for (int i = 0; i < roleList.size(); i++) {
			if (roleList.get(i).getRole_id().equals("01")) {
				System.out.println("1");
				roleList.remove(i);
			}
		}
		valueStack.set("roleList", roleList);
		return "add";
	}

	/**
	 * 感觉没有写好
	 * 
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		ActionContext context = ActionContext.getContext();
		ValueStack valueStack = context.getValueStack();
		String admin_id = request.getParameter("admin_id");
		System.out.println(admin_id);
		AdminBean adminBean = adminService.findAdminBeanById(admin_id);
		System.out.println(adminBean.getAdmin_name());
		this.adminBean = adminBean;
		/**
		 * 判断是否是管理员
		 */
		boolean flag = judgeService.isAdmin(admin_id);

		System.out.println("flag = " + flag + "__________");

		valueStack.set("flag", flag);
		/**
		 * 或得角色列表
		 */
		List<RoleBean> roleList = roleService.findRoleList();
		valueStack.set("roleList", roleList);
		return "edit";
	}

	public String save() throws Exception {
		String admin_select_role = request.getParameter("admin_select_role");
		adminService.saveAdmin(adminBean, admin_select_role);
		return "list";
	}

	public String delete() throws Exception {
		System.out.println("有没有进来");
		String admin_id = request.getParameter("admin_id");
		adminService.deleteAdmin(admin_id);
		return "list";
	}

	// /**
	// * 获得所有的用户
	// *
	// * @return
	// * @throws Exception
	// */
	// public String list() throws Exception {
	// ActionContext context = ActionContext.getContext();
	// ValueStack valueStack = context.getValueStack();
	// List<AdminBean> adminList = adminService.adminlist();
	// int num = 0;
	// for (AdminBean adminBean : adminList) {
	// num++;
	// }
	// valueStack.set("adminList", adminList);
	// valueStack.set("num", num);
	// System.out.println("初始加载");
	// // Gson gson = new Gson();
	// // String jsonString = gson.toJson(adminList);
	// //
	// // HttpServletResponse response = ServletActionContext.getResponse();
	// // response.setContentType("text/plain");
	// // response.getWriter().print(jsonString);
	//
	// return "list";
	// }

	// public String query() throws Exception {
	// String search_id = request.getParameter("search_id");
	// String search_name = request.getParameter("search_name");
	// String search_section = request.getParameter("search_section");
	//
	// search_id = new String(search_id.getBytes("iso-8859-1"), "utf-8");
	// search_name = new String(search_name.getBytes("iso-8859-1"), "utf-8");
	// search_section = new String(search_section.getBytes("iso-8859-1"),
	// "utf-8");
	//
	// List<UserBean> userList = userManageService.queryByLike(search_id,
	// search_name, search_section);
	// ActionContext context = ActionContext.getContext();
	// ValueStack valueStack = context.getValueStack();
	// valueStack.set("userList", userList);
	//
	// return "list";
	// }

	@Override
	public String execute() throws Exception {
		return null;
	}

	@Override
	public AdminBean getModel() {
		return adminBean;
	}

}
