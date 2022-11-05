package com.example.Spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    //  https://juejin.cn/post/7133020498434195464
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorParameter(true)
                //  根據傳參mediaType來決定返回樣式
                .parameterName("mediaType")
                .ignoreAcceptHeader(false)
                //  哪個放在前面，哪個的優先級就高； 當上面這個accept未禁用時，若請求傳的accept不能覆蓋下面兩種，則會出現406錯誤
                .defaultContentType(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("json", MediaType.APPLICATION_JSON);
    }
}
