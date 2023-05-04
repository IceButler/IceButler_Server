package com.example.icebutler_server.map.service;

import com.example.icebutler_server.map.dto.MapDTO;

public interface MapService {

    MapDTO.Location enterMap(String address);
}
