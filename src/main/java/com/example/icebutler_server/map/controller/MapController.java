package com.example.icebutler_server.map.controller;


import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.map.dto.MapDTO;
import com.example.icebutler_server.map.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/map")
@RequiredArgsConstructor
public class MapController {
    private final MapService mapService;
    @ResponseBody
    @PostMapping("/address")
    public ResponseCustom<MapDTO.Location> enterMap(@RequestParam String address) {
        return ResponseCustom.OK(mapService.enterMap(address));
    }

}
