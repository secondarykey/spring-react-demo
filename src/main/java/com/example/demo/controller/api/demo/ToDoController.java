package com.example.demo.controller.api.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.JSONController;
import com.example.demo.service.ToDoService;
import com.example.demo.transfer.request.ToDoRequest;
import com.example.demo.transfer.response.Result;
import com.example.demo.transfer.response.ToDoViewResponse;

/**
 * デモ用のサンプルコントローラー
 */
@RestController
@RequestMapping("/api/demo/todo")
@ConditionalOnWebApplication
public class ToDoController extends JSONController {
    @Autowired
    private ToDoService todoService;

	@RequestMapping(value = "/view",method = RequestMethod.POST)
	public Result<ToDoViewResponse> view(@RequestBody ToDoRequest json) {
		return todoService.find(json);
	}
	@RequestMapping(value = "/register",method = RequestMethod.POST)
	public Result<Integer> register(@Validated @RequestBody ToDoRequest json) {
		return todoService.insert(json);
	}
	@RequestMapping(value = "/delete",method = RequestMethod.DELETE)
	public Result<String> delete(@RequestBody ToDoRequest json) {
		return todoService.delete(json);
	}
}
