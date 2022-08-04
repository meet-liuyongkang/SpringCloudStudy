package com.jiangyue.zuul.component;

import com.alibaba.fastjson.JSONObject;
import com.jiangyue.common.vo.ResultMessage;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

/**
 * @author liuyongkang
 * @date Create in 2022/7/28 17:20
 *
 * Resilience 限流过滤器
 */
@Component
public class ResilienceLimiterFilter extends ZuulFilter {

    /**
     * 注入限速器注册机
     */
    @Autowired
    private RateLimiterRegistry rateLimiterRegistry;

    /**
     * 对用户服务过滤得正则匹配
     */
    private static final String USER_URL_PRE = "/u/";

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        // +30 是为了让其他的Pre过滤器先执行，只对有效的请求限流，所以放在后面
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 30;
    }

    /**
     * 是否拦截。true：拦截，false：不拦截。
     * @return
     */
    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        String requestURI = requestContext.getRequest().getRequestURI();
        // 只对 user 服务限流
        return requestURI.startsWith(USER_URL_PRE);
    }

    @Override
    public Object run() throws ZuulException {
        // 获取 user 限速器
        RateLimiter userRateLimiter = rateLimiterRegistry.rateLimiter("user");
        // 限速器的线程处理逻辑
        Callable<ResultMessage> callable = () -> new ResultMessage(true, "通过");
        // 限速器和处理线程绑定
        Callable<ResultMessage> reteLimiterCallable = RateLimiter.decorateCallable(userRateLimiter, callable);
        // 尝试获取结果，recover 中是异常处理，如果超过限流，则会报错，将报错转换为友好提示
        Try<ResultMessage> tryResult = Try.of(() -> reteLimiterCallable.call()).recover(exception -> new ResultMessage(false, "超过服务器流量限制，请稍后再试！"));

        ResultMessage resultMessage = tryResult.get();
        if (resultMessage.isSuccess()) {
            // 如果执行成功，则说明在限流范围内，直接放行请求
            return null;
        }

        // 下面是限流后的处理逻辑

        RequestContext requestContext = RequestContext.getCurrentContext();
        // 不再进行后面的路由，到此为止
        requestContext.setSendZuulResponse(false);
        // 设置响应状态码为 400 - 坏请求
        requestContext.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
        // 设置响应类型
        requestContext.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        // 设置返回值
        requestContext.setResponseBody(JSONObject.toJSONString(resultMessage));

        return null;
    }
}
