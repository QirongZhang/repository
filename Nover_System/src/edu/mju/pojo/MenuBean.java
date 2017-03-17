package edu.mju.pojo;

public class MenuBean {
	private String menu_id;
	private String menu_name;
	private String menu_href;
	private String menu_target;
	private String parent_id;
	private String grade;
	private String isleaf;

	public String getMenu_id() {
		return menu_id;
	}

	public void setMenu_id(String menu_id) {
		this.menu_id = menu_id;
	}

	public String getMenu_name() {
		return menu_name;
	}

	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}

	public String getMenu_href() {
		return menu_href;
	}

	public void setMenu_href(String menu_href) {
		this.menu_href = menu_href;
	}

	public String getMenu_target() {
		return menu_target;
	}

	public void setMenu_target(String menu_target) {
		this.menu_target = menu_target;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}

}
