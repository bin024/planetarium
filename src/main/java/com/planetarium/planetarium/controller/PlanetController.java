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
import com.planetarium.planetarium.models.Planet;
import com.planetarium.planetarium.service.PlanetService;

@RestController
public class PlanetController {

    @Autowired
    PlanetService planetService;

    private static Logger planetLogger = LoggerFactory.getLogger(PlanetController.class);

    @ExceptionHandler(EntityNull.class)
    public ResponseEntity<String> entityNotFound(EntityNull e){
        planetLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    //not really needed for planets since there is no unique constraints on names,
    //and the id is not set by user
    //actually, useful for improperly formatted Json
    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<String> sqlIssue(PSQLException e){
        planetLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationFailed.class)
    public ResponseEntity<String> authenticationFailed(AuthenticationFailed e){
        planetLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
    
    @GetMapping("/api/planets")
    public ResponseEntity<List<Planet>> getAllPlanets() {
        planetLogger.info("");
        return new ResponseEntity<>(this.planetService.getAllPlanets(), HttpStatus.OK);
    }

    @GetMapping("/api/planet/{name}")
    public ResponseEntity<List<Planet>> getPlanetByName(@PathVariable String name) {
        planetLogger.info("");
        return new ResponseEntity<>(this.planetService.getPlanetByName(name), HttpStatus.OK);
    }

    @GetMapping("/api/planet/id/{id}")
    public ResponseEntity<Planet> getPlanetByName(@PathVariable int id) {
        planetLogger.info("");
        return new ResponseEntity<>(this.planetService.getPlanetById(id), HttpStatus.OK);
    }

    /* when passing in body as Class, make sure to format the request Json exactly like
     * the name of the fields in the class, for example when passing in the Json for
     * creating a moon, the Json must be:
     * { "name":"someName",
     *   "myPlanetId": 0
     * }
    */
    @PostMapping("/api/planet")
    public ResponseEntity<String> createPlanet(@RequestBody Planet p) {
        planetLogger.info("");
        return new ResponseEntity<>(this.planetService.createPlanet(p), HttpStatus.OK);
    }

    @DeleteMapping("/api/planet/{id}")
    public ResponseEntity<String> deletePlanet(@PathVariable int id) {
        planetLogger.info("");
        return new ResponseEntity<>(this.planetService.deletePlanetById(id), HttpStatus.OK);
    }
}
