package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// TODO テスト時に同一アプリ内に存在するとなる
//@SpringBootApplication
public class BatchApplication {
	public static void main(String[] args) {
		System.exit(
			SpringApplication.exit(
				SpringApplication.run(BatchApplication.class, args)
			)
		);
	}
}
