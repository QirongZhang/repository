package edu.mju.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FindPropertiesUtil {
	private FindPropertiesUtil() {

	}

	public static String findNover_url() {
		String param = null;

		Properties prop = new Properties();
		InputStream in = FindPropertiesUtil.class
				.getResourceAsStream("/nover_url.properties");
		try {
			prop.load(in);
			param = prop.getProperty("nover_url").trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return param;
	}
	
	public static String findImg_url() {
		String param = null;

		Properties prop = new Properties();
		InputStream in = FindPropertiesUtil.class
				.getResourceAsStream("/nover_url.properties");
		try {
			prop.load(in);
			param = prop.getProperty("img_url").trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return param;
	}
	
	public static String findUpload_path() {
		String param = null;

		Properties prop = new Properties();
		InputStream in = FindPropertiesUtil.class
				.getResourceAsStream("/upload.properties");
		try {
			prop.load(in);
			param = prop.getProperty("upload_path").trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return param;
	}

	public static String getPath(String parentDir, String fileName) {

		String path = null;

		String userdir = System.getProperty("user.dir");

		String userdirName = new File(userdir).getName();

		if (userdirName.equalsIgnoreCase("lib")
				|| userdirName.equalsIgnoreCase("bin")) {

			File newf = new File(userdir);

			File newp = new File(newf.getParent());

			if (fileName.trim().equals("")) {

				path = newp.getPath() + File.separator + parentDir;

			} else {

				path = newp.getPath() + File.separator + parentDir

				+ File.separator + fileName;

			}

		} else {

			if (fileName.trim().equals("")) {

				path = userdir + File.separator + parentDir;

			} else {

				path = userdir + File.separator + parentDir + File.separator

				+ fileName;

			}

		}

		return path;

	}
	
}
