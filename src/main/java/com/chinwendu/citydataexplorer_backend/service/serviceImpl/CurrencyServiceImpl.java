package com.chinwendu.citydataexplorer_backend.service.serviceImpl;

import com.chinwendu.citydataexplorer_backend.dtos.response.CurrencyConversionResponse;
import com.chinwendu.citydataexplorer_backend.exceptions.CustomException;
import com.chinwendu.citydataexplorer_backend.service.CurrencyService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {
    private final Map<String, Map<String, Double>> exchangeRates = new HashMap<>();
    private final CityServiceImpl cityService;


    @Override
    public CurrencyConversionResponse convertCurrency(String country, double amount, String targetCurrency) throws CustomException {
        if (!exchangeRates.containsKey(country) || !exchangeRates.get(country).containsKey(targetCurrency)) {
            return null;
        }

        double exchangeRate = exchangeRates.get(country).get(targetCurrency);
        double convertedAmount = amount * exchangeRate;

        CurrencyConversionResponse response = new CurrencyConversionResponse();
        response.setCurrency(cityService.getCurrency(country));
        response.setConvertedValue(convertedAmount);

        return response;
    }


        @PostConstruct
        public void loadExchangeRates() {
            String file = "src//exchange_rate.csv";
            String line;
            String cvsSplitBy = ",";

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(cvsSplitBy);
                    if (data.length == 3) {
                        String sourceCurrency = data[0];
                        String targetCurrency = data[1];
                        double rate = Double.valueOf(data[2]);

                        exchangeRates.computeIfAbsent(sourceCurrency, k -> new HashMap<>()).put(targetCurrency, rate);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception appropriately (e.g., log, throw custom exception)
            }

        }

    }

