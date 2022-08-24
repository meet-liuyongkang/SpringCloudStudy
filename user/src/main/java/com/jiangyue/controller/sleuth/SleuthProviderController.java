package com.jiangyue.controller.sleuth;

import com.jiangyue.common.vo.ResultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuyongkang
 * @date Create in 2022/8/23 15:38
 *
 * 服务提供者
 */

@RestController
@RequestMapping("/user/sleuth/provider")
public class SleuthProviderController {

    private static final Logger logger = LoggerFactory.getLogger(SleuthProviderController.class);

    @GetMapping("/hello/{name}")
    public ResultMessage hello(@PathVariable("name") String name){
        logger.info("请求参数:" + name);
        String result = "hello " + name;
        logger.info("返回结果：" + result);

        return new ResultMessage(true, result);
    }

}
