package com.davromalc.testing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.davromalc.testing.publisher.MessagePublisher;

@SpringBootApplication
public class TestingApplication {

	public static void main(String[] args) {
		final ConfigurableApplicationContext appContext = SpringApplication.run(TestingApplication.class, args);
		final MessagePublisher publisher = appContext.getBean(MessagePublisher.class);
		publisher.publish("Hi!");
	}

}
