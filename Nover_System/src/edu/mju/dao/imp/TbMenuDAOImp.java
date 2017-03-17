package edu.mju.dao.imp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.springframework.stereotype.Repository;

import edu.mju.dao.TbMenuDAO;
import edu.mju.pojo.MenuBean;
import edu.mju.util.JdbcUtil;

@Repository(value = "tbMenuDAOImp")
public class TbMenuDAOImp implements TbMenuDAO {

	@Override
	public void addMenu(MenuBean menuBean, String select_parent_id) {
		Connection conn = null;
		Statement stmt = null;

		String menu_id = select_parent_id + menuBean.getMenu_id();
		String menu_name = menuBean.getMenu_name();
		String menu_href = menuBean.getMenu_href();
		String menu_target = menuBean.getMenu_target();
		String grade = menuBean.getGrade();
		String isleaf = menuBean.getIsleaf();

		StringBuffer insertSQL = new StringBuffer();
		insertSQL.append("Insert Into sp_menu");
		insertSQL.append("(menu_id,menu_name,menu_href,");
		insertSQL.append("menu_target,parent_id,grade,");
		insertSQL.append("isleaf)");
		insertSQL.append(" values(");
		insertSQL.append("'" + menu_id + "',");
		insertSQL.append("'" + menu_name + "',");
		insertSQL.append("'" + menu_href + "',");
		insertSQL.append("'" + menu_target + "',");
		insertSQL.append("'" + select_parent_id + "',");
		insertSQL.append("'" + grade + "' ,");
		insertSQL.append("'" + isleaf + "'");
		insertSQL.append(")");
		System.out.println(insertSQL.toString());
		try {
			conn = JdbcUtil.getConn();
			stmt = conn.createStatement();
			/**
			 * 1：插入到菜单表
			 */
			stmt.executeUpdate(insertSQL.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}
	}

	/**
	 * 判断是否为相同的菜单名
	 * @param menu_name
	 * @return
	 */
	@Override
	public boolean isSameMenuName(String menu_name) {
		StringBuffer sql = new StringBuffer();
		sql.append("Select * From sp_menu ");
		sql.append("Where menu_name = '" + menu_name + "'");
		QueryRunner runner = new QueryRunner();
		Connection conn = null;
		boolean flag = false;
		List<Map<String, Object>> menuList = new ArrayList<Map<String, Object>>();
		try {
			conn = JdbcUtil.getConn();
			menuList = runner.query(conn, sql.toString(), new MapListHandler());
			if (!menuList.isEmpty() || menuList.size() != 0) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return flag;
	}

	// @Override
	// public List<Map<String, Object>> findMenu() {
	// List<Map<String, Object>> menuList = new ArrayList<Map<String,
	// Object>>();
	// List<Integer> rowList = new ArrayList<Integer>();
	// Map<String, Integer> id_rowMap = new LinkedHashMap<String, Integer>();
	//
	// Connection conn = null;
	// Statement stmt = null;
	// ResultSet rs = null;
	// String sql = "Select * From ns_user_menu order by menu_id asc";
	//
	// try {
	// conn = JdbcUtil.getConn();
	// stmt = conn.createStatement();
	// rs = stmt.executeQuery(sql);
	// int rowNum = 1;
	// while (rs.next()) {
	// String menu_id = rs.getString("menu_id");
	// String menu_name = rs.getString("menu_name");
	//
	// String menu_href = rs.getString("menu_href");
	// String menu_target = rs.getString("menu_target");
	// String parent_id = rs.getString("parent_id");
	// int grade = rs.getInt("grade");
	// int isLeaf = rs.getInt("isleaf");
	// String iden_str = "";
	// for (int i = 1; i < grade; i++) {
	// iden_str = iden_str
	// + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	// }
	// menu_name = iden_str + menu_name;
	//
	// id_rowMap.put(menu_id, rowNum);
	// rowNum++;
	// if (parent_id.equals("0")) {
	// rowList.add(0);
	// } else {
	// int tempNo = id_rowMap.get(parent_id);
	// rowList.add(tempNo);
	// }
	//
	// Map<String, Object> rowMap = new HashMap<String, Object>();
	//
	// rowMap.put("menu_id", menu_id);
	// rowMap.put("menu_name", menu_name);
	// rowMap.put("menu_href", menu_href);
	// rowMap.put("menu_target", menu_target);
	// rowMap.put("parentid", parent_id);
	// rowMap.put("grade", grade);
	// rowMap.put("tempLeaf", isLeaf);
	//
	// menuList.add(rowMap);
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return menuList;
	// }

	// @Override
	// public void delMenu(String menu_id) {
	// Connection conn = null;
	// Statement stmt = null;
	// String delete = "";
	// delete = "Delete From sp_menu where menu_id = '" + menu_id + "'";
	// try {
	// conn = JdbcUtil.getConn();
	// stmt = conn.createStatement();
	// stmt.executeUpdate(delete);
	// } catch (Exception e) {
	// throw new RuntimeException(e);
	// } finally {
	// DbUtils.closeQuietly(stmt);
	// DbUtils.closeQuietly(conn);
	// }
	// }
	//
	// @Override
	// public void saveMenu(MenuBean menuBean, String select_parent_id) {
	// Connection conn = null;
	// Statement stmt = null;
	//
	// String menu_id = select_parent_id + menuBean.getMenu_id();
	// String menu_name = menuBean.getMenu_name();
	// String menu_href = menuBean.getMenu_href();
	// String menu_target = menuBean.getMenu_target();
	// String grade = menuBean.getGrade();
	// String isleaf = menuBean.getIsleaf();
	//
	// StringBuffer updateSQL = new StringBuffer();
	// updateSQL.append("Update sp_menu Set");
	// updateSQL.append(" menu_name = '" + menu_name + "'");
	// updateSQL.append(" menu_href = '" + menu_href + "',");
	// updateSQL.append(" menu_target = '" + menu_target + "',");
	// updateSQL.append(" select_parent_id = '" + select_parent_id + "',");
	// updateSQL.append(" grade = '" + grade + "',");
	// updateSQL.append(" isleaf = '" + isleaf + "',");
	// updateSQL.append(" where menu_id = '" + menu_id + "'");
	// try {
	// conn = JdbcUtil.getConn();
	// stmt = conn.createStatement();
	// /**
	// * 1：更新到角色表
	// */
	// stmt.executeUpdate(updateSQL.toString());
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// DbUtils.closeQuietly(conn);
	// }
	//
	// }
	//

	// @Override
	// public List<Map<String, Object>> getMenu_treeList(String userid) {
	// List<Map<String, Object>> menuList = new ArrayList<Map<String,
	// Object>>();
	// List<Map<String, Object>> dataList = new ArrayList<Map<String,
	// Object>>();
	//
	// QueryRunner runner = new QueryRunner();
	// StringBuffer querySQL = new StringBuffer();
	//
	// querySQL.append("Select * From sp_menu ");
	// querySQL.append(" where menu_id in (");
	// querySQL.append("	Select distinct menu_id ");
	// querySQL.append(" 		From sp_role_menu");
	// querySQL.append("		where role_id in (");
	// querySQL.append("	Select role_id From sp_user_role ");
	// querySQL.append("		where user_id = '" + userid + "'))");
	// querySQL.append("order by menu_id asc");
	//
	// Connection conn = null;
	// try {
	// conn = JdbcUtil.getConn();
	// dataList = runner.query(conn, querySQL.toString(),
	// new MapListHandler());
	// for (Map<String, Object> rowMap : dataList) {
	// Map<String, Object> treeCodeMap = new LinkedHashMap<>();
	// String menu_id = (String) rowMap.get("menu_id");
	// String menu_name = (String) rowMap.get("menu_name");
	// String parent_id = (String) rowMap.get("parent_id");
	// String isLeaf = (String) rowMap.get("isLeaf");
	//
	// treeCodeMap.put("id", menu_id);
	// treeCodeMap.put("name", menu_name);
	// treeCodeMap.put("pId", parent_id);
	// treeCodeMap.put("isLeaf", isLeaf);
	// if (parent_id.equals("0")) {
	// treeCodeMap.put("open", true);
	// }
	//
	// if (isLeaf.equals("1")) {
	// String menu_href = (String) rowMap.get("menu_href");
	// treeCodeMap.put("menu_href", menu_href);
	// }
	//
	// menuList.add(treeCodeMap);
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// DbUtils.closeQuietly(conn);
	//
	// }
	// return menuList;
	// }
	//
	// @Override
	// public MenuBean findMenuBeanById(String menu_id) {
	// QueryRunner runner = new QueryRunner();
	// Connection conn = null;
	// String sql = "Select * From sp_menu where menu_id = '" + menu_id + "'";
	// BeanHandler<MenuBean> handler = new BeanHandler<MenuBean>(
	// MenuBean.class);
	// MenuBean menuBean = null;
	// try {
	// conn = JdbcUtil.getConn();
	// menuBean = runner.query(conn, sql, handler);
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// DbUtils.closeQuietly(conn);
	// }
	// return menuBean;
	// }
	//

	//
	// public boolean MenuIsUse(String menu_id) {
	// StringBuffer sql = new StringBuffer();
	// sql.append("Select * From sp_role ");
	// sql.append("	Where role_id in(");
	// sql.append("		Select role_id From sp_role_menu ");
	// sql.append("				Where menu_id = " + menu_id + ") ");
	// QueryRunner runner = new QueryRunner();
	// Connection conn = null;
	// boolean flag = false;
	// List<Map<String, Object>> roleList = new ArrayList<Map<String,
	// Object>>();
	// try {
	// conn = JdbcUtil.getConn();
	// roleList = (List<Map<String, Object>>) runner.query(conn, sql.toString(),
	// new MapListHandler());
	// if (!roleList.equals("") || roleList != null) {
	// flag = true;
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// DbUtils.closeQuietly(conn);
	// }
	// return flag;
	// }

}
