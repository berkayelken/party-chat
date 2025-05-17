package io.github.berkayelken.party.chat.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Message {
	private String id;
	private String senderId;
	private String content;
	private long date;
}
