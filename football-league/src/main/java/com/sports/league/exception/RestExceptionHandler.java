package com.sports.league.exception;


import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	private Logger log = LoggerFactory.getLogger(getClass());

	private static final String EXCEPTION_TYPE = "Exception-Type";


	private ResponseEntity<Object> toResponseEntity(Exception exception, WebRequest request, String bodyOfResponse,HttpStatus status) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Exception-Type", exception.getClass().getName());
		return handleExceptionInternal(exception, bodyOfResponse, headers, status, request);
	}


//	@ExceptionHandler({ApplicationException.class})
//	public ResponseEntity<Object> toResponse(ApplicationException exception, WebRequest request) {
//		this.log.error("error: {}", request.getContextPath(), exception.getMessage());
//		String bodyOfResponse = exception.getMessage();
//		return toResponseEntity(exception, request, bodyOfResponse, HttpStatus.BAD_REQUEST);
//	}

	
	@ExceptionHandler({NoSuchElementException.class})
	public ResponseEntity<Object> toResponse(NoSuchElementException exception, WebRequest request) throws JsonProcessingException {
		ExceptionMessage exceptionMessage = new ExceptionMessage("Data not found!!");
		String bodyOfResponse = (new ObjectMapper()).writeValueAsString(exceptionMessage);
		return toResponseEntity(exception, request, bodyOfResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({Exception.class})
	public ResponseEntity<Object> toResponse(Exception exception, WebRequest request) throws JsonProcessingException {
		ExceptionMessage exceptionMessage = new ExceptionMessage(exception.getMessage());
		String bodyOfResponse = (new ObjectMapper()).writeValueAsString(exceptionMessage);
		return toResponseEntity(exception, request, bodyOfResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		this.log.error("Validation exception:{}", ex.getMessage());
		FieldError fieldError = ex.getBindingResult().getFieldError();
		String bodyOfResponse = "{\"status\":\"" + status.toString() + "\",\"message\":\""+ fieldError.getDefaultMessage() + "\"}";
		return ResponseEntity.badRequest().body(bodyOfResponse);
	}

	@ExceptionHandler(ApplicationException.class)
	public ResponseEntity<Object> handleBusinessException(ApplicationException exception, WebRequest request) throws JsonProcessingException {
		log.error("Business exception: {}", exception.getMessage());
		
		if(exception.getMessage().contains("error")) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
		}
		ExceptionMessage exceptionMessage = new ExceptionMessage(exception.getMessage());
		String bodyOfResponse = (new ObjectMapper()).writeValueAsString(exceptionMessage);

		return new ResponseEntity<>(bodyOfResponse, HttpStatus.BAD_REQUEST);
	}
}