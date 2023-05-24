package com.example.icebutler_server.global.sqs;

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

@Slf4j
@Component
@RequiredArgsConstructor
public class AwsSqsListener {
	@Value("${aws.sqs.queue.url}")
	private String queueUrl;

//	@Value("${aws.sqs.queue.name}")
//	private String queueName;

	@SqsListener(value = "${aws.sqs.queue.name}", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
	public void listen(@Payload String info, @Headers Map<String, String> headers, Acknowledgment ack) {
		log.info("-------------------------------------start SqsListener");
		log.info("-------------------------------------info {}", info);
//		log.info("-------------------------------------headers {}", headers);
		String id = headers.get("id");
		System.out.println("id = " + id);
		long approximateReceiveCount = Long.parseLong(headers.get("ApproximateReceiveCount"));
		System.out.println("approximateReceiveCount = " + approximateReceiveCount);

		ack.acknowledge();
	}
}