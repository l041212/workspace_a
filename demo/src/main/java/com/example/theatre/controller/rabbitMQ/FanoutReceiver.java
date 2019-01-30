package com.example.theatre.controller.rabbitMQ;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.demo.configurations.rabbitMQ.FanoutConfigurer;

@Component
public class FanoutReceiver {

	private final static Logger logger = LoggerFactory.getLogger(FanoutReceiver.class);

	@RabbitListener(queues = FanoutConfigurer.FANOUT_QUEUE1)
	public void receiverTest1(Map<String, Object> map) {
		logger.info("fanout q1: " + map);
	}

	@RabbitListener(queues = FanoutConfigurer.FANOUT_QUEUE2)
	public void receiverTest2(Map<String, Object> map) {
		logger.info("fanout q2: " + map);
	}

}
