package com.example.icebutler_server.map.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
public class MapDTO {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class Location{
        private String x;
        private String y;
    }
}
