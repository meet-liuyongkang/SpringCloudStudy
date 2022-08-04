package com.jiangyue.controller;

import com.jiangyue.common.vo.ResultMessage;
import com.jiangyue.sdk.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuyongkang
 * @date Create in 2022/6/23 16:11
 */
@RestController
public class CircuitBreakerController {

    @Autowired
    private UserFacade userFacade;

    @GetMapping("/ci/timeout")
    public ResultMessage timeout(){
        return userFacade.timeout();
    }

    @GetMapping("/ci/exp/{msg}")
    public ResultMessage exp(@PathVariable String msg){
        return userFacade.exp(msg);
    }


}
