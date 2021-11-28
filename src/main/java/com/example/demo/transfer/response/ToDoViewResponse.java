package com.example.demo.transfer.response;

import java.io.Serializable;
import java.util.List;

import com.example.demo.model.ToDo;

public class ToDoViewResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<ToDo> todos;

	public List<ToDo> getTodos() {
		return todos;
	}

	public void setTodos(List<ToDo> todos) {
		this.todos = todos;
	}

}
