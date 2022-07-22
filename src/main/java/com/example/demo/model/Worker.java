/**
 * Auto Generated:Fri Jul 22 16:46:24 JST 2022
 * Original SQL:
CREATE TABLE WORKER (
    ID SERIAL PRIMARY KEY,
    OPERATION_ID INTEGER,
    USER_ID VARCHAR(255),
    "DATE" DATE
)
 */
package com.example.demo.model;

import java.io.Serializable;
import java.util.Date;


import org.springframework.data.domain.Persistable;
import org.springframework.data.annotation.Id;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.example.demo.model.annotation.MappingName;
import com.example.demo.model.core.ModelImpl;
/**
 * 
 */
@Table("WORKER")
public class Worker extends ModelImpl
    implements Serializable ,Persistable<Integer> {

    /** シリアルバージョンID **/
	private static final long serialVersionUID = 1L;


	public static final String ID = "ID";
	public static final String OPERATION_ID = "OPERATION_ID";
	public static final String USER_ID = "USER_ID";
	public static final String DATE = "DATE";
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
     * Original SQL: USER_ID VARCHAR(255)
     */
    @Column("USER_ID")
    @MappingName(value="userId")
    private String userId;
    /**
     * 
     * Original SQL: "DATE" DATE
)
     */
    @Column("DATE")
    @MappingName(value="date")
    private Date date;
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
    public void setUserId(String userId) {
      this.userId = userId;
    }
    /**
     *  の取得
     */
    public String getUserId() {
        return this.userId;
    }
    /**
     *  の設定
     */
    public void setDate(Date date) {
      this.date = date;
    }
    /**
     *  の取得
     */
    public Date getDate() {
        return this.date;
    }

}
