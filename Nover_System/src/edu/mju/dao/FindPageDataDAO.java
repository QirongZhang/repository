package edu.mju.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import edu.mju.util.JdbcUtil;

public class FindPageDataDAO {
	public static List<Map<String, Object>> pageList(String searchSQL,String orderColumn,
			int start, int length,String orderDir) {
		System.out.println("有没有到这里来");
		QueryRunner runner = new QueryRunner();
		StringBuffer pageSQL = new StringBuffer();
		
		pageSQL.append("select top " + length + " * from ns_user_admin where ( ");
		pageSQL.append("admin_id not in( ");
		pageSQL.append("select top " + start
				+ " admin_id from ns_user_admin ");
		pageSQL.append("order by  "+orderColumn+"  "+orderDir+")) ");
		if (searchSQL != null && searchSQL != "") {
			pageSQL.append(" and ");
			pageSQL.append("(" + searchSQL + ")");
		}
		pageSQL.append("order by "+orderColumn+" "+orderDir+"");
		
		System.out.println(pageSQL.toString());
//		pageSQL.append("select top " + length + " * from ns_user_admin ");
//		pageSQL.append("where (admin_id not in( ");
//		pageSQL.append("select top " + start
//				+ " admin_id from ns_user_admin ");
//		pageSQL.append("order by cast(admin_id as int) asc)) ");
//		pageSQL.append("order by cast(admin_id as int) asc");

		Connection conn = null;
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		MapListHandler handler = new MapListHandler();
		// BeanListHandler<AdminBean> handler = new BeanListHandler<AdminBean>(
		// AdminBean.class);
		// List<AdminBean> adminList = null;
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

	public static String findModelCount(String searchSQL) {

		QueryRunner runner = new QueryRunner();
		StringBuffer pageSQL = new StringBuffer();
		pageSQL.append("select count(*) as num from ns_user_admin ");
		if (searchSQL != null  ) {
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
}
