package com.example.icebutler_server.global.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.Acknowledgment;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class AwsSqsListener {
	@Value("${aws.sqs.queue.url}")
	private String queueUrl;

	@SqsListener(value = "${aws.sqs.queue.name}", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
	public void listen(@Payload String info, @Headers Map<String, String> headers, Acknowledgment ack) {
		log.info("-------------------------------------start SqsListener");
		log.info("-------------------------------------info {}", info);
		log.info("-------------------------------------headers {}", headers);
		long approximateReceiveCount = Long.parseLong(headers.get("ApproximateReceiveCount"));
		System.out.println("approximateReceiveCount = " + approximateReceiveCount);
		AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

		List<Message> messages = sqs.receiveMessage(queueUrl)
				.getMessages();

		for (Message message : messages) {
			System.out.println(message.getBody());
		}
		ack.acknowledge();
	}
}