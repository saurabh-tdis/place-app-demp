package com.app.service.impl;

import com.app.entity.Place;
import com.app.entity.Status;
import com.app.exception.PlaceNotFoundException;
import com.app.repository.PlaceRepository;
import com.app.service.PlaceService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * @author Saurabh Vaish
 * @Date 06-10-2021
 */


@Service
@Transactional
@Log4j2
public class PlaceServiceImpl implements PlaceService {


    @Autowired
    private PlaceRepository placeRepository;

    @Override
    public Mono<Place> savePlace(Place place) {
        if(!StringUtils.hasText(place.getCity())){
            throw new PlaceNotFoundException("Please provide valid place city");
        }
        if(!StringUtils.hasText(place.getName())){
            throw new PlaceNotFoundException("Please provide valid place name");
        }
        if(!StringUtils.hasText(place.getState())){
            throw new PlaceNotFoundException("Please provide valid place state");
        }

        place.setStatus(Status.ACTIVE);
        place.setCreatedDate(LocalDate.now());
        place = this.placeRepository.save(place);

        log.info("place is saved in db with id "+place.getId());
        return Mono.just(place);
    }

    @Override
    public Mono<Place> updatePlace(Place place) {
        if(place.getId()==null){
            throw new PlaceNotFoundException("Please provide valid place id");
        }
        Place placeDb = this.placeRepository.findById(place.getId()).orElseThrow(()->new PlaceNotFoundException("No Place Found with given id"));

        if(StringUtils.hasText(place.getCity())){
           placeDb.setCity(place.getCity());
        }
        if(StringUtils.hasText(place.getName())){
           placeDb.setName(place.getName());
        }
        if(StringUtils.hasText(place.getState())){
           placeDb.setState(place.getState());
        }

        place = this.placeRepository.save(placeDb);

        log.info("place is updated in db with id "+place.getId());
        return Mono.just(place);
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Place> getPlaceById(Long id) {
        if(id==null){
            throw new PlaceNotFoundException("Please provide valid place id");
        }
        log.info("Returning place with given id "+id);
        return Mono.just(this.placeRepository.findById(id).orElseThrow(()->new PlaceNotFoundException("No Place Found with given id")));
    }

    @Override
    public Mono<Boolean> deletePlaceById(Long id) {
        this.getPlaceById(id).flatMap(place->{
            place.setStatus(Status.INACTIVE);

            log.info("Place is deleted with id = "+id);
            placeRepository.save(place);
            return Mono.just(place);
        }).subscribe();
        return Mono.just(true);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<Place> searchPlace(String cityName) {
        if(!StringUtils.hasText(cityName)){
            throw new PlaceNotFoundException("Please provide valid city name");
        }
        log.info("Returning place with given cityName "+cityName);
        return Flux.fromIterable(this.placeRepository.findByNameIgnoreCaseContaining(cityName.trim()))
                .switchIfEmpty(r->Flux.error(new PlaceNotFoundException("No place found with the following city name")));
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<Place> getPlaceByStateName(String stateName) {
        if(!StringUtils.hasText(stateName)){
            throw new PlaceNotFoundException("Please provide valid state name");
        }
        return Flux.fromIterable(this.placeRepository.findByStateIgnoreCase(stateName.trim()))
                .switchIfEmpty(r->Flux.error(new PlaceNotFoundException("No place found with the following state name")));
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<Place> getPlaceBetweenTwoCities(String city1, String city2) {
        if(!StringUtils.hasText(city1)){
            throw new PlaceNotFoundException("Please provide valid first city name");
        }

        if(!StringUtils.hasText(city2)){
            throw new PlaceNotFoundException("Please provide valid second city name");
        }

        return Flux.fromIterable(this.placeRepository.findPlaceByCityIn(Arrays.asList(city1.toLowerCase().trim(),city2.toLowerCase().trim())));
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<Place> getAllPlaces() {
        return Flux.fromIterable(placeRepository.findAll());
    }
}
