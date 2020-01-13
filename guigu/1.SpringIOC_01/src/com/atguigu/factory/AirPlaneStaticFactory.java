package com.atguigu.factory;

import com.atguigu.bean.AirPlane;

/**
 * 静态工厂
 * @author lfy
 *
 */
public class AirPlaneStaticFactory {
	
	//AirPlaneStaticFactory.getAirPlane()
	public static AirPlane getAirPlane(String jzName){
		System.out.println("AirPlaneStaticFactory...正在为你造飞机");
		AirPlane airPlane = new AirPlane();
		airPlane.setFdj("太行");
		airPlane.setFjsName("lfy");
		airPlane.setJzName(jzName);
		airPlane.setPersonNum(300);
		airPlane.setYc("198.98m");
		return airPlane;
	}

}
