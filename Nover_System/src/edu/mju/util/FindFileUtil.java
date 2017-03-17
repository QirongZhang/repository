package edu.mju.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class FindFileUtil {
	
	/**
	 * 阅读TXT文件
	 * @param filePath
	 * @return
	 */
	public static StringBuffer readTXTFile(String filePath) {
		InputStreamReader reader = null;
		BufferedReader bufferedReader = null;
		StringBuffer lineTXT = new StringBuffer();
		String line = null;
		String encoding = "GBK";
		File file = new File(filePath);
		try {
			file = new File(filePath);
			if (file.isFile() && file.exists()) {// 判断文件是否存在
				reader = new InputStreamReader(new FileInputStream(file),
						encoding);// 考虑到编码格式
				bufferedReader = new BufferedReader(reader);
				while ((line = bufferedReader.readLine()) != null) {
					lineTXT.append(line);
					lineTXT.append("\r\n");
				}
			} else {
				System.out.println("文件读取失败");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lineTXT;
	}// readTXTFile结束
	
	public static int countWord(String filePath) {
		StringBuffer stringBuffer = FindFileUtil.readTXTFile(filePath);
		char[] charArr = stringBuffer.toString().trim().toCharArray();
		int count = charArr.length;
		return count;
	}
}
