package com.jiangyue.gateway.config;

import com.jiangyue.gateway.filter.CustomGatewayFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author liuyongkang
 * @date Create in 2022/8/1 10:39
 */
@Configuration
public class GatewayConfig {

    @Autowired
    private CustomGatewayFilter customGatewayFilter;

    @Bean
    public RedisRateLimiter customRedisRateLimiter(){
        return new RedisRateLimiter(1, 2);
    }


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder){
        ZonedDateTime beforeDateTime = ZonedDateTime.now(ZoneId.systemDefault()).plusMinutes(2);


        return routeLocatorBuilder.routes()
                // path断言：设置 匹配路径 和 转发路径
//                .route(f -> f.path("/user/**").uri("http://localhost:6001"))
//                // 时间断言：过滤器在某个时间点前生效，相应的还有 after、between
//                .route(f -> f.before(beforeDateTime).uri("http://localhost:6001"))
//                // cookie断言：判断cookie中某个参数是否满足正则匹配
//                .route(f -> f.cookie("username", "liu.*").uri("http://localhost:6001"))
//                // header断言：判断header中某个参数是否满足正则匹配
//                .route(f -> f.header("username", "liu.*").uri("http://localhost:6001"))
//                // host断言：匹配设置的host
//                .route(f -> f.host("127.0.0.1", "127.0.0.2").uri("http://localhost:6001"))
//                // method断言：判断接口方法是否指定的类型
//                .route(f -> f.method(HttpMethod.GET).uri("http://localhost:6001"))
//                // query 断言：判断param是否包含某个参数
//                .route(f -> f.query("username").uri("http://localhost:6001"))
//                // query 断言：判断 param 中参数是否符合正则匹配
//                .route(f -> f.query("username", "liu.*").uri("http://localhost:6001"))
//                // weight 断言：user 服务组中的请求，20% 分配到 6001 服务器
//                .route(f -> f.weight("user", 20).uri("http://localhost:6001"))
//                // weight 断言：user 服务组中的请求，80% 分配到 6002 服务器
//                .route(f -> f.weight("user", 80).uri("http://localhost:6002"))
//
//
//
//                // 下面是过滤器配置，断言主要对路由进行匹配设置。而过滤器则是在请求发送到源服务器的前后，对请求和响应做出拦截和修改
//                // 添加请求头参数过滤器
//                .route("user-server", f -> f.path("/user/**")
//                        .filters(filter -> filter.addRequestHeader("id", "1"))
//                        .uri("http://localhost:6001"))
//
//                // 重试过滤器
//                .route("retry-server", f -> f.path("/user/**")
//                        .filters(filter -> filter.retry(retryConfig -> {
//                            // 重试次数 3
//                            retryConfig.setRetries(3);
//                            // 限制GET和POST请求才重试
//                            retryConfig.setMethods(HttpMethod.GET, HttpMethod.POST);
//                            // 限制响应码为服务器错误（500）和 坏请求（400）才重试
//                            retryConfig.setStatuses(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.BAD_REQUEST);
//                            // 限制重试系列为服务器错误
//                            retryConfig.setSeries(HttpStatus.Series.SERVER_ERROR);
//                        }))
//                        .uri("http://localhost:6001"))
//
//                // hystrix 服务降级过滤器
//                .route("hystrix-server", f -> f.path("/user/**")
//                        .filters(filter -> filter.hystrix(hystrixConfig -> {
//                            // hystrix 命令名称
//                            hystrixConfig.setName("hystrix-cmd");
//                            // 降级跳转url
//                            hystrixConfig.setFallbackUri("forward:/gateway/fallBack");
//                            // 设置 hystrix 参数
////                            hystrixConfig.setSetter("xxx")
//                        }))
//                        .uri("http://localhost:6001"))
//
//
//                // RequestRateLimiter 限流过滤器
//                .route("rateLimiter-server", f -> f.path("/user/**")
//                        .filters(filter -> filter.requestRateLimiter(limiterConfig -> {
//                            limiterConfig.setRateLimiter(customRedisRateLimiter());
//                        }))
//                        .uri("http://localhost:6001"))


                // 使用自定义过滤器，结合 Resilience4j 实现限流
//                .route("customFilter-server", f -> f.path("/user/**")
//                        .filters(filter -> filter.filters(customGatewayFilter))
//                        .uri("http://localhost:6001"))

                .build();
    }


}
