package com.example.demo.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.example.demo.anotation.model.MappingRS;

@Table("OPERATION")
public class Worker implements Model {
	@Id
	@Column("ID")
	@MappingRS("id")
	private int id;
	
	@Column("SEQ")
	@MappingRS("seq")
	private String seq;

	@Column("START")
	@MappingRS(value="start",method="getDate")
	private Date start;
	
	@Column("END")
	@MappingRS(value="end",method="getDate")
	private Date end;

	@Transient
	private String name;	

}
