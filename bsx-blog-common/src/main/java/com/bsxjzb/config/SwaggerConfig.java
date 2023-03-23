package com.bsxjzb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import springfox.documentation.builders.*;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

//todo 搞明白这个配置
@Configuration
@EnableOpenApi //注解启动用Swagger的使用，同时在配置类中对Swagger的通用参数进行配置
public class SwaggerConfig {
    @Bean
    public Docket createRestApi(){
        //返回文档概要信息
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .globalRequestParameters(getGlobalRequestParameters())
                .globalResponses(HttpMethod.GET,getGlobalResponseMessage())
                .globalResponses(HttpMethod.POST,getGlobalResponseMessage());
    }

    /*
    生成接口信息，包括标题，联系人等
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger3接口文档")
                .description("如有疑问,可联系百度")
                .contact(new Contact("李白","http://www.baidu.com","baidu@qq.com"))
                .version("1.0")
                .build();
    }


    /*
    封装全局通用参数
     */
    private List<RequestParameter> getGlobalRequestParameters() {
        List<RequestParameter> parameters=new ArrayList<>();
        parameters.add(new RequestParameterBuilder()
                .name("uuid")
                .description("设备uuid")
                .required(true)
                .in(ParameterType.QUERY)
                .query(q->q.model(m->m.scalarModel((ScalarType.STRING))))
                .required(false)
                .build());

        return parameters;
    }
    /*
    封装通用相应信息
     */
    private List<Response> getGlobalResponseMessage() {
        List<Response> responseList=new ArrayList<>();
        responseList.add(new ResponseBuilder().code("404").description("未找到资源").build());
        return responseList;
    }
}
