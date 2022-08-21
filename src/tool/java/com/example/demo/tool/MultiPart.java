package com.example.demo.tool;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class MultiPart {

	private String boundary;

	private Map<String,InputStream> files = new HashMap<>();
	private Map<String,String> fields = new HashMap<>();
	
	public MultiPart() {
        boundary = UUID.randomUUID().toString();	
	}
	
	public String getContentType() {
		return "multipart/form-data; boundary=" + boundary;
	}

	public void addFile(String name,InputStream stream) {
		files.put(name, stream);
	}

	public void addField(String name,String value) {
		fields.put(name, value);
	}
	
	private static final String EOL = "\r\n";

	public void write(OutputStream os) throws IOException {
		
		final String Charset = "UTF-8";

        OutputStreamWriter w = new OutputStreamWriter(os, Charset);
        PrintWriter writer = new PrintWriter(w, true);

        for ( Entry<String,String> e : fields.entrySet() ) {
        	String name = e.getKey();
        	String value = e.getValue();
            writer.append("--" + boundary).append(EOL);
            writer.append("Content-Disposition: form-data; name=\"" + name + "\"").append(EOL);
            writer.append("Content-Type: text/plain; charset=" + Charset).append(EOL);
            writer.append(EOL);
            writer.append(value).append(EOL);
            writer.flush();
        }

        for ( Entry<String,InputStream> e : files.entrySet() ) {
        	String name = e.getKey();
        	InputStream value = e.getValue();
            writer.append("--" + boundary).append(EOL);
            writer.append("Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + name + "\"").append(EOL);
            writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(name)).append(EOL);
            writer.append("Content-Transfer-Encoding: binary").append(EOL);
            writer.append(EOL);
            writer.flush();
            writeFile(os,value);
            writer.append(EOL);
            writer.flush();
        }
        
        writer.flush();
        writer.append("--" + boundary + "--").append(EOL);
        writer.close();
	}

	private void writeFile(OutputStream os,InputStream is) throws IOException {
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = is.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.flush();
        is.close();	
	}

}
