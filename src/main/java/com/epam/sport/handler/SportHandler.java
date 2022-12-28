package com.epam.sport.handler;

import com.epam.sport.model.Sport;
import com.epam.sport.service.SportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class SportHandler {

    @Autowired
    private SportService sportService;

    public Mono<ServerResponse> findByName(ServerRequest request) {
        Optional<String> name = request.queryParam("name");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(sportService.getSportByName(name.orElse("")), Sport.class));
    }

    public Mono<ServerResponse> postSport(ServerRequest request) {
        String sportName = request.pathVariable("sportName");
        return ServerResponse
                .ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(sportService.createSport(sportName), Sport.class));
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(sportService.findAll(), Sport.class));
    }
}
