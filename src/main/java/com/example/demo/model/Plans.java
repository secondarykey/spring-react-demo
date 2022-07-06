/**
 * Auto Generated:Wed Jul 06 10:38:06 JST 2022
 * Original SQL:
CREATE TABLE PLANS (
  ID SERIAL PRIMARY KEY,
  PLACES_ID INTEGER,
  DATE TIMESTAMP WITHOUT TIME ZONE,
  FOREIGN KEY(PLACES_ID) REFERENCES PLACES(ID)
)
 */
package com.example.demo.model;

import java.io.Serializable;
import java.util.Date;



import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.example.demo.model.annotation.MappingName;
import com.example.demo.model.core.ModelImpl;
/**
 * 
 */
@Table("PLANS")
public class Plans extends ModelImpl
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
     * Original SQL: PLACES_ID INTEGER
     */
    @Column("PLACES_ID")
    @MappingName(value="placesId")
    private Integer placesId;
    /**
     * 
     * Original SQL: DATE TIMESTAMP WITHOUT TIME ZONE
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
    public void setPlacesId(Integer placesId) {
      this.placesId = placesId;
    }
    /**
     *  の取得
     */
    public Integer getPlacesId() {
        return this.placesId;
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
