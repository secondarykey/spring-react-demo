package com.example.demo.util;

import java.nio.charset.StandardCharsets;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.example.demo.transfer.LoginUser;
import com.google.gson.Gson;

public class EncryptUtil {

	private static String Alg = "AES/CBC/PKCS5Padding";
	private static String KEY = "aes-256-cbc-text";

	public static String decode(String buf) {

		byte[] chipperd = Base64.getDecoder().decode(buf);
		byte[] saltData = Arrays.copyOfRange(chipperd, 8, 16);
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("アルゴリズムエラー", e);
		}

		final byte[][] keyAndIV = generateKeyAndIV(32, 16, 1, saltData, KEY.getBytes(StandardCharsets.UTF_8), md5);
		SecretKeySpec key = new SecretKeySpec(keyAndIV[0], "AES");
		IvParameterSpec iv = new IvParameterSpec(keyAndIV[1]);
		byte[] encrypted = Arrays.copyOfRange(chipperd, 16, chipperd.length);
		try {
			Cipher c = Cipher.getInstance(Alg);
			c.init(Cipher.DECRYPT_MODE, key, iv);
			byte[] decrypted = c.doFinal(encrypted);
			return new String(decrypted);
		} catch (Exception e) {
			throw new RuntimeException("復号化で失敗", e);
		}
	}

	public static LoginUser decodeUser(String buf) {
		String jsonBuf = decode(buf);
		Gson gson = new Gson();
		return gson.fromJson(jsonBuf, LoginUser.class);
	}

	//
	// CipherJS が発行しているIVの受け渡し方法は以下に書いてあります。
	// https://stackoverflow.com/questions/11783062/how-to-decrypt-file-in-Java-encrypted-with-openssl-command-using-aes
	//
	/**
	 * 
	 * @param keyLength
	 * @param ivLength
	 * @param iterations
	 * @param salt
	 * @param password
	 * @param md
	 * @return
	 */
	public static byte[][] generateKeyAndIV(int keyLength, int ivLength, int iterations, byte[] salt, byte[] password,
			MessageDigest md) {

		int digestLength = md.getDigestLength();
		int requiredLength = (keyLength + ivLength + digestLength - 1) / digestLength * digestLength;
		byte[] generatedData = new byte[requiredLength];
		int generatedLength = 0;

		try {
			md.reset();

			// Repeat process until sufficient data has been generated
			while (generatedLength < keyLength + ivLength) {

				// Digest data (last digest if available, password data, salt if available)
				if (generatedLength > 0)
					md.update(generatedData, generatedLength - digestLength, digestLength);
				md.update(password);
				if (salt != null)
					md.update(salt, 0, 8);
				md.digest(generatedData, generatedLength, digestLength);

				// additional rounds
				for (int i = 1; i < iterations; i++) {
					md.update(generatedData, generatedLength, digestLength);
					md.digest(generatedData, generatedLength, digestLength);
				}

				generatedLength += digestLength;
			}

			// Copy key and IV into separate byte arrays
			byte[][] result = new byte[2][];
			result[0] = Arrays.copyOfRange(generatedData, 0, keyLength);
			if (ivLength > 0)
				result[1] = Arrays.copyOfRange(generatedData, keyLength, keyLength + ivLength);

			return result;

		} catch (DigestException e) {
			throw new RuntimeException(e);

		} finally {
			// Clean out temporary data
			Arrays.fill(generatedData, (byte) 0);
		}
	}
}
