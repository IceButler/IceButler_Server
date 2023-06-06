package com.example.icebutler_server.global.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class AppleUtils {

  public static String appleProductionUrl;
  public static String appleSandboxUrl;

  @Value("${apple.production.url}")
  public static void setAppleProductionUrl(String value) {
    appleProductionUrl = value;
  }
  @Value("${apple.sandbox.url}")
  public static void setAppleSandboxUrl(String value) {
    appleSandboxUrl = value;
  }


}
