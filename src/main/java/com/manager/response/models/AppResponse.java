package com.manager.response.models;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AppResponse {
	
	private boolean success;
	
	private String description;

	private Object result;

	private List<AppErrorResponse> errors;
	
}