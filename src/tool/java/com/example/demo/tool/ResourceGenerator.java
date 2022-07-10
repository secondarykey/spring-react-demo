package com.example.demo.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.example.demo.util.Util;


/**
 * リソースのCSVファイルから言語ファイルを作成 
 *
 */
public class ResourceGenerator {

	/**
	 * リソースの出力
	 * @param args
	 */
	public static void main(String[] args) {
		
		String filePath = "";
		String outProperty = "";

		if ( args == null || args.length <= 1 ) {
			filePath = "db/resource.csv";
			outProperty = "./src/main/resources/locale";
			System.out.println("入力ファイルの指定がない為、開発環境モードで動作します");
		} else {
			File out = new File("gen");
			out.mkdir();

			filePath = args[0];
			outProperty = "./gen";
		}

		System.out.println("ResourceTasklet: start:" + filePath);
	    File csv = new File(filePath); 
	    BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(csv));
		} catch (FileNotFoundException e) {
			throw new RuntimeException("ファイル読み込み例外",e);
		}

	    List<String> headers = null;
	    List<List<String>> data = new ArrayList<List<String>>();
	    String line;
		try {
			//改行データ毎に設
			while ( (line = br.readLine()) != null ) {
				if ( Util.isEmpty(line) ) {
					continue;
				}
				List<String> columns = createColumns(line);
				if ( headers == null ) {
					headers = columns;
					System.out.printf("Header:%s\n",headers);
				} else {
					if ( columns != null ) {
						if ( columns.size() < headers.size() ) {
							//IDで増やす
							while ( columns.size() != headers.size() ) {
								columns.add(columns.get(0));
							}
						} else if ( columns.size() < headers.size() ) {
							//減らす
							List<String> wk = columns;
							columns = new ArrayList<String>();
							for ( String val : wk ) {
								columns.add(val);
								if ( columns.size() == headers.size() ) {
									break;
								}
							}
						}
						data.add(columns);
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException("ファイル読み込み例外",e);
		}

		Map<String,Map<String,String>> languageMap = new HashMap<String,Map<String,String>>();
		for ( List<String> lineData : data ) {
			String resourceKey = lineData.get(0);
			for ( int idx = 1; idx < headers.size(); ++idx ) {
				String lang = headers.get(idx);
				Map<String,String> resourceMap = languageMap.get(lang);
				if ( resourceMap == null ) {
					resourceMap = new LinkedHashMap<String,String>();
					languageMap.put(lang,resourceMap);
				}
				resourceMap.put(resourceKey,lineData.get(idx));
			}
		}

		createProperty(outProperty,languageMap);
		try {
			br.close();
		} catch (IOException e) {
			throw new RuntimeException("ファイル処理例外",e);
		}

		System.out.println("ResourceTasklet: success");
	}

	/**
	 * サーバサイド言語リソース出力
	 * @param outProperty
	 * @param languageMap
	 */
	private static void createProperty(String outProperty, Map<String, Map<String, String>> languageMap) {
		
		for (Entry<String, Map<String, String>> entry  : languageMap.entrySet() ) {
			String key = entry.getKey();
			Map<String,String> values = entry.getValue();
		
			String lowKey = key.toLowerCase();
			String file = outProperty + "/resource_" + lowKey + ".properties";
			StringBuffer buf = new StringBuffer();
			addPropertyData(buf,values);
			
			writeFile(file,buf);
		}
	}

	private static void writeFile(String file, StringBuffer buf) {
		System.out.printf("Output:%s\n",file);
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(buf.toString());
            fw.close();
        } catch (IOException ex) {
        	throw new RuntimeException("ファイル書き込み例外",ex);
        }
	}

	private static void addPropertyData(StringBuffer buf,Map<String, String> values) {
		for ( Entry<String, String> entry : values.entrySet() ) {
			buf.append(entry.getKey() + "=" + entry.getValue() + "\n");
		}
		return;
	}


	private static List<String> createColumns(String line) {

		//コメントだった場合
		if ( line.charAt(0) == '#' ) {
			return null;
		}

		List<String> csv = new ArrayList<>();
		String[] columns = line.split("\"");

		int idx = 0;
		for ( String col : columns ) {
			switch (idx) {
			case 0:
				if ( !col.trim().equals("") )  {
					System.out.println("error");
					return null;
				}
				break;
			case 1:
				csv.add(col);
				break;
			case 2:
				if ( !col.trim().equals(",") )  {
					return csv;
				}
				idx = 0;
				break;
			default:
			}
			idx++;
		}
		
		return csv;
	}

}
