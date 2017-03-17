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

import edu.mju.pojo.ChapterBean;
import edu.mju.pojo.StoryBean;
import edu.mju.service.ChapterService;
import edu.mju.service.StoryService;
import edu.mju.util.BaseAction;
import edu.mju.util.FindFileUtil;
import edu.mju.util.FindPropertiesUtil;

@ParentPackage(value = "struts-default")
@Namespace(value = "/")
@Action(value = "ChapterAction", results = { 
		@Result(name = "read", location = "/view/nover/read-container.jsp"),
		@Result(name = "list", location = "/view/nover/chapter_list.jsp") 
})
public class ChapterAction extends BaseAction implements ModelDriven<ChapterBean> {

	private static final long serialVersionUID = 7665532570981021932L;
	
	private ChapterBean chapterBean;
	
//	public ChapterBean getChapterBean() {
//		return chapterBean;
//	}
//
//	public void setChapterBean(ChapterBean chapterBean) {
//		this.chapterBean = chapterBean;
//	}

	@Autowired
	private ChapterService chapterService;
	
	public void setChapterService(ChapterService chapterService) {
		this.chapterService = chapterService;
	}
	
	private StoryService storyService;
	
	@Autowired
	public void setStoryService(StoryService storyService) {
		this.storyService = storyService;
	}
	
	public String findByID() throws Exception {
		ActionContext context = ActionContext.getContext();
		ValueStack valueStack = context.getValueStack();
		String chapter_id = request.getParameter("chapter_id");
 		this.chapterBean = chapterService.findById(chapter_id);
		System.out.println("chapter_id = "+chapterBean.getChapter_id());
		
		/**
		 * 获得文件读取路径
		 */
		StoryBean storyBean = storyService.findById(chapter_id);
		valueStack.set("storyBean", storyBean);
		
		String story_name = storyBean.getStory_name();
		
		System.out.println("story_name = " + story_name);
		
		String parentDir = FindPropertiesUtil.findNover_url();
		System.out.println("parentDir = " + parentDir);
		String chapterName = chapterBean.getChapter_name();
		String filePath = parentDir + "/" +story_name+"/"+ chapterName + ".txt";
		System.out.println("filePath = " + filePath);
		
		StringBuffer story = null;
		story = FindFileUtil.readTXTFile(filePath);
		
		valueStack.set("story", story);
		
		return "read";
	}
	
	public String findList() throws Exception {
		ActionContext context = ActionContext.getContext();
		ValueStack valueStack = context.getValueStack();
		String story_id = request.getParameter("story_id");
		List<ChapterBean> chapterList = chapterService.findList(story_id);
		valueStack.set("chapterList", chapterList);
		return "list";
	}
	
	@Override
	public String execute() throws Exception {
		return null;
	}

	@Override
	public ChapterBean getModel() {
//		if (chapterBean == null || chapterBean.equals("")) {
//			chapterBean = new ChapterBean();
//		}
		return chapterBean;
	}

}
