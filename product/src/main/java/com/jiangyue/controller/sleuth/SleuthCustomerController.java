package com.jiangyue.controller.sleuth;

import com.jiangyue.common.vo.ResultMessage;
import com.jiangyue.sdk.UserFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuyongkang
 * @date Create in 2022/8/23 15:38
 *
 * 服务消费者
 */

@RestController
@RequestMapping("/product/sleuth/customer")
public class SleuthCustomerController {

    private static final Logger logger = LoggerFactory.getLogger(SleuthCustomerController.class);

    @Autowired
    private UserFacade userFacade;

    @GetMapping("/hello/{name}")
    public ResultMessage hello(@PathVariable String name){
        return userFacade.sleuthHello(name);
    }

}
