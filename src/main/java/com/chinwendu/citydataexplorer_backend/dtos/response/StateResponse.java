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
public class StateResponse {
    private boolean error;
    private String msg;
    private List<Data> data;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Data {
        private String name;
        private String iso3;
        private List<State> states;


        }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class State {
        private String name;
        private String stateCode;


    }
}
