package com.jtrack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	 
	 @Bean
	 public ViewResolver viewResolver() {
	      InternalResourceViewResolver bean = new InternalResourceViewResolver();
	 
	      bean.setViewClass(JstlView.class);
	      bean.setPrefix("/WEB-INF/view/jsp/");
	      bean.setSuffix(".jsp");
	 
	      return bean;
	 }

     @Override
     public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
	      .addResourceHandler("/css/**")
	      .addResourceLocations("WEB-INF/view/css/"); 
        registry
	      .addResourceHandler("/img/**")
	      .addResourceLocations("WEB-INF/view/img/"); 
        registry
	      .addResourceHandler("/js/**")
	      .addResourceLocations("WEB-INF/view/js/"); 
     }
}
