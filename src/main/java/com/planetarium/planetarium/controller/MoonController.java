package com.planetarium.planetarium.controller;

import java.util.List;

import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.planetarium.planetarium.exceptions.AuthenticationFailed;
import com.planetarium.planetarium.exceptions.EntityNull;
import com.planetarium.planetarium.models.Moon;
import com.planetarium.planetarium.service.MoonService;

@RestController
public class MoonController {
    
    @Autowired
    private MoonService moonService;

    private static Logger moonLogger = LoggerFactory.getLogger(MoonController.class);

    @ExceptionHandler(EntityNull.class)
    public ResponseEntity<String> entityNotFound(EntityNull e){
        moonLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    //also not really needed, nvm again, see analogous in PlanetController
    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<String> sqlIssue(PSQLException e){
        moonLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationFailed.class)
    public ResponseEntity<String> authenticationFailed(AuthenticationFailed e){
        moonLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping("/api/moons")
    public ResponseEntity<List<Moon>> getAllMoons() {
        moonLogger.info("");
        return new ResponseEntity<>(this.moonService.getAllMoons(), HttpStatus.OK);
    }

    @GetMapping("/api/moon/{name}")
    public ResponseEntity<List<Moon>> getMoonByName(@PathVariable String name) {
        moonLogger.info("");
        return new ResponseEntity<>(this.moonService.getMoonByName(name), HttpStatus.OK);
    }

    @GetMapping("/api/moon/id/{id}")
    public ResponseEntity<Moon> getPlanetByName(@PathVariable int id) {
        moonLogger.info("");
        return new ResponseEntity<>(this.moonService.getMoonById(id), HttpStatus.OK);
    }

    //PathVariable must match exactly with how it is written in URI
    @GetMapping("/api/planet/{planetId}/moons")
    public ResponseEntity<List<Moon>> getMoonsFromPlanet(@PathVariable int planetId) {
        moonLogger.info("");
        return new ResponseEntity<>(this.moonService.getMoonsFromPlanet(planetId), HttpStatus.OK);
    }

    @PostMapping("/api/moon")
    public ResponseEntity<String> createMoon(@RequestBody Moon m) {
        moonLogger.info("");
        return new ResponseEntity<>(this.moonService.createMoon(m), HttpStatus.OK);
    }

    @DeleteMapping("/api/moon/{id}")
    public ResponseEntity<String> deleteMoon(@PathVariable int id) {
        moonLogger.info("");
        return new ResponseEntity<>(this.moonService.deleteMoonById(id), HttpStatus.OK);
    }
}
