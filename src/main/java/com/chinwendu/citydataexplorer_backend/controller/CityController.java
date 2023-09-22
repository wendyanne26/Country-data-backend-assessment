package com.chinwendu.citydataexplorer_backend.controller;

import com.chinwendu.citydataexplorer_backend.dtos.request.CountryInfoRequest;
import com.chinwendu.citydataexplorer_backend.exceptions.CustomException;
import com.chinwendu.citydataexplorer_backend.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/city")
@RequiredArgsConstructor
public class CityController {
    private final CityService cityService;
//    @GetMapping("/get-most-populated-cities")
//    public ResponseEntity<?> getMostPopulatedCities(@RequestParam(name = "N", required = true) int N){
//        return new ResponseEntity<>(cityService.getMostPopulatedCities(N), HttpStatus.OK);
//    }
    @GetMapping("/get-country-info")
    public ResponseEntity<?> getCountryInfo(@RequestParam String country) throws CustomException {
        CountryInfoRequest countryInfoRequest = new CountryInfoRequest(country);
     return new ResponseEntity<>(cityService.getCountryInfo(countryInfoRequest), HttpStatus.OK);
    }
    @GetMapping("/get-all-states-cities-in-state")
    public ResponseEntity<?> getAllStatesAndCitiesInState(@RequestBody CountryInfoRequest countryInfoRequest) throws CustomException {
        return  new ResponseEntity<>(cityService.getCountryDataInternal(countryInfoRequest), HttpStatus.OK);
    }


    }
