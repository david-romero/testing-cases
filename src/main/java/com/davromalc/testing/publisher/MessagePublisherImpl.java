package com.davromalc.testing.publisher;

import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessagePublisherImpl implements MessagePublisher {

    private final StringRedisTemplate redisTemplate;


    public MessagePublisherImpl(final StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void publish(final String message) {
    	try {
    		redisTemplate.convertAndSend("myTopic", message);
    	} catch (RedisConnectionFailureException e) {
    		log.error("Unable to connect to redis. Bringing down the instance");
    		System.exit(1);
    	}
    }

}
