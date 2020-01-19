package com.luban.dao;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface CityMapper2 {

    @Select( "select * from user --- test 2----")
    public List<Map<String,Object>> list2();
}
