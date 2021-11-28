package com.example.demo.controller;

import org.springframework.http.HttpStatus;

public class KnownException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String messageId;
	private HttpStatus status;
	private String reason;
	public KnownException(HttpStatus status,String msgId,String reason) {
		super();
		this.status = status;
		this.messageId = msgId;
		this.reason = reason;
	}

	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
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
}
