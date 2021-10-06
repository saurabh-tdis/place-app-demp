package com.app.repository;

import com.app.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Saurabh Vaish
 * @Date 06-10-2021
 */
public interface PlaceRepository extends JpaRepository<Place,Long> {

    List<Place> findByCity(String trim);

    List<Place> findByStateIgnoreCase(String trim);

    @Query("select p from Place p where lower(p.city) in ?1")
    List<Place> findPlaceByCityIn(List<String> cities);

    @Query("select p from Place p where upper(p.name) like upper(concat('%', ?1, '%'))")
    List<Place> findByNameIgnoreCaseContaining(String trim);
}
