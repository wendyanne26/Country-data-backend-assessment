package com.chinwendu.citydataexplorer_backend.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LocationResponse {
    private boolean error;
    private String msg;
    private List<Data> data;
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Data {
        private String name;
        private String iso2;
        private double longitude;
        private double latitude;


    }


}
