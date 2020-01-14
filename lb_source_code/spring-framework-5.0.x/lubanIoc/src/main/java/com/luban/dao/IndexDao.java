package com.luban.dao;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
@Description("test-desc--FactoryBean")
public class IndexDao implements Dao, Serializable, FactoryBean {

    private List<String> listProp;

	public IndexDao() {
		System.out.println("+++++++++++[5.] IndexDao 实现了 --> FactoryBean 的 构造方法  +++++++++");
	}

	/**
	 * 取钱
	 */
	public void query(){
		System.out.println("index");
		System.out.println("index");
		System.out.println("index");
		System.out.println("index");
		System.out.println("index");
		System.out.println("index");
		System.out.println("index");

	}


	@Override
	public Object getObject() throws Exception {
		return new IndexDao1();
	}

	@Override
	public Class<?> getObjectType() {
		return null;
	}

	public List<String> getListProp() {
		return listProp;
	}

	public void setListProp(List<String> listProp) {
		this.listProp = listProp;
	}
}
