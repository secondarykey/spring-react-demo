package com.example.demo.transfer.request;

import com.example.demo.transfer.Paging;

public class TimeInsertRequest {
	private String value;
	private String zone;
	private Paging paging;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Paging getPaging() {
		return paging;
	}
	public void setPaging(Paging paging) {
		this.paging = paging;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
}
