package com.epam.sport.router;

import com.epam.sport.handler.SportHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration(proxyBeanMethods = false)
public class SportRouter {

    @Bean
    public RouterFunction<ServerResponse> routes(SportHandler sportHandler) {
        return RouterFunctions
                .route(GET("/api/v1/sport"), sportHandler::findByName)
                .andRoute(GET("/api/v1/sports"), sportHandler::findAll)
                .andRoute(POST("/api/v1/sport/{sportName}"), sportHandler::postSport);
    }
}

