package com.jiangyue.controller;

import com.jiangyue.sdk.UserSdkService;
import com.jiangyue.user.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuyongkang
 * @date Create in 2022/7/6 15:54
 */
@RestController
@RequestMapping("/feign")
public class FeignController {

    @Autowired
    private UserSdkService userSdkService;

    @GetMapping("/user/{id}")
    public UserInfo getUser(@PathVariable Long id){
        return userSdkService.getUser(id);
    }


    @PutMapping("/user/{name}/{sex}/{age}")
    public UserInfo putUser(@PathVariable Integer age, @PathVariable String name, @PathVariable String sex){
        UserInfo userInfo = new UserInfo();
        userInfo.setName(name);
        userInfo.setAge(age);
        userInfo.setSex(sex);
        return userSdkService.putUser(userInfo);
    }



}
