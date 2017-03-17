package edu.mju.dao.imp;

import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import edu.mju.dao.CategoryDAO;
import edu.mju.pojo.CategoryBean;
import edu.mju.pojo.ChapterBean;
import edu.mju.util.JdbcUtil;

@Repository(value = "categoryDAOImp")
public class CategoryDAOImp extends JdbcDaoSupport implements CategoryDAO {

	private DataSource dataSource;

	@Autowired
	public void setDataSourceX(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	/**
	 * 插入操作
	 */
	public void insert(Object[] objectArray) {
		StringBuffer insertSQL = new StringBuffer();
		insertSQL.append("Insert Into ns_nover_chapter");
		insertSQL.append("(chapter_id , story_id,chapter_name,");
		insertSQL.append("word_count,publish_time)");
		insertSQL.append("Values (?,?,?,?,?)");

		int rowConut = this.getJdbcTemplate().update(insertSQL.toString(),
				objectArray);
		System.out.println("影响的行数 ：" + rowConut);
	}

	/**
	 * 查找根据ID
	 * 
	 * @param objectArray
	 * @return
	 */
	@Override
	public ChapterBean findById(String category_id) {
		String sql = "Select * From ns_nover_chapter Where chapter_id = '"
				+ category_id + "'";
		JdbcTemplate template = this.getJdbcTemplate();

		BeanPropertyRowMapper<ChapterBean> rowMapper = new BeanPropertyRowMapper<ChapterBean>(
				ChapterBean.class);
		ChapterBean chapterBean = template.queryForObject(sql, rowMapper);

		System.out.println("chapterBean.getChapter_id()"
				+ chapterBean.getChapter_id() + "---------------------------");
		return chapterBean;
	}

	/**
	 * 查找所有信息操作
	 * 
	 * @param objectArray
	 * @return
	 */
	@Override
	public List<CategoryBean> findList() {
		QueryRunner runner = new QueryRunner();
		String sql = "Select * From ns_nover_category order by category_id asc";
		BeanListHandler<CategoryBean> handler = new BeanListHandler<CategoryBean>(
				CategoryBean.class);
		Connection conn = null;
		List<CategoryBean> categoryList = null;
		try {
			conn = JdbcUtil.getConn();
			categoryList = runner.query(conn, sql, handler);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return categoryList;
	}

	/**
	 * 修改操作
	 * 
	 * @param objectArray
	 * @return
	 */
	public void modify(CategoryBean categoryBean) {
		StringBuffer modify = new StringBuffer();
		modify.append("Update ns_nover_category Set");
		modify.append("category_name = :category_name,");
		modify.append("describe = :describe");
		modify.append("Where category_id = :category_id");
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(
				this.dataSource);
		BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(
				categoryBean);
		template.update(modify.toString(), parameterSource);
	}

	/**
	 * 删除操作
	 * 
	 * @param objectArray
	 */
	public void delete(String chapter_id) {
		String delete = "Delete From ns_nover_chapter Where chapter_id = '"
				+ chapter_id + "'";
		int row = this.getJdbcTemplate().update(delete);
	}

}
