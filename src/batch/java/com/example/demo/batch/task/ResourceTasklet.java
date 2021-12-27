package com.example.demo.batch.task;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.demo.util.Util;

/**
 * リソースのCSVファイルから言語ファイルを作成 
 *
 */
@StepScope
@Component
public class ResourceTasklet implements Tasklet {

	private static Logger logger = LoggerFactory.getLogger(ResourceTasklet.class);
	@Value("#{jobParameters['resourceCSV']}")
	String filePath;
	@Value("#{jobParameters['output']}")
	String outputPath;

	
	private static final String CheckSumID = "RESOUCE_LANG";

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
		logger.info("ResourceTasklet:{} start",filePath);
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
			while ( (line = br.readLine()) != null ) {
				if ( logger.isDebugEnabled() ) {
					logger.debug(line);
				}
				if ( Util.isEmpty(line) ) {
					continue;
				}
				List<String> columns = createColumns(line);
				
				//TODO カラム数のチェック
				if ( headers == null ) {
					headers = columns;
				} else {
					if ( columns != null ) {
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

		String outJSON = outputPath;
		String outProperty = outputPath;
		
		if ( Util.isEmpty(outputPath) ) {
			//開発環境向け
			outJSON = "./src/frontend/src/locale-data";
			outProperty = "./src/main/resources/locale";
		}

		createJson(outJSON,languageMap);
		createProperty(outProperty,languageMap);
		
		try {
			br.close();
		} catch (IOException e) {
			throw new RuntimeException("ファイル処理例外",e);
		}

		return RepeatStatus.FINISHED;
	}

	private void createProperty(String outProperty, Map<String, Map<String, String>> languageMap) {
		
		for (Entry<String, Map<String, String>> entry  : languageMap.entrySet() ) {
			String key = entry.getKey();
			Map<String,String> values = entry.getValue();
			String file = outProperty + "/resource_" + key + ".properties";
			StringBuffer buf = new StringBuffer();
			addPropertyData(buf,values);
			
			writeFile(file,buf);
		}
	}

	private void writeFile(String file, StringBuffer buf) {
		logger.info("Output:{}",file);
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(buf.toString());
            fw.close();
        } catch (IOException ex) {
        	throw new RuntimeException("ファイル書き込み例外",ex);
        }
	}

	private void addPropertyData(StringBuffer buf,Map<String, String> values) {
		for ( Entry<String, String> entry : values.entrySet() ) {
			buf.append(entry.getKey() + "=" + entry.getValue() + "\n");
		}
		return;
	}

	private void createJson(String outJSON, Map<String, Map<String, String>> languageMap) {
		for (Entry<String, Map<String, String>> entry  : languageMap.entrySet() ) {
			String key = entry.getKey();
			Map<String,String> values = entry.getValue();
			String file = outJSON + "/" + key + ".json";

			StringBuffer buf = new StringBuffer();
			buf.append("{\n");
			addJSONData(buf,values);
			buf.append("  \"" + CheckSumID + "\":\"" + key + "\"");
			buf.append("\n}");

			writeFile(file,buf);
		}
	}

	private void addJSONData(StringBuffer buf,Map<String, String> values) {
		for ( Entry<String, String> entry : values.entrySet() ) {
			buf.append("  \"" + entry.getKey() + "\":\"" + entry.getValue() + "\",\n");
		}
		return;
	}

	private List<String> createColumns(String line) {
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
					return null;
				}
				break;
			case 1:
				csv.add(col);
				break;
			case 2:
				if ( !col.trim().equals(",") )  {
					return null;
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
