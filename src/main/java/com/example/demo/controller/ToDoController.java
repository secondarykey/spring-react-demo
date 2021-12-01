package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.ToDo;
import com.example.demo.service.ToDoService;
import com.example.demo.transfer.request.ToDoRequest;
import com.example.demo.transfer.response.Result;
import com.example.demo.transfer.response.ToDoViewResponse;

/**
 * デモ用のサンプルコントローラー
 */
@RestController
@RequestMapping("/api/demo/todo")
public class ToDoController extends JSONController {
    @Autowired
    private ToDoService toDoService;

	@RequestMapping(value = "/view",method = RequestMethod.POST)
	public Result<ToDoViewResponse> view(@RequestBody ToDoRequest json,@RequestHeader(name="Authorization",required = true) String bearer) {
		
		Result<ToDoViewResponse> result = new Result<>();
		List<ToDo> list = toDoService.find();
		ToDoViewResponse res = new ToDoViewResponse();
		res.setTodos(list);
		result.setResult(res);
		return result;
	}
	@RequestMapping(value = "/register",method = RequestMethod.POST)
	public Result<Integer> register(@RequestBody ToDoRequest json) {
		ToDo todo = new ToDo();
		todo.setValue(json.getValue());
		Result<Integer> result = new Result<>();

		toDoService.insert(todo);
		result.setResult(todo.getId());
		
		return result;
	}
	@RequestMapping(value = "/delete",method = RequestMethod.DELETE)
	public Result<Integer> delete(@RequestBody ToDoRequest json) {
		ToDo todo = new ToDo();
		todo.setId(json.getId());
		Result<Integer> result = new Result<>();
		int ret = toDoService.delete(todo);
		result.setResult(ret);
		return result;
	}
}
