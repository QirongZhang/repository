package edu.mju.dao.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.mju.dao.TbAdminDAO;
import edu.mju.pojo.AdminBean;
import edu.mju.util.JdbcUtil;

@Repository(value = "tbAdminDAOImp")
@Transactional(rollbackFor={Exception.class, RuntimeException.class})
public class TbAdminDAOImp extends JdbcDaoSupport implements TbAdminDAO {

	@Autowired
	public void setDataSourceX(DataSource dataSource) {
		super.setDataSource(dataSource);
	}
	
	@Override
	public List<AdminBean> adminList() {
		QueryRunner runner = new QueryRunner();
		String sql = "Select * From ns_user_admin";
		Connection conn = null;
		BeanListHandler<AdminBean> handler = new BeanListHandler<AdminBean>(
				AdminBean.class);
		List<AdminBean> adminList = null;
		try {
			conn = JdbcUtil.getConn();
			adminList = runner.query(conn, sql, handler);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return adminList;
	}

	@Override
	public void delete(String admin_id) {
		StringBuffer deleteSQL = new StringBuffer();
		deleteSQL.append("Delete From ns_user_admin");
		deleteSQL.append(" where admin_id = ?");
		Object[] objArray = { admin_id };
		JdbcTemplate template = this.getJdbcTemplate();
		int delConut = template.update(deleteSQL.toString(),
				objArray);
		System.out.println("影响的行数 = " + delConut);
		
		deleteSQL.setLength(0);
		deleteSQL.append("Delete From ns_user_admin_role");
		deleteSQL.append(" where admin_id = ?");
		int delConut1 = template.update(deleteSQL.toString(),
				objArray);
		System.out.println("影响的行数 = " + delConut1);
	}

	/**
	 * 增加管理员
	 */
	@Override
	public void addAdmin(AdminBean adminBean, String admin_select_role) {

		StringBuffer insertBuffer = new StringBuffer();
		insertBuffer.append("Insert Into ns_user_admin");
		insertBuffer.append("(admin_id,admin_name,password,");
		insertBuffer.append("sex, phone_num ) ");
		insertBuffer.append("Values (:admin_id,:admin_name,");
		insertBuffer.append(":password,:sex,:phone_num)");

		JdbcTemplate template = this.getJdbcTemplate();
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
				this.getDataSource());
		BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(
				adminBean);
		jdbcTemplate.update(insertBuffer.toString(),parameterSource);
		
		String admin_id = adminBean.getAdmin_id();
		
		String[] role_idArray = admin_select_role.split(",");
		for (String role_id : role_idArray) {
			insertBuffer.setLength(0);
			insertBuffer.append("Insert into ns_user_admin_role(");
			insertBuffer.append(" admin_id,role_id");
			insertBuffer.append(") values(");
			insertBuffer.append(" '" + admin_id + "',");
			insertBuffer.append(" '" + role_id + "'");
			insertBuffer.append(")");
			try {
//				int i = 1/0;
				template.update(insertBuffer.toString());
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
	}

	/*
	 * 找到一个adminbean
	 * @see edu.mju.dao.TbAdminDAO#findAdminBeanById(java.lang.String)
	 */
	@Override
	public AdminBean findAdminBeanById(String admin_id) {
		QueryRunner runner = new QueryRunner();
		Connection conn = null;
		String sql = "Select * From ns_user_admin where admin_id = '" + admin_id + "'";
		BeanHandler<AdminBean> handler = new BeanHandler<AdminBean>(
				AdminBean.class);
		AdminBean AdminBean = null;
		try {
			conn = JdbcUtil.getConn();
			AdminBean = (AdminBean) runner.query(conn, sql, handler);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return AdminBean;
	}
	
	@Override
	public void saveAdmin(AdminBean adminBean, String admin_select_roleid) {
		StringBuffer updateSQL = new StringBuffer();
		updateSQL.append("Update ns_user_admin Set");
		updateSQL.append(" admin_name = :admin_name,");
		updateSQL.append(" password = :password,");
		updateSQL.append(" phone_num = :phone_num,");
		updateSQL.append(" sex = :sex");
		updateSQL.append(" Where admin_id = :admin_id ");
		
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(this.getDataSource());
		
		BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(adminBean);
		
		jdbcTemplate.update(updateSQL.toString(), parameterSource);
		
		String admin_id = adminBean.getAdmin_id();
		JdbcTemplate template = this.getJdbcTemplate();
		
		StringBuffer deleteSQL = new StringBuffer();
		deleteSQL.append("Delete From ns_user_admin_role where admin_id = '"
				+ admin_id + "'");
		int deleteRow = template.update(deleteSQL.toString());
		System.out.println(" 删除影响的行数 "+deleteRow);
		
		String[] role_idArray = admin_select_roleid.split(",");
		StringBuffer insertSQL = new StringBuffer();
		for (String role_id : role_idArray) {
			insertSQL.setLength(0);
			insertSQL.append("Insert into ns_user_admin_role(");
			insertSQL.append(" admin_id,role_id");
			insertSQL.append(") values(");
			insertSQL.append(" '" + admin_id + "',");
			insertSQL.append(" '" + role_id + "'");
			insertSQL.append(")");
			template.update(insertSQL.toString());
		}
		

	}


//	public List<AdminBean> queryByLike(String user_id, String user_name,
//			String section) {
//		StringBuffer sql = new StringBuffer();
//		sql.append("Select * From sp_user ");
//		sql.append("Where user_id Like ? ");
//		sql.append("Or user_name Like ? ");
//		sql.append("Or section = ? ");
//		System.out.println(sql.toString());
//		user_id = "%" + user_id + "%";
//		user_name = "%" + user_name + "%";
//		Object[] params = new Object[] { user_id, user_name, section };
//		QueryRunner runner = new QueryRunner();
//		Connection conn = null;
//		List<AdminBean> AdminBeanList = null;
//		BeanListHandler<AdminBean> handler = new BeanListHandler<AdminBean>(
//				AdminBean.class);
//		try {
//			conn = JdbcUtil.getConn();
//			AdminBeanList = runner.query(conn, sql.toString(), handler, params);
//			for (AdminBean AdminBean : AdminBeanList) {
//				System.out.println("AdminBean = " + AdminBean.getAdmin_name());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			DbUtils.closeQuietly(conn);
//		}
//		return AdminBeanList;
//	}
}
