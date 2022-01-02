package com.example.demo.config;

import java.io.Serializable;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Component;

import com.example.demo.util.Util;

@Component
@ConditionalOnWebApplication
public class Session implements Serializable {
	
	private final static Logger logger = LoggerFactory.getLogger(Session.class);

	private static final long serialVersionUID = 1L;

	public static final String sessionUserKey = "session";
	public static final String sessionLanguageKey = "language";

	@Autowired
    @Value("${application.default.language}")
    private String defaultLanguage;
    @Autowired
    private HttpSession session;
    /**
     * 言語の取得
     * @return 言語コード
     */
	public String getLanguage() {
		Object sessObj = session.getAttribute(sessionLanguageKey);
		if ( sessObj == null ) {
			return defaultLanguage;
		}
		return (String)sessObj;
	}

	/**
	 * 言語の設定
	 * @param language
	 */
	public void setLanguage(String lang) {
		if ( Util.isDefault(lang) ) {
			lang = defaultLanguage;
		}
		logger.info("session language {},default {}",lang,defaultLanguage);
		session.setAttribute(sessionLanguageKey, lang);
	}
}
