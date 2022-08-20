package com.example.demo.controller.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.controller.JSONController;
import com.example.demo.transfer.response.Result;

/**
 * その日の休日と組織を取得
 * 
 * 
 * 
 */
@RestController
@RequestMapping("/api/demo/file")
@ConditionalOnWebApplication
public class FileController extends JSONController {

	private final static Logger logger = LoggerFactory.getLogger(FileController.class);

	@RequestMapping(value = "/upload",method = RequestMethod.PUT)
	public Result<String> upload(@RequestParam("file") MultipartFile file) {
		Result<String> result = new Result<>();
	
		logger.info("{}",file.getName());
		logger.info("{}",file.getOriginalFilename());
		
		
		return result;
	}
}
