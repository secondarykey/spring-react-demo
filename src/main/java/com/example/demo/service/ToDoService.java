package com.example.demo.service;

import java.util.ArrayList;
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
		Iterable<ToDo> itr = repo.findAll();
		ToDoViewResponse res = new ToDoViewResponse();
		List<ToDo> list = new ArrayList<>();
		itr.forEach(list::add);

		res.setTodos(list);
		result.setResult(res);
		return result;
	}

	public Result<Integer> insert(ToDoRequest json) {
		ToDo todo = new ToDo();
		todo.setValue(json.getValue());
		Result<Integer> result = new Result<>();
	
		todo = repo.save(todo);
		
		result.setResult(todo.getId());
		return result;
	}

	public Result<String> delete(ToDoRequest json) {
		ToDo todo = new ToDo();
		todo.setId(json.getId());
		Result<String> result = new Result<>();
		repo.delete(todo);
		result.setResult("Success");
		return result;
	}
}
