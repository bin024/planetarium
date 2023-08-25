package com.planetarium.planetarium.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planetarium.planetarium.exceptions.EntityNull;
import com.planetarium.planetarium.models.Planet;
import com.planetarium.planetarium.repository.PlanetDao;

@Service
public class PlanetService {
    
    @Autowired
    private PlanetDao planetDao;

    public List<Planet> getAllPlanets() {
        List<Planet> planets = this.planetDao.findAll();

        if (planets.size() != 0) {
            return planets;
        } else {
            throw new EntityNull("No planets found.");
        }
    }

    public List<Planet> getPlanetByName(String name) {

        List<Planet> possiblePlanets = this.planetDao.findByName(name);
        
        if(possiblePlanets.size() != 0){
            return possiblePlanets;
        } else {
            throw new EntityNull("Planet(s) not found.");
        }
    }
    
    //not a List return type since id is unique
    public Planet getPlanetById(int id) {
        Optional<Planet> possiblePlanet = this.planetDao.findById(id);

        if(possiblePlanet.isPresent()){
            return possiblePlanet.get();
        } else {
            throw new EntityNull("Planet not found.");
        }
    }

    public String createPlanet(Planet p) {
        this.planetDao.createPlanet(p.getName(), p.getOwnerId());

        return "Planet created.";
    }

    public String deletePlanetById(int id) {
        int rowsAffected = this.planetDao.deleteById(id);

        if (rowsAffected != 0) {
            return "Planet deleted.";
        } 
        else {
            throw new EntityNull("Planet does not exist.");
        }
    }

}
