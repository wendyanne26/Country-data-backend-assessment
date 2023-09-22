package com.chinwendu.citydataexplorer_backend.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CountryInfo {
    private long population;
    private String capitalCity;
    private String location;
    private String currency;
    private String iso2;
    private String iso3;
}
