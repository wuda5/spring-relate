package com.luban.service;

import com.luban.dao.CityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class IndexService {

    @Autowired
    CityMapper city;

    public List<Map<String,Object>> list(){
        List<Map<String, Object>> list = city.list();
        return list;
    }
    public Object list2(){
        return city.list();
    }
}
