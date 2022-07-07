package com.example.demo.controller;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.config.Resource;

/**
 * クライアント設定値のURL
 * <pre>
 * 
 * 注意：開発中は 静的ファイルにより実現している為、値等はそちらのファイルに依存します。
 * </pre>
 * @author p230985
 */
@Controller
@RequestMapping("/client")
@ConditionalOnWebApplication
public class ClientController {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
	
	@Autowired
	private Environment props;	
	@Autowired
	private Resource resource;

	/**
	 * JS書き込み用の文字列型
	 */
	private final static JSVariable.JSType Str = JSVariable.JSType.String;
	/**
	 * JS書き込み用の数値型
	 */
	private final static JSVariable.JSType Num = JSVariable.JSType.Number;

	/**
	 * クライアントの設定値
	 * <pre>
	 * 設定ファイルから、クライアントが扱える値に変更してJSとして返す
	 * </pre>
	 * @return 設定のJSイメージ
	 */
	@RequestMapping(
			value = { "/config.js" }, 
			method = RequestMethod.GET)
	public HttpEntity<byte[]> config() {

		JSVariable config = new JSVariable("clientConfig");
		//システムのデフォルト言語
		String lang = props.getProperty("application.client.default.language");
		//画面の言語一覧
		String langs = props.getProperty("application.client.languages");
		//画面の言語一覧
		String expiry = props.getProperty("application.client.expiry");
		//デフォルト言語
		config.add("defaultLanguage",Str,lang);
		//言語一覧
		config.add("languages",Str,langs.split(","));
		//デフォルト言語
		config.add("expiry",Num,expiry);

		HttpHeaders headers = new HttpHeaders();

		MediaType type = new MediaType("text","javascript");
		headers.setContentType(type);	

		StringBuffer buf = new StringBuffer();
		config.write(buf);
		
		try {
			String b = buf.toString();
			byte[] js = b.getBytes("utf-8");
			return new HttpEntity<byte[]>(js,headers);
		} catch (UnsupportedEncodingException e) {
			logger.error("{}",e);
			return null;
		}
	}

	/**
	 * 言語データJSON
	 * <pre>
	 * 指定された言語からリソースファイルを検索し、
	 * マップ情報からJSONファイルを返す
	 * </pre>
	 * @param lang 言語
	 * @return 言語JSON
	 */
	@RequestMapping(
			value = { "/{lang}.json" }, 
			method = RequestMethod.GET)
	public HttpEntity<byte[]> createLanguage(@PathVariable("lang") String lang) {
		
		logger.info("JSON:{}",lang);

		StringBuffer buf = new StringBuffer();

		HttpHeaders headers = new HttpHeaders();
		MediaType type = new MediaType("application","json");
		headers.setContentType(type);	

		Map<String,String> map = resource.getMap(lang);

		for ( String key : map.keySet() ) {
			String res = map.get(key);
			String sep = ",\n";
			if ( buf.length() <= 0 ) {
				sep = "{\n";
			}
			buf.append(String.format("%s  \"%s\":\"%s\"",sep,key,parseJSON(res)));
		}

		buf.append("}");

		byte[] b;
		try {
			String json = buf.toString();
			b = json.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("{}",e);
			b = "{}".getBytes();
		}
		return new HttpEntity<byte[]>(b,headers);
	}

	/**
	 * JSONデータ解析
	 * @param res 元文字列
	 * @return 解析した文字列
	 */
	private String parseJSON(String res) {
		return res.replaceAll("\n","\\n");
	}
}
