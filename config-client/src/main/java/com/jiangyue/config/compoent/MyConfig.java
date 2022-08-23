package com.jiangyue.config.compoent;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author liuyongkang
 * @date Create in 2022/8/19 10:13
 */
@Component
public class MyConfig {

    @Value("${version.message}")
    private String message;

    @PostConstruct
    public void init(){
        System.out.println(message);
    }

}
