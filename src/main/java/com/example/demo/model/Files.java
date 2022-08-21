/**
 * Auto Generated:Sun Aug 21 09:44:18 JST 2022
 * Original SQL:
CREATE TABLE FILES (
  ID SERIAL PRIMARY KEY,
  DATA BLOB
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
@Table("FILES")
public class Files extends ModelImpl
    implements Serializable ,Persistable<Integer> {

    /** シリアルバージョンID **/
	private static final long serialVersionUID = 1L;


	public static final String ID = "ID";
	public static final String DATA = "DATA";
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
     * Original SQL: DATA BLOB
)
     */
    @Column("DATA")
    @MappingName(value="data")
    private byte[] data;
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
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
}
