package com.example.demo.transfer.response;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.Worker;

public class Workers {

	private List<List<Worker>> workers = new ArrayList<>();
	public List<List<Worker>> getWorkers() {
		return workers;
	}

	public void addWorkers(List<Worker> workers) {
		this.workers.add(workers);
	}
}
