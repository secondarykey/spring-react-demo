package com.example.demo.transfer.response;

import java.io.Serializable;
import java.util.List;

import com.example.demo.model.Todos;

public class ToDoViewResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Todos> todos;

	public List<Todos> getTodos() {
		return todos;
	}

	public void setTodos(List<Todos> todos) {
		this.todos = todos;
	}

}
