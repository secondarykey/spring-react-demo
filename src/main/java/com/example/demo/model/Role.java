/**
 * Auto Generated:Thu Jul 07 09:04:13 JST 2022
 * Original SQL:
CREATE TABLE ROLE (
    ID VARCHAR(16) PRIMARY KEY,
    NAME VARCHAR(128)
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
@Table("ROLE")
public class Role extends ModelImpl
    implements Serializable ,Persistable<String> {

    /** シリアルバージョンID **/
	private static final long serialVersionUID = 1L;


    /**
     * 
     * Original SQL: ID VARCHAR(16) PRIMARY KEY
     */
    @Id
    @Column("ID")
    @MappingName(value="id")
    private String id;
    /**
     * 
     * Original SQL: NAME VARCHAR(128)
)
     */
    @Column("NAME")
    @MappingName(value="name")
    private String name;
    /**
     *  の設定
     */
    public void setId(String id) {
      this.id = id;
    }
    /**
     *  の取得
     */
    public String getId() {
        return this.id;
    }
    /**
     *  の設定
     */
    public void setName(String name) {
      this.name = name;
    }
    /**
     *  の取得
     */
    public String getName() {
        return this.name;
    }

}
