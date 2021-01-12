package com.fatehole.aclservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/12/22/12:10
 */
@MapperScan("com.fatehole.aclservice.mapper")
@EnableDiscoveryClient
@ComponentScan("com.fatehole")
@SpringBootApplication
public class AclApplication {

    public static void main(String[] args) {
        SpringApplication.run(AclApplication.class, args);
    }

}
