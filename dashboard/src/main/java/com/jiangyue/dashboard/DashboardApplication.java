package com.jiangyue.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * @EnableHystrixDashboard 开启Hystrix仪表盘
 *
 * @author liuyongkang
 * @date Create in 2022/6/24 16:51
 */
@SpringBootApplication
// 开启hystrix仪表盘单机监控
@EnableHystrixDashboard
// 开启仪表盘集群监控
@EnableTurbine
public class DashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(DashboardApplication.class, args);
    }

}
