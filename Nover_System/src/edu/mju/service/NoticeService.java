package edu.mju.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import edu.mju.dao.NoticeDAO;
import edu.mju.pojo.NoticeBean;
import edu.mju.util.Global;

@Service(value = "noticeService")
public class NoticeService {
	
	@Autowired
	private NoticeDAO noticeDAO;
	
	public void save(NoticeBean noticeBean, File attch, String attchFileName,
			String uploadDir){
		String filename = "";
		String filepath = "";
		int is_approve = 0;
		boolean upload_succ = true;
		if (attchFileName != null && !attchFileName.equals("")) {
			/**
			 * 上传附件
			 */
			filename = attchFileName;
			filepath = Global.getSysTimeStamp() + "_" + attchFileName;
			File destFile = new File(uploadDir + "/" + filepath);
			try {
				FileCopyUtils.copy(attch, destFile);
				upload_succ = true;
			} catch (IOException e) {
				// e.printStackTrace();
				upload_succ = false;
				throw new RuntimeException(e);
			}
		}
		noticeBean.setFilename(filename);
		noticeBean.setFilepath(filepath);
		noticeBean.setIs_approve(is_approve);
		if (upload_succ == true) {
			noticeDAO.save(noticeBean, attch, attchFileName, uploadDir);
		}
	}
	
	public NoticeBean getBeanByID(String notice_id){
		return noticeDAO.getBeanByID(notice_id);
	}
}
