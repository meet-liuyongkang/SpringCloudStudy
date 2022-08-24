package com.jiangyue.sdk;

import com.jiangyue.common.vo.ResultMessage;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;

import java.util.concurrent.Future;

/**
 * @author liuyongkang
 * @date Create in 2022/6/23 15:54
 */
@Service
public class UserFacadeImpl implements UserFacade{

    @Autowired
    private RestTemplate restTemplate;

    /**
     * @HystrixCommand 设置服务降级的调用方法
     * @return
     */
    @Override
    @HystrixCommand(fallbackMethod = "fallback1")
    public ResultMessage timeout() {
        String url = "http://user/hystrix/timeout";
        return restTemplate.getForObject(url, ResultMessage.class);
    }


    /**
     * @HystrixCommand 设置服务降级的调用方法
     * @param msg
     * @return
     */
    @Override
    @HystrixCommand(fallbackMethod = "fallback2")
    public ResultMessage exp(String msg) {
        String url = "http://user/hystrix/exp/" + msg;
        return restTemplate.getForObject(url, ResultMessage.class);
    }

    /**
     * hystrix底层其实是通过stream流的方式执行命令，所以我们可以自定义命令的方式来执行降级方法
     * @return
     */
    @Override
    @HystrixCommand(fallbackMethod = "fallback1")
    public Future<ResultMessage> timeoutCmd() {
        String url = "http://user/hystrix/timeout";
        return new AsyncResult<ResultMessage>() {
            @Override
            public ResultMessage invoke() {
                return restTemplate.getForObject(url, ResultMessage.class);
            }
        };
    }


    /**
     * @HystrixCommand 设置服务降级的调用方法, ObservableExecutionMode 表示执行模式，EAGER表示热观察模式，立即发送请求
     * @param params
     * @return
     */
    @Override
    @HystrixCommand(fallbackMethod = "fallback3", observableExecutionMode = ObservableExecutionMode.EAGER)
    public Observable<ResultMessage> expCmd(String[] params) {
        String url = "http://user/hystrix/exp/" + params;
        Observable.OnSubscribe<ResultMessage> onSubscribe = resSubscribe -> {
            try {
                // 定义一个计数器
                int count = 0;
                if (!resSubscribe.isUnsubscribed()) {
                    for (String param : params) {
                        count++;
                        System.out.println("观察者第" + count + "次发送参数。");
                        // 一次发送一个参数
                        ResultMessage resultMessage = restTemplate.getForObject(url, ResultMessage.class, param);
                        resSubscribe.onNext(resultMessage);
                    }
                    // 遍历完成后，告知服务器，参数发送完成
                    resSubscribe.onCompleted();
                }
            } catch (Exception e) {
                // 记录异常
                resSubscribe.onError(e);
            }
        };
        return Observable.create(onSubscribe);
    }

    @Override
    public ResultMessage sleuthHello(String name) {
        String url = "http://user/user/sleuth/provider/hello/" + name;
        return restTemplate.getForObject(url, ResultMessage.class);
    }

    /**
     * 降级方法1
     * @return
     */
    public ResultMessage fallback1(){
        return new ResultMessage(true, "超时降级！");
    }

    /**
     * 降级方法2，带参数
     * @param msg
     * @return
     */
    public ResultMessage fallback2(String msg) {
        return new ResultMessage(true, "异常降级！" + "参数msg:" + msg);
    }

    /**
     * 降级方法3，带多个参数
     * @param params
     * @return
     */
    public ResultMessage fallback3(String[] params) {
        return new ResultMessage(true, "异常降级！" + "参数msg:" + params.toString());
    }
}
