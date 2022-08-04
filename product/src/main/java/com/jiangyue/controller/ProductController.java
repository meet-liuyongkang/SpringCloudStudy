package com.jiangyue.controller;

import com.jiangyue.common.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyongkang
 * @date Create in 2022/6/22 15:47
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/purchase/{userId}/{productId}/{amount}")
    public ResultMessage purchaseProduct(@PathVariable Long userId,
                                         @PathVariable Long productId,
                                         @PathVariable Double amount){
        System.out.println("扣减产品余额");

        //请求地址
        String url = "http://fund/fund/account/balance/{userId}/{amount}";

        //请求参数
        Map<String, Object> params = new HashMap<>(2);
        params.put("userId", userId);
        params.put("amount", amount);

        ResultMessage result = restTemplate.postForObject(url, null, ResultMessage.class, params);
        System.out.println(result.toString());

        return new ResultMessage(true, "交易成功！");
    }

}
