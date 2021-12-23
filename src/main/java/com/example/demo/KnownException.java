package com.example.demo;

import org.springframework.http.HttpStatus;

public class KnownException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String messageID;
	private HttpStatus status;
	private String reason;
	public KnownException(HttpStatus status,String msg,String reason) {
		super();
		this.status = status;
		this.messageID = msg;
		this.reason = reason;
	}

	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getMessageID() {
		return messageID;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}
}
