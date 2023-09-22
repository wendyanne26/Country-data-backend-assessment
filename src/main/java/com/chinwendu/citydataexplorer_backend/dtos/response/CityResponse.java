package com.chinwendu.citydataexplorer_backend.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CityResponse {
    private boolean error;
    private String msg;
    private List<String> data;

}
