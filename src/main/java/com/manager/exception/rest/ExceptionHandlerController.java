package com.manager.exception.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.manager.exception.BadRequestException;
import com.manager.exception.ResourceNotFoundException;
import com.manager.exception.ServerSideException;
import com.manager.exception.UnauthorizedException;
import com.manager.response.models.AppErrorResponse;
import com.manager.response.models.AppResponse;

/**
 * The Class ExceptionHandlerController.
 */
@ControllerAdvice
@RestController
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

	/**
	 * Resource not found.
	 * @param ex
	 * the ex
	 * @return the response entity
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<AppResponse> resourceNotFound(final ResourceNotFoundException ex) {
		AppErrorResponse errorResp = AppErrorResponse.builder().errorCode(404).message(ex.getMessage())
				.build();
		List<AppErrorResponse> errors = new ArrayList<>();
		errors.add(errorResp);
		AppResponse appResponse = AppResponse.builder().success(false).description("Resource Unavailable")
				.errors(errors).build();

		return new ResponseEntity<>(appResponse, HttpStatus.NOT_FOUND);
	}

	/**
	 * Bad request.
	 * @param ex
	 * the ex
	 * @return the response entity
	 */
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<AppResponse> badRequest(final BadRequestException ex) {

		AppErrorResponse errorResp = AppErrorResponse.builder().errorCode(400).message(ex.getMessage())
				.build();
		List<AppErrorResponse> errors = new ArrayList<>();
		errors.add(errorResp);
		AppResponse appResponse = AppResponse.builder().success(false).description("That was a bad request")
				.errors(errors).build();
		return new ResponseEntity<>(appResponse, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Unauthorized Exception.
	 * @param ex
	 * the ex
	 * @return the response entity
	 */
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<AppResponse> unauthorized(final UnauthorizedException ex) {

		AppErrorResponse errorResp = AppErrorResponse.builder().errorCode(401).message(ex.getMessage())
				.build();
		List<AppErrorResponse> errors = new ArrayList<>();
		errors.add(errorResp);
		AppResponse appResponse = AppResponse.builder().success(false).description("You are unauthorized")
				.errors(errors).build();

		return new ResponseEntity<>(appResponse, HttpStatus.UNAUTHORIZED);
	}
	
	/**
	 * Server Side Exception
	 * @param ex
	 * the ex
	 * @return the response entity
	 */
	@ExceptionHandler(ServerSideException.class)
	public ResponseEntity<AppResponse> serverSideError(final ServerSideException ex) {

		AppErrorResponse errorResp = AppErrorResponse.builder().errorCode(500).message(ex.getMessage())
				.build();
		List<AppErrorResponse> errors = new ArrayList<>();
		errors.add(errorResp);
		AppResponse appResponse = AppResponse.builder().success(false).description("Server Error")
				.errors(errors).build();

		return new ResponseEntity<>(appResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 *  If invalid request that time it will throw errors
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		AppErrorResponse errorResp = AppErrorResponse.builder().errorCode(400).message(ex.getMessage())
				.build();
		List<AppErrorResponse> errors = new ArrayList<>();
		errors.add(errorResp);
		AppResponse appResponse = AppResponse.builder().success(false).description("That was a bad request")
				.errors(errors).build();

		return new ResponseEntity<>(appResponse, HttpStatus.BAD_REQUEST);
	}
}