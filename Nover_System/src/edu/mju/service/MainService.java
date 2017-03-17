package edu.mju.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.springframework.stereotype.Service;

import edu.mju.util.JdbcUtil;

@Service(value = "mainService")
public class MainService {
	public List<Map<String, Object>> getMenu_TreeList(String admin_id) {
		List<Map<String, Object>> menu_treeList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

		QueryRunner runner = new QueryRunner();
		Connection conn = null;
		StringBuffer querySQL = new StringBuffer();

		querySQL.append("Select * From ns_user_menu ");
		querySQL.append(" where menu_id in (");
		querySQL.append("	Select distinct menu_id ");
		querySQL.append(" 		From ns_user_role_menu");
		querySQL.append("		where role_id in (");
		querySQL.append("	Select role_id From ns_user_admin_role ");
		querySQL.append("		where admin_id = " + admin_id + "))");
		querySQL.append("order by menu_id asc");

		try {
			conn = JdbcUtil.getConn();
			dataList = runner.query(conn, querySQL.toString(),
					new MapListHandler());
			
			Map<String, Object> rowMap = null;
			for (int i = 0; i < dataList.size(); i++) {
				rowMap = dataList.get(i);
				Map<String, Object> treeNodeMap = new LinkedHashMap<String, Object>();
				String menu_id = String.valueOf(rowMap.get("menu_id"));
				String menu_name = String.valueOf(rowMap.get("menu_name"));
				String parentid = String.valueOf(rowMap.get("parent_id"));
				String isLeaf = String.valueOf(rowMap.get("isleaf"));
				
				treeNodeMap.put("id", menu_id);
				treeNodeMap.put("name", menu_name);
				treeNodeMap.put("pId", parentid);
				treeNodeMap.put("isLeaf", isLeaf);
				
				if (i == 0) {
					treeNodeMap.put("open", true);
				}
				if (isLeaf.equals("1")) {
					String menu_href = String.valueOf(rowMap.get("menu_href"));	
					treeNodeMap.put("menu_href", menu_href);
				}

				menu_treeList.add(treeNodeMap);
			}
			/*
			for (Map<String, Object> rowMap : dataList) {
				Map<String, Object> treeNodeMap = new LinkedHashMap<String, Object>();
				String menu_id = String.valueOf(rowMap.get("menu_id"));
				String menu_name = String.valueOf(rowMap.get("menu_name"));
				String parentid = String.valueOf(rowMap.get("parent_id"));
				String isLeaf = String.valueOf(rowMap.get("isleaf"));

				treeNodeMap.put("id", menu_id);
				treeNodeMap.put("name", menu_name);
				treeNodeMap.put("pId", parentid);
				treeNodeMap.put("isLeaf", isLeaf);
				
				if (parentid.equals("0")) {
					treeNodeMap.put("open", true);
				}
				if (isLeaf.equals("1")) {
					String menu_href = String.valueOf(rowMap.get("menu_href"));	
					treeNodeMap.put("menu_href", menu_href);
				}

				menu_treeList.add(treeNodeMap);
			}
			*/
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return menu_treeList;
	}
}
