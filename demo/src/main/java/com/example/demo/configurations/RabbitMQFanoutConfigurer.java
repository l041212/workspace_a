package com.example.demo.configurations;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQFanoutConfigurer {

	public static final String FANOUT_QUEUE1 = "fanout.queue1";
	public static final String FANOUT_QUEUE2 = "fanout.queue2";
	public static final String FANOUT_EXCHANGE1 = "fanout.exchange1";

	@Bean
	public Queue fanoutQuery1() {
		return new Queue(FANOUT_QUEUE1);
	}

	@Bean
	public Queue fanoutQuery2() {
		return new Queue(FANOUT_QUEUE2);
	}

	@Bean
	public FanoutExchange fanoutExchange1() {
		return new FanoutExchange(FANOUT_EXCHANGE1);
	}

	@Bean
	public Binding fanoutBinding1() {
		return BindingBuilder.bind(fanoutQuery1()).to(fanoutExchange1());
	}

	@Bean
	public Binding fanoutBinding2() {
		return BindingBuilder.bind(fanoutQuery2()).to(fanoutExchange1());
	}

}
