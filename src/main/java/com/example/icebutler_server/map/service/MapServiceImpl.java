package com.example.icebutler_server.map.service;

import com.example.icebutler_server.map.dto.MapDTO;
import com.example.icebutler_server.map.exception.NoHeaderException;
import com.example.icebutler_server.map.exception.NoResponseAPIException;
import lombok.RequiredArgsConstructor;

import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.example.icebutler_server.global.util.Constant.*;
import static com.example.icebutler_server.global.util.TokenUtils.accessKeyId;
import static com.example.icebutler_server.global.util.TokenUtils.secretKey;


@Service
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {


    @Override
    public MapDTO.Location enterMap(String roadFullAddr) {
        String apiUrl = API_URL;
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(ACCESS_KEYID, accessKeyId);
        headers.add(SECRET_KEY, secretKey);
        String body = "";
        String query = roadFullAddr;
        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        if(requestEntity.equals(null)){
            throw new NoHeaderException();
        }

        String url = apiUrl + "?query="  + query;
        ResponseEntity<String> responseEntity = rest.exchange(url, HttpMethod.GET, requestEntity, String.class);
        if(responseEntity.equals(null)){
            throw new NoResponseAPIException();
        }
        JSONObject rjson = new JSONObject(responseEntity.getBody());
        JSONObject documents = (JSONObject) rjson.getJSONArray("documents").get(0);
        JSONObject address = documents.getJSONObject("address");


        return new MapDTO.Location(address.getString("x"), address.getString("y"));
    }

}
