package com.jiangyue.sdk;

import com.jiangyue.user.vo.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author liuyongkang
 * @date Create in 2022/7/6 14:52
 */
@FeignClient("user")
public interface UserSdkService {

    /**
     * 根据id获取用户信息
     * @param id
     * @return
     */
    @GetMapping("/user/info/{id}")
    UserInfo getUser(@PathVariable(value = "id") Long id);

    /**
     * 新增用户
     * @param userInfo
     * @return
     */
    @PutMapping("/user/info")
    UserInfo putUser(@RequestBody UserInfo userInfo);


}
