package com.example.theatre.controller.MQReceiver;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.demo.configurations.RabbitMQFanoutConfigurer;

@Component
public class TestFanoutReceiver {

	private final static Logger logger = LoggerFactory.getLogger(TestFanoutReceiver.class);

	@RabbitListener(queues = RabbitMQFanoutConfigurer.FANOUT_QUEUE1)
	public void receiverTest1(Map<String, Object> map) {
		logger.info("fanout q1: " + map);
	}

	@RabbitListener(queues = RabbitMQFanoutConfigurer.FANOUT_QUEUE2)
	public void receiverTest2(Map<String, Object> map) {
		logger.info("fanout q2: " + map);
	}

}
