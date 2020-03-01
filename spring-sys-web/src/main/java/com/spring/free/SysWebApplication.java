package com.spring.free;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
//import org.springframework.boot.web.server.ConfigurableWebServerFactory;
//import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 启动类
 * 1.JAVA 8 实现通用的HTTP错误状态码；2.Spring Boot 启动方法
 * @author Memory
 * @date 2017/1/9
 * @Inc 恒谱科技
 */
@SpringBootApplication
@Configuration
@ComponentScan(basePackages={"com.spring.free.controller","com.spring.free.scheduled", "com.spring.free.*", "com.spring.free.sys.*", "com.spring.free.common.*", "com.spring.fee.service.*"})
@EnableAutoConfiguration
@MapperScan("com.spring.fee.dao.mapper")
@EnableAsync
@EnableScheduling
public class SysWebApplication {

    /**
     * JAVA 8 实现通用的HTTP错误状态码
     * @return
     */
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {

        return (container -> {
            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
            ErrorPage error405Page = new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, "/405.html");
            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");

            container.addErrorPages(error401Page, error404Page, error405Page, error500Page);

        });
    }
    @Bean
    public TaskScheduler taskScheduler(){
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        taskScheduler.setThreadNamePrefix("springboot-task");
        return  taskScheduler;
    }

    /**
     * 文件上传配置
     * @return
     */
//    @Bean
//    public MultipartConfigElement multipartConfigElement() {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        //文件最大
//        factory.setMaxFileSize("1024KB"); //KB,MB
//        /// 设置总上传数据总大小
//        factory.setMaxRequestSize("10240KB");
//        return factory.createMultipartConfig();
//    }




    /**
     * Spring Boot 启动方法
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(SysWebApplication.class, args);
    }
}
