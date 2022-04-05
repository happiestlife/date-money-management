package project1.dateMoneyManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project1.dateMoneyManagement.controller.member.interceptor.AfterLoginInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(afterLoginInterceptor())
                .order(1)
                .addPathPatterns("/member/**")
                .addPathPatterns("/")
                .excludePathPatterns("/login");
    }

    @Bean
    public AfterLoginInterceptor afterLoginInterceptor() {
        return new AfterLoginInterceptor();
    }
}
