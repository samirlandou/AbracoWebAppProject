package br.com.abracowebmanagement.util;

import java.text.DecimalFormat;

public class NumberFormatUtil {
	
	public String currencyFormat(Double number){
	    
		DecimalFormat df = new DecimalFormat();
	    
	    df.applyPattern("R$ #,##0.00");

		return df.format(number);		
	}


}
