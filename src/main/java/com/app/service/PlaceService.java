package com.app.service;

import com.app.entity.Place;

import java.util.List;

/**
 * @author Saurabh Vaish
 * @Date 06-10-2021
 */
public interface PlaceService {
    Place savePlace(Place place);

    Place updatePlace(Place place);

    Place getPlaceById(Long id);

    Boolean deletePlaceById(Long id);

    List<Place> searchPlace(String cityName);

    List<Place> getPlaceByStateName(String stateName);

    List<Place> getPlaceBetweenTwoCities(String city1, String city2);

    List<Place> getAllPlaces();
}
