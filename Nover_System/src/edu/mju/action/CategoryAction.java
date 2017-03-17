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

import edu.mju.pojo.CategoryBean;
import edu.mju.pojo.ChapterBean;
import edu.mju.service.CategoryService;
import edu.mju.service.ChapterService;
import edu.mju.util.BaseAction;
import edu.mju.util.FindFileUtil;
import edu.mju.util.FindPropertiesUtil;

@ParentPackage(value = "struts-default")
@Namespace(value = "/")
@Action(value = "CategoryAction", results = { 
		@Result(name = "list", location = "/view/nover/caretory_list.jsp") 
})
public class CategoryAction extends BaseAction implements ModelDriven<CategoryBean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7665532570981021932L;
	
	private CategoryBean categoryBean;
	
	@Autowired
	private CategoryService categoryService;
	
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	public String findByID() throws Exception {
		
		
		return "read";
	}
	
	public String findList() throws Exception {
		List<CategoryBean> categoryList = categoryService.findList();
		ActionContext context = ActionContext.getContext();
		ValueStack valueStack = context.getValueStack();
		valueStack.set("categoryList", categoryList);
		return "list";
		
	}
	
	@Override
	public String execute() throws Exception {
		return null;
	}

	@Override
	public CategoryBean getModel() {
		if (categoryBean == null || categoryBean.equals("")) {
			categoryBean = new CategoryBean();
		}
		return categoryBean;
	}

}
