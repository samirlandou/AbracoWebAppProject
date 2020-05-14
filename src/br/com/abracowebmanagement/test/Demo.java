package br.com.abracowebmanagement.test;

import org.apache.tomcat.util.codec.binary.Base64;

public class Demo {
	
    public static void main(String args[])
    {
        //String demo = System.getProperty("user.home");
        //System.out.println(demo);
        
		//String md5 = "e10adc3949ba59abbe56e057f20f883e";//"5756ba3274a81093b0eae45beef96488";
	    
		//Fazer o teste --> 
	    //byte[] bytes = Base64.encodeBase64(new BigInteger(md5, 16 /*or 128bits*/).toByteArray());
	    //String s = new String(bytes);
	    //System.out.println(s);
	     //V1a6MnSoEJOw6uRb7vlkiA
	     
	    // e10adc3949ba59abbe56e057f20f883e
	    
	    String originalInput = "123456";
	    String encodedString = new String(Base64.encodeBase64(originalInput.getBytes()));
	    String decodedString = new String(Base64.decodeBase64(encodedString.getBytes()));
	    
	    System.out.println(encodedString);
	    System.out.println(decodedString);

    }
    
    



	
	
}
