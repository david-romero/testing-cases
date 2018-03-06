package com.davromalc.testing.publisher;

import java.lang.reflect.Field;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.davromalc.testing.mockito.MockitoExtension;

import static org.mockito.BDDMockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for MessagePublisherTest class")
public class MessagePublisherTest {

	@Mock
	StringRedisTemplate redisTemplate;

	@InjectMocks
	MessagePublisherImpl messagePublisher;
	
	@Test
	@DisplayName("Given a message When is published Then is published by redis")
	public void givenAMessageWhenIsPublishedThenIsPublishedByRedis() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// given
		final String message = "Hi!";

		// when
		messagePublisher.publish(message);

		// then
		verify(redisTemplate, times(1)).convertAndSend(anyString(), eq(message));
	}

	@Test
	@DisplayName("Given the Redis instance down When a message is published Then the container is bringing down")
	public void givenTheRedisInstanceDownWhenAMessageIsPublishedThenTheContainerIsBringingDown() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// given
		willThrow(new RedisConnectionFailureException("")).given(redisTemplate)
				.convertAndSend(anyString(), any());
		final String message = "Hi!";
		Runtime originalRuntime = Runtime.getRuntime();
		Runtime spyRuntime = spy(originalRuntime);
		doNothing().when(spyRuntime).exit(eq(1));
		setField(Runtime.class, "currentRuntime", spyRuntime);

		// when
		messagePublisher.publish(message);

		// then
		verify(spyRuntime, times(1)).exit(eq(1));
		setField(Runtime.class, "currentRuntime", originalRuntime);
	}

	private void setField(Class<?> clazz, String name, Object spy)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		final Field field = clazz.getDeclaredField(name);
		field.setAccessible(true);
		field.set(null, spy);
	}

}
