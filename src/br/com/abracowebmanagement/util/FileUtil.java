package br.com.abracowebmanagement.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {
	
	/**
	 * Method to get a byte Array 
	 * since 06/10/2019
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public byte[] toByteArrayUsingJava(InputStream is) throws IOException {
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    int reads = is.read();
	    while (reads != -1) {
	        baos.write(reads);
	        reads = is.read();
	    }
	    return baos.toByteArray();
	}

}
