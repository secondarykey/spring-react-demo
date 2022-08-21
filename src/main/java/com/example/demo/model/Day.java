/**
 * Auto Generated:Sun Aug 21 09:44:18 JST 2022
 * Original SQL:
CREATE TABLE DAY (
    ID SERIAL PRIMARY KEY,
    ORGANIZATION_ID INTEGER,
    "DAY" DATE,
    "VALUE" INTEGER
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
@Table("DAY")
public class Day extends ModelImpl
    implements Serializable ,Persistable<Integer> {

    /** シリアルバージョンID **/
	private static final long serialVersionUID = 1L;


	public static final String ID = "ID";
	public static final String ORGANIZATION_ID = "ORGANIZATION_ID";
	public static final String DAY = "DAY";
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
     * Original SQL: ORGANIZATION_ID INTEGER
     */
    @Column("ORGANIZATION_ID")
    @MappingName(value="organizationId")
    private Integer organizationId;
    /**
     * 
     * Original SQL: "DAY" DATE
     */
    @Column("DAY")
    @MappingName(value="day")
    private Date day;
    /**
     * 
     * Original SQL: "VALUE" INTEGER
)
     */
    @Column("VALUE")
    @MappingName(value="value")
    private Integer value;
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
    public void setDay(Date day) {
      this.day = day;
    }
    /**
     *  の取得
     */
    public Date getDay() {
        return this.day;
    }
    /**
     *  の設定
     */
    public void setValue(Integer value) {
      this.value = value;
    }
    /**
     *  の取得
     */
    public Integer getValue() {
        return this.value;
    }

}
