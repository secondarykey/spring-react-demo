package com.example.demo.controller;

import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.transfer.response.LoginUser;

public class AuthenticationInterceptor implements HandlerInterceptor {
	
	public static Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.debug("preHandle()");
		String authHeader = request.getHeader("Authorization");
		logger.info(authHeader);

		//LoginUser user = decodeJWT(authHeader);
		return true;
	}

	private LoginUser decodeJWT(String value) {

        String encKey = "aes-256-cbc-text";

        try {
        byte[] keydata = encKey.getBytes();
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(encKey.toCharArray(), keydata, 100, 256);
        SecretKey secretKey = factory.generateSecret(spec);
        SecretKeySpec key = new SecretKeySpec(secretKey.getEncoded(), "AES");
        byte[] ivary = Arrays.copyOf(key.getEncoded(), 16);
        IvParameterSpec iv = new IvParameterSpec(ivary);	
 
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, iv);

            //byte[] byteResult = cipher.doFinal(Base64.getDecoder().decode(value));
            byte[] byteResult = cipher.doFinal(value.getBytes());

            String buf = new String(byteResult,"UTF-8");
          
            logger.info(buf);
            
        } catch (Exception e) {
        	throw new RuntimeException(e);
        }
		return null;
	}
}
