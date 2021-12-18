package com.example.demo.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.example.demo.transfer.response.LoginUser;

public class EncryptUtilTest {

	String encBuf = "U2FsdGVkX1+jnoJy0hRjEgE/Fu5jgPE73mLvAYY+6cPOcf7tc8EaFafsQT/L/oLrpEbQZ+RZLlpTwWs4Lz+SCHDWI5aSobt66zK4Mq58X6uL7Iq10bKKxQZi5dw9n65f5s2RjYwqCD8NOa0LjZ/avg==";
	String ansJson = "{\"id\":\"user\",\"name\":\"山田太郎\",\"role\":\"user\",\"expiry\":\"2021-12-13 06:05:38.1\"}";

	@Test
	void testDecode() {
		assertDoesNotThrow(() -> EncryptUtil.decode(encBuf));
		String rtn = EncryptUtil.decode(encBuf);
		assertEquals(rtn,ansJson);
	}

	@Test
	void testDecodeUser() {
		LoginUser user = EncryptUtil.decodeUser(encBuf);
		assertEquals(user.getId(),"user");
		assertEquals(user.getName(),"山田太郎");
		assertEquals(user.getExpiry(),"2021-12-13 06:05:38.1");
		assertEquals(user.getRole(),"user");
	}

	@Test
	void testHashPassword() {
		String rtn = EncryptUtil.hashPassword("passw0rd");
		assertEquals(rtn,"");
	}


}
