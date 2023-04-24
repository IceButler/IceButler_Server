package com.example.icebutler_server.global.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AwsS3ImageUrlUtil {

    public static String bucket;
    public static String region;

    @Value("${aws.s3.region}")
    public void setRegion(String value) {
        region = value;
    }
    @Value("${aws.s3.bucket}")
    public void setBucket(String value) {
        bucket = value;
    }

    public static String toUrl(String imageKey) {
        return "https://"+bucket+".s3."+region+".amazonaws.com/"+imageKey;
    }
}
