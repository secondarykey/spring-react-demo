/**
 * Auto Generated:Wed Jul 06 10:38:06 JST 2022
 * Original SQL:
CREATE TABLE TIMES (
    ID SERIAL PRIMARY KEY,
    "VALUE" VARCHAR(32),
    "DATE" date,
    "TIME" time,
    DATE_WITHOUT TIMESTAMP WITHOUT TIME ZONE,
    OFFSET_WITH TIMESTAMP WITH TIME ZONE
)
 */
package com.example.demo.model;

import java.io.Serializable;
import java.util.Date;
import java.time.OffsetDateTime;


import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.example.demo.model.annotation.MappingName;
import com.example.demo.model.core.ModelImpl;
/**
 * 
 */
@Table("TIMES")
public class Times extends ModelImpl
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
     */
    @Column("VALUE")
    @MappingName(value="value")
    private String value;
    /**
     * 
     * Original SQL: "DATE" date
     */
    @Column("DATE")
    @MappingName(value="date",method="getDate")
    private Date date;
    /**
     * 
     * Original SQL: "TIME" time
     */
    @Column("TIME")
    @MappingName(value="time",method="getTime")
    private Date time;
    /**
     * 
     * Original SQL: DATE_WITHOUT TIMESTAMP WITHOUT TIME ZONE
     */
    @Column("DATE_WITHOUT")
    @MappingName(value="dateWithout")
    private Date dateWithout;
    /**
     * 
     * Original SQL: OFFSET_WITH TIMESTAMP WITH TIME ZONE
)
     */
    @Column("OFFSET_WITH")
    @MappingName(value="offsetWith")
    private OffsetDateTime offsetWith;
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
    /**
     *  の設定
     */
    public void setTime(Date time) {
      this.time = time;
    }
    /**
     *  の取得
     */
    public Date getTime() {
        return this.time;
    }
    /**
     *  の設定
     */
    public void setDateWithout(Date dateWithout) {
      this.dateWithout = dateWithout;
    }
    /**
     *  の取得
     */
    public Date getDateWithout() {
        return this.dateWithout;
    }
    /**
     *  の設定
     */
    public void setOffsetWith(OffsetDateTime offsetWith) {
      this.offsetWith = offsetWith;
    }
    /**
     *  の取得
     */
    public OffsetDateTime getOffsetWith() {
        return this.offsetWith;
    }

}
