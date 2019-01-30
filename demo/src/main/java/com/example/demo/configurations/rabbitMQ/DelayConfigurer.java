package com.example.demo.configurations.rabbitMQ;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DelayConfigurer {

	public static final String X_DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";
	public static final String X_DEAD_LETTER_ROUTTING_KEY = "x-dead-letter-routing-key";
	public static final String DELAY_QUEUE1 = "delay.queue1";
	public static final String DELAY_QUEUE2 = "delay.queue2";
	public static final String DELAY_BINDINGKEY1 = "delay.bindingkey1";
	public static final String DELAY_BINDINGKEY2 = "delay.#";
	public static final String DELAY_EXCHANGE1 = "delay.exchange1";

	@Bean
	public Queue delayQueue1() {
		Map<String, Object> arguments = new HashMap<String, Object>();
		arguments.put(X_DEAD_LETTER_EXCHANGE, TopicConfigurer.TOPIC_EXCHANGE1);
		arguments.put(X_DEAD_LETTER_ROUTTING_KEY, "topic.bindingkey2.cc");
		return new Queue(DELAY_QUEUE1, true, false, false, arguments);
	}

	@Bean
	public Queue delayQueue2() {
		Map<String, Object> arguments = new HashMap<String, Object>();
		arguments.put(X_DEAD_LETTER_EXCHANGE, DirectConfigurer.DIRECT_EXCHANGE1);
		arguments.put(X_DEAD_LETTER_ROUTTING_KEY, "direct.bindingkey1");
		return new Queue(DELAY_QUEUE2, true, false, false, arguments);
	}

	@Bean
	public TopicExchange delayExchange1() {
		return new TopicExchange(DELAY_EXCHANGE1);
	}

	@Bean
	public Binding delayBinding1() {
		return BindingBuilder.bind(delayQueue1()).to(delayExchange1()).with(DELAY_BINDINGKEY1);
	}

	@Bean
	public Binding delayBinding2() {
		return BindingBuilder.bind(delayQueue2()).to(delayExchange1()).with(DELAY_BINDINGKEY2);
	}

}
