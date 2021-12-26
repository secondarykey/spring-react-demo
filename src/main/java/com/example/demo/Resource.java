package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.example.demo.config.Session;
import com.example.demo.util.Util;

@Component
@ConditionalOnWebApplication
public class Resource {
	
	private final static Logger logger = LoggerFactory.getLogger(Resource.class);
	@Autowired
	MessageSource messages;	
	@Autowired
	Session session;	

	public String get(String id,String ...args) {
	
		logger.info("Language {}" ,session.getLanguage());
		Locale loc = new Locale(session.getLanguage());
		List<Object> list = new ArrayList<Object>();
		if ( !Util.isEmpty(args) ) {
			for ( String argId : args ) {
				list.add(messages.getMessage(argId, null,loc));
			}
		}

		Object[] objArgs = null;
		if ( list.size() > 0 ) {
			objArgs = new Object[list.size()];
			list.toArray(objArgs);
		}
		return messages.getMessage(id, objArgs,loc);
	}
}
