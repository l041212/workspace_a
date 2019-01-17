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

import com.example.demo.configurations.RabbitMQDirectConfigurer;
import com.example.demo.configurations.RabbitMQFanoutConfigurer;
import com.example.demo.configurations.RabbitMQTopicConfigurer;

@RestController
@RequestMapping("/theatre/actorSender")
public class ActorSenderController {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@RequestMapping(path = "/testSend", method = RequestMethod.GET)
	public void senderTest(HttpServletRequest request, HttpServletResponse response) {
		for (Integer i = 0; i < 10; i++) {
			new Thread(() -> {
				Map<String, Object> message = new HashMap<String, Object>();
				message.put("name", "human");
				message.put("message", "yahoo~~");
				message.put("id", Thread.currentThread().getId());
				rabbitTemplate.convertAndSend(RabbitMQDirectConfigurer.DIRECT_EXCHANGE1, "direct.bindingkey1", message);
				rabbitTemplate.convertAndSend(RabbitMQDirectConfigurer.DIRECT_EXCHANGE1, "direct.bindingkey2", message);
				rabbitTemplate.convertAndSend(RabbitMQFanoutConfigurer.FANOUT_EXCHANGE1, "", message);
				rabbitTemplate.convertAndSend(RabbitMQTopicConfigurer.TOPIC_EXCHANGE1, "topic.bindingkey1.aa", message);
				rabbitTemplate.convertAndSend(RabbitMQTopicConfigurer.TOPIC_EXCHANGE1, "topic.bindingkey2.bb", message);
			}).start();
		}
	}

}
