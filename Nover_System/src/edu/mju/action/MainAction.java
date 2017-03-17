package edu.mju.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.google.gson.Gson;

import edu.mju.service.MainService;
import edu.mju.util.BaseAction;

@ParentPackage(value = "struts-default")
@Namespace(value = "/")
@Action(value = "MainAction", results = { 
		@Result(name = "index-admin", location = "/view/users/menu-tree.jsp")
})
public class MainAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	@Resource(name = "mainService")
	private MainService mainService;
	
	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}
	
	@Override
	public String execute() throws Exception {
		/**
		 * 从Session中取出登录的用户ID号。
		 */
		System.out.println("动态加载树");
		
//		String admin_id = "1";
		String admin_id  = request.getParameter("admin_id");
		System.out.println("admin_id = " + admin_id);
		
		List<Map<String, Object>> menu_treeList = mainService.getMenu_TreeList(admin_id);
		Gson gson = new Gson();
		String jsonString = gson.toJson(menu_treeList);
		request.setAttribute("jsonString", jsonString);

		return "index-admin";
	}
}
