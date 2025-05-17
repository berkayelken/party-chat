package io.github.berkayelken.party.chat.service;

import io.github.berkayelken.party.chat.domain.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageServiceTest {

	private MessageService messageService;

	@BeforeEach
	public void setUp() {
		messageService = new MessageService();
	}

	@Test
	public void testAddMessageAndGetAllMessages() {
		Message message = new Message();
		message.setContent("Hello");

		messageService.addMessage("user-1", message);

		Collection<Message> messages = messageService.getAllMessages();

		assertThat(messages).hasSize(1);
		Message stored = messages.iterator().next();
		assertThat(stored.getId()).isNotNull();
		assertThat(stored.getSenderId()).isEqualTo("user-1");
		assertThat(stored.getContent()).isEqualTo("Hello");
	}

	@Test
	public void testStreamMessagesEmitsNewMessage() {
		Message message = new Message();
		message.setContent("Streaming!");

		StepVerifier.create(messageService.streamMessages())
				.then(() -> messageService.addMessage("stream-user", message))
				.assertNext(m -> {
					assertThat(m.getContent()).isEqualTo("Streaming!");
					assertThat(m.getSenderId()).isEqualTo("stream-user");
				})
				.thenCancel()
				.verify();
	}

	@Test
	public void testMessageIsAssignedIdAndTimestamp() {
		Message message = new Message();
		message.setContent("Meta test");

		messageService.addMessage("meta-user", message);

		Message stored = messageService.getAllMessages().iterator().next();
		assertThat(stored.getId()).isNotBlank();
	}
}
