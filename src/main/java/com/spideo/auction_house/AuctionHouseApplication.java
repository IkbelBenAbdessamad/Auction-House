package com.spideo.auction_house;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EnableSwagger2
public class AuctionHouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuctionHouseApplication.class, args);
    }

    @Bean
    public Docket swaggerConfiguration() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.spideo.auction_house"))
                .build()
                .apiInfo(apiDetails());

    }

    private ApiInfo apiDetails() {
        return new ApiInfo(
                "AUCTION HOUSE WEB APP",
                "Develop a web application that manages an auction house",
                "1.0",
                "Free to use",
                new springfox.documentation.service.Contact("IKBEL Benabdessamad", "https://www.linkedin.com/in/ikbel-benab-396895193/", "ikbel.benabdessamad@ieee.org"),
                "API License",
                "Not defined yet",
                Collections.emptyList());
    }
}