package com.example.icebutler_server.global.feign.feignClient;

import com.example.icebutler_server.global.feign.dto.AddUserReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name="recipe-server", url = "${server.recipe.url}")
public interface RecipeServerClient {
    @PostMapping("/users")
    void addUser(@RequestBody AddUserReq addUserReq);
}
