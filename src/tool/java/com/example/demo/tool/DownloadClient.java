package com.example.demo.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class DownloadClient {

	public static void main(String[] args) throws IOException {
		
		String URL = "http://localhost:8080/api/demo/file/get/1";
		File output = new File("1.jpg");
	    // Set header
	    //Map<String, String> headers = new HashMap<>();
	    // Add file
        URL url = new URL(URL);
        URLConnection httpConn = url.openConnection();

        httpConn.setUseCaches(false);
        ((HttpURLConnection) httpConn).setRequestMethod("GET");
        //httpConn.setDoOutput(true);
        httpConn.setDoInput(true);

        // checks server's status code first
        int status = ((HttpURLConnection) httpConn).getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            InputStream is = httpConn.getInputStream();
            //ByteArrayOutputStream result = new ByteArrayOutputStream();
            OutputStream os = new FileOutputStream(output);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) != -1) {
                os.write(buffer, 0, length);
            }
            os.close();
            is.close();

            //byte[] data = result.toByteArray();
            
        } else {
            throw new IOException("Server returned non-OK status: " + status);
        }
	}

}
