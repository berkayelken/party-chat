package io.github.berkayelken.party.chat.service;

import io.github.berkayelken.party.chat.domain.FixedCapacityList;
import io.github.berkayelken.party.chat.domain.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.UUID;

@Service
public class MessageService {
	private static final int MESSAGE_QUEUE_LIMIT = 2500;
	private final FixedCapacityList<Message> messages = new FixedCapacityList<>(MESSAGE_QUEUE_LIMIT);
	private final Sinks.Many<Message> sink = Sinks.many().multicast().onBackpressureBuffer();

	public void addMessage(String userId, Message message) {
		message.setId(UUID.randomUUID().toString());
		message.setSenderId(userId);
		message.setDate(ZonedDateTime.now().toInstant().toEpochMilli());
		messages.add(message);
		sink.tryEmitNext(message);
	}

	public Collection<Message> getAllMessages() {
		return messages;
	}

	public Flux<Message> streamMessages() {
		return sink.asFlux();
	}
}
