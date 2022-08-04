package com.jiangyue.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.factory.RequestRateLimiterGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author liuyongkang
 * @date Create in 2022/8/1 10:38
 */
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(GatewayApplication.class, args);

        RequestRateLimiterGatewayFilterFactory rateLimiterGatewayFilterFactory = applicationContext.getBean(RequestRateLimiterGatewayFilterFactory.class);
        RedisRateLimiter redisRateLimiter = applicationContext.getBean(RedisRateLimiter.class);
        System.out.println(rateLimiterGatewayFilterFactory);
        System.out.println(redisRateLimiter);
    }

}
