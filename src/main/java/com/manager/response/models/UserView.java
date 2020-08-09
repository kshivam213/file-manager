package com.manager.response.models;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserView {

	private Long userId;
	private String profileId;
	private String email;
	private String contact;
	private String dob;
	private String gender;
	private String timezone;
	
	List<ConnectionView> connections;
}
