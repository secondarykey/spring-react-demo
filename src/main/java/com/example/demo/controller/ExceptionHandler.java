package com.example.demo.controller;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.transfer.response.Result;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({ KnownException.class })
    @ResponseBody
    public ResponseEntity<?> handleKnownError(KnownException ex) {
    	Result<String> result = new Result<>();
    	result.setResult(getTrace(ex));
    	result.setError(ex.getMessageId(), ex.getReason());

        return new ResponseEntity<>(result,ex.getStatus());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ Exception.class })
    @ResponseBody
    public ResponseEntity<?> handleUnknownError(Exception ex) {
    	Result<String> result = new Result<>();
    	result.setResult(getTrace(ex));
    	result.setError("PRFN00M000", ex.getMessage());
    	
    	ex.printStackTrace();
        return new ResponseEntity<>(result,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getTrace(Exception ex) {
        var sw = new StringWriter();
        var pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }
}
