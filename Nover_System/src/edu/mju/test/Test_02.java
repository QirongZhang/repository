package edu.mju.test;

import edu.mju.util.FindPropertiesUtil;

public class Test_02 {
	
	public static void main(String[] args) {
		String pathString = FindPropertiesUtil.findUpload_path();
		System.out.println("pathString = " + pathString);
	}

	
}
