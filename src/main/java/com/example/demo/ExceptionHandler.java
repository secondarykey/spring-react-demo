package com.example.demo;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.transfer.response.Result;
import com.example.demo.util.Util;

@ControllerAdvice
@Scope(value = "session")
@ConditionalOnWebApplication
public class ExceptionHandler {

	@Autowired
	Resource resource;

	private static Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    @org.springframework.web.bind.annotation.ExceptionHandler({ KnownException.class })
    @ResponseBody
    public ResponseEntity<?> handleKnownError(KnownException ex) {

    	logger.info("handleKnownError()",ex);

    	Result<String> result = new Result<>();

    	result.setResult(getTrace(ex));
    	result.setError();
    	result.setMessageId(ex.getMessageID(),ex.getReason());

        return new ResponseEntity<>(result,ex.getStatus());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ Exception.class })
    @ResponseBody
    public ResponseEntity<?> handleUnknownError(Exception ex) {

    	logger.error("handleUnknownError()", ex);
    	Result<String> result = new Result<>();
    	result.setResult(getTrace(ex));
    	result.setError();
    	result.setMessageId("PRFN00M000",ex.getMessage());
    	//result.addMessage(resource.get("PRFN00M000"));
    	result.setReason(ex.getMessage());
    	
        return new ResponseEntity<>(result,HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * 入力チェック時の例外
     * @param ex 例外
     * @return 
     */
    @org.springframework.web.bind.annotation.ExceptionHandler({ MethodArgumentNotValidException.class })
    @ResponseBody
    public ResponseEntity<?> handleValidateError(Exception ex) {

    	logger.debug("handleValidateError()", ex);
    	Result<String> result = new Result<>();
    	
    	Map<String,String> resourceMap = new HashMap<String,String>();
    	resourceMap.put("NotEmpty", "PRFN00M001");
    	
    	result.setError();
  
    	MethodArgumentNotValidException argEx = (MethodArgumentNotValidException)ex;
    	List<ObjectError> list = argEx.getAllErrors();
    	for ( ObjectError err : list ) {

    		String anotation = err.getCode();
    		String anotationId = resourceMap.get(anotation);

    		String columnId = err.getDefaultMessage();
    		//メッセージが本体だった場合
    		if ( Util.isEmpty(anotationId) ) {
    			result.addMessage(resource.get(columnId));
    		} else {
    			result.addMessage(resource.get(anotationId, columnId));
    		}
    	
    		/*
    		logger.info("code={},def={},name={} error",err.getCode(),err.getDefaultMessage(),err.getObjectName());
    		for ( Object obj : err.getArguments() ) {
    			logger.info("{} = {} arguments error",obj.getClass().getName(),obj.toString());
    		}
    		*/
    	}

    	result.setMessageId("PRFN00M000","入力チェックエラー");
    	result.setReason(getTrace(ex));
        return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
    }

    private String getTrace(Exception ex) {
        var sw = new StringWriter();
        var pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }
}
