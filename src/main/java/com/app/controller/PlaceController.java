package com.app.controller;

import com.app.dto.ApiResponse;
import com.app.entity.Place;
import com.app.service.PlaceService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Saurabh Vaish
 * @Date 06-10-2021
 */


@RestController
@RequestMapping("/api/")
@Log4j2
public class PlaceController {

    @Autowired
    private PlaceService placeService;


//    @PostMapping(value = "save-place",produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    public ResponseEntity<ApiResponse> savePlace(@RequestBody Place place){
//        Place placeDb = this.placeService.savePlace(place);
//        log.info("Place saved successfully");
//        return ResponseEntity.ok().body(new ApiResponse(placeDb,"Place saved successfully", HttpStatus.OK));
//    }
//
//    @PutMapping("update-place")
//    public ResponseEntity<ApiResponse> updatePlace(@RequestBody Place place){
//        Place placeDb = this.placeService.updatePlace(place);
//        log.info("Place updated successfully");
//        return ResponseEntity.ok().body(new ApiResponse(placeDb,"Place updated successfully", HttpStatus.OK));
//    }
//
//
//    @GetMapping("get-place-by-id/{id}")
//    public ResponseEntity<ApiResponse> getPlace(@PathVariable Long id){
//        Place placeDb = this.placeService.getPlaceById(id);
//        log.info("Getting place by id ");
//        return ResponseEntity.ok().body(new ApiResponse(placeDb,"SUCCESS", HttpStatus.OK));
//    }
//
//    @GetMapping("get-all-places")
//    public ResponseEntity<ApiResponse> getAllPlaces(){
//        List<Place> placeDb = this.placeService.getAllPlaces();
//        log.info("Getting place by id ");
//        return ResponseEntity.ok().body(new ApiResponse(placeDb,"SUCCESS", HttpStatus.OK));
//    }
//
//
//    @DeleteMapping("delete-place-by-id/{id}")
//    public ResponseEntity<ApiResponse> deletePlace(@PathVariable Long id){
//        Boolean isSuccess = this.placeService.deletePlaceById(id);
//        log.info("Deleting place by id ");
//        return ResponseEntity.ok().body(new ApiResponse(isSuccess,"SUCCESS", HttpStatus.OK));
//    }
//
//
//    @GetMapping("search-place")
//    public ResponseEntity<ApiResponse> searchPlace(@RequestParam String place){
//        List<Place> places = this.placeService.searchPlace(place);
//        log.info("Getting place by city name ");
//        return ResponseEntity.ok().body(new ApiResponse(places,"SUCCESS", HttpStatus.OK));
//    }
//
//
//    @GetMapping("get-place-by-state")
//    public ResponseEntity<ApiResponse> getPlaceByState(@RequestParam String stateName){
//        List<Place> places = this.placeService.getPlaceByStateName(stateName);
//        log.info("Getting place by state name ");
//        return ResponseEntity.ok().body(new ApiResponse(places,"SUCCESS", HttpStatus.OK));
//    }
//
//
//
//    @GetMapping("get-place-between-two-cities")
//    public ResponseEntity<ApiResponse> getPlaceBetweenTwoCities(@RequestParam String city1,@RequestParam String city2){
//        List<Place> places = this.placeService.getPlaceBetweenTwoCities(city1,city2);
//        log.info("Getting place in two cities ");
//        return ResponseEntity.ok().body(new ApiResponse(places,"SUCCESS", HttpStatus.OK));
//    }


    @PostMapping(value = "save-place",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Mono<ResponseEntity<ApiResponse>> savePlace(@RequestBody Place place){
        return this.placeService.savePlace(place).map(placeDb->{
            log.info("Place saved successfully");
            return ResponseEntity.ok().body(new ApiResponse(placeDb,"Place saved successfully", HttpStatus.OK));
        });
    }

    @PutMapping("update-place")
    public Mono<ResponseEntity<ApiResponse>> updatePlace(@RequestBody Place place){
        return this.placeService.updatePlace(place).map(placeDb->{
            log.info("Place updated successfully");
            return ResponseEntity.ok().body(new ApiResponse(placeDb,"Place updated successfully", HttpStatus.OK));
        });
    }


    @GetMapping("get-place-by-id/{id}")
    public Mono<ResponseEntity<ApiResponse>> getPlace(@PathVariable Long id){
        log.info("Getting place by id ");
        return  this.placeService.getPlaceById(id).map(place-> ResponseEntity.ok().body(new ApiResponse(place,"SUCCESS", HttpStatus.OK)));
    }

    @GetMapping("get-all-places")
    public Flux<Place> getAllPlaces(){
        log.info("Getting place by id ");
        return this.placeService.getAllPlaces();
//                .flatMap(placeDb->{
//            return Flux.just(new ApiResponse(placeDb,"SUCCESS", HttpStatus.OK));
//        });
    }


    @DeleteMapping("delete-place-by-id/{id}")
    public Mono<ResponseEntity<ApiResponse>> deletePlace(@PathVariable Long id){
        return this.placeService.deletePlaceById(id).map(isSuccess->{
            log.info("Deleting place by id ");
            return ResponseEntity.ok().body(new ApiResponse(isSuccess,"SUCCESS", HttpStatus.OK));
        });
    }


    @GetMapping("search-place")
    public Flux<Place> searchPlace(@RequestParam String place){
        log.info("Getting place by city name ");
        return this.placeService.searchPlace(place);
//        return ResponseEntity.ok().body(new ApiResponse(places,"SUCCESS", HttpStatus.OK));
    }


    @GetMapping("get-place-by-state")
    public Flux<Place> getPlaceByState(@RequestParam String stateName){
        log.info("Getting place by state name ");
        return this.placeService.getPlaceByStateName(stateName);
//        return ResponseEntity.ok().body(new ApiResponse(places,"SUCCESS", HttpStatus.OK));
    }



    @GetMapping("get-place-between-two-cities")
    public Flux<Place> getPlaceBetweenTwoCities(@RequestParam String city1, @RequestParam String city2){
        log.info("Getting place in two cities ");
        return this.placeService.getPlaceBetweenTwoCities(city1,city2);
//        return ResponseEntity.ok().body(new ApiResponse(places,"SUCCESS", HttpStatus.OK));
    }



}
