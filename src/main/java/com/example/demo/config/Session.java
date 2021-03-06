package com.example.demo.config;

import java.io.Serializable;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Component;

import com.example.demo.transfer.LoginUser;
import com.example.demo.util.Util;

@Component
@ConditionalOnWebApplication
public class Session implements Serializable {
	
	private final static Logger logger = LoggerFactory.getLogger(Session.class);

	private static final long serialVersionUID = 1L;

	public static final String sessionUserKey = "user";
	public static final String sessionLanguageKey = "language";

	@Autowired
    @Value("${application.client.default.language}")
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

	public void setUser(LoginUser user) {
		logger.info("session user[{}]",user.getId());
		session.setAttribute(sessionUserKey, user);
	}

	public LoginUser getUser() {
		LoginUser user = (LoginUser)session.getAttribute(sessionUserKey);
		if ( user == null ) {
			logger.warn("セッションユーザを取得していますが、Nullです。");
		}
		return user;
	}

	public int getBelong() {
		LoginUser user = this.getUser();
		if ( user == null ) {
			return -1;
		}
		return user.getBelong();
	}
}
