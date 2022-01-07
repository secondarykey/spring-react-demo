package com.example.demo.transfer.response;

import java.util.ArrayList;
import java.util.List;

public class WorkerResponse {
	
	private List<OperationResponse> operationList = new ArrayList<>();
	private List<Workers> workersList = new ArrayList<>();

	public List<OperationResponse> getOperationList() {
		return operationList;
	}

	public void addOperation(OperationResponse operation) {
		this.operationList.add(operation);
	}

	public List<Workers> getWorkersList() {
		return workersList;
	}

	public void addWorkers(Workers workers) {
		this.workersList.add(workers);
	}

}
