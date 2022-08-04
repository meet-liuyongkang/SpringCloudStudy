package com.jiangyue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * 产品服务
 * @author liuyongkang
 * @date Create in 2022/6/22 10:20
 *
 * 已经开启了Hystrix，想使用仪表盘监控，只需要将actuator的端点暴露出来即可
 */
@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
public class ProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

}
