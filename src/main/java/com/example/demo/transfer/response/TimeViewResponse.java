package com.example.demo.transfer.response;

import java.util.List;

import com.example.demo.model.Time;
import com.example.demo.transfer.Paging;

public class TimeViewResponse {
	private List<Time> times;
	private Paging paging;
	public List<Time> getTimes() {
		return times;
	}
	public void setTimes(List<Time> times) {
		this.times = times;
	}
	public Paging getPaging() {
		return paging;
	}
	public void setPaging(Paging paging) {
		this.paging = paging;
	}
	
}
