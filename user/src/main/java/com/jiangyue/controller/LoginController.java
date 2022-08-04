package com.jiangyue.controller;

import com.jiangyue.common.vo.ResultMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuyongkang
 * @date Create in 2022/7/28 10:44
 */
@RestController
@RequestMapping("user")
public class LoginController {

    @GetMapping("login")
    public ResultMessage login(@RequestParam String code){
        return new ResultMessage(true, "code:" + code);
    }

}
