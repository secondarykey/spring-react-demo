/**
 * Auto Generated:Fri Jul 22 16:46:24 JST 2022
 * Original SQL:
CREATE TABLE TODOS (
    ID SERIAL PRIMARY KEY,
    "VALUE" VARCHAR(32)
)
 */
package com.example.demo.model;

import java.io.Serializable;



import org.springframework.data.domain.Persistable;
import org.springframework.data.annotation.Id;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.example.demo.model.annotation.MappingName;
import com.example.demo.model.core.ModelImpl;
/**
 * 
 */
@Table("TODOS")
public class Todos extends ModelImpl
    implements Serializable ,Persistable<Integer> {

    /** シリアルバージョンID **/
	private static final long serialVersionUID = 1L;


	public static final String ID = "ID";
	public static final String VALUE = "VALUE";
    /**
     * 
     * Original SQL: ID SERIAL PRIMARY KEY
     */
    @Id
    @Column("ID")
    @MappingName(value="id")
    private Integer id;
    /**
     * 
     * Original SQL: "VALUE" VARCHAR(32)
)
     */
    @Column("VALUE")
    @MappingName(value="value")
    private String value;
    /**
     *  の設定
     */
    public void setId(Integer id) {
      this.id = id;
    }
    /**
     *  の取得
     */
    public Integer getId() {
        return this.id;
    }
    /**
     *  の設定
     */
    public void setValue(String value) {
      this.value = value;
    }
    /**
     *  の取得
     */
    public String getValue() {
        return this.value;
    }

}
