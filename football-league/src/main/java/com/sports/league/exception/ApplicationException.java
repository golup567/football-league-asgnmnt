package com.sports.league.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.util.HtmlUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApplicationException extends RuntimeException {
	private static final long serialVersionUID = -1L;

	private String message;

	private String code;

	private Map<String, String> map = new HashMap<>();


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Map<String, String> getDynamicMap() {
		return this.map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public void setMessage(String message, Map<String, String> dynamic)  {
		Map<String, Object> object = new HashMap<>();
		object.put("error", HtmlUtils.htmlUnescape(message));
		try {
			this.message = toJson(object);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setGuiMessage(String message) {
		this.message = "{\"error\": \"" +HtmlUtils.htmlUnescape(message) + "\"}";
	}

	private <T> String toJson(T data) throws JsonProcessingException {
		return (new ObjectMapper()).writeValueAsString(data);
	}

	public ApplicationException() {
	}

	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApplicationException(Throwable cause) {
		super(cause);
	}

	public ApplicationException(String message) {
		super(message);
		setMessage(message, this.map);
	}

	public ApplicationException(String message, String guiMessage) {
		super(message);
		setMessage(guiMessage, this.map);
	}

	public ApplicationException(String message, Map<String, String> dynamic) {
		super(message);
		if (dynamic != null)
			this.map = dynamic;
		setMessage(message, this.map);
	}
}