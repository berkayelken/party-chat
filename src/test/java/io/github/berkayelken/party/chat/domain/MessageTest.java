package io.github.berkayelken.party.chat.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MessageTest {
	private static final String TEST_STR = "TEST";

	@Test
	public void testNoArgsConstructor() {
		Assertions.assertDoesNotThrow(Message::new);
	}

	@Test
	public void testAccessors() {
		Message message = new Message();

		Assertions.assertNull(message.getId());
		Assertions.assertNull(message.getSenderId());
		Assertions.assertNull(message.getContent());
		Assertions.assertEquals(0L, message.getDate());
	}

	@Test
	public void testMutators() {
		Message message = new Message();

		Assertions.assertDoesNotThrow(() -> message.setId(TEST_STR));
		Assertions.assertDoesNotThrow(() -> message.setSenderId(TEST_STR));
		Assertions.assertDoesNotThrow(() -> message.setContent(TEST_STR));
		Assertions.assertDoesNotThrow(() -> message.setDate(Long.MAX_VALUE));

		Assertions.assertEquals(TEST_STR, message.getId());
		Assertions.assertEquals(TEST_STR, message.getSenderId());
		Assertions.assertEquals(TEST_STR, message.getContent());
		Assertions.assertEquals(Long.MAX_VALUE, message.getDate());
	}
}
