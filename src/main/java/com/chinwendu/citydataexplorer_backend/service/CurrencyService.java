package com.chinwendu.citydataexplorer_backend.service;

import com.chinwendu.citydataexplorer_backend.dtos.response.CurrencyConversionResponse;
import com.chinwendu.citydataexplorer_backend.exceptions.CustomException;

public interface CurrencyService {
    CurrencyConversionResponse convertCurrency(String country, double amount, String targetCurrency) throws CustomException;
}
