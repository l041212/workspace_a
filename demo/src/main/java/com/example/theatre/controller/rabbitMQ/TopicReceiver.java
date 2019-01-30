package com.example.theatre.controller.rabbitMQ;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.demo.configurations.rabbitMQ.TopicConfigurer;

@Component
public class TopicReceiver {

	private final static Logger logger = LoggerFactory.getLogger(TopicReceiver.class);

	@RabbitListener(queues = TopicConfigurer.TOPIC_QUEUE1)
	public void receiverTest1(Map<String, Object> map) {
		logger.info("topic q1: " + map);
	}

	@RabbitListener(queues = TopicConfigurer.TOPIC_QUEUE2)
	public void receiverTest2(Map<String, Object> map) {
		logger.info("topic q2: " + map);
	}

}
