package com.example.demo.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.example.demo.KnownException;
import com.example.demo.util.FileUtil;

@Component
@ConditionalOnWebApplication
public class ContentImage {
	
	private final static Logger logger = LoggerFactory.getLogger(ContentImage.class);
	
	@Autowired
    @Value("${application.images.path}")
    private String path;
	@Autowired
    @Value("${application.images.extension}")
    private String ext;

	private MediaType type = null;
	private byte[] data = null; 

	public void set(String id) {
		
		String name = id + "." + ext;
		if ( id.indexOf(".") != -1 ) {
			name = id;
		}

		String fullPath = FileUtil.join(path,name);
		logger.info("ContentImage:{}",fullPath);

		File file = new File(fullPath);
		//存在しない場合にNotFoundに切り替える
		if ( !file.exists() ) {
			logger.warn("nothing not found content");
			throw new KnownException(HttpStatus.NOT_FOUND,"指定したパスが存在しません。",fullPath);
		}
		setContent(file);
	}

	private void setContent(File file) {
		setType(file);
		try {
			InputStream stream = new FileInputStream(file);
			data = stream.readAllBytes();
			stream.close();
		} catch ( IOException ex ) {
			throw new RuntimeException("ファイル読み込み例外",ex);
		}
	}

	private void setType(File file) {
		String name = file.getName();
		int idx = name.lastIndexOf(".");
		if ( idx == -1 ) {
			return;
		}
		String fileExt = name.substring(idx + 1);
		if ( fileExt.equals("jpg") || fileExt.equals("jpeg") ) {
			type = MediaType.IMAGE_JPEG;
		} else if ( fileExt.equals("png") ) {
			type = MediaType.IMAGE_PNG;
		} else if ( fileExt.equals("gif") ) {
			type = MediaType.IMAGE_GIF;
		}
	}

	public MediaType type() {
		return type;
	}

	public long length() {
		if ( data != null ) {
			return data.length;
		}
		return -1;
	}

	public byte[] bytes() {
		return data;
	}
}
