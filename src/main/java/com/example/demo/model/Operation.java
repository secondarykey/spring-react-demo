/**
 * Auto Generated:Thu Jul 07 09:04:13 JST 2022
 * Original SQL:
CREATE TABLE OPERATION (
    ID SERIAL PRIMARY KEY,
    OPERATION_ID INTEGER,
    ORGANIZATION_ID INTEGER,
    SEQ INTEGER,
    "START" DATE,
    "END" DATE
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
@Table("OPERATION")
public class Operation extends ModelImpl
    implements Serializable ,Persistable<Integer> {

    /** シリアルバージョンID **/
	private static final long serialVersionUID = 1L;


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
     * Original SQL: ORGANIZATION_ID INTEGER
     */
    @Column("ORGANIZATION_ID")
    @MappingName(value="organizationId")
    private Integer organizationId;
    /**
     * 
     * Original SQL: SEQ INTEGER
     */
    @Column("SEQ")
    @MappingName(value="seq")
    private Integer seq;
    /**
     * 
     * Original SQL: "START" DATE
     */
    @Column("START")
    @MappingName(value="start")
    private Date start;
    /**
     * 
     * Original SQL: "END" DATE
)
     */
    @Column("END")
    @MappingName(value="end")
    private Date end;
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
    public void setOrganizationId(Integer organizationId) {
      this.organizationId = organizationId;
    }
    /**
     *  の取得
     */
    public Integer getOrganizationId() {
        return this.organizationId;
    }
    /**
     *  の設定
     */
    public void setSeq(Integer seq) {
      this.seq = seq;
    }
    /**
     *  の取得
     */
    public Integer getSeq() {
        return this.seq;
    }
    /**
     *  の設定
     */
    public void setStart(Date start) {
      this.start = start;
    }
    /**
     *  の取得
     */
    public Date getStart() {
        return this.start;
    }
    /**
     *  の設定
     */
    public void setEnd(Date end) {
      this.end = end;
    }
    /**
     *  の取得
     */
    public Date getEnd() {
        return this.end;
    }

}
