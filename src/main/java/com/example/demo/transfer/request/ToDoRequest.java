package com.example.demo.transfer.request;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;

public class ToDoRequest extends Arguments {

	private int id;

	@NotEmpty
	private String value;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setPassword(String value) {
		this.value = value;
	}
	
    @AssertTrue
    public boolean isValid(){
    	if ( value.length() > 10 ) {
    		return false;
    	}
        return true;
    }
}
