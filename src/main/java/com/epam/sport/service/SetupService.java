package com.epam.sport.service;

import com.epam.sport.dto.SportResponse;
import com.epam.sport.model.Sport;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Objects;

@Service
public class SetupService {

    public static final String URL = "https://sports.api.decathlon.com/sports";

    @Autowired
    private SportService sportService;

    @Autowired
    private WebClient webClient;

    public void setUp() {
        sportService.createSports(
                        webClient.get()
                                .uri(URL)
                                .retrieve()
                                .bodyToFlux(SportResponse.class)
                                .map(SportResponse::data)
                                .flatMap(Flux::fromIterable)
                                .map(data -> new Sport(data.attributes().name()))
                                .filter(sport -> Objects.nonNull(sport.getName()))
                                .distinct(Sport::getName))
                .blockLast(Duration.ofSeconds(10));
    }

    public void setUpWithBackPressure() {

        webClient.get()
                .uri(URL)
                .retrieve()
                .bodyToFlux(SportResponse.class)
                .map(SportResponse::data)
                .flatMap(Flux::fromIterable)
                .map(data -> new Sport(data.attributes().name()))
                .filter(sport -> Objects.nonNull(sport.getName()))
                .distinct(Sport::getName)
                .log()
                .subscribe(new SportBaseSubscriber());
    }

    private class SportBaseSubscriber extends BaseSubscriber<Sport> {
        private Subscription subscription;
        int onNextAmount;

        @Override
        protected void hookOnSubscribe(Subscription subscription) {
            this.subscription = subscription;
            this.subscription.request(20);
        }

        @Override
        protected void hookOnNext(Sport value) {
            sportService.createSport(value).subscribe();
            onNextAmount++;
            if (onNextAmount % 20 == 0) {
                this.subscription.request(20);
            }
        }
    }
}
