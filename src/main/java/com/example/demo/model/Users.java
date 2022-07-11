/**
 * Auto Generated:Mon Jul 11 09:04:26 JST 2022
 * Original SQL:
CREATE TABLE USERS (
    ID VARCHAR(255) PRIMARY KEY,
    NAME VARCHAR(255),
    PASSWORD VARCHAR(255),
    ROLE VARCHAR(16),
    BELONG INTEGER,
    LANGUAGE VARCHAR(8),
    EXPIRY TIMESTAMP WITH TIME ZONE,
    FOREIGN KEY(ROLE) REFERENCES ROLE(ID),
    FOREIGN KEY(BELONG) REFERENCES ORGANIZATION(ID)
)
 */
package com.example.demo.model;

import java.io.Serializable;

import java.time.OffsetDateTime;

import org.springframework.data.domain.Persistable;
import org.springframework.data.annotation.Id;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.example.demo.model.annotation.MappingName;
import com.example.demo.model.core.ModelImpl;
/**
 * 
 */
@Table("USERS")
public class Users extends ModelImpl
    implements Serializable ,Persistable<String> {

    /** シリアルバージョンID **/
	private static final long serialVersionUID = 1L;


    /**
     * 
     * Original SQL: ID VARCHAR(255) PRIMARY KEY
     */
    @Id
    @Column("ID")
    @MappingName(value="id")
    private String id;
    /**
     * 
     * Original SQL: NAME VARCHAR(255)
     */
    @Column("NAME")
    @MappingName(value="name")
    private String name;
    /**
     * 
     * Original SQL: PASSWORD VARCHAR(255)
     */
    @Column("PASSWORD")
    @MappingName(value="password")
    private String password;
    /**
     * 
     * Original SQL: ROLE VARCHAR(16)
     */
    @Column("ROLE")
    @MappingName(value="role")
    private String role;
    /**
     * 
     * Original SQL: BELONG INTEGER
     */
    @Column("BELONG")
    @MappingName(value="belong")
    private Integer belong;
    /**
     * 
     * Original SQL: LANGUAGE VARCHAR(8)
     */
    @Column("LANGUAGE")
    @MappingName(value="language")
    private String language;
    /**
     * 
     * Original SQL: EXPIRY TIMESTAMP WITH TIME ZONE
     */
    @Column("EXPIRY")
    @MappingName(value="expiry")
    private OffsetDateTime expiry;
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
    /**
     *  の設定
     */
    public void setPassword(String password) {
      this.password = password;
    }
    /**
     *  の取得
     */
    public String getPassword() {
        return this.password;
    }
    /**
     *  の設定
     */
    public void setRole(String role) {
      this.role = role;
    }
    /**
     *  の取得
     */
    public String getRole() {
        return this.role;
    }
    /**
     *  の設定
     */
    public void setBelong(Integer belong) {
      this.belong = belong;
    }
    /**
     *  の取得
     */
    public Integer getBelong() {
        return this.belong;
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
    public void setExpiry(OffsetDateTime expiry) {
      this.expiry = expiry;
    }
    /**
     *  の取得
     */
    public OffsetDateTime getExpiry() {
        return this.expiry;
    }

}
