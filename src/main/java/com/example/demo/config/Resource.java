package com.example.demo.config;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.example.demo.util.Util;

@Component
@ConditionalOnWebApplication
public class Resource {
	
	private final static Logger logger = LoggerFactory.getLogger(Resource.class);
	@Autowired
	private MessageSource messages;	
	@Autowired
	private Session session;	

	public String get(String id,String ...args) {

		logger.debug("Language {}" ,session.getLanguage());
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
	
	/**
	 * リソースマップを取得
	 * @param lang 
	 * @return 全リソースのマップ
	 */
	public Map<String,String> getMap(String lang) {
		Locale loc = new Locale(lang);
		ResourceBundle resources = ResourceBundle.getBundle(ResourceConfig.Name, loc);
		Map<String,String> map = new LinkedHashMap<>();
		Enumeration<String> keys = resources.getKeys();
		while ( keys.hasMoreElements() ) {
			String key = keys.nextElement();
			String value = resources.getString(key);
			map.put(key,value);
		}
		return map;
	}
}
