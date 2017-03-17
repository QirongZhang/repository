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
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import edu.mju.dao.ChapterDAO;
import edu.mju.pojo.CategoryBean;
import edu.mju.pojo.ChapterBean;
import edu.mju.util.JdbcUtil;

@Repository(value = "chapterDAOImp")
public class ChapterDAOImp extends JdbcDaoSupport implements ChapterDAO {

	private DataSource dataSource;

	@Autowired
	public void setDataSourceX(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	/**
	 * 插入操作
	 */
	@Override
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
	public ChapterBean findById(String chapter_id) {
		String sql = "Select * From ns_nover_chapter Where chapter_id = '"
				+ chapter_id + "'";
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
	public List<ChapterBean> findList(String story_id) {
		QueryRunner runner = new QueryRunner();
		String sql = "Select * From ns_nover_chapter Where story_id = '"
				+ story_id + "' order by cast(chapter_id as int)";
		BeanListHandler<ChapterBean> handler = new BeanListHandler<ChapterBean>(
				ChapterBean.class);
		Connection conn = null;
		List<ChapterBean> chapterList = null;
		try {
			conn = JdbcUtil.getConn();
			chapterList = runner.query(conn, sql, handler);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return chapterList;
	}

	/**
	 * 修改操作
	 * 
	 * @param objectArray
	 * @return
	 */
	public void modify(Object[] objectArray) {

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
