package com.planetarium.planetarium.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planetarium.planetarium.exceptions.EntityNull;
import com.planetarium.planetarium.models.Moon;
import com.planetarium.planetarium.repository.MoonDao;

@Service
public class MoonService {
    
    @Autowired
    private MoonDao moonDao;

    public List<Moon> getAllMoons() {
        List<Moon> moons = this.moonDao.findAll();

        if (moons.size() != 0) {
            return moons;
        } else {
            throw new EntityNull("No moons found.");
        }
    }

    public List<Moon> getMoonByName(String name) {

        List<Moon> possibleMoons = this.moonDao.findByName(name);
        
        if(possibleMoons.size() != 0){
            return possibleMoons;
        } else {
            throw new EntityNull("Moon(s) not found.");
        }
    }

    //not a List return type since id is unique
    public Moon getMoonById(int id) {
        Optional<Moon> possibleMoon = this.moonDao.findById(id);

        if(possibleMoon.isPresent()){
            return possibleMoon.get();
        } else {
            throw new EntityNull("Moon not found.");
        }
    }

    public String createMoon(Moon m) {
        this.moonDao.createPlanet(m.getName(), m.getMyPlanetId());

        return "Moon created.";
    }

    public String deleteMoonById(int id) {
        int rowsAffected = this.moonDao.deleteById(id);

        if (rowsAffected != 0) {
            return "Moon deleted.";
        }
        else {
            throw new EntityNull("Moon does not exist.");
        }
        
    }

    public List<Moon> getMoonsFromPlanet(int planetId)  {
        List<Moon> moons = this.moonDao.getMoonsFromPlanet(planetId);

        if (moons.size() != 0) {
            return moons;
        } else {
            throw new EntityNull("No moon(s) found.");
        }
    }

}
