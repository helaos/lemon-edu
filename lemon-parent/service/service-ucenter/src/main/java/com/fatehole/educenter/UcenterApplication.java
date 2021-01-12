package com.fatehole.educenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/11/29/22:56
 */
@EnableFeignClients
@EnableDiscoveryClient
@ComponentScan(basePackages = { "com.fatehole" })
@SpringBootApplication
public class UcenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class, args);
    }

}
