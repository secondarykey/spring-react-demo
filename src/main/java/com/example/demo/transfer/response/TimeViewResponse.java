package com.example.demo.transfer.response;

import java.util.List;

import com.example.demo.model.Times;
import com.example.demo.transfer.Paging;

public class TimeViewResponse {
	private List<Times> times;
	private Paging paging;
	public List<Times> getTimes() {
		return times;
	}
	public void setTimes(List<Times> times) {
		this.times = times;
	}
	public Paging getPaging() {
		return paging;
	}
	public void setPaging(Paging paging) {
		this.paging = paging;
	}
	
}
