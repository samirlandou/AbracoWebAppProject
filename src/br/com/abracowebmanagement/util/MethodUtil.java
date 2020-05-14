package br.com.abracowebmanagement.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;



public class MethodUtil {
	
	/**
	 * Validate a CPF
	 * 
	 * @param cpf
	 * @return
	 */
	public boolean validateCPF(String cpf) {

		String cpfNumber = cpf.replaceAll("[-/.]", "");
		
		// cpfNumber's error formed by a sequence of equal numbers is considered.
		if (cpfNumber.replaceAll("[-/]", "").equals("00000000000") || cpfNumber.equals("11111111111") || cpfNumber.equals("22222222222")
				|| cpfNumber.equals("33333333333") || cpfNumber.equals("44444444444") || cpfNumber.equals("55555555555")
				|| cpfNumber.equals("66666666666") || cpfNumber.equals("77777777777") || cpfNumber.equals("88888888888")
				|| cpfNumber.equals("99999999999") || (cpfNumber.length() != 11))
			return (false);

		char dig10, dig11;
		int sm, i, r, num, peso;

		// "try" - protects the code for any "int" type conversion errors
		try {
			
			// Calculation of the 1st check digit
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {

				/*
				 * converts the i-th character of the CPF into a number: for
				 * example, transforms the character '0' into the integer 0 (48
				 * is the position of '0' in the ASCII table)
				 */

				num = (int) (cpfNumber.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else
				dig10 = (char) (r + 48); // converts to the respective numeric character

			// Calculation of the 2nd check digit
			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (cpfNumber.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else
				dig11 = (char) (r + 48);

			// Checks whether the calculated digits match the entered digits.
			if ((dig10 == cpfNumber.charAt(9)) && (dig11 == cpfNumber.charAt(10)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}

	
	/**
	 * Print CPF
	 * 
	 * @param cpf
	 * @return
	 */
	public String printCPF(String cpf) {
	return (cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-"
			+ cpf.substring(9, 11));
	}
	
	
	
	public boolean validateCNPJ(String cnpj) {
		
		String cnpjNumber = cnpj.replaceAll("[-/.]", "");
		
		// cnpjNumber's error formed by a sequence of equal numbers is considered.
		if (cnpjNumber.equals("00000000000000") || cnpj.equals("11111111111111") || cnpjNumber.equals("22222222222222")
				|| cnpj.equals("33333333333333") || cnpjNumber.equals("44444444444444") || cnpj.equals("55555555555555")
				|| cnpjNumber.equals("66666666666666") || cnpj.equals("77777777777777") || cnpjNumber.equals("88888888888888")
				|| cnpj.equals("99999999999999") || (cnpjNumber.length() != 14))
			return (false);

		char dig13, dig14;
		int sm, i, r, num, peso;

		// "try" - protege o código para eventuais erros de conversao de tipo
		// (int)
		try {
			
			// Calculation of the 1st check digit
			sm = 0;
			peso = 2;
			for (i = 11; i >= 0; i--) {

				/*
				 * converts the i-th character of the CNPJ into a number: for
				 * example, transforms the character '0' into the integer 0 (48
				 * is the position of '0' in the ASCII table)
				 */
				num = (int) (cnpjNumber.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig13 = '0';
			else
				dig13 = (char) ((11 - r) + 48);

			// Calculation of the 2nd check digit
			sm = 0;
			peso = 2;
			for (i = 12; i >= 0; i--) {
				num = (int) (cnpjNumber.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig14 = '0';
			else
				dig14 = (char) ((11 - r) + 48);

			// Checks whether the calculated digits match the entered digits.
			if ((dig13 == cnpjNumber.charAt(12)) && (dig14 == cnpjNumber.charAt(13)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}

	
	/**
	 * Print CNPJ
	 * 
	 * @param cnpj
	 * @return
	 */
	public String printCNPJ(String cnpj) {
		//  formated CNPJ: 99.999.999.9999-99
		return (cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "." + cnpj.substring(5, 8) + "."
				+ cnpj.substring(8, 12) + "-" + cnpj.substring(12, 14));
	}
	
	
	/**
	 * Valid Email
	 * 
	 * @param email
	 * @return
	 */
	public boolean validateEmail(String email) {
	    boolean result = true;
	    try {
	        InternetAddress emailAddr = new InternetAddress(email);
	        emailAddr.validate();
	    } catch (AddressException ex) {
	        result = false;
	    }
	    return result;
	}
	

	/**
	 * Create Folder And Return Path As String
	 * @param path
	 * @return
	 */
	public String createFolderAndReturnPathAsString(String path){
		
		//Create directory
		 new File(path).mkdirs();
		 
		 return path;
	}

	
	/**
	 * Create Folder
	 * @param path
	 * @return
	 */
	public void createFolder(String path){
		
		//Create directory
		 new File(path).mkdirs();
	}
	
	
	public void deleteFolder(String path) throws IOException{
		
		FileUtils.deleteDirectory(new File(path));
	}
	
	
	
	public StreamedContent doStreamContent(String imagePath){
		
		StreamedContent streamContent = null;

		//Set Default Image User Path		
		Path path = Paths.get("C:/Users/Samir Landou/Documents/Desenvolvimento/Uploads/Default/Login/defaultUserImage.png");
		
		if (imagePath != null) {
			path = Paths.get(imagePath);
		}
			
			InputStream is = null;
			try {
				is = Files.newInputStream(path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			streamContent = new DefaultStreamedContent(is);
			
		
		return streamContent;
		
		
	}
	
	
	
	public String setCookie() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
		Cookie cookie = new Cookie("test", "Setted at time " + System.currentTimeMillis());
		cookie.setMaxAge(60 * 60 * 24 * 365);
		response.addCookie(cookie);
		return "verify_cookie";
	}
	 
	public String getTestCookie() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			if ("test".equals(cookies[i].getName())) {
				return cookies[i].getValue();
			}
		}
		return null;
	}
	
	
	public static Cookie eraseCookie(String strCookieName, String strPath) {
	    Cookie cookie = new Cookie(strCookieName, "");
	    cookie.setMaxAge(0);
	    cookie.setPath(strPath);

	    return cookie;
	}
}
