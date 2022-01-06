package com.example.demo.transfer.response;

import java.util.ArrayList;
import java.util.List;

public class WorkerResponse {
	
	private List<OperationResponse> operationList = new ArrayList<>();
	
	private int max = 4;

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public List<OperationResponse> getOperationList() {
		return operationList;
	}

	public void addOperation(OperationResponse operation) {
		this.operationList.add(operation);
	}

}
