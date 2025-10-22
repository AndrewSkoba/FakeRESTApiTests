package com.bookstore.config;

import com.bookstore.filters.AllureRestAssuredFilter;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "com.bookstore.client")
public class ApiConfig {
    @Bean
    public RequestSpecification requestSpec(@Value("${base.url}") String baseUrl, @Value("${log.level:INFO}") String logLevel) {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssuredFilter());

        switch (logLevel.toUpperCase()) {
            case "DEBUG" -> {
                builder.addFilter(new RequestLoggingFilter());
                builder.addFilter(new ResponseLoggingFilter());
            }
            case "INFO" -> {
                builder.addFilter(new RequestLoggingFilter(LogDetail.URI));
                builder.addFilter(new ResponseLoggingFilter(LogDetail.STATUS));
            }
        }
        return builder.build();
    }
}
