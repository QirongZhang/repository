package edu.mju.dao;

import java.io.File;

import edu.mju.pojo.NoticeBean;

public interface NoticeDAO {

	public void save(NoticeBean noticeBean, File attch, String attchFileName,
			String uploadDir);

	public NoticeBean getBeanByID(String notice_id);

}
