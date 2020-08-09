package com.manager.utils;

import com.manager.exception.BadRequestException;
import com.manager.exception.UnauthorizedException;

public class AppUtils {
	
	public static void preCondition(final boolean status, final String message) {
		if(status) {
			throw new BadRequestException(message);
		}
	}
	
	public static void preCondition(final boolean status, final String message, Exception ex) {
		if(status) {
			throw new BadRequestException(message);
		}
	}
	
	public static void checkAuthentication(final boolean status, final String message) {
		if(status) {
			throw new UnauthorizedException(message);
		}
	}
}
