package edu.mju.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringUtil {
	private static ApplicationContext context;
	static {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}

	public static Object getBean(String beanName) {
		Object obj = null;
		if (beanName != null && !beanName.equals("")) {
			obj = context.getBean(beanName);
		}
		return obj;
	}
}
