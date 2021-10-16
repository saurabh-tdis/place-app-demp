package com.app.service.impl;

import com.app.entity.Place;
import com.app.entity.Status;
import com.app.exception.PlaceNotFoundException;
import com.app.repository.PlaceRepository;
import com.app.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Arrays;

/**
 * @author Saurabh Vaish
 * @Date 06-10-2021
 */


@Service
@Transactional
@Log4j2
@RequiredArgsConstructor(onConstructor_ ={@Autowired} )
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;

    @Override
    public Mono<Place> savePlace(Place p) {

        return Mono.just(p).flatMap(place->{
            if(!StringUtils.hasText(place.getCity())){
                return Mono.error(new PlaceNotFoundException("Please provide valid place city"));
            }
            if(!StringUtils.hasText(place.getName())){
                return Mono.error( new PlaceNotFoundException("Please provide valid place name"));
            }
            if(!StringUtils.hasText(place.getState())){
                return Mono.error(new PlaceNotFoundException("Please provide valid place state"));
            }

            place.setStatus(Status.ACTIVE);
            place.setCreatedDate(LocalDate.now());
            log.info("place is saved in db with id "+place.getId());
            return this.placeRepository.save(place);
        });
    }

    @Override
    public Mono<Place> updatePlace(Place place) {
        return this.placeRepository.findById(place.getId()).switchIfEmpty(Mono.error(new PlaceNotFoundException("No Place Found with given id")))
                .flatMap(placeDb-> {

                    if (StringUtils.hasText(place.getCity())) {
                        placeDb.setCity(place.getCity());
                    }

                    if (StringUtils.hasText(place.getName())) {
                        placeDb.setName(place.getName());
                    }

                    if (StringUtils.hasText(place.getState())) {
                        placeDb.setState(place.getState());
                    }

                    log.info("place is updated in db with id " + place.getId());
                    return this.placeRepository.save(placeDb);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Place> getPlaceById(Long id) {

        log.info("Returning place with given id "+id);
        return this.placeRepository.findById(id).switchIfEmpty(Mono.error(new PlaceNotFoundException("No Place Found with given id")));
    }

    @Override
    public Mono<Boolean> deletePlaceById(Long id) {
        return this.getPlaceById(id).flatMap(place->{
            place.setStatus(Status.INACTIVE);

            log.info("Place is deleted with id = "+id);
            placeRepository.save(place).subscribe();
            return Mono.just(place);
        }).map(place -> place.getStatus().equals(Status.INACTIVE));
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<Place> searchPlace(String cityName) {
        if(!StringUtils.hasText(cityName)){
            return Flux.error(new PlaceNotFoundException("Please provide valid city name"));
        }
        log.info("Returning place with given cityName "+cityName);
        return this.placeRepository.findByNameIgnoreCaseContaining(cityName.trim())
                .switchIfEmpty(Flux.error(new PlaceNotFoundException("No place found with the following city name")));
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<Place> getPlaceByStateName(String stateName) {
        if(!StringUtils.hasText(stateName)){
            return Flux.error(new PlaceNotFoundException("Please provide valid state name"));
        }
        return this.placeRepository.findByStateIgnoreCase(stateName.trim())
                .switchIfEmpty(Mono.error(new PlaceNotFoundException("No place found with the following state name")));
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<Place> getPlaceBetweenTwoCities(String city1, String city2) {
        if(!StringUtils.hasText(city1)){
            return Flux.error(new PlaceNotFoundException("Please provide valid first city name"));
        }

        if(!StringUtils.hasText(city2)){
            return Flux.error(new PlaceNotFoundException("Please provide valid second city name"));
        }

        return this.placeRepository.findByCityInIgnoreCase(Arrays.asList(city1.toUpperCase().trim(),city2.toUpperCase().trim()))
                .switchIfEmpty(Mono.error(new PlaceNotFoundException("No place found with the following names")));
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<Place> getAllPlaces() {
        return placeRepository.findAll();
    }


}
