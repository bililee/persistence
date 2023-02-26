package com.z.persistence.rest.controller;

import com.z.persistence.entity.p1.Tuser1;
import com.z.persistence.entity.p2.Tuser2;
import com.z.persistence.mysql.p1.dao.Tuser1Mapper;
import com.z.persistence.mysql.p2.dao.Tuser2Mapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * HelloController
 *
 * @author Lee
 * @date 2023/2/26
 */
@RestController
public class HelloController {

    @Resource
    Tuser1Mapper tuser1Mapper;

    @Resource
    Tuser2Mapper tuser2Mapper;


    @GetMapping("hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("get")
    public Object testSql() {
        HashMap<String, Object> res = new HashMap<>();
        Tuser1 tuser1 = tuser1Mapper.selectByPrimaryKey(1);
        Tuser2 tuser2 = tuser2Mapper.selectByPrimaryKey(1);
        res.put("tuser1", tuser1);
        res.put("tuser2", tuser2);
        return res;
    }
}
