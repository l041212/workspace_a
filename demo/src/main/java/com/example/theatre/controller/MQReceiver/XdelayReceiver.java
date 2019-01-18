package com.example.theatre.controller.MQReceiver;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.demo.configurations.RabbitMQConfigurer.XdelayConfigurer;

@Component
public class XdelayReceiver {

	private final static Logger logger = LoggerFactory.getLogger(XdelayReceiver.class);

	@RabbitListener(queues = XdelayConfigurer.XDELAY_QUEUE1)
	public void receiverTest1(Map<String, Object> map) {
		logger.info("xdelay q1: " + map);
	}

	@RabbitListener(queues = XdelayConfigurer.XDELAY_QUEUE2)
	public void receiverTest2(Map<String, Object> map) {
		logger.info("xdelay q2: " + map);
	}
}
