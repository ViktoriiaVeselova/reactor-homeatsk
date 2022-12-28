package com.epam.sport.repository;

import com.epam.sport.model.Sport;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface SportRepository extends ReactiveCrudRepository<Sport, Long> {

    @Query("SELECT * FROM sport WHERE name = :name")
    Mono<Sport> findByName(String name);
}
