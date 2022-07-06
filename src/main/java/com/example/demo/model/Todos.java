/**
 * Auto Generated:Wed Jul 06 10:38:06 JST 2022
 * Original SQL:
CREATE TABLE TODOS (
    ID SERIAL PRIMARY KEY,
    "VALUE" VARCHAR(32)
)
 */
package com.example.demo.model;

import java.io.Serializable;




import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.example.demo.model.annotation.MappingName;
import com.example.demo.model.core.ModelImpl;
/**
 * 
 */
@Table("TODOS")
public class Todos extends ModelImpl
    implements Serializable  {

    /** シリアルバージョンID **/
	private static final long serialVersionUID = 1L;


    /**
     * 
     * Original SQL: ID SERIAL PRIMARY KEY
     */
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
