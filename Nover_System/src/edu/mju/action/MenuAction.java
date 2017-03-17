package edu.mju.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;

import edu.mju.pojo.MenuBean;
import edu.mju.service.MenuService;
import edu.mju.service.RoleService;
import edu.mju.util.BaseAction;
import edu.mju.util.JdbcUtil;

@ParentPackage(value = "struts-default")
@Namespace(value = "/")
@Action(value = "MenuAction", results = {
		@Result(name = "list", location = "/view/users/menu/list.jsp"),
		@Result(name = "add", location = "/view/users/menu/add.jsp"),
		@Result(name = "edit", location = "/view/users/menu/edit.jsp") })
public class MenuAction extends BaseAction implements ModelDriven<MenuBean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MenuBean menuBean;

	public MenuBean getMenu() {
		return menuBean;
	}

	public void setMenu(MenuBean menu) {
		this.menuBean = menu;
	}

	MenuService menuService = new MenuService();
	RoleService roleService = new RoleService();

	public String list() throws Exception {
		ActionContext context = ActionContext.getContext();
		//ValueStack valueStack = context.getValueStack();

		List<Map<String, Object>> menuList = new ArrayList<Map<String, Object>>();
		List<Integer> rowList = new ArrayList<Integer>();
		Map<String, Integer> id_rowMap = new LinkedHashMap<String, Integer>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "Select * From ns_user_menu order by menu_id asc";

		try {
			conn = JdbcUtil.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			int rowNum = 1;
			while (rs.next()) {
				String menu_id = rs.getString("menu_id");
				String menu_name = rs.getString("menu_name");

				String menu_href = rs.getString("menu_href");
				String menu_target = rs.getString("menu_target");
				String parent_id = rs.getString("parent_id");
				int grade = rs.getInt("grade");
				int isLeaf = rs.getInt("isleaf");
				String iden_str = "";
				for (int i = 1; i < grade; i++) {
					iden_str = iden_str
							+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				}
				menu_name = iden_str + menu_name;

				id_rowMap.put(menu_id, rowNum);
				rowNum++;
				if (parent_id.equals("0")) {
					rowList.add(0);
				} else {
					int tempNo = id_rowMap.get(parent_id);
					rowList.add(tempNo);
				}

				Map<String, Object> rowMap = new HashMap<String, Object>();

				rowMap.put("menu_id", menu_id);
				rowMap.put("menu_name", menu_name);
				rowMap.put("menu_href", menu_href);
				rowMap.put("menu_target", menu_target);
				rowMap.put("parentid", parent_id);
				rowMap.put("grade", grade);
				rowMap.put("tempLeaf", isLeaf);

				menuList.add(rowMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/**
		 * 灏唕owList杞负json鐨勫瓧绗︿覆銆�
		 */
		Gson gson = new Gson();
		String jsonStr = gson.toJson(rowList);

		System.out.println(jsonStr);
		context.put("jsonStr", jsonStr);
		context.put("menuList", menuList);

		return "list";
	}

	public String add() throws Exception {
		Gson gson = null;
		String jsonStr = null;
		List<Map<String, Object>> menu_treeList = roleService
				.findMenu_TreeList(null, "type_1");
		if (menuBean == null) {
			gson = new Gson();
			jsonStr = gson.toJson(menu_treeList);
			request.setAttribute("jsonStr", jsonStr);
			System.out.println(jsonStr);
			return "add";
		} else {
			String select_parent_id = request.getParameter("select_parent_id");
			/**
			 * 判断菜单名称是否存在
			 */
			String menu_name = menuBean.getMenu_name();
			boolean flag = menuService.isSameMenuName(menu_name);
			if ( flag) {
				addFieldError("addMassage", "菜单名称已经存在，请重新输入");
				gson = new Gson();
				jsonStr = gson.toJson(menu_treeList);
				request.setAttribute("jsonStr", jsonStr);
				return "add";
			} else {
				menuService.addMenu(menuBean, select_parent_id);
				return this.list();
			}
		}

	}
	
	
	
	
	
//	public String del() throws Exception {
//		String menu_id = request.getParameter("menu_id");
//		String errorMsg = null;
//		/**
//		 * 判断菜单是否是在使用
//		 */
//		boolean sign = menuService.MenuIsUse(menu_id);
//		if (!sign) {
//			if (menu_id == null || menu_id.equals("")) {
//				errorMsg = "菜单编码参数为空值，不能执行删除的操作";
//				response.setContentType("text/html");
//				PrintWriter out = response.getWriter();
//				out.println("<script language='javascript'>");
//				out.println("window.alert(\"" + errorMsg + "\");");
//				out.println("window.history.back();");
//				out.println("</script>");
//			} else {
//				boolean flag = false;
//				try {
//					menuService.delMenu(menu_id);
//					flag = true;
//				} catch (Exception e) {
//					e.printStackTrace();
//					errorMsg = e.getMessage();
//				}
//
//				if (flag == true) {
//					return this.list();
//				} else {
//					response.setContentType("text/html");
//					PrintWriter out = response.getWriter();
//					out.println("<script language='javascript'>");
//					out.println("window.alert(\"" + errorMsg + "\");");
//					out.println("window.history.back();");
//					out.println("</script>");
//				}
//			}
//		} else {
//			addFieldError("deleteMessage", "菜单使用中不能被删除!");
//			return this.list();
//		}
//		return NONE;
//	}
//
//	public String edit() throws Exception {
//		String menu_id = request.getParameter("menu_id");
//		System.out.println("menu_id = " + menu_id);
//		this.menuBean = menuService.findMenuBeanById(menu_id);
//		/**
//		 * 获得菜单树
//		 */
//		Gson gson = new Gson();
//		List<Map<String, Object>> menu_treeList = roleService
//				.getMenu_TreeList(null, "type_1");
//		String jsonStr = gson.toJson(menu_treeList);
//		request.setAttribute("jsonStr", jsonStr);
//		return "edit";
//	}
//
//	public String save() throws Exception {
//		String select_parent_id = request.getParameter("select_parent_id");
//		/**
//		 * 判断菜单名称是否存在
//		 */
//		String menu_name = menuBean.getMenu_name();
//		boolean flag = menuService.isSameMenuName(menu_name);
//		if (flag) {
//			addFieldError("addMassage", "菜单名称已经存在，请重新输入");
//			return this.list();
//		} else {
//			menuService.saveMenu(menuBean, select_parent_id);
//			return this.list();
//		}
//	}
//
//	public String getMenuTree() throws Exception {
//		String user_id = request.getParameter("user_id");
//		List<Map<String, Object>> menuList = menuService
//				.getMenu_treeList(user_id);
//		Gson gson = new Gson();
//		String jsonString = gson.toJson(menuList);
//		request.setAttribute("jsonString", jsonString);
//
//		return "get_menu_tree";
//	}

	@Override
	public String execute() throws Exception {
		return null;
	}

	@Override
	public MenuBean getModel() {
		return menuBean;
	}
}
