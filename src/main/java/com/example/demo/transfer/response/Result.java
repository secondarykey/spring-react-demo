package com.example.demo.transfer.response;

import java.io.Serializable;
import java.util.Date;

public class Result<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Result() {
		this.setSuccess(true);
		this.setMessageID("");
		this.setReason("");
	}
	
	private boolean success;
	private String messageID;
	private String reason;
	private T result;

	public void setMessage(String msgId,String reason) {
		this.setMessageID(msgId);
		this.setReason(reason);
	}

	public void setError(String msgId,String reason) {
		this.setSuccess(false);
		this.setMessage(msgId,reason);
	}


	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessageID() {
		return messageID;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public static String convertDate(Date expirs) {
		if (expirs == null ) {
			return "";
		}
		return expirs.toString();
	}
}
