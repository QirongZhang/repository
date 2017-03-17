package edu.mju.dao.imp;

import java.io.File;
import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import edu.mju.dao.NoticeDAO;
import edu.mju.pojo.NoticeBean;
import edu.mju.util.Global;

@Repository(value = "noticeDAOImp")
@Transactional(rollbackFor = java.lang.Exception.class)
public class NoticeDAOImp implements NoticeDAO {

	@Autowired
	private DataSource dataSource;

	@Override
	public void save(NoticeBean noticeBean, File attch, String attchFileName,
			String uploadDir) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(
				this.dataSource);
		StringBuffer insertSQL = new StringBuffer();
		insertSQL.append("Insert into ns_notice(");
		insertSQL.append("notice_title,notice_adduser,notice_addtime");
		insertSQL.append(",notice_content,filename,filepath,");
		insertSQL.append("is_approve)");
		insertSQL.append(" values(");
		insertSQL.append(":notice_title,:notice_adduser,:notice_addtime,");
		insertSQL.append(":notice_content,:filename,:filepath,");
		insertSQL.append(":is_approve");
		insertSQL.append(")");
		BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(
				noticeBean);
		int updateRow =  template.update(insertSQL.toString(), parameterSource);
		System.out.println("上传文件成功" + updateRow);
	}

	@Override
	public NoticeBean getBeanByID(String notice_id) {

		JdbcTemplate template = new JdbcTemplate(this.dataSource);
		String sql = "Select * From T_Notice where notice_id = '" + notice_id
				+ "'";
		BeanPropertyRowMapper<NoticeBean> rowMapper = new BeanPropertyRowMapper<NoticeBean>(
				NoticeBean.class);
		NoticeBean noticeBean = template.queryForObject(sql, rowMapper);
		return noticeBean;
	}
}
