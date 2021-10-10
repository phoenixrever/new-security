package com.phoenixhell.newsecurity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.phoenixhell.newsecurity.mapper")
public class NewSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewSecurityApplication.class, args);
    }

}
