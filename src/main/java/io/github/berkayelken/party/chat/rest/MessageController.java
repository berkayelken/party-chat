package io.github.berkayelken.party.chat.rest;

import io.github.berkayelken.party.chat.domain.Message;
import io.github.berkayelken.party.chat.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@AllArgsConstructor
@RestController
@RequestMapping("/api/message")
public class MessageController {
	private final MessageService service;

	@PostMapping("/send")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void addMessage(@RequestHeader("id") String userId, @RequestBody Message message) {
		service.addMessage(userId, message);
	}

	@GetMapping (value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Message> streamWithHistory() {
		return Flux.fromIterable(service.getAllMessages())
				.concatWith(service.streamMessages());
	}

}
