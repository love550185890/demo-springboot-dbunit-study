package com.example.study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.example.study.mapper")
public class DemoSpringbootDbunitStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSpringbootDbunitStudyApplication.class, args);
    }

}
