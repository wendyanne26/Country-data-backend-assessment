package com.chinwendu.citydataexplorer_backend.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CountryAndStateInfoRequest {
    private String country;
    private String state;
}
