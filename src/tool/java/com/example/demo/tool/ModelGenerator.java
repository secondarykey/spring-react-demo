package com.example.demo.tool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.util.Util;


/**
 * モデル生成ツール
 * @author p230985
 */
public class ModelGenerator {

	private static final Logger logger = LoggerFactory.getLogger(ModelGenerator.class);
	//元にするDDL
	private static final String targetDDL = "src/main/resources/schema.sql";

	//出力パッケージ名
	private static final String packageName = "com.example.demo";
	//出力元
	private static final String outputPathRoot = "src/main/java";
	//出力先(変更する場合フォーマットも少し考慮が必要)
	private static final String genDir = "model/gen";

	public static void main(String[] args) throws Exception {
		
		String outDir = packageName.replaceAll("\\.","/");
		String outputPath = outputPathRoot + "/" + outDir + "/" + genDir;

		try {
			Files.createDirectory(Paths.get(outputPath));
		} catch ( FileAlreadyExistsException ex ) {
			//無視
		}

		/* 
		 * 現状バッチ部分は作成しない
		try {
			Files.createDirectory(Paths.get(outputBatchPath));
		} catch ( FileAlreadyExistsException ex ) {
			//無視
		}
        */

		Map<String,Table> tables = createTables();
		for (Table table : tables.values() ) {

			StringBuffer buf = generateClass(table);
			String path = outputPath + "/" + table.className + ".java";
			
			//現状バッチは作成しない
			if ( table.isBatch() ) {
				//path = outputBatchPath + "/" + table.className + ".java";
				continue;
			}

			logger.info("generate:{}" ,path);
			output(path,buf);
		
			if ( table.usePKClass() ) {
				String name = table.className + "PK";
				String pkPath = outputPath + "/" + name + ".java";
				logger.info("generate PK:{}",name);
				output(pkPath,table.pkJava);
			}
		}
		
		logger.info("success");
	}

	/**
	 * 出力部分
	 * @param out
	 * @param buf
	 */
	private static void output(String out,StringBuffer buf) {
		File  file = new File(out);
		FileWriter w;
		try {
			w = new FileWriter(file);
			w.write(buf.toString());
			w.close();
		} catch (IOException e) {
			throw new RuntimeException("ファイル出力エラー",e);
		}
	}

