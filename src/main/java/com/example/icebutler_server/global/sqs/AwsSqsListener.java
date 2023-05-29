package com.example.icebutler_server.global.sqs;

import com.example.icebutler_server.food.service.FoodServiceImpl;
import com.example.icebutler_server.global.util.redis.RedisUtils;
import com.example.icebutler_server.global.util.redis.SyncData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.messaging.listener.Acknowledgment;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AwsSqsListener {

	private final ObjectMapper objectMapper;
	private final FoodServiceImpl foodService;
	private final RedisUtils redisUtils;

	@Value("${aws.sqs.queue.url}")
	private String queueUrl;

	@SqsListener(value = "${aws.sqs.queue.name}", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
	public void listen(@Payload String info, @Headers Map<String, String> headers, Acknowledgment ack) throws JsonProcessingException {
		log.info("-------------------------------------start SqsListener");
		log.info("-------------------------------------info {}", info);

		// 음식 데이터를 큐로부터 받는다.
		FoodData foodData = null;
		try{
			// 음식 데이터 json으로 변환
			foodData = objectMapper.readValue(info, FoodData.class);
		}catch (JsonProcessingException e){
			throw new RuntimeException(e);
		}
		// 레디스에 음식 데이터의 uuid에 해당하는 key 값을 가진 데이터가 있는지 확인한다.
		Optional<SyncData> optionalData = redisUtils.get(foodData.getUuid(), SyncData.class);
		// 만약 레디스에 데이터가 존재하고 && 해당 서버는 요 음식 데이터를 처음 받은 것이라면(== !syncData.isMainService())
		if(optionalData.isPresent()){
			SyncData syncData = optionalData.get();
			if(!syncData.isMainService()){
				//1. 음식 데이터 저장
				foodService.addFood(foodData.toFoodReq());
				System.out.println(" 음식 데이터 저장! ");
				//2. 레디스의 동기화용 데이터 삭제
				redisUtils.delete(foodData.getUuid());
				//3. 큐에 있는 음식 데이터 삭제
				ack.acknowledge();
			}
		}
		// 이미 내가 받은 음식 데이터라면 큐에 있는 음식 데이터와 레디스에 있는 데이터를 삭제하지 않고 넘어간다.
		log.info("음식 데이터 receive : {}", foodData);
	}
}