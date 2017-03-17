package edu.mju.test;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import edu.mju.dao.FindPageDataDAO;
import edu.mju.pojo.AdminBean;
import edu.mju.util.SpringUtil;

public class test01 extends JdbcDaoSupport {
	
	private DataSource dataSource =(DataSource) SpringUtil.getBean("dataSource");
	
//	@Autowired
//	public void setDataSourceX(DataSource dataSource) {
//		super.setDataSource(dataSource);
//	}
	
	public void insertIntoAdmintable() {
		StringBuffer insertBuffer = new StringBuffer();
		insertBuffer.append("Insert Into ns_user_admin");
		insertBuffer.append("(admin_id,admin_name,password,");
		insertBuffer.append("phone_num ,sex)");
		insertBuffer.append("Values (:admin_id,:admin_name,");
		insertBuffer.append(":password,:phone_num,:sex)");

		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(
				dataSource);
		
//		for (int i = 2; i < 13; i++) {
//			String admin_id = String.valueOf(i);
//			BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(
//					adminBean);
//			template.update(insertBuffer.toString(), parameterSource);
//		}
	}
	
	
	public static void main(String[] args) {
		System.out.println(FindPageDataDAO.findModelCount(null));
	}
}
