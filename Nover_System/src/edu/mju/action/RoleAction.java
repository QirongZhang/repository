package edu.mju.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;

import edu.mju.dao.FindPageDataDAO;
import edu.mju.pojo.RoleBean;
import edu.mju.service.JudgeService;
import edu.mju.service.MainService;
import edu.mju.service.RoleService;
import edu.mju.util.BaseAction;

@ParentPackage(value = "struts-default")
@Namespace(value = "/")
@Action(value = "RoleAction", results = {
		@Result(name = "list", location = "/view/users/role/list.jsp"),
		@Result(name = "add", location = "/view/users/role/add.jsp"),
		@Result(name = "edit", location = "/view/users/role/edit.jsp") })
public class RoleAction extends BaseAction implements ModelDriven<RoleBean> {

	private static final long serialVersionUID = -1257945078387910593L;

	private RoleBean role;

	public RoleBean getRole() {
		return role;
	}

	public void setRole(RoleBean role) {
		this.role = role;
	}

	@Autowired
	private RoleService roleService;

	@Autowired
	private MainService mainService;

	@Autowired
	private JudgeService judgeService;

	/**
	 * 获取datatable数据
	 */
	// 获取请求次数
	private String draw = "0";
	// 数据起始位置
	private String start;
	// 数据长度
	private String length;
	// 总记录数
	private String recordsTotal = "0";
	// 过滤后记录数
	private String recordsFiltered = "";
	// 定义列名
	private String[] cols = { "role_id", "role_name", "role_remark" };
	// 获取客户端需要那一列排序
	private String orderColumn = "0";
	// 获取排序方式 默认为asc
	private String orderDir = "asc";
	// 获取用户过滤框里的字符
	private String searchValue = "";

	public String getDraw() {
		return draw;
	}

