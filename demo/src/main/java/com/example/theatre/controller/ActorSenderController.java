package com.example.theatre.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.configurations.rabbitMQ.DelayConfigurer;
import com.example.demo.configurations.rabbitMQ.DirectConfigurer;
import com.example.demo.configurations.rabbitMQ.FanoutConfigurer;
import com.example.demo.configurations.rabbitMQ.TopicConfigurer;
import com.example.demo.configurations.rabbitMQ.XdelayConfigurer;

@RestController
@RequestMapping("/theatre/actorSender")
public class ActorSenderController {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@RequestMapping(path = "/testSend", method = RequestMethod.GET)
	public void senderTest(HttpServletRequest request, HttpServletResponse response) {
		for (Integer i = 0; i < 10; i++) {
			new Thread(() -> {
				Map<String, Object> object = new HashMap<String, Object>();
				object.put("name", "human");
				object.put("message", "yahoo~~");
				object.put("id", Thread.currentThread().getId());
				rabbitTemplate.convertAndSend(DirectConfigurer.DIRECT_EXCHANGE1, "direct.bindingkey1", object);
				rabbitTemplate.convertAndSend(DirectConfigurer.DIRECT_EXCHANGE1, "direct.bindingkey2", object);
				rabbitTemplate.convertAndSend(FanoutConfigurer.FANOUT_EXCHANGE1, "", object);
				rabbitTemplate.convertAndSend(TopicConfigurer.TOPIC_EXCHANGE1, "topic.bindingkey1.aa", object);
				rabbitTemplate.convertAndSend(TopicConfigurer.TOPIC_EXCHANGE1, "topic.bindingkey2.bb", object);
				rabbitTemplate.convertAndSend(DelayConfigurer.DELAY_EXCHANGE1, "delay.bindingkey1", object, message -> {
					message.getMessageProperties().setExpiration(String.valueOf(6*1000));
					return message;
				});
				rabbitTemplate.convertAndSend(DelayConfigurer.DELAY_EXCHANGE1, "delay.bindingkey2", object, message -> {
					message.getMessageProperties().setExpiration(String.valueOf(12*1000));
					return message;
				});
				rabbitTemplate.convertAndSend(XdelayConfigurer.XDELAY_EXCHANGE1, "xdelay.bindingkey.1", object, message -> {
					message.getMessageProperties().setDelay(6*1000);
					return message;
				});
				rabbitTemplate.convertAndSend(XdelayConfigurer.XDELAY_EXCHANGE1, "xdelay.bindingkey.2", object, message -> {
					message.getMessageProperties().setDelay(12*1000);
					return message;
				});
			}).start();
		}
	}

}
