package com.jiangyue.sdk;

import com.jiangyue.common.vo.ResultMessage;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import rx.Observable;

import java.util.concurrent.Future;

/**
 * @author liuyongkang
 * @date Create in 2022/6/23 15:52
 */
public interface UserFacade {

    /**
     * 测试hystrix超时的熔断
     * @return
     */
    @HystrixCommand(fallbackMethod = "fallback1")
    public ResultMessage timeout();

    /**
     * 测试hystrix异常的熔断
     * @param msg
     * @return
     */
    @HystrixCommand(fallbackMethod = "fallback2")
    public ResultMessage exp(String msg);

    @HystrixCommand(fallbackMethod = "fallback1")
    Future<ResultMessage> timeoutCmd();

    @HystrixCommand(fallbackMethod = "fallback2")
    Observable<ResultMessage> expCmd(String[] params);

    /**
     * sleuth 全链路追踪测试接口
     * @param name
     * @return
     */
    public ResultMessage sleuthHello(String name);
}
