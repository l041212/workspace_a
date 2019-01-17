package com.example.theatre.controller.MQReceiver;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.demo.configurations.RabbitMQDirectConfigurer;

@Component
public class TestDirectReceiver {

	private final static Logger logger = LoggerFactory.getLogger(TestDirectReceiver.class);

	@RabbitListener(queues = RabbitMQDirectConfigurer.DIRECT_QUEUE1)
	public void receiverTest1(Map<String, Object> map) {
		logger.info("direct q1: " + map);
	}

	@RabbitListener(queues = RabbitMQDirectConfigurer.DIRECT_QUEUE2)
	public void receiverTest2(Map<String, Object> map) {
		logger.info("direct q2: " + map);
	}

}
