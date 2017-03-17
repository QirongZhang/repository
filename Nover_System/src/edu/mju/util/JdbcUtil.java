package edu.mju.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcUtil {
	static String driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	static String url = "jdbc:sqlserver://localhost:1433;DatabaseName=nover_system";
	static String username = "sa";
	static String password = "123";

	static {
		try {
			Class.forName(driverClass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("无法加载数据库的驱动");
		}

	}

	public static Connection getConn() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static int executeUpdate(String sql) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = getConn();
			stmt = conn.createStatement();
			return stmt.executeUpdate(sql);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeResource(stmt, conn);
		}
	}

	public static void closeResource(Statement stmt, Connection conn) {
		closeResource(null, stmt, conn);
	}

	public static void closeResource(ResultSet rs, Statement stmt,
			Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
