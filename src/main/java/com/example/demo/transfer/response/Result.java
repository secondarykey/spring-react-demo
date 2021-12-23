package com.example.demo.transfer.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.util.Util;

/**
 * リクエスト戻り値
 * @param <T> 戻り値の型
 */
public class Result<T> implements Serializable {

	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(Result.class);
	private static final long serialVersionUID = 1L;
	
	public Result() {
		this.setSuccess(true);
		this.setReason("");
	}
	
	private boolean success;
	private String messageID;
	private List<String> messages = new ArrayList<String>();
	private String reason;
	private T result;

	public boolean isSuccess() {
		return success;
	}
	
	public void setError() {
		success = false;
	}

	public void addMessage(String msg) {
		this.messages.add(msg);
	}

	public List<String> getMessages() {
		return this.messages;
	}

	public void setSuccess(boolean success) {
		this.success = success;
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

	public boolean existMessage() {
		if ( !Util.isEmpty(this.messages) ) {
			return true;
		}
		if ( !Util.isEmpty(this.messageID) ) {
			return true;
		}
		return false;
	}

	public void setMessageId(String msgId, String reson) {
		messageID = msgId;
		setReason(reson);
	}

	public String getMessageID() {
		return messageID;
	}
}
