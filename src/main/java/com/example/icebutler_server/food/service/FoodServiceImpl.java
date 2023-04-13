package com.example.icebutler_server.food.service;

import com.example.icebutler_server.food.dto.response.BarcodeFoodRes;
import com.example.icebutler_server.food.dto.response.FoodRes;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.food.exception.BarcodeFoodNotFoundException;
import com.example.icebutler_server.food.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FoodServiceImpl implements FoodService{
    //TODO: 배포 설정 후 수정예정
//    @Value("${barcode-service-key}")
//    String serviceKey;

    private final FoodRepository foodRepository;

    @Override
    public List<FoodRes> getAllFood() {
        return foodRepository.findAll().stream().map(FoodRes::toDto).collect(Collectors.toList());
    }

    @Override
    public List<FoodRes> getAllFoodByCategory(String foodCategoryName) {
        FoodCategory foodCategory = FoodCategory.getFoodCategoryByName(foodCategoryName);
        return foodRepository.findAllByFoodCategory(foodCategory)
                .stream().map(FoodRes::toDto).collect(Collectors.toList());
    }

    @Override
    public BarcodeFoodRes searchByBarcode(String barcodeNum) throws IOException, org.json.simple.parser.ParseException {
        JSONObject data = callBarcodeApi(barcodeNum);
        if (data == null) throw new BarcodeFoodNotFoundException();
        String foodDetailName = (String) data.get("PRDT_NM");
        String apiCategory = (String) data.get("PRDLST_NM");

        // TODO: GPT도입 후 수정예정
        return BarcodeFoodRes.toDto(null, foodDetailName, null);
    }

    @Override
    public List<FoodRes> getAllFoodByCategoryAndWord(String categoryName, String word) {
        FoodCategory foodCategory = FoodCategory.getFoodCategoryByName(categoryName);
        return foodRepository.findByFoodNameContainsAndFoodCategory(word, foodCategory)
                .stream().map(FoodRes::toDto).collect(Collectors.toList());
    }

    @Override
    public List<FoodRes> getAllFoodByWord(String word) {
        return foodRepository.findByFoodNameContains(word)
                .stream().map(FoodRes::toDto).collect(Collectors.toList());
    }

    private JSONObject callBarcodeApi(String barcodeNum) throws IOException, ParseException {
        String serviceKey = "5b44035a29544b54aa72";
        URL url = new URL("https://openapi.foodsafetykorea.go.kr/api/" + serviceKey +
                "/I2570/json/1/5/BRCD_NO=" + barcodeNum);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader rd;
        // 서비스코드가 정상이면 200~300
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject)parser.parse(sb.toString());
        JSONObject result = (JSONObject) obj.get("I2570");
        JSONArray row = (JSONArray) result.get("row");
        if (row == null) return null;
        return (JSONObject) row.get(0);
    }
}
