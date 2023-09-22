package com.chinwendu.citydataexplorer_backend.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PopulationResponse {
    private boolean error;
    private String msg;
    @JsonProperty("data")
    private List<ResponseData> data;
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseData {
        private String country;
        private String code;
        private String iso3;
        private List<PopulationCount> populationCounts;

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class PopulationCount {
            private int year;
            private long value;
        }

    }
}


