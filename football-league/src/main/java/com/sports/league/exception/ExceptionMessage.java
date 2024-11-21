package com.sports.league.exception;


public class ExceptionMessage {
	private String error;

	public ExceptionMessage(String error) {
		this.error = error;
	}

	public ExceptionMessage(Exception exception) {
		this(exception.getMessage());
	}

	public String getError() {
		return this.error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String toString() {
		return "Exception [" + ((this.error != null) ? ("error=" + this.error) : "") + "]";
	}
}