package com.fatehole.statistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/12/18/12:51
 */
@EnableScheduling
@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.fatehole")
public class StatisticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatisticsApplication.class, args);
    }
}
