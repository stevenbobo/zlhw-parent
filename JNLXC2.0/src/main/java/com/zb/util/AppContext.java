package com.zb.util;

import org.springframework.context.support.*;

public class AppContext {
	private static AppContext instance;
	private AbstractApplicationContext appContext;

	public synchronized static AppContext getInstance() {
		if (instance == null) {
			instance = new AppContext();
		}
		return instance;
	}

	private AppContext() {
		this.appContext = new ClassPathXmlApplicationContext(
				"ApplicationContext.xml");
	}

	public AbstractApplicationContext getAppContext() {
		return appContext;
	}
}
