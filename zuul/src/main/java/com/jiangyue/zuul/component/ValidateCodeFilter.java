package com.jiangyue.zuul.component;

import com.alibaba.fastjson.JSONObject;
import com.jiangyue.common.vo.ResultMessage;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 * @author liuyongkang
 * @date Create in 2022/7/27 17:24
 * 基于zuul的验证码过滤器
 */

// 如果 ZuulFilter 过滤器的子类被装配为Spring Bean，那么Spring会自动将其注册为zuul过滤器
@Component
public class ValidateCodeFilter extends ZuulFilter {

    private static String CODE_PARAM_NAME = "code";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 过滤器类型
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 过滤器优先级
     * @return
     */
    @Override
    public int filterOrder() {
        return 15;
    }

    /**
     * 此次请求，是否执行过滤器逻辑。true：执行，false：不执行。
     * @return
     */
    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        // 如果请求参数包含验证码，则执行过滤逻辑
        if (requestContext.getRequest().getMethod().equals("GET") &&
                requestContext.getRequestQueryParams().containsKey(CODE_PARAM_NAME)) {
            return true;
        }

        return false;
    }

    /**
     * 过滤器逻辑
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        String paramCode = requestContext.getRequestQueryParams().get(CODE_PARAM_NAME).get(0);
        String redisCode = stringRedisTemplate.opsForValue().get(CODE_PARAM_NAME);

        // 判断请求参数重点code和Redis中的code是否相等
        if (paramCode != null && paramCode.equals(redisCode)){
            // 如果相等，则直接放行
            return null;
        }

        // 如果不相等，则返回异常信息
        // 不再放行路由，过滤链执行到此为止
        requestContext.setSendZuulResponse(false);
        // 设置返回状态码401-未签名
        requestContext.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
        // 设置响应类型
        requestContext.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        // 响应结果
        ResultMessage resultMessage = new ResultMessage(false, "验证码输入错误，请重试！");
        // 设置响应体
        requestContext.setResponseBody(JSONObject.toJSONString(resultMessage));
        return null;
    }
}
