package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class JSONController {
	
	protected void Unauthorized(String msgId,String reason) {
		throw new UnauthorizedException(msgId,reason);
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
		public UnauthorizedException(String msgId, String reason) {
			super(HttpStatus.UNAUTHORIZED, msgId, reason);
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

	//403
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public class InternalServerErrorException extends KnownException {
		private static final long serialVersionUID = 1L;
		public InternalServerErrorException(String msgId, String reason) {
			super(HttpStatus.INTERNAL_SERVER_ERROR,msgId,reason);
		}
	}
}
