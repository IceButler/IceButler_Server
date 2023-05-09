package com.example.icebutler_server;

import com.example.icebutler_server.global.feign.feignClient.RecipeServerClient;
import io.awspring.cloud.autoconfigure.context.ContextRegionProviderAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableFeignClients(clients = {RecipeServerClient.class})
@SpringBootApplication(exclude = ContextRegionProviderAutoConfiguration.class)
public class IceButlerServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(IceButlerServerApplication.class, args);
	}
}
