package com.jiangyue.controller;

import com.jiangyue.user.vo.UserInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuyongkang
 * @date Create in 2022/7/22 16:32
 */
@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping("/info/{id}")
    public UserInfo getUserInfo(@PathVariable(value = "id") Long id) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setName("张三");
        userInfo.setAge(23);
        userInfo.setSex("男");

        System.out.println(System.currentTimeMillis());

        return userInfo;
    }

}
