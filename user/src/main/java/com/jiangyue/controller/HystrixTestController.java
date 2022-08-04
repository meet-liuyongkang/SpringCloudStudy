package com.jiangyue.controller;

import com.jiangyue.common.vo.ResultMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author liuyongkang
 * @date Create in 2022/6/23 15:35
 */
@RestController
@RequestMapping("/hystrix")
public class HystrixTestController {

    private static final Long MAX_TIMEOUT = 5000L;

    /**
     * 测试接口超时的熔断
     * @return
     */
    @GetMapping("/timeout")
    public ResultMessage timeout(){
        Random random = new Random();
        int nextInt = random.nextInt(2000);

        System.out.println("休眠时间" + nextInt);
        try {
            Thread.sleep(nextInt);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ResultMessage(true, "休眠时间：" + nextInt);
    }


    /**
     * 测试出现异常时的熔断
     * @param msg
     * @return
     */
    @GetMapping("/exp/{msg}")
    public ResultMessage exp(@PathVariable("msg") String msg){
        if ("spring".equalsIgnoreCase(msg)) {
            return new ResultMessage(true, msg);
        } else {
            throw new RuntimeException("出现了异常，请确认是否输入的spring");
        }
    }


}
