package com.planetarium.planetarium.repository;

import java.util.List;
//import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.planetarium.planetarium.models.Planet;

public interface PlanetDao extends JpaRepository<Planet, Integer>{

    //made this a list since the name has no unique constraint
    List<Planet> findByName(String name);

    @Modifying
    @Transactional
    @Query(value = "insert into planets values (default, :name, :ownerid)", nativeQuery = true) 
    void createPlanet(@Param("name") String name, @Param("ownerid") int ownerId);

    @Modifying
    @Transactional
    @Query(value = "delete from planets where id = :id", nativeQuery = true) 
    int deleteById(@Param("id") int id);
    
}
