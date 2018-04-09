package com.study.crawler.client;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextSave implements ApplicationContextAware {
	private static ApplicationContext applicationConetextss = null;

	/***
	 * 当继承了ApplicationContextAware类之后，那么程序在调用 getBean(String)的时候会自动调用该方法，不用自己操作
	 */
	public void setApplicationContext(ApplicationContext applicationConetext) throws BeansException {
		// TODO Auto-generated method stub
		ApplicationContextSave.applicationConetextss = applicationConetext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationConetextss;
	}

	public static <T> T getBean(Class<T> clasz) {
		return applicationConetextss.getBean(clasz);
	}

	public static Object getBean(String name) {
		return applicationConetextss.getBean(name);
	}
}
