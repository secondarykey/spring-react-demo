package com.example.demo.service;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Files;
import com.example.demo.repository.FileRepository;

@Service
public class FileService  extends BusinessService {
	
	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(FileService.class);

	@Autowired(required=true)
	FileRepository query;

	public Integer put(InputStream stream) {
		
		Files file = new Files();
	
		byte[] byteArray;
		try {
			byteArray = stream.readAllBytes();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		file.setData(byteArray);
		file.setRegister(true);
		query.save(file);

		logger.info("ID:{}",file.getId());
		return file.getId();
	}

	public byte[] get(int id) {
		Files files = query.findById(id).get();
		return files.getData();
	}
}
