package edu.mju.action;

import java.io.File;
import java.io.InputStream;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;

import edu.mju.pojo.NoticeBean;
import edu.mju.service.NoticeService;
import edu.mju.util.BaseAction;
import edu.mju.util.FindPropertiesUtil;
import edu.mju.util.Global;

@ParentPackage(value = "struts-default")
@Namespace(value = "/")
@Action(value = "NoticeAction", results = {
		@Result(name = "list", location = "/view/users/notice/list.jsp"),
		@Result(name = "add", location = "/view/users/notice/add.jsp"),
		@Result(name = "edit", location = "/view/users/notice/edit.jsp") })
public class NoticeAction extends BaseAction implements ModelDriven<NoticeBean> {
	
	private static final long serialVersionUID = 6290967612218961606L;

	@Autowired
	private NoticeService noticeService;

	private NoticeBean noticeBean;

	/**
	 * 上传附件时，使用
	 */
	private File attch;
	private String attchFileName;

	public File getAttch() {
		return attch;
	}

	public void setAttch(File attch) {
		this.attch = attch;
	}

	public String getAttchFileName() {
		return attchFileName;
	}

	public void setAttchFileName(String attchFileName) {
		this.attchFileName = attchFileName;
	}

	/**
	 * 公告添加的页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		/**
		 * 将时间默认为当前的系统时间
		 */
		ActionContext context = ActionContext.getContext();
		String notice_addtime = Global.getSysDate();
		context.put("notice_addtime", notice_addtime);
		return "add";
	}

	public String save() throws Exception {
		String uploadDir = FindPropertiesUtil.findUpload_path();
		try {
			this.noticeService.save(this.noticeBean, this.attch,
					this.attchFileName, uploadDir);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}

	public String edit() throws Exception {
		ActionContext context = ActionContext.getContext();
		ValueStack valueStack = context.getValueStack();
		String notice_id = "14";
		this.noticeBean = this.noticeService.getBeanByID(notice_id);
		if (this.noticeBean != null) {

			valueStack.push(this.noticeBean);
		}
		return "edit";
	}
	
	@Override
	public NoticeBean getModel() {
		if (noticeBean == null) {
			noticeBean = new NoticeBean();
		}
		return noticeBean;
	}

	@Override
	public String execute() throws Exception {
		return null;
	}
}