	public void setDraw(String draw) {
		this.draw = draw;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(String recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public String getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(String recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public String[] getCols() {
		return cols;
	}

	public void setCols(String[] cols) {
		this.cols = cols;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public String getOrderDir() {
		return orderDir;
	}

	public void setOrderDir(String orderDir) {
		this.orderDir = orderDir;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public void getAllResultFiles() throws IOException {

		// 获取文件参数
		draw = request.getParameter("draw");
		start = request.getParameter("start");
		length = request.getParameter("length");
		orderColumn = request.getParameter("order[0][column]");
		orderColumn = cols[Integer.parseInt(orderColumn)];
		orderDir = request.getParameter("order[0][dir]");
		searchValue = request.getParameter("search[value]");

		List<String> sArray = new ArrayList<String>();

		if (searchValue != null) {// 判断搜索框是否为空，添加查询条件
			System.out.println("判断搜索框是否为空，添加查询条件");
			sArray.add(" role_id like '%" + searchValue + "%'");
			sArray.add(" role_name like '%" + searchValue + "%'");
			sArray.add(" role_remark like '%" + searchValue + "%'");
		}
		System.out.println("sArray.size() = " + sArray.size());
		String individualSearch = "";
		if (sArray.size() == 1) {
			individualSearch = sArray.get(0);
		} else if (sArray.size() > 1) {
			for (int i = 0; i < sArray.size() - 1; i++) {
				individualSearch += sArray.get(i) + " or ";
			}
			individualSearch += sArray.get(sArray.size() - 1);
		}
		// 获取数据库总记录数
		recordsTotal = "" + roleService.findModelCount(null);
		// recordsTotal = "" + FindPageDataDAO.findModelCount(null);
		System.out.println("recordsTotal = " + recordsTotal);

		String searchSQL = "";
		if (individualSearch != "") {
			searchSQL = individualSearch;
		}

		if (searchValue != null) {
			System.out
					.println("-------------------------------------------------------");
			// recordsFiltered = "" + FindPageDataDAO.findModelCount(searchSQL);
			recordsFiltered = "" + roleService.findModelCount(searchSQL);
			System.out.println(recordsFiltered);
		} else {
			recordsFiltered = recordsTotal;
		}

		// searchSQL+= " order by cast( " + orderColumn + " as int) " +
		// orderDir;

		// 获取data数据
		System.out.println("+++++++++++++++++分割线+++++++++++++++++++++");

		// List<Map<String, Object>> resultList = FindPageDataDAO.pageList(
		// searchSQL, orderColumn, Integer.valueOf(start),
		// Integer.valueOf(length), orderDir);
		List<Map<String, Object>> resultList = roleService.pageList(searchSQL,
				orderColumn, Integer.valueOf(start), Integer.valueOf(length),
				orderDir);

		Gson gson = new Gson();
		String results = null;
		if (resultList != null) {
			// 对数据进行处理
			Map<Object, Object> info = new HashMap<Object, Object>();
			info.put("data", resultList);
			info.put("recordsTotal", recordsTotal);
			info.put("recordsFiltered", recordsFiltered);
			info.put("draw", draw);

			results = gson.toJson(info);
			System.out.println("results = " + results);
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(results);
		response.getWriter().close();
	}

	public String showAddJsp() throws Exception {
		List<Map<String, Object>> menu_treeList = roleService
				.findMenu_TreeList(null, "type_1");
		Gson gson = new Gson();
		String jsonStr = gson.toJson(menu_treeList);
		System.out.println(jsonStr);
		request.setAttribute("jsonStr", jsonStr);
		return "add";
	}

	/**
	 * 保存角色信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addRole() throws Exception {
		boolean flag = false;
		String errorMsg = "";

		/**
		 * 判断角色名称是否存在
		 */
		String role_id = request.getParameter("role_id");
		String role_name = request.getParameter("role_name");
		String role_remark = request.getParameter("role_remark");
		role = new RoleBean();
		role.setRole_id(role_id);
		role.setRole_name(role_name);
		role.setRole_remark(role_remark);

		boolean sign = judgeService.isSamRoleName(role_name);

		if (sign) {
			addFieldError("addMassage", "角色名称已经存在，请重新输入");
			return this.showAddJsp();
		} else {
			try {
				String role_select_menuid = request
						.getParameter("role_select_menuid");
				this.roleService.addRole(role, role_select_menuid);
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
				errorMsg = e.getMessage();
			}
			if (flag == true) {
				return "list";
			} else {
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.println("<script language='javascript'>");
				out.println("window.alert(\"" + errorMsg + "\");");
				out.println("window.history.back();");
				out.println("</script>");
			}
		}
		return "list";
	}

	/**
	 * 编辑角色页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editRole() throws Exception {
		String role_id = request.getParameter("role_id");
		System.out.println("修改错误返回 " + role_id);
		this.role = this.roleService.getRoleBeanById(role_id);

		List<Map<String, Object>> menu_treeList = roleService
				.findMenu_TreeList(role_id, "type_2");
		Gson gson = new Gson();
		String jsonStr = gson.toJson(menu_treeList);
		request.setAttribute("jsonStr", jsonStr);
		/**
		 * 判断角色是否是超级管理员
		 */
		ActionContext context = ActionContext.getContext();
		ValueStack valueStack = context.getValueStack();
		boolean flag = judgeService.isSuperAdmin(null, role_id);
		valueStack.set("flag", flag);
		return "edit";
	}

	public String updateRole() throws Exception {

		String role_id = request.getParameter("role_id");
		String role_name = request.getParameter("role_name");
		String role_remark = request.getParameter("role_remark");
		role = new RoleBean();
		role.setRole_id(role_id);
		role.setRole_name(role_name);
		role.setRole_remark(role_remark);

		String role_select_menuid = request.getParameter("role_select_menuid");
		System.out.println("role_name = " + role_name);
		// 判断是否是相同的名字
		boolean sign = judgeService.isSamRoleName(role_name);
		System.out.println("sign = " + sign);
		if (sign == true) {
			addFieldError("updateMassage", "角色名称已经存在，请重新输入");
			return this.editRole();
		} else {
			roleService.updateRole(role, role_select_menuid);
			return "list";
		}

	}

	/**
	 * 删除角色
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteRole() throws Exception {
		String role_id = request.getParameter("role_id");
		System.out.println("role_id = " + role_id);
		String errorMsg = null;
		
		//判断角色是否是在使用
		boolean sign = judgeService.roleIsUse(role_id);
		//判断是否是超级管理员
		boolean judge = judgeService.isSuperAdmin(null, role_id);
		if (judge == true) {
			if (sign == false) {
				roleService.deleteRole(role_id);
				System.out.println("是执行到这里了吗");
				return "list";
			} else {
				/**
				 * 提示功能不行
				 */
				addFieldError("deleteMessage", "角色使用中不能被删除!");
				return "list";
			}
		}else {
			addFieldError("deleteMessage", "您不是超级管理员，不能删除角色!");
			return "list";
		}
	}

	
//	if (role_id == null || role_id.equals("")) {
//		errorMsg = "角色编码参数为空值，不能执行删除的操作";
//		response.setContentType("text/html");
//		PrintWriter out = response.getWriter();
//		out.println("<script language='javascript'>");
//		out.println("window.alert(\"" + errorMsg + "\");");
//		out.println("window.history.back();");
//		out.println("</script>");
//	} else {
//		boolean flag = false;
//		try {
//			
//			flag = true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			errorMsg = e.getMessage();
//		}
//
//		if (flag == true) {
//			return "list";
//		} else {
//			response.setContentType("text/html");
//			PrintWriter out = response.getWriter();
//			out.println("<script language='javascript'>");
//			out.println("window.alert(\"" + errorMsg + "\");");
//			out.println("window.history.back();");
//			out.println("</script>");
//		}
//	}

	
	
	
	
	
	
//	 /**
//	 * 获得角色列表
//	 * @return
//	 * @throws Exception
//	 */
//	 public String roleList() throws Exception {
//	 ActionContext context = ActionContext.getContext();
//	 ValueStack valueStack = context.getValueStack();
//	 /**
//	 * 判断是否是超级管理员
//	 */
//	 Map session = context.getSession();
//	 UserBean user = (UserBean) session.get("user");
//	 String user_id = user.getUser_id();
//	 System.out.println("user_id = " + user_id);
//	 boolean flag = roleService.isAdmin(user_id);
//	 valueStack.set("flag", flag);
//	 List<RoleBean> roleList =roleService.getRoleList();
//	 valueStack.set("roleList", roleList);
//	 return "list";
//	 }

	@Override
	public String execute() throws Exception {
		return null;
	}

	@Override
	public RoleBean getModel() {
		return role;
	}

}
