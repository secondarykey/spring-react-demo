package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.demo.model.ToDo;
import com.example.demo.repository.ToDoRepository;
import com.example.demo.transfer.request.ToDoRequest;
import com.example.demo.transfer.response.Result;
import com.example.demo.transfer.response.ToDoViewResponse;

@Service
public class ToDoService extends BusinessService {

	@Autowired(required=true)
	ToDoRepository repo;

	public Result<ToDoViewResponse> find(ToDoRequest json) {
		Result<ToDoViewResponse> result = new Result<>();
		List<ToDo> list = repo.find();
		ToDoViewResponse res = new ToDoViewResponse();
		res.setTodos(list);
		result.setResult(res);
		return result;
	}

	public Result<Integer> insert(ToDoRequest json) {
		ToDo todo = new ToDo();
		todo.setValue(json.getValue());
		Result<Integer> result = new Result<>();
		repo.insert(todo);
		result.setResult(todo.getId());
		return result;
	}

	public Result<Integer> delete(ToDoRequest json) {
		ToDo todo = new ToDo();
		todo.setId(json.getId());
		Result<Integer> result = new Result<>();
		int ret = repo.delete(todo);
		result.setResult(ret);
		return result;
	}

}
