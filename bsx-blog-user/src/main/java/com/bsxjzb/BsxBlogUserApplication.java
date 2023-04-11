package com.bsxjzb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class BsxBlogUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(BsxBlogUserApplication.class,args);
    }
}

