package com.example.demo.tool;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UploadClient {

	public static void main(String[] args) throws IOException {
		
		String URL = "http://localhost:3000/api/demo/file/upload";
	    File file = new File("C:\\Users\\secon\\Desktop\\2022-Fall\\86215113_124096422477017_6634535410624102400_n_1000x1000.webp");

	    // Set header
	    Map<String, String> headers = new HashMap<>();
	    MultiPart multipart = new MultiPart();
	    // Add file
	    multipart.addFile("file", new FileInputStream(file));
        URL url = new URL(URL);

        URLConnection httpConn = url.openConnection();


        httpConn.setRequestProperty("Content-Type", multipart.getContentType());
        if (headers != null && headers.size() > 0) {
            Iterator<String> it = headers.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                String value = headers.get(key);
                httpConn.setRequestProperty(key, value);
            }
        }
        
        httpConn.setUseCaches(false);
        ((HttpURLConnection) httpConn).setRequestMethod("PUT");
        httpConn.setDoOutput(true);
        httpConn.setDoInput(true);

        OutputStream os = httpConn.getOutputStream();
        multipart.write(os);

        // checks server's status code first
        int status = ((HttpURLConnection) httpConn).getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = httpConn.getInputStream().read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            Object response = result.toString();
            ((HttpURLConnection) httpConn).disconnect();
            System.out.println(response);
        } else {
            throw new IOException("Server returned non-OK status: " + status);
        }
	}

}
