package com.app.repository;

import com.app.entity.Place;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author Saurabh Vaish
 * @Date 06-10-2021
 */
public interface PlaceRepository extends ReactiveCrudRepository<Place,Long> {

    Flux<Place> findByCity(String trim);

    Flux<Place> findByStateIgnoreCase(String trim);

    Flux<Place> findByCityInIgnoreCase(List<String> cities);

    Flux<Place> findByNameIgnoreCaseContaining(String name);
}
