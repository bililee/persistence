package com.z.persistence.controller;

import com.z.persistence.component.EtcdService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * TestController
 *
 * @author Lee
 * @date 2023/5/20
 */
@RestController
@RequestMapping("test")
public class TestController {

    @Resource
    private EtcdService etcdService;

    @RequestMapping("put")
    public Object put(
            @RequestParam(required = true, name = "value") String value
    ) {
        return etcdService.put("service1", value);
    }
}
