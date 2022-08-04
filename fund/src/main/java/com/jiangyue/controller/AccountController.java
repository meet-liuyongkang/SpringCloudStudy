package com.jiangyue.controller;

import com.jiangyue.common.vo.ResultMessage;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liuyongkang
 * @date Create in 2022/6/22 14:52
 */
@RestController
@RequestMapping("fund")
public class AccountController {

    @PostMapping("/account/balance/{userId}/{amount}")
    public ResultMessage deductingBalance(@PathVariable String userId,
                                          @PathVariable Double amount,
                                          HttpServletRequest request){
        System.out.println("userId=" + userId + ", double=" + amount + " 扣除资金");
        String message = "端口：" + request.getServerPort() + " 资金扣减成功！";
        ResultMessage resultMessage = new ResultMessage(true, message);
        return resultMessage;
    }
}
