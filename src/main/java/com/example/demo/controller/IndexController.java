package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerMapping;

import com.example.demo.config.ContentImage;

@Controller
@ConditionalOnWebApplication
public class IndexController {

	public static Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	ContentImage images;

	@RequestMapping(
			value = { "/", "/pages/*", "/message/*", "/error/*" }, 
			method = RequestMethod.GET)
	String index() {
		logger.info("index()");
		return "/index.html";
	}

	@RequestMapping(
			value = { "/images/**" }, 
			method = RequestMethod.GET)
	HttpEntity<byte[]> images(HttpServletRequest req) {
		
		String id = findId(req);
		
		images.set(id);
		//ディレクト内から画像を検索
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(images.type());	
		headers.setContentLength(images.length());

		return new HttpEntity<byte[]>(images.bytes(),headers);
	}
	
    private static String findId(final HttpServletRequest request){
        String path = (String)request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String)request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        return new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);
    }
}
