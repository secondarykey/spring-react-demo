package com.example.demo.model.query;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.mapping.core.SQLBuilder;
import com.example.demo.model.Todos;

@ExtendWith(MockitoExtension.class)
public class QueryBuilderTest {

    @Mock
    private ResultSet resultSet;

	
	@Test
	void testCreate() throws SQLException {

		Mockito.when(resultSet.getInt("id")).thenReturn(2021);	
		Mockito.when(resultSet.getString("value")).thenReturn("Value");	

		//assertEquals(model.getId(),2021);
		//assertEquals(model.getValue(),"Value");
	}
	
}
