package io.github.berkayelken.party.chat.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class FixedCapacityListTest {
	@Test
	public void testAllArgsConstructor() {
		Assertions.assertDoesNotThrow(() -> new FixedCapacityList<Message>(Integer.SIZE));
	}

	@Test
	public void testAdd() {
		FixedCapacityList<Message> list = new FixedCapacityList<>(Integer.SIZE);
		Stream.generate(Message::new).limit(Integer.SIZE * 2).forEachOrdered(list::add);
		Assertions.assertEquals(Integer.SIZE, list.size());
	}

	@Test
	public void testAddIndex() {
		FixedCapacityList<Message> list = new FixedCapacityList<>(Integer.SIZE);
		list.add(new Message());
		list.add(new Message());
		Stream.generate(Message::new).limit(Integer.SIZE * 2).forEachOrdered(message -> list.add(1, message));
		Assertions.assertEquals(Integer.SIZE, list.size());
	}

	@Test
	public void testAddAll() {
		FixedCapacityList<Message> list = new FixedCapacityList<>(Integer.SIZE);
		List<Message> list2 = Stream.generate(Message::new).limit(Integer.SIZE * 2).toList();
		list.addAll(list2);
		Assertions.assertEquals(Integer.SIZE, list.size());
	}

	@Test
	public void testAddAllIndex() {
		AtomicLong indexer1 = new AtomicLong(0L);
		FixedCapacityList<Message> list = new FixedCapacityList<>(Integer.SIZE);
		Stream.generate(Message::new).peek(message -> message.setDate(indexer1.getAndIncrement())).limit(7).forEach(list::add);
		AtomicLong indexer2 = new AtomicLong(900L);
		List<Message> list2 = Stream.generate(Message::new).peek(message -> message.setDate(indexer2.getAndIncrement())).limit(Integer.SIZE * 2).toList();
		list.addAll(6, list2);
		Assertions.assertEquals(Integer.SIZE, list.size());
	}

}
