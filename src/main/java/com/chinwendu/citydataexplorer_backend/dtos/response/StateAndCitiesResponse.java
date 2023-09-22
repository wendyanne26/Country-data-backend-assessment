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
public class StateAndCitiesResponse {
    private List<StateResponse.State> stateResponseList;
    private List<CityResponse> cityResponseList;
}
