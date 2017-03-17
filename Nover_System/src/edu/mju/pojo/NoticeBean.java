package edu.mju.pojo;

import org.springframework.stereotype.Repository;

@Repository(value = "noticeBean")
public class NoticeBean {
	private int notice_id;
	private String notice_title;
	private String notice_adduser;
	private String notice_addtime;
	private String notice_content;
	private String filename;
	private String filepath;
	private int is_approve;
	private String approve_user;
	private String approve_time;

	public int getNotice_id() {
		return notice_id;
	}

	public void setNotice_id(int notice_id) {
		this.notice_id = notice_id;
	}

	public String getNotice_title() {
		return notice_title;
	}

	public void setNotice_title(String notice_title) {
		this.notice_title = notice_title;
	}

	public String getNotice_adduser() {
		return notice_adduser;
	}

	public void setNotice_adduser(String notice_adduser) {
		this.notice_adduser = notice_adduser;
	}

	public String getNotice_addtime() {
		return notice_addtime;
	}

	public void setNotice_addtime(String notice_addtime) {
		this.notice_addtime = notice_addtime;
	}

	public String getNotice_content() {
		return notice_content;
	}

	public void setNotice_content(String notice_content) {
		this.notice_content = notice_content;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public int getIs_approve() {
		return is_approve;
	}

	public void setIs_approve(int is_approve) {
		this.is_approve = is_approve;
	}

	public String getApprove_user() {
		return approve_user;
	}

	public void setApprove_user(String approve_user) {
		this.approve_user = approve_user;
	}

	public String getApprove_time() {
		return approve_time;
	}

	public void setApprove_time(String approve_time) {
		this.approve_time = approve_time;
	}
}
