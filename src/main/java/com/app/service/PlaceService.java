package com.app.service;

import com.app.entity.Place;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author Saurabh Vaish
 * @Date 06-10-2021
 */
public interface PlaceService {
    Mono<Place> savePlace(Place place);

    Mono<Place> updatePlace(Place place);

    Mono<Place> getPlaceById(Long id);

    Mono<Boolean> deletePlaceById(Long id);

    Flux<Place> searchPlace(String cityName);

    Flux<Place> getPlaceByStateName(String stateName);

    Flux<Place> getPlaceBetweenTwoCities(String city1, String city2);

    Flux<Place> getAllPlaces();
}
