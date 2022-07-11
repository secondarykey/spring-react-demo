/**
 * Auto Generated:Mon Jul 11 09:04:26 JST 2022
 * Original SQL:
CREATE TABLE PLACES (
    ID SERIAL PRIMARY KEY,
    NAME VARCHAR(128),
    TIMEZONE VARCHAR(32)
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
@Table("PLACES")
public class Places extends ModelImpl
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
     * Original SQL: NAME VARCHAR(128)
     */
    @Column("NAME")
    @MappingName(value="name")
    private String name;
    /**
     * 
     * Original SQL: TIMEZONE VARCHAR(32)
)
     */
    @Column("TIMEZONE")
    @MappingName(value="timezone")
    private String timezone;
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
    public void setTimezone(String timezone) {
      this.timezone = timezone;
    }
    /**
     *  の取得
     */
    public String getTimezone() {
        return this.timezone;
    }

}
