package com.luban.FactoryBean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class MyFactoryBean implements FactoryBean {
	@Override
	public Object getObject() throws Exception {
		return new FbTest();
	}

	@Override
	public Class<?> getObjectType() {
		return FbTest.class;
	}
}
