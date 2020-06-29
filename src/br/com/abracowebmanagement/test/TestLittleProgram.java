package br.com.abracowebmanagement.test;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.GregorianCalendar;

public abstract class TestLittleProgram {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int time = 98;
	
		int hours = time / 60;
		int minutes = time % 60;
		//System.out.printf("%02d:%02d", hours, minutes);

		String formatHour = String.format("%02d", hours);
		
		String formatMinutes = String.format("%02d", minutes % 60);
		
		String formatTime = (hours == 0 ? "": formatHour + "h") + (minutes % 60 == 0 ? "": formatMinutes + "mn");
	
		System.out.println(formatTime);
		
		String teste = convertIntoddMMyyyy(new Date());
		System.out.println(teste);
	
	}

	
	public static String convertIntoddMMyyyy(Date date){
		
		//Transform Date into LocalDateTime
		LocalDateTime ldt = convertDateToLocalDateTime(date);
		
		//Format day
		String day = ldt.getDayOfMonth() < 10 ? "0" + ldt.getDayOfMonth() : String.valueOf(ldt.getDayOfMonth());
		
		//Format month
		String month = (ldt.getMonthValue()) < 10 ? "0" + (ldt.getMonthValue()) : String.valueOf(ldt.getMonthValue());
		
		
		//Return date into dd/MM/yyyy format		
		return day + "/" + month + "/" + ldt.getYear();		
	}
	
	//Convert Date to LocalDateTime
	public static LocalDateTime convertDateToLocalDateTime(Date date) {
	    GregorianCalendar cal = new GregorianCalendar();
	    cal.setTime(date);
	    ZonedDateTime zdt = cal.toZonedDateTime();
	    return zdt.toLocalDateTime();
	}
}
