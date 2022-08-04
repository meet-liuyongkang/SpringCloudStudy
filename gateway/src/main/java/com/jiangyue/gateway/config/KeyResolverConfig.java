package com.jiangyue.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * @author liuyongkang
 * @date Create in 2022/8/4 14:41
 *
 * RequestRateLimiterGatewayFilterFactory
 * RequestRateLimiter 网关过滤器使用一个 RateLimiter 实现，来决定是否当前请求可以继续往下走。
 * 如果不能，默认将返回HTTP 429 - Too Many Requests。
 *
 * 这个过滤器接受一个可选的参数keyResolver，这个参数是一个特定的rate limiter。
 *
 * 默认情况下，如果KeyResolver没有找到一个key，那么请求将会被denied（译：否认，拒绝）
 *
 */
@Configuration
public class KeyResolverConfig {

    /**
     * 在配置的时候，使用SpEL按名称引用Bean。#{@pathKeyResolver}是一个SpEL表达式，表示引用名字叫myKeyResolver的Bean
     * @return
     */
    @Bean
    public KeyResolver pathKeyResolver() {
        // 对接口路径进行限流
//        return exchange -> Mono.just(exchange.getRequest().getPath().value());

        // 对访问IP进行限流
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }

}
