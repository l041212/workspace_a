package com.example.demo.configurations.RabbitMQConfigurers;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectConfigurer {

	public static final String DIRECT_QUEUE1 = "direct.queue1";
	public static final String DIRECT_QUEUE2 = "direct.queue2";
	public static final String DIRECT_BINDINGKEY1 = "direct.bindingkey1";
	public static final String DIRECT_BINDINGKEY2 = "direct.bindingkey2";
	public static final String DIRECT_EXCHANGE1 = "direct.exchange1";

	@Bean
	public Queue directQueue1() {
		return new Queue(DIRECT_QUEUE1);
	}

	@Bean
	public Queue directQueue2() {
		return new Queue(DIRECT_QUEUE2);
	}

	@Bean
	public DirectExchange directExchange1() {
		return new DirectExchange(DIRECT_EXCHANGE1);
	}

	@Bean
	public Binding directBinding1() {
		return BindingBuilder.bind(directQueue1()).to(directExchange1()).with(DIRECT_BINDINGKEY1);
	}

	@Bean
	public Binding directBinding2() {
		return BindingBuilder.bind(directQueue2()).to(directExchange1()).with(DIRECT_BINDINGKEY2);
	}

}
