package com.manager.response.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatView {

	private Long userId;
	private Long connectionId;
	private Long chatId;
	private String profileId;
	private Long timestamp;
	private String message;
}