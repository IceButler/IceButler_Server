package com.example.icebutler_server.global.feign.feignClient;

import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.feign.dto.AdminReq;
import com.example.icebutler_server.global.feign.dto.UserReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(name="recipe-server", url = "${server.recipe.url}")
public interface RecipeServerClient {
    @PostMapping("/users")
    void addUser(@RequestBody UserReq userReq);

    @PatchMapping("/users")
    void changeUser(@RequestBody UserReq userReq);

    @DeleteMapping("/users")
    void deleteUser(@RequestBody UserReq userReq);

    @PostMapping("/admin")
    ResponseCustom<Void> addAdmin(@RequestBody AdminReq adminReq);
}
