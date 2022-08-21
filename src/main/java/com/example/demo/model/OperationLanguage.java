/**
 * Auto Generated:Sun Aug 21 09:44:17 JST 2022
 * Original SQL:
CREATE TABLE OPERATION_LANGUAGE (
    ID SERIAL PRIMARY KEY,
    OPERATION_ID INTEGER,
    LANGUAGE VARCHAR(8),
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
@Table("OPERATION_LANGUAGE")
public class OperationLanguage extends ModelImpl
    implements Serializable ,Persistable<Integer> {

    /** シリアルバージョンID **/
	private static final long serialVersionUID = 1L;


	public static final String ID = "ID";
	public static final String OPERATION_ID = "OPERATION_ID";
	public static final String LANGUAGE = "LANGUAGE";
	public static final String NAME = "NAME";
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
     * Original SQL: OPERATION_ID INTEGER
     */
    @Column("OPERATION_ID")
    @MappingName(value="operationId")
    private Integer operationId;
    /**
     * 
     * Original SQL: LANGUAGE VARCHAR(8)
     */
    @Column("LANGUAGE")
    @MappingName(value="language")
    private String language;
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
    public void setOperationId(Integer operationId) {
      this.operationId = operationId;
    }
    /**
     *  の取得
     */
    public Integer getOperationId() {
        return this.operationId;
    }
    /**
     *  の設定
     */
    public void setLanguage(String language) {
      this.language = language;
    }
    /**
     *  の取得
     */
    public String getLanguage() {
        return this.language;
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