	/**
	 * SQLファイルを分析
	 * @return
	 */
	private static Map<String,Table> createTables() {
	
		Map<String,Table> rtn = new HashMap<>();
		String buf;
		try {
			buf = Files.readString(Paths.get(targetDDL));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		String[] sqls = buf.split(";");

		for ( String sql : sqls ) {
				
			Table table = createTable(sql);
			if ( table != null ) {
				if ( rtn.containsKey(table.className) ) {
						logger.error("出力クラス:{} , テーブル名{} はすでに存在します。",table.className,table.name);
					throw new RuntimeException("クラス名の重複");
				}
				rtn.put(table.className,table);
			} else {

				String commentLine = "comment on ";
				int idx = sql.indexOf(commentLine);
				if ( idx == -1 ) {
					logger.warn("unknown strings:" + sql);
					continue;
				}
			
				//comment on table pas_m_time_attribute is 'comment'
				//comment on column pas_m_time_attribute.created_user is 'comment'
				// (e.g.)
				// table pas_m_time_attribute is 'comment'
				String line = sql.substring(idx + commentLine.length()).trim();
				if ( !setComment(line,rtn) ) {
					logger.warn("setComment error: {}",sql);
				}
			}
		}
		return rtn;
	}

	/**
	 * コメントの設定
	 * @param rtn
	 */
	private static boolean setComment(String line,Map<String, Table> rtn) {
		// (e.g.)
		// table pas_m_time_attribute , 'comment'
		String[] keyVal = line.split(" is ");
		// (e.g.)
		// table pas_m_time_attribute
		String typeName = keyVal[0];
		// (e.g.)
		// 'comment'
		String val = keyVal[1];
		String comment = val.substring(1,val.length() - 1);

		// table,pas_m_time_attribute
		String[] tnCsv = typeName.split(" ");
		// table
		String type = tnCsv[0];
		
		// pas_m_time_attribute
		String name = tnCsv[1];
		String t = name;
		String c = "";
		
		//logger.info("comment line:{}",line);
		//logger.info("type:{} ,name:{}",type,name);
		if ( !type.equals("table") ) {
		    //pas_m_time_attribute.created_user
			String[] tc = name.split("\\.");
		    //pas_m_time_attribute
			t = tc[0];
		    //created_user
			c = tc[1];
		}

		for ( Table obj : rtn.values() ) { 
			if ( !t.equals(obj.name) ) {
				continue;
			}
			if ( type.equals("table") ) {
				obj.comment = comment;
				return true;
			}
			Column col = obj.columns.get(c);
			col.comment = comment;
			return true;
		}
		return false;
	}

	/**
	 * テーブルデータの作成
	 * @param sql 元DDL
	 * @return Tableオブジェクト
	 */
	private static Table createTable(String sql) {
		
		String lowSql = sql.toLowerCase();

		int idx = lowSql.indexOf("create table");
		if ( idx == -1 ) {
			return null;
		}

		String newSql = sql.substring(idx).trim();

		String[] csv = newSql.split(" ");

		Table table = new Table();
		table.original = newSql;
		table.name = csv[2];
	
		logger.info("TableName:{}",table.name);
		if ( table.isBatch() ) {

			table.className = toCam(table.name,true);

		} else {

			String name = toCam(table.name,true);

			String prefix = "";
			/** 接頭子
			String type = table.name.substring(4,5);
			if ( type.equals("m") ) {
				prefix = "Mst";
			} else if ( type.equals("t") ) {
				prefix = "Tbl";
			}
			 */

			table.className = prefix + name;
		}
		table.columns = createColums(sql);
		return table;
	}

	/**
	 * カラムデータ作成
	 * @param sql
	 * @return
	 */
	private static Map<String, Column> createColums(String sql) {

		Map<String,Column> columns = new LinkedHashMap<>();
	
		int first = sql.indexOf("(");
		int last = sql.lastIndexOf(")");
		
		String csv = sql.substring(first + 1,last + 1);
		String[] lines = csv.trim().split(",");

		for ( int i = 0; i < lines.length; ++i ) {

			String line = lines[i].trim();
			String lowLine = line.toLowerCase();
			if ( lowLine.indexOf("constraint") != -1 ) {
		
				//TODO PK 書き方がまずいかも
				String pk = "primary key (";
				int idx = lowLine.indexOf(pk);
				if ( idx == -1 )  {
					logger.info("---- constraint line:{}",line);
					break;
				}
				String col = line.substring(idx + pk.length());
				logger.info("---- primary key line:{}",line);
				while (true) {
					int ldx = col.indexOf(")");
					if ( ldx != -1 ) {
						col = col.substring(0,ldx);
					}

					Column c = columns.get(col);
					if ( c == null ) {
						logger.warn("not found pk column:{}",col);
						break;
					} else {
						c.id = true;
					}
					if ( ldx != -1 ) {
						break;
					}
					i++;
					col = lines[i];
				}
				continue;
			} else if ( lowLine.indexOf("foreign key") != -1 ) {
				//いまんとこ何もしない
				continue;
			}

			Column col = new Column();
			col.original = line;
			String[] chars = line.split(" ");
			col.name = chars[0];
		
			if ( col.name.indexOf("\"") == 0 && 
			     col.name.lastIndexOf("\"") == (col.name.length() - 1) ) {
				col.name = col.name.subSequence(1, col.name.length() - 1).toString();
			}

			col.type = chars[1];
			if ( "timestamp".equals(col.type.toLowerCase()) )  {
				if ( chars.length >= 3 ) {
					String with = chars[2];
					if ( with.toLowerCase().indexOf("with") != -1 ) {
						col.type += " " + with;
					}
				}
			}
			
		
			//単独行でのPKも対応
			if ( col.original.toLowerCase().indexOf("primary key") != -1 ) {
				col.id = true;
			}
			
			columns.put(col.name,col);
		}
		return columns;
	}

	/**
	 * テーブル用オブジェクト
	 * @author p230985
	 */
	private static class Table {
		public String original = "";
		public String comment = "";
		public String name = "";
		public String className = "";
		public StringBuffer pkJava = null;
		public StringBuffer idFunc = null;
		public Map<String,Column> columns = new LinkedHashMap<>();
		public boolean isBatch() {
			if ( this.name.toLowerCase().indexOf("batch_") == 0 ) {
				return true;
			}
			return false;
		}
		public boolean hasPK() {
			String pk = findPKType();
			return pk != null;
		}

		public boolean usePKClass() {
			if ( hasPK() ) {
			   return Util.isEmpty(findPKType());
			}
			return false;
		}

		public String findPKType() {
			String t = null;
			for ( Column col : columns.values() ) {
				if ( col.id ) {
					//２個存在する場合空文字
					if ( Util.isEmpty(t) ) { 
						t = toJavaType(col.type);
					} else {
						return "";
					}
				}
			}
			return t;
		}
		public String getPKType() {
			return findPKType();
		}
		public boolean useDate() {
			for ( Column col : columns.values() ) {
				if ( toJavaType(col.type).equals("Date") ) {
					return true;
				}
			}
			return false;
		}
		public boolean useOffsetDateTime() {
			for ( Column col : columns.values() ) {
				if ( toJavaType(col.type).equals("OffsetDateTime") ) {
					return true;
				}
			}
			return false;
		}
	}

	/**
	 * カラム用オブジェクト
	 * @author p230985
	 */
	private static class Column {
		public boolean id = false;
		public String original = "";
		public String name = "";
		public String type = "";
		public String comment = "";
	}

	/**
	 * クラスファイル形式作成
	 * @param table 対象テーブル
	 * @return クラス形式の文字列
	 */
	private static StringBuffer generateClass(Table table) {

		setPK(table);

		StringBuffer buf = new StringBuffer();
		String batch = "";
		if ( table.isBatch() ) {
			batch = ".batch";
		}
	
		String addImport = "";
		String addImplement = "";
		String getId = "";
		if ( table.hasPK() ) {
			if ( table.usePKClass() ) {
				addImport = persistableImport;
				addImplement = String.format(",Persistable<%sPK>",table.className);
				getId = table.idFunc.toString();
			} else {
				addImport = persistableImport + idImport;
				addImplement = String.format(",Persistable<%s>",table.getPKType());
				getId = table.idFunc.toString();
			}
		}

		//重複する為、空にする
		for ( Column col : table.columns.values() )  {
			if ( col.name.toLowerCase().equals("id") ) {
				getId = "";
			}
		}
		
		
	
		String dateImp = "";
		if ( table.useDate() )  {
			dateImp = dateImport;
		}
		String offsetImp = "";
		if ( table.useOffsetDateTime() )  {
			offsetImp = offsetDateTimeImport;
		}

		//ヘッダ部分を追加
		buf.append(String.format(importFormat,
				(new Date()).toString(),table.original,
				packageName,
				batch,dateImp,offsetImp,addImport,
				packageName,packageName));

		StringBuffer members = new StringBuffer();

		//名称用の変数を追加
		for ( Column col : table.columns.values() ) {
			members.append(String.format(getFieldFoemat,col.name.toUpperCase(),col.name));
		}

		boolean use = table.usePKClass();
		//メンバを追加
		for ( Column col : table.columns.values() ) {
			members.append(createMember(table.name,col,use));
		}
		//上と分ける必要はないけど、メンバのみを見やすくするために後に設定
		for ( Column col : table.columns.values() ) {
			members.append(createFunction(table.name,col));
		}

		//table.className = toCam(table.name,true);
		//if ( !table.isBatch() ) {
		//通常クラス名を接頭子抜きにする
		//pas_m_Xxxxxx
		//batch_Xxxxxx
		//}
		//クラス分を追加
		buf.append(String.format(classFormat,
				   table.comment,
				   table.name,
				   table.className,
				   addImplement,
				   getId,
				   members.toString()));
		return buf;
	}

	/**
	 * PKデータの作成
	 * @param table
	 * @return
	 */
	private static void setPK(Table table) {
		
		if ( !table.hasPK() ) {
			return;
		}

		String useDate = "";
		List<Column> pks = new ArrayList<>();
		for ( Column col : table.columns.values() ) {
			if ( col.id ) {
				pks.add(col);
				if ( toJavaType(col.type).equals("Date") ) {
					useDate += dateImport;
				}
				if ( toJavaType(col.type).equals("OffsetDateTime") ) {
					useDate += offsetDateTimeImport;
				}
			}
		}

		//PKクラスが必要な場合
		if ( table.usePKClass() ) {

			StringBuffer buf = new StringBuffer();
			//PKのimport文を生成
			buf.append(String.format(packageName,pkImportFormat,useDate));

			StringBuffer inner = new StringBuffer();
			//PK用のカラムを作成
			for ( Column col : pks ) {
				String map = toCam(col.name,false);
				String type = toJavaType(col.type);
				inner.append(String.format(pkMemberFormat,col.comment,type,map));
			}
		
			//上と分ける必要はないけど、メンバのみを見やすくするために後に設定
			for ( Column col : pks ) {
				inner.append(createFunction(table.name,col));
			}
			//PKクラスの型を設定
			buf.append(
				String.format(pkClassFormat,
					table.className,table.className,inner.toString()));
		
			table.pkJava = buf;
		}
		

		//元クラス用のgetId()メソッドを作成
		StringBuffer func = new StringBuffer();
		if ( table.usePKClass() ) {
			//PK の設定部分の生成
			StringBuffer setter = new StringBuffer();
			for ( Column col : pks) {
				String funcName = toCam(col.name,true);
				String memName = toCam(col.name,false);
				setter.append(String.format(pkSetFormat,funcName,memName));
			}

			//関数の書式に埋め込む
			func.append(
				String.format(getIdFormat,
					table.className,table.className,table.className,setter.toString()));

		} else {
			Column col = pks.get(0);
			if ( !Util.equals(col.name,"id") ) {
				//1つだけのPK時
				func.append(
					String.format(getSingleIdFormat,toJavaType(col.type),toCam(col.name,false)));
			} else {
				logger.info("getId() が存在する為、出力しない {}",table.name);
			}
		}

		table.idFunc = func;
	}

	/**
	 * メンバーを作成
	 * @param col
	 * @return
	 */
	private static String createMember(String tblName,Column col,boolean usePK) {
	
		String map = toCam(col.name,false);
		String type = toJavaType(col.type);
	
		String id = "";
		if ( !usePK && col.id ) {
			id = "\n    @Id";
		}

		String method = "";
		String methodFmt = ",method=\"%s\"";
		
		// JDBC と データベース型の差異がある場合
		if ( col.type.equals("date") ) {
			method = String.format(methodFmt,"getDate");
		} else if ( col.type.equals("time") ) {
			method = String.format(methodFmt,"getTime");
		}
		
		String mem = map;

		return String.format(memberFormat,
				col.comment,col.original,id,
				col.name,map,method,type,mem);
	}

	/**
	 * Javaのタイプ変換
	 * @param type
	 * @return
	 */
	private static String toJavaType(String pureType) {
		
		String type = pureType.toLowerCase();
		if ( type.indexOf("char") != -1 ||
		     type.indexOf("nvarchar") != -1 ||
		     type.indexOf("text") != -1 ||
		     type.indexOf("varchar") != -1 ) {
			return "String";
		} else if ( type.indexOf("date") != -1 ||
		     type.indexOf("datetime") != -1 ) {
			return "Date";
		} else if ( type.indexOf("time") != -1 ) {
		    if ( type.indexOf("timestamp with") != -1 ) {
		      if ( type.indexOf("timestamp without") == -1 ) {
		    	  return "OffsetDateTime";
		      }
		    }
		    //time もここに入る
			return "Date";
		} else if ( type.indexOf("int") != -1 ||
		            type.indexOf("serial") != -1 ) {
			return "Integer";
		} else if ( type.indexOf("boolean") != -1 ) {
			return "Boolean";
		} else if ( type.indexOf("double") != -1 ) {
			return "Double";
		}
		return "NotFound";
	}

	/**
	 * 関数を作成
	 * @param col
	 * @return
	 */
	private static String createFunction(String tblName,Column col) {
		StringBuffer buf = new StringBuffer();
		String hCam = toCam(col.name,true);
		String cam = toCam(col.name,false);
		String javaType = toJavaType(col.type);

		String ifline = "";
		//名称マスタのvalueの場合
		if ( "pas_m_name".equals(tblName) && "value".equals(col.name) ) {
			ifline = "if ( value == null ) return;\n      ";
		}
		//setter 6
		buf.append(String.format(setterFormat,col.comment,hCam,javaType,cam,ifline,cam,cam));

		//getter 4
		buf.append(String.format(getterFormat,col.comment,javaType,hCam,cam));
		return buf.toString();
	}

	/**
	 * スネークからキャメルに変換
	 * @param name snakeケースの文字列
	 * @return キャメルケースの文字列
	 */
	private static String toCam(String name,boolean head) {
		String[] base = name.split("_");
		StringBuffer buf = new StringBuffer();
		for ( String elm : base) {
			String p = elm.toLowerCase();
			if ( buf.length() != 0 || head ) {
				p = Util.capitalize(elm,true);
			}
			buf.append(p);
		}
		return buf.toString();
	}

	private static final String importFormat = """ 
/**
 * Auto Generated:%s
 * Original SQL:
%s
 */
package %s.model.gen%s;

import java.io.Serializable;
%s
%s

%s
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import %s.model.annotation.MappingName;
import %s.model.core.ModelImpl;
""";

	private static final String dateImport = """
import java.util.Date; """;

	private static final String offsetDateTimeImport = """
import java.time.OffsetDateTime;""";
	
	private static final String persistableImport = """ 
import org.springframework.data.domain.Persistable;
""";
	private static final String idImport = """ 
import org.springframework.data.annotation.Id;
""";

	private static final String classFormat = """ 
/**
 * %s
 */
@Table("%s")
public class %s extends ModelImpl 
    implements Serializable %s {
  
    /** シリアルバージョンID **/ 
	private static final long serialVersionUID = 1L; 

%s 
%s
}
""";

	private static final String getFieldFoemat = """ 
	public static final String %s = "%s";
""";

	private static final String getIdFormat = """ 
	/**
	 * PKの取得
	 */
	@Override
	public %sPK gewIdK() {
		%sPK pk = new %sPK();
%s
		return pk;
	}
""";
	private static final String getSingleIdFormat = """ 
	/**
	 * PKの取得
	 */
	@Override
	public %s getId() {
		return this.%s;
	}
""";

	private static final String pkSetFormat = """ 
        pk.set%s(this.%s);
""";
	
	private static final String memberFormat = """ 
    /**
     * %s
     * Original SQL: %s
     */%s
    @Column("%s")
    @MappingName(value="%s"%s)
    private %s %s;
""";

	private static final String setterFormat = """ 
    /**
     * %s の設定
     */
    public void set%s(%s %s) {
      %sthis.%s = %s;
    }  		
""";

	private static final String getterFormat = """ 
    /**
     * %s の取得
     */
    public %s get%s() {
        return this.%s;
    }  		
""";

	private static final String pkImportFormat = """ 
/**
 *
 */
package %s.model.gen;

import java.io.Serializable;
%s
""";

	private static final String pkClassFormat = """ 
/**
 * %s のPKクラス
 */
public class %sPK implements Serializable {
    /** シリアルバージョンID **/ 
	private static final long serialVersionUID = 1L; 
%s
}
""";
	private static final String pkMemberFormat = """ 
	/**
	 * %s
	 */
    private %s %s;
""";
}
