package com.chinwendu.citydataexplorer_backend.service;

import com.chinwendu.citydataexplorer_backend.dtos.request.CountryInfoRequest;
import com.chinwendu.citydataexplorer_backend.dtos.response.CountryInfo;
import com.chinwendu.citydataexplorer_backend.dtos.response.CurrencyConversionResponse;
import com.chinwendu.citydataexplorer_backend.exceptions.CustomException;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CityService {
    // List<City> getMostPopulatedCities(int n);

    CountryInfo getCountryInfo(CountryInfoRequest country) throws CustomException;

    ResponseEntity<Map<String, List<String>>> getCountryDataInternal(CountryInfoRequest countryInfoRequest) throws CustomException;


}
