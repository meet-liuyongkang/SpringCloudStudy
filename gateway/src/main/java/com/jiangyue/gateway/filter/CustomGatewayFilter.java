package com.jiangyue.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.jiangyue.common.vo.ResultMessage;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.Callable;

/**
 * @author liuyongkang
 * @date Create in 2022/8/3 16:54
 * 自定义 Gateway 过滤器
 */
@Component
public class CustomGatewayFilter implements GatewayFilter {

    @Autowired
    private RateLimiterRegistry rateLimiterRegistry;

    /**
     * 限流逻辑
     * @return 是否放行结果
     */
    private ResultMessage rateLimit(){
        // 获取限流器
        RateLimiter userRateLimiter = rateLimiterRegistry.rateLimiter("user");

        Callable<ResultMessage> callable = RateLimiter.decorateCallable(userRateLimiter, () -> {
            return new ResultMessage(true, "PASS");
        });

        Try<ResultMessage> resultMessageTry = Try.of(() -> callable.call()).recover(ex -> new ResultMessage(false, "服务已经降级！"));

        return resultMessageTry.get();

    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ResultMessage resultMessage = rateLimit();
        if(!resultMessage.isSuccess()){
            ServerHttpResponse response = exchange.getResponse();
            // 状态码设置为，请求过多
            response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);

            String jsonString = JSONObject.toJSONString(resultMessage.getMessage());

            DataBuffer dataBuffer = response.bufferFactory().wrap(jsonString.getBytes());
            return response.writeWith(Mono.just(dataBuffer));
        }
        // 请求成功则直接放行
        return chain.filter(exchange);
    }
}
