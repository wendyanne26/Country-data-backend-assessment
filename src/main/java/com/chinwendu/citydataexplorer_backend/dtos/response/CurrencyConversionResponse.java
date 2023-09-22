package com.chinwendu.citydataexplorer_backend.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyConversionResponse {
    private String currency;
    private double convertedValue;
}
