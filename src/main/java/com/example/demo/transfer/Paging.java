package com.example.demo.transfer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Paging {
	
	private static final Logger logger = LoggerFactory.getLogger(Paging.class);

	private int currentPage;
	private int numberOfDisplay;
	private int numberOfPage;
	private int dbCount = -1;

	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int current) {
		this.currentPage = current;
	}
	public int getNumberOfPage() {
		return numberOfPage;
	}
	public void setNumberOfPage(int number) {
		this.numberOfPage = number;
	}
	public int getNumberOfDisplay() {
		return numberOfDisplay;
	}
	public void setNumberOfDisplay(int numberOfDisplay) {
		this.numberOfDisplay = numberOfDisplay;
	}
	public int getDbCount() {
		return dbCount;
	}

	public void setDbCount(int dbCount) {
		this.dbCount = dbCount;
		int num = this.getNumberOfDisplay();

		this.numberOfPage = this.dbCount / num + 1;
		if ( this.dbCount % num == 0 ) {
			this.numberOfPage--;
		}
		
		if ( this.currentPage > this.numberOfPage ) {
			logger.warn("現在のページが最大のページ数を超えています。Max={},Now={}",this.numberOfPage,this.currentPage);
		}
	}

	public int getOffset() {
		if ( dbCount < 0 ) {
			throw new InternalError("setDbCount()を呼び出していません");
		}
		return (this.currentPage - 1) * this.numberOfDisplay;
	}
	
}