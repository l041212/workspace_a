package com.example.theatre.controller.MQReceiver;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.demo.configurations.RabbitMQTopicConfigurer;

@Component
public class TestTopicReceiver {

	private final static Logger logger = LoggerFactory.getLogger(TestTopicReceiver.class);

	@RabbitListener(queues = RabbitMQTopicConfigurer.TOPIC_QUEUE1)
	public void receiverTest1(Map<String, Object> map) {
		logger.info("topic q1: " + map);
	}

	@RabbitListener(queues = RabbitMQTopicConfigurer.TOPIC_QUEUE2)
	public void receiverTest2(Map<String, Object> map) {
		logger.info("topic q2: " + map);
	}

}
