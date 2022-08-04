package com.jiangyue.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author liuyongkang
 * @date Create in 2022/7/11 11:19
 *
 * EnableZuulProxy 注解里面开启了 EnableCircuitBreaker，也就是hystrix组件，从依赖上也可以发现，Zuul依赖了Hystrix。
 * 之所以这样是因为在高并发场景下，使用hystrix的限流，来防止Zuul网关瘫痪，注意zuul默认使用信号量的方式限流。
 */
@SpringBootApplication
@EnableEurekaClient
// 这个注解会自动装配一些常用的默认过滤器
@EnableZuulProxy
public class ZuulApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ZuulApplication.class, args);
        StringRedisTemplate stringRedisTemplate = applicationContext.getBean(StringRedisTemplate.class);
        // 设置验证码的值
        stringRedisTemplate.opsForValue().set("code", "666");
    }

}
