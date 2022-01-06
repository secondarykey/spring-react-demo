package com.example.demo.transfer.response;

import java.util.List;

import com.example.demo.model.Operation;
import com.example.demo.model.Worker;

public class OperationResponse {
	private Operation operation;
	private List<Worker> workers;
	public Operation getOperation() {
		return operation;
	}
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	public List<Worker> getWorkers() {
		return workers;
	}
	public void setWorkers(List<Worker> workers) {
		this.workers = workers;
	}
}
