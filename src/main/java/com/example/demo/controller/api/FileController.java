package com.example.demo.controller.api;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.controller.JSONController;
import com.example.demo.service.FileService;
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

	@Autowired
	FileService fileService;

	@RequestMapping(value = "/upload",method = RequestMethod.PUT)
	public Result<Integer> upload(@RequestParam("file") MultipartFile file) {

		Result<Integer> result = new Result<>();

		logger.info("{}",file.getName());
		logger.info("{}",file.getOriginalFilename());
		
		int id = 0;
		try {
			id = fileService.put(file.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		result.setResult(id);
		return result;
	}
	
	@RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
	public HttpEntity<byte[]> get(@PathVariable("id")Integer id) {

		Result<byte[]> result = new Result<>();
		byte[] bytes = fileService.get(id);
		result.setResult(bytes);

		HttpHeaders headers = new HttpHeaders();
		//headers.setContentType(images.type());	
		headers.setContentLength(bytes.length);
		return new HttpEntity<byte[]>(bytes,headers);
	}
}
