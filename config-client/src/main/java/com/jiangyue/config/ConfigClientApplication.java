package com.jiangyue.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liuyongkang
 * @date Create in 2022/8/16 16:54
 *
 * 注意，读取配置中心的文件规则为，配置文件的名称 = 当前项目的 {spring.application.name}-{spring.profiles.active}.yml
 * 如果配置中心没有该规则的文件，则读取不到相应的配置。
 *
 * 浏览器访问规则：http://localhost:4001/{spring.application.name}/{spring.profiles.active}/[{spring.cloud.config.label}]
 * 例如：http://localhost:4001/config-client/v001
 *
 */
@SpringBootApplication
public class ConfigClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigClientApplication.class, args);
    }
}
