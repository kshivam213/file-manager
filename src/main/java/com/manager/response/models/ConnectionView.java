package com.manager.response.models;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ConnectionView {
	
	private Long userId;
	private Long connectionId;
	private String profileId;
	private String email;
	private String contact;
	
	List<ChatView> chats;
}
