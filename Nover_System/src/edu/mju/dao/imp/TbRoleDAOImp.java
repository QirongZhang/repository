package edu.mju.dao.imp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.mju.dao.TbRoleDAO;
import edu.mju.pojo.RoleBean;
import edu.mju.util.JdbcUtil;

@Repository(value = "tbRoleDAOImp")
@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
public class TbRoleDAOImp extends JdbcDaoSupport implements TbRoleDAO {

	@Autowired
	public void setDataSourceX(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	/**
	 * 获得角色列表
	 */
	@Override
	public List<RoleBean> findRoleList() {
		QueryRunner runner = new QueryRunner();
		Connection conn = null;
		String sql = "Select * From ns_user_role order by role_id desc";
		BeanListHandler<RoleBean> handler = new BeanListHandler<RoleBean>(
				RoleBean.class);
		List<RoleBean> roleList = null;
		try {
			conn = JdbcUtil.getConn();
			roleList = runner.query(conn, sql, handler);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return roleList;
	}

	@Override
	public List<Map<String, Object>> pageList(String searchSQL,
			String orderColumn, int start, int length, String orderDir) {
		QueryRunner runner = new QueryRunner();
		StringBuffer pageSQL = new StringBuffer();

		pageSQL.append("select top " + length + " * from ns_user_role where ( ");
		pageSQL.append("role_id not in( ");
		pageSQL.append("select top " + start + " role_id from ns_user_role ");
		pageSQL.append("order by  " + orderColumn + "  " + orderDir + ")) ");
		if (searchSQL != null && searchSQL != "") {
			pageSQL.append(" and ");
			pageSQL.append("(" + searchSQL + ")");
		}
		pageSQL.append("order by " + orderColumn + " " + orderDir + "");

		System.out.println(pageSQL.toString());

		Connection conn = null;
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		MapListHandler handler = new MapListHandler();
		try {
			conn = JdbcUtil.getConn();
			dataList = runner.query(conn, pageSQL.toString(), handler);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return dataList;
	}

	@Override
	public String findModelCount(String searchSQL) {

		QueryRunner runner = new QueryRunner();
		StringBuffer pageSQL = new StringBuffer();
		pageSQL.append("select count(*) as num from ns_user_role ");
		if (searchSQL != null) {
			pageSQL.append(" where ");
			pageSQL.append(searchSQL);
		}
		System.out.println(pageSQL.toString());

		Connection conn = null;
		Map<String, Object> count = null;
		String num = null;
		try {
			conn = JdbcUtil.getConn();
			count = runner.query(conn, pageSQL.toString(), new MapHandler());
			num = count.get("num") + "";
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return num;
	}

	@Override
	public List<Map<String, Object>> findMenu_treeList(String role_id,
			String view_type) {
		List<Map<String, Object>> menu_treeList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

		QueryRunner runner = new QueryRunner();
		Connection conn = null;
		String sql = "";
		if (view_type.equals("type_1")) {
			sql = "Select * From ns_user_menu order by menu_id asc";
		} else if (view_type.equals("type_2")) {
			sql = " Select a.*,b.menu_id as new_menu_id From ns_user_menu a";
			sql = sql + " left outer join (";
			sql = sql + "	Select * From ns_user_role_menu where role_id = '"
					+ role_id + "'";
			sql = sql + " ) b";
			sql = sql + " on a.menu_id = b.menu_id";
			sql = sql + " order by a.menu_id asc";
		}
		System.out.println("sql = " + sql);
		Set<String> role_menuSet = this.findRoleMenuSetByID(role_id);

		try {
			conn = JdbcUtil.getConn();
			dataList = runner.query(conn, sql, new MapListHandler());
			for (Map<String, Object> rowMap : dataList) {
				Map<String, Object> treeNodeMap = new LinkedHashMap<String, Object>();
				String menu_id = String.valueOf(rowMap.get("menu_id"));
				String menu_name = String.valueOf(rowMap.get("menu_name"));
				String parent_id = String.valueOf(rowMap.get("parent_id"));

				treeNodeMap.put("id", menu_id);
				treeNodeMap.put("name", menu_name);
				treeNodeMap.put("pId", parent_id);
				if (parent_id.equals("0")) {
					treeNodeMap.put("open", true);
				}
				if (view_type != null && view_type.equals("type_1")) {
					// if (role_menuSet.contains(menu_id)) {
					// treeNodeMap.put("checked", true);
					// }
				} else if (view_type != null && view_type.equals("type_2")) {
					if (role_menuSet.contains(menu_id)) {
						treeNodeMap.put("checked", true);
					}
					// String new_menu_id =
					// String.valueOf(rowMap.get("b.menu_id"));
					// System.out.println("b.menu_id = " + new_menu_id);
					// if (new_menu_id != null && !new_menu_id.equals("")
					// && !new_menu_id.equals("null")) {
					// treeNodeMap.put("checked", true);
					// }
				}
				menu_treeList.add(treeNodeMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return menu_treeList;
	}

	public Set<String> findRoleMenuSetByID(String role_id) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "Select menu_id From ns_user_role_menu where role_id = "
				+ role_id + "";

		Set<String> menuSet = new HashSet<String>();

		try {
			conn = JdbcUtil.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String menu_id = rs.getString("menu_id");
				menuSet.add(menu_id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(stmt);
			DbUtils.closeQuietly(conn);
		}
		return menuSet;
	}

	/**
	 * 保存角色信息
	 */
	@Override
	public void addRole(RoleBean roleBean, String role_select_menuid) {

		String role_id = roleBean.getRole_id();

		StringBuffer insertSQL = new StringBuffer();
		insertSQL.append("Insert Into ns_user_role");
		insertSQL.append("(role_id,role_name,");
		insertSQL.append("role_remark)");
		insertSQL.append(" values(");
		insertSQL.append(":role_id,:role_name,:role_remark )");
		JdbcTemplate template = this.getJdbcTemplate();
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
				this.getDataSource());
		BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(
				roleBean);
		jdbcTemplate.update(insertSQL.toString(), parameterSource);

		String[] menu_idArray = role_select_menuid.split(",");
		for (String menu_id : menu_idArray) {
			insertSQL.setLength(0);
			insertSQL.append("Insert into ns_user_role_menu(");
			insertSQL.append(" role_id,menu_id");
			insertSQL.append(") values(");
			insertSQL.append(" '" + role_id + "',");
			insertSQL.append(" '" + menu_id + "'");
			insertSQL.append(")");
			int insertRow = template.update(insertSQL.toString());
			System.out.println("影响的行数 = " + insertRow);
		}
	}

	/**
	 * 根据user_id 获得角色
	 * 
	 * @param role_id
	 * @return
	 */
	@Override
	public RoleBean getRoleBeanById(String role_id) {
		QueryRunner runner = new QueryRunner();
		Connection conn = null;
		String sql = "Select * From ns_user_role where role_id = " + role_id
				+ "";
		BeanHandler<RoleBean> handler = new BeanHandler<RoleBean>(
				RoleBean.class);
		RoleBean roleBean = null;
		try {
			conn = JdbcUtil.getConn();
			roleBean = runner.query(conn, sql, handler);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return roleBean;
	}

	@Override
	public void updateRole(RoleBean roleBean, String role_select_menuid) {
		String role_id = roleBean.getRole_id();

		StringBuffer updateSQL = new StringBuffer();
		updateSQL.append("Update ns_user_role Set");
		updateSQL.append(" role_name =  :role_name ,");
		updateSQL.append(" role_remark =  :role_remark ");
		updateSQL.append(" where role_id =  :role_id ");
		JdbcTemplate template = this.getJdbcTemplate();
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
				this.getDataSource());
		BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(
				roleBean);
		/**
		 * 1：更新到角色表
		 */
		jdbcTemplate.update(updateSQL.toString(), parameterSource);
		StringBuffer deleteSQL = new StringBuffer();
		deleteSQL.append("Delete From ns_user_role_menu where role_id = '"
				+ role_id + "'");
		/**
		 * 2：将角色菜单表中的数据删除。
		 */
		int deleteRow = template.update(deleteSQL.toString());
		System.out.println("删除的行数 = " + deleteRow);
		/**
		 * 3：插入到角色菜单表
		 */
		String[] menu_idArray = role_select_menuid.split(",");
		StringBuffer insertSQL = new StringBuffer();
		for (String menu_id : menu_idArray) {
			insertSQL.setLength(0);
			insertSQL.append("Insert into ns_user_role_menu(");
			insertSQL.append(" role_id,menu_id");
			insertSQL.append(") values(");
			insertSQL.append(" '" + role_id + "',");
			insertSQL.append(" '" + menu_id + "'");
			insertSQL.append(")");
			template.update(insertSQL.toString());
		}
	}

	@Override
	public void deleteRole(String role_id) {
		String delete_1 = "";
		String delete_2 = "";
		delete_1 = "Delete From ns_user_role where role_id = '" + role_id + "'";
		delete_2 = "Delete From ns_user_role_menu where role_id = '" + role_id + "'";
		JdbcTemplate template = this.getJdbcTemplate();
		template.update(delete_1);
		template.update(delete_2);
		
	}
}
