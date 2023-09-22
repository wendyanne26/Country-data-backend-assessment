package com.chinwendu.citydataexplorer_backend.service.serviceImpl;

import com.chinwendu.citydataexplorer_backend.dtos.request.CountryAndStateInfoRequest;
import com.chinwendu.citydataexplorer_backend.dtos.request.CountryInfoRequest;
import com.chinwendu.citydataexplorer_backend.dtos.response.*;
import com.chinwendu.citydataexplorer_backend.exceptions.CustomException;
import com.chinwendu.citydataexplorer_backend.service.CityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class CityServiceImpl implements CityService {

    private final RestTemplate restTemplate;
    @Value("${country-client-api.base-url}")
    String baseUrl;

    @Override
    public CountryInfo getCountryInfo(CountryInfoRequest request) throws CustomException {
    CountryInfo countryInfo = new CountryInfo();
    countryInfo.setPopulation(getPopulationValue(request.getCountry()));
    countryInfo.setCapitalCity(getCapital(request.getCountry()));
    countryInfo.setLocation(getLocation(request.getCountry()));
    countryInfo.setCurrency(getCurrency(request.getCountry()));
    countryInfo.setIso2(getIso2(request.getCountry()));
    countryInfo.setIso3(getIso3(request.getCountry()));
    return countryInfo;
    }


//    private PopulationResponse getCountryData(String country) {
//        return countryFeignClient.getCountryInfo(country);
//    }
@Override
    public ResponseEntity<Map<String, List<String>>> getCountryDataInternal(CountryInfoRequest countryInfoRequest) throws CustomException {
    List<StateResponse.State> states = getStates(countryInfoRequest.getCountry());
    log.info("response {}", states);
    Map<String, List<String>> countryData = new LinkedHashMap<>(); // Use LinkedHashMap to maintain order

    for (StateResponse.State state : states) {
        List<String> cities = getCities(countryInfoRequest.getCountry(), state.getName());
        countryData.put(state.getName(), cities);
    }
    return ResponseEntity.ok(countryData);
}
    private long getPopulationValue(String country) throws CustomException {
        String apiUrl = baseUrl + "/api/v0.1/countries/population";
        CountryInfoRequest request = new CountryInfoRequest();
        request.setCountry(country);
        HttpHeaders headers = getHeaders();
        HttpEntity<CountryInfoRequest> requestEntity = new HttpEntity<>(request, headers);

        ResponseEntity<PopulationResponse> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                requestEntity,
                PopulationResponse.class
        );

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            PopulationResponse populationResponse = responseEntity.getBody();
            log.info("response {}", populationResponse);

           if (!populationResponse.isError()) {
               List<PopulationResponse.ResponseData.PopulationCount> populationCounts = populationResponse.getData().get(0).getPopulationCounts();
                if (!populationCounts.isEmpty()) {
                    return populationCounts.get(populationCounts.size() - 1).getValue();
                }
            }
            throw new CustomException("operation failed", HttpStatus.INTERNAL_SERVER_ERROR);
            // Return a default value or handle error cases as needed

       }
        return 0;
    }
    private String getCapital(String country) throws CustomException {
        String apiUrl = baseUrl + "/api/v0.1/countries/capital";
        CountryInfoRequest request = new CountryInfoRequest();
        request.setCountry(country);
        HttpHeaders headers = getHeaders();
        HttpEntity<CountryInfoRequest> requestEntity = new HttpEntity<>(request, headers);

        ResponseEntity<CapitalResponse> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                requestEntity,
                CapitalResponse.class
        );
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            CapitalResponse capitalResponse = responseEntity.getBody();
            if (!capitalResponse.isError()) {
                return capitalResponse.getData().get(0).getCapital();
            } else {
                // Handle error cases as needed
                throw new CustomException("operation failed", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return "Error occurred";
    }
    private List<StateResponse.State> getStates(String country) throws CustomException {
        String apiUrl = baseUrl + "/api/v0.1/countries/states";
        CountryInfoRequest request = new CountryInfoRequest();
        request.setCountry(country);
        HttpHeaders headers = getHeaders();
        HttpEntity<CountryInfoRequest> requestEntity = new HttpEntity<>(request, headers);
        ResponseEntity<StateResponse> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                requestEntity,
                StateResponse.class
        );

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            StateResponse stateResponse = responseEntity.getBody();
            if (!stateResponse.isError()){
                return stateResponse.getData().get(0).getStates();
            }
        }
        throw new CustomException("operation for state failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private List<String> getCities(String country, String state) throws CustomException {
        String apiUrl = baseUrl + "/api/v0.1/countries/states/cities";

        CountryAndStateInfoRequest countryAndStateInfoRequest = new CountryAndStateInfoRequest();
        countryAndStateInfoRequest.setCountry(country);
        countryAndStateInfoRequest.setState(state);

        HttpHeaders headers = getHeaders();
        HttpEntity<CountryAndStateInfoRequest> requestEntity = new HttpEntity<>(countryAndStateInfoRequest, headers);
        ResponseEntity<CityResponse> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                CityResponse.class
        );

            CityResponse citiesResponse = responseEntity.getBody();
        log.info("get cities {}",citiesResponse);

        if (citiesResponse != null && citiesResponse.getData() != null) {
                log.info("response {}", citiesResponse.getData());

                return citiesResponse.getData();


        }
        throw new CustomException("operation for cities failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private String getLocation(String country) throws CustomException {
        String apiUrl = baseUrl +"/api/v0.1/countries/positions";
        CountryInfoRequest request = new CountryInfoRequest();
        request.setCountry(country);
        HttpHeaders headers = getHeaders();
        HttpEntity<CountryInfoRequest> requestEntity = new HttpEntity<>(request, headers);


        ResponseEntity<LocationResponse> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                requestEntity,
                LocationResponse.class
        );
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            LocationResponse locationResponse = responseEntity.getBody();
            if (!locationResponse.isError()) {
                double latitude = locationResponse.getData().get(0).getLatitude();
                double longitude = locationResponse.getData().get(0).getLongitude();
                return "Latitude: " + latitude + ", Longitude: " + longitude;
            } else {
                // Handle error cases as needed
                throw new CustomException("operation failed", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return "Error occurred";
    }
    public String getCurrency(String country) throws CustomException {
        ResponseEntity<CurrencyResponse> responseEntity = extract(country);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            CurrencyResponse currencyResponse = responseEntity.getBody();
            if (!currencyResponse.isError()) {
                String currency = currencyResponse.getData().get(0).getCurrency();
                return currency;
            } else {
                // Handle error cases as needed
                throw new CustomException("operation failed", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return "Error occurred";
    }

    private ResponseEntity<CurrencyResponse> extract(String country) {
        String apiUrl = baseUrl + "/api/v0.1/countries/currency";
        CountryInfoRequest request = new CountryInfoRequest();
        request.setCountry(country);
        HttpHeaders headers = getHeaders();
        HttpEntity<CountryInfoRequest> requestEntity = new HttpEntity<>(request, headers);


        ResponseEntity<CurrencyResponse> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                requestEntity,
                CurrencyResponse.class
        );
        return responseEntity;
    }

    private String getIso2(String country) throws CustomException {
       ResponseEntity<CurrencyResponse> responseEntity = extract(country);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            CurrencyResponse currencyResponse = responseEntity.getBody();
            if (!currencyResponse.isError()) {
                String iso2 = currencyResponse.getData().get(0).getIso2();
                return iso2;
            } else {
                // Handle error cases as needed
                throw new CustomException("operation failed", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return "Error occurred";
    }

    private String getIso3(String country) throws CustomException {
        ResponseEntity<CurrencyResponse> responseEntity = extract(country);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            CurrencyResponse currencyResponse = responseEntity.getBody();
            if (!currencyResponse.isError()) {
                String iso3 = currencyResponse.getData().get(0).getIso3();
                return iso3;
            } else {
                // Handle error cases as needed
                throw new CustomException("operation failed", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return "Error occurred";
    }
    private HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }
}


