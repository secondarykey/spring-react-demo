/**
 * Auto Generated:Sun Aug 21 09:44:17 JST 2022
 * Original SQL:
CREATE TABLE PLAN_DETAILS (
  ID SERIAL PRIMARY KEY,
  PLANS_ID INTEGER,
  NAME VARCHAR(128),
  START TIMESTAMP WITHOUT TIME ZONE,
  "END" TIMESTAMP WITHOUT TIME ZONE,
  FOREIGN KEY(PLANS_ID) REFERENCES PLANS(ID)
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
@Table("PLAN_DETAILS")
public class PlanDetails extends ModelImpl
    implements Serializable ,Persistable<Integer> {

    /** シリアルバージョンID **/
	private static final long serialVersionUID = 1L;


	public static final String ID = "ID";
	public static final String PLANS_ID = "PLANS_ID";
	public static final String NAME = "NAME";
	public static final String START = "START";
	public static final String END = "END";
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
     * Original SQL: PLANS_ID INTEGER
     */
    @Column("PLANS_ID")
    @MappingName(value="plansId")
    private Integer plansId;
    /**
     * 
     * Original SQL: NAME VARCHAR(128)
     */
    @Column("NAME")
    @MappingName(value="name")
    private String name;
    /**
     * 
     * Original SQL: START TIMESTAMP WITHOUT TIME ZONE
     */
    @Column("START")
    @MappingName(value="start")
    private Date start;
    /**
     * 
     * Original SQL: "END" TIMESTAMP WITHOUT TIME ZONE
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
    public void setPlansId(Integer plansId) {
      this.plansId = plansId;
    }
    /**
     *  の取得
     */
    public Integer getPlansId() {
        return this.plansId;
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
