package com.example.demo.configurations.RabbitMQConfigurers;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfigurer {

	public static final String TOPIC_QUEUE1 = "topic.queue1";
	public static final String TOPIC_QUEUE2 = "topic.queue2";
	public static final String TOPIC_BINDINGKEY1 = "topic.#.#";
	public static final String TOPIC_BINDINGKEY2 = "topic.bindingkey2.#";
	public static final String TOPIC_EXCHANGE1 = "topic.exchange1";

	@Bean
	public Queue topicQueue1() {
		return new Queue(TOPIC_QUEUE1);
	}

	@Bean
	public Queue topicQueue2() {
		return new Queue(TOPIC_QUEUE2);
	}

	@Bean
	public TopicExchange topicExchange1() {
		return new TopicExchange(TOPIC_EXCHANGE1);
	}

	@Bean
	public Binding topicBinding1() {
		return BindingBuilder.bind(topicQueue1()).to(topicExchange1()).with(TOPIC_BINDINGKEY1);
	}

	@Bean
	public Binding topicBinding2() {
		return BindingBuilder.bind(topicQueue2()).to(topicExchange1()).with(TOPIC_BINDINGKEY2);
	}

}
