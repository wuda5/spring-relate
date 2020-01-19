package com.luban.dao;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface CityMapper {

    @Select( "select * from user")
    public List<Map<String,Object>> list();
}
