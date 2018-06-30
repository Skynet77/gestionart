package com.pol.gestionart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EntityScan(basePackages = {"com.pol.gestionart.entity"} )
@EnableJpaRepositories("org.springframework.data.jpa.repository.JpaRepository")
public class GestionartApplication {

	@SuppressWarnings("deprecation")
	@Bean
    WebMvcConfigurer configurer () {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addResourceHandlers (ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/pages/**").
                          addResourceLocations("classpath:/static/");
            }
        };
    }
	
	public static void main(String[] args) {
		SpringApplication.run(GestionartApplication.class, args);
	}
}
