package com.luban.xmlBeans;

public class xmlBeanTest {

	private  String xmlprop ;

	public xmlBeanTest() {
		System.out.println("xml setter  create xmlBeanTest +++++++++++++++++  ");
	}

	// 需要！--xml setter
	public void setXmlprop(String xmlprop) {
		this.xmlprop = xmlprop;
	}

	@Override
	public String toString() {
		return "xmlprop{" +
				"xmlprop='" + xmlprop + '\'' +
				'}';
	}
}
