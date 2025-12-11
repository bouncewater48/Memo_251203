package com.bounce.memo.config;

import com.bounce.memo.common.FileManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

//    Override에 빨간 밑줄이 생길 경우 Override를 지우라는게 아닌 아래쪽 메소드에 문제가 생겼다는 뜻임
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        /images/** : 이미지의 실제 경로를 따라간다
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:///" + FileManager.FILE_UPLOAD_PATH);
    }
}
