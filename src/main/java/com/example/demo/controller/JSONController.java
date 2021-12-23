package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo.KnownException;

public class JSONController {

	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(JSONController.class);

	protected void Unauthorized(String msg,String reason) {
		throw new UnauthorizedException(msg,reason);
	}

	protected void Forbidden(String msgId,String reason) {
		throw new ForbiddenException(msgId,reason);
	}

	protected void FatalError(String msgId,String reason) {
		throw new InternalServerErrorException(msgId,reason);
	}

	//401
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public class UnauthorizedException extends KnownException {
		private static final long serialVersionUID = 1L;
		public UnauthorizedException(String message, String reason) {
			super(HttpStatus.UNAUTHORIZED, message, reason);
		}
	}

	//403
	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	public class ForbiddenException extends KnownException {
		private static final long serialVersionUID = 1L;
		public ForbiddenException(String msgId, String reason) {
			super(HttpStatus.FORBIDDEN,msgId,reason);
		}
	}

	//500
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public class InternalServerErrorException extends KnownException {
		private static final long serialVersionUID = 1L;
		public InternalServerErrorException(String msgId, String reason) {
			super(HttpStatus.INTERNAL_SERVER_ERROR,msgId,reason);
		}
	}
}
