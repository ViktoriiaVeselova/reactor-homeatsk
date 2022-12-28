package com.epam.sport.service;

import com.epam.sport.exception.SportAlreadyExistsException;
import com.epam.sport.exception.SportNotFoundException;
import com.epam.sport.model.Sport;
import com.epam.sport.repository.SportRepository;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SportService {

    @Autowired
    private SportRepository sportRepository;

    public Mono<Sport> getSportByName(String name) {
        return sportRepository.findByName(name).switchIfEmpty(Mono.error(new SportNotFoundException(name)));
    }

    public Flux<Sport> createSports(Publisher<Sport> sports) {
        return sportRepository.saveAll(sports);
    }

    public Mono<Sport> createSport(Sport sport) {
        return sportRepository.save(sport);
    }

    public Mono<Sport> createSport(String name) {
        return sportRepository
                .save(new Sport(name))
                .onErrorMap(Exception.class, e -> new SportAlreadyExistsException(name));
    }

    public Flux<Sport> findAll() {
        return sportRepository.findAll();
    }
}
