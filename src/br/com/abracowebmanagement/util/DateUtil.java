package br.com.abracowebmanagement.util;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateUtil implements Serializable{

	private static final long serialVersionUID = 1L;


	//private Date beginDate;


	public void Dateutil(){
		
	}
	
	
	public String getDay(){
		Calendar.getInstance();
		return String.valueOf(Calendar.DAY_OF_MONTH);		
	}

	
	public String getMonth(){
		Calendar.getInstance();
		return String.valueOf(Calendar.MONTH);		
	}

	
	public String getYear(){
		Calendar.getInstance();
		return String.valueOf(Calendar.YEAR);		
	}
	
	
	public long getTime(Date beginDate, Date endDate, TimeUnit timeUnit){		
		long time = endDate.getTime() - beginDate.getTime();
		return timeUnit.convert(time,TimeUnit.MILLISECONDS);		
	}
	
	
	public void getStringDate(){
		final long timestamp = new Date().getTime();

		// with java.util.Date/Calendar api
		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timestamp);
		// here's how to get the minutes
		final int minutes = cal.get(Calendar.MINUTE);
		// and here's how to get the String representation
		final String timeString =
		    new SimpleDateFormat("HH:mm:ss:SSS").format(cal.getTime());
		System.out.println(minutes);
		System.out.println(timeString);
	}
	
	
	public Date getFormatedDate(String value, String inputFormat, String outputFormat){
		Date outputDate = null;
		String convertDate;
		try {
			
			// Set InputFormat
			SimpleDateFormat inputDateFormat = new SimpleDateFormat(inputFormat);
			inputDateFormat.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));			
			
            //Parse value into Date object
            Date inputDate = inputDateFormat.parse(value);
			
            //Set OutputFormat
			SimpleDateFormat outputDateFormat = new SimpleDateFormat(outputFormat);
			outputDateFormat.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
			
			//Convert result to String
			convertDate = outputDateFormat.format(inputDate);
			
			//parse result into Date object
			outputDate = outputDateFormat.parse(convertDate);		
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return outputDate;		
	}
	
/**
 * Calculate difference between two hours (date1 & date2) and minutes (date3).	
 * @param data1
 * @param data2
 * @param date3
 * @return
 */
	public static String returnDiffBetweenDates(Date data1, Date data2, int date3){
		long difMilli = data2.getTime() - data1.getTime();
		
		int timeInSeconds = (int)((difMilli / 1000) - (date3 * 60)) ;  
		int hours, minutes, seconds;
		hours = timeInSeconds / 3600;  
		timeInSeconds = timeInSeconds - (hours * 3600);  
		minutes = timeInSeconds / 60;  
		timeInSeconds = timeInSeconds - (minutes * 60);  
		seconds = timeInSeconds;  
		return fillZero(hours) + ":" + fillZero(minutes) + ":" + fillZero(seconds);
		
	}
	
	public static String returnDiffBetweenDates2(Date data1, Date data2, int date3){
		long difMilli = data2.getTime() - data1.getTime();
		
		int timeInSeconds = (int)((difMilli / 1000) - (date3 * 60)) ;  
		int hours, minutes;
		hours = timeInSeconds / 3600;  
		timeInSeconds = timeInSeconds - (hours * 3600);  
		minutes = timeInSeconds / 60;  
		timeInSeconds = timeInSeconds - (minutes * 60);
		return fillZero(hours) + ":" + fillZero(minutes);
		
	}

	public static String returnDiffBetweenDatesWithQuantity(Date data1, Date data2, int date3, int quantity){
		long difMilli = data2.getTime() - data1.getTime();
		
		int timeInSeconds = (int)(((difMilli / 1000) - (date3 * 60)) * quantity) ;  
		int hours, minutes;
		hours = timeInSeconds / 3600;  
		timeInSeconds = timeInSeconds - (hours * 3600);  
		minutes = timeInSeconds / 60;  
		timeInSeconds = timeInSeconds - (minutes * 60);
		return fillZero(hours) + ":" + fillZero(minutes);
		
	}	
	
	public static int returnDiffInMinutes(Date data1, Date data2, int date3){
		long diffMilli = data2.getTime() - data1.getTime();
		int diffMin = (int)(((diffMilli / 1000) - (date3 * 60))/60);
		return diffMin;
	}
	
	public static int returnDiffInMinutesWithQuantity(Date data1, Date data2, int date3, int quantity){
		long diffMilli = data2.getTime() - data1.getTime();
		int diffMin = (int)((((diffMilli / 1000) - (date3 * 60))/60) * quantity);
		return diffMin;
	}
	
	private static String fillZero(int number ){
		if(number >=0 && number <10){
			return "0"+number;
		}
		return String.valueOf(number);
	}
	
	public static synchronized String formatDate(Date data, String commonsPattern) {
		if (data == null || commonsPattern == null || "".equals(commonsPattern)) {
			return "";
		}
		DateFormat formatter = new SimpleDateFormat(commonsPattern);
		formatter.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
		return formatter.format(data);
	}
	
	public static synchronized Date parse(String value, String commonsPattern) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
		sdf.applyPattern(commonsPattern);
		return sdf.parse(value);
		
	}
	
	//Convert Date to LocalDateTime
	public LocalDateTime convertDateToLocalDateTime(Date date) {
	    GregorianCalendar cal = new GregorianCalendar();
	    cal.setTime(date);
	    ZonedDateTime zdt = cal.toZonedDateTime();
	    return zdt.toLocalDateTime();
	}

	//Convert LocalDateTime to Date
	public Date convertLocalDateTimeToDate(LocalDateTime ldt) {
	    ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
	    GregorianCalendar cal = GregorianCalendar.from(zdt);
	    return cal.getTime();
	}

	
	public String formatDurationTime(int time){
		
		int hours = time / 60;
		int minutes = time % 60;
		//System.out.printf("%02d:%02d", hours, minutes);

		String formatHour = String.format("%02d", hours);
		
		String formatMinutes = String.format("%02d", minutes % 60);
		
		//String formatTime = (hours == 0 ? "": formatHour + "h") + (minutes % 60 == 0 ? "": formatMinutes + "mn");
		
		return  (hours == 0 ? "": formatHour + "h") + (minutes % 60 == 0 ? "": formatMinutes + "mn");
		
	}

	
	public String convertIntoHHmm(Date date){
		
		//Transform Date into LocalDateTime
		LocalDateTime ldt = convertDateToLocalDateTime(date);
		
		//Return date into HH:mm format
		return (ldt.getHour() < 10 ? "0"+ ldt.getHour() : ldt.getHour()) 
				+ "h" 
				+ (ldt.getMinute() < 10 ? "0"+ ldt.getMinute() : ldt.getMinute());		
	}
	
	
	public String convertIntoddMMyyyy(Date date){
		
		//Transform Date into LocalDateTime
		LocalDateTime ldt = convertDateToLocalDateTime(date);
		
		//Format day
		String day = ldt.getDayOfMonth() < 10 ? "0" + ldt.getDayOfMonth() : String.valueOf(ldt.getDayOfMonth());
		
		//Format month
		String month = (ldt.getMonthValue()) < 10 ? "0" + (ldt.getMonthValue()) : String.valueOf(ldt.getMonthValue());
		
		
		//Return date into dd/MM/yyyy format		
		return day + "/" + month + "/" + ldt.getYear();		
	}
	
}
