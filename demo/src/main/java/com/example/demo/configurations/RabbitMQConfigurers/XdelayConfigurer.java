package com.example.demo.configurations.RabbitMQConfigurers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XdelayConfigurer {

	public static final String X_DELAYED_TYPE = "x-delayed-type";
	public static final String X_DELAYED_MESSAGE = "x-delayed-message";
	public static final String XDELAY_QUEUE1 = "xdelay.queue1";
	public static final String XDELAY_QUEUE2 = "xdelay.queue2";
	public static final String XDELAY_BINDINGKEY1 = "xdelay.bindingkey.1";
	public static final String XDELAY_BINDINGKEY2 = "xdelay.bindingkey.#";
	public static final String XDELAY_EXCHANGE1 = "xdelay.exchange1";

	@Bean
	public Queue xdelayQueue1() {
		return new Queue(XDELAY_QUEUE1);
	}

	@Bean
	public Queue xdelayQueue2() {
		return new Queue(XDELAY_QUEUE2);
	}

	@Bean
	public CustomExchange xdelayExchange1() {
		Map<String, Object> arguments = new HashMap<String, Object>();
		arguments.put(X_DELAYED_TYPE, "topic");
		return new CustomExchange(XDELAY_EXCHANGE1, X_DELAYED_MESSAGE, true, false, arguments);
	}

	@Bean
	public Binding xdelayBinding1() {
		return BindingBuilder.bind(xdelayQueue1()).to(xdelayExchange1()).with(XDELAY_BINDINGKEY1).noargs();
	}

	@Bean
	public Binding xdelayBinding2() {
		return BindingBuilder.bind(xdelayQueue2()).to(xdelayExchange1()).with(XDELAY_BINDINGKEY2).noargs();
	}

}
