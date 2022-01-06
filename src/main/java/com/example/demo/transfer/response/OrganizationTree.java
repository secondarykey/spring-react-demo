package com.example.demo.transfer.response;

import java.util.List;

public class OrganizationTree {
	
	private Integer key;
	private String title;
	private List<OrganizationTree> children;

	
	public Integer getKey() {
		return key;
	}
	public void setKey(Integer key) {
		this.key = key;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<OrganizationTree> getChildren() {
		return children;
	}
	public void setChildren(List<OrganizationTree> children) {
		this.children = children;
	}
}
