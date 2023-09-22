package com.chinwendu.citydataexplorer_backend.controller;

import com.chinwendu.citydataexplorer_backend.exceptions.CustomException;
import com.chinwendu.citydataexplorer_backend.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/currency")
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyService currencyService;
    @GetMapping("/convert")
    public ResponseEntity<?> convertCurrency(
            @RequestParam String country,
            @RequestParam double amount,
            @RequestParam String targetCurrency) throws CustomException {
        return  new ResponseEntity<>(currencyService.convertCurrency(country, amount, targetCurrency), HttpStatus.OK);
    }
}
