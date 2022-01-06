package com.example.demo.transfer.response;

import java.util.List;

import com.example.demo.model.Day;

public class DayResponse {

	private List<Day> days;
	private OrganizationTree org;
	public List<Day> getDays() {
		return days;
	}
	public void setDays(List<Day> days) {
		this.days = days;
	}
	public OrganizationTree getOrg() {
		return org;
	}
	public void setOrg(OrganizationTree org) {
		this.org = org;
	}
}
