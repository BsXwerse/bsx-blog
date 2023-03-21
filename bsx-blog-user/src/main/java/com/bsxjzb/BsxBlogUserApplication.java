package com.bsxjzb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class BsxBlogUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(BsxBlogUserApplication.class,args);
    }
}

