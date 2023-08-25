package com.planetarium.planetarium.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.planetarium.planetarium.models.Moon;

public interface MoonDao extends JpaRepository<Moon, Integer>{

    //made this a list since the name has no unique constraint
    List<Moon> findByName(String name);

    @Modifying
    @Transactional
    @Query(value = "insert into moons values (default, :name, :myplanetid)", nativeQuery = true) 
    void createPlanet(@Param("name") String name, @Param("myplanetid") int myPlanetId);

    @Modifying
    @Transactional
    @Query(value = "delete from moons where id = :id", nativeQuery = true) 
    int deleteById(@Param("id") int id);

    @Query(value = "select * from moons where myplanetid = :id", nativeQuery = true)
    public List<Moon> getMoonsFromPlanet(@Param("id") int planetId);
}
