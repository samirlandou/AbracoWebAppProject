package br.com.abracowebmanagement.test;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.junit.Test;

public class LogTest {
	
	
	private static final Logger logger = Logger.getLogger("LogTest.class");
	
	@Test
	public void doTestLog(){
		logger.info("Trying the log ....");
	}
	
	@Test
	public void doTestLog2() {  

	    //Logger logger = Logger.getLogger("MyLog");  
	    FileHandler fh;  

	    try {  

	        // This block configure the logger with handler and formatter  
	        fh = new FileHandler("C:/Users/Samir Landou/Documents/Desenvolvimento/Arquivos/test/MyLogFile.log");  
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);  

	        // the following statement is used to log any messages  
	        logger.info("My first log");  

	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  

	    logger.info("Hi How r u?");  

	}
	
}
