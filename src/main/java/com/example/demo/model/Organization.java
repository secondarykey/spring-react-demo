/**
 * Auto Generated:Thu Jul 07 09:04:13 JST 2022
 * Original SQL:
CREATE TABLE ORGANIZATION (
    ID SERIAL PRIMARY KEY,
    ORGANIZATION_ID INTEGER,
    NAME VARCHAR(128),
    PARENT INTEGER,
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
@Table("ORGANIZATION")
public class Organization extends ModelImpl
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
     * Original SQL: ORGANIZATION_ID INTEGER
     */
    @Column("ORGANIZATION_ID")
    @MappingName(value="organizationId")
    private Integer organizationId;
    /**
     * 
     * Original SQL: NAME VARCHAR(128)
     */
    @Column("NAME")
    @MappingName(value="name")
    private String name;
    /**
     * 
     * Original SQL: PARENT INTEGER
     */
    @Column("PARENT")
    @MappingName(value="parent")
    private Integer parent;
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
    public void setName(String name) {
      this.name = name;
    }
    /**
     *  の取得
     */
    public String getName() {
        return this.name;
    }
    /**
     *  の設定
     */
    public void setParent(Integer parent) {
      this.parent = parent;
    }
    /**
     *  の取得
     */
    public Integer getParent() {
        return this.parent;
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
