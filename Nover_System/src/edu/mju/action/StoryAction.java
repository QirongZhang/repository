package edu.mju.action;

import java.io.FileInputStream;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;

import edu.mju.pojo.StoryBean;
import edu.mju.service.StoryService;
import edu.mju.util.BaseAction;
import edu.mju.util.FindPropertiesUtil;

@ParentPackage(value = "struts-default")
@Namespace(value = "/")
@Action(value = "StoryAction", results = { 
		@Result(name = "list", location = "/view/nover/story_list.jsp") 
})
public class StoryAction extends BaseAction implements
		ModelDriven<StoryBean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7665532570981021932L;

	private StoryBean storyBean;

	@Autowired
	private StoryService storyService;

	public void setStoryService(StoryService storyService) {
		this.storyService = storyService;
	}

	public String findByID() throws Exception {

		return "read";
	}

	public String findList() throws Exception {
		ActionContext context = ActionContext.getContext();
		ValueStack valueStack = context.getValueStack();
		
		String category_id = null;
		try {
			category_id = request.getParameter("category_id");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("category_id = " + category_id);
		List<StoryBean> storyList = storyService.findList(category_id);
		valueStack.set("storyList", storyList);
		/**
		 * 获取图片的路劲
		 */
//		String imgPath = FindPropertiesUtil.findImg_url();
//		String newImgPath = "";
//		FileInputStream inputStream = null;
//		StringBuffer imgBuffer = new StringBuffer();
//		for (StoryBean storyBean : storyList) {
//			String story_name = storyBean.getStory_name();
//			newImgPath = imgPath+"/"+story_name+".jpg";
//			System.out.println(newImgPath+"----------------------------");
//			inputStream = new FileInputStream(newImgPath);  
//			int i = inputStream.available();  
//			//byte数组用于存放图片字节数据 
//			byte[] buff = new byte[i];  
//			inputStream.read(buff);  
//			
//		}
		
//	        //记得关闭输入流  
//	        inputStream.close();  
		
		
		
		return "list";
	}


	@Override
	public String execute() throws Exception {
		return null;
	}

	@Override
	public StoryBean getModel() {
		if (storyBean == null || storyBean.equals("")) {
			storyBean = new StoryBean();
		}
		return storyBean;
	}

}
