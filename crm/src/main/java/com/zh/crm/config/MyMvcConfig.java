package com.zh.crm.config;

import com.zh.crm.util.FileUploadProperteis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class MyMvcConfig  implements WebMvcConfigurer {

    @Autowired
    private FileUploadProperteis fileUploadProperteis;
   @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(fileUploadProperteis.getStaticAccessPath()).addResourceLocations("file:"+fileUploadProperteis.getUploadFolder()+"/");
    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/record").setViewName("view/report/record");
    }
}
