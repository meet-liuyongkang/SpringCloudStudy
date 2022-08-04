package com.jiangyue.gateway.controller;

import com.jiangyue.common.vo.ResultMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuyongkang
 * @date Create in 2022/8/3 11:00
 *
 * 服务降级接口
 */
@RestController
@RequestMapping("gateway")
public class GatewayFallBackController {

    @GetMapping("/fallBack")
    public ResultMessage fallBack(){
        return new ResultMessage(false, "路由失败，请检查服务器状况！");
    }

}
