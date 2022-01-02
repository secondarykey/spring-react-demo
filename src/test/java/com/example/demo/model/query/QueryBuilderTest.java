package com.example.demo.model.query;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.mapping.SQLBuilder;
import com.example.demo.model.ToDo;

@ExtendWith(MockitoExtension.class)
public class QueryBuilderTest {

    @Mock
    private ResultSet resultSet;

	@Test
	void testGenerate() {
		//省略型の場合エスケープする
		String todoAs = SQLBuilder.generateColumns(ToDo.class, "", "");
		assertEquals(todoAs,"\"ID\" AS \"id\", \"VALUE\" AS \"value\"");

		todoAs = SQLBuilder.generateColumns(ToDo.class, "TODOS.", "todos.");
		assertEquals(todoAs,"TODOS.ID AS todos.id, TODOS.VALUE AS todos.value");
	}
	
	@Test
	void testCreate() throws SQLException {

		Mockito.when(resultSet.getInt("id")).thenReturn(2021);	
		Mockito.when(resultSet.getString("value")).thenReturn("Value");	

		ToDo model = SQLBuilder.create(ToDo.class,"",resultSet);

		assertEquals(model.getId(),2021);
		assertEquals(model.getValue(),"Value");
	}
	
}
