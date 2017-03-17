package edu.mju.test;

import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import edu.mju.util.FileUtil;
import edu.mju.util.SpringUtil;
import edu.mju.util.FindFileUtil;

@Repository(value = "insert")
public class Insert extends JdbcDaoSupport{

	@Autowired
	public void setDataSourceX(DataSource dataSource) {
		super.setDataSource(dataSource);
	}
	public void insert(Object [] objectArray) {
		StringBuffer insertSQL = new StringBuffer();
		insertSQL.append("Insert Into ns_nover_chapter");
		insertSQL.append("(chapter_id , story_id,chapter_name,");
		insertSQL.append("word_count,publish_time)");
		insertSQL.append("Values (?,?,?,?,?)");
		
		int rowConut = this.getJdbcTemplate().update(insertSQL.toString(), objectArray);
		System.out.println("影响的行数 ：" + rowConut);
		
	}
	
	public Date findDate() {
		java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
		return currentDate;
		
	}
	
	public void insertFile() {
		FileUtil findFileName = (FileUtil) SpringUtil.getBean("findFileNameUtil");
		Insert insert = (Insert) SpringUtil.getBean("insert");
		
		String path = "E:/学习资料/在线小说阅读系统/回到明朝当王爷/001-463(第1章+)";
		
		String[] fileName = findFileName.getFileName(path);
		
		for (int i = 0; i < fileName.length; i++) {
			fileName[i] = fileName[i].replaceFirst(".txt", "");
		}
		
		Object[] objectArray = new Object [5];
		for (int i = 0; i < fileName.length; i++) {
			objectArray[0] = i+1;
			objectArray[1] = 1;
			objectArray[2] = fileName[i];
			objectArray[3] = 1000;
			objectArray[4] = insert.findDate();
			insert.insert(objectArray);
			System.out.println("Chapter_id"+objectArray[0] +"\t Story_id:"+objectArray[1]+
					"\t Chapter_name"+objectArray[2]
					+"\t Word_count"+objectArray[3]+"\t Publish_time"+objectArray[4]);
		}
		
		System.out.println("--------------------------------");
	}
	
	
	public static void main(String[] args) {
		FindFileUtil fileUtil = new FindFileUtil();
		String filePath = "E:/在线小说阅读系统/小说/回到明朝当王爷/001-九世善人.txt";
		StringBuffer stringBuffer = fileUtil.readTXTFile(filePath);
		
//		Pattern pattern =  Pattern.compile("([\u4e00-\u9fa5]{1})"); //定义匹配模式:1个汉字
//		int num_of_words = 0;
//		Matcher matcher = pattern.matcher(stringBuffer.toString());
//        while(matcher.find()) num_of_words++;
//		System.out.println(num_of_words);
        
		char[] charArr = stringBuffer.toString().trim().toCharArray();
        System.out.println(charArr.length);
	}
}
