package com.example.icebutler_server.global.util;

import com.example.icebutler_server.global.exception.BaseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.icebutler_server.global.exception.ReturnCode.INTERNAL_SERVER_ERROR;
import static com.example.icebutler_server.global.exception.ReturnCode.NOT_FOUND_BARCODE_FOOD;

@Component
public class FoodBarcodeUtils {

    public static final String FOOD_BARCODE_MAIN_URL = "https://openapi.foodsafetykorea.go.kr/api/";
    public static final String FOOD_BARCODE_DETAIL_URL = "/I2570/json/1/5/BRCD_NO=";
    public static final String FOOD_NAME = "PRDT_NM";
    public static final String SERVICE_ID = "I2570";
    public static final String ROW = "row";
    public static final String HTTP_METHOD_GET = "GET";
    public static final int SUCCESS_CODE_START = 200;
    public static final int SUCCESS_CODE_END = 300;


    //TODO: 배포 설정 후 수정예정
    @Value("${barcode-service-key}")
    private String serviceKey;

    public String callBarcodeApi(String barcodeNum) {
        StringBuilder sb = callAPI(barcodeNum);
        JSONObject data = extractValidData(sb);
        return (String) data.get(FOOD_NAME);
    }

    private StringBuilder callAPI(String barcodeNum) {
        try {
            URL url = new URL(FOOD_BARCODE_MAIN_URL + serviceKey + FOOD_BARCODE_DETAIL_URL + barcodeNum);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(HTTP_METHOD_GET);

            int responseCode = conn.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    isSuccessCode(responseCode) ? conn.getInputStream() : conn.getErrorStream()));
            StringBuilder sb = new StringBuilder();
            br.lines().forEach(sb::append);

            br.close();
            conn.disconnect();
            return sb;
        } catch (IOException e) {
            throw new BaseException(INTERNAL_SERVER_ERROR);
        }
    }

    private boolean isSuccessCode(int responseCode) {
        return responseCode >= SUCCESS_CODE_START && responseCode <= SUCCESS_CODE_END;
    }

    private JSONObject extractValidData(StringBuilder sb) {
        JSONObject obj = getJsonObjectByParser(sb);
        JSONObject result = (JSONObject) obj.get(SERVICE_ID);
        JSONArray row = (JSONArray) result.get(ROW);
        if (row == null) throw new BaseException(NOT_FOUND_BARCODE_FOOD);
        return (JSONObject) row.get(0);
    }

    private JSONObject getJsonObjectByParser(StringBuilder sb) {
        try {
            return (JSONObject) new JSONParser().parse(sb.toString());
        } catch (ParseException e) {
            throw new BaseException(NOT_FOUND_BARCODE_FOOD);
        }
    }
}
